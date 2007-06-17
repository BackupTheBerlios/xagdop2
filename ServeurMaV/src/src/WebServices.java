package src;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class WebServices {
//	 log4J Category



	static Driver drv = null;
	static DataSource datasrc = null;
	static Connection conn = null;

	/**
	 * Boolean to check if nobody try to initialize more than
	 * one connection to the DataBase
	 */
	protected static boolean m_bDBconnected = false;
	
	
	public static void main(String[] args) {
		List toto = getResultatsWebServices();
		for (int i=0;i<toto.size();i++) {
			String titi = (String) toto.get(i);
			System.out.println(titi);
		}
		System.out.println("fini !");
	}

		
		
	public static List getResultatsWebServices() {
		List resultats = new ArrayList();
		
		String query = "SELECT c.nom, sum(v.nbVotes) FROM Candidat c, Vote v WHERE v.idCandidat = c.idCandidat group by c.idCandidat"; 
		//String query = "SELECT idCandidat, sum(nbVotes) FROM Vote group by idCandidat"; 
		
		String nomCandidat = "";
		int nbVotes = 0;
		String resultat = "";
		ResultSet rs = WebServices.select(query);
		try {
			while(rs.next()) {
				nomCandidat = rs.getString(1);
				nbVotes = rs.getInt(2);
				resultat = nomCandidat + " : " + nbVotes;
				resultats.add(resultat);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return resultats;
	}

	private  static void CreateDBConnection()
	{
		try
		{
			if (m_bDBconnected == false)	{

				try {
					//Creation of the driver to use the Connection Pool

					try {
						Class.forName("com.mysql.jdbc.Driver");
						// Class.forName("oracle.jdbc.driver.OracleDriver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					try {
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						
						conn = DriverManager.getConnection("jdbc:mysql://localhost/mav", "claire", "claire");


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
			Connection con = getConnection();
			if (con!=null) {
				stm = con.createStatement();
				stm.execute (query); 
				rs = stm.getResultSet();
			}
			else {
				System.out.println("Connexion nulle");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}


	public  static Connection getConnection()
	{
		if(conn==null)
			CreateDBConnection();

		return conn;

	}

}