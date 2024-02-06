package fr.ebiron.septunneuf.shop.dto;


import java.util.List;

public class ListIncubatorsResponse {
    private List<Long> incubatorIds;

    public List<Long> getIncubatorIds() {
        return incubatorIds;
    }

    public ListIncubatorsResponse() {
    }

    public void setIncubatorIds(List<Long> incubatorIds) {
        this.incubatorIds = incubatorIds;
    }
}
