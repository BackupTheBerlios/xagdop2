package xagdop.Controleur;


import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Svn.SvnHistory;
import xagdop.Svn.SvnRemove;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;


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
	
	public void createProject(String projectName, String description) throws Exception {
		
		ProjectsParser pp = ProjectsParser.getInstance();
		if(pp.isProject(projectName)==false){
			//SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
			if(!SvnHistory.isUnderVersion(new File(IPreferences.getDefaultPath()))){
				SvnUpdate svnu = new SvnUpdate() ;
				svnu.checkOut(new File(IPreferences.getDefaultPath()));
			}
			File project = new File(IPreferences.getDefaultPath()+projectName);
			if(!project.exists())
				project.mkdir();
			File icon = new File(IPreferences.getDefaultPath()+projectName+File.separator+"icones");
			if(!icon.exists())
				icon.mkdir();
			FileWriter dependencies;
			try {
				dependencies = new FileWriter(project.getAbsolutePath()+File.separator+"dependencies.xml");
				dependencies.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><files><dependencies></dependencies><toUpdate></toUpdate></files>");
				dependencies.close();
			} catch (IOException e) {
				ErrorManager.getInstance().setErrMsg("Cr??ation du fichier des d??pendances.\nVeuillez v??rifier les droits du dossier.");
				ErrorManager.getInstance().setErrTitle("Cr??ation de projet impossible");
				throw e;
			}
			User user = XAGDOP.getInstance().getUser();	
			pp.addProject(projectName,user,description);
			//DependenciesParser.getInstance().add(projectName,d);
		}else{
			ErrorManager.getInstance().setErrMsg("Le projet existe d?j?.");
			ErrorManager.getInstance().setErrTitle("Projet existant");
			throw new Exception();
		}
		
		//wcClient.doAdd(project,false,false,false,true);
		
		//sendFile(project,description);
		
	}
	
	
	/* Fonction supprimant un projet  */
	public int deleteProject(CTreeNode node) throws HeadlessException, Exception{
		int error = 0;
		SvnRemove svnR = new SvnRemove();
		svnR.delete(node);
		
		JOptionPane.showMessageDialog(null ,"Le projet "+node.getName()+" sera supprim? lors du prochain commit", "Validation" , 1) ;
		//Enregistrement dans le XML du projet
		ProjectsParser PP = ProjectsParser.getInstance();
		PP.removeProject(node.getName());
		return error;
	}
}