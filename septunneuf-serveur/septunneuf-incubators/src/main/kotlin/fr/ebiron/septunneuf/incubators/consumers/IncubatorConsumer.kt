package fr.ebiron.septunneuf.incubators.consumers

import fr.ebiron.septunneuf.incubators.dto.CreateMonsterRequestMessage
import fr.ebiron.septunneuf.incubators.services.IncubatorService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class IncubatorConsumer (private val incubatorService: IncubatorService){
    @RabbitListener(queues = ["createIncubator.queue"])
    fun handleCreateIncubator(message: CreateMonsterRequestMessage){
        incubatorService.createIncubator(message.heroName)
    }
}