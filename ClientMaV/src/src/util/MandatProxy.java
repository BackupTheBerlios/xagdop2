/**
 * 
 */
package src.util;

import MaV.Mandat;

/**
 * @author claire
 *
 */
public class MandatProxy {
	private Mandat mandTmp = null;
	
	public MandatProxy(Mandat mand){
		mandTmp = mand ;
	}
	
	public String toString(){
		if(mandTmp==null)
			return null;
		 if(mandTmp.anneeF().equals("")){
			 return mandTmp.anneeD() + " : " + mandTmp.titre() ;
		 }else{
			 return mandTmp.anneeD() + "-" + mandTmp.anneeF() + " : "  + mandTmp.titre() ;
		 }
		
	}
	
	public Mandat getMand(){
		return mandTmp;
	}
	

}
