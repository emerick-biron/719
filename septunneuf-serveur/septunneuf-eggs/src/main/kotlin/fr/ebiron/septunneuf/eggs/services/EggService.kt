package fr.ebiron.septunneuf.eggs.services

import fr.ebiron.septunneuf.eggs.exceptions.NotFoundException
import fr.ebiron.septunneuf.eggs.models.Egg
import fr.ebiron.septunneuf.eggs.repositories.EggRepository
import fr.ebiron.septunneuf.eggs.utils.randomHexColor
import fr.ebiron.septunneuf.eggs.utils.randomIncubationTime
import org.springframework.stereotype.Service

@Service
class EggService(private val db: EggRepository, private val sequenceGeneratorService: SequenceGeneratorService) {

    fun generateEgg(): Egg {
        val id = sequenceGeneratorService.generateSequence(Egg.SEQUENCE_NAME)
        val color = randomHexColor()
        val incubationTime = randomIncubationTime()
        val egg = Egg(id, color, incubationTime)
        db.save(egg)
        return egg
    }

    fun getEggById(eggId: Long): Egg {
        return db.findById(eggId).orElseThrow { NotFoundException("Egg not found for id $eggId") }
    }
}