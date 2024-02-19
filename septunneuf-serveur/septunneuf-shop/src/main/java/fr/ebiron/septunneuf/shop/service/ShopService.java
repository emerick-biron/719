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
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ShopService {
    private final HeroRepository heroBD;
    private final EggRepository eggBD;
    private final ShopPublisher shopPublisher;
    private final RestClient restClient;
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${septunneuf.shop.eggs.url}")
    private String serviceEggsUrl;
    @Value("${septunneuf.shop.inventory.url}")
    private String serviceInventoryUrl;
    @Value("${septunneuf.shop.incubators.url}")
    private String serviceIncubatorsUrl;

    @Autowired
    public ShopService(HeroRepository heroBD, EggRepository eggBD, ShopPublisher shopPublisher, RestClient restClient) {
        this.heroBD = heroBD;
        this.eggBD = eggBD;
        this.shopPublisher = shopPublisher;
        this.restClient = restClient;
    }

    public List<Egg> getEggs() {
        return eggBD.findAll();
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    @Retryable(retryFor = EggsGenerationException.class, backoff = @Backoff(10000))
    public void updateEggs() throws EggsGenerationException {
        log.info("Updating Eggs");
        List<Long> generatedEggIds = generateEggs();
        List<Long> eggIdsToRemove = this.getEggs().stream().map(Egg::getId).toList();
        if (!eggIdsToRemove.isEmpty()) {
            shopPublisher.sendRemoveEggsMessage(eggIdsToRemove);
        }
        eggBD.deleteAll();
        log.info("All eggs deleted from shop");
        List<Egg> toSave = generatedEggIds.stream()
                .map(eggId -> new Egg(eggId, RandomGenerators.randomEggPrice()))
                .toList();
        eggBD.saveAll(toSave);
        log.info("Eggs added to shop: {}", toSave);
    }

    private List<Long> generateEggs() throws EggsGenerationException {
        log.info("Send generateEggs request to {}{}", serviceEggsUrl, "/generate");
        try {
            ListEggsResponse response = restClient.post()
                    .uri(serviceEggsUrl + "/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new GenerateEggsRequest(RandomGenerators.randomQuantity()))
                    .retrieve()
                    .body(ListEggsResponse.class);
            if (response == null || response.getEggIds() == null || response.getEggIds().isEmpty()) {
                log.error("Error during egg generation");
                throw new EggsGenerationException("Error during egg generation");
            }
            log.info("Eggs generated: {}", response.getEggIds());
            return response.getEggIds();
        } catch (RestClientException e) {
            log.error("Error during egg generation", e);
            throw new EggsGenerationException("Error during egg generation");
        }
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

    public long sellEgg(long eggId, String heroName) throws NotFoundException, NotOwned, GetHeroEggsException {
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " dosn't exist"));
        List<Long> heroEggIds = getHeroEggIds(heroName);

        if (!heroEggIds.contains(eggId)) {
            log.error("Hero {} don't have egg {}", heroName, eggId);
            throw new NotOwned("Hero " + heroName + " don't have egg #" + eggId);
        }

        hero.setMoney(hero.getMoney() + RandomGenerators.randomEggPrice());
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        shopPublisher.sendRemoveEggToInventoryMessage(eggId, heroName);

        return hero.getMoney();
    }

    private List<Long> getHeroEggIds(String heroName) throws GetHeroEggsException {
        log.info("Send get eggs request to {}{}", serviceInventoryUrl, "/eggs");
        try {
            ListEggsResponse response = restClient.get()
                    .uri(serviceInventoryUrl + "/eggs")
                    .header("heroName", heroName)
                    .retrieve()
                    .body(ListEggsResponse.class);
            if (response == null || response.getEggIds() == null) {
                log.error("Error during request for eggs for {}", heroName);
                throw new GetHeroEggsException("Error while retrieving eggs from the hero " + heroName);
            }
            log.info("Hero eggs in inventory: {}", response.getEggIds());
            return response.getEggIds();
        } catch (RestClientException e) {
            log.error("Error while retrieving eggs from the hero " + heroName, e);
            throw new GetHeroEggsException("Error while retrieving eggs from the hero " + heroName);
        }
    }


    public long sellMonster(long monsterId, String heroName) throws NotFoundException, NotOwned, GetHeroMonstersException {
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " dosn't exist"));
        List<Long> heroMonsterIds = getHeroMonsterIds(heroName);

        if (!heroMonsterIds.contains(monsterId)) {
            log.error("Hero {} don't have monster {}", heroName, monsterId);
            throw new NotOwned("Hero " + heroName + " don't have monster #" + monsterId);
        }

        hero.setMoney(hero.getMoney() + RandomGenerators.randomMonsterPrice());
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        shopPublisher.sendRemoveMonsterToInventoryMessage(monsterId, heroName);

        return hero.getMoney();
    }

    private List<Long> getHeroMonsterIds(String heroName) throws GetHeroMonstersException {
        log.info("Send get monsters request to {}{}", serviceInventoryUrl, "/monsters");
        try {
            ListMonstersResponse response = restClient.get()
                    .uri(serviceInventoryUrl + "/monsters")
                    .header("heroName", heroName)
                    .retrieve()
                    .body(ListMonstersResponse.class);

            if (response == null || response.getMonsterIds() == null) {
                log.error("Error during request for monster for {}", heroName);
                throw new GetHeroMonstersException("Error while retrieving monsters from the hero " + heroName);
            }
            log.info("Hero monsters in inventory: {}", response.getMonsterIds());
            return response.getMonsterIds();
        } catch (RestClientException e) {
            log.error("Error while retrieving monsters from the hero " + heroName, e);
            throw new GetHeroMonstersException("Error while retrieving monsters from the hero " + heroName);
        }
    }

    public long buyIncubator(String heroName) throws NotFoundException, TooManyIncubator, NotEnoughtMoney, GetHeroIncubatorsException {
        int incubatorPrice = 10;
        Hero hero = heroBD.findById(heroName).orElseThrow(() -> new NotFoundException("Hero " + heroName + " dosn't exist"));
        if (hero.getMoney() < incubatorPrice) {
            log.error("Hero {} don't have money to buy an incubator", heroName);
            throw new NotEnoughtMoney("Hero " + heroName + " don't have money to buy an incubator");
        }
        List<Long> incubatorIds = getHeroIncubatorIds(heroName);

        if (incubatorIds.size() >= 6) {
            log.error("Hero {} have already 6 incubators", heroName);
            throw new TooManyIncubator("Hero " + heroName + " have already 6 incubators");
        }

        hero.setMoney(hero.getMoney() - incubatorPrice);
        heroBD.save(hero);
        log.info("Hero money updated: {}", hero);
        shopPublisher.sendCreateIncubatorMessage(heroName);

        return hero.getMoney();
    }

    private List<Long> getHeroIncubatorIds(String heroName) throws GetHeroIncubatorsException {
        log.info("Send get incubators request to {}", serviceIncubatorsUrl);
        try {
            ListIncubatorsResponse response = restClient.get()
                    .uri(serviceIncubatorsUrl)
                    .header("heroName", heroName)
                    .retrieve()
                    .body(ListIncubatorsResponse.class);

            if (response == null || response.getIncubatorIds() == null) {
                log.error("Error while retrieving incubators from the hero " + heroName);
                throw new GetHeroIncubatorsException("Error while retrieving incubators from the hero " + heroName);
            }
            log.info("Hero incubators {}", response.getIncubatorIds());
            return response.getIncubatorIds();
        } catch (RestClientException e) {
            log.error("Error while retrieving incubators from the hero " + heroName, e);
            throw new GetHeroIncubatorsException("Error while retrieving incubators from the hero " + heroName);
        }
    }
}
