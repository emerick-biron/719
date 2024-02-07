package fr.ebiron.septunneuf.monsters.consumers;


import fr.ebiron.septunneuf.monsters.dto.CreateMonsterMessage;
import fr.ebiron.septunneuf.monsters.dto.DeleteMonsterMessage;
import fr.ebiron.septunneuf.monsters.exception.NotFoundException;
import fr.ebiron.septunneuf.monsters.model.Monster;
import fr.ebiron.septunneuf.monsters.publishers.MonsterPublisher;
import fr.ebiron.septunneuf.monsters.service.MonsterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonsterConsumer {
    private final MonsterService monsterService;
    private final MonsterPublisher monsterPublisher;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MonsterConsumer(MonsterService monsterService, MonsterPublisher monsterPublisher) {
        this.monsterService = monsterService;
        this.monsterPublisher = monsterPublisher;
    }

    @RabbitListener(queues = "createMonster.queue")
    public void receivedMessage(CreateMonsterMessage message) {
        log.info("Received createMonster.queue - heroName='{}'", message.getHeroName());
        Monster monster = monsterService.createMonster();
        monsterPublisher.sendAddToInventoryMessage(monster.getId(), message.getHeroName());
    }

    @RabbitListener(queues = "deleteMonster.queue")
    public void ReleaseMonster(DeleteMonsterMessage message) throws NotFoundException {
        log.info("Received createMonster.queue - monsterId={}", message.getMonsterId());
        monsterService.releaseMonster(message.getMonsterId());
    }

}
