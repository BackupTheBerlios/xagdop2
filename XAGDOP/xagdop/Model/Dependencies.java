package xagdop.Model;
import java.util.ArrayList;
import java.util.List;

public class Dependencies {

	/*
	 * 
	 * Attributs de la classe
	 * 
	 * 
	 */
		
	String apesFile;
	List pogFiles;
	
	/*
	 * Constructeurs
	 */
	public Dependencies(String apes){
		apesFile = apes;
		pogFiles = new ArrayList();
	}
	
	public Dependencies(String apes, String pog){
		apesFile = apes;
		pogFiles = new ArrayList();
		addDependencies(pog);
	}
	
	/*
	 * Methode de la classe
	 */
	public void  addDependencies(String pogF){
		pogFiles.add(pogF);
	}
	
	public boolean isDependencie(String pogF){
		if(pogFiles.contains(pogF))
			return true;
		return false;
	}
	
	public List getDepencies(){
		return pogFiles;
		
	}
	
}
