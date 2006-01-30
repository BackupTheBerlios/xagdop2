package xagdop.Controleur;


import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.XAGDOP;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Svn.SvnCommit;
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
			ProjectsParser pp = ProjectsParser.getInstance();
			if(pp.isProject(projectName)==false){
				SvnCommit 	svnC = new SvnCommit();
				//SVNCommitInfo report = 
				svnC.createProject(projectName, description);
				// Enregistrement dans le XML du projet
				
				User user = XAGDOP.getInstance().getUser();	
				pp.addProject(projectName,user,description);
				
				/*if(report.getError()!=null){
					throw new Exception(report.toString());
				}*/
				
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
			svnR.delete(node);
		} catch (SVNException e) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
			e.printStackTrace();
			return 1;
		}
		/*if(report.getError()!=null){
			JOptionPane.showMessageDialog(null ,"Le projet "+node.getName()+" n'a pu etre supprimer ", "Validation" , 1) ;
			return error;
		}*/
		JOptionPane.showMessageDialog(null ,"Le projet "+node.getName()+" sera supprimer lors du prochain commit", "Validation" , 1) ;
		//Enregistrement dans le XML du projet
		ProjectsParser PP = ProjectsParser.getInstance();
		PP.removeProject(node.getName());
		return error;
	}
}