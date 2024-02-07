package fr.ebiron.septunneuf.monsters.config;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue addToInventoryQueue(){
        return QueueBuilder.durable("addMonsterToInventory.queue")
                .deadLetterExchange("addMonsterToInventory.exchange")
                .deadLetterRoutingKey("addMonsterToInventory.queue.wait")
                .build();
    }

    @Bean
    public Queue createMonsterQueue(){
        return new Queue("createMonster.queue");
    }

    @Bean
    public Queue deleteMonsterQueue(){
        return new Queue("deleteMonster.queue");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}


