package org.labwork.examples;

import org.labwork.SampleFitnessFunction;
import org.jgap.*;
import org.jgap.impl.*;

public class ConstraintExample {
    private static final int NUM_EVOLUTIONS = 2000;

    public static void main(String[] args) throws
            InvalidConfigurationException {
        double targetAmount = 1.84;
        int alpha = 500;

        // Configuration.reset();

        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);

        // We use Delta evaluator (low value for fitness = better):
        conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(true);

        FitnessFunction fitnessFunction = new
                SampleFitnessFunction(targetAmount, alpha);
        conf.setFitnessFunction(fitnessFunction);

        Gene[] sampleGenes = new Gene[4];
        sampleGenes[0] = new IntegerGene(conf, 0, 20); // Quarters
        sampleGenes[1] = new IntegerGene(conf, 0, 30); // Dimes
        sampleGenes[2] = new IntegerGene(conf, 0, 50); // Nickels
        sampleGenes[3] = new IntegerGene(conf, 0, 80); // Pennies

        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(500);

        Genotype population = Genotype.randomInitialGenotype(conf);
        for (int i = 0; i < NUM_EVOLUTIONS; i++) {
            population.evolve();
            IChromosome bestSolutionSoFar =
                    population.getFittestChromosome();
            DisplayIndividual(bestSolutionSoFar);
        }
    }

    public static void DisplayIndividual(IChromosome chr) {
        System.out.print("Fitness value: " + chr.getFitnessValue());
        System.out.print(", Coins: ");
        for (int i = 0; i < 4; i++)
            System.out.print(SampleFitnessFunction.getNrCoinsAtGene(chr, i)
                    + " ");
        System.out.println(", total change: " +
                SampleFitnessFunction.amountOfChange(chr));
    }
}