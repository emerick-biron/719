package fr.ebiron.septunneuf.incubators.consumers

import fr.ebiron.septunneuf.incubators.dto.CreateMonsterRequestMessage
import fr.ebiron.septunneuf.incubators.services.IncubatorService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class IncubatorConsumer(private val incubatorService: IncubatorService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = ["createIncubator.queue"])
    fun handleCreateIncubator(message: CreateMonsterRequestMessage) {
        log.info("Received createIncubator.queue - heroName='${message.heroName}'")
        incubatorService.createIncubator(message.heroName)
    }
}