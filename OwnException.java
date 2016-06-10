package management;

import javax.swing.JOptionPane;

public class OwnException extends Exception
{
	private static final long serialVersionUID = 1L;
	////////////////////////////////////////////////
	
	OwnException()
	{
		
	}
	
	
	OwnException(String message)
	{
		JOptionPane.showMessageDialog(null,
				  message,
				  "Error",
				  JOptionPane.ERROR_MESSAGE);
	}

}
