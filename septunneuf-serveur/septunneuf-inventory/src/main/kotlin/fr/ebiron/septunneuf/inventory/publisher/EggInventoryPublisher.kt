package fr.ebiron.septunneuf.inventory.publisher

import fr.ebiron.septunneuf.inventory.dto.EggIdsMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class EggInventoryPublisher(private val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun sendRemoveEggsMessage(eggIds: List<Long>) {
        log.info("Send removeEggs.queue - eggIds=$eggIds")
        val message = EggIdsMessage(eggIds)
        rabbitTemplate.convertAndSend("removeEggs.queue", message)
    }
}