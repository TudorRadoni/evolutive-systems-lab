package org.labwork.lab2.exercise3;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.Arrays;

public class PolynomialApproximation {
    // private static final int MAX_ALLOWED_EVOLUTIONS = 1000;
    // private static final int POPULATION_SIZE = 100;

    private static final int MAX_ALLOWED_EVOLUTIONS = 500;
    private static final int POPULATION_SIZE = 400;

    public static void main(String[] args) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(false);
        conf.setFitnessFunction(new PolynomialFitnessFunction());
        int nrGenes = 66;
        IChromosome sampleChromosome = new Chromosome(conf, new BooleanGene(conf), nrGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(POPULATION_SIZE);
        // conf.addGeneticOperator(new MutationOperator(conf, 20));
        conf.addGeneticOperator(new MutationOperator(conf, 50));

        Genotype population = Genotype.randomInitialGenotype(conf);

        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
            population.evolve();
            IChromosome bestChrSoFar = population.getFittestChromosome();
            double[] coefficients = PolynomialFitnessFunction.Mapping(bestChrSoFar);
            System.out.println("Generation: " + i + ", Fitness: " + bestChrSoFar.getFitnessValue() + ", Coefficients: "
                    + Arrays.toString(coefficients));
        }

        IChromosome bestChrSoFar = population.getFittestChromosome();
        double[] coefficients = PolynomialFitnessFunction.Mapping(bestChrSoFar);
        PlotPolynomial.plot(coefficients);
    }
}