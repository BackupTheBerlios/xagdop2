package xagdop.Controleur;


import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNCommitInfo;

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
		SvnDisplayRepositoryTree project = new SvnDisplayRepositoryTree();
		if((projectName.equals("")==false))
		{
		if(project.existProject(projectName)==false){
			SvnCommit svnC = new SvnCommit();
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