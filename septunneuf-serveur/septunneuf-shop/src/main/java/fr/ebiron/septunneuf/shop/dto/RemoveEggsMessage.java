package fr.ebiron.septunneuf.shop.dto;


import java.util.List;

public class RemoveEggsMessage {
    private List<Long> eggIds; // Using Long to accommodate large values

    // Getter and Setter
    public List<Long> getEggIds() {
        return eggIds;
    }

    public void setEggIds(List<Long> eggIds) {
        this.eggIds = eggIds;
    }

    public RemoveEggsMessage(List<Long> eggIds) {
        this.eggIds = eggIds;
    }
}
