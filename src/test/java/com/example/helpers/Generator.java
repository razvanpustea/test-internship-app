package com.example.helpers;

public class Generator {

    public static String generateRandomName() {
        String[] names =
                {
                        "Liam", "Noah", "Oliver", "Elijah", "James",
                        "Olivia", "Emma", "Charlotte", "Amelia", "Ava"
                };

        int index = generateRandomDigit(0, 9);
        String generatedName = names[index];

        for (int i = 1; i <= 3; i++)
            generatedName += generateRandomDigit(0, 9);

        return generatedName;
    }

    public static String generateRandomEmail() {
        return generateRandomName() + "@yahoo.com";
    }

    // generates a random number between [min, max]
    public static int generateRandomDigit(int min, int max) {
        ++max;
        if (min == 0)
            return (int) (Math.random() * max);
        else
            return (int) (Math.random() * (max - min) + min);
    }
}
