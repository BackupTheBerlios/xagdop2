package src;

import java.sql.ResultSet;
import java.sql.SQLException;

import MaV._StatistiquesImplBase;

public class StatsImpl extends _StatistiquesImplBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public int getNbVotes(int id) {
		// TODO Auto-generated method stub
		int nb = 0;
		String query = "SELECT sum(nbVotes) FROM vote WHERE idCandidat = " + id + " group by idCandidat"; 
		
		ResultSet rs = DBUtils.select(query);
		if(rs!=null){
			
			try {			

				if(rs.next())
					nb = rs.getInt(1);
				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nb;
	
	}

	public int getNbVotesParBureau(int idB, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNbVotesParCanton(int idCa, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNbVotesParCirc(int idCi, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNbVotesParDept(int idD, int id) {
		// TODO Auto-generated method stub
		return 0;
	}


}
