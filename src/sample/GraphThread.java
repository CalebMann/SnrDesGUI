package sample;

import javafx.application.Platform;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.sql.SQLOutput;

public class GraphThread implements Runnable
{
    //public static XYChart.Series<Number,Number> series;
    int currentPointer = 0;
    @Override
    public void run()
    {
        try{
            GUI.SharedData.packetsReceived = 0;
            GUI.SharedData.sumData = 0;
            currentPointer = GUI.SharedData.dataPointer;
            GUI.SharedData.incDP();
            if(GUI.series != null){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GUI.series.getData().clear();
                    }
                });
            }
            if(!(currentPointer < 3 || currentPointer > 5)){
            }
            else if(!(currentPointer < 10 || currentPointer > 14)){
            }
            else if(!(currentPointer < 20 || currentPointer > 28)){

            }else if(!(currentPointer < 50 || currentPointer > 80)){

            }else if(!(currentPointer < 150 || currentPointer > 200)){

            }else if(!(currentPointer < 290)){

            }else GUI.SharedData.data[currentPointer] = 30000 + (int)(Math.sin(currentPointer/10.0)*10000);
            //System.out.println("Putting temp data " + GUI.SharedData.data[currentPointer] + " in pos " + currentPointer);
            //System.out.println("NEW THREAD*****************************");
            //GUI.SharedData.printdata();
            for(int i=0; i<299; i++){
                //System.out.println("Printing Data["+currentPointer+"]: " + GUI.SharedData.data[currentPointer]);
                //System.out.print(currentPointer+"\t");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(currentPointer <= 0){
                                currentPointer = 299;
                            }else{
                                currentPointer--;
                            }
                            //System.out.println("DataPtr: "+GUI.SharedData.dataPointer+" Curptr: "+ currentPointer + " data:" + GUI.SharedData.data[currentPointer]);
                            if(GUI.SharedData.data[currentPointer] != null){
                                //System.out.println("DataPtr: "+GUI.SharedData.dataPointer+" Curptr: "+ currentPointer + " data:" + GUI.SharedData.data[currentPointer]);
                                //System.out.println("Printing Data["+currentPointer+"]: " + GUI.SharedData.data[currentPointer]);
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
