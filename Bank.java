package management;
public class Bank 
{
	public static void main (String args[])
	{
		Client C1 = null;
		Client C2 = null;
		
		try 
		{
			C1 = new Client();
			C2 = new Client();
			
			//C1.getTransactionsAmountForStatistics();
			//C1.someThread.start();
			C1.someThreadMain.start();
			C2.someThread.start();
			
		} 
		catch (OwnException e) 
		{
			
		}
		
		C1.setVisible(true);
	}

}
