package fr.ebiron.septunneuf.eggs.services

import fr.ebiron.septunneuf.eggs.exceptions.NotFoundException
import fr.ebiron.septunneuf.eggs.models.Egg
import fr.ebiron.septunneuf.eggs.repositories.EggRepository
import fr.ebiron.septunneuf.eggs.utils.randomHexColor
import fr.ebiron.septunneuf.eggs.utils.randomIncubationTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EggService(private val db: EggRepository, private val sequenceGeneratorService: SequenceGeneratorService) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun generateEgg(quantity: Int): List<Egg> {
        val eggs = List(quantity) {
            val id = sequenceGeneratorService.generateSequence(Egg.SEQUENCE_NAME)
            val color = randomHexColor()
            val incubationTime = randomIncubationTime()
            Egg(id, color, incubationTime)
        }
        db.saveAll(eggs)
        log.info("Eggs generated: $eggs")
        return eggs
    }

    fun getEggById(eggId: Long): Egg {
        return db.findById(eggId).orElseThrow { NotFoundException("Egg not found for id $eggId") }
    }

    fun deleteEggsById(eggIds: List<Long>) {
        db.deleteAllById(eggIds)
        log.info("Eggs deleted: $eggIds")
    }
}