package fr.ebiron.septunneuf.inventory.publisher

import fr.ebiron.septunneuf.inventory.dto.StoreMonsterMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class MonsterInventoryPublisher(private val rabbitTemplate: RabbitTemplate) {

    fun sendStoreMonster(heroName: String, monsterId: Long) {
        val message = StoreMonsterMessage(heroName, monsterId)
        rabbitTemplate.convertAndSend("storeMonster.queue", message)
    }
}