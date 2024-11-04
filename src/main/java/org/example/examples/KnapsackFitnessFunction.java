package org.example.examples;

import org.jgap.*;
import org.jgap.impl.*;

public class KnapsackFitnessFunction extends FitnessFunction {
    private static final int MAX_VOLUME = 30; // V = 30
    private static final int NUM_OBJECTS = 30;
    private static final double ALPHA = 0.01; // Factor de penalizare

    @Override
    public double evaluate(IChromosome chromosome) {
        double totalVolume = 0;
        double totalValue = 0;

        // Calculăm volumul și valoarea totală a obiectelor selectate
        for (int i = 0; i < NUM_OBJECTS; i++) {
            boolean isIncluded = (Boolean) chromosome.getGene(i).getAllele();
            if (isIncluded) {
                int volume = i + 1;
                int value = (i + 1) * (i + 1);
                totalVolume += volume;
                totalValue += value;
            }
        }

        // Penalizare dacă volumul total depășește capacitatea rucsacului
        if (MAX_VOLUME <= totalVolume) {
            double penalty = ALPHA * Math.pow(totalVolume - MAX_VOLUME, 2);
            totalValue += penalty;
        }

        return totalValue;
    }
}
