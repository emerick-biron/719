package fr.ebiron.septunneuf.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SeptunneufShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeptunneufShopApplication.class, args);
    }

}
