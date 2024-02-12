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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class HeroService {
    private final HeroRepository bd;
    private final RestClient restClient;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${septunneuf.shop.service.url}")
    private String serviceShopUrl;

    @Autowired
    public HeroService(HeroRepository bd, RestClient restClient) {
        this.bd = bd;
        this.restClient = restClient;
    }

    public Hero createHero(String heroName, String color) throws ConflictException, WalletCreationException {
        if (bd.existsById(heroName)) {
            throw new ConflictException("Hero " + heroName + " already exist");
        }

        createHeroWallet(heroName);

        Hero hero = new Hero(heroName, color);
        bd.save(hero);
        log.info("Hero created: {}", hero);
        return hero;
    }

    private void createHeroWallet(String heroName) throws WalletCreationException {
        log.info("Send create wallet request to {}{}", serviceShopUrl, "/shop/wallet/create");
        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri(serviceShopUrl + "/wallet/create")
                    .header("heroName", heroName)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Create wallet response code: {}", response.getStatusCode());
        } catch (RestClientException e) {
            log.error("Error while creating wallet", e);
            throw new WalletCreationException("Cannot create wallet for " + heroName);
        }
    }

    public Hero getHero(String name) throws NotFoundException {
        return bd.findById(name).orElseThrow(() -> new NotFoundException("Hero " + name + " not found"));
    }
}
