package fr.ebiron.septunneuf.shop.service;

import fr.ebiron.septunneuf.shop.dto.GenerateEggsRequest;
import fr.ebiron.septunneuf.shop.dto.ListEggsResponse;
import fr.ebiron.septunneuf.shop.dto.ListIncubatorsResponse;
import fr.ebiron.septunneuf.shop.dto.ListMonstersResponse;
import fr.ebiron.septunneuf.shop.exception.*;
import fr.ebiron.septunneuf.shop.model.Egg;
import fr.ebiron.septunneuf.shop.model.Hero;
import fr.ebiron.septunneuf.shop.publishers.ShopPublisher;
import fr.ebiron.septunneuf.shop.repository.EggRepository;
import fr.ebiron.septunneuf.shop.repository.HeroRepository;
import fr.ebiron.septunneuf.shop.utils.RandomGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ShopService {
    private final HeroRepository heroBD;
    private final EggRepository eggBD;
    private final ShopPublisher shopPublisher;
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${septunneuf.shop.eggs.url}")
    private String serviceEggsUrl;
    @Value("${septunneuf.shop.inventory.url}")
    private String serviceInventoryUrl;
    @Value("${septunneuf.shop.incubators.url}")
    private String serviceIncubatorsUrl;

    @Autowired
    public ShopService(HeroRepository heroBD, EggRepository eggBD, ShopPublisher shopPublisher) {
        this.heroBD = heroBD;
        this.eggBD = eggBD;
        this.shopPublisher = shopPublisher;
    }

    public List<Egg> getEggs() {
        return eggBD.findAll();
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void updateEggs() throws NotFoundException {
        log.info("Updating Eggs");
        RestTemplate restTemplate = new RestTemplate();
        log.info("Send generateEggs request to {}{}", serviceEggsUrl, "/generate");
        ListEggsResponse response = restTemplate.postForObject(serviceEggsUrl + "/generate", new GenerateEggsRequest(RandomGenerators.randomQuantity()), ListEggsResponse.class);
        if (response == null || response.getEggIds() == null || response.getEggIds().isEmpty()) {
            log.error("Bizarre, les oeufs ne sont pas arrivés");
            throw new NotFoundException("Pas d'oeufs généré");
        }
        log.info("Eggs generated: {}", response.getEggIds());
        List<Long> eggIdsToRemove = this.getEggs().stream().map(Egg::getId).toList();
        if (!eggIdsToRemove.isEmpty()) {
            shopPublisher.sendRemoveEggsMessage(eggIdsToRemove);
        }
        eggBD.deleteAll();
        log.info("All eggs deleted from shop");
        List<Egg> toSave = response.getEggIds().stream()
                .map(eggId -> new Egg(eggId, RandomGenerators.randomEggPrice()))
                .toList();
        eggBD.saveAll(toSave);
        log.info("Eggs added to shop: {}", toSave);
    }

    public void createHeroWallet(String heroName) throws ConflictException {
        if (heroBD.existsById(heroName)) {
            throw new ConflictException("Wallet for " + heroName + " already exist");
        }
        Hero hero = new Hero(heroName, 25);
        heroBD.save(hero);
        log.info("Wallet created for {}", heroName);
    }

    public long purchaseEgg(long eggId, String heroName) throws NotFoundException, NotEnoughtMoney {
        Egg egg = eggBD.findById(eggId).orElseThrow(() -> new NotFoundException("Egg #" + eggId + " doesn't exist"));
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " doesn't exist"));
        if (hero.getMoney() < egg.getPrice()) {
            throw new NotEnoughtMoney("Hero " + heroName + " don't have money to buy egg #" + eggId);
        }
        hero.setMoney(hero.getMoney() - egg.getPrice());
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        eggBD.deleteById(eggId);
        log.info("Egg with id {} deleted from shop", eggId);
        shopPublisher.sendAddEggToInventoryMessage(eggId, heroName);

        return hero.getMoney();
    }

    public long sellEgg(long eggId, String heroName) throws NotFoundException, NotOwned {
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " dosn't exist"));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", heroName);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        log.info("Send get eggs request to {}{}", serviceInventoryUrl, "/eggs");
        ResponseEntity<ListEggsResponse> response = restTemplate.exchange(serviceInventoryUrl + "/eggs", HttpMethod.GET, httpEntity, ListEggsResponse.class);

        if (response.getBody() == null || response.getBody().getEggIds() == null) {
            log.error("Error during request for eggs for {}", heroName);
            throw new NotFoundException("Error during request for eggs for " + heroName);
        }
        log.info("Hero eggs in inventory: {}", response.getBody().getEggIds());

        if (!response.getBody().getEggIds().contains(eggId)) {
            log.error("Hero {} don't have egg {}", heroName, eggId);
            throw new NotOwned("Hero " + heroName + " don't have egg #" + eggId);
        }

        hero.setMoney(hero.getMoney() + RandomGenerators.randomEggPrice());
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        shopPublisher.sendRemoveEggToInventoryMessage(eggId, heroName);

        return hero.getMoney();
    }


    public long sellMonster(long monsterId, String heroName) throws NotFoundException, NotOwned {
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " dosn't exist"));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", heroName);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        log.info("Send get monsters request to {}{}", serviceInventoryUrl, "/monsters");
        ResponseEntity<ListMonstersResponse> response = restTemplate.exchange(serviceInventoryUrl + "/monsters", HttpMethod.GET, httpEntity, ListMonstersResponse.class);

        if (response.getBody() == null || response.getBody().getMonsterIds() == null) {
            log.error("Error during request for monster for {}", heroName);
            throw new NotFoundException("Error during request for monster for " + heroName);
        }
        log.info("Hero monsters in inventory: {}", response.getBody().getMonsterIds());

        if (!response.getBody().getMonsterIds().contains(monsterId)) {
            log.error("Hero {} don't have monster {}", heroName, monsterId);
            throw new NotOwned("Hero " + heroName + " don't have monster #" + monsterId);
        }

        hero.setMoney(hero.getMoney() + RandomGenerators.randomMonsterPrice());
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        shopPublisher.sendRemoveMonsterToInventoryMessage(monsterId, heroName);

        return hero.getMoney();
    }

    public long buyIncubator(String heroName) throws NotFoundException, TooManyIncubator, NotEnoughtMoney {
        int incubatorPrice = 10;
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " dosn't exist"));
        if (hero.getMoney() < incubatorPrice) {
            log.error("Hero {} don't have money to buy an incubator", heroName);
            throw new NotEnoughtMoney("Hero " + heroName + " don't have money to buy an incubator");
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", heroName);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        log.info("Send get incubators request to {}", serviceIncubatorsUrl);
        ResponseEntity<ListIncubatorsResponse> response = restTemplate.exchange(serviceIncubatorsUrl, HttpMethod.GET, httpEntity, ListIncubatorsResponse.class);

        if (response.getBody() == null || response.getBody().getIncubatorIds() == null) {
            log.error("Error during request for incubators for {}", heroName);
            throw new NotFoundException("Error during request for incubators for " + heroName);
        }
        log.info("Hero incubators {}", response.getBody().getIncubatorIds());

        if (response.getBody().getIncubatorIds().size() >= 6) {
            log.error("Hero {} have already 6 incubators", heroName);
            throw new TooManyIncubator("Hero " + heroName + " have already 6 incubators");
        }

        hero.setMoney(hero.getMoney() - incubatorPrice);
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        shopPublisher.sendCreateIncubatorMessage(heroName);

        return hero.getMoney();
    }
}
