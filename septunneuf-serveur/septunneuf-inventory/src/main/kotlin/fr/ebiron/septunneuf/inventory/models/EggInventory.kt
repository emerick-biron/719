package fr.ebiron.septunneuf.inventory.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("egg")
data class EggInventory(
    @Id
    val heroName: String,
    val eggIds: MutableSet<Long> = mutableSetOf()
)
