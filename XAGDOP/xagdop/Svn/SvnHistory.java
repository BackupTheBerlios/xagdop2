/*
 * ====================================================================
 * Copyright (c) 2004 TMate Software Ltd.  All rights reserved.
 *
 * This software is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://tmate.org/svn/license.html.
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 * ====================================================================
 */
package xagdop.Svn;
 
import java.io.File;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
 


public class SvnHistory {
  
    
    	SVNRepository repository;
    	public SvnHistory(String url, String name, String password) throws SVNException{
    		repository = SvnConnect.getInstance(url,name,password).getRepository();
    		
    	}
    	public SvnHistory() throws SVNException{
    		repository = SvnConnect.getInstance().getRepository();
    	}

    	/**
    	 * @param file Fichier que l'on veut tester
    	 * @return Vrai si le fichier est versionné, faux sinon
    	 * Teste si un fichier est versionné
    	 */
    	public static boolean  isUnderVersion(File file){
    		
    		SVNStatusClient ssClient;
			try {
				ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
				SVNStatus sStatus = ssClient.doStatus(file, false);
				SVNStatusType ssType = sStatus.getContentsStatus();
				if(ssType == SVNStatusType.STATUS_UNVERSIONED)
					return false;
				
					
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return false;
			}
			return true;
    	}
    		
    	
    	/**
    	 * @param file Fichier que l'on veut tester
    	 * @return Vrai si le fichier a été modifié, faux sinon
    	 * Teste si un fichier a été modifié
    	 */
    	public static boolean isModified(File file) {
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
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return true;
			}
			return false;
    	}
    	
    	/**
    	 * @param file Teste le fichier fait parti du repository courant
    	 * @return Vrai si il appartient au repository courant, faux sinon
    	 * Marche pas
    	 */
    	public static boolean isCurrentRepository(File file){
    		SVNStatusClient ssClient;
			try {
				ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
				SVNStatus sStatus = ssClient.doStatus(file, false);
				return !sStatus.isSwitched();
				
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
    	}
    	
    	/**
    	 * @param file Fichier dont on veut la revision
    	 * @return le numéro de revision
    	 */
    	public static long getRevision(File file){
    		SVNStatusClient ssClient;
			try {
				ssClient = new SVNStatusClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
				SVNStatus sStatus = ssClient.doStatus(file, false);
				//System.out.println("Version : "+sStatus.getRevision().getNumber());
				return sStatus.getRevision().getNumber();
				
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
			}
			return -1;
			
    	}
    	
}
