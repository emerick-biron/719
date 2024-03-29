package fr.ebiron.septunneuf.heroes.dto;

public class CreateHeroResponse {
    private String name;
    private String color;

    public CreateHeroResponse() {
    }


    public CreateHeroResponse(String name, String color) {
        this.name = name;
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
