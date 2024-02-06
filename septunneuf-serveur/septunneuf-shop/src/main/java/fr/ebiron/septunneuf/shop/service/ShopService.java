package fr.ebiron.septunneuf.shop.service;

import fr.ebiron.septunneuf.shop.exception.ConflictException;
import fr.ebiron.septunneuf.shop.model.Hero;
import fr.ebiron.septunneuf.shop.repository.EggRepository;
import fr.ebiron.septunneuf.shop.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class ShopService {
    private final HeroRepository heroBD;
    private final EggRepository eggBD;

    @Autowired
    public ShopService(HeroRepository heroBD, EggRepository eggBD) {
        this.heroBD = heroBD;
        this.eggBD = eggBD;
    }

    public void UpdateEggs(){
        eggBD.deleteAll();

    }

    @ExceptionHandler
    public void CreateHeroWallet(String heroName) throws ConflictException {
        if (heroBD.existsById(heroName)){
            throw new ConflictException("Wallet for "+heroName+" already exist");
        }
        Hero hero = new Hero(heroName, 25);
        heroBD.save(hero);
        return;
    }
}
