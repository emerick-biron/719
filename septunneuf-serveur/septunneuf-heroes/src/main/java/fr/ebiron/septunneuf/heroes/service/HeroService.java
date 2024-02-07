package fr.ebiron.septunneuf.heroes.service;

import fr.ebiron.septunneuf.heroes.exception.ConflictException;
import fr.ebiron.septunneuf.heroes.exception.NotFoundException;
import fr.ebiron.septunneuf.heroes.exception.WalletCreationException;
import fr.ebiron.septunneuf.heroes.model.Hero;
import fr.ebiron.septunneuf.heroes.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HeroService {
    private final HeroRepository bd;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${septunneuf.shop.service.url}")
    private String serviceShopUrl;

    @Autowired
    public HeroService(HeroRepository bd) {
        this.bd = bd;
    }

    public Hero createHero(String nom, String color) throws ConflictException, WalletCreationException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", nom);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);

        log.info("Send create wallet request to {}{}", serviceShopUrl, "/shop/wallet/create");
        ResponseEntity<Void> response = restTemplate.postForEntity(serviceShopUrl + "/shop/wallet/create", httpEntity, Void.class);
        log.info("Create wallet response code: {}", response.getStatusCode());

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new WalletCreationException("Cannot create wallet for " + nom);
        }
        if (bd.existsById(nom)) {
            throw new ConflictException("Hero " + nom + " already exist");
        }
        Hero hero = new Hero(nom, color);
        bd.save(hero);
        log.info("Hero created: {}", hero);
        return hero;
    }

    public Hero getHero(String name) throws NotFoundException {
        return bd.findById(name).orElseThrow(() -> new NotFoundException("Hero " + name + " not found"));
    }
}
