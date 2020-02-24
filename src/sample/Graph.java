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

    }

}
