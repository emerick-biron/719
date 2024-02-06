package fr.ebiron.septunneuf.shop.service;

import fr.ebiron.septunneuf.shop.dto.CreateIncubatorMessage;
import fr.ebiron.septunneuf.shop.dto.EggToInventoryMessage;
import fr.ebiron.septunneuf.shop.dto.MonsterToInventoryMessage;
import fr.ebiron.septunneuf.shop.dto.RemoveEggsMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRemoveEggsMessage(RemoveEggsMessage message) {
        rabbitTemplate.convertAndSend("removeEggs.queue", message);
    }

    public void sendAddEggToInventoryMessage(EggToInventoryMessage message) {
        rabbitTemplate.convertAndSend("addEggToInventory.queue", message);
    }

    public void sendRemoveEggToInventoryMessage(EggToInventoryMessage message) {
        rabbitTemplate.convertAndSend("removeEggToInventory.queue", message);
    }

    public void sendRemoveMonsterToInventoryMessage(MonsterToInventoryMessage message) {
        rabbitTemplate.convertAndSend("removeMonsterToInventory.queue", message);
    }

    public void sendCreateIncubatorMessage(CreateIncubatorMessage message){
        rabbitTemplate.convertAndSend("createIncubator.queue", message);
    }
}
