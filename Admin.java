package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

import jdbcwithobjectpool.JDBCConnectionPool;


public class Admin extends JFrame
{
	private static final long serialVersionUID = 1L;
	////////////////////////////////////////////////
	
	int depositCount, withdrawCount, transferCount;

	Admin()
	{
		//customize frame
		setLayout(new BorderLayout());
		getContentPane().setPreferredSize(new Dimension(490, 390));
	}
	
	public void createAdminPanel()
	{
		//customize frame
		//setLayout(new BorderLayout());
		//getContentPane().setPreferredSize(new Dimension(490, 390));
		
		JPanel adminButtonsPanel = new JPanel();
		adminButtonsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		JButton visualizeUsersTransactionsHistory = new JButton("User's History");
		
		adminButtonsPanel.add(visualizeUsersTransactionsHistory);
		
		add(adminButtonsPanel, BorderLayout.NORTH);
		
		visualizeUsersTransactionsHistory.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//customizeUsersTransactionsHistoryPanel();
				getUsersTansactionHistory();
			}
			
		});
		
		setVisible(true);
		pack();
		validate();		
	}
	
	public void customizeUsersTransactionsHistoryPanel()
	{
		JLabel usersHistory = new JLabel();
		usersHistory.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
	    //create a dataset    
	    DefaultPieDataset pieDataSet = new DefaultPieDataset();
	    
	    //add values to dataset
	    pieDataSet.setValue("Deposit", depositCount);
	    pieDataSet.setValue("Withdraw", withdrawCount);
	    pieDataSet.setValue("Transfer", transferCount);
	    
	    //create a 3D chart with the given title that appears above the chart
	    JFreeChart chart = ChartFactory.createPieChart3D("COUNTING TRANSACTIONS", pieDataSet, true, false, true);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(315, 195));
		chartPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

	    //declare a pieplot so we can customize the chart
	    PiePlot3D plot = (PiePlot3D) chart.getPlot();
	    plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
		plot.setForegroundAlpha(0.7f);
		plot.setDepthFactor(0.1);
		plot.setNoDataMessage("Not enough data to display.");
		plot.setMaximumLabelWidth(0.2);

		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
	            "{0} {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.00%"));
		plot.setLabelGenerator(gen);
		
		//add(usersHistory, BorderLayout.CENTER);

		pack();
		validate();
	}
	

	
	public void getUsersTansactionHistory()
	{
		Connection connection = null;
		try
		{
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//get all users list
			String sqlGetUsersList = "SELECT username FROM CLIENTS WHERE clientid > 0";
			PreparedStatement stmtGetUsersList = connection.prepareStatement(sqlGetUsersList);
			ResultSet rsGetUsersList = stmtGetUsersList.executeQuery();
			Vector<String> usersList = new Vector<String>();
			int i = 0;
			
			while(rsGetUsersList.next())
			{
				usersList.add(rsGetUsersList.getString("Username"));	
				i = i + 1;		
			}
			
			JComboBox<String> usersList2 = new JComboBox<String>(usersList);
			usersList2.setSelectedIndex(0);
			JPanel usersList2Panel = new JPanel();
			usersList2Panel.add(usersList2);
			JLabel selectUser = new JLabel("Select an user");
			selectUser.setHorizontalAlignment(SwingConstants.CENTER);
			JPanel westSide = new JPanel();
			westSide.setLayout(new GridLayout(14, 1));
			westSide.add(selectUser);
			westSide.add(usersList2Panel);
			
			usersList2.addActionListener(new ActionListener()
			{
				@SuppressWarnings({ "rawtypes", "unchecked" })
				@Override
				public void actionPerformed(ActionEvent a) 
				{
					if (a.getSource() == usersList2)
					{
						JComboBox<Object> comboBox = new JComboBox<Object>();
						comboBox = (JComboBox)a.getSource();
						
						if (comboBox.getSelectedItem().equals("geo"))
						{
							JPanel usersStatistics = new JPanel();
							usersStatistics.setBorder(BorderFactory.createTitledBorder("fdfd"));
							add(usersStatistics);
						}
					}
				}
				
			});
			
			add(westSide, BorderLayout.WEST);
			
			pack();
			setVisible(true);

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
			catch(SQLException e) 
			{
					
			}
		}
		
	}
	
}
