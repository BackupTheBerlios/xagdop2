package src.Controleur;

import java.util.Collection;

import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

import src.Svn.SvnDisplayRepositoryTree;

public class CProject {
	/*
	 * 
	 * Attributs de la classe
	 * 
	 * 
	 */
	
	/*
	 * Constructeur
	 */
	public CProject(String projectName,String descriptionProjet){
		
	}
	
	/*
	 * Methode de la classe
	 */
	
	public void newProject(String projectName,String descriptionProjet)
	{
		//Creation du projet sur le depot
		
		
		
		
		
		JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" a bien ete cree ", "Validation" , 1) ;

	}
	
	public boolean existProject(String projectName){
		Collection isIn;
		SvnDisplayRepositoryTree SvnExist = new SvnDisplayRepositoryTree();
		SVNRepository repository = SvnExist.connect();
		
		try {
			isIn = SvnExist.listEntries(repository,".");
			
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}	
}