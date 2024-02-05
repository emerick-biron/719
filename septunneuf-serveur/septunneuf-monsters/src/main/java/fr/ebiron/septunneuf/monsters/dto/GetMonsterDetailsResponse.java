package fr.ebiron.septunneuf.monsters.dto;

import fr.ebiron.septunneuf.monsters.model.Monster;

public class GetMonsterDetailsResponse {
    private long id;
    private String name;
    private String color;
    private long attack;
    private long level;

    public GetMonsterDetailsResponse(Monster monster) {
        this.id = monster.getId();
        this.name = monster.getName();
        this.color = monster.getColor();
        this.attack = monster.getAttack();
        this.level = monster.getLevel();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
