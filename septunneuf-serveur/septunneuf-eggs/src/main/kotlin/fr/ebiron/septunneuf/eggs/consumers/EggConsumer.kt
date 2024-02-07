package fr.ebiron.septunneuf.eggs.consumers

import fr.ebiron.septunneuf.eggs.dto.RemoveEggsMessage
import fr.ebiron.septunneuf.eggs.services.EggService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class EggConsumer(private val eggService: EggService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["removeEggs.queue"])
    fun handleRemoveEggs(message: RemoveEggsMessage) {
        log.info("Received removeEggs.queue - eggIds=${message.eggIds}")
        eggService.deleteEggsById(message.eggIds)
    }
}