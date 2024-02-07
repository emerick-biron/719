package fr.ebiron.septunneuf.inventory.publisher

import fr.ebiron.septunneuf.inventory.dto.EggIdsMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class EggInventoryPublisher(private val rabbitTemplate: RabbitTemplate) {

    fun sendRemoveEggsMessage(eggIds:List<Long>) {
        val message = EggIdsMessage(eggIds)
        rabbitTemplate.convertAndSend("removeEggs.queue", message)
    }
}