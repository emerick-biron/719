package fr.ebiron.septunneuf.inventory.consumers

import fr.ebiron.septunneuf.inventory.dto.MonsterInventoryMessage
import fr.ebiron.septunneuf.inventory.publisher.MonsterInventoryPublisher
import fr.ebiron.septunneuf.inventory.services.MonsterInventoryService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MonsterInventoryConsumer(
    private val monsterInventoryService: MonsterInventoryService,
    private val monsterInventoryPublisher: MonsterInventoryPublisher
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["removeMonsterToInventory.queue"])
    fun handleMonsterRemoval(message: MonsterInventoryMessage) {
        log.info("Received removeMonsterToInventory.queue - heroName='${message.heroName}', monsterId=${message.monsterId}")
        monsterInventoryService.removeMonsterToInventory(message.heroName, message.monsterId)
        monsterInventoryPublisher.sendDeleteMonsterMessage(message.heroName, message.monsterId)
    }

    @RabbitListener(queues = ["addMonsterToInventory.queue"])
    fun handleMonsterAddition(message: MonsterInventoryMessage) {
        log.info("Received addMonsterToInventory.queue - heroName='${message.heroName}', monsterId=${message.monsterId}")
        monsterInventoryService.addMonsterToInventory(message.heroName, message.monsterId)
    }
}