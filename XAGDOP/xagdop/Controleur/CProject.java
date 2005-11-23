package xagdop.Controleur;


import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;

import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnDisplayRepositoryTree;
import xagdop.Svn.SvnRemove;


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
	
	
	/* Fonction supprimant un projet  */
	public static int deleteProject(String Name){
		int error = 0;
		SVNCommitInfo report;
		SvnRemove svnR;
		try {
			svnR = new SvnRemove();
		} catch (SVNException e) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
			e.printStackTrace();
			return 1;
		}
		String path = "/"+Name;
	
		try {
			report = svnR.deleteDir(path);
		} catch (SVNException e) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
			e.printStackTrace();
			return 1;
		}
		if(report.getError()!=null){
			JOptionPane.showMessageDialog(null ,"Le projet "+Name+" n'a pu etre supprimer ", "Validation" , 1) ;
			return error;
		}
		JOptionPane.showMessageDialog(null ,"Le projet "+Name+" est supprime", "Validation" , 1) ;
		
		return error;
	}
}