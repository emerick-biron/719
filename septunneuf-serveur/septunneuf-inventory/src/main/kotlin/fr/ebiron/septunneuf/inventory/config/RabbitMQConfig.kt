package fr.ebiron.septunneuf.inventory.config

import org.springframework.amqp.core.*
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
    fun removeEggToInventoryQueue(): Queue {
        return Queue("removeEggToInventory.queue")
    }

    @Bean
    fun removeMonsterToInventoryQueue(): Queue {
        return Queue("removeMonsterToInventory.queue")
    }


    @Bean
    fun storeMonsterQueue(): Queue {
        return Queue("storeMonster.queue")
    }

    @Bean
    fun deleteMonsterQueue():Queue{
        return Queue("deleteMonster.queue")
    }

    @Bean
    fun removeEggsQueue():Queue{
        return Queue("removeEggs.queue")
    }


    @Bean
    fun addMonsterToInventoryQueue(): Queue {
        return QueueBuilder.durable("addMonsterToInventory.queue")
            .deadLetterExchange("addMonsterToInventory.exchange")
            .deadLetterRoutingKey("addMonsterToInventory.queue.wait")
            .build()
    }

    @Bean
    fun addMonsterToInventoryExchange(): DirectExchange {
        return DirectExchange("addMonsterToInventory.exchange")
    }


    @Bean
    fun addMonsterToInventoryWaitQueue(): Queue {
        return QueueBuilder.durable("addMonsterToInventory.queue.wait")
            .deadLetterExchange("addMonsterToInventory.exchange")
            .deadLetterRoutingKey("addMonsterToInventory.routingKey")
            .ttl(10000)
            .build()
    }

    @Bean
    fun addMonsterToInventoryBinding(
        addMonsterToInventoryQueue: Queue,
        addMonsterToInventoryExchange: DirectExchange
    ): Binding {
        return BindingBuilder.bind(addMonsterToInventoryQueue).to(addMonsterToInventoryExchange)
            .with("addMonsterToInventory.routingKey")
    }

    @Bean
    fun addMonsterToInventoryWaitBinding(
        addMonsterToInventoryWaitQueue: Queue,
        addMonsterToInventoryExchange: DirectExchange
    ): Binding {
        return BindingBuilder.bind(addMonsterToInventoryWaitQueue).to(addMonsterToInventoryExchange)
            .with("addMonsterToInventory.queue.wait")
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