package sample;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//This is the panel for the buttons and display for showing the temperature
public class ShowTempFahrOrCel extends JPanel
{
    //A button to toggle to celsius
    public final JToggleButton celsius;
    //A button to toggle to fahrenheit
    public final JToggleButton fahrenheit;
    //A text field to show the display
    public JTextField tempTextDisplay;

    public ShowTempFahrOrCel()
    {
        //Sets the layout of the panel
        setLayout(new FlowLayout());

        //Set a default display to the text field and gets the settings set
        tempTextDisplay = new JTextField("Unplugged Sensor");
        tempTextDisplay.setEditable(false);
        add(tempTextDisplay);

        //Set up the toggleability and properties of the buttons
        celsius = new JToggleButton("Celsius", true);
        celsius.setSize(70,40);
        add(celsius);

        //Set up the toggleability and properties of the buttons
        fahrenheit = new JToggleButton("Fahrenheit", false);
        celsius.setSize(70,40);
        add(fahrenheit);

        Toggle t = new Toggle();
        celsius.addMouseListener(t);
        fahrenheit.addMouseListener(t);
    }

    //A class to set up how the buttons will toggle, making one deselect the other when pressed
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
