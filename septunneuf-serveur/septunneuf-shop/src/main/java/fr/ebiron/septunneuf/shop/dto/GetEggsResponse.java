package fr.ebiron.septunneuf.shop.dto;

import fr.ebiron.septunneuf.shop.model.Egg;

import java.util.List;

public class GetEggsResponse {
    private List<Egg> eggs;

    public GetEggsResponse(List<Egg> eggs) {
        this.eggs = eggs;
    }

    public List<Egg> getEggs() {
        return eggs;
    }

    public void setEggs(List<Egg> eggs) {
        this.eggs = eggs;
    }
}
