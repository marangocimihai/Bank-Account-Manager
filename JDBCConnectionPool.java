package jdbcwithobjectpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionPool extends ObjectPool<Connection> 
{
	public JDBCConnectionPool() 
	{
		super();
		
	    try 
	    {
	      Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	    }
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    }
	}

	@Override
	protected Connection create() 
	{
		try 
		{
	      return (DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "miTzuliK", "chelseafan"));
	    } 
	    catch (SQLException e) 
	    {
	      e.printStackTrace();
	      return (null);
	    }
	}

	@Override
	public void expire(Connection o) 
	{
	    try 
	    {
	      ((Connection) o).close();
	    } 
	    catch (SQLException e) 
	    {
	      e.printStackTrace();
	    }
	}

	@Override
	public boolean validate(Connection o) 
	{
	    try 
	    {
	      return (!((Connection) o).isClosed());
	    } 
	    catch (SQLException e) 
	    {
	      e.printStackTrace();
	      return (false);
	    }
	}
}