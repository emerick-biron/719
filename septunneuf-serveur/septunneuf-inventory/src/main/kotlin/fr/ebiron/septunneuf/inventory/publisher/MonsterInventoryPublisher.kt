package fr.ebiron.septunneuf.inventory.publisher

import fr.ebiron.septunneuf.inventory.dto.MonsterInventoryMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MonsterInventoryPublisher(private val rabbitTemplate: RabbitTemplate) {

    fun sendStoreMonsterMessage(heroName: String, monsterId: Long) {
        val message = MonsterInventoryMessage(heroName, monsterId)
        rabbitTemplate.convertAndSend("storeMonster.queue", message)
    }

    fun sendDeleteMonsterMessage(heroName: String, monsterId: Long) {
        val message = MonsterInventoryMessage(heroName, monsterId)
        rabbitTemplate.convertAndSend("deleteMonster.queue", message)
    }
}