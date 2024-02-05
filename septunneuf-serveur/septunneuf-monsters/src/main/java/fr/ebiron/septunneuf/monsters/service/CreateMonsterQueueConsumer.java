package fr.ebiron.septunneuf.monsters.service;


import fr.ebiron.septunneuf.monsters.dto.CreateMonsterMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class CreateMonsterQueueConsumer {

    @RabbitListener(queues = "createMonster.queue")
    public void receivedMessage(CreateMonsterMessage message) {
        System.out.println("Received Message From RabbitMQ: " + message.getHeroName());
        // Ajoutez ici la logique supplémentaire pour traiter le message
    }
}
