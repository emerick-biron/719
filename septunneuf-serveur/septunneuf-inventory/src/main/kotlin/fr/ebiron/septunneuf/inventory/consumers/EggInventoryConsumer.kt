package fr.ebiron.septunneuf.inventory.consumers

import fr.ebiron.septunneuf.inventory.dto.EggInventoryMessage
import fr.ebiron.septunneuf.inventory.publisher.EggInventoryPublisher
import fr.ebiron.septunneuf.inventory.services.EggInventoryService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class EggInventoryConsumer(
    private val eggInventoryService: EggInventoryService,
    private val eggInventoryPublisher: EggInventoryPublisher
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["removeEggToInventory.queue"])
    fun handleEggRemoval(message: EggInventoryMessage) {
        log.info("Received removeEggToInventory.queue - heroName='${message.heroName}', eggId=${message.eggId}")
        eggInventoryService.removeEggToInventory(message.heroName, message.eggId)
        eggInventoryPublisher.sendRemoveEggsMessage(listOf(message.eggId))
    }

    @RabbitListener(queues = ["addEggToInventory.queue"])
    fun handleEggAddition(message: EggInventoryMessage) {
        log.info("Received addEggToInventory.queue - heroName='${message.heroName}', eggId=${message.eggId}")
        eggInventoryService.addEggToInventory(message.heroName, message.eggId)
    }
}