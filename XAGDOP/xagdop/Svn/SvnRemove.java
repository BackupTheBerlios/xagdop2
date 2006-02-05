package xagdop.Svn;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CFile;
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
	
	
	public  void delete(CTreeNode node){
		File toDelete = new File(node.getLocalPath());
		SVNWCClient svnWCC = new SVNWCClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
		if(SvnHistory.isUnderVersion(toDelete)){
			try {
				svnWCC.doDelete(toDelete,true,true,false);
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
			CFile.deleteDirectory(toDelete);			
	}
	
}
