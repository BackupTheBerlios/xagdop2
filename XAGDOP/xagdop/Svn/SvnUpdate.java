package xagdop.Svn;

import java.io.File;
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
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		
		
		File droitsLocal = new File(IPreferences.getDefaultPath()+".xagdop/");
		if(droitsLocal.exists())
			up.doUpdate(droitsLocal,SVNRevision.HEAD,false);
		else
			up.doCheckout(repository.getLocation(),droitsLocal,SVNRevision.HEAD,SVNRevision.HEAD,false);
		droitsLocal.deleteOnExit();
		droitsLocal = new File(IPreferences.getDefaultPath()+".xagdop/users.xml");
		droitsLocal.deleteOnExit();
		return droitsLocal;
		
	}
	
	public File getProjectFile() throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		File projectDirectoryLocal = new File(IPreferences.getDefaultPath());
		if(!projectDirectoryLocal.exists())
			projectDirectoryLocal.mkdir();
		
		
		File projectLocal = new File(IPreferences.getDefaultPath()+".xagdop/");
		if(projectLocal.exists())
			up.doUpdate(projectLocal,SVNRevision.HEAD,false);
		else
			up.doCheckout(repository.getLocation(),projectLocal,SVNRevision.HEAD,SVNRevision.HEAD,false);
		projectLocal.deleteOnExit();
		projectLocal = new File(IPreferences.getDefaultPath()+".xagdop/projects.xml");
		projectLocal.deleteOnExit();
		return projectLocal;
		
	}
	
	
	public void checkOut(CTreeNode node) throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		Users user = XAGDOP.getInstance().getUser();
		ProjectsParser pp = new ProjectsParser();
		
		
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
            		File projectDirectory = new File(IPreferences.getDefaultPath()+"/"+entry.getName());
            		if(!projectDirectory.exists())
            			projectDirectory.mkdir();
            		up.doCheckout(SVNURL.parseURIEncoded(SvnConnect.getInstance().getUrl()+entry.getName()),projectDirectory,SVNRevision.HEAD,SVNRevision.HEAD,true);
            	}
            }
        }
	}
	
	
	
}