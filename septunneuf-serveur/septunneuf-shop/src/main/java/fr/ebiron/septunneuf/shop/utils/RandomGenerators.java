package fr.ebiron.septunneuf.shop.utils;

import java.util.Random;

public class RandomGenerators {
    public static int randomEggPrice() {
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }

    public static int randomMonsterPrice() {
        Random rand = new Random();
        return rand.nextInt(20) + 5;
    }

    public static int randomQuantity() {
        Random rand = new Random();
        return rand.nextInt(9) + 3;
    }

}