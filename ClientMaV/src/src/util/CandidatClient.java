/**
 * 
 */
package src.util;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import MaV.Candidat;

/**
 * @author claire
 *
 */
public class CandidatClient {

	private Candidat candTmp = null;
	
	public CandidatClient(Candidat cand){
		candTmp = cand ;
	}
	
	public CandidatClient(int id){
		candTmp = new Candidat() ;
		try {
			candTmp.id = id;
			UtilORB.getListeC().saveCandidat(candTmp);
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
		
		return candTmp.nom + " " + candTmp.prenom;
	}
	
	public Candidat getCand(){
		return candTmp;
	}
	
}
