package fr.ebiron.septunneuf.shop.dto;


import java.util.List;

public class ListIncubatorsResponse {
    private List<Long> incubatorsIds;

    public List<Long> getIncubatorsIds() {
        return incubatorsIds;
    }

    public ListIncubatorsResponse() {
    }

    public void setIncubatorsIds(List<Long> incubatorsIds) {
        this.incubatorsIds = incubatorsIds;
    }
}
