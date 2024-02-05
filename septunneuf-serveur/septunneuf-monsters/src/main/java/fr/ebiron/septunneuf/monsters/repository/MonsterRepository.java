package fr.ebiron.septunneuf.monsters.repository;

import fr.ebiron.septunneuf.monsters.model.Monster;
import org.springframework.data.repository.CrudRepository;

public interface MonsterRepository extends CrudRepository<Monster, Long> {
}
