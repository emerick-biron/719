package fr.ebiron.septunneuf.storage.consumers

import fr.ebiron.septunneuf.storage.dto.StoreMonsterMessage
import fr.ebiron.septunneuf.storage.services.MonsterStorageService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MonsterStorageConsumer(
    private val monsterStorageService: MonsterStorageService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["storeMonster.queue"])
    fun handleStoreMonsterMessage(message: StoreMonsterMessage) {
        log.info("Received storeMonster.queue - heroName='${message.heroName}', monsterId=${message.monsterId}")
        monsterStorageService.storeMonster(message.heroName, message.monsterId)
    }
}