package sample;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class DisplayThread implements Runnable
{
    ShowTempFahrOrCel panel;
    public boolean aboveMax = false;
    public boolean belowMin = false;
    DisplayThread(ShowTempFahrOrCel panel){this.panel = panel;}

    @Override
    public void run()
    {
        while(true)
        {
            if(GUI.SharedData.data[GUI.SharedData.dataPointer] != null)
            {
                Float currentData = GUI.SharedData.data[GUI.SharedData.dataPointer].floatValue()/((float)1000);
                if(panel.fahrenheit.isSelected()){
                    currentData = (float)(1.8 * currentData + 32);
                }
                panel.tempTextDisplay.setText(currentData.toString());
                if(aboveMax)
                {
                    if(currentData < (GUI.SharedData.Tmax - 5) || belowMin)
                    {
                        aboveMax = false;
                    }
                }
                if(belowMin)
                {
                    if(currentData > (GUI.SharedData.Tmin +5) || aboveMax)
                    {
                        belowMin = false;
                    }
                }
                if(currentData > GUI.SharedData.Tmax && !aboveMax)
                {
                    try
                    {
                        String test = "+" + GUI.SharedData.phoneNumber;
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
                    aboveMax = true;
                }
                else if(currentData < GUI.SharedData.Tmin && !belowMin)
                {
                    try
                    {
                        String test = "-" + GUI.SharedData.phoneNumber;
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
                    belowMin = true;
                }
            }
            else
            {

            }

        }
    }
}
