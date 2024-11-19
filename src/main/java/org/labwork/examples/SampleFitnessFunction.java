package org.labwork;

//package lab1;

import org.jgap.*;

public class SampleFitnessFunction extends FitnessFunction {
    private final double targetAmount;
    private final int alpha;

    public SampleFitnessFunction(double targetAmount, int alpha) {
        this.targetAmount = targetAmount;
        this.alpha = alpha;
    }

    public double evaluate(IChromosome chr) {
        double changeAmount = amountOfChange(chr); //mapping
        int totalCoins = getTotalNumberOfCoins(chr); //mapping

        double changeDifference = Math.abs(targetAmount - changeAmount);
        double fitness = alpha * changeDifference * changeDifference;
        fitness += totalCoins > 1 ? totalCoins : 0;
        return fitness;
    }

    public static double amountOfChange(IChromosome chr) {
        int numQuarters = getNrCoinsAtGene(chr, 0);
        int numDimes = getNrCoinsAtGene(chr, 1);
        int numNickels = getNrCoinsAtGene(chr, 2);
        int numPennies = getNrCoinsAtGene(chr, 3);
        return (numQuarters * 0.25) + (numDimes * 0.1) + (numNickels *
                0.05) + (numPennies * 0.01);
    }

    public static int getNrCoinsAtGene(IChromosome chr, int position) {
        Integer numCoins = (Integer) chr.getGene(position).getAllele();
        return numCoins.intValue();
    }

    public static int getTotalNumberOfCoins(IChromosome chr) {
        int totalCoins = 0;
        for (int i = 0; i < chr.size(); i++)
            totalCoins += getNrCoinsAtGene(chr, i);

        return totalCoins;
    }
}