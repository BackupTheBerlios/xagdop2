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
package src.Svn;
 
import java.util.Collection;
import java.util.Iterator;
 
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
 
/*
 * This example shows how to get the repository tree at the latest (HEAD)
 * revision starting with the directory that is the path/to/repository part of
 * the repository location URL. The main point is SVNRepository.getDir() method
 * that is called recursively for each directory (till the end of the tree).
 * getDir collects all entries located inside a directory and returns them as a
 * java.util.Collection. As an example here's one of the program layouts (for
 * the default url used in the program ):
 * 
 * Repository Root: /svn/jsvn 
 * Repository UUID: 0a862816-5deb-0310-9199-c792c6ae6c6e
 * 
 * /rss2.php (author:alex; revision:345) 
 * /build.html (author:alex; revision:406)
 * /feed (author:alex; revision:210)
 * /feed/rss_util.php (author:alex; revision:210) 
 * /feed/lgpl.txt (author:alex; revision:33)
 * /feed/feedcreator.class.php (author:alex; revision:33) 
 * /feed/rss.php
 * (author:alex; revision:33) 
 * /ant.html (author:alex; revision:193)
 * /license.html (author:alex; revision:193) 
 * /status.html (author:alex; revision:439) 
 * /usage.html (author:alex; revision:301) 
 * /logging.html (author:alex; revision:397) 
 * /.htaccess (author:alex; revision:51)
 * /subclipse.html (author:alex; revision:406) 
 * /index.php (author:alex; revision:535) 
 * /plugin.xml (author:alex; revision:208) 
 * /rss.php (author:alex; revision:345) 
 * /list.html (author:alex; revision:535)
 * 
 * --------------------------------------------- 
 * Repository latest revision: 645
 */
public class SvnDisplayRepositoryTree extends SvnConnect{
    /*
     * args parameter is used to obtain a repository location URL, user's
     * account name & password to authenticate him to the server.
     */
	 
    public SvnDisplayRepositoryTree() {
       
    }
 
    public SvnDisplayRepositoryTree(String _url, String _name, String _password){
		 url = _url;
		 name = _name;
		 password = _password;
	 }
    
    public boolean existProject(String projectName){
		Collection isIn;
		SVNRepository repository = connect();
		
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
        Collection entries = repository.getDir(path, -1, null,
                (Collection) null);
        	
        return entries;
        /*
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println("/" + (path.equals("") ? "" : path + "/")
                    + entry.getName() + " (author:" + entry.getAuthor()
                    + "; revision:" + entry.getRevision() + ")");
            /*
             * Checking up if the entry is a directory.
             */
          /*  if (entry.getKind() == SVNNodeKind.DIR) {
                listEntries(repository, (path.equals("")) ? entry.getName()
                        : path + "/" + entry.getName());
            }
        }*/
    }
}