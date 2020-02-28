package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
//This class handles a button to virtually control the physical button on the hardware
public class ShutOffScreen extends JPanel
{
    //A button to simulate the hardware
    private final JButton turnOffButton;
    //A socket for sending the data
    private static DatagramSocket socket;

    public ShutOffScreen()
    {
        //Setting the layout type
        setLayout(new FlowLayout());
        //Setting up the button and event handler
        turnOffButton = new JButton("Box Display Button");
        add(turnOffButton);
        ButtonHit buttonHit = new ButtonHit();
        turnOffButton.addMouseListener(buttonHit);

        try // create DatagramSocket for sending and receiving packets
        {
            socket = new DatagramSocket();
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            System.exit(1);
        }
    }
//Listener for the button being pressed
    private class ButtonHit implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e)
        {
            if(e.getSource() == turnOffButton)
            {
                //some instruction to turn the display off will be sent
                try
                {
                    //Send a "1" to the hardware
                    String test = "1";
                    byte[] bytes = test.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(
                            bytes, bytes.length,
                            InetAddress.getByName("172.20.10.8"), 9998);
                    GUI.sendPackets(sendPacket);
                    System.out.println("Display on sent");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        }
        //Listener for when the button is released
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(e.getSource() == turnOffButton)
            {
                //some instruction to turn the display off will be sent
                try
                {
                    //Send a "0" to the hardware
                    String test = "0";
                    byte[] bytes = test.getBytes();
                    //byte[] addr = new byte[] {172,20,10,8};

                    DatagramPacket sendPacket = new DatagramPacket(
                            bytes, bytes.length,
                            InetAddress.getByName("172.20.10.8"), 9998);
                    GUI.sendPackets(sendPacket);
                    System.out.println("Display off sent");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
