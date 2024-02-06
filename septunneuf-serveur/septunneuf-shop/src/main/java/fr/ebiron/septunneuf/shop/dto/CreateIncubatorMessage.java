package fr.ebiron.septunneuf.shop.dto;


public class CreateIncubatorMessage {
    private String heroName;

    public CreateIncubatorMessage(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
