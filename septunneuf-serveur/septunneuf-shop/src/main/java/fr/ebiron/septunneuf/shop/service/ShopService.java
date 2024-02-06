package fr.ebiron.septunneuf.shop.service;

import fr.ebiron.septunneuf.shop.dto.*;
import fr.ebiron.septunneuf.shop.exception.*;
import fr.ebiron.septunneuf.shop.model.Egg;
import fr.ebiron.septunneuf.shop.model.Hero;
import fr.ebiron.septunneuf.shop.repository.EggRepository;
import fr.ebiron.septunneuf.shop.repository.HeroRepository;
import fr.ebiron.septunneuf.shop.utils.RandomGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ShopService {
    private final HeroRepository heroBD;
    private final EggRepository eggBD;
    private final RabbitMQProducer rabbitMQProducer;

    @Value("${septunneuf.shop.eggs.url}")
    private String serviceEggsUrl;

    @Value("${septunneuf.shop.inventory.url}")
    private String serviceInventoryUrl;

    @Value("${septunneuf.shop.incubators.url}")
    private String serviceIncubatorsUrl;

    @Autowired
    public ShopService(HeroRepository heroBD, EggRepository eggBD, RabbitMQProducer rabbitMQProducer) {
        this.heroBD = heroBD;
        this.eggBD = eggBD;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public List<Egg> getEggs(){
        return eggBD.findAll();
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void updateEggs() throws NotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        ListEggsResponse response = restTemplate.postForObject(serviceEggsUrl + "/generate", new GenerateEggsRequest(RandomGenerators.randomQuantity()), ListEggsResponse.class);

        if (response.getEggIds() == null || response.getEggIds().isEmpty()) {
            System.out.println("Bizarre, les oeufs ne sont pas arrivés");
            throw new NotFoundException("Pas d'oeufs généré");
        }
        rabbitMQProducer.sendRemoveEggsMessage(new RemoveEggsMessage(this.getEggs().stream().map(Egg::getId).toList()));
        eggBD.deleteAll();
        for (long eggId : response.getEggIds()) {
            Egg egg = new Egg(eggId, RandomGenerators.randomEggPrice());
            eggBD.save(egg);
        }
    }

    public void createHeroWallet(String heroName) throws ConflictException {
        if (heroBD.existsById(heroName)){
            throw new ConflictException("Wallet for "+heroName+" already exist");
        }
        Hero hero = new Hero(heroName, 25);
        heroBD.save(hero);
        return;
    }

    public long purchaseEgg(long eggId, String heroName) throws NotFoundException, NotEnoughtMoney{
        Egg egg = eggBD.findById(eggId).orElseThrow(()->new NotFoundException("Egg #"+eggId+" dosn't exist"));
        Hero hero = heroBD.findById(heroName).orElseThrow(()->new NotFoundException("Hero "+heroName+" dosn't exist"));
        if (hero.getMoney() < egg.getPrice()){
            throw new NotEnoughtMoney("Hero "+heroName+" don't have money to buy egg #"+eggId);
        }
        hero.setMoney(hero.getMoney()-egg.getPrice());
        heroBD.save(hero);
        eggBD.deleteById(eggId);
        rabbitMQProducer.sendAddEggToInventoryMessage(new EggToInventoryMessage(eggId, heroName));

        return hero.getMoney();
    }

    public long sellEgg(long eggId, String heroName) throws NotFoundException, NotOwned {
        Hero hero = heroBD.findById(heroName).orElseThrow(()->new NotFoundException("Hero "+heroName+" dosn't exist"));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", heroName);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        ListEggsResponse response = restTemplate.getForObject(serviceInventoryUrl +"/eggs", ListEggsResponse.class, httpEntity);

        if (response.getEggIds() == null || response.getEggIds().isEmpty()) {
            throw new NotFoundException("Error during request for eggs for "+heroName);
        }

        if (!response.getEggIds().contains(eggId)){
            throw new NotOwned("Hero "+heroName+" don't have egg #"+eggId);
        }

        hero.setMoney(hero.getMoney()+RandomGenerators.randomEggPrice());
        heroBD.save(hero);
        rabbitMQProducer.sendRemoveEggToInventoryMessage(new EggToInventoryMessage(eggId, heroName));

        return hero.getMoney();
    }


    public long sellMonster(long monsterId, String heroName) throws NotFoundException, NotOwned {
        Hero hero = heroBD.findById(heroName).orElseThrow(()->new NotFoundException("Hero "+heroName+" dosn't exist"));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", heroName);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        ListMonstersResponse response = restTemplate.getForObject(serviceInventoryUrl +"/monsters", ListMonstersResponse.class, httpEntity);

        if (response.getMonsterIds() == null || response.getMonsterIds().isEmpty()) {
            throw new NotFoundException("Error during request for monster for "+heroName);
        }

        if (!response.getMonsterIds().contains(monsterId)){
            throw new NotOwned("Hero "+heroName+" don't have monster #"+monsterId);
        }

        hero.setMoney(hero.getMoney()+RandomGenerators.randomMonsterPrice());
        heroBD.save(hero);
        rabbitMQProducer.sendRemoveMonsterToInventoryMessage(new MonsterToInventoryMessage(monsterId, heroName));

        return hero.getMoney();
    }

    public long buyIncubator(String heroName) throws NotFoundException, TooManyIncubator, NotEnoughtMoney {
        int incubatorPrice = 10;
        Hero hero = heroBD.findById(heroName).orElseThrow(()->new NotFoundException("Hero "+heroName+" dosn't exist"));

        if (hero.getMoney() < incubatorPrice){
            throw new NotEnoughtMoney("Hero "+heroName+" don't have money to buy an incubator");
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("heroName", heroName);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null, httpHeaders);
        ListIncubatorsResponse response = restTemplate.getForObject(serviceIncubatorsUrl +"/monsters", ListIncubatorsResponse.class, httpEntity);

        if (response.getIncubatorsIds() == null || response.getIncubatorsIds().isEmpty()) {
            throw new NotFoundException("Error during request for incubators for "+heroName);
        }

        if (!(response.getIncubatorsIds().size() >= 6)){
            throw new TooManyIncubator("Hero "+heroName+" have already 6 incubators");
        }

        hero.setMoney(hero.getMoney()-incubatorPrice);
        heroBD.save(hero);
        rabbitMQProducer.sendCreateIncubatorMessage(new CreateIncubatorMessage(heroName));

        return hero.getMoney();
    }
}
