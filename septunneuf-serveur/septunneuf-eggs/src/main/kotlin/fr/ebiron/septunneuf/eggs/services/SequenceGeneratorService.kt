package fr.ebiron.septunneuf.eggs.services

import fr.ebiron.septunneuf.eggs.models.DatabaseSequence
import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service


@Service
class SequenceGeneratorService(private val mongoOperations: MongoOperations) {

    fun generateSequence(sequenceName: String): Long {
        val counter: DatabaseSequence? = mongoOperations.findAndModify(
            query(where("_id").`is`(sequenceName)),
            Update().inc("seq", 1), options().returnNew(true).upsert(true),
            DatabaseSequence::class.java
        )
        return counter?.seq ?: 1
    }
}