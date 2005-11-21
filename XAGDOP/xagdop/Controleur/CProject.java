package xagdop.Controleur;


import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;

import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnDisplayRepositoryTree;

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
		if((projectName.equals("")==false))
		{
			SvnDisplayRepositoryTree project;
			try {
				project = new SvnDisplayRepositoryTree();
			} catch (SVNException e) {
				JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
				e.printStackTrace();
				error =1;
				return error;
			}
		if(project.existProject(projectName)==false){
			SvnCommit svnC;
			try {
				svnC = new SvnCommit();
			} catch (SVNException e) {
				JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
				e.printStackTrace();
				error =1;
				return error;
			}
			SVNCommitInfo report = svnC.createProject(projectName, description);
			if(report.getError()!=null){
				JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" a bien ete cree ", "Validation" , 1) ;
				return error;
			}
			else{
				JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" n'a pu etre cree ", "Validation" , 1) ;
				return error;
			}
		}
		else {
			JOptionPane.showMessageDialog(null ,"Le projet "+projectName+" existe deja", "Validation" , 1) ;
			error = 1;
		}
		}
		else
		{
			JOptionPane.showMessageDialog(null ,"Le projet doit obligatoirement avoir un nom.", "Validation" , 1) ;
			error = 1;
		}
		
		return error;
		
	}
	
	
}