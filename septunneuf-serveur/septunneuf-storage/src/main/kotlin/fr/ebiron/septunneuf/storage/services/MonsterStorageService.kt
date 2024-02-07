package fr.ebiron.septunneuf.storage.services

import fr.ebiron.septunneuf.storage.models.MonsterStorage
import fr.ebiron.septunneuf.storage.repositories.MonsterStorageRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class MonsterStorageService(private val db: MonsterStorageRepository) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun storeMonster(heroName: String, monsterId: Long): MonsterStorage {
        val monsterStorage = db.findById(heroName).getOrElse { MonsterStorage(heroName) }
        monsterStorage.monsterIds.add(monsterId)
        db.save(monsterStorage)
        log.info("Monster stored: $monsterStorage")
        return monsterStorage
    }

    fun getStoredMonsters(heroName: String): MonsterStorage {
        return db.findById(heroName).getOrElse { MonsterStorage(heroName) }
    }
}