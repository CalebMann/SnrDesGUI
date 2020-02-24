package sample;

import javax.swing.*;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class Graph extends JPanel
{
    final NumberAxis xAxis;
    final NumberAxis yAxis;
    public Graph()
    {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        xAxis.setLabel("Seconds ago");
        xAxis.setAnimated(false);

        yAxis.setLabel("Temperature (C)");
        yAxis.setAnimated(false);

        final ScatterChart<Number,Number> graph = new ScatterChart(xAxis,yAxis);
        graph.setTitle("Realtime Temperature Data");
        graph.setAnimated(false);

        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setName("Temperature Data");

        graph.getData().add(series);

    }

}
