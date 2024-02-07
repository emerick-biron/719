package fr.ebiron.septunneuf.shop.publishers;

import fr.ebiron.septunneuf.shop.dto.CreateIncubatorMessage;
import fr.ebiron.septunneuf.shop.dto.EggToInventoryMessage;
import fr.ebiron.septunneuf.shop.dto.MonsterToInventoryMessage;
import fr.ebiron.septunneuf.shop.dto.RemoveEggsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopPublisher {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ShopPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendRemoveEggsMessage(List<Long> eggIds) {
        log.info("Send removeEggs.queue - monsterIds={}", eggIds);
        rabbitTemplate.convertAndSend("removeEggs.queue", new RemoveEggsMessage(eggIds));
    }

    public void sendAddEggToInventoryMessage(long eggId, String heroName) {
        log.info("Send addEggToInventory.queue - heroName='{}', eggId={}", heroName, eggId);
        rabbitTemplate.convertAndSend("addEggToInventory.queue", new EggToInventoryMessage(eggId, heroName));
    }

    public void sendRemoveEggToInventoryMessage(long eggId, String heroName) {
        log.info("Send removeEggToInventory.queue - heroName='{}', eggId={}", heroName, eggId);
        rabbitTemplate.convertAndSend("removeEggToInventory.queue", new EggToInventoryMessage(eggId, heroName));
    }

    public void sendRemoveMonsterToInventoryMessage(long monsterId, String heroName) {
        log.info("Send removeMonsterToInventory.queue - heroName='{}', monsterId={}", heroName, monsterId);
        rabbitTemplate.convertAndSend("removeMonsterToInventory.queue", new MonsterToInventoryMessage(monsterId, heroName));
    }

    public void sendCreateIncubatorMessage(String heroName) {
        log.info("Send createIncubator.queue - heroName='{}'", heroName);
        rabbitTemplate.convertAndSend("createIncubator.queue", new CreateIncubatorMessage(heroName));
    }
}
