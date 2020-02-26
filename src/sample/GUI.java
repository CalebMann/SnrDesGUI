package sample;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import  javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GUI extends JFrame
{
    private static DatagramSocket socket;
    private static DatagramSocket hardwareSocket;


    public GUI() {
        //this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        getContentPane().setLayout(new GridLayout());
        setSize(new Dimension(600,800));



        JSplitPane splitTemp = new JSplitPane();
        JSplitPane splitChart = new JSplitPane();
        JSplitPane splitInfo = new JSplitPane();
        ShowTempFahrOrCel panel1 = new ShowTempFahrOrCel();
        JFXPanel panel2 = new JFXPanel();
        UserInputs panel3 = new UserInputs();
        JPanel panel4 = new ShutOffScreen();


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(panel2);
            }
        });


        getContentPane().add(splitTemp);
        splitTemp.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitTemp.setDividerLocation(100);
        splitChart.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitChart.setDividerLocation(400);
        splitInfo.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitInfo.setDividerLocation(100);

        splitTemp.setTopComponent(panel1);
        splitTemp.setBottomComponent(splitChart);

        splitChart.setTopComponent(panel2);
        splitChart.setBottomComponent(splitInfo);

        splitInfo.setTopComponent(panel3);
        splitInfo.setBottomComponent(panel4);

        this.setVisible(true);

        try // create DatagramSocket for sending and receiving packets
        {
            socket = new DatagramSocket();
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }
        try // create DatagramSocket for sending and receiving packets
        {
            hardwareSocket = new DatagramSocket(9999);
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }
        for(int i=0; i<300; i++){
            SharedData.data[i] = 0;
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduleService =Executors.newScheduledThreadPool(1);
        executorService.execute(new DisplayThread(panel1));
        scheduleService.scheduleAtFixedRate(new GraphThread(), 0, 1, TimeUnit.SECONDS);
        executorService.execute(new dataThread(this));
    }


    public static void sendPackets(DatagramPacket receivePacket)
            throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(
                receivePacket.getData(), receivePacket.getLength(),
                receivePacket.getAddress(), receivePacket.getPort());
        socket.send(sendPacket); // send packet to client
        System.out.println("Sent: " + new String(receivePacket.getData()));
    }

    public void waitForDataPackets(){
        while (true){
            try{
                byte[] data = new byte[1000];
                DatagramPacket receivePacket = new DatagramPacket(data,data.length);
                hardwareSocket.receive(receivePacket);
                String messageReceived = new String(receivePacket.getData());
                messageReceived = messageReceived.substring(0,5);
                System.out.println(messageReceived);

                SharedData.packetsReceived++;
                SharedData.sumData += Integer.parseInt(messageReceived);
                SharedData.data[SharedData.dataPointer] = SharedData.sumData/SharedData.packetsReceived;

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


    public static class SharedData{
        static Integer data[] = new Integer[300];
        static int dataPointer = 0;
        static float Tmax = 63;
        static float Tmin = -10;
        static String phoneNumber = "5632310443";
        static Integer sumData = 0;
        static Integer packetsReceived = 0;
    }


    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }




    private static Scene createScene() {
        NumberAxis xAxis = new NumberAxis(-300,0,100);
        NumberAxis yAxis = new NumberAxis(10,50,5);

        yAxis.setSide(Side.RIGHT);

        xAxis.setLabel("Seconds ago");
        xAxis.setAnimated(false);

        yAxis.setLabel("Temperature (C)");
        yAxis.setAnimated(false);

        final ScatterChart<Number,Number> graph = new ScatterChart(xAxis,yAxis);
        graph.setTitle("Realtime Temperature Data");
        graph.setAnimated(false);
        graph.setScaleShape(true);

        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        series.setName("Temperature Data");

        //series.getData().add(new XYChart.Data(-10,27));

        graph.getData().add(series);

        Scene scene = new Scene(graph);

        return (scene);
    }


}



