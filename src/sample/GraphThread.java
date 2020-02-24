package sample;

public class GraphThread implements Runnable
{

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(1000);
            }catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}
