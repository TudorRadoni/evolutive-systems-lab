package org.labwork.lab2;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class FitnessFunctionGrIV extends FitnessFunction {
    private final double POSITIVE_BIAS = 5;

    public double evaluate(IChromosome chr) {
        double individ = Mapping(chr);
        double fitness = Math.pow(individ, 4) - 2 * Math.pow(individ, 2) -
                individ;
        return fitness + POSITIVE_BIAS;
    }

    public static double Mapping(IChromosome chr) {
        double base10 = 0;
        for (int i = 0; i < chr.size(); i++) {
            boolean allele = ((Boolean) chr.getGene(i).getAllele()).booleanValue();
            if (allele)
                base10 += Math.pow(2, i);
        }
        double individ = base10 / (Math.pow(2, 12) - 1) * 4 - 2;
        return individ;
    }
}
