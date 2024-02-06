package fr.ebiron.septunneuf.shop.controller;

import fr.ebiron.septunneuf.shop.dto.MoneyResponse;
import fr.ebiron.septunneuf.shop.dto.getEggsResponse;
import fr.ebiron.septunneuf.shop.exception.*;
import fr.ebiron.septunneuf.shop.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void shopUpdate() throws NotFoundException {
        shopService.updateEggs();
    }

    @GetMapping("/eggs")
    @ResponseBody
    public getEggsResponse GetEggs() {
        return new getEggsResponse(shopService.getEggs());
    }

    @PostMapping("/wallet/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHeroWallet(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName
    ) throws ConflictException {
        shopService.createHeroWallet(heroName);
    }

    @PostMapping("/eggs/{eggId}/purchase")
    @ResponseBody
    public MoneyResponse eggPurchase(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName,
            @PathVariable long eggId
    ) throws NotFoundException, NotEnoughtMoney {
        long money = shopService.purchaseEgg(eggId, heroName);
        return new MoneyResponse(money);
    }

    @PostMapping("/eggs/{eggId}/sell")
    @ResponseBody
    public MoneyResponse eggSell(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName,
            @PathVariable long eggId
    ) throws NotFoundException, NotOwned {
        long money = shopService.sellEgg(eggId, heroName);
        return new MoneyResponse(money);
    }

    @PostMapping("/monster/{monsterId}/sell")
    @ResponseBody
    public MoneyResponse monsterSell(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName,
            @PathVariable long monsterId
    ) throws NotFoundException, NotOwned {
        long money = shopService.sellMonster(monsterId, heroName);
        return new MoneyResponse(money);
    }

    @PostMapping("/incubator")
    @ResponseBody
    public MoneyResponse shopIncubator(
            @RequestHeader(required = true, name = "heroName") @Valid String heroName
    ) throws NotFoundException, TooManyIncubator, NotEnoughtMoney {
        long money = shopService.buyIncubator(heroName);
        return new MoneyResponse(money);
    }
}
