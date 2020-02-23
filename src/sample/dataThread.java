package sample;
import java.security.SecureRandom;
public class dataThread implements Runnable{

    static final SecureRandom generator = new SecureRandom();
    GUI application;
    dataThread(GUI application){this.application = application;}

    @Override
    public void run()
    {
        application.waitForDataPackets();
    }

}
