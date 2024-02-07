package fr.ebiron.septunneuf.inventory.publisher

import fr.ebiron.septunneuf.inventory.dto.MonsterInventoryMessage
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MonsterInventoryPublisher(private val rabbitTemplate: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun sendStoreMonsterMessage(heroName: String, monsterId: Long) {
        log.info("Send storeMonster.queue - heroName='$heroName', monsterId=$monsterId")
        val message = MonsterInventoryMessage(heroName, monsterId)
        rabbitTemplate.convertAndSend("storeMonster.queue", message)
    }

    fun sendDeleteMonsterMessage(heroName: String, monsterId: Long) {
        log.info("Send deleteMonster.queue - heroName='$heroName', monsterId=$monsterId")
        val message = MonsterInventoryMessage(heroName, monsterId)
        rabbitTemplate.convertAndSend("deleteMonster.queue", message)
    }
}