package fr.ebiron.septunneuf.eggs.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "database_sequences")
data class DatabaseSequence(
    @Id
    val id: String,
    val seq: Long
)
