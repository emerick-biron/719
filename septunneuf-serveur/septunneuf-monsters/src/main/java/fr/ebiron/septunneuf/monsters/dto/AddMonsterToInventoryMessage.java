package fr.ebiron.septunneuf.monsters.dto;

public class AddMonsterToInventoryMessage {

    private long monsterId;
    private String heroName;

    public AddMonsterToInventoryMessage() {
    }

    public AddMonsterToInventoryMessage(long monsterId, String heroName) {
        this.monsterId = monsterId;
        this.heroName = heroName;
    }

    public long getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(long monsterId) {
        this.monsterId = monsterId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
