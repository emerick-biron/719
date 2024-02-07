package fr.ebiron.septunneuf.inventory.services

import fr.ebiron.septunneuf.inventory.exceptions.InventoryFullException
import fr.ebiron.septunneuf.inventory.exceptions.MonsterInventoryNotFound
import fr.ebiron.septunneuf.inventory.models.MonsterInventory
import fr.ebiron.septunneuf.inventory.publisher.MonsterInventoryPublisher
import fr.ebiron.septunneuf.inventory.repositories.MonsterInventoryRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class MonsterInventoryService(
    private val db: MonsterInventoryRepository,
    private val monsterInventoryPublisher: MonsterInventoryPublisher
) {

    fun removeMonsterToInventory(heroName: String, monsterId: Long): MonsterInventory {
        val monsterInventory = db.findById(heroName)
            .orElseThrow { MonsterInventoryNotFound("MonsterInventory not found for hero $heroName") }
        monsterInventory.monsterIds.remove(monsterId)
        db.save(monsterInventory)
        return monsterInventory
    }

    fun storeMonster(heroName: String, monsterId: Long) {
        removeMonsterToInventory(heroName, monsterId)
        monsterInventoryPublisher.sendStoreMonsterMessage(heroName, monsterId)
    }

    fun releaseMonster(heroName: String, monsterId: Long) {
        removeMonsterToInventory(heroName, monsterId)
        monsterInventoryPublisher.sendDeleteMonsterMessage(heroName, monsterId)
    }

    fun addMonsterToInventory(heroName: String, monsterId: Long): MonsterInventory {
        val monsterInventory = db.findById(heroName).getOrElse { MonsterInventory(heroName) }
        if (monsterInventory.monsterIds.size >= 6) {
            throw InventoryFullException("Cannot add monter with id $monsterId to $heroName inventory. Inventory is full")
        }
        monsterInventory.monsterIds.add(monsterId)
        db.save(monsterInventory)
        return monsterInventory
    }

    fun getMonsterInventory(heroName: String): MonsterInventory {
       return db.findById(heroName).getOrElse { MonsterInventory(heroName) }
    }

}