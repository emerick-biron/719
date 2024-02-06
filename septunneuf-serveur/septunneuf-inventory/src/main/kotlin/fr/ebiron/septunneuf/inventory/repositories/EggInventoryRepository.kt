package fr.ebiron.septunneuf.inventory.repositories

import fr.ebiron.septunneuf.inventory.models.EggInventory
import org.springframework.data.repository.CrudRepository

interface EggInventoryRepository : CrudRepository<EggInventory, String>