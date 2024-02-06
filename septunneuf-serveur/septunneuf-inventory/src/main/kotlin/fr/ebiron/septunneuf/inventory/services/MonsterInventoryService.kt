package fr.ebiron.septunneuf.inventory.services

import fr.ebiron.septunneuf.inventory.exceptions.EggInventoryNotFound
import fr.ebiron.septunneuf.inventory.exceptions.MonsterInventoryNotFound
import fr.ebiron.septunneuf.inventory.models.EggInventory
import fr.ebiron.septunneuf.inventory.models.MonsterInventory
import fr.ebiron.septunneuf.inventory.publisher.MonsterInventoryPublisher
import fr.ebiron.septunneuf.inventory.repositories.MonsterInventoryRepository
import org.springframework.stereotype.Service

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
        monsterInventoryPublisher.sendStoreMonster(heroName, monsterId)
    }

    fun addMonsterToInventory(heroName: String, monsterId: Long) {
        println("Monster $monsterId added to $heroName")
    }
}