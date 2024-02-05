package fr.ebiron.septunneuf.heroes.service;

import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;
import fr.ebiron.septunneuf.heroes.model.Hero;
import fr.ebiron.septunneuf.heroes.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class HeroService {
    private final HeroRepository bd;

    @Autowired
    public HeroService(HeroRepository bd) {
        this.bd = bd;
    }

    public Hero createHero(String nom, String color) throws ConflictException {
        if (bd.existsById(nom)){
            throw new ConflictException("Hero "+nom+" already exist");
        }
        Hero hero = new Hero(nom, color);
        bd.save(hero);
        return hero;
    }

    @ExceptionHandler
    public Hero getHero(String name) throws NotFoundException {
        return bd.findById(name).orElseThrow(()->new NotFoundException("Hero "+name+"not found"));
    }
}
