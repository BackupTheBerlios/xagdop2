package xagdop.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNWorkspaceMediator;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindowBuilder;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;



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
		File droits = new File("users.droits");
		File droitsLocal = new File("/home/nephos/users.droits");
		up.doExport(droits,droitsLocal,SVNRevision.HEAD,SVNRevision.HEAD, null, true,false);
		return droitsLocal;
		
	}
	
	
	public void checkOut(CTreeNode node) throws SVNException{
		SVNUpdateClient up = new SVNUpdateClient(repository.getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		File droits = new File("/users/iupisi/m1isi7/IUP/BE/test/");
		up.doCheckout(repository.getLocation(),droits,SVNRevision.HEAD,SVNRevision.HEAD,false);
	}
	
	
	
}