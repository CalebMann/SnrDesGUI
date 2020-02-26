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
        try{
            int currentPointer = GUI.SharedData.dataPointer;
            GUI.SharedData.incDP();
            if(series != null){
                series.getData().clear();
            }
            for(int i=0; i<299; i++){
                if(GUI.SharedData.data[currentPointer] != null){
                    System.out.println("Graphing3: "+GUI.SharedData.data[currentPointer]);
                    series.getData().add(new XYChart.Data(-1*i,GUI.SharedData.data[currentPointer]/1000.0));
                    if(currentPointer==0){
                        currentPointer=299;
                    }else{
                        currentPointer--;
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }






    }
}
