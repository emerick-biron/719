package fr.ebiron.septunneuf.incubators.services

import fr.ebiron.septunneuf.incubators.exceptions.EggAlreadyInIncubatorException
import fr.ebiron.septunneuf.incubators.exceptions.NotFoundException
import fr.ebiron.septunneuf.incubators.exceptions.TooManyIncubatorException
import fr.ebiron.septunneuf.incubators.models.Incubator
import fr.ebiron.septunneuf.incubators.publishers.IncubatorPublisher
import fr.ebiron.septunneuf.incubators.repositories.IncubatorRepository
import org.slf4j.LoggerFactory
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
    private val incubatorPublisher: IncubatorPublisher
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun createIncubator(heroName: String): Incubator {
        val ownedIncubators: List<Incubator> = getHeroIncubators(heroName)
        if (ownedIncubators.size >= 6) {
            log.error("Hero $heroName has already 6 incubators")
            throw TooManyIncubatorException("Hero $heroName has already 6 incubators")
        }
        val id = sequenceGeneratorService.generateSequence(Incubator.SEQUENCE_NAME)
        val incubator = Incubator(id, heroName)
        db.save(incubator)
        log.info("Incubator created: $incubator")
        return incubator
    }

    fun getHeroIncubators(heroName: String): List<Incubator> {
        return db.findAllByHeroName(heroName)
    }

    fun getIncubatorById(incubatorId: Long): Incubator {
        return db.findById(incubatorId).orElseThrow { NotFoundException("Incubator not found for id $incubatorId") }
    }

    fun fillIncubator(incubatorId: Long, eggId: Long, incubationTime: Duration) {
        val incubator = getIncubatorById(incubatorId)
        incubator.eggId = eggId
        incubator.hatchingDateTime = LocalDateTime.now().plus(incubationTime.toJavaDuration())
        try {
            db.save(incubator)
            log.info("Egg put in incubator: $incubator")
        } catch (e: DuplicateKeyException) {
            log.error("Egg $eggId is already in an incubator")
            throw EggAlreadyInIncubatorException("Egg $eggId is already in an incubator")
        }
    }

    @Scheduled(fixedRate = 5000)
    fun checkEggHatching() {
        log.info("Checking EggHatching")
        val now = LocalDateTime.now()
        val incubatorsWithEggHatched = db.findByHatchingDateTimeBefore(now)
        log.info("Hatched eggs: ${incubatorsWithEggHatched.map { it.eggId }}")
        incubatorsWithEggHatched.forEach {
            incubatorPublisher.sendCreateMonsterMessage(it.heroName)
        }
        val eggsToRemove = incubatorsWithEggHatched.mapNotNull { it.eggId }
        if (eggsToRemove.isNotEmpty()) {
            incubatorPublisher.sendRemoveEggsMessage(eggsToRemove)
        }
        val (toSave, toDelete) = incubatorsWithEggHatched.map { incubator ->
            incubator.apply {
                eggId = null
                hatchingDateTime = null
                durability--
            }
        }.partition { it.durability > 0 }

        db.saveAll(toSave)
        log.info("Incubators updated: $toSave")
        db.deleteAll(toDelete)
        log.info("Incubators deleted: $toDelete")
    }
}