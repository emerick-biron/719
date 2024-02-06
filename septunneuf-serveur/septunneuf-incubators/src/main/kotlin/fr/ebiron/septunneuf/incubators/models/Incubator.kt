package fr.ebiron.septunneuf.incubators.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("incubator")
@CompoundIndex(def = "{'heroName': 1, 'eggId': 1}", unique = true, partialFilter = "{'eggId' : { '\$exists': true }}")
data class Incubator(
    @Id
    val id: Long,
    val heroName: String,
    var durability: Int = 5,
    var eggId: Long? = null,
    var hatchingDateTime: LocalDateTime? = null
) {
    companion object {
        @Transient
        val SEQUENCE_NAME: String = "incubators_sequence"
    }
}