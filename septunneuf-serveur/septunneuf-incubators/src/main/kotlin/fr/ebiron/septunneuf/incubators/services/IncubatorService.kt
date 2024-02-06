package fr.ebiron.septunneuf.incubators.services

import fr.ebiron.septunneuf.incubators.dto.CreateMonsterRequestMessage
import fr.ebiron.septunneuf.incubators.exceptions.EggAlreadyInIncubatorException
import fr.ebiron.septunneuf.incubators.exceptions.NotFoundException
import fr.ebiron.septunneuf.incubators.exceptions.TooManyIncubatorException
import fr.ebiron.septunneuf.incubators.models.Incubator
import fr.ebiron.septunneuf.incubators.repositories.IncubatorRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@Service
class IncubatorService(
    private val db: IncubatorRepository,
    private val sequenceGeneratorService: SequenceGeneratorService,
    private val rabbitMQSenderService: RabbitMQSenderService
) {
    fun createIncubator(heroName: String): Incubator {
        val ownedIncubators: List<Incubator> = getHeroIncubators(heroName)
        println(ownedIncubators)
        if (ownedIncubators.size >= 6) {
            throw TooManyIncubatorException("Hero $heroName has already 6 incubators")
        }
        // TODO : call shop service (POST) /shop/incubator
        val id = sequenceGeneratorService.generateSequence(Incubator.SEQUENCE_NAME)
        val incubator = Incubator(id, heroName)
        db.save(incubator)
        return incubator
    }

    fun getHeroIncubators(heroName: String): List<Incubator> {
        return db.findAllByHeroName(heroName)
    }

    fun getIncubatorById(incubatorId: Long): Incubator {
        return db.findById(incubatorId).orElseThrow { NotFoundException("Incubator not found for if $incubatorId") }
    }

    fun fillIncubator(incubatorId: Long, eggId: Long, incubationTime: Duration) {
        val incubator = getIncubatorById(incubatorId)
        incubator.eggId = eggId
        incubator.hatchingDateTime = LocalDateTime.now().plus(incubationTime.toJavaDuration())
        try {
            db.save(incubator)
        } catch (e: DuplicateKeyException) {
            throw EggAlreadyInIncubatorException("Egg $eggId is already in an incubator")
        }
    }

    @Scheduled(fixedRate = 5000)
    fun checkEggHatching() {
        val now = LocalDateTime.now()
        val incubatorsWithEggHatched = db.findByHatchingDateTimeBefore(now)

        incubatorsWithEggHatched.forEach {
            rabbitMQSenderService.sendCreateMonsterMessage(CreateMonsterRequestMessage(it.heroName))
        }

        val (toSave, toDelete) = incubatorsWithEggHatched.map { incubator ->
            incubator.apply {
                eggId = null
                hatchingDateTime = null
                durability--
            }
        }.partition { it.durability > 0 }

        db.saveAll(toSave)
        db.deleteAll(toDelete)
    }
}