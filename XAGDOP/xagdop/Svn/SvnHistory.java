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
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
 


public class SvnHistory {
  
    
    	SVNRepository repository;
    	public SvnHistory(String url, String name, String password) throws SVNException{
    		repository = SvnConnect.getInstance(url,name,password).getRepository();
    		
    	}
    	public SvnHistory() throws SVNException{
    		repository = SvnConnect.getInstance().getRepository();
    	}

    	public static boolean  isUnderVersion(File file){
    		SVNWCClient wcClient;
			try {
				wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
				SVNInfo info = wcClient.doInfo(file, SVNRevision.WORKING);
				if(info == null || info.getRevision() == SVNRevision.UNDEFINED)
					return false;
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return false;
			}
			//info
    		return 	true;
    	}
    	
}
