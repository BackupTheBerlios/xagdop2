/**
 * 
 */
package src.util;

import MaV._CandidatStub;

/**
 * @author claire
 *
 */
public class Candidat {

	private _CandidatStub candTmp = null;
	
	public Candidat(_CandidatStub cand){
		candTmp = cand ;
	}
	
	public String toString(){
		if(candTmp==null)
			return null;
		
		return candTmp.nom() + " " + candTmp.prenom();
	}
	
	public _CandidatStub getCand(){
		return candTmp;
	}
	
}
