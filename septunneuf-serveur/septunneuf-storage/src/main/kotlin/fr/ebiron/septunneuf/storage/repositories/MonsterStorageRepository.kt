package fr.ebiron.septunneuf.storage.repositories

import fr.ebiron.septunneuf.storage.models.MonsterStorage
import org.springframework.data.repository.CrudRepository

interface MonsterStorageRepository:CrudRepository<MonsterStorage, String>