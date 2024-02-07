package fr.ebiron.septunneuf.monsters.dto;

import java.io.Serializable;

public class DeleteMonsterMessage implements Serializable {
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
