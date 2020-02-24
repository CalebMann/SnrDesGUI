package sample;

public class TriggerThread implements Runnable
{

    @Override
    public void run()
    {
        while(true)
        {
            //System.out.println("Triggered");
            try {
                Thread.sleep(1000);
            }catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}
