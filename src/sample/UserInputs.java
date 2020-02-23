package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInputs extends JPanel
{
    private final JTextField phoneInput;
    private final JTextField maxInput;
    private final JTextField minInput;
    private final JLabel phoneDisplay;
    private final JLabel maxDisplay;
    private final JLabel minDisplay;
    private final JTextField currentphoneInput;
    private final JTextField currentmaxInput;
    private final JTextField currentminInput;
    private final JLabel currentphoneDisplay;
    private final JLabel currentmaxDisplay;
    private final JLabel currentminDisplay;
    private final JLabel current;

    public UserInputs()
    {
        setLayout(new FlowLayout());

        phoneDisplay = new JLabel("Phone #: ");
        phoneDisplay.setSize(30,20);
        add(phoneDisplay);

        phoneInput = new JTextField();
        phoneInput.setColumns(10);
        add(phoneInput);

        maxDisplay = new JLabel("Max temp (*C): ");
        maxDisplay.setSize(30,20);
        add(maxDisplay);

        maxInput = new JTextField();
        maxInput.setColumns(8);
        add(maxInput);

        minDisplay = new JLabel("Min temp (*C): ");
        minDisplay.setSize(30,20);
        add(minDisplay);

        minInput = new JTextField();
        minInput.setColumns(8);
        add(minInput);

        current = new JLabel("Current - ");
        current.setSize(30,20);
        add(current);

        currentphoneDisplay = new JLabel("Phone #: ");
        currentphoneDisplay.setSize(30,20);
        add(currentphoneDisplay);

        currentphoneInput = new JTextField();
        currentphoneInput.setColumns(8);
        currentphoneInput.setEditable(false);
        currentphoneInput.setText("5632310443");
        add(currentphoneInput);

        currentmaxDisplay = new JLabel("Max temp (*C): ");
        currentmaxDisplay.setSize(30,20);
        add(currentmaxDisplay);

        currentmaxInput = new JTextField();
        currentmaxInput.setColumns(6);
        currentmaxInput.setEditable(false);
        currentmaxInput.setText("63");
        add(currentmaxInput);

        currentminDisplay = new JLabel("Min temp (*C): ");
        currentminDisplay.setSize(30,20);
        add(currentminDisplay);

        currentminInput = new JTextField();
        currentminInput.setColumns(6);
        currentminInput.setEditable(false);
        currentminInput.setText("-10");
        add(currentminInput);

        EnterListener enterListener = new EnterListener();
        phoneInput.addKeyListener(enterListener);
        maxInput.addKeyListener(enterListener);
        minInput.addKeyListener(enterListener);

    }

    private class EnterListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e)
        {
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                if(e.getSource() == phoneInput)
                {
                    validateAndSetPhone();
                }
                else if(e.getSource() == maxInput)
                {
                    validateAndSetMax();
                }
                else if(e.getSource() == minInput)
                {
                    validateAndSetMin();
                }
            }
        }

        public void validateAndSetPhone()
        {
            if(phoneInput.getText().matches("\\d{10}")) //10 digit decimal number
            {
                currentphoneInput.setText(phoneInput.getText());
            }
            phoneInput.setText("");
        }

        public void validateAndSetMax()
        {
            Float input = -10.0f;
            try
            {
                input = Float.valueOf(maxInput.getText());
                String precision = String.format("%.2f",input);
                input = Float.parseFloat(precision);
            }
            catch (Exception ex)
            {
                System.out.println("Couldn't format float from max input.");
            }

            if(input > Float.valueOf(currentminInput.getText()) && input < 63)
            {
                currentmaxInput.setText(input.toString());
            }
            maxInput.setText("");
        }

        public void validateAndSetMin()
        {
            Float input = 63.0f;
            try
            {
                input = Float.valueOf(minInput.getText());
                String precision = String.format("%.2f",input);
                input = Float.parseFloat(precision);
            }
            catch (Exception ex)
            {
                System.out.println("Couldn't format float from min input.");
            }

            if(input < Float.valueOf(currentmaxInput.getText()) && input > -10)
            {
                currentminInput.setText(input.toString());
            }
            minInput.setText("");
        }
    }

}
