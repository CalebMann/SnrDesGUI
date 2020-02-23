package sample;
import java.security.SecureRandom;
public class dataThread implements Runnable{

    static final SecureRandom generator = new SecureRandom();

    @Override
    public void run()
    {
        while(true)
        {
            int data = generator.nextInt(75);
            data = data-11;
            if(data < -10){
                data = -999;
            }else if(data > 63){
                data = 999;
            }

            System.out.println("Data");
            try {
                Thread.sleep(generator.nextInt(1100));
            }catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }

}
