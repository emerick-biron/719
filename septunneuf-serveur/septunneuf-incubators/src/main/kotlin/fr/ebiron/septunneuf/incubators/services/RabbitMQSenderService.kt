package fr.ebiron.septunneuf.incubators.services

import fr.ebiron.septunneuf.incubators.dto.CreateMonsterRequestMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitMQSenderService(private val rabbitTemplate: RabbitTemplate) {

    fun sendCreateMonsterMessage(message: CreateMonsterRequestMessage) {
        rabbitTemplate.convertAndSend("createMonster.queue", message)
    }
}