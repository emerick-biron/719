package fr.ebiron.septunneuf.monsters.dto;

public class ReleaseMonsterResponse {
    private long id;

    public ReleaseMonsterResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
