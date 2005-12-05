package xagdop.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
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
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;



public class SvnCommit{
	SVNRepository repository;
	SVNCommitClient svnCC;
	public SvnCommit(String url, String name, String password) throws SVNException{
		//super(SVNWCUtil.createDefaultAuthenticationManager(SvnConnect.getInstance().getName(), SvnConnect.getInstance().getPassword()),SVNWCUtil.createDefaultOptions(true));
		repository = SvnConnect.getInstance(url,name,password).getRepository();
		svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
	}
	public SvnCommit() throws SVNException{
		//super(SVNWCUtil.createDefaultAuthenticationManager(SvnConnect.getInstance().getName(), SvnConnect.getInstance().getPassword()),SVNWCUtil.createDefaultOptions(true));
		repository = SvnConnect.getInstance().getRepository();
		svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
	}
	
	
	
	public SVNCommitInfo createProject(String projectName, String description) throws SVNException {
		
		
		String dirPath = projectName;
		ISVNEditor editor = null;
		SVNCommitInfo commitInfo = null;
		/*Collection urls = new ArrayList();
		SVNURL url = SVNURL.parseURIEncoded(SvnConnect.getInstance().getUrl()+"/"+projectName);
		urls.add(url);
		SVNURL test = (SVNURL)urls.toArray();
		System.out.println("bla+ "+test.length+test[0].toString());
		try{
			
			svnCC.doMkDir((SVNURL[])urls.toArray(),description);
		}
		catch(SVNException e){
			e.toString();
		}*/
		
		//ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(SvnConnect.getInstance().getName(), SvnConnect.getInstance().getPassword());
		//svnC = new SVNCommitClient(authManager, SVNWCUtil.createDefaultOptions(true));
		//SvnConnect.getInstance().setUrl("svn://marine.edu.ups-tlse.fr/users/iupisi/m1isb4/svn/"+projectName);
		//repository = SvnConnect.getInstance().reconnect();
		
		//repository = svnCC.createRepository( SVNURL.parseURIEncoded("svn://marine.edu.ups-tlse.fr/users/iupisi/m1isb4/svn/"+projectName),true);
	
		/*
		 * Gets an editor for committing the changes to  the  repository.  NOTE:
		 * you  must not invoke methods of the SVNRepository until you close the
		 * editor with the ISVNEditor.closeEdit() method.
		 * 
		 * commitMessage will be applied as a log message of the commit.
		 * 
		 * ISVNWorkspaceMediator  will  be  used by the editor for  intermediate 
		 * file delta storing.
		 */
		try {
			editor = repository.getCommitEditor(description,new WorkspaceMediator());
		} catch (SVNException svne) {
			try{
				return editor.closeEdit();
			} catch (SVNException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		
		/*
		 * Adding a new directory containing a file to the repository.
		 */
		try {
			commitInfo = addDir(editor, dirPath);
		} catch (SVNException svne) {
			try {
				System.err.println("failed to add the directory with the file due to errors: "
						+ svne.getMessage());
				/*
				 * An exception was thrown during the work  of  the  editor. The
				 * editor must be aborted to behave in a  right  way in order to
				 * the breakdown won't cause any unstability.
				 */
				return editor.closeEdit();
			} catch (SVNException inner) {
				System.exit(0);
			}
		}
		
		return commitInfo;
		
	}
	
	
	private SVNCommitInfo addDir(ISVNEditor editor, String dirPath) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD, of course (actually, for a comit  editor  this 
		 * number is irrelevant)
		 */
		editor.openRoot(-1);
		/*
		 * Adds a new directory to the currently opened directory (in this  case 
		 * - to the  root  directory  for which the SVNRepository was  created). 
		 * Since this moment all changes will be applied to this new  directory.
		 * 
		 * dirPath is relative to the root directory.
		 * 
		 * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
		 * (the 3rd) parameter is set to  -1  since  the  directory is not added 
		 * with history (is not copied, in other words).
		 */
		editor.addDir(dirPath, null, -1);
		/*
		 * Adds a new file to the just added  directory. The  file  path is also 
		 * defined as relative to the root directory.
		 *
		 * copyFromPath (the 2nd parameter) is set to null and  copyFromRevision
		 * (the 3rd parameter) is set to -1 since  the file is  not  added  with 
		 * history.
		 */
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		
		return editor.closeEdit();
		
	}
	
	public void commit(CTreeNode node, String commitMessage) throws SVNException{
		SVNCommitClient svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
		File toCommit = new File(node.getLocalPath());
		
		File[] fileInDirectory = toCommit.listFiles();
		int i = 0;
		if(toCommit.isDirectory()){
			fileInDirectory = toCommit.listFiles(new FilenameFilter() {
			
				public boolean accept(File dir, String name) {
					if(dir.isHidden())
						return false;
					else return true;
				}
			
			});
			while(i<fileInDirectory.length){
				System.out.println(fileInDirectory[i].getAbsolutePath()+" : "+node.getLocalPath());
				i++;
			}
		svnCC.doCommit(toCommit.listFiles(),false,commitMessage, false, true);
		}
	}
	

	/*
	 * This class is to be used for temporary storage allocations needed  by  an
	 * ISVNEditor to write file delta that will be supplied  to  the  repository
	 * server.
	 */
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