package fr.ebiron.septunneuf.monsters.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public Monster(String nom, String color) {
        this.name = nom;
        this.color = color;
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
}
