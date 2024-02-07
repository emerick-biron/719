package fr.ebiron.septunneuf.monsters.dto;

import java.io.Serializable;

public class DeleteMonsterMessage {
    private Long monsterId;

    public DeleteMonsterMessage() {
    }

    public Long getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(Long monsterId) {
        this.monsterId = monsterId;
    }
}
