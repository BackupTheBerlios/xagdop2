package xagdop.Svn;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IPreferences;



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
		File droitsLocal = new File(IPreferences.getDefaultPath()+".xagdop/");
		if(droitsLocal.exists())
			up.doUpdate(droitsLocal,SVNRevision.HEAD,false);
		else
			up.doCheckout(repository.getLocation(),droitsLocal,SVNRevision.HEAD,SVNRevision.HEAD,false);
		droitsLocal.deleteOnExit();
		droitsLocal = new File(IPreferences.getDefaultPath()+".xagdop/users.droits");
		droitsLocal.deleteOnExit();
		return droitsLocal;
		
	}
	
	
	public void checkOut(CTreeNode node) throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		File droits = new File(IPreferences.getDefaultPath());
		up.doCheckout(repository.getLocation(),droits,SVNRevision.HEAD,SVNRevision.HEAD,false);
	}
	
	
	
}