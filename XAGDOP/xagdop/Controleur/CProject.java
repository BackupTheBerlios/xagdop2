package xagdop.Controleur;


import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import xagdop.Interface.XAGDOP;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Model.User;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.ProjectsParser;
import xagdop.Svn.SvnCommit;
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
			
			File css = new File(project,"css");
			if(!css.exists())
				css.mkdirs();
			
			File bib = new File(project,"bib");
			if(!bib.exists())
				bib.mkdirs();
			
			File icon = new File(IPreferences.getDefaultPath()+projectName,"icones");
			if(!icon.exists())
				icon.mkdir();
			
			File produits = new File(icon,"produits");
			if(!produits.exists())
				produits.mkdir();
			
			File composants = new File(icon,"composants");
			if(!composants.exists())
				composants.mkdir();
			
			
			FileWriter dependencies;
			try {
				dependencies = new FileWriter(project.getAbsolutePath()+File.separator+"dependencies.xml");
				dependencies.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><files project=\""+projectName+"\"><dependencies></dependencies><toUpdate></toUpdate><toCreate></toCreate></files>");
				dependencies.close();
			} catch (IOException e) {
				ErrorManager.getInstance().setErrMsg("Cr??ation du fichier des d??pendances.\nVeuillez v??rifier les droits du dossier.");
				ErrorManager.getInstance().setErrTitle("Cr??ation de projet impossible");
				throw e;
			}
			User user = XAGDOP.getInstance().getUser();	
			pp.addProject(projectName,user,description);
			DependenciesParser.getInstance().addFile(projectName, new File(project,"dependencies.xml"));
			CRole.getInstance().refreshRole();
			//XAGDOP.getInstance().refreshTree();
			SvnCommit commit = new SvnCommit();
			commit.sendFile(project,"Cr??ation du projet");
			commit.sendXMLFile();
			
			File style = new File(project,"website");
			if(!style.exists())
				style.mkdirs();
			
			File export = new File(project,"export");
			if(!export.exists())
				export.mkdirs();
			
		}else{
			ErrorManager.getInstance().setErrMsg("Le projet existe d?j?.");
			ErrorManager.getInstance().setErrTitle("Projet existant");
			throw new Exception();
		}
		
		//wcClient.doAdd(project,false,false,false,true);
		
		//sendFile(project,description);
		
	}
	
	
	//Supression du fichier correspondant au noeud
	public void deleteProject(CTreeNode node) throws HeadlessException, Exception{
		
		if (!node.isProject())
		{
			CCommit cc = new CCommit(node);
			cc.DependencesRemoveInitialize(node);
		}
		//Verification du type du noeud
		//Si c'est un projet, il faut le retirer du project parser
		
		
		SvnRemove svnR = new SvnRemove();
		svnR.delete(node);
		
		if (node.isProject())
		{
			ProjectsParser.getInstance().removeProject(node.getName());
			SvnCommit commit = new SvnCommit();
			//System.out.println(SvnHistory.isToDelete((File)node.getUserObject()));
			commit.commitRemoveProject(node,"Suppression du projet");
		}
		
		//JOptionPane.showMessageDialog(null ,"Le dossier "+node.getName()+" sera supprim? lors du prochain commit", "Validation" , 1) ;
		//Enregistrement dans le XML du projet
		/*ProjectsParser PP = ProjectsParser.getInstance();
		PP.removeProject(node.getName());*/
		
	}
}