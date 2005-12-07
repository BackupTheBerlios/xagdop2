package xagdop.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
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
	
	public  SVNCommitInfo removeFile(String dirPath, String filePath) throws SVNException{
		ISVNEditor editor  = null;

		try {
			editor = repository.getCommitEditor("",new WorkspaceMediator());
		} catch (SVNException svne) {
			try{
				return editor.closeEdit();
			} catch (SVNException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		
		editor.openRoot(-1);
		editor.openDir(dirPath,-1);
		editor.deleteEntry(dirPath+"/"+filePath,-1);
		editor.closeDir();
		editor.closeDir();
		
		return editor.closeEdit();
	}
	
	
	
	

	
	public  void deleteDir(CTreeNode node)
	throws SVNException {
		System.out.println(((CTreeNode)node.getParent()).getName());
		SVNCommitClient svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
		ArrayList urlDirectory = new ArrayList();
		urlDirectory.add(SVNURL.parseURIDecoded(SvnConnect.getInstance().getUrl()+node.getName()));
		SVNURL[] url = new SVNURL[urlDirectory.size()];
		
		svnCC.doDelete((SVNURL[])urlDirectory.toArray(url),"");
		File toCommit = new File(((CTreeNode)node.getParent()).getLocalPath());
		
		/*
		if(toCommit.isDirectory()){
			File[] fileInDirectory = toCommit.listFiles(new FilenameFilter() {
			
				public boolean accept(File dir, String name) {
					if(dir.isHidden()|| name.equals(".svn")){
						return false;
					}
					else return true;
				}
			
			});
		svnCC.doCommit(fileInDirectory,false,"", false, true);
		}else{
			ArrayList fileInDirectory = new ArrayList();
			fileInDirectory.add(toCommit);
			
			File[] file = new File[fileInDirectory.size()];
			
			svnCC.doCommit((File[])fileInDirectory.toArray(file),false,"", true, false);
		}*/
	
		/*
		ISVNEditor editor  = null;

		try {
			editor = repository.getCommitEditor("",new WorkspaceMediator());
		} catch (SVNException svne) {
			try{
				return editor.closeEdit();
			} catch (SVNException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		//editor.openRoot(-1);
		/*
		 * Deletes the subdirectory with all its contents.
		 * 
		 * dirPath is relative to the root directory.
		 */
		//editor.deleteEntry(dirPath, -1);
		/*
		 * Closes the root directory.
		 */
		//editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		//return editor.closeEdit();
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
