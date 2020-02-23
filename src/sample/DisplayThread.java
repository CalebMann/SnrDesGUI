package sample;

public class DisplayThread implements Runnable
{

    @Override
    public void run()
    {
        while(true)
        {
            System.out.println("Diplay");
            try {
                Thread.sleep(1000);
            }catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}
