package fr.ebiron.septunneuf.monsters.controller;

import fr.ebiron.septunneuf.monsters.dto.GetMonsterDetailsResponse;
import fr.ebiron.septunneuf.monsters.exception.NotFoundException;
import fr.ebiron.septunneuf.monsters.model.Monster;
import fr.ebiron.septunneuf.monsters.service.MonsterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monsters")
public class MonsterController {
    private final MonsterService monsterService;

    private final Logger log = LoggerFactory.getLogger(MonsterController.class);

    @Autowired
    public MonsterController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

    @GetMapping("/{monsterId}/details")
    @ResponseBody
    public GetMonsterDetailsResponse getMonsterDetails(@PathVariable long monsterId) throws NotFoundException {
        log.info("GET /monsters/{}/details", monsterId);
        Monster monster = monsterService.getMonster(monsterId);
        return new GetMonsterDetailsResponse(monster);
    }
}
