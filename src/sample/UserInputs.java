package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInputs extends JPanel
{
    //Lots and Lots of fields for text inputs and displays on the GUI
    //In total, there is a min/max threshold, a min/max text message and a phone number, plus displays for each as well
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
    private final JTextField highTempTextInput;
    private final JTextField lowTempTextInput;
    private final JLabel highTempTextDisplay;
    private final JLabel lowTempTextDisplay;
    private final JTextField currenthighTempTextInput;
    private final JTextField currentlowTempTextInput;
    private final JLabel currenthighTempTextDisplay;
    private final JLabel currentlowTempTextDisplay;

    public UserInputs()
    {
        //Set up the layout type
        setLayout(new FlowLayout());
        //Settings for the phone display
        phoneDisplay = new JLabel("Phone #: ");
        phoneDisplay.setSize(30,20);
        add(phoneDisplay);
        //Settings for the phone input
        phoneInput = new JTextField();
        phoneInput.setColumns(10);
        add(phoneInput);
        //Settings for the max temp threshold display
        maxDisplay = new JLabel("Max temp (*C): ");
        maxDisplay.setSize(30,20);
        add(maxDisplay);
        //Settings for the max temp threshold input
        maxInput = new JTextField();
        maxInput.setColumns(8);
        add(maxInput);
        //Settings for the min temp threshold display
        minDisplay = new JLabel("Min temp (*C): ");
        minDisplay.setSize(30,20);
        add(minDisplay);
        //Settings for the min temp threshold input
        minInput = new JTextField();
        minInput.setColumns(8);
        add(minInput);
        //Settings for a display for the text Current
        current = new JLabel("Current - ");
        current.setSize(30,20);
        add(current);
        //Settings for the phone number display
        currentphoneDisplay = new JLabel("Phone #: ");
        currentphoneDisplay.setSize(30,20);
        add(currentphoneDisplay);
        //Settings for the  for the phone number input
        currentphoneInput = new JTextField();
        currentphoneInput.setColumns(8);
        currentphoneInput.setEditable(false);
        currentphoneInput.setText("5632310443");
        add(currentphoneInput);
        //Settings for the max temp display
        currentmaxDisplay = new JLabel("Max temp (*C): ");
        currentmaxDisplay.setSize(30,20);
        add(currentmaxDisplay);
        //Settings for the  for the current max input
        currentmaxInput = new JTextField();
        currentmaxInput.setColumns(6);
        currentmaxInput.setEditable(false);
        currentmaxInput.setText("63");
        add(currentmaxInput);
        //Settings for the current min display
        currentminDisplay = new JLabel("Min temp (*C): ");
        currentminDisplay.setSize(30,20);
        add(currentminDisplay);
        //Settings for the current min input
        currentminInput = new JTextField();
        currentminInput.setColumns(6);
        currentminInput.setEditable(false);
        currentminInput.setText("-10");
        add(currentminInput);
        //Settings for the high temp text message display
        highTempTextDisplay = new JLabel("High Temp Text: ");
        add(highTempTextDisplay);
        //Settings for the high temp text message input
        highTempTextInput = new JTextField();
        highTempTextInput.setColumns(12);
        add(highTempTextInput);
        //Settings for the low temp text display
        lowTempTextDisplay = new JLabel("Low Temp Text: ");
        add(lowTempTextDisplay);
        //Settings for the low temp text input
        lowTempTextInput = new JTextField();
        lowTempTextInput.setColumns(12);
        add(lowTempTextInput);
        //Settings for the current high temp text display
        currenthighTempTextDisplay = new JLabel("Current High Temp Text: ");
        add(currenthighTempTextDisplay);
        //Settings for the current high temp input
        currenthighTempTextInput = new JTextField("high");
        currenthighTempTextInput.setColumns(10);
        currenthighTempTextInput.setEditable(false);
        add(currenthighTempTextInput);
        //Settings for the current low temp display
        currentlowTempTextDisplay = new JLabel("Current Low Temp Text: ");
        add(currentlowTempTextDisplay);
        //Settings for the current low temp input
        currentlowTempTextInput = new JTextField("low");
        currentlowTempTextInput.setColumns(10);
        currentlowTempTextInput.setEditable(false);
        add(currentlowTempTextInput);

        //Settings for the Setting up each listener for each text field input
        EnterListener enterListener = new EnterListener();
        phoneInput.addKeyListener(enterListener);
        maxInput.addKeyListener(enterListener);
        minInput.addKeyListener(enterListener);
        highTempTextInput.addKeyListener(enterListener);
        lowTempTextInput.addKeyListener(enterListener);

    }

    private class EnterListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        //If the user hits enter, update the field they hit enter inside of
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
                else if(e.getSource() == highTempTextInput)
                {
                    validateAndSetHighText();
                }
                else if(e.getSource() == lowTempTextInput)
                {
                    validateAndSetLowText();
                }
            }
        }
        //A function to update the phone number
        public void validateAndSetPhone()
        {
            if(phoneInput.getText().matches("\\d{10}")) //10 digit decimal number
            {
                currentphoneInput.setText(phoneInput.getText());
                GUI.SharedData.phoneNumber = phoneInput.getText();
            }
            phoneInput.setText("");
        }
        //A function to update the maximum temp threshold
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
            //Don't set if within 5 of the low temp or if below the min temp
            if(input > Float.valueOf(currentminInput.getText())+5 && input <= 63)
            {
                currentmaxInput.setText(input.toString());
                GUI.SharedData.Tmax = input;
            }
            maxInput.setText("");
        }
        //A function to update the minimum temp threshold
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
            //Don't set the new threshold if within 5 of the max threshold or if greate than the max threshold
            if(input < Float.valueOf(currentmaxInput.getText())-5 && input >= -10)
            {
                currentminInput.setText(input.toString());
                GUI.SharedData.Tmin = input;
            }
            minInput.setText("");
        }
        //Update the text message
        public void validateAndSetHighText()
        {
            currenthighTempTextInput.setText(highTempTextInput.getText());
            GUI.SharedData.highText = highTempTextInput.getText();
            highTempTextInput.setText("");
        }
        //Update the text message
        public void validateAndSetLowText()
        {
            currentlowTempTextInput.setText(lowTempTextInput.getText());
            GUI.SharedData.lowText = lowTempTextInput.getText();
            lowTempTextInput.setText("");
        }
    }
}
