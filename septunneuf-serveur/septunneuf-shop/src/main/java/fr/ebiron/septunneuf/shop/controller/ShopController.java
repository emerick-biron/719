package fr.ebiron.septunneuf.shop.controller;

import fr.ebiron.septunneuf.shop.dto.GetEggsResponse;
import fr.ebiron.septunneuf.shop.dto.MoneyResponse;
import fr.ebiron.septunneuf.shop.exception.*;
import fr.ebiron.septunneuf.shop.service.ShopService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void shopUpdate() throws NotFoundException {
        log.info("POST /shop/update");
        shopService.updateEggs();
    }

    @GetMapping("/eggs")
    public GetEggsResponse getEggs() {
        log.info("GET /shop/eggs");
        return new GetEggsResponse(shopService.getEggs());
    }

    @PostMapping("/wallet/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHeroWallet(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName
    ) throws ConflictException {
        log.info("POST /shop/wallet/create - heroName='{}'", heroName);
        shopService.createHeroWallet(heroName);
    }

    @PostMapping("/eggs/{eggId}/purchase")
    public MoneyResponse eggPurchase(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName,
            @PathVariable long eggId
    ) throws NotFoundException, NotEnoughtMoney {
        log.info("POST /shop/eggs/{}/purchase - heroName='{}'", eggId, heroName);
        long money = shopService.purchaseEgg(eggId, heroName);
        return new MoneyResponse(money);
    }

    @PostMapping("/eggs/{eggId}/sell")
    public MoneyResponse eggSell(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName,
            @PathVariable long eggId
    ) throws NotFoundException, NotOwned {
        log.info("POST /shop/eggs/{}/sell - heroName='{}'", eggId, heroName);
        long money = shopService.sellEgg(eggId, heroName);
        return new MoneyResponse(money);
    }

    @PostMapping("/monsters/{monsterId}/sell")
    public MoneyResponse monsterSell(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName,
            @PathVariable long monsterId
    ) throws NotFoundException, NotOwned {
        log.info("POST /shop/monsters/{}/sell - heroName='{}'", monsterId, heroName);
        long money = shopService.sellMonster(monsterId, heroName);
        return new MoneyResponse(money);
    }

    @PostMapping("/incubator")
    public MoneyResponse shopIncubator(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName
    ) throws NotFoundException, TooManyIncubator, NotEnoughtMoney {
        log.info("POST /shop/incubator - heroName='{}'", heroName);
        long money = shopService.buyIncubator(heroName);
        return new MoneyResponse(money);
    }
}
