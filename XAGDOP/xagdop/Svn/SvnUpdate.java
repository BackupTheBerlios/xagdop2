package xagdop.Svn;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
import xagdop.Model.User;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.ProjectsParser;



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
		File droitsLocal = new File(IPreferences.getRootPath()+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator+"users.xml");
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
		
		File project = new File(IPreferences.getRootPath()+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator+"projects.xml");
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
		
		File dependenciesLocal = new File(IPreferences.getRootPath()+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator+"dependencies.xml");
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
		File projectDirectoryLocal = new File(IPreferences.getRootPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		File projectLocal = new File(IPreferences.getRootPath()+".xagdop"+File.separator);
		if(!projectLocal.exists())
			projectLocal.mkdir();
		projectLocal = new File(IPreferences.getRootPath()+".xagdop"+File.separator+repository.getRepositoryUUID()+File.separator);
		
		//R??cup??ration des fichiers
		if(projectLocal.exists())
			up.doUpdate(projectLocal,SVNRevision.HEAD,false);
		else
			up.doCheckout(repository.getLocation(),projectLocal,SVNRevision.HEAD,SVNRevision.HEAD,false);
	}
	
	/**
	 * @param node Neoud que l'on veut recuperer
	 * @throws SVNException
	 */
	public void checkOut(CTreeNode node) throws SVNException{
		
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
	
		//Cr??ation du dossier local
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		
		projectDirectoryLocal = new File(node.getLocalPath());
		
		//System.out.println(IPreferences.getDefaultPath()+" : "+projectDirectoryLocal.exists());
		//Si il n'est pas versionn?? alors on fait un checkout sinon un update
		if(SvnHistory.isUnderVersion(projectDirectoryLocal))
			up.doUpdate(projectDirectoryLocal,SVNRevision.HEAD,true);
		else
			up.doCheckout(SVNURL.parseURIEncoded(SvnConnect.getInstance().getUrl()),projectDirectoryLocal,SVNRevision.HEAD,SVNRevision.HEAD,true);
		
		//On supprime les projets dont on ne fait pas parti
		cleanUp(projectDirectoryLocal);
	}
	
	public void cleanUp(File file){
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
				deleteDirectory(fileInDirectory[i]);
		}
	}
	
	
	
	
	public void checkOut_(CTreeNode node) throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		User user = XAGDOP.getInstance().getUser();
		ProjectsParser pp = ProjectsParser.getInstance();
		
		//IPreferences.setDefaultPath(IPreferences.getDefaultPath()+user.getLogin());
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		
		SvnDisplayRepositoryTree sdrt = new SvnDisplayRepositoryTree();
		Collection projectList = sdrt.listEntries(".");
		Iterator iterator = projectList.iterator();
		
		
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            if(pp.isUserInProject(entry.getName(),user.getLogin())){
            	if(entry.getKind()==SVNNodeKind.DIR){
            		File projectDirectory = new File(IPreferences.getDefaultPath()+entry.getName());
            		if(!projectDirectory.exists()){
            			projectDirectory.mkdir();
            			up.doCheckout(SVNURL.parseURIEncoded(SvnConnect.getInstance().getUrl()+entry.getName()),projectDirectory,SVNRevision.HEAD,SVNRevision.HEAD,true);
            		}else
            			up.doUpdate(projectDirectory,SVNRevision.HEAD,true);
            	}
            }
        }
        
        
        File[] projectLocal = projectDirectoryLocal.listFiles(new FilenameFilter() {
			
				public boolean accept(File dir, String name) {
					if(dir.isHidden()|| name.equals(".xagdop")){
						return false;
					}
					else return true;
				}
			
			});
        
        int i = 0;
        boolean isDelete = true;
        while (i < projectLocal.length) {
        	iterator = projectList.iterator();
        	//System.out.println(projectLocal[i].getName());
        	while(iterator.hasNext()){
        		SVNDirEntry entry = (SVNDirEntry) iterator.next();
        		
        		if(projectLocal[i].getName().equals(entry.getName())){
        			//System.out.println(projectLocal[i].getName()+" : "+entry.getName());
        			isDelete = false;
        			break;
        		}   
            }
        	if(isDelete){
        		File projectDirectory = new File(projectLocal[i].getAbsolutePath());
        		deleteDirectory(projectDirectory);
        		projectDirectory.delete();     		
        	}
        	isDelete = true;
        	i++;
       }
        try {
			DependenciesParser.getInstance().loadTreeInMemory(getDependenciesFile());
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	
	
	private void deleteDirectory(File dir){
		int i = 0 ;
		//System.out.println(dir.getName());
		if(dir.isDirectory())
		{
			File[] allFile = dir.listFiles();
			while(i<allFile.length){
				if(allFile[i].isDirectory())
					deleteDirectory(allFile[i]);
				allFile[i].delete();
				i++;
			}
		}
		dir.delete();
		
	}
	
	
}