package fr.ebiron.septunneuf.shop.repository;

import fr.ebiron.septunneuf.shop.model.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends CrudRepository<Hero, String> {
}
