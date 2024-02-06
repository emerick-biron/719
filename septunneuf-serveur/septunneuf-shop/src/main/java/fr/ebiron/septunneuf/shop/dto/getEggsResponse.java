package fr.ebiron.septunneuf.shop.dto;

import fr.ebiron.septunneuf.shop.model.Egg;

import java.util.List;

public class getEggsResponse {
    private List<Egg> eggs;

    public getEggsResponse(List<Egg> eggs) {
        this.eggs = eggs;
    }

    public List<Egg> getEggs() {
        return eggs;
    }

    public void setEggs(List<Egg> eggs) {
        this.eggs = eggs;
    }
}
