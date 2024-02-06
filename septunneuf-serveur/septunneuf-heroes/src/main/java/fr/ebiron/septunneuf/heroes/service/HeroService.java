package fr.ebiron.septunneuf.heroes.service;

import fr.ebiron.septunneuf.heroes.dto.CreateWalletRequest;
import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;
import fr.ebiron.septunneuf.heroes.model.Hero;
import fr.ebiron.septunneuf.heroes.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

@Service
public class HeroService {
    private final HeroRepository bd;

    @Value("${septunneuf.shop.service.url}")
    private String serviceShopUrl;

    @Autowired
    public HeroService(HeroRepository bd) {
        this.bd = bd;
    }

    @ExceptionHandler
    public Hero createHero(String nom, String color) throws ConflictException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", nom);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<Void> response = restTemplate.postForEntity(serviceShopUrl +"/shop/wallet/create", httpEntity, Void.class);

        if (!(response.getStatusCode() == HttpStatusCode.valueOf(201))) {
            throw new ConflictException("Hero wallet "+nom+" already exist");
        }
        if (bd.existsById(nom)){
            throw new ConflictException("Hero "+nom+" already exist");
        }
        Hero hero = new Hero(nom, color);
        bd.save(hero);

        return hero;
    }

    @ExceptionHandler
    public Hero getHero(String name) throws NotFoundException {
        return bd.findById(name).orElseThrow(()->new NotFoundException("Hero "+name+" not found"));
    }
}
