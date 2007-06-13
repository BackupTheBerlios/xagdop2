package src;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import javax.sql.DataSource;





public class DBUtils
{
	// log4J Category



	static Driver drv = null;
	static DataSource datasrc = null;
	static Connection conn = null;

	/**
	 * Boolean to check if nobody try to initialize more than
	 * one connection to the DataBase
	 */
	protected static boolean m_bDBconnected = false;


	private  static void CreateDBConnection()
	{
		try
		{
			if (m_bDBconnected == false)	{

				try {
					//Creation of the driver to use the Connection Pool

					try {
						//System.out.println("Driver Name : "+ReadDbConfig.getInstance().getDbDriverName());
						Class.forName(ReadDbConfig.getInstance().getDbDriverName() );
						// Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					try {
						ReadDbConfig rdc = ReadDbConfig.getInstance();
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						
						conn = DriverManager.getConnection(rdc.getUrl(), rdc.getReadOnlyLogin(), rdc.getReadOnlyPassword());


					} catch (Exception e) {
						System.out.println("Exception 3");
						e.printStackTrace();
					}

				}
				catch (Exception e) {
					System.out.println("Exception 2");
					e.printStackTrace();
				}
				m_bDBconnected = true;
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception 1");
			e.printStackTrace();
		}
	}

	public static ResultSet select(String query){
		Statement stm;
		ResultSet rs = null;
		try {
			stm = getConnection().createStatement();
			stm.execute (query); 
			rs = stm.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public static int insert(String table, ArrayList cols, ArrayList values){
		PreparedStatement pstm;
		int ok = -1;

		String query = "INSERT INTO " + table + " (";
		for(int i=0; i<cols.size(); i++)
		{
			query += cols.get(i);
			if (i != cols.size()-1)
				query += " , ";
		}
		query += " ) VALUES ( ";

		for(int i=0; i<values.size(); i++)
		{
			query += " ? ";
			if (i != values.size()-1)
				query += " , ";
		}
		query += " ) ";
		try {
			pstm = getConnection().prepareStatement(query);
			for(int i=0; i<values.size(); i++)
			{
				if (values.get(i).getClass().equals(Integer.class))
					pstm.setInt(i+1, ((Integer)values.get(i)).intValue());
				else if (values.get(i).getClass().equals(String.class))
					pstm.setString(i+1, ((String)values.get(i)).toString());
				else if (values.get(i).getClass().equals(Boolean.class))
					pstm.setBoolean(i+1, ((Boolean)values.get(i)).booleanValue());
			}
			ok = pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ok;

	}

	public static int update(String table, ArrayList cols, ArrayList values, String condition){
		PreparedStatement pstm;
		int ok = -1;

		String query = "UPDATE " + table + " SET ";
		
		for(int i=0; i<cols.size(); i++)
		{
			query += cols.get(i) + " =  ?";		  
			if (i != cols.size()-1)
				query += " , ";
		}
		query += " WHERE " + condition;

		try {
			//System.out.println(query);
			pstm = getConnection().prepareStatement(query);
			for(int i=0; i<values.size(); i++)
			{
				//System.out.println(values.get(i).getClass().toString());
				if (values.get(i).getClass().equals(Integer.class))
					pstm.setInt(i+1, ((Integer)values.get(i)).intValue());
				else if (values.get(i).getClass().equals(String.class))
					pstm.setString(i+1, ((String)values.get(i)).toString());
				else if (values.get(i).getClass().equals(Boolean.class))
					pstm.setBoolean(i+1, ((Boolean)values.get(i)).booleanValue());
			}
			ok = pstm.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ok;

	}
	
	public static int delete(String table, String condition){
		PreparedStatement pstm;
		int ok = -1;

		String query = "DELETE FROM " + table + " WHERE "+condition;
		try {
			//System.out.println(query);
			pstm = getConnection().prepareStatement(query);
			ok = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ok;
		
	}

	public  static Connection getConnection()
	{
		if(conn==null)
			CreateDBConnection();

		return conn;

	}

}