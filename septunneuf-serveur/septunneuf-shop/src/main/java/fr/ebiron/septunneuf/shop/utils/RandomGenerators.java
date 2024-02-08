package fr.ebiron.septunneuf.shop.utils;

import java.util.Random;

public class RandomGenerators {
    private static final Random RANDOM = new Random();

    public static int randomEggPrice() {
        return RANDOM.nextInt(5) + 1;
    }

    public static int randomMonsterPrice() {
        return RANDOM.nextInt(20) + 5;
    }

    public static int randomQuantity() {
        return RANDOM.nextInt(9) + 3;
    }

}