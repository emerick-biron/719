package fr.ebiron.septunneuf.monsters.publishers;


import fr.ebiron.septunneuf.monsters.dto.AddMonsterToInventoryMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonsterPublisher {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public MonsterPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAddToInventoryMessage(Long monsterId, String heroName) {
        log.info("Send addMonsterToInventory.queue - heroName='{}', monsterId={}", heroName, monsterId);
        AddMonsterToInventoryMessage message = new AddMonsterToInventoryMessage(monsterId, heroName);
        rabbitTemplate.convertAndSend("addMonsterToInventory.queue", message);
    }
}

