package fr.ebiron.septunneuf.shop.config;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue removeEggs(){
        return new Queue("removeEggs.queue");
    }

    @Bean
    public Queue addEggToInventory(){
        return new Queue("addEggToInventory.queue");
    }

    @Bean
    public Queue removeEggToInventory(){
        return new Queue("removeEggToInventory.queue");
    }

    @Bean
    public Queue removeMonsterToInventory(){
        return new Queue("removeMonsterToInventory.queue");
    }

    @Bean
    public Queue createIncubator(){
        return new Queue("createIncubator.queue");
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


