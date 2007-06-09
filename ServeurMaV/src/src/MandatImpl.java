package src;

import java.util.ArrayList;

import MaV._MandatImplBase;

public class MandatImpl extends _MandatImplBase {

	int idMandat;
	String titre ;
	String anneeD;
	String anneeF;
	
	public MandatImpl(int _id, String _titre, String _anneeD, String _anneeF){
		idMandat = _id;
		titre = _titre;
		anneeD = _anneeD;
		anneeF = _anneeF;
	}
	
	public String anneeD() {
		return anneeD;
	}

	public void anneeD(String newAnneeD) {
		anneeD = newAnneeD;
	}

	public String anneeF() {
		
		return anneeF;
	}

	public void anneeF(String newAnneeF) {
		anneeF = newAnneeF;

	}

	public int id() {
		return idMandat;
	}

	public void id(int newId) {
		idMandat=newId;
	}

	public void saveMandat() {
		ArrayList cols = new ArrayList();
		ArrayList values = new ArrayList();

		cols.add("titre");
		cols.add("anneeD");
		cols.add("anneeF");

		values.add(titre);
		values.add(anneeD);
		values.add(anneeF);
		
		DBUtils.update("mandat", cols, values, "idMandat = " + idMandat);

	}

	public String titre() {
		return titre;
	}

	public void titre(String newTitre) {
		titre = newTitre;

	}

}
