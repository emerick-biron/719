package fr.ebiron.septunneuf.inventory.repositories

import fr.ebiron.septunneuf.inventory.models.MonsterInventory
import org.springframework.data.repository.CrudRepository

interface MonsterInventoryRepository : CrudRepository<MonsterInventory, String>