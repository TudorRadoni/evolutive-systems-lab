package org.example.lab2.exercise3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PlotPolynomial extends ApplicationFrame {

    public PlotPolynomial(String title, double[] coefficients) {
        super(title);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Polynomial Approximation of Sine Function",
                "X", "Y",
                createDataset(coefficients),
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = lineChart.getXYPlot();
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYSeriesCollection createDataset(double[] coefficients) {
        XYSeries sineSeries = new XYSeries("Sine Function");
        XYSeries polySeries = new XYSeries("Polynomial Approximation");

        int numPoints = 1000;
        double step = Math.PI / numPoints;
        for (int i = 0; i <= numPoints; i++) {
            double x = i * step;
            sineSeries.add(x, Math.sin(x));
            polySeries.add(x, evaluatePolynomial(coefficients, x));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(sineSeries);
        dataset.addSeries(polySeries);
        return dataset;
    }

    private double evaluatePolynomial(double[] coefficients, double x) {
        return coefficients[0] * Math.pow(x, 5) +
                coefficients[1] * Math.pow(x, 4) +
                coefficients[2] * Math.pow(x, 3) +
                coefficients[3] * Math.pow(x, 2) +
                coefficients[4] * x +
                coefficients[5];
    }

    public static void plot(double[] coefficients) {
        PlotPolynomial chart = new PlotPolynomial("Polynomial Approximation of Sine Function", coefficients);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}