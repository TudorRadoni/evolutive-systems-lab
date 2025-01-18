package org.labwork.lab3.exercise2;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FitnessFunctionTrajectory extends FitnessFunction {

    public static final int timeConstant = 20;
    public static final int NO_OF_GENES = 11;
    public static final int optimizationHorizon = timeConstant;

    public static Function<Integer, Double> randomInput = k -> new Random().nextDouble();
    public static final double[] aBase = {0.8, 0.1, 0.2, 0.7};
    public static final double[] bBase = {5, 3};
    public static final double[] cBase = {0.3, -0.7};
    public static final double dBase = 0;

    public static List<Function<Integer, Double>> functionList = new ArrayList<>(List.of(randomInput));
    public static final double[] yref = getTotalY(functionList, aBase, bBase, cBase, dBase);

    public double evaluate(IChromosome chr) {
        double[] params = Mapping(chr);
        double[] a = Arrays.copyOfRange(params, 0, 4);
        double[] b = Arrays.copyOfRange(params, 4, 6);
        double[] c = Arrays.copyOfRange(params, 6, 8);
        double d = params[8];
        double errorSum = 0;
        for (Function<Integer, Double> integerDoubleFunction : functionList) {
            double[] u = getInput(integerDoubleFunction);
            double[] yEst = GetY(u, a, b, c, d);
            for (int j = 0; j < optimizationHorizon; j++) {
                errorSum += Math.pow(Math.abs(yEst[j] - yref[j]), 2);
            }
        }
        return errorSum;
    }

    public static double[] getTotalY(List<Function<Integer, Double>> functionList, double[] a, double[] b, double[] c, double d) {
        double[] y = new double[optimizationHorizon];
        Arrays.fill(y, 0.0);
        for (Function<Integer, Double> integerDoubleFunction : functionList) {
            {
                double[] u = getInput(integerDoubleFunction);
                double[] yFunc = GetY(u, a, b, c, d);
                for (int j = 0; j < optimizationHorizon; j++) {
                    y[j] += yFunc[j];
                }
            }
        }
        return y;
    }

    public static double[] Mapping(IChromosome chr) {
        //add Mapping(chr) method
        double[] params = new double[NO_OF_GENES];
        for (int i = 0; i < chr.size(); i++)
            params[i] = (Double) chr.getGene(i).getAllele();
        return params;
    }

    public static double[] GetY(double[] u, double[] a, double[] b, double[] c, double d) {
        //add GetY(u) method
        double[] y = new double[optimizationHorizon];
        double x1 = 2, x2 = -2;
        for (int i = 0; i < optimizationHorizon; i++) {
            // calc Y
            y[i] = c[0] * x1 + c[1] * x2 + d * u[i];

            // calc X
            x1 = a[0] * x1 + 0.1 * a[1] * x1 + b[0] * u[i];
            x2 = a[2] * x1 + 0.1 * a[3] * x2 + b[1] * u[i];
        }
        return y;
    }

    public static double[] getInput(Function<Integer, Double> inputFunction) {
        double[] u = new double[optimizationHorizon];
        for (int i = 0; i < optimizationHorizon; i++) {
            u[i] = inputFunction.apply(i); // Use the provided input function
        }
        return u;
    }

    public static String toString(IChromosome chr) {
        DecimalFormat df2 = new DecimalFormat("#.##");

        double[] u = Mapping(chr);
        StringBuilder s = new StringBuilder();
        for (double v : u) s.append(" ").append(df2.format(v));
        return s.toString();
    }
}