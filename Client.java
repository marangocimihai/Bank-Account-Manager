package management;

//own imports
import jdbcwithobjectpool.JDBCConnectionPool;
import linecharttransactionsvalues.LineChartTransactions;
import focustraversalpolicy.*;

//basic imports
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import java.text.DecimalFormat;

//SQL import
import java.sql.*;

//AWT imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

//SWING imports
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

//apache POI imports
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//jfreechart imports
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

//iText imports
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Client extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	////////////////////////////////////////////////

	public String maleOrFemale = "-";
	public boolean hasCurrentUserMadeTransactions = false, didUserChoosePhoto = false, defaultPhoto = false;
	public static int clientID;
	public int whichColorIsChosen = 0;
	public int depositCount = 0, withdrawCount = 0, transferCount = 0;
	
	public File image;
	static double pieChartThreadIterator;
	public boolean isThreadRunning = false;
	Timer rotate3DPieChart, changePhotosMainFrame;
	
	public JPanel transactionsCenterFinalPanel = new JPanel();
	public JPanel personalInformationCenterFinalPanel = new JPanel();
	public Object[][] transactionsHistoryTableContent;
	ArrayList<Integer> dValues;
	ArrayList<Integer> wValues;
	ArrayList<Integer> tValues;
	
	public static JLabel headers;
	public JPanel personalInformationCenterBorder, allInOne, whole;
	public JMenuBar menuBar;
	public JRadioButton maleOption, femaleOption;
	public JComboBox<Object> daysList, monthsList, yearsList;
	public JComboBox<Object> daysListCBD, monthsListCBD, yearsListCBD;
	public JToggleButton updateUserInfo;
	
	public JTextField username, usernameR, firstNameR, lastNameR, emailR, usernameLP, newFirstName, newLastName, 
					  newEmail;
	
	public JButton closeApp, registerNewAccount, recoverPassword, logIn, logOut, confirm, cancel, done, 
				   back, printPDF, printWORD, printEXCEL, submitTransfer, submitWithdraw, submitDeposit, 
				   submitChangePassword, submitNewFirstName, submitNewLastName, submitNewEmail, submitNewBirthDate,
				   choosePhotoFromPC, submitNewPhoto;
	
	public JToggleButton defaultPhoto1, defaultPhoto2, defaultPhoto3, defaultPhoto4, defaultPhoto5, defaultPhoto6, 
						 defaultPhoto7, defaultPhoto8, defaultPhoto9, defaultPhoto10, defaultPhoto11, defaultPhoto12; 
	
	
	private JTextField userToTransferMoneyTo, transferMoneyAmount, withdrawMoneyAmount, depositMoneyAmount;
	private JPasswordField password, passwordR, newPasswordCP, currentPasswordCP;
	private JTable transactionsHistoryTable;
	
	private JToggleButton statistics, deposit, withdraw, transferTo, transactionsHistory, changePassword, settings, 
						   changeFirstName,  changeLastName, changeEmail, changeBirthDate, changePhoto, print,
						   transactionsCount, transactionsValues;
	
	private JPanel transactions, personalInformationTab, destinationUserAndAmountFinalPanel, withdrawFinalPanel, 
				   depositFinalPanel, changePasswordFinalPanel, changeDetailsFinalPanel, firstNameEditFinalPanel, 
				   lastNameEditFinalPanel, birthDateEditFinalPanel, eMailEditFinalPanel, changePhotoEditFinalPanel,
				   allInOneWestFinal, statisticsPanelSouth;
	
	Thread someThread, someThreadMain;
	
	ImageIcon[] headerImages = 
	{
		new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
				   	+ "[OP]Bank\\icons\\headers\\header1.png"),
		new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
			   		+ "[OP]Bank\\icons\\headers\\header2.png"),
		new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
		   			+ "[OP]Bank\\icons\\headers\\header3.png")
	};
	
	public Client(String currentUserIDLineChart)
	{

	}
	
	//constructor
	Client() throws OwnException
	{	
		someThreadMain = new Thread(new Runnable() 
	    {
            @Override
			public void run() 
            {
            	goToMainFrame();
            }
        });	
		    
	    someThread = new Thread(new Runnable() 
	    {
            @Override
			public void run() 
            {
            	int i = 0;
            	
            	while(true)
            	{
            		try 
            		{
            			Thread.sleep(5000);
            		}
            		catch (InterruptedException e) 
            		{
            			e.printStackTrace();
            		}
            		
            		//set next image
            		headers.setIcon(headerImages[i]);
            		
            		//increment
            		i++;
            			
            		//if i = welcomeMessage.length, asign 0 to i
            		if (i == 3)
            		{
            			i = 0;
            		}        		
            	}
            }
        });
	}	

	@SuppressWarnings("deprecation")
	//customize the main window
	public void goToMainFrame()
	{
		try
		{		
			getContentPane().removeAll();
			validate();
			
			//set theme of the main frame
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			
			//layout, icon and size of the main frame
			ImageIcon mainFrameLogo = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\bank logo.png");
			setIconImage(mainFrameLogo.getImage());
			getContentPane().setLayout(new GridLayout(2, 1));
			setPreferredSize(new Dimension(490, 390));
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
			//create a menu bar and add it to the main frame
			menuBar = new JMenuBar();
			JMenu firstOption = new JMenu("App");
			JMenu secondOption = new JMenu("Language");
			JMenu thirdOption = new JMenu("Help");
			
			JMenuItem info = new JMenuItem("Info");
			JMenuItem about = new JMenuItem("About");	
			JMenuItem exit = new JMenuItem("Exit");
			JMenuItem newAccount = new JMenuItem("New Account");
			JMenuItem recoverPasswordMenu = new JMenuItem("Recover Password");
			JMenuItem englishLanguage = new JMenuItem("EN");
			
			ImageIcon englishLanguageIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\english language icon.png");
			englishLanguage.setIcon(englishLanguageIcon);
			
			firstOption.add(newAccount);
			firstOption.add(recoverPasswordMenu);
			firstOption.addSeparator();
			firstOption.add(exit);
			secondOption.add(englishLanguage);
			thirdOption.add(info);
			thirdOption.add(about);
			
			menuBar.add(firstOption);
			menuBar.add(secondOption);
			menuBar.add(thirdOption);
			
			setJMenuBar(menuBar);
					
			//create the header for the main frame, and add an image to it
			headers = new JLabel();
			headers.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			ImageIcon headersIcon =new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
		   									   + "[OP]Bank\\icons\\headers\\header3.png");
			headers.setIcon(headersIcon);
			
			//add the header to the main frame
			getContentPane().add(headers, BorderLayout.NORTH);
				
			//declare a new panel which will be the south side of the main frame
			JPanel southSide = new JPanel(null);
			southSide.setBorder(BorderFactory.createMatteBorder(0, 30, 0, 2, Color.black));
		
			//username field
			username = new JTextField();
			username.setToolTipText("Type here your username.");
			username.setLocation(140, 25);
			username.setSize(100, 25);
			southSide.add(username);
			
			//password field	
			password = new JPasswordField();
			password.setToolTipText("Type here your password.");
			password.setLocation(140, 65);
			password.setSize(100, 25);
			southSide.add(password);
		
			//'Username: '
			JLabel usr = new JLabel("Username:");
			usr.setLocation(84, 25);
			usr.setSize(100, 25);
			southSide.add(usr);
			
			//'Password: '
			JLabel pswr = new JLabel("Password:");
			pswr.setLocation(86, 65);
			pswr.setSize(100, 25);
			southSide.add(pswr);
					
			//create, customize and add the 'login' button
			ImageIcon logInIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\log in icon.png");
			logIn = new JButton("Connect");
			logIn.setIcon(logInIcon);
			logIn.setLocation(139, 95);
			logIn.setSize(102, 25);
			southSide.add(logIn);
			
			//create, customize and add the 'register account' button
			ImageIcon registerNewAccountIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\register new account icon.png");
			registerNewAccount = new JButton("Register");
			registerNewAccount.setIcon(registerNewAccountIcon);
			registerNewAccount.setLocation(300, 22);
			registerNewAccount.setSize(102, 25);
			registerNewAccount.setToolTipText("Do you want to join us ? Click here and start making a new account.");
			southSide.add(registerNewAccount);		
			
			//create, customize and add the 'lost password' button
			ImageIcon recoverPasswordIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\lost password icon.png");
			recoverPassword = new JButton("Recover");
			recoverPassword.setIcon(recoverPasswordIcon);
			recoverPassword.setLocation(300, 48);
			recoverPassword.setSize(102, 25);
			recoverPassword.setToolTipText("Did you forget your password ? Click here to get it back.");
			southSide.add(recoverPassword);
			
			//create, customize and add the 'close app' button
			ImageIcon closeAppIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\close app icon.png");
			closeApp = new JButton ("Close    ");
			closeApp.setIcon(closeAppIcon);
			closeApp.setLocation(300, 74);
			closeApp.setSize(102, 25);
			closeApp.setToolTipText("Close the application.");
			southSide.add(closeApp);
			
			//create, customize and add the copyright label
			JLabel copyrightLabel = new JLabel ("Copyright 2016 © Marangoci Mihai");
			copyrightLabel.setLayout(null);
			copyrightLabel.setLocation(270, 145);
			copyrightLabel.setSize(175, 15);
			southSide.add(copyrightLabel);
			
			//create, customize and add the facebook button
			ImageIcon facebookAccountIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\facebook icon.png");
			JLabel facebookAccount = new JLabel();
			facebookAccount.setIcon(facebookAccountIcon);
			facebookAccount.setLocation(445, 145);
			facebookAccount.setSize(15, 15);
			southSide.add(facebookAccount);
			
			//create, customize and add the facebook button from the west side
			ImageIcon facebookWESTIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\facebook icon.png");
			JLabel facebookWEST = new JLabel();
			facebookWEST.setIcon(facebookWESTIcon);
			facebookWEST.setLocation(20, 10);
			facebookWEST.setSize(15, 15);
			southSide.add(facebookWEST);
			
			//create, customize and add the twitter button from the west side
			ImageIcon twitterWESTIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\twitter icon.png");
			JLabel twitterWEST = new JLabel();
			twitterWEST.setIcon(twitterWESTIcon);
			twitterWEST.setLocation(20, 30);
			twitterWEST.setSize(15, 15);
			southSide.add(twitterWEST);
			
			//create, customize and add the google+ button from the west side
			ImageIcon googlePlusWESTIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\googleplus icon.png");
			JLabel googlePlusWEST = new JLabel();
			googlePlusWEST.setIcon(googlePlusWESTIcon);
			googlePlusWEST.setLocation(20, 50);
			googlePlusWEST.setSize(15, 15);
			southSide.add(googlePlusWEST);
			
			//create, customize and add the google+ button from the west side
			ImageIcon linkedinWESTIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\linkedin icon.png");
			JLabel linkedinWEST = new JLabel();
			linkedinWEST.setIcon(linkedinWESTIcon);
			linkedinWEST.setLocation(20, 70);
			linkedinWEST.setSize(15, 15);
			southSide.add(linkedinWEST);
			
			//add the southSide panel, which contains all the buttons + text labels to the south side of the main frame
			getContentPane().add(southSide);
			
			//create a new focus traversal policy
			Vector<Component> order = new Vector<Component>(7);
			order.add(username);
			order.add(password);
			order.add(logIn);
			order.add(registerNewAccount);
			order.add(recoverPassword);
			order.add(closeApp);
			
			//then add it to the southSide Panel
			MyOwnFocusTraversalPolicy newPolicy = new MyOwnFocusTraversalPolicy(order);
			southSide.setFocusCycleRoot(true);
		    southSide.setFocusTraversalPolicy(newPolicy);
		    
			//add listeners for all buttons / menus / etc.
			closeApp.addActionListener(this);
			registerNewAccount.addActionListener(this);
			recoverPassword.addActionListener(this);
			logIn.addActionListener(this);
			exit.addActionListener(this);
			about.addActionListener(this);
			info.addActionListener(this);
			newAccount.addActionListener(this);
			recoverPasswordMenu.addActionListener(this);
			
			//listener for password text field on login
			password.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e)
				{
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
			   	 	{
						if (username.getText().length() <= 0)
						{
							try
							{
								throw new OwnException("Username is too short !");
							}
							catch (OwnException ex)
							{
								
							}
						}
						
						else if (password.getText().length() <= 0)
						{
							try
							{
								throw new OwnException("Password is too short !");
							}
							catch (OwnException ex)
							{

							}
						}
						else if (username.getText().equals("admin"))
						{
							dispose();
							
							Admin A = new Admin();
							A.createAdminPanel();
						}
						
						else
						{
							logIn();
						}
			        } 
				}
			});
			
			//listener for facebook label
			facebookAccount.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if(JOptionPane.showConfirmDialog(null,
						 	"You will be redirected to the author's facebook page. "
						 	+ "Are you sure you want to continue ?", 
						 	"Question",
						 	JOptionPane.YES_NO_OPTION,
						 	JOptionPane.QUESTION_MESSAGE) 
							== JOptionPane.YES_OPTION)
					{
						if(Desktop.isDesktopSupported())
						{
							try 
							{
								Desktop.getDesktop().browse(new URI("https://www.facebook.com/mitzulik93"));
							} 
							catch (IOException | URISyntaxException e1) 
							{
								
							}
						}
					}
				}
				
			});
			
			facebookWEST.addMouseListener(new MouseAdapter()
			{
				//if the user goes over the button with the cursor, change the appearance of the button
				public void mouseEntered(MouseEvent e)
				{
					facebookWEST.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\facebook rectangular.png"));
					facebookWEST.setSize(45, 15);
					facebookWEST.setLocation(20, 10);
					revalidate();						
				}
				
				//if he clicks on it, go on specified link
				public void mouseClicked(MouseEvent e)
				{
					if(Desktop.isDesktopSupported())
					{
						try 
						{
							Desktop.getDesktop().browse(new URI("https://www.facebook.com/"));		
						} 
						catch (IOException | URISyntaxException e1) 
						{
								
						}						
					}
				}	
						
				//if the user removes the cursor from the button, change the appearance of the button back to normal
				public void mouseExited(MouseEvent e)
				{
					facebookWEST.setIcon(facebookWESTIcon);
					facebookWEST.setLocation(20, 10);
					facebookWEST.setSize(15, 15);
					revalidate();
				}
			});
			
			twitterWEST.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					twitterWEST.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\twitter rectangular.png"));
					twitterWEST.setSize(45, 15);
					twitterWEST.setLocation(20, 30);
					revalidate();				
				}	
				
				public void mouseClicked(MouseEvent e)
				{
					if(Desktop.isDesktopSupported())
					{
						try 
						{
							if (true)
							{
								Desktop.getDesktop().browse(new URI("https://twitter.com/"));
							}
						} 
						catch (IOException | URISyntaxException e1) 
						{
								
						}						
					}
				}	
				
				public void mouseExited(MouseEvent e)
				{
					twitterWEST.setIcon(twitterWESTIcon);
					twitterWEST.setLocation(20, 30);
					twitterWEST.setSize(15, 15);
					revalidate();
				}
			});
			
			googlePlusWEST.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					googlePlusWEST.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\googleplus rectangular.png"));
					googlePlusWEST.setSize(45, 15);
					googlePlusWEST.setLocation(20, 50);
					revalidate();
					
				}	
				
				public void mouseClicked(MouseEvent e)
				{
					if(Desktop.isDesktopSupported())
					{
						try 
						{
							if (true)
							{
								Desktop.getDesktop().browse(new URI("https://plus.google.com/"));
							}
						} 
						catch (IOException | URISyntaxException e1) 
						{
								
						}						
					}
				}	
				
				public void mouseExited(MouseEvent e)
				{
					googlePlusWEST.setIcon(googlePlusWESTIcon);
					googlePlusWEST.setLocation(20, 50);
					googlePlusWEST.setSize(15, 15);
					revalidate();
				}
			});
			
			linkedinWEST.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					linkedinWEST.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\main frame icons\\linkedin rectangular.png"));
					linkedinWEST.setSize(45, 15);
					linkedinWEST.setLocation(20, 70);
					revalidate();
				}
				
				public void mouseClicked(MouseEvent e)
				{
					if(Desktop.isDesktopSupported())
					{
						try 
						{
							Desktop.getDesktop().browse(new URI("https://www.linkedin.com/"));
						} 
						catch (IOException | URISyntaxException e1) 
						{
							e1.printStackTrace();
						}						
					}
				}	
				
				public void mouseExited(MouseEvent e)
				{
					linkedinWEST.setIcon(linkedinWESTIcon);
					linkedinWEST.setLocation(20, 70);
					linkedinWEST.setSize(15, 15);
					revalidate();
				}
			});
			
			
			//listener for main X button
			this.addWindowListener(new WindowAdapter () 
			{
				public void windowClosing(WindowEvent e) 
				{
					if(JOptionPane.showConfirmDialog(null,
						 	"Do you really wish to close the application ?", 
						 	"Question",
						 	JOptionPane.YES_NO_OPTION,
						 	JOptionPane.QUESTION_MESSAGE) 
							== JOptionPane.YES_OPTION)
					{
						System.exit(0);
					}
				}
			});
			
			username.requestFocusInWindow(); //set focus on username textfield when the app is started
			
			pack();		
			setVisible(true);
		}
		
		//handle exceptions
		catch(Exception e)
		{
			System.exit(0);
		}
	}
	
	@SuppressWarnings("deprecation")
	//log in function
	public void logIn()
	{	
		Connection connection = null;
		
		try 
		{
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			String sqlSelectUsername = "SELECT firstname FROM clients WHERE username LIKE ?";
			String sqlSelectPassword = "SELECT pswrd FROM clients WHERE username LIKE ?";
			PreparedStatement stmtUsername = connection.prepareStatement(sqlSelectUsername);
			PreparedStatement stmtPassword = connection.prepareStatement(sqlSelectPassword);
			stmtUsername.setString(1, username.getText());
			stmtPassword.setString(1, username.getText());
			ResultSet rsUsername = stmtUsername.executeQuery();
			
			
			//check if username exists in the database
			if (rsUsername.next())
			{				
				ResultSet rsPassword = stmtPassword.executeQuery();
				
				//check if the given password was correct
				if (rsPassword.next())
				{
					String passwordFromQuery = rsPassword.getString("pswrd");
					
					if (password.getText().equals(passwordFromQuery))
					{
						if (username.getText().equals("admin"))
						{
							
						}
						else
						{
							createUserPersonalPage();
						}
					}
					else
						throw new OwnException("Incorrect password !");
				}
			}
			else
			{
				throw new OwnException("Incorrect username !");
			}

		} 	
		catch(SQLException e) 
		{
			
		} 
		catch(OwnException e)
		{

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
	
	//searches for the password in database and display it
	public void lostPassword()
	{
		//check if the user has pressed 'done' button more than once, to remove the last component of the 'whole' panel
		if (whole.getComponentCount() > 1)
		{
			whole.remove(1);
		}
	
		Connection connection = null;
		
		try 
		{
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			String sqlSelectPassword = "SELECT pswrd FROM clients WHERE username LIKE ?";
			PreparedStatement stmtPassword = connection.prepareStatement(sqlSelectPassword);
			stmtPassword.setString(1, usernameLP.getText());
			ResultSet rsPassword = stmtPassword.executeQuery();
			
			//check if the user has specified an username to recover the password for
			if (usernameLP.getText().length() <= 0)
			{
				throw new OwnException("Username is too short !");
			}	
			
			//if the user exists in database, get his password
			if (rsPassword.next())
			{	
				//create / customize a label where the password shoul be viewed
				JLabel passwordLabel = new JLabel("Your password is: " + rsPassword.getString("pswrd"));
				passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
				passwordLabel.setBorder(BorderFactory.createTitledBorder(""));
				
				//add the password label created above to the 'whole' panel which was created in createLostPasswordPage() function
				whole.add(passwordLabel);
				
				validate();
			}
			else
				throw new OwnException("Specified user does not exist in the database !");

		} 	
		catch(SQLException e) 
		{
			
		} 
		catch(OwnException e)
		{
			
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
	
	@SuppressWarnings("deprecation")
	//insert the information from the register form fields on database
	public void register()
	{
		Connection connection = null;
		
		try 
		{
			//increment clientID every time someone is registering
			clientID++; 
					
			
			//establish a connection to the database
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			String sqlAddNewUser = "INSERT INTO clients(clientid, username, pswrd, firstname, lastname, genre, balance, email, birthdate) "
													 + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmtAddNewUser = connection.prepareStatement(sqlAddNewUser);
			
			stmtAddNewUser.setInt(1, clientID);
			stmtAddNewUser.setString(2, usernameR.getText());
			stmtAddNewUser.setString(3, passwordR.getText());
			stmtAddNewUser.setString(4, firstNameR.getText());
			stmtAddNewUser.setString(5, lastNameR.getText());
			stmtAddNewUser.setString(6, maleOrFemale);
			stmtAddNewUser.setInt(7, 0);
			stmtAddNewUser.setString(8, emailR.getText());
			
			String daysListString = (String) daysList.getSelectedItem();
			String monthsListString = (String) monthsList.getSelectedItem();
			String yearsListString = (String) yearsList.getSelectedItem();
			
			String monthsListStringFinal = (String) monthsList.getSelectedItem();
			
			switch (monthsListString) 
			{
            	case "January":  monthsListStringFinal = "1";
                     break;
            	case "February":  monthsListStringFinal = "2";
                     break;
            	case "March":  monthsListStringFinal = "3";
                     break;
            	case "April":  monthsListStringFinal = "4";
                     break;
            	case "May":  monthsListStringFinal = "5";
                     break;
            	case "June":  monthsListStringFinal = "6";
                     break;
            	case "July":  monthsListStringFinal = "7";
                     break;
            	case "August":  monthsListStringFinal = "8";
                     break;
            	case "September":  monthsListStringFinal = "9";
                     break;
            	case "October": monthsListStringFinal = "10";
                     break;
            	case "November": monthsListStringFinal = "11";
                     break;
            	case "December": monthsListStringFinal = "12";
                     break;
			}
			
			stmtAddNewUser.setString(9, daysListString + "/" + monthsListStringFinal + "/" + yearsListString);
			
			int successOrNo = stmtAddNewUser.executeUpdate();
			
			//check if the new user was successfully added in database
			if (successOrNo == 1)
			{				
				JOptionPane.showMessageDialog (null,
						   					   "The user has been created. You will be redirected to the Log In page.",
						   					   "Success", 
						   					   JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new OwnException("An error has occured !");
			}
			

		} 	
		catch(SQLException e) 
		{

		} 
		catch(OwnException e)
		{
			
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
	
	@SuppressWarnings("deprecation")
	//customize user's personal page
	private void createUserPersonalPage()
	{
		//remove all the components of the main frame
		getContentPane().removeAll();
		menuBar.removeAll();
		validate();
		
		//set layout
		getContentPane().setLayout(new BorderLayout());
		
		//customize west part header of user's personal page
		JLabel personalPage = new JLabel();
		ImageIcon personalPageIcon1 = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\personal page icon.png");
		personalPage.setIcon(personalPageIcon1);
		JPanel personalPagePanel = new JPanel();
		personalPagePanel.setLayout(new FlowLayout());
		personalPagePanel.setPreferredSize(new Dimension(275, 55));
		personalPagePanel.add(personalPage);
		personalPagePanel.add(new JLabel("   "));
		
		//customize east part header of user's personal page
		JLabel personalPage2 = new JLabel();
		ImageIcon imagineIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\visa1.png");
		personalPage2.setIcon(imagineIcon);
		personalPagePanel.add(personalPage2);
		personalPagePanel.setBackground(Color.lightGray);
		
		getContentPane().add(personalPagePanel, BorderLayout.NORTH);	
		
		//set a welcome message after logging in
		Object[] thankYou = {"Thank you !"};
		JOptionPane.showOptionDialog(this,
									 "Welcome, " + username.getText() + " !",
									 "Welcome message",
									 JOptionPane.OK_CANCEL_OPTION,
									 JOptionPane.INFORMATION_MESSAGE,
									 null,
									 thankYou,
									 thankYou[0]);
		
		//make new pannels for each side
		JPanel east = new JPanel();
		JPanel west = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		
		//set layout for center side
		center.setLayout(new GridLayout(1, 2));
		
		//make a tabbed pane with various options
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//make 'Personal Information' tab and customize it
		personalInformationTab = new JPanel();
		personalInformationTab.setLayout(new BorderLayout());
		ImageIcon personalInformationTabIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\personal information tab icon.png");
		personalInformationCenterBorder = new JPanel();
		tabbedPane.addTab("Personal Information", personalInformationTabIcon, personalInformationTab);
		
		//create and customize the 'Update' button in the user's personal page
		updateUserInfo = new JToggleButton("Update");
		updateUserInfo.setToolTipText("Have you recently made changes to your account ? Click here to show them properly.");
		ImageIcon updateUserInfoIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\update icon.png");
		updateUserInfo.setIcon(updateUserInfoIcon);
		JPanel userButtonsPanel = new JPanel();
		JPanel userButtonsPanelAvoidResize = new JPanel();
		userButtonsPanel.setLayout(new GridLayout(5, 1));
		userButtonsPanel.add(updateUserInfo);
		userButtonsPanel.add(new JLabel(" ")); //kind of a separator
		
		//create and customize 'Change password' button in the user's personal page
		ImageIcon changePasswordIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change password icon.png");
		changePassword = new JToggleButton("Change P.");
		changePassword.setToolTipText("Do you want to change your password ? Click here !");
		changePassword.setIcon(changePasswordIcon);
		userButtonsPanel.add(changePassword);
		
		//create and customize 'Settings' button in the user's personal page
		ImageIcon settingsIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\settings icon.png");
		settings = new JToggleButton("Settings    ");
		settings.setToolTipText("Do you want to make changes in your account ? Click here !");
		settings.setIcon(settingsIcon);
		userButtonsPanel.add(settings);
		
		//create and customize 'log out' button in the user's personal page
		ImageIcon logOutPIIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\log out icon.png");
		logOut = new JButton("Disconnect");
		logOut.setToolTipText("Do you want to log our from your account ? Click here !");
		logOut.setIcon(logOutPIIcon);
		userButtonsPanel.add(logOut);
		
		//create a button group and add them buttons created above to it, besides 'updateUserInfo' button
		ButtonGroup userButtonPanelButtonGroup = new ButtonGroup();
		userButtonPanelButtonGroup.add(changePassword);
		userButtonPanelButtonGroup.add(updateUserInfo);
		userButtonPanelButtonGroup.add(settings);
			
		//add the panels created above, along with the buttons, to a new one, then add the resulting panel to the main panel
		userButtonsPanelAvoidResize.add(userButtonsPanel);
		personalInformationTab.add(userButtonsPanelAvoidResize, BorderLayout.WEST);
		
		transactions = new JPanel();
		transactions.setLayout(new BorderLayout());
		
		//customized panel for the west side of the transactions tab
		JPanel transactionsWestSide = new JPanel();
		transactionsWestSide.setLayout(new GridLayout(7, 1));		
		
		//make the 'Statistics' toggle button
		statistics = new JToggleButton("Statistics");
		ImageIcon statisticsIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\statistics icon1.png");
		statistics.setIcon(statisticsIcon);
		statistics.setToolTipText("Do you wish to see your statistics ? Click here !");
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.add(statistics);
		transactionsWestSide.add(statisticsPanel);
		
		//make the 'Deposit' toggle button
		deposit = new JToggleButton("Deposit   ");
		ImageIcon depositIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\deposit icon.png");
		deposit.setIcon(depositIcon);
		deposit.setToolTipText("Do you wish to deposit money ? Click here !");
		JPanel depositPanel = new JPanel();
		depositPanel.add(deposit);
		transactionsWestSide.add(depositPanel);
		
		//make the 'Withdraw' toggle button
		withdraw = new JToggleButton("Withdraw");
		ImageIcon withdrawIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\withdraw icon.png");
		withdraw.setIcon(withdrawIcon);
		withdraw.setToolTipText("Do you wish to withdraw money ? Click here !");
		JPanel withdrawPanel = new JPanel();
		withdrawPanel.add(withdraw);
		transactionsWestSide.add(withdrawPanel);
		
		//make the 'Transfer' toggle button
		transferTo = new JToggleButton("Transfer  ");
		ImageIcon transferToIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\transfer icon.png");
		transferTo.setIcon(transferToIcon);
		transferTo.setToolTipText("Do you wish to make a transfer ? Click here !");
		JPanel transferPanel = new JPanel();
		transferPanel.add(transferTo);
		transactionsWestSide.add(transferPanel);
		
		//make the 'History' toggle button
		transactionsHistory = new JToggleButton("History   ");
		ImageIcon transactionsHistoryIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\transactions history icon.png");
		transactionsHistory.setIcon(transactionsHistoryIcon);
		transactionsHistory.setToolTipText("Do you wish to see your transactions history ? Click here !");
		JPanel transactionsHistoryPanel = new JPanel();
		transactionsHistoryPanel.add(transactionsHistory);
		transactionsWestSide.add(transactionsHistoryPanel);
		
		//make a ButtonGroup object and add the 3 toggle buttons created above
		ButtonGroup transactionsTabButtons = new ButtonGroup();
		transactionsTabButtons.add(deposit);
		transactionsTabButtons.add(withdraw);
		transactionsTabButtons.add(transferTo);
		transactionsTabButtons.add(transactionsHistory);
		transactionsTabButtons.add(statistics);
		
		//add the resulting panel to the WEST side of the transactions tab
		transactions.add(transactionsWestSide, BorderLayout.WEST);
		
		//add the transactions tab to the main tabbed pane
		ImageIcon transactionsTabIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\transactions tab icon.png");
		tabbedPane.addTab("Transactions", transactionsTabIcon, transactions);
		
		//offerts tab - still have to work on this one
		JComponent panel3 = new JPanel();
		panel3.add(new JTextField("To be continued..."));
		panel3.setBackground(Color.GRAY);
		//tabbedPane.addTab("Offerts", null, panel3);

		//add the tabbed pane created above to the center panel
		center.add(tabbedPane);
		
		//information actualization
		if (!(username.getText().equals("admin")))
		{
			getUserInfoFromDatabase();
		}

		//listener for various buttons
		ItemListener listener = new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{	
				if (e.getSource() == updateUserInfo && e.getStateChange() == ItemEvent.SELECTED)
				{
					personalInformationCenterFinalPanel.removeAll();
					//if updateUserInfo button is in 'selected' state, show more information about the current user
					getUserInfoFromDatabase();
				}
				
				else if (e.getSource() == changePassword && e.getStateChange() == ItemEvent.SELECTED)
				{
					personalInformationCenterFinalPanel.removeAll();
					//if changePassword button is in 'selected' state, show more information about the recovery
					customizeChangePasswordPanelWhenButtonIsPressed();
				}
				
				else if (e.getSource() == settings && e.getStateChange() == ItemEvent.SELECTED)
				{
					personalInformationCenterFinalPanel.removeAll();
					//if settings button is in 'selected' state, show more information about what is the user able to do
					customizeFirstSettingsPanel();
				}
				
				else if (e.getSource() == statistics && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//the border must be explicity removed
					transactionsCenterFinalPanel.setBorder(BorderFactory.createEmptyBorder());
					
					//if statistics button is in 'selected' state, show more information about statistics
					getTransactionsCountForStatistics();
					customizeStatisticsCountPanelWhenButtoninIsPressed();
				}
				
				else if (e.getSource() == deposit && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//the border must be explicity removed
					transactionsCenterFinalPanel.setBorder(BorderFactory.createEmptyBorder());
					
					//if deposit button is in 'selected' state, show more information about the deposit
					customizeDepositPanelWhenButtonIsPressed();
				}
				
				else if(e.getSource() == withdraw && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//the border must be explicity removed
					transactionsCenterFinalPanel.setBorder(BorderFactory.createEmptyBorder());
					
					//if withdraw button is in 'selected' state, show more information about the withdraw
					customizeWithdrawPanelWhenButtonIsPressed();
				}
				
				else if (e.getSource() == transferTo && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//the border must be explicity removed
					transactionsCenterFinalPanel.setBorder(BorderFactory.createEmptyBorder());
					
					//if transferTo button is in 'selected' state, show more information about the transfer
					customizeTransferPanelWhenButtonIsPressed();
				}
				
				else if (e.getSource() == transactionsHistory && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//if history button is in 'selected' state, show more information about the transactions history
					getTransactionsHistory(username.getText());
					customizeHistoryPanelWhenButtonIsPressed();
				}
			}			      
		};
		
		//add listeners for buttons
		updateUserInfo.addItemListener(listener);
		changePassword.addItemListener(listener);
		settings.addItemListener(listener);
		logOut.addActionListener(this);
		deposit.addItemListener(listener);
		withdraw.addItemListener(listener);
		transferTo.addItemListener(listener);
		transactionsHistory.addItemListener(listener);
		statistics.addItemListener(listener);
		
		//add the panels to the frame
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.WEST);
		getContentPane().add(center, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
		
		//set focus on tabbed component for going through its components using arrows
		tabbedPane.requestFocus();
		
		pack();
		show();		
	}

	@SuppressWarnings("deprecation")
	//customize the register page
	public void createRegisterPage()
	{
		//remove all the elements from the frame and validate it
		getContentPane().removeAll();
		menuBar.removeAll();
		setPreferredSize(new Dimension(730, 340));
		validate();
			
		//set layout
		getContentPane().setLayout(new BorderLayout());	
		
		//create a header
		JLabel header = new JLabel("Register a new account");
		header.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		Border headerBorder = BorderFactory.createTitledBorder("");
		header.setBorder(headerBorder);
		JPanel headerPanel = new JPanel();
		headerPanel.setPreferredSize(new Dimension(275, 50));
		headerPanel.add(header);
		getContentPane().add(headerPanel, BorderLayout.NORTH);	
				
		//create and customize required fields for registration
		usernameR = new JTextField(10);
		firstNameR = new JTextField(10);
		lastNameR = new JTextField(10);
		passwordR = new JPasswordField(10);
		emailR = new JTextField(10);
		
		//days, months and years for completing date of birth
		String[] days = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				          "11", "12", "13", "14", "15", "16", "17", "18", 
				          "19", "20", "21", "22", "23", "24", "25", "26", 
				          "27", "28", "29", "30", "31"
				        };
		
		String[] months = { "January", "February", "March", "April", "May", 
							"June", "July", "August", "September",  "October", 
							"November", "December"
						  };
		
		String [] years = { "1998", "1997", "1996", "1995", "1994", "1993", 
							"1992", "1991", "1990", "1989","1988", "1988", 
							"1987", "1986", "1985", "1984", "1983", "1982", 
							"1981", "1980", "1979", "1978", "1977", "1976", 
							"1975", "1974", "1973", "1972", "1971", "1970", 
						  };
									
		//combo boxes for each for days, month, years
		daysList = new JComboBox<Object>(days);
		daysList.setSelectedIndex(0);
		daysList.setEditable(true);
		monthsList = new JComboBox<Object>(months);
		daysList.setSelectedIndex(0);
		monthsList.setEditable(true);
		yearsList = new JComboBox<Object>(years);
		daysList.setSelectedIndex(0);
		yearsList.setEditable(true);
		
		JPanel usernameRPanel = new JPanel();
		JPanel firstNameRPanel = new JPanel();
		JPanel lastNameRPanel = new JPanel();
		JPanel passwordRPanel = new JPanel();
		JPanel emailRPanel = new JPanel();
		JPanel genreRPanel = new JPanel();
		JPanel daysListPanel = new JPanel();
		JPanel monthsListPanel = new JPanel();
		JPanel yearsListPanel = new JPanel();
		
		//create a separate panel for birth date
		JPanel dayMonthYearPanel = new JPanel();
		dayMonthYearPanel.setLayout(new FlowLayout());
		
		//create a separate panel for genre
		JPanel maleAndFemaleOptionsPanel = new JPanel();
		maleOption = new JRadioButton("Male");
		femaleOption = new JRadioButton("Female");
		ButtonGroup bg = new ButtonGroup();
		maleAndFemaleOptionsPanel.add(maleOption);
		maleAndFemaleOptionsPanel.add(femaleOption);
		bg.add(maleOption);
		bg.add(femaleOption);
		
		usernameRPanel.add(new JLabel(" Username:"));
		usernameRPanel.add(usernameR);
		firstNameRPanel.add(new JLabel("First name:"));
		firstNameRPanel.add(firstNameR);
		lastNameRPanel.add(new JLabel("Last Name:"));
		lastNameRPanel.add(lastNameR);
		passwordRPanel.add(new JLabel(" Password:"));
		passwordRPanel.add(passwordR);
		emailRPanel.add(new JLabel("       E-mail: "));
		emailRPanel.add(emailR);
		genreRPanel.add(new JLabel("Genre: "));
		genreRPanel.add(maleAndFemaleOptionsPanel);
		daysListPanel.add(new JLabel("Day"));
		daysListPanel.add(daysList);
		monthsListPanel.add(new JLabel("Month"));
		monthsListPanel.add(monthsList);
		yearsListPanel.add(new JLabel(" Year"));
		yearsListPanel.add(yearsList);
		
		//add birth date details panels to another panel
		dayMonthYearPanel.add(daysListPanel);
		dayMonthYearPanel.add(monthsListPanel);
		dayMonthYearPanel.add(yearsListPanel);
		
		//create a final panel for birth date option - design purpose
		JPanel dayMonthYearPanelFinal = new JPanel();
		dayMonthYearPanelFinal.add(new JLabel ("Date of birth:"));
		dayMonthYearPanelFinal.add(dayMonthYearPanel);
		
		//add some of the panels above in the west side
		JPanel allInOneWest = new JPanel();
		allInOneWest.setLayout(new GridLayout(5, 1)); 
		allInOneWest.add(usernameRPanel);
		allInOneWest.add(passwordRPanel);
		allInOneWest.add(firstNameRPanel);
		allInOneWest.add(lastNameRPanel);
		allInOneWest.add(emailRPanel);
		
		//add some of the panels above in the east side
		JPanel allInOneEast = new JPanel();
		allInOneEast.setLayout(new GridLayout(3, 1)); 
		allInOneEast.add(dayMonthYearPanelFinal);
		allInOneEast.add(genreRPanel);
	
		//set a border on the allInOneWest panel
		allInOneWest.setBorder(BorderFactory.createTitledBorder("Mandatory details"));
		
		//set a border on the allInOneEast panel
		allInOneEast.setBorder(BorderFactory.createTitledBorder("Optional details"));	
				
		//create 'confirm' and 'cancel' buttons and add them to a panel
		ImageIcon confirmIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\confirm icon.png");
		confirm = new JButton("Confirm");
		confirm.setIcon(confirmIcon);
		
		ImageIcon cancelIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\cancel icon.png");
		cancel = new JButton("Cancel");
		cancel.setIcon(cancelIcon);
		JPanel confirmAndCancel = new JPanel();
		confirmAndCancel.add(confirm);
		confirmAndCancel.add(cancel);
		
		//set a border to confirmAndCancel panel
		Border confirmAndCancelBorder = BorderFactory.createTitledBorder("Options");
		confirmAndCancel.setBorder(confirmAndCancelBorder);
		
		//add the confirmAndCancel panel in allInOne Panel
		JPanel confirmAndCancelFinal = new JPanel();
		confirmAndCancelFinal.add(confirmAndCancel);
		
		//add allInOne panel to a new one to avoid redimension issues
		JPanel finalAllInOne = new JPanel();
		finalAllInOne.setLayout(new FlowLayout());
		finalAllInOne.add(allInOneWest);
		finalAllInOne.add(allInOneEast);
		
		//add finalAllInOne border to the frame
		getContentPane().add(finalAllInOne, BorderLayout.CENTER);
		getContentPane().add(confirmAndCancelFinal, BorderLayout.SOUTH);
		
		//add listeners for buttons
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		maleOption.addActionListener(this);
		femaleOption.addActionListener(this);	
		
		pack();
		show();
	}
	
	@SuppressWarnings("deprecation")
	//customize 'lost password' page
	public void createLostPasswordPage()
	{
		//remove all the elements from the current frame
		getContentPane().removeAll();
		menuBar.removeAll();
		setPreferredSize(new Dimension(450, 390));
		validate();
		
		//set layout
		setLayout(new BorderLayout());
		
		//set header
		JLabel header = new JLabel("Recover password");
		header.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		Border headerBorder = BorderFactory.createTitledBorder("");
		header.setBorder(headerBorder);
		JPanel headerPanel = new JPanel();
		headerPanel.setPreferredSize(new Dimension(275, 50));
		headerPanel.add(header);
		getContentPane().add(headerPanel, BorderLayout.NORTH);	

		//create and customize required fields for recovering the password
		usernameLP = new JTextField(10);
		JPanel usernameLPPanel = new JPanel();
		usernameLPPanel.add(new JLabel("Username:"));
		usernameLPPanel.add(usernameLP);
	
		//add all the panels above in one panel
		allInOne = new JPanel();
		allInOne.setLayout(new GridLayout(2, 1));
		
		//set a border on the allInOne panel
		Border usernameLPPanelBorder = BorderFactory.createTitledBorder("Details");
		allInOne.setBorder(usernameLPPanelBorder);	
		
		//create 'done' button and add it to a panel
		ImageIcon doneIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\done icon.png");
		done = new JButton("Done");
		done.setIcon(doneIcon);
		
		ImageIcon backIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\back icon.png");
		back = new JButton("Back");
		back.setIcon(backIcon);
		JPanel doneAndBackPanel = new JPanel();
		doneAndBackPanel.add(done);
		doneAndBackPanel.add(back);

		//add the 2 panels created above to a 3rd one
		allInOne.add(usernameLPPanel);
		allInOne.add(doneAndBackPanel);
		
		//add allInOne panel to a new one to avoid redimension issues
		JPanel finalAllInOne = new JPanel();
		finalAllInOne.add(allInOne);
	
		//create a new JPanel 'whole' for further use in lostPassword() function
		whole = new JPanel();
		whole.setLayout(new GridLayout(3, 1));
		whole.add(finalAllInOne);

		//add listeners for buttons
		back.addActionListener(this);
		done.addActionListener(this);
		usernameLP.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					lostPassword();
				}
			}
		});
		
		//add whole panel to the frame
		getContentPane().add(whole, BorderLayout.CENTER);
		
		pack();
		show();	
	}
	
	@SuppressWarnings("deprecation")
	//checks the registration form so the text field contain only allowed characters
	public boolean checkRegisterForm()
	{
		boolean isAnyFieldEmpty = false;
		
		//first of all, check if the desired username already exists in database
		Connection connection = null;
		try
		{
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			String sqlCheckIfUsernameAlreadyExists = "SELECT clientid FROM clients WHERE username like '" 
													+ usernameR.getText() 
													+ "'";

			Statement stmtCheckIfUsernameAlreadyExists = connection.createStatement();
			ResultSet rsCheckIfUsernameAlreadyExists = stmtCheckIfUsernameAlreadyExists.executeQuery(sqlCheckIfUsernameAlreadyExists);

			if (rsCheckIfUsernameAlreadyExists.next())
			{
				JOptionPane.showMessageDialog(null,
						  "The username already exists in database !",
						  "Warning",
						  JOptionPane.WARNING_MESSAGE);			
				return false;
			}
		} 
		catch (SQLException e) 
		{
			
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

		//check if the typed username has at least 1 digit and 1 letter
		if (!usernameR.getText().matches("((\\d+)([a-zA-Z]+))+(([a-zA-Z]*)(\\d*))*"
									   + "|"
									   + "(([a-zA-Z]+)(\\d+))+(([a-zA-Z]*)(\\d*))*"
									   +"|"
									   + "(\\d+)+(([a-zA-Z]*)(\\d*))*"
									   + "|"
									   + "([a-zA-Z]+)+(([a-zA-Z]*)(\\d*))*"
									    ))
		{
			JOptionPane.showMessageDialog(null,
										  "The username must contain at least: \n\n"
									    + "> 1 digit (Ex.: 0 1 2 3 4 5 6 7 8 9)\n"
										+ "> OR\n"
									    + "> 1 letter (Ex.: a b c d e f g h ... )\n\n"
									    + "Also, the username must contain ONLY patterns mentioned above.",
										  "Warning",
									      JOptionPane.WARNING_MESSAGE);	
			return false;
		}	
		
		//check if the typed password has at least 1 digit, 1 letter and 1 symbol
		if (passwordR.getText().length() > 0 && !passwordR.getText().matches(
											"(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)((\\d+)([a-zA-Z0-9]+)))"
										  + "(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)((\\d*)([a-zA-Z0-9]*)))*"
										  + "|"
										  + "(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)(([a-zA-Z0-9]+)(\\d+)))"
										  + "(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)(([a-zA-Z0-9]*)(\\d*)))*"
										  + "|"
										  + "(((\\d+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)([a-zA-Z0-9]+)))"
										  + "(((\\d*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)([a-zA-Z0-9]*)))*"
										  + "|"
										  + "(((\\d+)([a-zA-Z0-9]+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)))"
										  + "(((\\d*)([a-zA-Z0-9]*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)))*"
										  + "|"
										  + "((([a-zA-Z0-9]+)(\\d+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)))"
										  + "((([a-zA-Z0-9]*)(\\d*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)))*"
										  + "|"
										  + "((([a-zA-Z0-9]+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)(\\d+)))"
										  + "((([a-zA-Z0-9]*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)(\\d*)))*"
																			))
		{
			JOptionPane.showMessageDialog(null,
										  "The password must contain at least: \n\n"
										+ "> 1 digit (Ex.: 0 1 2 3 4 5 6 7 8 9)\n"
										+ "> 1 letter (Ex.: a b c d e f g h ... )\n"
										+ "> 1 symbol (Ex.: @ # $ % % ... )\n\n"
										+ "Also, the password must contain ONLY patterns mentioned above.",
										  "Warning",
										  JOptionPane.WARNING_MESSAGE);
			return false;
		}	
		
		//check if the typed e-mail address is valid
		if (!emailR.getText().matches("\\b[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}\\b"))
		{
			JOptionPane.showMessageDialog(null,
									   	  "Error in e-mail format.  \n\n"
									   	+ "> Ex.: username@gmail.com\n\n"
									   	+ "Please properly rewrite yor e-mail address.",
									      "Warning",
									   	  JOptionPane.WARNING_MESSAGE);	
			return false;
		}
		
		
		//check typed username length
		if (usernameR.getText().length() == 0)
		{
			usernameR.setBorder(BorderFactory.createLineBorder(Color.RED));			
			isAnyFieldEmpty = true;
		}
		
		//check typed password length
		if (passwordR.getText().length() == 0)
		{
			passwordR.setBorder(BorderFactory.createLineBorder(Color.RED));
			isAnyFieldEmpty = true;
		}
		
		//check typed first name length
		if (firstNameR.getText().length() == 0)
		{
			firstNameR.setBorder(BorderFactory.createLineBorder(Color.RED));
			isAnyFieldEmpty = true;
		}
		
		//check typed last name length
		if (lastNameR.getText().length() == 0)
		{
			lastNameR.setBorder(BorderFactory.createLineBorder(Color.RED));
			isAnyFieldEmpty = true;
		}
		
		//check typed email length
		if (emailR.getText().length() == 0)
		{
			emailR.setBorder(BorderFactory.createLineBorder(Color.RED));
			isAnyFieldEmpty = true;
		}
		
		//check if day of birth is valid
		if (!((String)daysList.getSelectedItem()).matches("\\b([1-9])|([1|2][0-9])|([3][0|1])\\b"))
		{		
			JOptionPane.showMessageDialog(null,
										  "Invalid day of birth. The day of birth must be between 1 and 31 !",
										  "Warning",
										  JOptionPane.WARNING_MESSAGE);
			return false;
		}
	
		//check if month of birth is valid
		if(!(
			((String)monthsList.getSelectedItem()).equals("January") ||
			((String)monthsList.getSelectedItem()).equals("February") ||
			((String)monthsList.getSelectedItem()).equals("March") ||
			((String)monthsList.getSelectedItem()).equals("April") ||
			((String)monthsList.getSelectedItem()).equals("May") ||
			((String)monthsList.getSelectedItem()).equals("June") ||
			((String)monthsList.getSelectedItem()).equals("July") ||
			((String)monthsList.getSelectedItem()).equals("August") ||
			((String)monthsList.getSelectedItem()).equals("September") ||
			((String)monthsList.getSelectedItem()).equals("October") ||
			((String)monthsList.getSelectedItem()).equals("November") ||
			((String)monthsList.getSelectedItem()).equals("December")
			)) 
			{
				JOptionPane.showMessageDialog(null,
							  				  "Invalid month !",
							  				  "Warning",
							  				  JOptionPane.WARNING_MESSAGE);
					return false;				
			}
		
		//check if year of birth is valid 1
		if (!(((String)yearsList.getSelectedItem()).matches("([1][8][0-9][0-9])|([1][9][9][0-8])|([1][9][0-8][0-9])")))
		{
			JOptionPane.showMessageDialog(null,
										  "Invalid year of birth. The year of birth must be between 1800 and 1998 !",
					 					  "Warning",
					 					  JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		//check if year of birth is valid 2
		if (Integer.parseInt((String)yearsList.getSelectedItem()) > 1998)
		{
			JOptionPane.showMessageDialog(null,
										 "You must be at least 18 years old in order to make a new account !",
					  				   	 "Warning",
					  				   	 JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
			
		if (isAnyFieldEmpty)
		{
			JOptionPane.showMessageDialog(null,
										  "Please complete all the required fields.",
										  "Warning",
										  JOptionPane.WARNING_MESSAGE);			
			return false;
		}	
		return true;
	}
	
	//checks the birth date change formul so the text field contain only allowed characters
	private boolean checkChangeBirthdateForm()
	{
		//check if day of birth is valid
		if (!((String)daysListCBD.getSelectedItem()).matches("\\b([1-9])|([1|2][0-9])|([3][0|1])\\b"))
		{
					JOptionPane.showMessageDialog(null,
										  "Invalid day of birth. The day of birth must be between 1 and 31 !",
										  "Warning",
										  JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		//check if month of birth is valid
		if(!(
			((String)monthsListCBD.getSelectedItem()).equals("January") ||
			((String)monthsListCBD.getSelectedItem()).equals("February") ||
			((String)monthsListCBD.getSelectedItem()).equals("March") ||
			((String)monthsListCBD.getSelectedItem()).equals("April") ||
			((String)monthsListCBD.getSelectedItem()).equals("May") ||
			((String)monthsListCBD.getSelectedItem()).equals("June") ||
			((String)monthsListCBD.getSelectedItem()).equals("July") ||
			((String)monthsListCBD.getSelectedItem()).equals("August") ||
			((String)monthsListCBD.getSelectedItem()).equals("September") ||
			((String)monthsListCBD.getSelectedItem()).equals("October") ||
			((String)monthsListCBD.getSelectedItem()).equals("November") ||
			((String)monthsListCBD.getSelectedItem()).equals("December")
			)) 
		{
				JOptionPane.showMessageDialog(null,
							  				  "Invalid month !",
							  				  "Warning",
							  				  JOptionPane.WARNING_MESSAGE);
					return false;				
		}
		
		//check if year of birth is valid 1
		if (!((String)yearsListCBD.getSelectedItem()).matches("\\b([1][8][0-9][0-9])|([1][9][9][0-8])|([1][9][0-8][0-9])\\b"))
		{
			JOptionPane.showMessageDialog(null,
										  "Invalid year of birth. The year of birth must be between 1800 and 1998 !",
					 					  "Warning",
					 					  JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		//check if year of birth is valid 2
		if (Integer.parseInt((String)yearsListCBD.getSelectedItem()) > 1998)
		{		
			JOptionPane.showMessageDialog(null,
										 "You must be at least 18 years old in order to make a new account !",
					  				   	 "Warning",
					  				   	 JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;	
	}
	
	//get the greatest client ID avalaible in database - useful when a new user is registering,
	//just to know what client ID we asign to that new one user
	public void getMaxClientID()
	{
		Connection connection = null;
		
		try 
		{	
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			Statement stmt = connection.createStatement();
			String sqlgetMaxClientID = "SELECT max(clientid) FROM clients";
			ResultSet rs = stmt.executeQuery(sqlgetMaxClientID);
				
			//extract the max id
			if (rs.next()) 
			{
				clientID = rs.getInt(1);
			}
		} 	
		catch(SQLException e) 
		{

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
	
	@SuppressWarnings("finally")
	//get the current logged in user ID
	public int getUserID(String usernameID)
	{
		Connection connection = null;
		int currentUserID = 0;
		
		try 
		{				
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//get current user ID for further use
			String sqlgetCurrentUserID = "SELECT clientid from clients WHERE username LIKE ?";
			PreparedStatement stmtCurrentUserID = connection.prepareStatement(sqlgetCurrentUserID);
			stmtCurrentUserID.setString(1, username.getText());
			ResultSet rsCurrentUserID = stmtCurrentUserID.executeQuery();

			
			if (rsCurrentUserID.next())
			{
				currentUserID = rsCurrentUserID.getInt("ClientID");
			}
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
			return currentUserID;
		}
	}

	
	//get the informations + photo for the current logged in user from database, and display them
	private void getUserInfoFromDatabase()
	{
		setPreferredSize(new Dimension(490, 380));
		
		Connection connection = null;	
		allInOneWestFinal = new JPanel();
		allInOneWestFinal.setLayout(new GridLayout(2, 1));
		JPanel allInOneCenterFinal = new JPanel();
		String currentUserGenre = "";
			
		try 
		{	
			personalInformationCenterFinalPanel.removeAll();
			personalInformationCenterFinalPanel.repaint();
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			Statement stmt = connection.createStatement();
			String sqlQuerry = "SELECT * FROM CLIENTS where username like '" + username.getText() +"'";
			ResultSet rs = stmt.executeQuery(sqlQuerry);
				
			//extract all the info from the current logged in user and show them
			while (rs.next()) 
			{			
				//create new labels for user's info
				JLabel userName = new JLabel("Username: " + rs.getString("Username"));
				JLabel firstName = new JLabel("First name: " + rs.getString("Firstname"));
				JLabel lastName = new JLabel("Last name: " + rs.getString("Lastname"));
				JLabel genre = new JLabel("Genre: " + rs.getString("Genre"));
				JLabel eMail = new JLabel("E-mail: " + rs.getString("email"));
				JLabel birthDate= new JLabel("Birth date: " + rs.getString("birthdate"));
				JLabel balance1 = new JLabel(rs.getString("Balance"));
				JLabel balance2 = new JLabel("Balance");
				
				//we store the current user's genre once more for further use in user's photos section,
				//to set an avatar regarding whether he is a he / she
				currentUserGenre = rs.getString("Genre");
				
				//customize balance1 panel
				ImageIcon balance1Icon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\balance icon.png");
				balance1.setIcon(balance1Icon);
				
				//customize balance2 panel
				balance2.setFont(new Font("Arial", Font.BOLD, 20));
				
				JPanel balance1Panel = new JPanel();
				JPanel balance2Panel = new JPanel();

				balance1Panel.add(balance1);
				balance2Panel.add(balance2);
				
				//set border
				balance1Panel.setBorder(BorderFactory.createTitledBorder(""));
							
				JPanel allInOneWest = new JPanel();
				JPanel allInOneCenter = new JPanel();				
				allInOneWest.setLayout(new GridLayout(6, 1));
				allInOneCenter.setLayout(new GridLayout(4, 1));
				
				//add the panels constructed above to a new customized one (design purpose) - west side
				allInOneWest.add(userName);
				allInOneWest.add(firstName);
				allInOneWest.add(lastName);
				allInOneWest.add(genre);
				allInOneWest.add(eMail);
				allInOneWest.add(birthDate);
				
				//add the panels constructed above to a new customized one (design purpose) - center side
				allInOneCenter.add(balance2Panel); 
				allInOneCenter.add(balance1Panel);
				
				//set border of the customized panels - west
				allInOneWest.setBorder(BorderFactory.createTitledBorder("Info"));
				
				//add the customized panel to a final panel (design purpose)
				allInOneWestFinal = new JPanel();
				allInOneWestFinal.setLayout(new GridLayout(2, 1));
				allInOneCenterFinal = new JPanel();
				allInOneWestFinal.add(allInOneWest);
				allInOneCenterFinal.add(allInOneCenter);
			}
			
			//another sql statement, this time for getting the profile photo of the current user 
			String sqlGetUserPhoto = "SELECT image FROM users_photos WHERE username LIKE ?";
	    	PreparedStatement pstmtGetUserPhoto = connection.prepareStatement(sqlGetUserPhoto);
	    	pstmtGetUserPhoto.setString(1, username.getText());
	        ResultSet rsGetUserPhoto = pstmtGetUserPhoto.executeQuery();
	        boolean doesCurrentUserHaveAvatar = false;
	        
	        while (rsGetUserPhoto.next()) 
	        {
	        	doesCurrentUserHaveAvatar = true;
	        	
	        	//first we get the image from table
	            InputStream in = rsGetUserPhoto.getBinaryStream("image");
	            
	            //then we convert it
	            BufferedImage  bufImg = ImageIO.read(in); 
	            
	            //after that, we put it in its destined label
				JLabel userPhoto = new JLabel();
				userPhoto.setIcon(new ImageIcon(bufImg));
				JPanel userPhotoPanel = new JPanel();
				userPhotoPanel.add(userPhoto);
				userPhotoPanel.setBorder(BorderFactory.createTitledBorder("Photo"));
				allInOneWestFinal.add(userPhotoPanel);
	        }
	        
	        //we check if the current user does not have a photo;
	        //if he doesn't, we set a default photo regarding his / her genre
	        if (!doesCurrentUserHaveAvatar)
	        {
	        	if (username.getText().equals("admin"))
	        	{
	        		
	        	}
	        	else if (currentUserGenre.equals("M"))
	        	{
		        	JLabel userPhoto = new JLabel();
					userPhoto.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\male user icon.png"));
					JPanel userPhotoPanel = new JPanel();
					userPhotoPanel.add(userPhoto);
					userPhotoPanel.setBorder(BorderFactory.createTitledBorder("Photo"));
					allInOneWestFinal.add(userPhotoPanel);
	        	}
	        	else if(currentUserGenre.equals("F"))
	        	{
		        	JLabel userPhoto = new JLabel();
					userPhoto.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\female user icon.png"));
					JPanel userPhotoPanel = new JPanel();
					userPhotoPanel.add(userPhoto);
					userPhotoPanel.setBorder(BorderFactory.createTitledBorder("Photo"));
					allInOneWestFinal.add(userPhotoPanel);
	        	}
	        	else
	        	{
		        	JLabel userPhoto = new JLabel();
					userPhoto.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\no profile picture icon.png"));
					JPanel userPhotoPanel = new JPanel();
					userPhotoPanel.add(userPhoto);
					userPhotoPanel.setBorder(BorderFactory.createTitledBorder("Photo"));
					allInOneWestFinal.add(userPhotoPanel);
	        	}
	        }
	        
	        //set border of the final panel (design purpose)
			allInOneWestFinal.setBorder(BorderFactory.createTitledBorder(" "));
			allInOneCenterFinal.setBorder(BorderFactory.createTitledBorder(" "));
			
			//add the final panel to another (design purpose)
			personalInformationCenterFinalPanel.setLayout(new BorderLayout());
			personalInformationCenterFinalPanel.add(allInOneWestFinal, BorderLayout.WEST);
			personalInformationCenterFinalPanel.add(allInOneCenterFinal, BorderLayout.CENTER);
			
			//add the final panel to another which represents the center of the current page
			personalInformationTab.add(personalInformationCenterFinalPanel, BorderLayout.CENTER);

			revalidate();
		} 	
		
		catch(SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
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
	
	//customize the panel designed for changing the user's password
	public void customizeChangePasswordPanelWhenButtonIsPressed()
	{
		//set main panel of the center side of the 'personal information' tab
		personalInformationCenterBorder.setLayout(new GridLayout(1, 1));		
		
		//create and customize text field for the current password
		currentPasswordCP = new JPasswordField(10);
		currentPasswordCP.setToolTipText("Type here your current password.");
		JLabel currentPasswordLabel = new JLabel("Password: ");
		JPanel currentPasswordPanel = new JPanel();
		currentPasswordPanel.add(currentPasswordLabel);
		currentPasswordPanel.add(currentPasswordCP);
		
		//create and customize text field for the new password
		newPasswordCP = new JPasswordField(10);
		newPasswordCP.setToolTipText("Type here your new desired password.");
		JLabel changePasswordLabel = new JLabel("New pass: ");
		JPanel changePasswordPanel = new JPanel();
		changePasswordPanel.add(changePasswordLabel);
		changePasswordPanel.add(newPasswordCP);
		
		//create 'submit' button and add it to a panel
		submitChangePassword = new JButton("Submit   ");
		ImageIcon submitIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\submit icon.png");
		submitChangePassword.setIcon(submitIcon);
		JLabel submitLabel = new JLabel("                  ");
		JPanel submitPanel = new JPanel();
		submitPanel.add(submitLabel);
		submitPanel.add(submitChangePassword);
				
		JLabel changePasswordInfo = new JLabel("                          Complete the following form: ");
		changePasswordFinalPanel = new JPanel();
		changePasswordFinalPanel.setLayout(new GridLayout(7, 1));
		changePasswordFinalPanel.add(changePasswordInfo);
		changePasswordFinalPanel.add(currentPasswordPanel);
		changePasswordFinalPanel.add(changePasswordPanel);
		changePasswordFinalPanel.add(submitPanel);
		changePasswordFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		personalInformationCenterFinalPanel.setLayout(new GridLayout(1, 1));
		personalInformationCenterFinalPanel.add(changePasswordFinalPanel);
		
		//add listeners for buttons
		submitChangePassword.addActionListener(this);
		
		personalInformationTab.add(personalInformationCenterFinalPanel, BorderLayout.CENTER);
		
		revalidate();
	}
	
	//customize panel for visualising the statistics
	public void customizeStatisticsCountPanelWhenButtoninIsPressed()
	{	
		transactionsCenterFinalPanel.setLayout(new BorderLayout());
		//center side of then main panel
		JPanel statisticsPanelCenter = new JPanel();
		statisticsPanelCenter.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

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
		
		
		//first background image
		try 
		{
			plot.setBackgroundImage(ImageIO.read(new File("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
														+ "[OP]Bank\\icons\\statistics background images\\"
														+ "number of transactions statistics.jpg")));
		} catch (IOException e) 
		{

		}
		
		//second background image
		try 
		{
		    chart.setBackgroundImage(ImageIO.read(new File("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
		    											 + "[OP]Bank\\icons\\statistics background images\\"
		    											 + "number of transactions statistics.jpg")));
		} catch (IOException ex) 
		{
		
		}
		

	    //create a timer for animation
	    rotate3DPieChart = new Timer(150, new ActionListener()
	    {
	        public void actionPerformed(ActionEvent ae)
	        {
	            //set the start angle, this increases everytime timer is executed
	            plot.setStartAngle(pieChartThreadIterator = pieChartThreadIterator + 1.0);               
	        }
	    });
	    
		//south side
		statisticsPanelSouth = new JPanel();
		statisticsPanelSouth.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		//create south buttons
		transactionsCount = new JToggleButton("Count");
		statisticsPanelSouth.add(transactionsCount);
		
		transactionsValues = new JToggleButton("Values");
		statisticsPanelSouth.add(transactionsValues);
		
		ItemListener listener = new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{	
				if (e.getSource() == transactionsCount && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//the border must be explicity removed
					transactionsCenterFinalPanel.setBorder(BorderFactory.createEmptyBorder());
					
					customizeStatisticsCountPanelWhenButtoninIsPressed();
				}
				if (e.getSource() == transactionsValues && e.getStateChange() == ItemEvent.SELECTED)
				{
					transactionsCenterFinalPanel.removeAll();
					
					//the border must be explicity removed
					transactionsCenterFinalPanel.setBorder(BorderFactory.createEmptyBorder());
					
					customizeStatisticsAmountPanelWhenButtonIsPressed();
				}
		
			}
		};
		
		transactionsCount.addItemListener(listener);
		transactionsValues.addItemListener(listener);
		
		transactionsCenterFinalPanel.add(chartPanel, BorderLayout.CENTER);
		transactionsCenterFinalPanel.add(statisticsPanelSouth, BorderLayout.SOUTH);
		
		transactions.add(transactionsCenterFinalPanel, BorderLayout.CENTER);
		
	    //start the timer (animation starts!)
	    rotate3DPieChart.start();	
	    isThreadRunning = true;
		
		revalidate();
	}
	
	private void customizeStatisticsAmountPanelWhenButtonIsPressed()
	{
		//stop the thread which shows statistics in a 3D pie chart manner
		if (isThreadRunning)
		{
			rotate3DPieChart.stop();
		}
		
		//set main panel of the center side of the 'transactions' tab
		transactionsCenterFinalPanel.setLayout(new BorderLayout());
		
		int clientID = getUserID(username.getText());
		LineChartTransactions L = new LineChartTransactions("da", clientID);
	
		L.chartPanelLine.setPreferredSize(new Dimension(315, 195));
		L.chartPanelLine.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

	 	//declare a pieplot so we can customize the chart
		XYPlot plot = (XYPlot)L.lineChart.getXYPlot();
	 	plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
	 	plot.setForegroundAlpha(0.7f);
	 	plot.setNoDataMessage("Not enough data to display.");
	 	
	 	
		//first background image
		try 
		{
		    plot.setBackgroundImage(ImageIO.read(new File("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
		    											 + "[OP]Bank\\icons\\statistics background images\\"
		    											 + "number of transactions statistics.jpg")));
		} catch (IOException ex) 
		{
		
		}
		
		//second background image
		try 
		{
		    L.lineChart.setBackgroundImage(ImageIO.read(new File("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\"
		    											 + "[OP]Bank\\icons\\statistics background images\\"
		    											 + "number of transactions statistics.jpg")));
		} catch (IOException ex) 
		{
		
		}
	 	
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        plot.setRenderer(renderer);
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        
			
		JPanel statisticsPanel = new JPanel(new BorderLayout());
		statisticsPanel.add(L.chartPanelLine);
			
		
		transactionsCenterFinalPanel.add(statisticsPanel, BorderLayout.CENTER);
		transactionsCenterFinalPanel.add(statisticsPanelSouth, BorderLayout.SOUTH);
		
		transactions.add(transactionsCenterFinalPanel, BorderLayout.CENTER);
		
		revalidate();
	}
	
	//customize the panel designed for depositing money
	public void customizeDepositPanelWhenButtonIsPressed()
	{	  
		//stop the thread which shows statistics in a 3D pie chart manner
		if (isThreadRunning)
		{
			rotate3DPieChart.stop();
		}
		
		//set main panel of the center side of the 'transactions' tab
		transactionsCenterFinalPanel.setLayout(new GridLayout(1, 1));
				
		//create and customize text field for the amount to be deposited
		depositMoneyAmount = new JTextField(10);
		depositMoneyAmount.setToolTipText("Type here the amount of money you want to deposit.");
		JLabel transferMoneyLabel = new JLabel("    Amount: ");
		JPanel depositMoneyAmountPanel = new JPanel();
		depositMoneyAmountPanel.add(transferMoneyLabel);
		depositMoneyAmountPanel.add(depositMoneyAmount);
		
		//create 'submit' button and add it to a panel
		submitDeposit = new JButton("Submit   ");
		ImageIcon submitIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\submit icon.png");
		submitDeposit.setIcon(submitIcon);
		JLabel submitLabel = new JLabel("                  ");
		JPanel submitPanel = new JPanel();
		submitPanel.add(submitLabel);
		submitPanel.add(submitDeposit);
				
		JLabel transferInfo = new JLabel("                             Complete the following form: ");
		depositFinalPanel = new JPanel();
		depositFinalPanel.setLayout(new GridLayout(7, 1));
		transferInfo.setText("            Set here the amount of money to be deposited:");
		depositFinalPanel.add(transferInfo);
		depositFinalPanel.add(depositMoneyAmountPanel);
		depositFinalPanel.add(submitPanel);
		depositFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		transactionsCenterFinalPanel.add(depositFinalPanel);
		
		//add listeners for buttons
		submitDeposit.addActionListener(this);
		
		transactions.add(transactionsCenterFinalPanel, BorderLayout.CENTER);
		
		revalidate();
	}
	
	//customize the panel designed for transfering money
	public void customizeTransferPanelWhenButtonIsPressed()
	{
		//stop the thread which shows statistics in a 3D pie chart manner
		if (isThreadRunning)
		{
			rotate3DPieChart.stop();
		}
		
		//set main panel of the center side of the 'transactions' tab
		transactionsCenterFinalPanel.setLayout(new GridLayout(1, 1));
		
		//create and customize text field for the destination user who receives the money
		userToTransferMoneyTo = new JTextField(10);
		userToTransferMoneyTo.setToolTipText("Type here the username you want to send money to.");
		JLabel userToLabel = new JLabel("Username: ");
		JPanel userToTransferMoneyToPanel = new JPanel();
		userToTransferMoneyToPanel.add(userToLabel);
		userToTransferMoneyToPanel.add(userToTransferMoneyTo);
		
		//create and customize text field for the amount to be transfered
		transferMoneyAmount = new JTextField(10);
		transferMoneyAmount.setToolTipText("Type here the amount of money you want to transfer.");
		JLabel withdrawMoneyLabel = new JLabel("    Amount: ");
		JPanel transferMoneyAmountPanel = new JPanel();
		transferMoneyAmountPanel.add(withdrawMoneyLabel);
		transferMoneyAmountPanel.add(transferMoneyAmount);
		
		//create transfer button and add it to a panel
		submitTransfer = new JButton("Submit   ");
		ImageIcon submitTransferIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\perform icon.png");
		submitTransfer.setIcon(submitTransferIcon);
		JLabel submitTransferLabel = new JLabel("                  ");
		JPanel submitTransferPanel = new JPanel();
		submitTransferPanel.add(submitTransferLabel);
		submitTransferPanel.add(submitTransfer);
				
		JLabel transferInfo = new JLabel("                             Complete the following form: ");
		destinationUserAndAmountFinalPanel = new JPanel();
		destinationUserAndAmountFinalPanel.setLayout(new GridLayout(7, 1));
		destinationUserAndAmountFinalPanel.add(transferInfo);
		destinationUserAndAmountFinalPanel.add(userToTransferMoneyToPanel);
		destinationUserAndAmountFinalPanel.add(transferMoneyAmountPanel);
		destinationUserAndAmountFinalPanel.add(submitTransferPanel);
		destinationUserAndAmountFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		transactionsCenterFinalPanel.add(destinationUserAndAmountFinalPanel);
		
		//add listeners for buttons
		submitTransfer.addActionListener(this);
		
		transactions.add(transactionsCenterFinalPanel, BorderLayout.CENTER);
		
		revalidate();
	}
	
	//customize the panel designed for withdrawing money
	public void customizeWithdrawPanelWhenButtonIsPressed()
	{
		//stop the thread which shows statistics in a 3D pie chart manner
		if (isThreadRunning)
		{
			rotate3DPieChart.stop();
		}

		//set main panel of the center side of the 'transactions' tab
		transactionsCenterFinalPanel.setLayout(new GridLayout(1, 1));
				
		//create and customize text field for the amount to be withdrawn
		withdrawMoneyAmount = new JTextField(10);
		withdrawMoneyAmount.setToolTipText("Type here the amount of money you want to withdraw.");
		JLabel transferMoneyLabel = new JLabel("    Amount: ");
		JPanel withdrawMoneyAmountPanel = new JPanel();
		withdrawMoneyAmountPanel.add(transferMoneyLabel);
		withdrawMoneyAmountPanel.add(withdrawMoneyAmount);
		
		//create 'submit' button and add it to a panel
		submitWithdraw = new JButton("Submit   ");
		ImageIcon submitIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\submit icon.png");
		submitWithdraw.setIcon(submitIcon);
		JLabel submitLabel = new JLabel("                  ");
		JPanel submitPanel = new JPanel();
		submitPanel.add(submitLabel);
		submitPanel.add(submitWithdraw);
				
		JLabel transferInfo = new JLabel("                             Complete the following form: ");
		withdrawFinalPanel = new JPanel();
		withdrawFinalPanel.setLayout(new GridLayout(7, 1));
		transferInfo.setText("             Set here the amount of money to be withdrawn:");
		withdrawFinalPanel.add(transferInfo);
		withdrawFinalPanel.add(withdrawMoneyAmountPanel);
		withdrawFinalPanel.add(submitPanel);
		withdrawFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		transactionsCenterFinalPanel.add(withdrawFinalPanel);
		
		//add listeners for buttons
		submitWithdraw.addActionListener(this);
		
		transactions.add(transactionsCenterFinalPanel, BorderLayout.CENTER);
		
		revalidate();	
	}
	
	//customize the panel designed for seeing the history of transactions made by now
	public void customizeHistoryPanelWhenButtonIsPressed()
	{
		//stop the thread which shows statistics in a 3D pie chart manner
		if (isThreadRunning)
		{
			rotate3DPieChart.stop();
		}
		
		if (hasCurrentUserMadeTransactions)
		{			
			//set main panel of the center side of the 'transactions' tab
			transactionsCenterFinalPanel.setLayout(new BorderLayout());

			//create a new panel for the table which contains transactions information
			JPanel transactionsHistoryTablePanel = new JPanel();
			transactionsHistoryTablePanel.setLayout(new GridLayout());
			transactionsHistoryTablePanel.add(transactionsHistoryTable);
			
			//create a header for table and set its color
			JTableHeader transactionsTableheader = transactionsHistoryTable.getTableHeader();
			transactionsTableheader.setForeground(new Color(20, 80, 250)); //light blue color
			
			//create print button
			print = new JToggleButton("Print");
			ImageIcon printIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\print icon.png");
			print.setIcon(printIcon);
			print.setToolTipText("Do you want to get a copy of your transactions history ? Click here !");
			JPanel printPanel = new JPanel();
			printPanel.add(print);
			
			//create a new panel for the print buttons, with some additional panels
			JPanel printButtonsPanel = new JPanel();
			printButtonsPanel.setLayout(new GridLayout(1, 4));
			printButtonsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			printButtonsPanel.add(printPanel);
			
			// some additional panels, just so the 'print' button is set in the west side  
			JLabel a = new JLabel(" "); //additional panel - design purpose
			JLabel b = new JLabel(" "); //additional panel - design purpose
			JLabel c = new JLabel(" "); //additional panel - design purpose
			printButtonsPanel.add(a);
			printButtonsPanel.add(b);
			printButtonsPanel.add(c);
			
			//create print PDF button
			printPDF = new JButton("PDF  ");
			ImageIcon printPDFIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\printpdf icon.png");
			printPDF.setIcon(printPDFIcon);
			printPDF.setToolTipText("PDF format");
			JPanel printPDFPanel = new JPanel();
			printPDFPanel.add(printPDF);
			
			//create printWORD
			printWORD = new JButton("WORD ");
			ImageIcon printWORDIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\printword icon.png");
			printWORD.setIcon(printWORDIcon);
			printWORD.setToolTipText("WORD format");
			JPanel printWORDPanel = new JPanel();
			printWORDPanel.add(printWORD);
			
			//create printEXCEL
			printEXCEL = new JButton("EXCEL");
			ImageIcon printEXCELIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\printexcel icon.png");
			printEXCEL.setIcon(printEXCELIcon);
			printEXCEL.setToolTipText("EXCEL format");
			JPanel printEXCELPanel = new JPanel();
			printEXCELPanel.add(printEXCEL);
			
			//add scrollpane to the table
			JScrollPane js = new JScrollPane(transactionsHistoryTable);
			js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			js.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			js.setVisible(true);
			transactionsCenterFinalPanel.add(js, BorderLayout.CENTER);
			transactionsCenterFinalPanel.add(printButtonsPanel, BorderLayout.SOUTH);
			
			ItemListener listener = new ItemListener() 
			{
				public void itemStateChanged(ItemEvent e) 
				{
					if (e.getSource() == print && e.getStateChange() == ItemEvent.SELECTED)
					{
						printButtonsPanel.remove(c);
						printButtonsPanel.remove(b);
						printButtonsPanel.remove(a);
						validate();
						printButtonsPanel.add(printPDFPanel);
						printButtonsPanel.add(printWORDPanel);
						printButtonsPanel.add(printEXCELPanel);
						validate();
					}
					
					else
					{
						printButtonsPanel.remove(printEXCELPanel);
						printButtonsPanel.remove(printWORDPanel);
						printButtonsPanel.remove(printPDFPanel);
						validate();
						printButtonsPanel.add(a);
						printButtonsPanel.add(b);
						printButtonsPanel.add(c);
						validate();
					}
				}
			};
			
			//add listeners for print buttons
			print.addItemListener(listener);
			printPDF.addActionListener(this);
			printWORD.addActionListener(this);
			printEXCEL.addActionListener(this);
			
			//add the final panel to the center side of the main frame
			transactions.add(transactionsCenterFinalPanel, BorderLayout.CENTER);
			
			revalidate();
		}
		else
		{
			transactionsCenterFinalPanel.setLayout(new FlowLayout());
			
			//info message if current user has no transactions made
			JLabel messageIfNoTransactions = new JLabel("You currently have no transactions made.");
			JPanel messageIfNoTransactionsPanel = new JPanel();
			messageIfNoTransactionsPanel.setLayout(new GridLayout());
			messageIfNoTransactionsPanel.add(messageIfNoTransactions);
			messageIfNoTransactionsPanel.setBorder(BorderFactory.createTitledBorder(""));
				
			//add the panel created above to a new one
			transactionsCenterFinalPanel.add(messageIfNoTransactionsPanel);
			
			//set border to the main panel of the center side of the main frame
			transactionsCenterFinalPanel.setBorder(BorderFactory.createTitledBorder(""));
			
			//add the final panel to the center side of the main frame
			transactions.add(transactionsCenterFinalPanel);
			
			revalidate();
		}
	}
	
	//customize the main settings panel
	void customizeFirstSettingsPanel()
	{
		//set main panel of the center side of the 'personal information' tab
		personalInformationCenterBorder.setLayout(new GridLayout(1, 1));		
			
		//create and customize button for changing first name
		changeFirstName = new JToggleButton(" First name");
		changeFirstName.setToolTipText("Click here to change your first name.");
		JPanel changeFirstNamePanel = new JPanel();
		changeFirstNamePanel.add(changeFirstName);
		
		//create and customize button for changing last name
		changeLastName = new JToggleButton(" Last name");
		changeLastName.setToolTipText("Click here to change your last name.");
		JPanel changeLastNamePanel = new JPanel();
		changeLastNamePanel.add(changeLastName);
		
		//create and customize button for changing birth date
		changeBirthDate = new JToggleButton(" Birth Date ");
		changeBirthDate.setToolTipText("Click here to change your birth date.");
		JPanel changeBirthDatePanel = new JPanel();
		changeBirthDatePanel.add(changeBirthDate);
		
		//create and customize button for changing email
		changeEmail = new JToggleButton("  E-mail");
		ImageIcon changeEmailIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\email icon.png");
		changeEmail.setIcon(changeEmailIcon);
		changeEmail.setToolTipText("Click here to change your e-mail.");
		changeEmail.setHorizontalTextPosition(SwingConstants.RIGHT);
		JPanel changeEmailPanel = new JPanel();
		changeEmailPanel.add(changeEmail);
		
		//create and customize button for changing photo
		changePhoto = new JToggleButton("Photo  ");
		ImageIcon changePhotoIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change photo icon.png");
		changePhoto.setIcon(changePhotoIcon);
		changePhoto.setToolTipText("Click here to change your photo.");
		JPanel changePhotoPanel = new JPanel();
		changePhotoPanel.add(changePhoto);
			
		//create a button group for the 3 buttons created above
		ButtonGroup settingsButtons = new ButtonGroup();
		settingsButtons.add(changeFirstName);
		settingsButtons.add(changeLastName);
		settingsButtons.add(changeBirthDate);
		settingsButtons.add(changeEmail);
		settingsButtons.add(changePhoto);
		
		//create settings info panel
		JLabel changeDetailsInfo = new JLabel("Change:");
		JPanel changeDetailsInfoPanel = new JPanel();
		changeDetailsInfoPanel.add(changeDetailsInfo);
		
		//add all the components created above to a new final panel
		changeDetailsFinalPanel = new JPanel();
		changeDetailsFinalPanel.setLayout(new GridLayout(7, 1));
		changeDetailsFinalPanel.add(changeDetailsInfoPanel);
		changeDetailsFinalPanel.add(changeFirstNamePanel);
		changeDetailsFinalPanel.add(changeLastNamePanel);
		changeDetailsFinalPanel.add(changeBirthDatePanel);
		changeDetailsFinalPanel.add(changeEmailPanel);
		changeDetailsFinalPanel.add(changePhotoPanel);
		
		//set border of the final panel
		changeDetailsFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//add the final panel to a new one (design purpose)
		personalInformationCenterFinalPanel.setLayout(new GridLayout(1, 2));
		personalInformationCenterFinalPanel.add(changeDetailsFinalPanel);
		personalInformationCenterFinalPanel.add(new JPanel()); //design purpose
		
		//listeners for buttons created above
		ItemListener listener = new ItemListener() 
		{
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getSource() == changeFirstName && e.getStateChange() == ItemEvent.SELECTED)
				{
					customizeSecondSettingsPanelFN();
				}
				
				else if (e.getSource() == changeLastName && e.getStateChange() == ItemEvent.SELECTED)
				{
					customizeSecondSettingsPanelLN();
				}
				
				else if (e.getSource() == changeEmail && e.getStateChange() == ItemEvent.SELECTED)
				{
					customizeSecondSettingsPanelEM();
				}
				else if (e.getSource() == changeBirthDate && e.getStateChange() == ItemEvent.SELECTED)
				{
					customizeSecondSettingsPanelBD();
				}
				
				else if (e.getSource() == changePhoto && e.getStateChange() == ItemEvent.SELECTED)
				{
					customizeSecondSettingPanelUP();
				}
			}			      
		};
		
		//add listeners for buttons
		changeFirstName.addItemListener(listener);
		changeLastName.addItemListener(listener);
		changeEmail.addItemListener(listener);
		changeBirthDate.addItemListener(listener);
		changePhoto.addItemListener(listener);
		
		//add the final panel to the center side of the main frame
		personalInformationTab.add(personalInformationCenterFinalPanel, BorderLayout.CENTER);
		
		revalidate();
		
	}
	
	//customize the panel designed for changing first name
	public void customizeSecondSettingsPanelFN()
	{	
		//remove last element from the center side panel just to avoid overredimensioning
		personalInformationCenterFinalPanel.remove(1);
		
		//create and customize text field for the new first name
		newFirstName = new JTextField(10);
		newFirstName.setToolTipText("Type here your new first name.");
		JPanel newFirstNamePanel = new JPanel();
		newFirstNamePanel.add(newFirstName);
		
		//create submit button and add it to a panel
		submitNewFirstName = new JButton("Submit   ");
		ImageIcon submitNewFirstNameIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\perform icon.png");
		submitNewFirstName.setIcon(submitNewFirstNameIcon);
		JPanel submitNewFirstNamePanel = new JPanel();
		submitNewFirstNamePanel.add(submitNewFirstName);
				
		//create info label
		JLabel firstNameInfo = new JLabel("Insert new first name: ");
		JPanel firstNameInfoPanel = new JPanel();
		firstNameInfoPanel.add(firstNameInfo);
		
		//add all the components above into a new final panel
		firstNameEditFinalPanel = new JPanel();
		firstNameEditFinalPanel.setLayout(new GridLayout(4, 1));
		firstNameEditFinalPanel.add(firstNameInfoPanel);
		firstNameEditFinalPanel.add(newFirstNamePanel);
		firstNameEditFinalPanel.add(submitNewFirstNamePanel);

		//set border of the new final panel
		firstNameEditFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//add listeners for buttons
		submitNewFirstName.addActionListener(this);
		
		//add the final panel to the center side of main frame
		personalInformationCenterFinalPanel.add(firstNameEditFinalPanel);
		
		revalidate();	
	}
	
	//customize the panel designed for changing last name
	void customizeSecondSettingsPanelLN()
	{
		//remove last element from the center side panel just to avoid overredimensioning
		personalInformationCenterFinalPanel.remove(1);
		
		//create and customize text field for the new last name
		newLastName = new JTextField(10);
		newLastName.setToolTipText("Type here your new last name.");
		JPanel newLastNamePanel = new JPanel();
		newLastNamePanel.add(newLastName);
		
		//create submit button and add it to a panel
		submitNewLastName = new JButton("Submit   ");
		ImageIcon submitnewLastNameIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\perform icon.png");
		submitNewLastName.setIcon(submitnewLastNameIcon);
		JPanel submitnewLastNamePanel = new JPanel();
		submitnewLastNamePanel.add(submitNewLastName);
				
		//create info label
		JLabel lastNameInfo = new JLabel("Insert new last name: ");
		JPanel lastNameInfoPanel = new JPanel();
		lastNameInfoPanel.add(lastNameInfo);
		
		//add all the components above into a new final panel
		lastNameEditFinalPanel = new JPanel();
		lastNameEditFinalPanel.setLayout(new GridLayout(4, 1));
		lastNameEditFinalPanel.add(lastNameInfoPanel);
		lastNameEditFinalPanel.add(newLastNamePanel);
		lastNameEditFinalPanel.add(submitnewLastNamePanel);

		//set border of the new final panel
		lastNameEditFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//add listeners for buttons
		submitNewLastName.addActionListener(this);
		
		//add the final panel to the center side of main frame
		personalInformationCenterFinalPanel.add(lastNameEditFinalPanel);
		
		revalidate();		
	}
	
	//customize the panel designed for changing the date of birth
	void customizeSecondSettingsPanelBD()
	{
		//remove last element from the center side panel just to avoid overredimensioning
		personalInformationCenterFinalPanel.remove(1);
		
		//days, months and years for completing date of birth
		String[] days = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
						  "11", "12", "13", "14", "15", "16", "17", "18", 
						  "19", "20", "21", "22", "23", "24", "25", "26", 
						  "27", "28", "29", "30", "31"
					    };
				
		String[] months = { "January", "February", "March", "April", "May", 
							"June", "July", "August", "September",  "October", 
							"November", "December"
						  };
				
		String [] years = { "1998", "1997", "1996", "1995", "1994", "1993", 
							"1994", "1991", "1990", "1989","1988", "1988", 
							"1987", "1986", "1985", "1984", "1983", "1982", 
							"1981", "1980", "1979", "1978", "1977", "1976", 
							"1975", "1974", "1973", "1972", "1971", "1970", 
						  };
											
		//combo boxes for each for days, month, years
		daysListCBD = new JComboBox<Object>(days);
		daysListCBD.setSelectedIndex(0);
		daysListCBD.setEditable(true);
		monthsListCBD = new JComboBox<Object>(months);
		daysListCBD.setSelectedIndex(0);
		monthsListCBD.setEditable(true);
		yearsListCBD = new JComboBox<Object>(years);
		daysListCBD.setSelectedIndex(0);
		yearsListCBD.setEditable(true);
				
		JPanel daysListCBDPanel = new JPanel();
		daysListCBDPanel.setLayout(new GridLayout(2, 1));
		JPanel monthsListCBDPanel = new JPanel();
		monthsListCBDPanel.setLayout(new GridLayout(2, 1));
		JPanel yearsListCBDPanel = new JPanel();
		yearsListCBDPanel.setLayout(new GridLayout(2, 1));
				
		//create a separate panel for birth date
		JPanel dayMonthYearPanel = new JPanel();
		dayMonthYearPanel.setLayout(new GridLayout(3, 1));	
		
		daysListCBDPanel.add(new JLabel("Day"));
		daysListCBDPanel.add(daysListCBD);
		monthsListCBDPanel.add(new JLabel("Month"));
		monthsListCBDPanel.add(monthsListCBD);
		yearsListCBDPanel.add(new JLabel(" Year"));
		yearsListCBDPanel.add(yearsListCBD);
		
		//add birth date details panels to another panel
		dayMonthYearPanel.add(daysListCBD);
		dayMonthYearPanel.add(monthsListCBD);
		dayMonthYearPanel.add(yearsListCBD);	
		
		//create submit button and add it to a panel
		submitNewBirthDate = new JButton("Submit   ");
		ImageIcon submitNewBirthDateIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\perform icon.png");
		submitNewBirthDate.setIcon(submitNewBirthDateIcon);
		JPanel submitNewBirthDatePanel = new JPanel();
		submitNewBirthDatePanel.add(submitNewBirthDate);
				
		//create info label
		JLabel birthDateInfo = new JLabel("Insert new birth date: ");
		JPanel birthDateInfoPanel = new JPanel();
		birthDateInfoPanel.add(birthDateInfo);
		
		//add all the components above into a new final panel
		birthDateEditFinalPanel = new JPanel();
		birthDateEditFinalPanel.setLayout(new GridLayout(3, 1));
		birthDateEditFinalPanel.add(birthDateInfoPanel);
		birthDateEditFinalPanel.add(dayMonthYearPanel);
		birthDateEditFinalPanel.add(submitNewBirthDatePanel);
	
		//set border of the new final panel
		birthDateEditFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//add listeners for buttons
		submitNewBirthDate.addActionListener(this);
		
		//add the final panel to the center side of main frame
		personalInformationCenterFinalPanel.add(birthDateEditFinalPanel);
		
		revalidate();		
	}

	//customize the panel designed for changing the email
	void customizeSecondSettingsPanelEM()
	{
		//remove last element from the center side panel just to avoid overredimensioning
		personalInformationCenterFinalPanel.remove(1);
		
		//create and customize text field for the new email
		newEmail = new JTextField(10);
		newEmail.setToolTipText("Type here your new last name.");
		JPanel newEmailPanel = new JPanel();
		newEmailPanel.add(newEmail);
		
		//create submit button and add it to a panel
		submitNewEmail = new JButton("Submit   ");
		ImageIcon submitnewEmailIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\perform icon.png");
		submitNewEmail.setIcon(submitnewEmailIcon);
		JPanel submitnewEmailPanel = new JPanel();
		submitnewEmailPanel.add(submitNewEmail);
				
		//create info label
		JLabel eMailInfo = new JLabel("Insert new e-mail: ");
		JPanel eMailInfoPanel = new JPanel();
		eMailInfoPanel.add(eMailInfo);
		
		//add all the components above into a new final panel
		eMailEditFinalPanel = new JPanel();
		eMailEditFinalPanel.setLayout(new GridLayout(4, 1));
		eMailEditFinalPanel.add(eMailInfoPanel);
		eMailEditFinalPanel.add(newEmailPanel);
		eMailEditFinalPanel.add(submitnewEmailPanel);

		//set border of the new final panel
		eMailEditFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//add listeners for buttons
		submitNewEmail.addActionListener(this);
		
		//add the final panel to the center side of main frame
		personalInformationCenterFinalPanel.add(eMailEditFinalPanel);
		
		revalidate();		
	}
	
	//customize the panel designed for changing the user's photo
	void customizeSecondSettingPanelUP()
	{
		//remove last element from the center side panel just to avoid overredimensioning
		personalInformationCenterFinalPanel.remove(1);
			
		//create submit button and add it to a panel - bottom
		submitNewPhoto = new JButton("Submit   ");
		ImageIcon submitNewPhotoIcon = new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\perform icon.png");
		submitNewPhoto.setIcon(submitNewPhotoIcon);
		JPanel submitNewPhotoPanel = new JPanel();
		submitNewPhotoPanel.add(submitNewPhoto);
				
		//create info label - top
		JLabel changePhotoInfo = new JLabel("Choose a default image: ");
		changePhotoInfo.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel changePhotoInfoPanel = new JPanel(new GridLayout(1, 1));
		JPanel defaultPhotos = new JPanel(new GridLayout(2, 1));
		
		//create the panel which contains select default files or from pc - middle
		JLabel choosePhotoFromPCInfo = new JLabel("Or select file:");
		choosePhotoFromPCInfo.setHorizontalAlignment(SwingConstants.CENTER);
		choosePhotoFromPC = new JButton("Browse");
		choosePhotoFromPC.setIcon(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\browse folder icon.png"));
		JPanel choosePhotoFromPCPanel = new JPanel();
		choosePhotoFromPCPanel.add(choosePhotoFromPC);
		JPanel selectFromPCFinal = new JPanel(new GridLayout(2, 1));
		selectFromPCFinal.add(choosePhotoFromPCInfo);
		selectFromPCFinal.add(choosePhotoFromPCPanel);
		selectFromPCFinal.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		JLabel defaultPhotosContent = new JLabel();
		defaultPhotosContent.setLayout(new GridLayout(3, 4));
		
		//make togglebuttons for default images
		defaultPhoto1 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo black.png"));
		defaultPhoto2 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo cyan.png"));
		defaultPhoto3 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo gray.png"));
		defaultPhoto4 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo red.png"));
		defaultPhoto5 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo blue.png"));
		defaultPhoto6 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo orange.png"));
		defaultPhoto7 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo yellow.png"));
		defaultPhoto8 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo purple.png"));
		defaultPhoto9 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo pink.png"));
		defaultPhoto10 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo brown.png"));
		defaultPhoto11 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo dark blue.png"));
		defaultPhoto12 = new JToggleButton(new ImageIcon("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\change user photo dark green.png"));
			
		//set tooltips info for buttons above
		defaultPhoto1.setToolTipText("Black");
		defaultPhoto2.setToolTipText("Cyan");
		defaultPhoto3.setToolTipText("Gray");
		defaultPhoto4.setToolTipText("Red");
		defaultPhoto5.setToolTipText("Blue");
		defaultPhoto6.setToolTipText("Orange");
		defaultPhoto7.setToolTipText("Yellow");
		defaultPhoto8.setToolTipText("Purple");
		defaultPhoto9.setToolTipText("Pink");
		defaultPhoto10.setToolTipText("Brown");
		defaultPhoto11.setToolTipText("Dark Blue");
		defaultPhoto12.setToolTipText("Dark Green");
		
		//add the buttons created above in a button group
		ButtonGroup defaultPhotosGroup = new ButtonGroup();
		defaultPhotosGroup.add(defaultPhoto1);
		defaultPhotosGroup.add(defaultPhoto2);
		defaultPhotosGroup.add(defaultPhoto3);
		defaultPhotosGroup.add(defaultPhoto4);
		defaultPhotosGroup.add(defaultPhoto5);
		defaultPhotosGroup.add(defaultPhoto6);
		defaultPhotosGroup.add(defaultPhoto7);
		defaultPhotosGroup.add(defaultPhoto8);
		defaultPhotosGroup.add(defaultPhoto9);
		defaultPhotosGroup.add(defaultPhoto10);
		defaultPhotosGroup.add(defaultPhoto11);
		defaultPhotosGroup.add(defaultPhoto12);
		
		//group all panels of the toggle buttons
		defaultPhotosContent.add(defaultPhoto1);
		defaultPhotosContent.add(defaultPhoto2);
		defaultPhotosContent.add(defaultPhoto3);
		defaultPhotosContent.add(defaultPhoto4);
		defaultPhotosContent.add(defaultPhoto5);
		defaultPhotosContent.add(defaultPhoto6);
		defaultPhotosContent.add(defaultPhoto7);
		defaultPhotosContent.add(defaultPhoto8);
		defaultPhotosContent.add(defaultPhoto9);
		defaultPhotosContent.add(defaultPhoto10);
		defaultPhotosContent.add(defaultPhoto11);
		defaultPhotosContent.add(defaultPhoto12);
		
		//create a new panel in which we add the two options: choose default image or from PC
		JPanel defaultPhotosContentPanel = new JPanel(new BorderLayout());
		defaultPhotosContent.setHorizontalAlignment(SwingConstants.CENTER);
		defaultPhotosContentPanel.add(defaultPhotosContent, BorderLayout.CENTER);
		defaultPhotosContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));	
		defaultPhotos.add(defaultPhotosContentPanel);
		defaultPhotos.add(selectFromPCFinal);
		
		//top info panel
		changePhotoInfoPanel.add(changePhotoInfo);
		
		//add all the components above into a new final panel which will be viewed on the east part of the main frame
		changePhotoEditFinalPanel = new JPanel();
		changePhotoEditFinalPanel.setLayout(new BorderLayout());
		changePhotoEditFinalPanel.add(changePhotoInfoPanel, BorderLayout.NORTH);
		changePhotoEditFinalPanel.add(defaultPhotos, BorderLayout.CENTER);
		changePhotoEditFinalPanel.add(submitNewPhotoPanel, BorderLayout.SOUTH);

		//set border of the new final panel
		changePhotoEditFinalPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//add listeners for buttons
		choosePhotoFromPC.addActionListener(this);
		submitNewPhoto.addActionListener(this);
		
		//listeners for all 12 toggle buttons which represent default images
		//we asign to a variable different values so we know which image should be set as main photo
		//this is for further use
		ItemListener a = new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				if (e.getSource() == defaultPhoto1 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 1;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto2 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 2;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto3 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 3;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto4 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 4;
					defaultPhoto = true;			
				}
				
				if (e.getSource() == defaultPhoto5 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 5;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto6 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 6;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto7 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 7;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto8 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 8;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto9 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 9;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto10 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 10;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto11 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 11;
					defaultPhoto = true;
				}
				
				if (e.getSource() == defaultPhoto12 && e.getStateChange() == ItemEvent.SELECTED)
				{
					whichColorIsChosen = 12;
					defaultPhoto = true;
				}
			}
			
		};
		
		defaultPhoto1.addItemListener(a);
		defaultPhoto2.addItemListener(a);
		defaultPhoto3.addItemListener(a);
		defaultPhoto4.addItemListener(a);
		defaultPhoto5.addItemListener(a);
		defaultPhoto6.addItemListener(a);
		defaultPhoto7.addItemListener(a);
		defaultPhoto8.addItemListener(a);
		defaultPhoto9.addItemListener(a);
		defaultPhoto10.addItemListener(a);
		defaultPhoto11.addItemListener(a);
		defaultPhoto12.addItemListener(a);
		
		//add the final panel to the center side of main frame
		personalInformationCenterFinalPanel.add(changePhotoEditFinalPanel);
		
		revalidate();
	}
	
	@SuppressWarnings("deprecation")
	//change password
	public void changePassword()
	{
		Connection connection = null;
		
		try 
		{	
			//check if the new typed password has at least 1 digit, 1 letter and 1 symbol
			if (newPasswordCP.getText().length() > 0 && !newPasswordCP.getText().matches(
												"(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)((\\d+)([a-zA-Z0-9]+)))"
											  + "(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)((\\d*)([a-zA-Z0-9]*)))*"
											  + "|"
											  + "(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)(([a-zA-Z]+)(\\d+)))"
											  + "(([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)(([a-zA-Z]*)(\\d*)))*"
											  + "|"
											  + "(((\\d+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)([a-zA-Z0-9]+)))"
											  + "(((\\d*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)([a-zA-Z0-9]*)))*"
											  + "|"
											  + "(((\\d+)([a-zA-Z0-9]+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)))"
											  + "(((\\d*)([a-zA-Z0-9]*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)))*"
											  + "|"
											  + "((([a-zA-Z0-9]+)(\\d+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)))"
											  + "((([a-zA-Z0-9]*)(\\d*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)))*"
											  + "|"
											  + "((([a-zA-Z0-9]+)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]+)(\\d+)))"
											  + "((([a-zA-Z0-9]*)([-!@#$%^&*()_+|~=`{}\\[\\]:;'<>?,.\\//]*)(\\d*)))*"
																				     ))
			{
				throw new OwnException("The new password must contain at least: \n\n"
											+ "> 1 digit (Ex.: 0 1 2 3 4 5 6 7 8 9)\n"
											+ "> 1 letter (Ex.: a b c d e f g h ... )\n"
											+ "> 1 symbol (Ex.: @ # $ % % ... )\n\n"
											+ "Also, the new password must contain ONLY patterns mentioned above.");
			}
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//get current user actual password
			String sqlCurrentPassword = "SELECT pswrd FROM clients WHERE username LIKE ?";
			PreparedStatement stmtCurrentPassword = connection.prepareStatement(sqlCurrentPassword);
			stmtCurrentPassword.setString(1, username.getText().toString());
			ResultSet rsGetCurrentUserPassword = stmtCurrentPassword.executeQuery();
			String currentPassword = "";
			
			//store current password in a string
			if (rsGetCurrentUserPassword.next())
			{
				currentPassword = rsGetCurrentUserPassword.getString("pswrd");
			}
			
			//compare current user actual password with the password from currentPasswordCP
			if (!currentPassword.equals(currentPasswordCP.getText()))
			{
				throw new OwnException("The password you entered does not match the one from database !");
			}
			
			//update the password of the current user in the database
			String sqlSetNewPassword = "UPDATE clients SET pswrd = '" 
				      + newPasswordCP.getText()
				      + "'"
				      + " WHERE username LIKE '"
				      + username.getText()
					  + "'";
	
			Statement stmtSetNewPassword = connection.prepareStatement(sqlSetNewPassword);
	
			//execute sql statement created above
			int didCurrentUserUpdateHisNewPassword = stmtSetNewPassword.executeUpdate(sqlSetNewPassword);
			
			if (didCurrentUserUpdateHisNewPassword > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully changed your password !" ,
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new OwnException("An error has occurred ! The password could not be changed.");
			}
		}
		catch(SQLException e)
		{
	
		} 
		catch (OwnException e) 
		{
	
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

	//change first name
	void changeFirstName()
	{
		Connection connection = null;
		
		try 
		{	
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//update first name of the current user in the database
			String sqlSetNewFirstName = "UPDATE clients SET firstname = '" 
				      + newFirstName.getText()
				      + "'"
				      + " WHERE username LIKE '"
				      + username.getText()
					  + "'";

			Statement stmtSetNewFirstName = connection.prepareStatement(sqlSetNewFirstName);

			//execute sql statement created above
			int didCurrentUserUpdateHisNewFirstName = stmtSetNewFirstName.executeUpdate(sqlSetNewFirstName);
			
			if (didCurrentUserUpdateHisNewFirstName > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully changed your first name !" ,
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new OwnException("An error has occurred ! Your first name could not be changed.");
			}
		}
		catch(SQLException e)
		{

		} 
		catch (OwnException e) 
		{

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
	
	//change last name
	void changeLastName()
	{
		Connection connection = null;
		
		try 
		{	
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//update last name of the current user in the database
			String sqlSetNewLastName = "UPDATE clients SET lastname = '" 
				      + newLastName.getText()
				      + "'"
				      + " WHERE username LIKE '"
				      + username.getText()
					  + "'";

			Statement stmtSetNewLastName = connection.prepareStatement(sqlSetNewLastName);

			//execute sql statement created above
			int didCurrentUserUpdateHisNewLastName = stmtSetNewLastName.executeUpdate(sqlSetNewLastName);
			
			if (didCurrentUserUpdateHisNewLastName > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully changed your last name !",
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new OwnException("An error has occurred ! Your last name could not be changed.");
			}
		}
		catch(SQLException e)
		{

		} 
		catch (OwnException e) 
		{

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
	
	
	//change the e-mail
	void changeEmail()
	{
		Connection connection = null;
		boolean isEmailFormatCorrect = false;
		
		try 
		{	
			//check if the typed new e-mail address is valid
			if (!newEmail.getText().matches("\\b[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}\\b"))
			{
				throw new OwnException("Error in new e-mail format.  \n\n"
										   	+ "> Ex.: username@gmail.com\n\n"
										   	+ "Please properly rewrite yor e-mail address.");
			}
			
			isEmailFormatCorrect = true;
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//update e-mail of the current user in the database
			String sqlSetNewEmail = "UPDATE clients SET email = '" 
				      + newEmail.getText()
				      + "'"
				      + " WHERE username LIKE '"
				      + username.getText()
					  + "'";

			Statement stmtSetNewEmail = connection.prepareStatement(sqlSetNewEmail);

			//execute sql statement created above
			int didCurrentUserUpdateHisNewEmail = stmtSetNewEmail.executeUpdate(sqlSetNewEmail);
			
			if (didCurrentUserUpdateHisNewEmail > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully changed your e-mail !",
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new OwnException("An error has occurred ! Your e-mail could not be changed.");
			}
		}
		catch(SQLException e)
		{

		} 
		catch (OwnException e) 
		{

		}
		finally 
		{
			if (isEmailFormatCorrect)
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
	
	//change the date of birth
	void changeBirthDate()
	{
		Connection connection = null;
		
		try 
		{	
			//store birth date details in strings
			String daysListString = (String) daysListCBD.getSelectedItem();
			String monthsListString = (String) monthsListCBD.getSelectedItem();
			String yearsListString = (String) yearsListCBD.getSelectedItem();
			
			//string for getting the new birth month number
			String getMonthNumber = (String) monthsListCBD.getSelectedItem();
			
			switch (monthsListString) 
			{
            	case "January":  getMonthNumber = "1";
                     break;
            	case "February":  getMonthNumber = "2";
                     break;
            	case "March":  getMonthNumber = "3";
                     break;
            	case "April":  getMonthNumber = "4";
                     break;
            	case "May":  getMonthNumber = "5";
                     break;
            	case "June":  getMonthNumber = "6";
                     break;
            	case "July":  getMonthNumber = "7";
                     break;
            	case "August":  getMonthNumber = "8";
                     break;
            	case "September":  getMonthNumber = "9";
                     break;
            	case "October": getMonthNumber = "10";
                     break;
            	case "November": getMonthNumber = "11";
                     break;
            	case "December": getMonthNumber = "12";
                     break;
			}
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//String finalBirthDate = daysListCBD.get
			//update e-mail of the current user in the database
			String sqlSetNewBirthDate = "UPDATE clients SET birthdate = '" 
								  	     + daysListString
								  	     + "/"
								  	     + getMonthNumber
								  	     + "/"
								  	     + yearsListString
								  	     + "'"
								  	     + "WHERE username LIKE '"
								  	     + username.getText()
								  	     + "'";

			Statement stmtSetNewBirthDate = connection.prepareStatement(sqlSetNewBirthDate);

			//execute sql statement created above
			int didCurrentUserUpdateHisNewBirthDate = stmtSetNewBirthDate.executeUpdate(sqlSetNewBirthDate);
			
			if (didCurrentUserUpdateHisNewBirthDate > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully changed your birth date !",
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				throw new OwnException("An error has occurred ! Your birth date could not be changed.");
			}
		}
		catch(SQLException e)
		{

		} 
		catch (OwnException e) 
		{

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
	
	//browse for selecting a new custom photo
	public void browsePhotos()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a file");
		fileChooser.setVisible(true);
		
		int status = fileChooser.showOpenDialog(null);
		
		if (status == JFileChooser.APPROVE_OPTION) 
		{
		      image = fileChooser.getSelectedFile();
		      didUserChoosePhoto = true;
		      defaultPhoto = false;
		} 
	}
	
	//if changing user's photo it's about a custom photo, taken from PC, we check its size
	//to be at max 120 x 65
	public boolean checkPhoto()
	{	
		try
		{
			//get the absolute path of the selected image
			BufferedImage newUserPhoto = ImageIO.read(new File(image.getAbsolutePath()));

			//check if the selected photo accomplishes dimension criterions
			if (newUserPhoto.getWidth() > 120 || newUserPhoto.getHeight() > 65)
			{
				return false;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	//change user's photo - select from the default avalaible photos
	public void changeDefaultPhoto(String pathToPhoto)
	{
		Connection connection = null;

		try 
		{			
			//declare a FileInputStream object to store binary stream of given photo
			FileInputStream fis;
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//first, check if the user already has a customized avatar stored in database
			String sqlCheckIfUserAlreadyHasPhoto = "SELECT username FROM users_photos WHERE username like ?";
			PreparedStatement pstmtCheckIfUserAlreadyHasPhoto = connection.prepareStatement(sqlCheckIfUserAlreadyHasPhoto);
			pstmtCheckIfUserAlreadyHasPhoto.setString(1, username.getText());
			ResultSet rsCheckIfUserAlreadyHasPhoto = pstmtCheckIfUserAlreadyHasPhoto.executeQuery();
			boolean checkIfUserAlreadyHasPhoto = false;
			
			if (rsCheckIfUserAlreadyHasPhoto.next())
			{
				checkIfUserAlreadyHasPhoto = true;
			}
			
			//if he does, delete the whole row of information about it
			if (checkIfUserAlreadyHasPhoto)
			{
				String sqlDeleteCurrentPhoto = "DELETE FROM users_photos WHERE username LIKE ?";
				PreparedStatement pstmtDeleteCurrentPhoto = connection.prepareStatement(sqlDeleteCurrentPhoto);
				pstmtDeleteCurrentPhoto.setString(1, username.getText());
				pstmtDeleteCurrentPhoto.executeUpdate();			
			}
			
			String sqlChangePhoto = "INSERT INTO users_photos(username, image) VALUES (?, ?)";
			PreparedStatement pstmtChangePhoto = connection.prepareStatement(sqlChangePhoto);
			pstmtChangePhoto.setString(1, username.getText());
			//here, we create a new file using the default images
			File newDefaultPhoto = new File(pathToPhoto);
			fis = new FileInputStream(newDefaultPhoto);
			pstmtChangePhoto.setBinaryStream(2, (InputStream)fis, (int)(newDefaultPhoto.length())); //the image to be stored in database

			int hasTheUserChangedHisPhoto = pstmtChangePhoto.executeUpdate();
				
			//check if the new photo has been updated successfully in database
			if(hasTheUserChangedHisPhoto > 0) 
			{
				JOptionPane.showMessageDialog (null,
		   					   				   "You have successfully changed your photo.", 
		   					   				   "Success", 
		   					   				    JOptionPane.INFORMATION_MESSAGE);
			}
			else 
			{
				JOptionPane.showMessageDialog (null,
		   					   				   "Changing avatar has failed.", 
		   					   				   "Success", 
		   					   				    JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			//close all the connections
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

	//change user's photo - custom photo specified by user himself using a JFileChooser
	private void changeCustomPhoto()
	{
		Connection connection = null;

		try 
		{			
			//declare a FileInputStream object to store binary stream of given photo
			FileInputStream fis;
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//first, check if the user already has a customized avatar stored in database
			String sqlCheckIfUserAlreadyHasPhoto = "SELECT username FROM users_photos WHERE username like ?";
			PreparedStatement pstmtCheckIfUserAlreadyHasPhoto = connection.prepareStatement(sqlCheckIfUserAlreadyHasPhoto);
			pstmtCheckIfUserAlreadyHasPhoto.setString(1, username.getText());
			ResultSet rsCheckIfUserAlreadyHasPhoto = pstmtCheckIfUserAlreadyHasPhoto.executeQuery();
			boolean checkIfUserAlreadyHasPhoto = false;
			
			if (rsCheckIfUserAlreadyHasPhoto.next())
			{
				checkIfUserAlreadyHasPhoto = true;
			}
			
			//if he does, delete the whole row of information about it
			if (checkIfUserAlreadyHasPhoto)
			{
				String sqlDeleteCurrentPhoto = "DELETE FROM users_photos WHERE username LIKE ?";
				PreparedStatement pstmtDeleteCurrentPhoto = connection.prepareStatement(sqlDeleteCurrentPhoto);
				pstmtDeleteCurrentPhoto.setString(1, username.getText());
				pstmtDeleteCurrentPhoto.executeUpdate();			
			}
			
			String sqlChangePhoto = "INSERT INTO users_photos(username, image) VALUES (?, ?)";
			PreparedStatement pstmtChangePhoto = connection.prepareStatement(sqlChangePhoto);
			pstmtChangePhoto.setString(1, username.getText());
			fis = new FileInputStream(image);
			pstmtChangePhoto.setBinaryStream(2, (InputStream)fis, (int)(image.length())); //the image to be stored in database

			int hasTheUserChangedHisPhoto = pstmtChangePhoto.executeUpdate();
				
			//check if the new photo has been updated successfully in database
			if(hasTheUserChangedHisPhoto > 0) 
			{
				JOptionPane.showMessageDialog (null,
		   					   				   "You have successfully changed your photo.", 
		   					   				   "Success", 
		   					   				    JOptionPane.INFORMATION_MESSAGE);
			}
			else 
			{
				JOptionPane.showMessageDialog (null,
		   					   				   "Changing avatar has failed.", 
		   					   				   "Success", 
		   					   				    JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			//close all the connections
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
	
	//functia trebuie terminata
	public void getTransactionsCountForStatistics()
	{
		Connection connection = null;
		try
		{
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//get current user ID for further use
			int currentUserID = getUserID(username.getText());
			
			//get current number of storages made	
			String sqlGetDepositCount = "SELECT count(ttype) FROM transactions WHERE clientid = "
									+ currentUserID
									+ "AND ttype LIKE "
									+ "'depositing'";
			PreparedStatement stmtGetDepositCount = connection.prepareStatement(sqlGetDepositCount);
			ResultSet rsGetDepositCount = stmtGetDepositCount.executeQuery();
			
			if (rsGetDepositCount.next())
			{
				depositCount = rsGetDepositCount.getInt(1);
			}
			
			
			//get current number of withdrawals made	
			String sqlGetWithdrawCount = "SELECT count(ttype) FROM transactions WHERE clientid = "
									+ currentUserID
									+ "AND ttype LIKE "
									+ "'withdrawal'";
			PreparedStatement stmtGetWithdrawCount = connection.prepareStatement(sqlGetWithdrawCount);
			ResultSet rsGetWithdrawCount = stmtGetWithdrawCount.executeQuery();
			
			if (rsGetWithdrawCount.next())
			{
				withdrawCount = rsGetWithdrawCount.getInt(1);
			}
			
			
			//get current number of transfers made	
			String sqlGetTransferCount = "SELECT count(ttype) FROM transactions WHERE clientid = "
									+ currentUserID
									+ "AND ttype LIKE "
									+ "'transfer'";
			PreparedStatement stmtGetTransferCount = connection.prepareStatement(sqlGetTransferCount);
			ResultSet rsGetTransferCount = stmtGetTransferCount.executeQuery();
			
			if (rsGetTransferCount.next())
			{
				transferCount = rsGetTransferCount.getInt(1);
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
			catch(SQLException e) 
			{
					
			}
		}
		
	}
	
	public void getTransactionsAmountForStatistics()
	{
		Connection connection = null;
		try
		{
			//call this function here for getting the number of each kind of transaction for further use
			getTransactionsCountForStatistics();
		
			int array[] = {depositCount, withdrawCount, transferCount};
			Arrays.sort(array);
			dValues = new ArrayList<Integer>();
		
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
		
			//get current user values of each storage he made
			String sqlDepositAmounts = "SELECT amount FROM transactions WHERE clientID = ?"
									 + "AND ttype LIKE 'depositing'";
			PreparedStatement stmtDepositAmounts = connection.prepareStatement(sqlDepositAmounts);
			stmtDepositAmounts.setInt(1, getUserID(username.getText()));
			ResultSet rsDepositAmounts = stmtDepositAmounts.executeQuery();
			
			while (rsDepositAmounts.next())
			{
				dValues.add(rsDepositAmounts.getInt("Amount"));
			}
			
			//get current user values of each withdrawal he made
			String sqlWithdrawAmounts = "SELECT amount FROM transactions WHERE clientID = ?"
									 + "AND ttype LIKE 'withdrawal'";
			PreparedStatement stmtWithdrawAmounts = connection.prepareStatement(sqlWithdrawAmounts);
			stmtWithdrawAmounts.setInt(1, getUserID(username.getText()));
			ResultSet rsWithdrawAmounts = stmtWithdrawAmounts.executeQuery();
			
			while (rsWithdrawAmounts.next())
			{
				wValues.add(rsWithdrawAmounts.getInt("Amount"));
			}
			
			//get current user values of each transfer he made
			String sqlTransferAmounts = "SELECT amount FROM transactions WHERE clientID = ?"
									 + "AND ttype LIKE 'transfer'";
			PreparedStatement stmtTransferAmounts = connection.prepareStatement(sqlTransferAmounts);
			stmtTransferAmounts.setInt(1, getUserID(username.getText()));
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
	
	//deposit money in your bank account
	public void depositMoney()
	{
		Connection connection = null;
		
		try 
		{	
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//get current user balance
			String sqlCurrentAmountOfMoney = "SELECT balance FROM clients WHERE username LIKE ?";
			PreparedStatement stmtCurrentAmountOfMoney = connection.prepareStatement(sqlCurrentAmountOfMoney);
			stmtCurrentAmountOfMoney.setString(1, username.getText());
			ResultSet rsGetCurrentUserBalance = stmtCurrentAmountOfMoney.executeQuery();
			int currentUserBalance = 0;
			
			//get current user balance
			if (rsGetCurrentUserBalance.next())
			{
				currentUserBalance = rsGetCurrentUserBalance.getInt("Balance");
			}
			
			
			//calculate current user new balance after depositing money
			int currentUserNewBalance = currentUserBalance + Integer.parseInt(depositMoneyAmount.getText());
			
			//update the balance of the current user in the database after he stored money
			String sqlDeposit = "UPDATE clients SET balance = " 
				      + currentUserNewBalance
				      + " WHERE username LIKE '"
				      + username.getText()
					  + "'";

			Statement stmtDeposit = connection.prepareStatement(sqlDeposit);

			//execute sql statements created above
			int didCurrentUserUpdateHisNewBalance = stmtDeposit.executeUpdate(sqlDeposit);
			
			if (didCurrentUserUpdateHisNewBalance > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully deposited " 
	   					   + depositMoneyAmount.getText()
	   					   + ". Your new balance is "
	   					   + currentUserNewBalance
	   					   + ".",
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
				
				//insert in database details about depositing
				String sqlTransactionNumber = "SELECT max(nr) FROM transactions";
				Statement stmtTransactionNumber = connection.createStatement();
				ResultSet rsTransactionNumber = stmtTransactionNumber.executeQuery(sqlTransactionNumber);
				int transactionNumber = 0;
				
				if (rsTransactionNumber.next())
				{
					transactionNumber = rsTransactionNumber.getInt(1);
				}
				
				transactionNumber++;
				
				int currentUserID = getUserID(username.getText());
				String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
				String currentTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
				String sqlDepositDetails = "INSERT INTO transactions(nr, clientid, ttype, amount, tdate, thour) "
										   + "VALUES ("
										   + transactionNumber
										   + ", "
										   + currentUserID
										   + ", 'depositing', "
										   + Integer.parseInt(depositMoneyAmount.getText())
										   + ", '" 
										   + currentDate 
										   + "', '"
										   + currentTime
										   + "')";
				Statement stmtDepositDetails = connection.createStatement();
				stmtDepositDetails.executeUpdate(sqlDepositDetails);
			}
			else
			{
				throw new OwnException("An error has occurred ! The depositing has been canceled.");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		catch (OwnException e) 
		{

		}
		catch(NumberFormatException e)
		{
			//if the current user specifies anything but a number in the deposit amount text field, error
			new OwnException("Specified amount of storage is invalid !");
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
	
	
	//withdraw money from your bank account
	public void withdrawMoney()
	{
		Connection connection = null;
		
		try 
		{	
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//get current user balance
			String sqlCurrentAmountOfMoney = "SELECT balance FROM clients WHERE username LIKE ?";
			PreparedStatement stmtCheckIfDestinationUserExists= connection.prepareStatement(sqlCurrentAmountOfMoney);
			stmtCheckIfDestinationUserExists.setString(1, username.getText());
			ResultSet rsGetCurrentUserBalance = stmtCheckIfDestinationUserExists.executeQuery();
			int currentUserBalance = 0;
			
			//get current user balance
			if (rsGetCurrentUserBalance.next())
			{
				currentUserBalance = rsGetCurrentUserBalance.getInt("Balance");
			}
			
			//if the current user has 0 balance, error
			if (currentUserBalance <= 0)
			{
				throw new OwnException("You are forbidden from making withdrawals.\n\n" 
										+ " > Current balance: " 
										+ currentUserBalance 
										+"\n\n"
										+"In order to have access to withdrawals, please make a storage first.");
			}
			//if the current user wishes to withdraw more than he is able to, error
			else if (currentUserBalance < Integer.parseInt(withdrawMoneyAmount.getText()))
			{
				throw new OwnException("Your withdrawal amount is greater than your current balance.\n\n"
									  + " > Current Balance: "
									  + currentUserBalance
									  + "\n"
									  + " > Desired withdrawal amount: "
									  + Integer.parseInt(withdrawMoneyAmount.getText()));
			}
			
			//calculate current user new balance after withdrawing money
			int currentUserNewBalance = currentUserBalance - Integer.parseInt(withdrawMoneyAmount.getText());
			
			//update the balance of the current user in the database after he had withdrawn money
			String sqlWithdraw = "UPDATE clients SET balance = " 
				      + currentUserNewBalance
				      + " WHERE username LIKE '"
				      + username.getText()
					  + "'";

			Statement stmtWithdraw = connection.prepareStatement(sqlWithdraw);

			//execute sql statements created above
			int didCurrentUserUpdateHisNewBalance = stmtWithdraw.executeUpdate(sqlWithdraw);
			
			if (didCurrentUserUpdateHisNewBalance > 0)
			{
				JOptionPane.showMessageDialog (null,
	   					   "You have successfully withdrawn " 
	   					   + withdrawMoneyAmount.getText()
	   					   + ". Your new balance is "
	   					   + currentUserNewBalance
	   					   + ".",
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
				
				//insert in database details about withdrawal
				String sqlTransactionNumber = "SELECT max(nr) FROM transactions";
				Statement stmtTransactionNumber = connection.createStatement();
				ResultSet rsTransactionNumber = stmtTransactionNumber.executeQuery(sqlTransactionNumber);
				int transactionNumber = 0;
				
				if (rsTransactionNumber.next())
				{
					transactionNumber = rsTransactionNumber.getInt(1);
				}
				
				transactionNumber++;
				
				int currentUserID = getUserID(username.getText());
				String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
				String currentTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
				String sqlWithdrawalDetails = "INSERT INTO transactions(nr, clientid, ttype, amount, tdate, thour) "
										   	  + "VALUES ("
										   	  + transactionNumber 
										   	  + ", "
										   	  + currentUserID
										   	  + ", 'withdrawal', "
										   	  + Integer.parseInt(withdrawMoneyAmount.getText())
										   	  + ", '"
										   	  + currentDate
										   	  + "', '"
										   	  + currentTime
										   	  + "')";
				Statement stmtWithdrawalDetails = connection.createStatement();
				stmtWithdrawalDetails.executeUpdate(sqlWithdrawalDetails);
			}
			else
			{
				throw new OwnException("An error has occurred ! The withdraw has been canceled.");
			}
		}
		catch(SQLException e)
		{
			
		} 
		catch (OwnException e) 
		{

		}
		catch(NumberFormatException e)
		{
			//if the current user specifies anything but a number in the withdraw amount text field, error
			new OwnException("Specified amount of withdraw is invalid !");
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
	
	//transfer money to any other user
	public void transferMoney()
	{
		Connection connection = null;
		
		try 
		{	
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//check if destination user exists in the database
			String sqlCheckIfDestinationUserExists = "SELECT firstname FROM clients WHERE username LIKE ?";
			PreparedStatement stmtCheckIfDestinationUserExists= connection.prepareStatement(sqlCheckIfDestinationUserExists);
			stmtCheckIfDestinationUserExists.setString(1, userToTransferMoneyTo.getText());
			ResultSet rsCheckIfDestinationUserExists = stmtCheckIfDestinationUserExists.executeQuery();
			
			if (rsCheckIfDestinationUserExists.next())
			{	
				//do nothing
			}
			else
			{
				//show a warning message
				throw new OwnException("Specified user does not exist in the database !");
			}
			
			//parse the text from the 'transferMoneyAmount' text field from string to int for further use
			int transferMoneyAmountParse = Integer.parseInt(transferMoneyAmount.getText());
			
			//get current user balance	
			String sqlGetCurrentUserBalance = "SELECT balance FROM clients WHERE username LIKE '" + username.getText() +"'";
			Statement stmtTransfer4 = connection.createStatement();
			ResultSet rs1 = stmtTransfer4.executeQuery(sqlGetCurrentUserBalance);
			int currentUserBalance = 0;
			
			if(rs1.next())
			{
				currentUserBalance = rs1.getInt("Balance");
			}
			
			//if the destination user is the same as the current user, error
			if (username.getText().equals(userToTransferMoneyTo.getText()))
			{
				throw new OwnException("You cannot transfer money to yourself !");
			}
			
			//if the current user has 0 balance, error
			if (currentUserBalance <= 0)
			{
				throw new OwnException("You are forbidden from making transfers.\n\n" 
										+ " > Current balance: " 
										+ currentUserBalance 
										+"\n\n"
										+"In order to have access to transfers, please make a storage first.");
			}
			//if the current user wishes to transfer more than he is able to, error
			else if (currentUserBalance < transferMoneyAmountParse)
			{
				throw new OwnException("Your transfer amount is greater than your current balance.\n\n"
									  + " > Current Balance: "
									  + currentUserBalance
									  + "\n"
									  + " > Desired transfer amount: "
									  + transferMoneyAmountParse);
			}
			
			//calculate current logged in user balance after the transfer it's done
			currentUserBalance = currentUserBalance - transferMoneyAmountParse;
			
			//get destination user current balance	
			String sqlGetDestinationUserBalance = "SELECT balance FROM clients WHERE username LIKE '" + userToTransferMoneyTo.getText() +"'";
			Statement stmtTransfer3 = connection.createStatement();
			ResultSet rs = stmtTransfer3.executeQuery(sqlGetDestinationUserBalance);
			int destinationUserCurrentBalance = 0;
			
			if(rs.next())
			{
				destinationUserCurrentBalance = rs.getInt("Balance");
			}
			
			//keep the transfer amount value for further use
			int transferMoneyAmountParseInitial = transferMoneyAmountParse;
			
			//calculate the destination user new balance after he received the money
			transferMoneyAmountParse = transferMoneyAmountParse + destinationUserCurrentBalance;
			
			//sql statement for updating the destination user new balance
			String sqlTransfer1 = "UPDATE clients SET balance = " 
							      + transferMoneyAmountParse
							      + " WHERE username LIKE '"
							      + userToTransferMoneyTo.getText()
								  + "'";
			
			//sql statement for updating the current user (the one who transfers) new balance
			String sqlTransfer2 = "UPDATE clients SET balance = " 
								  + currentUserBalance
								  + " WHERE username LIKE '"
								  + username.getText()
								  +"'";
			
			Statement stmtTransfer = connection.prepareStatement(sqlTransfer1);
			
			//execute sql statements created above
			int didDestinationUserReceiveMoney = stmtTransfer.executeUpdate(sqlTransfer1);
			int didCurrentUserUpdateHisNewBalance = stmtTransfer.executeUpdate(sqlTransfer2);
			
			//check if the destination user has successfully received the money
			//&&
			//check if the current user (the on who makes the transfer) has updated his new balance after transfering money
			if(didDestinationUserReceiveMoney > 0 && didCurrentUserUpdateHisNewBalance > 0) 
			{			
				JOptionPane.showMessageDialog (null,
	   					   "You successfully transfered " 
	   					   + transferMoneyAmountParseInitial 
	   					   + " to "
	   					   + userToTransferMoneyTo.getText()
	   					   + ".",
	   					   "Success", 
	   					   JOptionPane.INFORMATION_MESSAGE);
				
				//insert in database details about the transfer
				String sqlTransactionNumber = "SELECT max(nr) FROM transactions";
				Statement stmtTransactionNumber = connection.createStatement();
				ResultSet rsTransactionNumber = stmtTransactionNumber.executeQuery(sqlTransactionNumber);
				int transactionNumber = 0;
				
				if (rsTransactionNumber.next())
				{
					transactionNumber = rsTransactionNumber.getInt(1);
				}
				
				transactionNumber++;
				
				int currentUserID = getUserID(username.getText());
				String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
				String currentTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
				String sqlTransferDetails = "INSERT INTO transactions(nr, clientid, ttype, amount, sender, receiver, tdate, thour) "
										   + "VALUES ("
										   + transactionNumber
										   + ", "
										   + currentUserID
										   + ", 'transfer', "
										   + Integer.parseInt(transferMoneyAmount.getText())
										   + ", '"
										   + username.getText()
										   + "', '" 
										   + userToTransferMoneyTo.getText()
										   + "', '"
										   + currentDate 
										   + "', '"
										   + currentTime
										   + "')";;
				Statement stmtTransferDetails = connection.createStatement();
				stmtTransferDetails.executeUpdate(sqlTransferDetails);
				
			}
			else
			{
				throw new OwnException("An error has occured ! The transfer has been canceled.");
			}		
		} 	
		catch(SQLException e) 
		{

		} 
		catch (OwnException e)
		{
			
		}
		catch(NumberFormatException e)
		{
			//if the current user specifies anything but a number in the transfer amount text field, error
			new OwnException("Specified amount of transfer is invalid !");
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
	
	//get the transactions history from database and put it into a table
	void getTransactionsHistory(String usernameToGetHistoryFor)
	{
		Connection connection = null;
		
		try 
		{	
			//we have to explicitly set this to false here once again because of this scenario:
			//first, if someone who has transactions history logs in and check his transactions history,
			//then if someone who doesn't have transaction history logs in and checks his transactions history,
			//he will see an empty table with the length of the transactions history table of the previous
			//logged in user
			hasCurrentUserMadeTransactions = false;
			
			//establish a connection to the database
			JDBCConnectionPool pool = new JDBCConnectionPool();	
			connection = pool.checkOut();
			
			//get current used ID for further use
			int currentUserID = getUserID(username.getText());
			
			//get rows count from 'transactions' table so we know the dimension of the table to be viewed by the user
			String sqlGetRowsCount = "SELECT count(ttype) FROM transactions WHERE clientid = ? OR receiver LIKE ?";
			PreparedStatement stmtGetRowsCount = connection.prepareStatement(sqlGetRowsCount);
			stmtGetRowsCount.setInt(1, currentUserID);
			stmtGetRowsCount.setString(2, usernameToGetHistoryFor);
			ResultSet rsGetRowsCount = stmtGetRowsCount.executeQuery();
			int rowsCount = 0;
			
			if (rsGetRowsCount.next())
			{
				rowsCount = rsGetRowsCount.getInt(1);
			}
			
			//properly initialize the matrix of objects which will be used for creating the transactions table
			transactionsHistoryTableContent = new Object[rowsCount][7];
			
			//get current user transactions
			String sqlCurrentUserTransactions = "SELECT * FROM transactions WHERE clientid = ? OR receiver LIKE ?"
											  + "ORDER BY nr ASC";
			PreparedStatement stmtCurrentUserTransactions = connection.prepareStatement(sqlCurrentUserTransactions);
			stmtCurrentUserTransactions.setInt(1, currentUserID);
			stmtCurrentUserTransactions.setString(2, username.getText());
			ResultSet rsCurrentUserTransactions = stmtCurrentUserTransactions.executeQuery();

			//initialize the matrix of objects with the transactions information taken from database
			int rowsNumber = 0, numberOfCurrentTransaction = 1;
			
			while (rsCurrentUserTransactions.next())
			{
				hasCurrentUserMadeTransactions = true;
				
				transactionsHistoryTableContent[rowsNumber][0] = numberOfCurrentTransaction;
				transactionsHistoryTableContent[rowsNumber][1] = rsCurrentUserTransactions.getString("TType");
				transactionsHistoryTableContent[rowsNumber][2] = rsCurrentUserTransactions.getString("Sender");
				transactionsHistoryTableContent[rowsNumber][3] = rsCurrentUserTransactions.getString("Receiver");
				transactionsHistoryTableContent[rowsNumber][4] = rsCurrentUserTransactions.getString("Amount");
				transactionsHistoryTableContent[rowsNumber][5] = rsCurrentUserTransactions.getString("TDate");
				transactionsHistoryTableContent[rowsNumber][6] = rsCurrentUserTransactions.getString("THour");
				
				rowsNumber++;
				numberOfCurrentTransaction++;
			}
			
			//additional vector of objects needed for instantiating transactionsHistoryTable
			Object[] columns = {"Nr.", "Type", "Sender", "Receiver", "Amount", "Date", "Hour"};
			
			//fill the content of the table with the information received above
			transactionsHistoryTable = new JTable(transactionsHistoryTableContent, columns);
			
			//set some columns dimensions
			transactionsHistoryTable.getColumn("Nr.").setMaxWidth(35);
			transactionsHistoryTable.getColumn("Type").setMinWidth(36);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		catch(NumberFormatException e)
		{
			//if the current user specifies anything but a number in the withdraw amount text field, error
			new OwnException("Specified amount of withdraw is invalid !");
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
	
	//print transaction's history in PDF format
	public void printTransactionsHistoryPDF()
	{
		Document document = new Document(PageSize.B4);
		
        try 
        {
            PdfWriter.getInstance(document,new FileOutputStream(username.getText() + "'s transactions history.pdf"));
            
            //open (and customize) the document so we can write in it after
            document.setMargins(2, 2, 2, 2);
            document.open();    
            document.add(new Paragraph("                     " + username.getText() + "'s transactions history"));
            document.add(new Paragraph(" "));
            
            //create a pdftable object in which we will store what's in the actual transactions table
            PdfPTable tab = new PdfPTable(7);
            
            //add the corresponding columns to the new pdftable object
            tab.addCell("Nr.");
            tab.addCell("Type");
            tab.addCell("Sender");
            tab.addCell("Receiver");
            tab.addCell("Amount");
            tab.addCell("Date");
            tab.addCell("Hour");
           
            //get the number of rows of the actual transactions table so we know the dimension of the new pdftable object
            int count = transactionsHistoryTable.getRowCount();
            
            //iterate through the actual transactions table and add each row in the new pdftable object
            for(int i = 0; i < count; i++)
            {
            	Object numberOfTransaction = transactionsHistoryTable.getModel().getValueAt(i, 0);
            	Object typeColumn = transactionsHistoryTable.getModel().getValueAt(i, 1);
            	Object senderColumn = transactionsHistoryTable.getModel().getValueAt(i, 2);
            	Object receiverColumn = transactionsHistoryTable.getModel().getValueAt(i, 3);
            	Object amountColumn = transactionsHistoryTable.getModel().getValueAt(i, 4);;
            	Object dateColumn = transactionsHistoryTable.getModel().getValueAt(i, 5);
            	Object hourColumn = transactionsHistoryTable.getModel().getValueAt(i, 6);
     
            	tab.addCell(numberOfTransaction.toString());
            	tab.addCell((String)typeColumn);
            	tab.addCell((String)senderColumn);
            	tab.addCell((String)receiverColumn);
            	tab.addCell((String)amountColumn);
            	tab.addCell((String)dateColumn);
            	tab.addCell((String)hourColumn);
            }
            
            //finally, add the pdftable object to the document
            document.add(tab);
          
            //add date & time of the generation of the pdf
			String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			String currentTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
			document.add(new Paragraph("                     Date: " + currentDate));
			document.add(new Paragraph("                     Time: " + currentTime));
			
            //then close the document
            document.close();
            
            //after the document has created, we give the option to see it or not to see it
            if(JOptionPane.showConfirmDialog(null,
											 "The document has been created. Would you like to see it ?", "Question",
											 JOptionPane.YES_NO_OPTION,
											 JOptionPane.QUESTION_MESSAGE) 
											== JOptionPane.YES_OPTION)
            {
            	if (Desktop.isDesktopSupported()) 
            	{
            	    try 
            	    {
            	        File myFile = new File("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\" 
            	        					   + username.getText() + "'s transactions history.pdf");
            	        Desktop.getDesktop().open(myFile);
            	    } 
            	    catch (IOException ex) 
            	    {
            	        try 
            	        {
							throw new OwnException("There has been an error opening the document of transactions history.");
						} 
            	        catch (OwnException e) 
            	        {

						}
            	    }
            	}
            }
			
        } 
        catch (DocumentException e) 
        {
            e.printStackTrace();
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
	}
	
	//print transaction's history in excel format - not implemented yet
	public void printTransactionsHistoryWORD()
	{
		//not implemented yet
		
        //if the user p;res
		JOptionPane.showMessageDialog(null,
				  					  "This function is not avalaible !",
				  					  "Error",
				  					  JOptionPane.ERROR_MESSAGE);
	}
	
	//print transaction's history in excel format
	public void printTransactionsHistoryEXCEL(JTable table)
	{
	    Workbook wb = new XSSFWorkbook();
	    org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet();
	    Row row = sheet.createRow(2); //Row created at line 3
	    TableModel model = table.getModel(); //Table model

	    
	    //first, create a row at line 0 for columns
	    Row headerRow = sheet.createRow(0);
	    
	    //then write the columns names
	    for(int headings = 0; headings < model.getColumnCount(); headings++) 
	    {
	        headerRow.createCell(headings).setCellValue(model.getColumnName(headings));
	    }

	    //then, we get each value from transactionsHistoryTable table and include it to the excel file
	    for(int rows = 0; rows < model.getRowCount(); rows++)
	    { 
	        for(int cols = 0; cols < table.getColumnCount(); cols++)
	        { 
	        	if (cols == 0)
	        	{
	        		row.createCell(cols).setCellValue((int)model.getValueAt(rows, cols));
	        	}
	        	else
	        	{
	        		row.createCell(cols).setCellValue((String)model.getValueAt(rows, cols));
	        	}
	        }
	        //set the row to the next one in the sequence 
	        row = sheet.createRow((rows + 3)); 
	    }
	  
	    //write the whole table in excel file
	    try 
	    {
			wb.write(new FileOutputStream("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\" 
										+ username.getText() + "'s transactions history.xlsx"));
		} 
	    catch (IOException e) 
	    {

		}
	    finally
	    {
	    	try 
	    	{
	    		//finally, close the file, no matter what happens
				wb.close();
			} 
	    	catch (IOException e) 
	    	{
	    		
			}
	    }
	    
        //after the document has created, we give the option to see it or not to see it
        if(JOptionPane.showConfirmDialog(null,
										 "The document has been created. Would you like to see it ?", "Question",
										 JOptionPane.YES_NO_OPTION,
										 JOptionPane.QUESTION_MESSAGE) 
										== JOptionPane.YES_OPTION)
        {
        	if (Desktop.isDesktopSupported()) 
        	{
        	    try 
        	    {
        	        File myFile = new File("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\" 
        	        					   + username.getText() + "'s transactions history.xlsx");
        	        Desktop.getDesktop().open(myFile);
        	    } 
        	    catch (IOException ex) 
        	    {
        	        try 
        	        {
						throw new OwnException("There has been an error opening the document of transactions history.");
					} 
        	        catch (OwnException e) 
        	        {

					}
        	    }
        	}
        }
	}
	
	//handlers for a variety of components, such as JButtons, JMenuItem, etc.
	public void actionPerformed(ActionEvent a) 
	{		
		if (a.getSource() == logIn)
		{
			logIn();
		}
		
		else if (a.getSource() == logOut)
		{
			if(JOptionPane.showConfirmDialog(null,
						"Are you sure you want to log out ?", "Question",
					 	JOptionPane.YES_NO_OPTION,
					 	JOptionPane.QUESTION_MESSAGE) 
						== JOptionPane.YES_OPTION)
			{
				goToMainFrame();
			}
		}
		
		else if (a.getSource() == closeApp)
		{
			if(JOptionPane.showConfirmDialog(null,
                    						 	"Do you really wish to close the application ?", "Question",
                    						 	JOptionPane.YES_NO_OPTION,
                    						 	JOptionPane.QUESTION_MESSAGE) 
												== JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
		
		else if (a.getSource() == registerNewAccount)
		{
			createRegisterPage();
		}
		
		else if (a.getSource() == recoverPassword)
		{
			createLostPasswordPage();
		}
			
		else if (a.getSource() == confirm)
		{
			if (checkRegisterForm())
			{
				getMaxClientID();
				register();
				goToMainFrame();
			}
		}
		
		else if(a.getSource() == cancel)
		{
			if (JOptionPane.showConfirmDialog(null,
											  "Are you sure you want to cancel the registration ?", "Question",
											  JOptionPane.YES_NO_OPTION,
											  JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			{
				goToMainFrame();
			}		
		}
		
		else if (a.getSource() == back)
		{
			goToMainFrame();
		}
		
		else if (a.getSource() == done)
		{
			lostPassword();
		}
	
		else if (a.getSource() == submitDeposit)
		{
			depositMoney();
		}
		
		else if (a.getSource() == submitWithdraw)
		{
			withdrawMoney();
		}
		
		else if (a.getSource() == submitTransfer)
		{
			transferMoney();
		}
		
		else if (a.getSource() == printPDF)
		{
			printTransactionsHistoryPDF();
		}
		
		else if (a.getSource() == printWORD)
		{
			printTransactionsHistoryWORD();
		}
		
		else if (a.getSource() == printEXCEL)
		{
			printTransactionsHistoryEXCEL(transactionsHistoryTable);
		}
		
		else if (a.getSource() == submitChangePassword)
		{
			changePassword();
		}
		
		else if (a.getSource() == submitNewFirstName)
		{
			changeFirstName();
		}
		
		else if (a.getSource() == submitNewLastName)
		{
			changeLastName();
		}
		
		else if (a.getSource() == submitNewEmail)
		{
			changeEmail();
		}
		
		else if (a.getSource() == submitNewBirthDate)
		{
			if (checkChangeBirthdateForm())
			{
				changeBirthDate();
			}
		}
		
		else if (a.getSource() == choosePhotoFromPC)
		{
			browsePhotos();
		}
		
		else if (a.getSource() == submitNewPhoto)
		{
			if (!defaultPhoto)
			{
				if (didUserChoosePhoto)
				{
					if (checkPhoto())
					{
						changeCustomPhoto();
					}
					else
					{
						JOptionPane.showMessageDialog (null,
										"The image is too large. Maximum dimension "
									  + "of the selected image must be 120 x 65 !",
									  	"Error", 
									  	JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog (null,
									"You must select an image first !",
									"Error", 
									JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				switch (whichColorIsChosen) 
				{
	            	case 1: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            			                  + "default profile photos\\default black image.png");
	            			break;
	            	case 2: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
			                  			     + "default profile photos\\default cyan image.png");
	            			break;
	            	case 3: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							 + "default profile photos\\default gray image.png");
	            			break;
	            	case 4: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							 + "default profile photos\\default red image.png");
	            			break;
	            	case 5: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							 + "default profile photos\\default blue image.png");
	            			break;
	            	case 6: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							 + "default profile photos\\default orange image.png");
	            			break;
	            	case 7: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							 + "default profile photos\\default yellow image.png");
	            			break;
	            	case 8: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							 + "default profile photos\\default purple image.png");
	            			break;
	            	case 9: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            						 	 + "default profile photos\\default pink image.png");
	            			break;
	            	case 10: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            						 	  + "default profile photos\\default brown image.png");
	            			break;
	            	case 11: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
	            							  + "default profile photos\\default dark blue image.png");
	            			break;
	            	case 12: changeDefaultPhoto("D:\\Programe\\Eclipse IDE\\eclipse\\projects\\[OP]Bank\\icons\\"
			                  					+ "default profile photos\\default dark green image.png");
	                    	break;
				}
			}
		}
			
		else if (a.getActionCommand().equals("New Account"))
		{
			createRegisterPage();
		}
		
		else if (a.getActionCommand().equals("Recover Password"))
		{
			createLostPasswordPage();
		}
		
		else if (a.getActionCommand().equals("Exit"))
		{
			if(JOptionPane.showConfirmDialog(null,
					                         "Do you really wish to close the application ?", "Question",
					                         JOptionPane.YES_NO_OPTION,
					                         JOptionPane.QUESTION_MESSAGE) 
											 == JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
		
		else if (a.getActionCommand().equals("About"))
		{
			JOptionPane.showMessageDialog (null,
										   "Name: Bank Account Handler\n"
										   + "Author: Marangoci Mihai\n"
										   + "Start date: 25/05/2016\n"
										   + "Estimated Completion Date: 05/06/2016\n"
										   + "__________________________________________",
										     "Project details", 
										   JOptionPane.INFORMATION_MESSAGE);
		}
			
		else if (a.getActionCommand().equals("Male"))
		{
			maleOrFemale = "M";
		}	
			
		else if (a.getActionCommand().equals("Female"))
		{
			maleOrFemale = "F";
		}				
	}
}