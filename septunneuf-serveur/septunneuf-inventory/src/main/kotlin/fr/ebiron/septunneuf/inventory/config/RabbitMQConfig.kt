package fr.ebiron.septunneuf.inventory.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {
    @Bean
    fun addEggToInventoryQueue(): Queue {
        return Queue("addEggToInventory.queue")
    }

    @Bean
    fun addMonsterToInventoryQueue(): Queue {
        return Queue("addMonsterToInventory.queue")
    }

    @Bean
    fun removeEggToInventoryQueue(): Queue {
        return Queue("removeEggToInventory.queue")
    }

    @Bean
    fun removeMonsterToInventoryQueue(): Queue {
        return Queue("removeMonsterToInventory.queue")
    }


    @Bean
    fun storeMonsterQueue():Queue{
        return Queue("storeMonster.queue")
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = producerJackson2MessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }
}