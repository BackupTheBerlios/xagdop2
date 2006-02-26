package xagdop.Svn;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CFile;
import xagdop.Controleur.CRole;
import xagdop.Interface.XAGDOP;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Util.ErrorManager;



/**
 * @author nephos
 *
 */
public class SvnUpdate{
	SVNRepository repository;
	public SvnUpdate(String url, String name, String password) throws SVNException{
		repository = SvnConnect.getInstance(url,name,password).getRepository();	
	}
	
	public SvnUpdate() throws SVNException{
		repository = SvnConnect.getInstance().getRepository();
	}
	
	/**
	 * @return Le fichier contenant les infos sur les utilisateurs
	 * @throws SVNException
	 */
	public File getUsersFile() throws SVNException{
		File droitsLocal = new File(System.getProperty("user.home")+File.separator+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator+"users.xml");
		if(!droitsLocal.exists())
			getFiles();
		droitsLocal.deleteOnExit();
		return droitsLocal;
		
	}
	
	/**
	 * @return Le fichier contenant les infos sur les projets
	 * @throws SVNException
	 */
	public File getProjectFile() throws SVNException{
		
		File project = new File(System.getProperty("user.home")+File.separator+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator+"projects.xml");
		if(!project.exists())
			getFiles();
		project.deleteOnExit();
		return project;
		
	}
	/**
	 * @return Le fichier contenant les infos sur les dependances
	 * @throws SVNException
	 */
	public File getDependenciesFile() throws SVNException{
		
		File dependenciesLocal = new File(System.getProperty("user.home")+File.separator+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator+"dependencies.xml");
		if(!dependenciesLocal.exists())
			getFiles();
		dependenciesLocal.deleteOnExit();
		return dependenciesLocal;
		
	}
	
	/**
	 * @throws SVNException
	 * R??cup??re les fichiers xml contenu sur le serveur
	 */
	public void getFiles() throws SVNException{
		
		
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		//Test si les dossiers des diff??rents utilisateurs existent sinon il les cr??e
		
		File projectLocal = new File(System.getProperty("user.home")+File.separator+".xagdop"+File.separator);
		if(!projectLocal.exists())
			projectLocal.mkdir();
		projectLocal = new File(System.getProperty("user.home")+File.separator+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator);
		
		//R??cup??ration des fichiers
		if(projectLocal.exists())
			try{
				up.doUpdate(projectLocal,SVNRevision.HEAD,false);
			} catch (SVNException svne) {
				ErrorManager.getInstance().setErrMsg("Impossible de se synchroniser avec le serveur.\nVeuillez v??rifier l'adresse de ce dernier.");
				ErrorManager.getInstance().setErrTitle("Update Impossible");
				throw svne;
			}
		else
			try{
				up.doCheckout(repository.getLocation(),projectLocal,SVNRevision.HEAD,SVNRevision.HEAD,false);
			} catch (SVNException svne) {
				ErrorManager.getInstance().setErrMsg("Impossible de se synchroniser avec le serveur.\nVeuillez v??rifier l'adresse de ce dernier.");
				ErrorManager.getInstance().setErrTitle("Checkout Impossible");
				throw svne;
			}
			
	}
	
	/**
	 * @param node Neoud que l'on veut recuperer
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void checkOut(File parent) throws IOException, Exception{
		
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
	
		//Cr??ation du dossier local
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		projectDirectoryLocal = new File(parent.getAbsolutePath());
		
		//System.out.println(IPreferences.getDefaultPath()+" : "+projectDirectoryLocal.exists());
		//Si il n'est pas versionn?? alors on fait un checkout sinon un update
		if(SvnHistory.isUnderVersion(projectDirectoryLocal))
			try{
				up.doUpdate(projectDirectoryLocal,SVNRevision.HEAD,true);
			} catch (SVNException svne) {
				ErrorManager.getInstance().setErrMsg("Impossible de se synchroniser avec le serveur.\nVeuillez v??rifier l'adresse de ce dernier.");
				ErrorManager.getInstance().setErrTitle("Update Impossible");
				throw svne;
			}
		else
			try{
				up.doCheckout(repository.getLocation(),projectDirectoryLocal,SVNRevision.HEAD,SVNRevision.HEAD,true);
			} catch (SVNException svne) {
				ErrorManager.getInstance().setErrMsg("Impossible de se synchroniser avec le serveur.\nVeuillez v??rifier l'adresse de ce dernier.");
				ErrorManager.getInstance().setErrTitle("Checkout Impossible");
				throw svne;
			}
		//On supprime les projets dont on ne fait pas parti
		if(parent.compareTo(new File(IPreferences.getDefaultPath()))==0)
			cleanUp(projectDirectoryLocal);
		
		CRole.getInstance().refreshRole();
	}
	
	
	/**
	 * @param node Neoud que l'on veut recuperer
	 * @throws Exception 
	 * @throws IOException 
	 * @throws XPathExpressionException 
	 */
	public void checkOut(ArrayList listProject) throws XPathExpressionException, IOException, Exception{
		
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
	
		//Cr??ation du dossier local
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists()||!SvnHistory.isUnderVersion(projectDirectoryLocal)){
			//System.out.println(listProject.toString()+" : "+projectDirectoryLocal.getName());
			checkOut(projectDirectoryLocal);
			File[] fileInDirectory = projectDirectoryLocal.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					File directory = new File(dir.getAbsolutePath()+"/"+name); 
					if(directory.isHidden())
						return false;
					return true;
				}
			});
			for(int i=0; i < fileInDirectory.length;i++){
				if(!listProject.contains(fileInDirectory[i].getName()))
					CFile.deleteDirectory(fileInDirectory[i]);
			}
		}
		else{	
			ArrayList project = ProjectsParser.getInstance().getProjects(XAGDOP.getInstance().getUser().getLogin());
			if(project.size()==listProject.size())
				checkOut(projectDirectoryLocal);
			else{
				for(int i=0; i < listProject.size();i++){
					projectDirectoryLocal = new File(IPreferences.getDefaultPath()+File.separator+(String)listProject.get(i));
					//Si il n'est pas versionn?? alors on fait un checkout sinon un update
					if(SvnHistory.isUnderVersion(projectDirectoryLocal))
						try{
							up.doUpdate(projectDirectoryLocal,SVNRevision.HEAD,true);
						} catch (SVNException svne) {
							ErrorManager.getInstance().setErrMsg("Impossible de se synchroniser avec le serveur.\nVeuillez v??rifier l'adresse de ce dernier.");
							ErrorManager.getInstance().setErrTitle("Update Impossible");
							throw svne;
						}
				}
			}
		}
		CRole.getInstance().refreshRole();	
	}
	
	public void cleanUp(File file) throws SVNException, IOException, Exception{
		User user = XAGDOP.getInstance().getUser();
		ProjectsParser pp = ProjectsParser.getInstance();
		File[] fileInDirectory = file.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				File directory = new File(dir.getAbsolutePath()+"/"+name); 
				if(directory.isHidden())
					return false;
				return true;
			}
		});
		for(int i = 0; i < fileInDirectory.length; i++){
			if(!pp.isUserInProject(fileInDirectory[i].getName(),user.getLogin()))
				CFile.deleteDirectory(fileInDirectory[i]);
		}
	}
	
	
}