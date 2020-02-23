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

    public GUI() {
        //this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        getContentPane().setLayout(new GridLayout());
        JSplitPane splitPane = new JSplitPane();
        setSize(new Dimension(400,800));
        getContentPane().add(splitPane);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(50);
        ShowTempFahrOrCel panel = new ShowTempFahrOrCel();
        splitPane.setTopComponent(panel);
        UserInputs panel2 = new UserInputs();
        splitPane.setBottomComponent(panel2);
        this.setVisible(true);

        try // create DatagramSocket for sending and receiving packets
        {
            socket = new DatagramSocket();
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new TriggerThread());
        executorService.execute(new DisplayThread());
        executorService.execute(new GraphThread());
        executorService.execute(new dataThread());

        try
        {
            String test = "-100";
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

}
