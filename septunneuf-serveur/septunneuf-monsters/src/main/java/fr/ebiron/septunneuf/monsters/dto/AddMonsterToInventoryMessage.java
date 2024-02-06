package fr.ebiron.septunneuf.monsters.dto;

public class AddMonsterToInventoryMessage {

    private long id;
    private String heroName;

    public AddMonsterToInventoryMessage() {
    }

    public AddMonsterToInventoryMessage(long id, String heroName) {
        this.id = id;
        this.heroName = heroName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
