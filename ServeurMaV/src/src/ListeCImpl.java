package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MaV.Candidat;


public class ListeCImpl extends MaV._ListeCImplBase  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean saveCandidat(Candidat c) {
		ArrayList cols = new ArrayList();
		ArrayList values = new ArrayList();

		cols.add("nom");
		cols.add("prenom");
		cols.add("age");
		cols.add("profession");

		values.add(c.nom());
		values.add(c.prenom());
		values.add(new Integer(c.age()));
		values.add(c.profession());

		
		DBUtils.update("candidat", cols, values, "idCandidat = " + c.id());

		return true;
	}

	public Candidat[] getAllCandidats() {
		CandidatImpl ca;
		Candidat[] result = null;

		String query = "SELECT * FROM candidat"; 
		ResultSet rs = DBUtils.select(query);
		int taille = 0;

		try {			
			while(rs.next()){
				taille++;
			}
			rs.beforeFirst();

			result = new Candidat[taille];

			int i = 0;
			while(rs.next()){
				ca = new CandidatImpl();
				ca.id(rs.getInt(1));
				ca.nom(rs.getString(2));
				ca.prenom(rs.getString(3));
				ca.age(rs.getInt(4));
				ca.profession(rs.getString(5));
				result[i] = ca;
				i++;
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	public int getNbVotes(int id) {
		String query = "SELECT sum(nbVotes) FROM vote WHERE idCandidat = " + id + " group by idCandidat"; 
		//System.out.println(query);
		int nb = -1;
		ResultSet rs = DBUtils.select(query);
		try {
			if (rs.next())
				nb = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nb;
	}



	public void deleteCandidat(int id) {
		DBUtils.delete("candidat", "idCandidat="+id);
	}

	public Candidat createCandidat(String nom, String prenom, int age, String profession) {


		ArrayList cols = new ArrayList();
		ArrayList values = new ArrayList();

		cols.add("nom");
		cols.add("prenom");
		cols.add("age");
		cols.add("profession");

		values.add(nom);
		values.add(prenom);
		values.add(new Integer(age));
		values.add(profession);

		// si pas d'id, alors cr�ation
		DBUtils.insert("candidat", cols, values);
		String query = "SELECT * FROM candidat where idCandidat=LAST_INSERT_ID()"; 
		ResultSet rs = DBUtils.select(query);
		CandidatImpl ca = null;
		try {			
			rs.next();
			ca = new CandidatImpl();
			ca.id(rs.getInt(1));
			ca.nom(rs.getString(2));
			ca.prenom(rs.getString(3));
			ca.age(rs.getInt(4));
			ca.profession(rs.getString(5));

			rs.close();
			

			String requete = "SELECT idBureau FROM bureau";

			ResultSet rq = DBUtils.select(requete);
			
			ArrayList colsInsertVote = new ArrayList();
			ArrayList valuesInsertVote = new ArrayList();

			colsInsertVote.add("idCandidat");
			colsInsertVote.add("idBureau");
			colsInsertVote.add("nbVotes");
				
			while (rq.next()) {	
				int idBureau = rq.getInt(1);
				valuesInsertVote = new ArrayList();
				valuesInsertVote.add(new Integer(ca.id()));
				valuesInsertVote.add(new Integer(idBureau));
				valuesInsertVote.add(new Integer(0));
				DBUtils.insert("vote", colsInsertVote, valuesInsertVote);
			}

			rq.close();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ca;
	}

	public String getNomCandidat(int idCandidat) {
		// TODO Auto-generated method stub
		String nom = "";
		Candidat[] ca = getAllCandidats();
		for(int i=0; i<ca.length; i++){
			if(ca[i].id() == idCandidat)
				nom = ca[i].nom();
		}
		
		return nom;
	}



}



