package fr.ebiron.septunneuf.inventory.consumers

import fr.ebiron.septunneuf.inventory.dto.EggInventoryMessage
import fr.ebiron.septunneuf.inventory.publisher.EggInventoryPublisher
import fr.ebiron.septunneuf.inventory.services.EggInventoryService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class EggInventoryConsumer(
    private val eggInventoryService: EggInventoryService,
    private val eggInventoryPublisher: EggInventoryPublisher
) {
    @RabbitListener(queues = ["removeEggToInventory.queue"])
    fun handleEggRemoval(message: EggInventoryMessage) {
        eggInventoryService.removeEggToInventory(message.heroName, message.eggId)
        eggInventoryPublisher.sendRemoveEggsMessage(listOf(message.eggId))
    }

    @RabbitListener(queues = ["addEggToInventory.queue"])
    fun handleEggAddition(message: EggInventoryMessage) {
        eggInventoryService.addEggToInventory(message.heroName, message.eggId)
    }
}