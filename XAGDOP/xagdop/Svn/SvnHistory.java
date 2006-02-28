package xagdop.Svn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CFile;
import xagdop.Controleur.CTreeNode;
import xagdop.Util.ErrorManager;



public class SvnHistory {
	
	
	/**
	 * @param file Fichier que l'on veut tester
	 * @return Vrai si le fichier est versionn??, faux sinon
	 * Teste si un fichier est versionn??
	 */
	public static boolean  isUnderVersion(File file){
		
		SVNStatusClient ssClient;
		SVNStatus sStatus;
		file = new File(file.getAbsolutePath());
		try {
			ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
			if(ssClient!=null)
				sStatus = ssClient.doStatus(file, false);
			else 
				return false;
			if(sStatus==null)
				return false;
			SVNStatusType ssType = sStatus.getContentsStatus();
			if(ssType == SVNStatusType.STATUS_UNVERSIONED)
				return false;
			
		} catch (SVNException e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * @param file Fichier que l'on veut tester
	 * @return Vrai si le fichier a ??t?? modifi??, faux sinon
	 * Teste si un fichier a ??t?? modifi??
	 * @throws SVNException 
	 */
	public static boolean isModified(File file) throws SVNException {
		SVNStatusClient ssClient;
		try {
			ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
			SVNStatus sStatus = ssClient.doStatus(file, false);
			if(sStatus==null)
				return true;
			SVNStatusType ssType = sStatus.getContentsStatus();
			
			if(ssType == SVNStatusType.STATUS_MODIFIED || ssType == SVNStatusType.STATUS_UNVERSIONED)
				return true;
		} catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("L'attribut du "+file.getName()+" n'a pu ??tre r??cup??r??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Fichier invalide");
			throw e;
		}
		return false;
	}
	
	
	public static boolean isToDelete(File file) throws SVNException{
		SVNStatusClient ssClient;
		try {
			ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
			SVNStatus sStatus = ssClient.doStatus(file, false);
			if(sStatus==null)
				return true;
			SVNStatusType ssType = sStatus.getContentsStatus();
			
			if(ssType == SVNStatusType.STATUS_DELETED )
				return true;
		} catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("L'attribut du "+file.getName()+" n'a pu ??tre r??cup??r??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Fichier invalide");
			throw e;
		}
		return false;
	}
	
	/**
	 * @param file Teste le fichier fait parti du repository courant
	 * @return Vrai si il appartient au repository courant, faux sinon
	 * Marche pas
	 * @throws SVNException 
	 *//*
	 public static boolean isCurrentRepository(File file){
	 SVNStatusClient ssClient;
	 try {
	 ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
	 SVNStatus sStatus = ssClient.doStatus(file, false);
	 return !sStatus.isSwitched();
	 
	 } catch (SVNException e) {
	 // TODO Auto-generated catch block
	  //e.printStackTrace();
	   return true;
	   }
	   }
	   */
	public static String getRepositoryUUID(File file){
		try {
			if(isUnderVersion(file)){
				SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
				SVNInfo info = wcClient.doInfo(file,SVNRevision.WORKING);
				return info.getRepositoryUUID();
			}else
				return SvnConnect.getInstance().getRepositoryUUID();
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	/**
	 * @param file Fichier dont on veut la revision
	 * @return le num??ro de revision
	 */
	public static long getRevision(CTreeNode file) throws SVNException{
		SVNStatusClient ssClient;
		try {
			ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
			SVNStatus sStatus = ssClient.doStatus((File)file.getUserObject(), false);
			return sStatus.getRevision().getNumber();
			
		} catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("La version du fichier "+file.getName()+" n'a pu ??tre r??cup??r??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Fichier invalide");
			throw e;
		}
		
	}
	
	public static double getVersion(CTreeNode file) throws SVNException{
		
		try {
			SVNRepository repository = SvnConnect.getInstance().getRepository();
			
			Collection logEntries = repository.log(new String[] {file.getProject().getName()+"/"+CFile.treePathName(file)}, null,
					0, repository.getLatestRevision(), false, true);
			//System.out.println((1+logEntries.size()/100));
			return (1.0+logEntries.size()/100);
			
		} catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("La version du fichier "+file.getName()+" n'a pu ??tre r??cup??r??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Fichier invalide");
			throw e;
		}
	}
	
	public static ArrayList getInfo(CTreeNode file) throws SVNException{
		
		try {
			if(!isUnderVersion((File)file.getUserObject())||file.isRoot())
				return null;
			SVNRepository repository = SvnConnect.getInstance().getRepository();
			Collection logEntries = repository.log(new String[] {file.getProject().getName()+"/"+CFile.treePathName(file)}, null,
					0, repository.getLatestRevision(), false, false);
			return new ArrayList(logEntries);
			
		} catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("La version du fichier "+file.getName()+" n'a pu ??tre r??cup??r??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Fichier invalide");
			throw e;
		}
		
	}
	
}
