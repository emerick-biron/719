package fr.ebiron.septunneuf.inventory.services

import fr.ebiron.septunneuf.inventory.exceptions.AlreadyInInventoryException
import fr.ebiron.septunneuf.inventory.exceptions.EggInventoryNotFound
import fr.ebiron.septunneuf.inventory.models.EggInventory
import fr.ebiron.septunneuf.inventory.repositories.EggInventoryRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class EggInventoryService(private val db: EggInventoryRepository) {

    fun getEggInventory(heroName: String): EggInventory {
        return db.findById(heroName).getOrElse { EggInventory(heroName) }
    }

    fun addEggToInventory(heroName: String, eggId: Long): EggInventory {
        val eggInventory = db.findById(heroName).getOrElse { EggInventory(heroName) }
        if (eggId in eggInventory.eggIds) {
            throw AlreadyInInventoryException("Egg with if $eggId already in inventory of $heroName")
        }
        eggInventory.eggIds.add(eggId)
        db.save(eggInventory)
        return eggInventory
    }

    fun removeEggToInventory(heroName: String, eggId: Long): EggInventory {
        val eggInventory =
            db.findById(heroName).orElseThrow { EggInventoryNotFound("EggInventory not found for hero $heroName") }
        eggInventory.eggIds.remove(eggId)
        db.save(eggInventory)
        return eggInventory
    }
}