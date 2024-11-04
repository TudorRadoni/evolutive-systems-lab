package org.example.lab2.exercise3;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

public class PolynomialFitnessFunction extends FitnessFunction {
    @Override
    protected double evaluate(IChromosome chromosome) {
        double[] coefficients = Mapping(chromosome);
        return calculateFitness(coefficients);
    }

    public static double[] Mapping(IChromosome chromosome) {
        double[] coefficients = new double[6];
        for (int i = 0; i < 6; i++) {
            int value = 0;
            for (int j = 0; j < 11; j++) {
                BooleanGene gene = (BooleanGene) chromosome.getGene(i * 11 + j);
                if (gene.booleanValue()) {
                    value += Math.pow(2, 10 - j);
                }
            }
            coefficients[i] = value / (Math.pow(2, 11) - 1) * 20 - 10; // Scale to [-10, 10]
        }
        return coefficients;
    }

    private double calculateFitness(double[] coefficients) {
        double fitness = 0;
        int numPoints = 1000;
        double step = Math.PI / numPoints;
        for (int i = 0; i <= numPoints; i++) {
            double x = i * step;
            double polyValue = evaluatePolynomial(coefficients, x);
            double sineValue = Math.sin(x);
            fitness += Math.pow(polyValue - sineValue, 2);
        }
        return fitness;
    }

    private double evaluatePolynomial(double[] coefficients, double x) {
        return coefficients[0] * Math.pow(x, 5) +
                coefficients[1] * Math.pow(x, 4) +
                coefficients[2] * Math.pow(x, 3) +
                coefficients[3] * Math.pow(x, 2) +
                coefficients[4] * x +
                coefficients[5];
    }
}