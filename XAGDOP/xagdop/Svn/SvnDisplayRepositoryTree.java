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
    
    /**
     * @param projectName nom du projet à tester
     * @return vrai si il existe faux sinon
     * Test si un projet du même nom esiste déjà
     */
    public boolean existProject(String projectName){
		Collection isIn;
		try {
			isIn = listEntries(".");
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
    
    
    /**
     * @param path path dont on veut récupérer les infos
     * @return Une collection contenant les différents fichiers contenus
     * @throws SVNException
     */
    public  Collection listEntries(String path)
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