package fr.ebiron.septunneuf.shop.repository;

import fr.ebiron.septunneuf.shop.model.Egg;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EggRepository extends ListCrudRepository<Egg, Long> {

}
