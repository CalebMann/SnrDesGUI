package sample;

import javafx.scene.chart.XYChart;

import java.awt.*;

public class GraphThread implements Runnable
{
    //public static XYChart.Series<Number,Number> series;

    @Override
    public void run()
    {
        //this.series = GUI.series;
        //System.out.println(series);
/*        for(int i = 0; i<300; i++)
        {
            try{
                if(GUI.SharedData.data[i] != null)
                {
                    System.out.println(GUI.SharedData.data[i]);
                }
            }catch (Exception ex) {}
        }*/
        try{
            GUI.SharedData.packetsReceived = 0;
            GUI.SharedData.sumData = 0;
            int currentPointer = GUI.SharedData.dataPointer;
            GUI.SharedData.incDP();
            if(GUI.series != null){
                GUI.series.getData().clear();
            }
            for(int i=0; i<299; i++){
                if(GUI.SharedData.data[currentPointer] != null){
                    //System.out.println("Graphing3: "+GUI.SharedData.data[currentPointer]);
                    GUI.series.getData().add(new XYChart.Data(-1*i,GUI.SharedData.data[currentPointer]/1000.0));
                    //System.out.println("Graphing4: "+GUI.SharedData.data[currentPointer]);
                    if(currentPointer==0){
                        currentPointer=299;
                    }else{
                        currentPointer--;
                    }
                    System.out.println("Graphing5: "+GUI.SharedData.data[currentPointer]);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }






    }
}
