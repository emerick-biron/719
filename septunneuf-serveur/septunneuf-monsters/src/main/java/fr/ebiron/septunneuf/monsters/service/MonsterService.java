package fr.ebiron.septunneuf.monsters.service;


import fr.ebiron.septunneuf.monsters.exception.NotFoundException;
import fr.ebiron.septunneuf.monsters.model.Monster;
import fr.ebiron.septunneuf.monsters.repository.MonsterRepository;
import fr.ebiron.septunneuf.monsters.utils.RandomGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MonsterService {
    private final MonsterRepository bd;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MonsterService(MonsterRepository bd, SequenceGeneratorService sequenceGeneratorService) {
        this.bd = bd;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    public Monster createMonster() {
        String nom = RandomGenerators.randomMonsterName();
        String color = RandomGenerators.randomHexColor();
        long id = sequenceGeneratorService.generateSequence(Monster.SEQUENCE_NAME);

        Monster monster = new Monster(id, nom, color);
        bd.save(monster);
        log.info("Monter created: {}", monster);
        return monster;
    }

    public Monster getMonster(long id) throws NotFoundException {
        return bd.findById(id).orElseThrow(() -> new NotFoundException("Monster for id " + id + " not found"));
    }

    public Monster releaseMonster(long id) throws NotFoundException {
        Monster monster = getMonster(id);
        bd.delete(monster);
        log.info("Monter deleted: {}", monster);
        return monster;
    }


}
