package org.labwork.lab1;

import org.jgap.*;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.DefaultConfiguration;


public class KnapsackProblem {
    private static final int POPULATION_SIZE = 80;
    private static final int NUM_EVOLUTIONS = 100;

    public static void main(String[] args) throws InvalidConfigurationException {

        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(true);

        FitnessFunction fitnessFunction = new KnapsackFitnessFunction();
        conf.setFitnessFunction(fitnessFunction);

        Gene[] sampleGenes = new Gene[30];
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new BooleanGene(conf); // Boolean pentru a indica includerea fiecărui obiect
        }

        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(POPULATION_SIZE);

        Genotype population = Genotype.randomInitialGenotype(conf);

        for (int i = 0; i < NUM_EVOLUTIONS; i++) {
            population.evolve();
            IChromosome bestSolutionSoFar = population.getFittestChromosome();
            displayIndividual(bestSolutionSoFar);
        }
    }

    public static void displayIndividual(IChromosome chromosome) {
        System.out.print("Fitness value: " + chromosome.getFitnessValue() + ", Items included: ");
        for (int i = 0; i < chromosome.size(); i++) {
            if ((Boolean) chromosome.getGene(i).getAllele()) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
    }
}
