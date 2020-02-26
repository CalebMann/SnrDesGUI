package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ShutOffScreen extends JPanel
{
    private final JButton turnOffButton;
    private static DatagramSocket socket;

    public ShutOffScreen()
    {
        setLayout(new FlowLayout());

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
                    String test = "1";
                    byte[] bytes = test.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(
                            bytes, bytes.length,
                            InetAddress.getLocalHost(), 9999);
                    GUI.sendPackets(sendPacket);
                    System.out.println("Display on sent");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(e.getSource() == turnOffButton)
            {
                //some instruction to turn the display off will be sent
                try
                {
                    String test = "1";
                    byte[] bytes = test.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(
                            bytes, bytes.length,
                            InetAddress.getLocalHost(), 9999);
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
