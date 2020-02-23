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

        phoneInput = new JTextField();
        add(phoneInput);

        phoneDisplay = new JLabel();
        add(phoneDisplay);

        maxInput = new JTextField();
        add(maxInput);

        maxDisplay = new JLabel();
        add(maxDisplay);

        minInput = new JTextField();
        add(minInput);

        minDisplay = new JLabel();
        add(minDisplay);
    }

}
