package fr.ebiron.septunneuf.shop.dto;


import java.util.List;

public class ListMonstersResponse {
    private List<Long> monsterIds;

    public List<Long> getMonsterIds() {
        return monsterIds;
    }

    public void setMonsterIds(List<Long> monsterIds) {
        this.monsterIds = monsterIds;
    }
}
