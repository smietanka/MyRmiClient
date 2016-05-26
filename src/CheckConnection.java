import java.rmi.Naming;

public class CheckConnection implements Runnable {
	
	private Client myClient;
	
	public CheckConnection(Client myClient)
	{
		this.myClient = myClient;
	}
	
	@Override
	public void run() {
		int asin = 0;
		while(true)
		{
			try
			{
				IMyServer as = (IMyServer)Naming.lookup("rmi://127.0.0.1/myabc");
				asin = 0;
			}
			catch(Exception e)
			{
				if(asin == 0)
				{
					myClient.WriteLog("Roz³¹czono z serwerem.");
					myClient.changeConnectButtonText("Po³¹cz");
				}
				Client.connected = false;
				asin++;
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
