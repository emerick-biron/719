package fr.ebiron.septunneuf.eggs.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import kotlin.time.Duration


@Document("egg")
data class Egg(
    @Id
    val id: Long,
    val color: String,
    val incubationTime: Duration
) {
    companion object {
        @Transient
        val SEQUENCE_NAME: String = "eggs_sequence"
    }
}
