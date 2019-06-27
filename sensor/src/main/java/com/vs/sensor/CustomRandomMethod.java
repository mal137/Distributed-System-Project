package com.vs.sensor;

import java.util.Random;

/**
 * Custom Methods for generating datatypes
 */
class CustomRandomMethod {

    /**
     * This random integer method generates a random number depending the parameter min and max
     * @param min Minimum value
     * @param max Maximum value
     * @return Returns the generated integer
     */
    public static int randomInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a random Boolean which is using a modulo calculation
     * @return generated boolean out of a random operation
     */
    public static boolean randomBoolean() {
        Random rand = new Random();

        int num = rand.nextInt(4);

        return (num % 2) == 0;
    }
}
