package fr.ebiron.septunneuf.incubators.publishers

import fr.ebiron.septunneuf.incubators.dto.CreateMonsterRequestMessage
import fr.ebiron.septunneuf.incubators.dto.EggToInventoryMessage
import fr.ebiron.septunneuf.incubators.dto.RemoveEggsMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class IncubatorPublisher(private val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun sendCreateMonsterMessage(heroName: String) {
        log.info("Send createMonster.queue - heroName='${heroName}'")
        rabbitTemplate.convertAndSend("createMonster.queue", CreateMonsterRequestMessage(heroName))
    }

    fun sendRemoveEggsMessage(eggIds: List<Long>) {
        log.info("Send removeEggs.queue - eggIds=$eggIds")
        rabbitTemplate.convertAndSend("removeEggs.queue", RemoveEggsMessage(eggIds))
    }

    fun sendRemoveEggToInventoryMessage(eggId: Long, heroName: String) {
        log.info("Send removeEggToInventory.queue - heroName='{}', eggId={}", heroName, eggId)
        rabbitTemplate.convertAndSend("removeEggToInventory.queue", EggToInventoryMessage(eggId, heroName))
    }
}