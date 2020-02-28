package sample;
import java.net.DatagramPacket;
import java.net.InetAddress;
//This thread will handle the display of real time data from the hardware and it will also check for if the data needs to trigger a text message.
public class DisplayThread implements Runnable
{
    //Define the panel variables
    ShowTempFahrOrCel panel;
    //Two flags to indicate if the text has been sent for the high or low marker as to not spam texts more than once
    public boolean aboveMax = false;
    public boolean belowMin = false;
    //Constructor to import the panel data
    DisplayThread(ShowTempFahrOrCel panel){this.panel = panel;}

    @Override
    public void run()
    {
        while(true)
        {
            //Create a current malleable pointer that is one less than the actual datapointer as we want to look at the last locked in data rather than the current one.
            //If you want to see the real time data, then just have currentPointer = GUI.dataPointer
            int currentPointer = 0;
            if(GUI.SharedData.dataPointer <= 0){
                currentPointer = 299;
            }else{
                currentPointer = GUI.SharedData.dataPointer-1;
            }
            //If the data is not NULL
            if(GUI.SharedData.data[currentPointer] != null)
            {
                //Turn the data into its correct format by dividing by 1000
                Float currentData = GUI.SharedData.data[currentPointer].floatValue()/((float)1000);
                //If fahrenheit is selected then convert the display to fahrenheit else leave it as celsius
                if(panel.fahrenheit.isSelected()){
                    panel.tempTextDisplay.setText(((Float)(1.8f*currentData+32)).toString());
                }else{
                    panel.tempTextDisplay.setText(currentData.toString());
                }
                //If we have triggered the flag for being above the max temp threshold
                if(aboveMax)
                {
                    //If the current data is 5 less than that threshold or if it goes below the minimum threshold, then reset the flag
                    //The -5 is for a buffer so that hovering around the threshold value doesn't spam the user
                    if(currentData < (GUI.SharedData.Tmax - 5) || belowMin)
                    {
                        aboveMax = false;
                    }
                }
                //If we have triggered the falg for being below the min temp threshold
                if(belowMin)
                {
                    //If the current data is 5 more than the min threshold or if it goes above the max threshold, the reset the flag
                    if(currentData > (GUI.SharedData.Tmin +5) || aboveMax)
                    {
                        belowMin = false;
                    }
                }
                //If the Data is above the max threshold and the Maxflag is not set
                if(currentData > GUI.SharedData.Tmax && !aboveMax)
                {
                    try
                    {
                        //Send the message to the other application so that a text message will be sent.
                        String test = GUI.SharedData.phoneNumber + ";" + GUI.SharedData.highText;
                        byte[] bytes = test.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                bytes, bytes.length,
                                InetAddress.getLocalHost(), 5000);
                        GUI.sendPackets(sendPacket);
                        System.out.println("Text sent high");
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                    //Set the flag
                    aboveMax = true;
                }//If the data is less than the minimum threshold and the minFlag hasn't been set
                else if(currentData < GUI.SharedData.Tmin && !belowMin)
                {
                    try
                    {
                        //Send the message to the other application so that a text message will be sent.
                        String test = GUI.SharedData.phoneNumber + ";" + GUI.SharedData.lowText;
                        byte[] bytes = test.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                bytes, bytes.length,
                                InetAddress.getLocalHost(), 5000);
                        GUI.sendPackets(sendPacket);
                        System.out.println("Text sent low");
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }
                    //Set the min Flag
                    belowMin = true;
                }
            }
        }
    }
}
