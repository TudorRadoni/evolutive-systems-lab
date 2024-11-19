package org.labwork.lab2.exercise2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.util.Collections;
import java.util.List;

public class PlotFitnessValues extends ApplicationFrame {

    public PlotFitnessValues(String title, List<Double> fitnessValues) {
        super(title);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                "Fitness Values Over Generations",
                "Generation", "Fitness",
                createDataset(fitnessValues),
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = lineChart.getXYPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setRange(0, fitnessValues.size());

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        double minValue = Collections.min(fitnessValues);
        double maxValue = Collections.max(fitnessValues);
        rangeAxis.setRange(minValue, maxValue);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1600, 800));
        setContentPane(chartPanel);
    }

    private XYSeriesCollection createDataset(List<Double> fitnessValues) {
        XYSeries series = new XYSeries("Fitness");
        for (int i = 0; i < fitnessValues.size(); i++) {
            series.add(i, fitnessValues.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public static void plot(List<Double> fitnessValues) {
        PlotFitnessValues chart = new PlotFitnessValues("Fitness Values Over Generations", fitnessValues);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}