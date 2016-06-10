package linecharttransactionsvalues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import jdbcwithobjectpool.JDBCConnectionPool;

public class LineChartTransactions 
{
	////////////////////////////////////////////////
	
	public int depositCount = 0, withdrawCount = 0, transferCount = 0;
	static int clientID = 0;
	
	ArrayList<Integer> dValues;
	ArrayList<Integer> wValues;
	ArrayList<Integer> tValues;
	
	public JFreeChart lineChart;
	public ChartPanel chartPanelLine;

	@SuppressWarnings("static-access")
	public LineChartTransactions(String chartTitle, int clientID) 
	{
		this.clientID = clientID;
		
        //based on the dataset we create the chart
        lineChart = ChartFactory.createXYLineChart(null, null, "Amount", createDataset(), PlotOrientation.VERTICAL, true, true, false);

        //adding chart into a chart panel
        chartPanelLine = new ChartPanel(lineChart);
   }
  
	public XYDataset createDataset() 
	{  
		//fill the arrays with information from database
		getTransactionsAmountForStatistics();
		
		final XYSeries deposit = new XYSeries("Deposit");
     
		//fill deposit
		for (int d = 0; d < dValues.size(); d++)
		{
			deposit.add(d+1, dValues.get(d));
		}

		
		final XYSeries withdraw = new XYSeries("Withdraw");
		
		//fill withdraw 
		for (int w = 0; w < wValues.size(); w++)
      	{
			withdraw.add(w+1, wValues.get(w));
      	}
		
		
		final XYSeries transfer = new XYSeries("Transfer");
		
		//fill transfer
		for (int t = 0; t < tValues.size(); t++)
		{
			transfer.add(t+1, tValues.get(t));
		}
     
		
		//add all 3 arrays above in a dataset used to create the line chart
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(deposit);
		dataset.addSeries(withdraw);
		dataset.addSeries(transfer);
     
		return dataset;   
	}
   
	public void getTransactionsAmountForStatistics()
	{
		Connection connection = null;
		try
		{
			//call this function here for getting the number of each kind of transaction for further use		
			//int array[] = {depositCount, withdrawCount, transferCount};
			//Arrays.sort(array);
			dValues = new ArrayList<Integer>();
			wValues = new ArrayList<Integer>();
			tValues = new ArrayList<Integer>();
		
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//get current user values of each storage he made
			String sqlDepositAmounts = "SELECT amount FROM transactions WHERE clientID = ?"
									 + "AND ttype LIKE 'depositing'"
									 + "ORDER BY nr";
			PreparedStatement stmtDepositAmounts = connection.prepareStatement(sqlDepositAmounts);
			stmtDepositAmounts.setInt(1, clientID);
			ResultSet rsDepositAmounts = stmtDepositAmounts.executeQuery();
			
			while (rsDepositAmounts.next())
			{
				dValues.add(rsDepositAmounts.getInt("Amount"));
			}
			
			//get current user values of each withdrawal he made
			String sqlWithdrawAmounts = "SELECT amount FROM transactions WHERE clientID = ?"
									 + "AND ttype LIKE 'withdrawal'"
									 + "ORDER BY nr";
			PreparedStatement stmtWithdrawAmounts = connection.prepareStatement(sqlWithdrawAmounts);
			stmtWithdrawAmounts.setInt(1, clientID);
			ResultSet rsWithdrawAmounts = stmtWithdrawAmounts.executeQuery();
			
			while (rsWithdrawAmounts.next())
			{
				wValues.add(rsWithdrawAmounts.getInt("Amount"));
			}
					
			//get current user values of each transfer he made
			String sqlTransferAmounts = "SELECT amount FROM transactions WHERE clientID = ?"
									 + "AND ttype LIKE 'transfer'"
									 + "ORDER BY nr";
			PreparedStatement stmtTransferAmounts = connection.prepareStatement(sqlTransferAmounts);
			stmtTransferAmounts.setInt(1, clientID);
			ResultSet rsTransferAmounts = stmtTransferAmounts.executeQuery();
			
			while (rsTransferAmounts.next())
			{
				tValues.add(rsTransferAmounts.getInt("Amount"));
			}		
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{	
			try 
			{
				connection.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}		
		}		
	}
}