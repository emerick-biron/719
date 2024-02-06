package fr.ebiron.septunneuf.shop.repository;

import fr.ebiron.septunneuf.shop.model.Egg;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EggRepository extends CrudRepository<Egg, Long> {}
