package sample;
import java.security.SecureRandom;
public class dataThread implements Runnable{
//This flag will handle all incoming datapackets from the hardware
    static final SecureRandom generator = new SecureRandom();
    //Pass in and store the application so it can call the waitforpackets() method
    GUI application;
    dataThread(GUI application){this.application = application;}

    @Override
    public void run()
    {
        application.waitForDataPackets();
    }

}
