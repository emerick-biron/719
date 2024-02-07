package fr.ebiron.septunneuf.inventory.consumers

import fr.ebiron.septunneuf.inventory.dto.MonsterInventoryMessage
import fr.ebiron.septunneuf.inventory.publisher.MonsterInventoryPublisher
import fr.ebiron.septunneuf.inventory.services.MonsterInventoryService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MonsterInventoryConsumer(
    private val monsterInventoryService: MonsterInventoryService,
    private val monsterInventoryPublisher: MonsterInventoryPublisher
) {

    @RabbitListener(queues = ["removeMonsterToInventory.queue"])
    fun handleMonsterRemoval(message: MonsterInventoryMessage) {
        monsterInventoryService.removeMonsterToInventory(message.heroName, message.monsterId)
        monsterInventoryPublisher.sendDeleteMonsterMessage(message.heroName, message.monsterId)
    }

    @RabbitListener(queues = ["addMonsterToInventory.queue"])
    fun handleMonsterAddition(message: MonsterInventoryMessage) {
        monsterInventoryService.addMonsterToInventory(message.heroName, message.monsterId)
    }
}