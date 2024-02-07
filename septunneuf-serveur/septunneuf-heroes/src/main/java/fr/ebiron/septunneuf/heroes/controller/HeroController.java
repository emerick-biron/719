package fr.ebiron.septunneuf.heroes.controller;

import fr.ebiron.septunneuf.heroes.dto.CreateHeroRequest;
import fr.ebiron.septunneuf.heroes.dto.CreateHeroResponse;
import fr.ebiron.septunneuf.heroes.dto.GetHeroDetailsResponse;
import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;
import fr.ebiron.septunneuf.heroes.exception.WalletCreationException;
import fr.ebiron.septunneuf.heroes.model.Hero;
import fr.ebiron.septunneuf.heroes.service.HeroService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateHeroResponse createHero(@Valid @RequestBody CreateHeroRequest req) throws ConflictException, WalletCreationException {
        log.info("POST /heroes/create - name='{}', color='{}'", req.getName(), req.getColor());
        Hero hero = heroService.createHero(req.getName(), req.getColor());
        return new CreateHeroResponse(hero.getName(), hero.getColor());
    }

    @GetMapping("/{heroName}/details")
    public GetHeroDetailsResponse getHeroDetails(@PathVariable String heroName) throws NotFoundException {
        log.info("GET /heroes/{}/details", heroName);
        Hero hero = heroService.getHero(heroName);
        return new GetHeroDetailsResponse(hero.getName(), hero.getColor());
    }
}
