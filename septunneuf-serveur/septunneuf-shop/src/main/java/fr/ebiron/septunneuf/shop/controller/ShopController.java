package fr.ebiron.septunneuf.shop.controller;

import fr.ebiron.septunneuf.shop.exception.ConflictException;
import fr.ebiron.septunneuf.shop.service.ShopService;
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
    public void ShopUpdate(){

        return;
    }

    @PostMapping("/heroes/{heroName}/wallet/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateHeroWallet(@PathVariable String heroName) throws ConflictException {
        shopService.CreateHeroWallet(heroName);
        return;
    }
}
