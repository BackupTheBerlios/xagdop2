/**
 * 
 */
package src.util;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import MaV.Candidat;
import MaV.ListeC;

/**
 * @author claire
 *
 */
public class CandidatClient {

	private Candidat candTmp = null;
	
	public CandidatClient(Candidat cand){
		candTmp = cand ;
	}
	
	public CandidatClient(String nom, String prenom, int age, String profession){
		try {
			ListeC listCand = UtilORB.getListeC();
			candTmp = listCand.createCandidat(nom, prenom, age, profession);
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString(){
		if(candTmp==null)
			return null;
		
		return candTmp.nom() + " " + candTmp.prenom();
	}
	
	public Candidat getCand(){
		return candTmp;
	}
	
}
