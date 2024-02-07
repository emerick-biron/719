package fr.ebiron.septunneuf.storage.consumers

import fr.ebiron.septunneuf.storage.dto.StoreMonsterMessage
import fr.ebiron.septunneuf.storage.services.MonsterStorageService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MonsterStorageConsumer (
    private val monsterStorageService: MonsterStorageService
){

    @RabbitListener(queues = ["storeMonster"])
    fun handleStoreMonsterMessage(message: StoreMonsterMessage) {
        monsterStorageService.storeMonster(message.heroName, message.monsterId)
    }
}