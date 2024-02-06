package fr.ebiron.septunneuf.monsters.service;


import fr.ebiron.septunneuf.monsters.dto.AddMonsterToInventoryMessage;
import fr.ebiron.septunneuf.monsters.dto.CreateMonsterMessage;
import fr.ebiron.septunneuf.monsters.model.Monster;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateMonsterQueueConsumer {
    private final MonsterService monsterService;
    private final AddMonsterToInventoryQueue addMonsterToInventoryQueue;

    @Autowired
    public CreateMonsterQueueConsumer(MonsterService monsterService, AddMonsterToInventoryQueue addMonsterToInventoryQueue) {
        this.monsterService = monsterService;
        this.addMonsterToInventoryQueue = addMonsterToInventoryQueue;
    }

    @RabbitListener(queues = "createMonster.queue")
    public void receivedMessage(CreateMonsterMessage message) {
        Monster monster = monsterService.createMonster();
        addMonsterToInventoryQueue.sendAddToInventoryMessage(new AddMonsterToInventoryMessage(monster.getId(), message.getHeroName()));
    }
}
