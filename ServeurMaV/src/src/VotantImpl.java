package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MaV.Electeur;
import MaV._VotantImplBase;

public class VotantImpl extends _VotantImplBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean aDejaVote(int insee) {
		// TODO Auto-generated method stub
		String query = "SELECT aVote FROM electeur WHERE insee = " + insee ; 
		//System.out.println(query);
		boolean ok = false;
		ResultSet rs = DBUtils.select(query);
		try {
			rs.next();
			ok = rs.getBoolean(1);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;

	}

	public Electeur verifierElecteur(int insee, int code) {
		// TODO Auto-generated method stub
		Electeur votant = null;
		
		String query = "SELECT e.nom,e.prenom, b.nom, ca.nom, ci.nom, d.nom " +
				"  FROM electeur e, bureau b, canton ca, circonscription ci,dept d, lieu l" +
				"  WHERE insee = " + insee + " and code = " + code 
				+" AND b.idBureau = e.idBureau AND l.idBureau = e.idBureau AND ca.idCanton = l.idCanton " +
						" AND ci.idCirconscription = l.idCirconscription AND d.idDept = l.idDept"; 
		//System.out.println(query);
		ResultSet rs = DBUtils.select(query);
		int taille = 0;
		try {			
			while(rs.next())
			{
				taille++;
			}
			
			if (taille != 1)
				return null;
			
			rs.beforeFirst();
			rs.next();
			votant = new Electeur(insee, rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6) );
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return votant;
	}

	public void votePour(int id, int insee) {
		// TODO Auto-generated method stub
		
		if(!this.aDejaVote(insee)){
		
		ListeCImpl lcand = new ListeCImpl();
		int nbV = lcand.getNbVotes(id) + 1;

		ArrayList cols = new ArrayList();
		cols.add("nbVotes");
		
		ArrayList val = new ArrayList();
		val.add(new Integer(nbV));
		
		DBUtils.update("candidat",cols , val, "idCandidat = " + id);
		
		cols.clear();
		cols.add("aVote");
		
		val.clear();
		val.add(new Boolean(true));
		DBUtils.update("electeur",cols , val, "insee = " + insee);
		}
		else{
			System.out.println("A deja voté!!");
		}
	}

	public void deleteElecteur(int id) {
		// TODO Auto-generated method stub
		
	}

	

	public boolean saveElecteur(Electeur e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean exists(int insee, int code) {
		// TODO Auto-generated method stub
		boolean res = false;
		
		String query = "SELECT COUNT(*) " +
		"  FROM electeur e" +
		"  WHERE insee = " + insee + " and code = " + code; 
		ResultSet rs = DBUtils.select(query);
			
		try {
			if(rs.next()){
				if(rs.getInt(1)>0)
				res = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
