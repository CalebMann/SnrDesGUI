package sample;

import javafx.scene.chart.XYChart;

import java.awt.*;

public class GraphThread implements Runnable
{
    public static XYChart.Series<Number,Number> series;

    public GraphThread(XYChart.Series<Number,Number> series){
        this.series = series;
    }


    @Override
    public void run()
    {
        int currentPointer = GUI.SharedData.dataPointer;
        GUI.SharedData.incDP();

        series.getData().clear();
        System.out.println("Graphing1: "+GUI.SharedData.data[currentPointer]);

        for(int i=0; i<299; i++){
            System.out.println("Graphing2: "+GUI.SharedData.data[currentPointer]);

            if(GUI.SharedData.data[currentPointer] != null){
                System.out.println("Graphing3: "+GUI.SharedData.data[currentPointer]);
                series.getData().add(new XYChart.Data(-1*i,GUI.SharedData.data[currentPointer]));
                if(currentPointer==0){
                    currentPointer=299;
                }else{
                    currentPointer--;
                }
            }
        }





    }
}
