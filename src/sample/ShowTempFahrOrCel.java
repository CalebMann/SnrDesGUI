package sample;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowTempFahrOrCel extends JPanel
{
    public final JToggleButton celsius;
    public final JToggleButton fahrenheit;
    public JTextField tempTextDisplay;

    public ShowTempFahrOrCel()
    {
        setLayout(new FlowLayout());

        tempTextDisplay = new JTextField("Unplugged Sensor");
        tempTextDisplay.setEditable(false);
        add(tempTextDisplay);

        celsius = new JToggleButton("Celsius", true);
        celsius.setSize(70,40);
        add(celsius);

        fahrenheit = new JToggleButton("Fahrenheit", false);
        celsius.setSize(70,40);
        add(fahrenheit);

        Toggle t = new Toggle();
        celsius.addMouseListener(t);
        fahrenheit.addMouseListener(t);
    }

    private class Toggle implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(e.getSource() == fahrenheit)
            {
                fahrenheit.setSelected(true);
                celsius.setSelected(false);
            }
            else if(e.getSource() == celsius)
            {
                fahrenheit.setSelected(false);
                celsius.setSelected(true);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
