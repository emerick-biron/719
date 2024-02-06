package fr.ebiron.septunneuf.eggs.services

import fr.ebiron.septunneuf.eggs.exceptions.NotFoundException
import fr.ebiron.septunneuf.eggs.models.Egg
import fr.ebiron.septunneuf.eggs.repositories.EggRepository
import fr.ebiron.septunneuf.eggs.utils.randomHexColor
import fr.ebiron.septunneuf.eggs.utils.randomIncubationTime
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class EggService(private val db: EggRepository, private val sequenceGeneratorService: SequenceGeneratorService) {

    fun generateEgg(quantity: Int): List<Egg> {
        val eggs = List(quantity) {
            val id = sequenceGeneratorService.generateSequence(Egg.SEQUENCE_NAME)
            val color = randomHexColor()
            val incubationTime = randomIncubationTime()
            Egg(id, color, incubationTime)
        }
        db.saveAll(eggs)
        return eggs
    }

    fun getEggById(eggId: Long): Egg {
        return db.findById(eggId).orElseThrow { NotFoundException("Egg not found for id $eggId") }
    }

    fun deleteEggById(eggId: Long): Egg {
        val egg = getEggById(eggId)
        db.delete(egg)
        return egg
    }
}