package xagdop.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindowBuilder;



public class SvnCommit{
	SVNRepository repository;
	public SvnCommit(String url, String name, String password) throws SVNException{
		repository = SvnConnect.getInstance(url,name,password).getRepository();
		
	}
	public SvnCommit() throws SVNException{
		repository = SvnConnect.getInstance().getRepository();
	}
	
	
	
	public SVNCommitInfo createProject(String projectName, String description) {
		String dirPath = projectName;
		ISVNEditor editor = null;
		SVNCommitInfo commitInfo = null;
		
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
	
	
	
	
	
	public SVNCommitInfo addFile(String dirPath, String filePath, String description) throws SVNException, FileNotFoundException, IOException {
		ISVNEditor editor = null;
		String urlFile ="~" +dirPath+"/" +filePath;
		long deltaLength = 0 ;
		editor = repository.getCommitEditor(description,new WorkspaceMediator());
		
		FileInputStream data = new FileInputStream(urlFile);
		deltaLength = data.available();
		
		
		
		editor.openRoot(-1);
		
		
		editor.openDir(dirPath,-1);
		
		editor.addFile(dirPath+"/"+filePath, null, -1);
		
		
		/*
		 * The next steps are directed to applying delta to the  file  (that  is 
		 * the full contents of the file in this case).
		 */
		
		editor.applyTextDelta(dirPath+"/"+filePath, null);
		
		/*
		 * Creating  a  new  diff  window  (provided  the  size  of the delta  - 
		 * deltaLength) that will contain instructions of applying the delta  to
		 * the file in the repository.
		 */
		SVNDiffWindow diffWindow = SVNDiffWindowBuilder.createReplacementDiffWindow(deltaLength);
		
		/*
		 * Gets an OutputStream where the delta will be written to.
		 */
		OutputStream os = null;
		
		os = editor.textDeltaChunk(dirPath+"/"+filePath, diffWindow);
		try {
			/*
			 * If the file is not empty this code writes the file delta  to  the
			 * OutputStream.
			 */
			byte[] toWrite = new byte[1];
			for (int i = 0; i < deltaLength; i++) {
				data.read(toWrite);
				os.write(toWrite);
			}
		} catch (IOException ioe) {
			System.err.println("An i/o error while writing the delta bytes: "
					+ ioe.getMessage());
			throw ioe;
		} finally {
			/*
			 * Don't forget to close the stream after you have written the delta!
			 */
			if (os != null) {
				try {
					os.close();
				} catch (IOException ioeInternal) {
					throw ioeInternal;
				}
			}
		}
		
		data.close();
		
		/*
		 * Finally closes the delta when all the bytes are already written. From
		 * this  point  the previously defined diff window 'knows' how to  apply 
		 * the delta to the file (that will be created in the repository).
		 */
		
		editor.textDeltaEnd(dirPath+"/"+filePath);
		
		
		/*
		 * Closes the new added file.
		 */
		
		editor.closeFile(dirPath+"/"+filePath, null);
		
		/*
		 * Closes the new added directory.
		 */
		
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		
		editor.closeDir();
		
		return editor.closeEdit();
	}
	
	/*
	 * This method performs committing file modifications.
	 */
	public  SVNCommitInfo modifyFile(String dirPath, String filePath, String description) throws SVNException, FileNotFoundException, IOException {
		ISVNEditor editor = null;
		String urlFile ="~" +dirPath+"/" +filePath;
		long deltaLength = 0 ;
		editor = repository.getCommitEditor(description,new WorkspaceMediator());
		
		FileInputStream data = new FileInputStream(urlFile);
		deltaLength = data.available();
		
		
		
		editor.openRoot(-1);
		
		
		editor.openDir(dirPath,-1);
		
		editor.addFile(dirPath+"/"+filePath, null, -1);
		
		
		/*
		 * The next steps are directed to applying delta to the  file  (that  is 
		 * the full contents of the file in this case).
		 */
		
		editor.applyTextDelta(dirPath+"/"+filePath, null);
		
		/*
		 * Creating  a  new  diff  window  (provided  the  size  of the delta  - 
		 * deltaLength) that will contain instructions of applying the delta  to
		 * the file in the repository.
		 */
		SVNDiffWindow diffWindow = SVNDiffWindowBuilder.createReplacementDiffWindow(deltaLength);
		
		/*
		 * Gets an OutputStream where the delta will be written to.
		 */
		OutputStream os = null;
		
		os = editor.textDeltaChunk(dirPath+"/"+filePath, diffWindow);
		try {
			/*
			 * If the file is not empty this code writes the file delta  to  the
			 * OutputStream.
			 */
			byte[] toWrite = new byte[1];
			for (int i = 0; i < deltaLength; i++) {
				data.read(toWrite);
				os.write(toWrite);
			}
		} catch (IOException ioe) {
			System.err.println("An i/o error while writing the delta bytes: "
					+ ioe.getMessage());
			throw ioe;
		} finally {
			/*
			 * Don't forget to close the stream after you have written the delta!
			 */
			if (os != null) {
				try {
					os.close();
				} catch (IOException ioeInternal) {
					throw ioeInternal;
				}
			}
		}
		
		data.close();
		
		/*
		 * Finally closes the delta when all the bytes are already written. From
		 * this  point  the previously defined diff window 'knows' how to  apply 
		 * the delta to the file (that will be created in the repository).
		 */
		
		editor.textDeltaEnd(dirPath+"/"+filePath);
		
		
		/*
		 * Closes the new added file.
		 */
		
		editor.closeFile(dirPath+"/"+filePath, null);
		
		/*
		 * Closes the new added directory.
		 */
		
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		
		editor.closeDir();
	
		return editor.closeEdit();
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