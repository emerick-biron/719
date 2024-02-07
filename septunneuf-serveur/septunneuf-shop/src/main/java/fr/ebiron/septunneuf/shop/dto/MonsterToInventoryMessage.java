package fr.ebiron.septunneuf.shop.dto;

public class MonsterToInventoryMessage {
    private long monsterId;
    private String heroName;

    public MonsterToInventoryMessage(long monsterId, String heroName) {
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
