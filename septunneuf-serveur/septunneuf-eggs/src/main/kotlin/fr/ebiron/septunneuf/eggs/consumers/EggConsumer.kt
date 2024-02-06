package fr.ebiron.septunneuf.eggs.consumers

import fr.ebiron.septunneuf.eggs.controllers.RemoveEggsMessage
import fr.ebiron.septunneuf.eggs.services.EggService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class EggConsumer(private val eggService: EggService) {

    @RabbitListener(queues = ["removeEggs.queue"])
    fun handleRemoveEggs(message: RemoveEggsMessage){
        eggService.deleteEggsById(message.eggIds)
    }
}