package fr.ebiron.septunneuf.monsters.service;


import fr.ebiron.septunneuf.monsters.dto.AddMonsterToInventoryMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddMonsterToInventoryQueue {


    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public AddMonsterToInventoryQueue(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAddToInventoryMessage(AddMonsterToInventoryMessage message) {
        rabbitTemplate.convertAndSend("addMonsterToInventory.queue", message);
    }
}

