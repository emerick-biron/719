package fr.ebiron.septunneuf.heroes.dto;

public class CreateHeroResponse {
    private String name;

    public CreateHeroResponse() {
    }

    public CreateHeroResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
