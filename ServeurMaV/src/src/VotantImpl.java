package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MaV.Candidat;
import MaV.Electeur;
import MaV.Stats;
import MaV._VotantImplBase;

public class VotantImpl extends _VotantImplBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean aDejaVote(int insee) {
		// TODO Auto-generated method stub
		String query = "SELECT aVote FROM electeur WHERE insee = " + insee ; 
		
		boolean ok = false;
		ResultSet rs = DBUtils.select(query);
		try {
			if(rs.next())
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

		String query = "SELECT e.nom,e.prenom, b.idBureau, e.code" +
		"  FROM electeur e, bureau b, canton ca, circonscription ci,dept d, lieu l" +
		"  WHERE insee = " + insee + " and code = " + code 
		+" AND b.idBureau = e.idBureau AND l.idBureau = e.idBureau AND ca.idCanton = l.idCanton " +
		" AND ci.idCirconscription = l.idCirconscription AND d.idDept = l.idDept"; 
		
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
			votant = new Electeur(insee, rs.getString(1),rs.getString(2), rs.getInt(3), rs.getInt(4) );

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return votant;
	}

	public void votePour(int id, int insee, int bureau) {
		// TODO A refaire avec la nouvelle base

		if(!this.aDejaVote(insee)){

			ListeCImpl lcand = new ListeCImpl();
			int nbV = lcand.getNbVotes(id) + 1;

			ArrayList cols = new ArrayList();
			cols.add("nbVotes");

			ArrayList val = new ArrayList();
			val.add(new Integer(nbV));

			DBUtils.update("vote",cols , val, "idCandidat = " + id);

			cols.clear();
			cols.add("aVote");

			val.clear();
			val.add(new Boolean(true));
			DBUtils.update("electeur",cols , val, "insee = " + insee);
		}
		else{
			System.out.println("A bla voté!!");
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

	public String getNom(int insee) {
		String nom = "";

		String query = "SELECT e.nom " +
		"  FROM electeur e" +
		"  WHERE insee = " + insee; 
		ResultSet rs = DBUtils.select(query);

		if (rs!=null) {
			try {
				if(rs.next()){
					nom = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return nom;
	}


	public void votePour2(int id, int insee, int bureau) {
		// TODO Auto-generated method stub
		if(!this.aDejaVote(insee)){

			ListeCImpl lcand = new ListeCImpl();
			int nbV = lcand.getNbVotes(id) + 1;

			ArrayList cols = new ArrayList();
			cols.add("nbVotes");

			ArrayList val = new ArrayList();
			val.add(new Integer(nbV));

			DBUtils.update("vote",cols , val, "idCandidat = " + id);

			cols.clear();
			cols.add("aVote");

			val.clear();
			val.add(new Boolean(true));
			DBUtils.update("electeur",cols , val, "insee = " + insee);
		}
		else{
			System.out.println("A déjà voté!!");
		}

//		notification du vote aux clients

		//Recuperation du nombre de votes par candidat
		StatsImpl statsI = StatsImpl.getInstance();
		ListeCImpl lca = new ListeCImpl();
		Candidat[] ca = lca.getAllCandidats();
		
		/*
		 * Structure de Stats : 
		 * idCandidat 	: int
		 * idBureau		: int
		 * nbVotes		: int
		 */
		
		/** TODO 
		 * Pour l'instant, total des votes pour chaque candidat, à modifier pour des stats plus précises
		 */
		int l = ca.length;
		Stats[] s = new Stats[l];
		for(int i=0; i<ca.length;i++)
		{
			Stats tmp = new Stats();
			tmp.idCandidat = ca[i].id();
			tmp.nbVotes = statsI.getNbVotes(ca[i].id());
			tmp.idBureau = 1;
			//System.out.println(tmp.nbVotes + "  "+ tmp.idCandidat);
			s[i] = tmp;
		}
		
		for (int i=0; i<statsI.getListe().size(); i++){
			statsI.getListe().get(i).callback(s);
		}
	}

	

}
