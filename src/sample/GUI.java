package sample;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
Main Class to control the gui and all components, will makes the threads and set up components to handle the events.

 */
public class GUI extends JFrame
{
    //Variables to store the data to be added to the graph on the gui
    public static XYChart.Series<Number,Number> series;
    //A socket for sending datagram packets to the other application
    private static DatagramSocket socket;
    //A socket for receiving data from the hardware components
    private static DatagramSocket hardwareSocket;

    public GUI() {
        //Set the layout of the GUI to use a grid style
        getContentPane().setLayout(new GridLayout());
        //Set the default window size
        setSize(new Dimension(600,800));
        //Set up a series of split planes to split up the GUI into segments for each part
        JSplitPane splitTemp = new JSplitPane();
        JSplitPane splitChart = new JSplitPane();
        JSplitPane splitInfo = new JSplitPane();
        //Create a panel to show the current temperature and give the ability to swap between Fahrenheit and Celsius
        //This window also deals with when to send packets to the application to send a text message
        ShowTempFahrOrCel panel1 = new ShowTempFahrOrCel();
        //This panel will handle the graph in a javaFX subenvironment
        JFXPanel panel2 = new JFXPanel();
        //This panel will handle the user input stuff including the temp max/min setting, the phone number setting, and the text message setting
        UserInputs panel3 = new UserInputs();
        //This panel will handle the button to interact with the hardware and virtually press their button
        JPanel panel4 = new ShutOffScreen();
        //This will run the Javafx scene to create the graph
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(panel2);
            }
        });
        //This part just sets up the formatting of each pane by setting the split top and bottom parts to the correct pane.
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
        //Makes the GUI visible
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
        //Clear the data in the shared data, this will init everything to NULL
        SharedData.cleardata();

        //Set up the threads for handling different windows.
        //There is a display thread for showing the data real time
        //There is a datathread for handling the input data from the hardware in real time
        //There is a graphthread that will be run every 1 second to update the graph
        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduleService =Executors.newScheduledThreadPool(1);
        executorService.execute(new DisplayThread(panel1));
        scheduleService.scheduleAtFixedRate(new GraphThread(), 0, 1, TimeUnit.SECONDS);
        executorService.execute(new dataThread(this));
    }

    //A function to send the input packet through the global output socket.
    public static void sendPackets(DatagramPacket receivePacket)
            throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(
                receivePacket.getData(), receivePacket.getLength(),
                receivePacket.getAddress(), receivePacket.getPort());
        socket.send(sendPacket); // send packet to client
        System.out.println("Sent: " + new String(receivePacket.getData()));
    }
    //A function to handle the incoming data from the hardware and save the average temperature over that past 1 second into the data
    public void waitForDataPackets(){
        while (true){
            try{
                //Deal with the data receiving
                byte[] data = new byte[1000];
                DatagramPacket receivePacket = new DatagramPacket(data,data.length);
                hardwareSocket.receive(receivePacket);
                String messageReceived = new String(receivePacket.getData());
                messageReceived = messageReceived.substring(0,5);
                System.out.println(messageReceived);
                //Increment the number of packets seen in the past 1 second
                if(Integer.parseInt(messageReceived) < -10000){
                    if(SharedData.packetsReceived == 0){
                        SharedData.data[SharedData.dataPointer] = Integer.MIN_VALUE;
                    }
                }else if(Integer.parseInt(messageReceived) > 63000){
                    if(SharedData.packetsReceived == 0){
                        SharedData.data[SharedData.dataPointer] = Integer.MAX_VALUE;
                    }
                }else{
                    SharedData.packetsReceived++;
                    //Add the received data to the current sum
                    SharedData.sumData += Integer.parseInt(messageReceived);
                    //Store the average in the data array
                    SharedData.data[SharedData.dataPointer] = SharedData.sumData/SharedData.packetsReceived;
                }
            }catch (Exception ex){
                //ex.printStackTrace();
            }
        }
    }
    //A static class for shared data needed between different classes among all the files
    public static class SharedData{
        static Integer data[] = new Integer[300];
        static int dataPointer = 0;
        static float Tmax = 63;
        static float Tmin = -10;
        static String phoneNumber = "5632310443";
        static Integer sumData = 0;
        static Integer packetsReceived = 0;
        static String lowText = "low";
        static String highText = "high";

        //Increments the datapointer and sets the value to null
        public static void incDP(){
            if(dataPointer == 299){
                dataPointer = 0;
            }else{
                dataPointer++;
            }
            data[dataPointer] = null;
        }
        //Initializes the data to null
        public static void cleardata(){
            for(int i=0; i<300; i++){
                data[i] = null;
            }
        }
        //Prints the data to stdout, used for debugging mainly
        public static void printdata(){
            for(int i=0; i<300; i++){
                int address = dataPointer - i;
                if(address < 0)address+=300;
                if(data[address] != null){
                    System.out.println("Data["+address+"]: "+data[address]);
                }
            }
        }
    }
    //Sets up the JavaFX panel inside the swing GUI
    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }
    //Sets up the scene for the javaFX panel
    private static Scene createScene() {
        NumberAxis xAxis = new NumberAxis(-300,0,100);
        NumberAxis yAxis = new NumberAxis(10,50,5);
        //Setting for the graph
        yAxis.setSide(Side.RIGHT);
        xAxis.setLabel("Seconds ago");
        xAxis.setAnimated(false);
        yAxis.setLabel("Temperature (C)");
        yAxis.setAnimated(false);
        //Create the scatter chart and settings for it
        final ScatterChart<Number,Number> graph = new ScatterChart(xAxis,yAxis);
        graph.setTitle("Realtime Temperature Data");
        graph.setAnimated(false);
        graph.setScaleShape(true);
        //Assign a series of data and add it to the chart so it will be displayed in real time
        series = new XYChart.Series<>();
        series.setName("Temperature Data");
        graph.getData().add(series);
        Scene scene = new Scene(graph);
        return (scene);
    }
}



