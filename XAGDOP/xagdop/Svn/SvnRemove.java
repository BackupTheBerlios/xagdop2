package xagdop.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNWorkspaceMediator;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;





public class SvnRemove {
	
	SVNRepository repository;
	public SvnRemove(String url, String name, String password) throws SVNException{
		repository = SvnConnect.getInstance(url,name,password).getRepository();
	}
	public SvnRemove()throws SVNException{
		repository = SvnConnect.getInstance().getRepository();
	}
	
	/**
	 * @param dirPath Chemin du dossier où se trouve le fichier à supprimer
	 * @param filePath fichier à supprimer
	 * @return Les infos de comment s'est passé la suppression
	 * @throws SVNException
	 */
	public  SVNCommitInfo removeFile(String dirPath, String filePath) throws SVNException{
		ISVNEditor editor  = null;

		try {
			//Récupération de l'editeur du repository
			editor = repository.getCommitEditor("",new WorkspaceMediator());
		} catch (SVNException svne) {
			try{
				return editor.closeEdit();
			} catch (SVNException e) {
				e.printStackTrace();
			}
		}
		
		//Ouverture du dossier de base
		editor.openRoot(-1);
		//Ouverture du dossier où se trouve le fichier à supprimer
		editor.openDir(dirPath,-1);
		//Suppression du fichier spécifié
		editor.deleteEntry(dirPath+File.separator+filePath,-1);
		//Fermeture du dossier choisi
		editor.closeDir();
		//Fermeture du repertoire de base
		editor.closeDir();
		
		//Renvoi les infos de cla suppression
		return editor.closeEdit();
	}
	
	
	
	

	
	/**
	 * @param node Noeud
	 * @throws SVNException
	 */
	public  void deleteDir(CTreeNode node)
	throws SVNException {
		SVNCommitClient svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
		ArrayList urlDirectory = new ArrayList();
		urlDirectory.add(SVNURL.parseURIDecoded(SvnConnect.getInstance().getUrl()+node.getName()));
		SVNURL[] url = new SVNURL[urlDirectory.size()];
		
		svnCC.doDelete((SVNURL[])urlDirectory.toArray(url),"");
	
	}
	private static class WorkspaceMediator implements
	ISVNWorkspaceMediator {
		private Map myTmpFiles = new HashMap();
		
		public String getWorkspaceProperty(String path, String name)
		throws SVNException {
			return null;
		}
		
		public void setWorkspaceProperty(String path, String name, String value)
		throws SVNException {
		}
		
		/*
		 * Creates a temporary file delta  storage.  id  will  be  used  as  the
		 * temporary storage identifier. Returns  an  OutputStream to write  the
		 * delta data into the temporary storage.
		 */
		public OutputStream createTemporaryLocation(String path, Object id)
		throws IOException {
			ByteArrayOutputStream tempStorageOS = new ByteArrayOutputStream();
			myTmpFiles.put(id, tempStorageOS);
			return tempStorageOS;
		}
		
		/*
		 * Returns an InputStream of the temporary file delta storage identified
		 * by id to read the delta.
		 */
		public InputStream getTemporaryLocation(Object id) throws IOException {
			return new ByteArrayInputStream(((ByteArrayOutputStream)myTmpFiles.get(id)).toByteArray());
		}
		
		/*
		 * Gets the length of the  delta  that  was  written  to  the  temporary 
		 * storage identified by id.
		 */
		public long getLength(Object id) throws IOException {
			ByteArrayOutputStream tempStorageOS = (ByteArrayOutputStream)myTmpFiles.get(id);
			if (tempStorageOS != null) {
				return tempStorageOS.size();
			}
			return 0;
		}
		
		/*
		 * Deletes the temporary file delta storage identified by id.
		 */
		public void deleteTemporaryLocation(Object id) {
			myTmpFiles.remove(id);
		}
	}
	
	
	
}
