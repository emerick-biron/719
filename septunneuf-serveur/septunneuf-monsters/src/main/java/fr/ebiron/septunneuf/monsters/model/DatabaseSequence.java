package fr.ebiron.septunneuf.monsters.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    private String id;
    private Long seq;

    public Long getSeq() {
        return seq;
    }

    public DatabaseSequence() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}