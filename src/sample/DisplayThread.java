package sample;
import javax.swing.*;

public class DisplayThread implements Runnable
{
    ShowTempFahrOrCel panel;
    DisplayThread(ShowTempFahrOrCel panel){this.panel = panel;}

    @Override
    public void run()
    {
        while(true)
        {
            Float currentData = GUI.SharedData.data[GUI.SharedData.dataPointer].floatValue()/((float)100);
            if(panel.fahrenheit.isSelected()){
                currentData = (float)(1.8 * currentData + 32);
            }
            panel.tempTextDisplay = new JTextField(currentData.toString());
        }
    }
}
