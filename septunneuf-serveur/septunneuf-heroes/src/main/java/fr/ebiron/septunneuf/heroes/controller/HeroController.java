package fr.ebiron.septunneuf.heroes.controller;

import fr.ebiron.septunneuf.heroes.dto.CreateHeroRequest;
import fr.ebiron.septunneuf.heroes.dto.CreateHeroResponse;
import fr.ebiron.septunneuf.heroes.dto.GetHeroDetailsResponse;
import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;
import fr.ebiron.septunneuf.heroes.model.Hero;
import fr.ebiron.septunneuf.heroes.service.HeroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/heroes")
public class HeroController {
    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CreateHeroResponse createHero(@Valid @RequestBody CreateHeroRequest req) throws ConflictException {
        Hero hero = heroService.createHero(req.getName(), req.getColor());
        return new CreateHeroResponse(hero.getName(), hero.getColor());
    }

    @GetMapping("/{heroName}/details")
    @ResponseBody
    public GetHeroDetailsResponse GetHeroDetails(@PathVariable String heroName) throws NotFoundException {
        Hero hero = heroService.getHero(heroName);
        return new GetHeroDetailsResponse(hero.getName(), hero.getColor());
    }
}
