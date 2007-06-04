package src;

import java.sql.ResultSet;
import java.sql.SQLException;

import MaV.Mandat;
import MaV._CandidatImplBase;

public class CandidatImpl  extends _CandidatImplBase {
	int id;
	String nom;
	String prenom;
	int age;
	String profession;

	public String _toString() {
		return nom()+" "+prenom();
	}

	public int age() {
		return age;
	}

	public void age(int newAge) {
		age= newAge;
	}

	public int id() {
		return id;
	}

	public void id(int newId) {
		id = newId;
	}

	public String nom() {
		return nom;
	}

	public void nom(String newNom) {
		nom = newNom;
	}

	public String prenom() {
		return prenom;
	}

	public void prenom(String newPrenom) {
		prenom = newPrenom;
	}

	public String profession() {
		return profession;
	}

	public void profession(String newProfession) {
		profession = newProfession;
	}

	public Mandat createMandat(String titre, String anneeD, String anneeF) {
		// TODO Auto-generated method stub
		return null;
	}

	public Mandat[] getMandats() {
		Mandat ma;
		Mandat[] result = null;

		String query = "SELECT m.idMandat, m.titre, m.anneeD, m.anneeF FROM mandat m , brigue b WHERE b.idCandidat = " + id + " AND m.idMandat = b.idMandat"; 
		//System.out.println(query);
		ResultSet rs = DBUtils.select(query);
		if(rs!=null){
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
					if(rs.getString(4)==null)
						ma = new Mandat(rs.getInt(1), rs.getString(2), rs.getString(3), "");
					else
						ma = new Mandat(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
					result[i] = ma;
					i++;
				}
				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

}
