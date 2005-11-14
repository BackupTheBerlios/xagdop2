package src.Controleur;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

import src.Svn.SvnCommit;
import src.Svn.SvnDisplayRepositoryTree;

public class CProject {
	/*
	 * 
	 * Attributs de la classe
	 * 
	 * 
	 */
	protected String projectName;
	protected String description;
	
	/*
	 * Constructeur
	 */
	public CProject(String _projectName,String _description){
		projectName = _projectName;
		description = _description;
	}
	
	public int createProject(){
		int error = 0;
		SvnDisplayRepositoryTree project = new SvnDisplayRepositoryTree();
		if(project.existProject(projectName)==false){
			SvnCommit svnC = new SvnCommit();
			if((error = svnC.createProject(projectName, description))==0){
				JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" a bien été crée ", "Validation" , 1) ;
				return error;
			}
			else{
				JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" n'a pu être crée ", "Validation" , 1) ;
				return error;
			}
		}
		else {
			JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" existe déjà", "Validation" , 1) ;
			error = 1;
		}
		
		return error;
		
	}
	
	
}