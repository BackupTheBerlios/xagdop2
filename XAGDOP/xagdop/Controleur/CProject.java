package xagdop.Controleur;


import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.XAGDOP;
import xagdop.Model.Users;
import xagdop.Parser.ProjectsParser;
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

	
	/*
	 * Constructeur
	 */
	public CProject(){
		
	}
	
	public void createProject(String projectName,String description) throws Exception, SVNException{
		
		if((projectName.equals("")==false))
		{
			SvnDisplayRepositoryTree project;
			project = new SvnDisplayRepositoryTree();
			if(project.existProject(projectName)==false){
				SvnCommit 	svnC = new SvnCommit();
				SVNCommitInfo report = svnC.createProject(projectName, description);
				// Enregistrement dans le XML du projet
				ProjectsParser PP = new ProjectsParser();
				Users user = XAGDOP.getInstance().getUser();	
				PP.addProject(projectName,user,description);
				
				if(report.getError()!=null){
					throw new Exception(report.toString());
				}
				
			}else throw new Exception("Projet existant");		
		}
		
		
	}
	
	
	/* Fonction supprimant un projet  */
	public int deleteProject(CTreeNode node){
		int error = 0;
		SvnRemove svnR;
		try {
			svnR = new SvnRemove();
		} catch (SVNException e) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
			e.printStackTrace();
			return 1;
		}
		
		try {
			svnR.deleteDir(node);
		} catch (SVNException e) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
			e.printStackTrace();
			return 1;
		}
		/*if(report.getError()!=null){
			JOptionPane.showMessageDialog(null ,"Le projet "+node.getName()+" n'a pu etre supprimer ", "Validation" , 1) ;
			return error;
		}*/
		JOptionPane.showMessageDialog(null ,"Le projet "+node.getName()+" est supprime", "Validation" , 1) ;
		//Enregistrement dans le XML du projet
		ProjectsParser PP = new ProjectsParser();
		PP.removeProject(node.getName());
		return error;
	}
}