package fr.ebiron.septunneuf.monsters.utils;

import java.util.Random;

public class RandomGenerators {
    private static final Random RDM = new Random();

    public static String randomHexColor() {
        // Générer un code hexadécimal aléatoire de 6 caractères (ex: "1a2b3c")
        StringBuilder hexColor = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            int randomInt = RDM.nextInt(16); // Chiffres hexadécimaux de 0 à 15
            hexColor.append(Integer.toHexString(randomInt));
        }
        return hexColor.toString();
    }

    public static String randomMonsterName() {
        // Générer un nom de monstre aléatoire entre 3 et 8 lettres (1ère lettre en majuscule)
        int nameLength = RDM.nextInt(6) + 3; // Entre 3 et 8 lettres
        StringBuilder monsterName = new StringBuilder();

        // Définition des listes de voyelles et de consonnes
        String vowels = "aeiou";
        String consonants = "bcdfghjklmnpqrstvwxyz";

        // Variable pour alterner entre voyelles et consonnes
        boolean isVowel = false;

        for (int i = 0; i < nameLength; i++) {
            if (isVowel) {
                // Ajouter une voyelle aléatoire en minuscules
                monsterName.append(vowels.charAt(RDM.nextInt(vowels.length())));
            } else {
                // Ajouter une consonne aléatoire en minuscules
                monsterName.append(consonants.charAt(RDM.nextInt(consonants.length())));
            }
            isVowel = !isVowel; // Alterner entre voyelles et consonnes
        }

        // Mettre en majuscule la première lettre du nom
        monsterName.setCharAt(0, Character.toUpperCase(monsterName.charAt(0)));

        return monsterName.toString();
    }

}