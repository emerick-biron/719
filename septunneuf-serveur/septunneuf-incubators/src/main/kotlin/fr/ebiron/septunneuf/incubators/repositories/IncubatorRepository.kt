package fr.ebiron.septunneuf.incubators.repositories

import fr.ebiron.septunneuf.incubators.models.Incubator
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime


interface IncubatorRepository: CrudRepository<Incubator, Long>{
    fun findAllByHeroName(heroName:String): List<Incubator>
    fun findByHatchingDateTimeBefore(hatchingDateTime: LocalDateTime): List<Incubator>
}