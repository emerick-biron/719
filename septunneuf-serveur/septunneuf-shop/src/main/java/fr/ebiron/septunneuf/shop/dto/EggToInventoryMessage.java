package fr.ebiron.septunneuf.shop.dto;

public class EggToInventoryMessage {
    private long eggId;
    private String heroName;

    public EggToInventoryMessage(long eggId, String heroName) {
        this.eggId = eggId;
        this.heroName = heroName;
    }

    public long getEggId() {
        return eggId;
    }

    public void setEggId(long eggId) {
        this.eggId = eggId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
