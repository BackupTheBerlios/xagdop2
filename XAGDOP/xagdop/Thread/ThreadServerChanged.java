package xagdop.Thread;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CPreferencies;
import xagdop.Interface.IWaiting;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Interface.Preferences.RemotePathPanel;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Svn.SvnConnect;
import xagdop.Svn.SvnHistory;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;




public class ThreadServerChanged extends Thread {
	RemotePathPanel remotePathPanel;
	public ThreadServerChanged(RemotePathPanel remotePathPanel) {
		this.remotePathPanel = remotePathPanel;
	
	}

	public void run() {
		//Refresh des fichiers de projet et de user
		try{			
			//On remet le nouveau path
			SvnConnect.getInstance().setUrl(RemotePathPanel.getRemotePath());
			//On s'y reconnecte
			SvnConnect.getInstance().connect();
			if(!SvnConnect.getInstance().getRepositoryUUID().equals(SvnHistory.getRepositoryUUID(new File(IPreferences.getDefaultPath())))){
				ErrorManager.getInstance().setErrMsg("Le r??pertoire de travail choisi est d??j?? utilis?? pour un autre d??p??t SubVersion, en choisir un autre avant de changer l'adresse du serveur");
				ErrorManager.getInstance().setErrTitle("Probleme serveur");
				//ErrorManager.getInstance().display();
				remotePathPanel.setRemotePath(CPreferencies.getServerPath());
				throw new SVNException();
			}
			//System.out.println(SvnConnect.getInstance().getRepositoryUUID());
			//System.out.println(SvnHistory.getRepositoryUUID(new File(IPreferences.getDefaultPath())));
			//On fait un update
			SvnUpdate svnu = new SvnUpdate();
			//On recupere les fichiers user et project
			svnu.getFiles();
			//On remet a jour les arbres en memoire
			ProjectsParser.getInstance().setFile();
			UsersParser.getInstance().setFile();
			//On fait un refresh du local
		//	XAGDOP.getInstance().refreshTree();
			CPreferencies.setServerPath(RemotePathPanel.getRemotePath());
		}catch (Exception e1)
		{
			ErrorManager.getInstance().display();
			remotePathPanel.setRemotePath(CPreferencies.getServerPath());

		}

		finally{
			IWaiting.getInstance().arreter();
		}
		
	}
	
	
}
