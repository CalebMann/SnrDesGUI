package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShutOffScreen extends JPanel
{
    private final JButton turnOffButton;

    public ShutOffScreen()
    {
        setLayout(new FlowLayout());

        turnOffButton = new JButton("Box Display Button");
        add(turnOffButton);

        ButtonHit buttonHit = new ButtonHit();
        turnOffButton.addMouseListener(buttonHit);
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
                System.out.println("Display on");
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(e.getSource() == turnOffButton)
            {
                //some instruction to turn the display off will be sent
                System.out.println("Display off");
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
