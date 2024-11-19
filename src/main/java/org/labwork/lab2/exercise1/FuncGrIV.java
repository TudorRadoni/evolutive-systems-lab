package org.labwork.lab2.exercise1;

import org.jgap.*;
import org.jgap.Chromosome;
import org.jgap.DeltaFitnessEvaluator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;
import org.jgap.impl.DefaultConfiguration;
import org.labwork.lab2.exercise2.PlotFitnessValues;

import java.util.ArrayList;
import java.util.List;

public class FuncGrIV {
    private static final int MAX_ALLOWED_EVOLUTIONS = 500;

    public static void main(String[] args) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        // low value for fitness = better
        conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(true);
        conf.setFitnessFunction(new FitnessFunctionGrIV());

        int nrGenes = 12;
        IChromosome sampleChromosome = new Chromosome(conf,
                new BooleanGene(conf), nrGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(5);
        Genotype population = Genotype.randomInitialGenotype(conf);

        List<Double> fitnessValues = new ArrayList<>();
        long totalTime = 0;

        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
            long startTime = System.nanoTime();
            population.evolve();
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);

            IChromosome bestChrSoFar = population.getFittestChromosome();
            fitnessValues.add(bestChrSoFar.getFitnessValue());
            System.out.println(
                    "Generation: " + i + ", Fitness: " + bestChrSoFar.getFitnessValue() + ", " +
                            "individual: " + FitnessFunctionGrIV.Mapping((bestChrSoFar)));
        }

        double averageTimePerGeneration = totalTime / (double) MAX_ALLOWED_EVOLUTIONS;
        System.out.println("Average time per generation: " + averageTimePerGeneration + " ns");

        // Plot the fitness values
        PlotFitnessValues.plot(fitnessValues);
    }
}