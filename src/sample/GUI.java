package sample;

import  javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class GUI extends JFrame
{
    private static DatagramSocket socket;

    public static void main(String[] args){
        ShowTempFahrOrCel frame = new ShowTempFahrOrCel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        try // create DatagramSocket for sending and receiving packets
        {
            socket = new DatagramSocket(5000);
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }

    }

    public void waitForPackets() {
        while (true){
            try {
                byte[] data = new byte[1000]; // set up packet
                DatagramPacket receivePacket =
                        new DatagramPacket(data, data.length);
                socket.receive(receivePacket); // wait to receive packet
                String packetData = new String(receivePacket.getData(),
                        0, receivePacket.getLength()).toLowerCase();


                receivePacket.setData(("Some Words That are Temporary").getBytes());

                sendPacketToMaven(receivePacket); // send packet to client
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendPacketToMaven(DatagramPacket receivePacket)
            throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(
                receivePacket.getData(), receivePacket.getLength(),
                receivePacket.getAddress(), receivePacket.getPort());
        socket.send(sendPacket); // send packet to client
        System.out.println("Sent: " + receivePacket.getData().toString());
    }

}
