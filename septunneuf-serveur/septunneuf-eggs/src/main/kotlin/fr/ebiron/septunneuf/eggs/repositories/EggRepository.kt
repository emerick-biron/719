package fr.ebiron.septunneuf.eggs.repositories

import fr.ebiron.septunneuf.eggs.models.Egg
import org.springframework.data.repository.CrudRepository

interface EggRepository:CrudRepository<Egg, Long>