package fr.ebiron.septunneuf.storage.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("monster")
data class MonsterStorage(
    @Id
    val heroName: String,
    val monsterIds: MutableSet<Long> = mutableSetOf()
)
