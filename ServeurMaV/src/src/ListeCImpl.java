package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MaV.Candidat;
import MaV.Mandat;


public class ListeCImpl extends MaV._ListeCImplBase  {

	public boolean saveCandidat(Candidat c) {
		ArrayList cols = new ArrayList();
		ArrayList values = new ArrayList();


		cols.add("nom");
		cols.add("prenom");
		cols.add("age");
		cols.add("profession");

		values.add(c.nom);
		values.add(c.prenom);
		values.add(new Integer(c.age));
		values.add(c.profession);
		
		// si pas d'id, alors création
		if (c.id==0) {
			DBUtils.insert("candidat", cols, values);

			/** TODO Nico créer une ligne correspondante dans la table vote, sinon le mettre à jour.
			 * 
			 */
			
		}
		// sinon, mise à jour
		else {
			DBUtils.update("candidat", cols, values, "idCandidat = " + c.id);
		}
		
		
		
		return true;
	}

	public Candidat[] getAllCandidats() {

		Candidat ca;
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
				ca = new Candidat(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
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
		String query = "SELECT nbVotes FROM candidat WHERE idCandidat = " + id ; 
		//System.out.println(query);
		int nb = -1;
		ResultSet rs = DBUtils.select(query);
		try {
			rs.next();
			nb = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nb;
	}

	public Mandat[] getMandats(int id) {
		Mandat ma;
		Mandat[] result = null;

		String query = "SELECT m.idMandat, m.titre, m.annee FROM mandat m , brigue b WHERE b.idCandidat = " + id + " AND m.idMandat = b.idMandat"; 
		//System.out.println(query);
		ResultSet rs = DBUtils.select(query);
		int taille = 0;

		try {			
			while(rs.next())
			{
				taille++;
			}
			rs.beforeFirst();

			result = new Mandat[taille];

			int i = 0;
			while(rs.next()){
				ma = new Mandat(rs.getInt(1), rs.getString(2), rs.getString(3));
				result[i] = ma;
				i++;
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public void deleteCandidat(int id) {
		// TODO Auto-generated method stub
		
	}



}



