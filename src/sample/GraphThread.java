package sample;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.sql.SQLOutput;
//This thread will handle the graph data for the javafx panel
public class GraphThread implements Runnable
{
    //Global variable so the sub thread can see the currentPointer. Current pointer acts a local malleable dataPointer
    int currentPointer = 0;
    @Override
    public void run()
    {
        try{
            //Reset the data for the past second and increment the data pointer. This effectively locks in place the current data and moves onto the next location
            GUI.SharedData.packetsReceived = 0;
            GUI.SharedData.sumData = 0;
            currentPointer = GUI.SharedData.dataPointer;
            GUI.SharedData.incDP();
            //If the series is defined, then clear the series
            if(GUI.series != null){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GUI.series.getData().clear();
                    }
                });
            }

/*            if(!(currentPointer < 3 || currentPointer > 8)){}
            else if(!(currentPointer < 50 || currentPointer > 65)){}
            else {
                int d = 30000 + (int)(Math.sin(currentPointer/15.0)*40000);
                if(d < -10000){
                    d = Integer.MIN_VALUE;
                }else if (d > 63000){
                    d = Integer.MAX_VALUE;
                }
                GUI.SharedData.data[currentPointer] = d;
            }*/
            //System.out.println("**********NEW THREAD**********");
            //A for loop will run for each piece of data
            for(int i=0; i<299; i++){
                //Create a new thread on the javaFX panel for each data point
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Decrement the currentpointer and If the currentpoints is put out of bounds, wrap it around
                            if(currentPointer <= 0){
                                currentPointer = 299;
                            }else{
                                currentPointer--;
                            }
/*                            if(GUI.SharedData.data[currentPointer] != null){
                                System.out.println("Data["+currentPointer+"]: "+GUI.SharedData.data[currentPointer]);
                            }*/
                            //If data exists then add it to the series
                            if(GUI.SharedData.data[currentPointer] != null && (GUI.SharedData.data[currentPointer]>= -10000 && GUI.SharedData.data[currentPointer] <= 63000)){
                                if(GUI.SharedData.dataPointer - currentPointer < 0){
                                    GUI.series.getData().add(new XYChart.Data<>(-1*(GUI.SharedData.dataPointer - currentPointer+300),GUI.SharedData.data[currentPointer]/1000.0));
                                }else{
                                    GUI.series.getData().add(new XYChart.Data<>(-1*(GUI.SharedData.dataPointer - currentPointer),GUI.SharedData.data[currentPointer]/1000.0));
                                }
                            }
                        }catch (Exception ex){
                            //System.out.println("error: "+ex);
                        }
                    }
                });
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
