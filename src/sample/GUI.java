package sample;

import  javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class GUI extends JFrame
{
    private static DatagramSocket socket;

    public static void main(String[] args) {
        ShowTempFahrOrCel frame = new ShowTempFahrOrCel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        try // create DatagramSocket for sending and receiving packets
        {
            socket = new DatagramSocket();
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }

        try
        {
            String test = "-100";
            byte[] bytes = test.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                    bytes, bytes.length,
                    InetAddress.getLocalHost(), 5000);
            sendPacketToMaven(sendPacket);
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
