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
import xagdop.Model.Users;
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
	
	public File getUsersFile() throws SVNException{
		getFiles();
		File droitsLocal = new File(IPreferences.getDefaultPath()+".xagdop"+File.separator+"users.xml");
		droitsLocal.deleteOnExit();
		return droitsLocal;
		
	}
	
	public File getProjectFile() throws SVNException{
		getFiles();
		File projectLocal = new File(IPreferences.getDefaultPath()+".xagdop"+File.separator+"projects.xml");
		projectLocal.deleteOnExit();
		return projectLocal;
		
	}
	
	public File getDependenciesFile() throws SVNException{
		getFiles();
		File dependenciesLocal = new File(IPreferences.getDefaultPath()+".xagdop"+File.separator+"dependencies.xml");
		dependenciesLocal.deleteOnExit();
		return dependenciesLocal;
		
	}
	
	public void getFiles() throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		
		
		File projectLocal = new File(IPreferences.getDefaultPath()+".xagdop"+File.separator);
		if(projectLocal.exists())
			up.doUpdate(projectLocal,SVNRevision.HEAD,false);
		else
			up.doCheckout(repository.getLocation(),projectLocal,SVNRevision.HEAD,SVNRevision.HEAD,false);
		projectLocal.deleteOnExit();
	}
	
	
	public void checkOut(CTreeNode node) throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		Users user = XAGDOP.getInstance().getUser();
		ProjectsParser pp = new ProjectsParser();
		
		//IPreferences.setDefaultPath(IPreferences.getDefaultPath()+user.getLogin());
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		
		SvnDisplayRepositoryTree sdrt = new SvnDisplayRepositoryTree();
		Collection projectList = sdrt.listEntries(repository,".");
		Iterator iterator = projectList.iterator();
		
		
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            if(pp.exist(entry.getName(),user.getLogin())){
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
		//System.out.println("delDir");
		File[] allFile = dir.listFiles();
		while(i<allFile.length){
			if(allFile[i].isDirectory())
				deleteDirectory(allFile[i]);
			allFile[i].delete();
			i++;
		}
		
	}
	
	
}