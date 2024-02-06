package fr.ebiron.septunneuf.inventory.consumers

import com.rabbitmq.client.GetResponse
import fr.ebiron.septunneuf.inventory.dto.MonsterInventoryMessage
import fr.ebiron.septunneuf.inventory.services.MonsterInventoryService
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import kotlin.concurrent.Volatile

@Service
class MonsterInventoryConsumer(
    private val monsterInventoryService: MonsterInventoryService,
    private val rabbitTemplate: RabbitTemplate,
    private val messageConverter: Jackson2JsonMessageConverter,
) {
    @RabbitListener(queues = ["removeMonsterToInventory.queue"])
    fun handleMonsterRemoval(message: MonsterInventoryMessage) {
        monsterInventoryService.removeMonsterToInventory(message.heroName, message.monsterId)
    }

    private val messagePropertiesConverter: MessagePropertiesConverter = DefaultMessagePropertiesConverter()


    // Pour éviter la surcharge avec les messages remis en queue en cas d'erreur, nous utilisons un cron
    // pour récupérer les messages
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    fun handleMonsterAddition() {
       rabbitTemplate.execute { channel ->
            val response: GetResponse = channel.basicGet("addMonsterToInventory.queue", false)
            var result: MonsterInventoryMessage? = null
            try {
                val messageProps: MessageProperties =
                    messagePropertiesConverter.toMessageProperties(response.props, response.envelope, "UTF-8")
                if (response.messageCount >= 0) {
                    messageProps.messageCount = response.messageCount
                }
                val message = Message(response.body, messageProps)
                result =
                    messageConverter.fromMessage(message, MonsterInventoryMessage::class) as MonsterInventoryMessage
                channel.basicAck(response.envelope.deliveryTag, false)

                // Handle message
                monsterInventoryService.addMonsterToInventory(result.heroName, result.monsterId)
            } catch (e: Exception) {
                // Envoie du Nack au broken RabbitMq afin qu'il soit remis dans la queue
                channel.basicNack(response.envelope.deliveryTag, false, true)
            }
            return@execute result
        }
    }
}