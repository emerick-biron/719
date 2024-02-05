package fr.ebiron.septunneuf.monsters.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;


@JsonAutoDetect
public class CreateMonsterMessage implements Serializable {
    private String heroName;

    public CreateMonsterMessage() {
    }

    public CreateMonsterMessage(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }


}
