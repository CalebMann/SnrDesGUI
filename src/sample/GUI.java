package sample;

import javafx.scene.control.SplitPane;

import  javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        JPanel panel2 = new JPanel();
        UserInputs panel3 = new UserInputs();
        JPanel panel4 = new ShutOffScreen();


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


        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new TriggerThread());
        executorService.execute(new DisplayThread());
        executorService.execute(new GraphThread());
        executorService.execute(new dataThread(this));








        try
        {
            String test = "-3197438628";
            byte[] bytes = test.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                    bytes, bytes.length,
                    InetAddress.getLocalHost(), 5000);
            //sendPacketToMaven(sendPacket);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }


    }


    private static void sendPacketToMaven(DatagramPacket receivePacket)
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
                System.out.println(messageReceived);

                //Parse and store data function
                SharedData.data[SharedData.dataPointer] = Integer.parseInt(messageReceived);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }





    public static class SharedData{
        static Integer data[] = new Integer[300];
        static int dataPointer = 0;
        static int Tmax = 63;
        static int Tmin = -10;
        static String phoneNumber = "5555555555";


    }



}



