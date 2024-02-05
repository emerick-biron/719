package fr.ebiron.septunneuf.heroes.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateHeroRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Color is mandatory")
    private String color;

    public CreateHeroRequest() {
    }

    public CreateHeroRequest(String name, String color) {
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
