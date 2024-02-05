package fr.ebiron.septunneuf.heroes.repository;

import fr.ebiron.septunneuf.heroes.model.Hero;
import org.springframework.data.repository.CrudRepository;

public interface HeroRepository extends CrudRepository<Hero, String> {
}
