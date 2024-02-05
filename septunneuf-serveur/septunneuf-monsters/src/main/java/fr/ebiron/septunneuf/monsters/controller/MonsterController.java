package fr.ebiron.septunneuf.monsters.controller;

import fr.ebiron.septunneuf.monsters.dto.CreateMonsterResponse;
import fr.ebiron.septunneuf.monsters.dto.GetMonsterDetailsResponse;
import fr.ebiron.septunneuf.monsters.dto.ReleaseMonsterResponse;
import fr.ebiron.septunneuf.monsters.exception.NotFoundException;
import fr.ebiron.septunneuf.monsters.model.Monster;
import fr.ebiron.septunneuf.monsters.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monsters")
public class MonsterController {
    private final MonsterService monsterService;

    @Autowired
    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMonsterResponse createHero(){
        Monster monster = monsterService.createMonster();
        return new CreateMonsterResponse(monster.getName(), monster.getColor());
    }

    @GetMapping("/{monsterId}/details")
    @ResponseBody
    public GetMonsterDetailsResponse GetMonsterDetails(@PathVariable long monsterId) throws NotFoundException {
        Monster monster = monsterService.getMonster(monsterId);
        return new GetMonsterDetailsResponse(monster);
    }

    @DeleteMapping("/{monsterId}/release")
    @ResponseBody
    public ReleaseMonsterResponse ReleaseMonster(@PathVariable long monsterId) throws NotFoundException {

        Monster monster = monsterService.releaseMonster(monsterId);
        return new ReleaseMonsterResponse(monster.getId());
    }

}
