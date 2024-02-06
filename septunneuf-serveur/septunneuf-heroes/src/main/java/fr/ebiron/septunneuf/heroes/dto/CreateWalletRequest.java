package fr.ebiron.septunneuf.heroes.dto;

public class CreateWalletRequest {

    public String name;

    public CreateWalletRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
