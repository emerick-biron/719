package fr.ebiron.septunneuf.inventory.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("monster")
data class MonsterInventory(
    @Id
    val heroName: String,
    val monsterIds: MutableSet<Long> = mutableSetOf()
)
