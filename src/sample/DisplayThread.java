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
            int currentPointer = 0;
            if(GUI.SharedData.dataPointer <= 0){
                currentPointer = 299;
            }else{
                currentPointer = GUI.SharedData.dataPointer-1;
            }
            if(GUI.SharedData.data[currentPointer] != null)
            {
                Float currentData = GUI.SharedData.data[currentPointer].floatValue()/((float)1000);
                //System.out.println("This is the current data: " + currentData);
                if(panel.fahrenheit.isSelected()){
                    panel.tempTextDisplay.setText(((Float)(1.8f*currentData+32)).toString());
                }else{
                    panel.tempTextDisplay.setText(currentData.toString());
                }

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
                        String test = GUI.SharedData.phoneNumber + GUI.SharedData.highText;
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
                        String test = GUI.SharedData.phoneNumber + GUI.SharedData.lowText;
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
