package fr.ebiron.septunneuf.monsters.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;

import static org.springframework.amqp.core.QueueBuilder.LeaderLocator.random;

@Document("monster")
public class Monster {
    @Id
    private long id;
    private String name;

    private String color;
    private long attack;
    private long level;

    @Transient
    public static final String SEQUENCE_NAME = "monsters_sequence";

    public Monster(long id, String nom, String color) {
        this.id = id;
        this.name = nom;
        this.color = color;

        Random rand = new Random();
        this.attack = rand.nextInt(5)+1;
        this.level = 1;
    }

    public Monster() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAttack() {
        return attack;
    }

    public void setAttack(long attack) {
        this.attack = attack;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
