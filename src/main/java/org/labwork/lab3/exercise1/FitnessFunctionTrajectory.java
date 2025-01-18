package org.labwork.lab3.exercise1;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import java.text.DecimalFormat;
import java.util.Random;

public class FitnessFunctionTrajectory extends FitnessFunction {
    public static final double[] yref = {0, 0, 2, 2, 1, 1, 1.5, 2, 2,
            1, 1, 1, 0, 0};
    public static final int optimizationHorizon = 13;
    public static final int NO_OF_GENES = 13;

    public double evaluate(IChromosome chr) {
        double[] u = Mapping(chr);
        double[] y = GetY(u);
        double errorSum = 0;
        for (int i = 0; i < optimizationHorizon; i++)
            errorSum += Math.abs(y[i] - yref[i]);
        return errorSum;
    }

    public static double[] Mapping(IChromosome chr) {
        // add Mapping(chr) method
        double[] u = new double[NO_OF_GENES];
        for (int i = 0; i < chr.size(); i++)
            u[i] = (Double) chr.getGene(i).getAllele();
        return u;
    }

    public static double[] GetY(double[] u) {
        // add GetY(u) method
        double[] y = new double[optimizationHorizon];
        double x1 = 0, x2 = 0;
        Random random = new Random();
        for (int i = 0; i < optimizationHorizon; i++) {
            // calc Y
            y[i] = 0.3 * x1 - 0.7 * x2;

            double perturbation = -0.2 + (0.4 * random.nextDouble());
            // calc X

            x1 = 0.8 * x1 + 0.1 * x2 + 5 * u[i] + perturbation;
            // x1 = 0.8 * x1 + 0.1 * x2 + 5 * u[i];

            x2 = 0.2 * x1 + 0.7 * x2 + 3 * u[i];
        }
        return y;
    }

    public static String toString(IChromosome chr) {
        DecimalFormat df2 = new DecimalFormat("#.##");

        double[] u = Mapping(chr);
        StringBuilder s = new StringBuilder();
        for (double v : u) s.append(" ").append(df2.format(v));
        return s.toString();
    }
}