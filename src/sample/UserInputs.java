package sample;

import javax.swing.*;
import java.awt.*;

public class UserInputs extends JPanel
{
    private final JTextField phoneInput;
    private final JTextField maxInput;
    private final JTextField minInput;
    private final JLabel phoneDisplay;
    private final JLabel maxDisplay;
    private final JLabel minDisplay;

    public UserInputs()
    {
        setLayout(new FlowLayout());

        phoneDisplay = new JLabel("Phone #: ");
        phoneDisplay.setSize(30,20);
        add(phoneDisplay);

        phoneInput = new JTextField();
        phoneInput.setColumns(10);
        add(phoneInput);

        maxDisplay = new JLabel("Max temp: ");
        maxDisplay.setSize(30,20);
        add(maxDisplay);

        maxInput = new JTextField();
        maxInput.setColumns(6);
        add(maxInput);

        minDisplay = new JLabel("Min temp");
        minDisplay.setSize(30,20);
        add(minDisplay);

        minInput = new JTextField();
        minInput.setColumns(6);
        add(minInput);
    }

}
