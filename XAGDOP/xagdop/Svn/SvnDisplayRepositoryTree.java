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
 
import java.util.Collection;
import java.util.Iterator;
 
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
 

public class SvnDisplayRepositoryTree{
	SVNRepository repository;
    public SvnDisplayRepositoryTree() throws SVNException {
    	repository = SvnConnect.getInstance().getRepository();
    }
 
    public SvnDisplayRepositoryTree(String url, String name, String password) throws SVNException{
    	repository = SvnConnect.getInstance(url,name,password).getRepository();
	 }
    
    public boolean existProject(String projectName){
		Collection isIn;
		try {
			isIn = listEntries(repository,".");
			//String path =".";
			Iterator iterator = isIn.iterator();
	        while (iterator.hasNext()) {
	            SVNDirEntry entry = (SVNDirEntry) iterator.next();
	            if(projectName.equals(entry.getName()))
	            	return true;
	        }
			
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}	
    
    
    public  Collection listEntries(SVNRepository repository, String path)
            throws SVNException {
        /*
         * Gets the contents of the directory specified by path at the latest
         * revision (for this purpose -1 is used here as the revision number to
         * mean HEAD-revision) getDir returns a Collection of SVNDirEntry
         * elements. SVNDirEntry represents information about the directory
         * entry. Here this information is used to get the entry name, the name
         * of the person who last changed this entry, the number of the revision
         * when it was last changed and the entry type to determine whether it's
         * a directory or a file. If it's a directory listEntries steps into a
         * next recursion to display the contents of this directory. The third
         * parameter of getDir is null and means that a user is not interested
         * in directory properties. The fourth one is null, too - the user
         * doesn't provide its own Collection instance and uses the one returned
         * by getDir.
         */
        Collection entries = repository.getDir(path, -1, null, (Collection) null);
        	
        return entries;
       
    }
}