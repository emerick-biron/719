package fr.ebiron.septunneuf.shop.dto;


import java.util.List;

public class ListEggsResponse {
    private List<Long> eggIds;

    public List<Long> getEggIds() {
        return eggIds;
    }

    public void setEggIds(List<Long> eggIds) {
        this.eggIds = eggIds;
    }
}
