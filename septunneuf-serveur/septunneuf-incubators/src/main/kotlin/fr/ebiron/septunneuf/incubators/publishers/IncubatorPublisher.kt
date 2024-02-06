package fr.ebiron.septunneuf.incubators.publishers

import fr.ebiron.septunneuf.incubators.dto.CreateMonsterRequestMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class IncubatorPublisher(private val rabbitTemplate: RabbitTemplate) {

    fun sendCreateMonsterMessage(heroName: String) {
        rabbitTemplate.convertAndSend("createMonster.queue", CreateMonsterRequestMessage(heroName))
    }
}