package src.util;

import MaV.Electeur;

public class ElecteurClient {


	private Electeur electTmp = null;
	
	public ElecteurClient(Electeur elect){
		electTmp = elect ;
	}
	
	public ElecteurClient(String nom, String prenom, int age, String profession){
		
		
	}
	
	public String toString(){
		if(electTmp==null)
			return null;
		
		return electTmp.nom + " " + electTmp.prenom;
	}
	
	public Electeur getElect(){
		return electTmp;
	}
	
	
	
}
