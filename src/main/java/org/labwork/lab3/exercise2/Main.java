package org.labwork.lab3.exercise2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {

    public static final int MAX_ALLOWED_EVOLUTIONS = 200;
    public static final int POPULATION_SIZE = 1000;
    public static final int NO_OF_GENES = 11;
    public static double lastBestFitness = Double.MAX_VALUE;
    public static int stagnantGenerations = 0;

    public static void main(String[] args) throws
            InvalidConfigurationException, InterruptedException {
        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        //low value for fitness = better
        conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(true);

        conf.setFitnessFunction(new FitnessFunctionTrajectory());
        IChromosome sampleChromosome = new Chromosome(conf, new DoubleGene(conf, -10, 10), NO_OF_GENES);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(POPULATION_SIZE);
        Genotype population = Genotype.randomInitialGenotype(conf);

        XYSeries yRefSeries = new XYSeries("yRef");
        for (int i = 0; i < FitnessFunctionTrajectory.yref.length; i++) {
            yRefSeries.add(i, FitnessFunctionTrajectory.yref[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(yRefSeries); //sine function
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Trajectory Approximation",
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesShapesVisible(0, false); // Line only
        plot.setRenderer(0, renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);


        for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
            population.evolve();
            IChromosome bestChrSoFar = population.getFittestChromosome();
            double currentFitness = bestChrSoFar.getFitnessValue();
            XYSeries updatedBestApproximationSeries = new XYSeries("yEst");
            double[] params = FitnessFunctionTrajectory.Mapping(bestChrSoFar);
            double[] a = Arrays.copyOfRange(params, 0, 4);
            double[] b = Arrays.copyOfRange(params, 4, 6);
            double[] c = Arrays.copyOfRange(params, 6, 8);
            double d = params[8];
            double[] yEst = FitnessFunctionTrajectory.getTotalY(FitnessFunctionTrajectory.functionList, a, b, c, d);
            for (int j = 0; j < yEst.length; j++) {
                updatedBestApproximationSeries.add(j, yEst[j]);
            }

            if (dataset.getSeriesCount() > 1) {
                dataset.removeSeries(1);
            }
            dataset.addSeries(updatedBestApproximationSeries);
            SwingUtilities.invokeLater(chartPanel::repaint);
            Thread.sleep(200);
            System.out.println(
                    "Fitness: " + currentFitness + ", " +
                            "Params: " + FitnessFunctionTrajectory.toString(bestChrSoFar));

            if (Math.abs(lastBestFitness - currentFitness) / lastBestFitness < 0.05) {
                stagnantGenerations++;
            } else {
                stagnantGenerations = 0;
            }
            lastBestFitness = currentFitness;

            if (stagnantGenerations >= 10) {
                System.out.println("Stopping after " + i + " generations due to stagnation.");
                break;
            }
        }
    }
}
