package fr.ebiron.septunneuf.monsters.service;

import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;

import fr.ebiron.septunneuf.monsters.model.Monster;
import fr.ebiron.septunneuf.monsters.repository.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class MonsterService {
    private final MonsterRepository bd;

    @Autowired
    public MonsterService(MonsterRepository bd) {
        this.bd = bd;
    }

    @ExceptionHandler
    public Monster createMonster() throws ConflictException {
        if (bd.existsById(nom)){
            throw new ConflictException("Hero "+nom+" already exist");
        }
        Hero hero = new Hero(nom, color);
        bd.save(hero);
        return hero;
    }

    public

    @ExceptionHandler
    public Monster getMonster(String name) throws NotFoundException {
        return bd.findById(name).orElseThrow(()->new NotFoundException("Hero "+name+"not found"));
    }
}
