package sample;

import  javax.swing.*;

public class GUI
{
    public static void main(String[] args) {
        ShowTempFahrOrCel frame = new ShowTempFahrOrCel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
