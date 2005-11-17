package src.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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



public class SvnCommit extends SvnConnect{
	
	public SvnCommit(String url, String name, String password){
		super(url,name,password);
	}
	public SvnCommit(){
		super();
	}
	
	
	
	public int createProject(String projectName, String description) {
		String dirPath = projectName;
		SVNRepository repository = connect();    
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
			System.err.println("error while getting a commit editor for the location '"
					+ _url + "': " + svne.getMessage());
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
				editor.abortEdit();
			} catch (SVNException inner) {
			}
		}
		//System.out.println("The directory was added.");
		/*
		 * Displaying the commit info.
		 */
		printCommitInfo(commitInfo);
		return 0;
		
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
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
		
		
	}
	
	private SVNCommitInfo addFile(ISVNEditor editor, String dirPath, String filePath, byte[] data) throws SVNException {
		editor.openRoot(-1);
		editor.openDir(dirPath,-1);
		editor.addFile(filePath, null, -1);
		/*
		 * The next steps are directed to applying delta to the  file  (that  is 
		 * the full contents of the file in this case).
		 */
		editor.applyTextDelta(filePath, null);
		long deltaLength = data.length;
		/*
		 * Creating  a  new  diff  window  (provided  the  size  of the delta  - 
		 * deltaLength) that will contain instructions of applying the delta  to
		 * the file in the repository.
		 */
		SVNDiffWindow diffWindow = SVNDiffWindowBuilder
		.createReplacementDiffWindow(deltaLength);
		/*
		 * Gets an OutputStream where the delta will be written to.
		 */
		OutputStream os = editor.textDeltaChunk(filePath, diffWindow);
		
		try {
			/*
			 * If the file is not empty this code writes the file delta  to  the
			 * OutputStream.
			 */
			for (int i = 0; i < deltaLength; i++) {
				os.write(data[i]);
			}
		} catch (IOException ioe) {
			System.err.println("An i/o error while writing the delta bytes: "
					+ ioe.getMessage());
		} finally {
			/*
			 * Don't forget to close the stream after you have written the delta!
			 */
			if (os != null) {
				try {
					os.close();
				} catch (IOException ioeInternal) {
					//
				}
			}
		}
		
		/*
		 * Finally closes the delta when all the bytes are already written. From
		 * this  point  the previously defined diff window 'knows' how to  apply 
		 * the delta to the file (that will be created in the repository).
		 */
		editor.textDeltaEnd(filePath);
		/*
		 * Closes the new added file.
		 */
		editor.closeFile(filePath, null);
		/*
		 * Closes the new added directory.
		 */
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}
	
	/*
	 * This method performs committing file modifications.
	 */
	private static SVNCommitInfo modifyFile(ISVNEditor editor, String dirPath,
			String filePath, byte[] newData) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		editor.openRoot(-1);
		/*
		 * Opens a next subdirectory (in this example program it's the directory
		 * added  in  the  last  commit).  Since this moment all changes will be
		 * applied to this directory.
		 * 
		 * dirPath is relative to the root directory.
		 * -1 - revision is HEAD
		 */
		editor.openDir(dirPath, -1);
		/*
		 * Opens the file added in the previous commit.
		 * 
		 * filePath is also defined as a relative path to the root directory.
		 */
		editor.openFile(filePath, -1);
		/*
		 * The next steps are directed to applying and writing the file delta.
		 */
		editor.applyTextDelta(filePath, null);
		long deltaLength = newData.length;
		/*
		 * Creating  a  new  diff  window  (provided  the  size  of the delta  - 
		 * deltaLength) that will contain instructions of applying the delta  to
		 * the file in the repository.
		 */
		SVNDiffWindow diffWindow = SVNDiffWindowBuilder
		.createReplacementDiffWindow(deltaLength);
		/*
		 * Gets an OutputStream where the delta will be written to.
		 */
		OutputStream os = editor.textDeltaChunk(filePath, diffWindow);
		try {
			/*
			 * If there's non-empty file delta this code writes the delta to the
			 * OutputStream.
			 */
			for (int i = 0; i < deltaLength; i++) {
				os.write(newData[i]);
			}
		} catch (IOException ioe) {
			System.err.println("An i/o error while writing the delta bytes: "
					+ ioe.getMessage());
		} finally {
			/*
			 * Don't forget to close the stream after you have written the delta!
			 */
			if (os != null) {
				try {
					os.close();
				} catch (IOException ioeInternal) {
					//
				}
			}
		}
		
		/*
		 * Finally closes the delta when all the bytes are already written. From
		 * this point the previously defined diff window 'knows'  how  to  apply 
		 * the delta to the file.
		 */
		editor.textDeltaEnd(filePath);
		
		/*
		 * Closes the file.
		 */
		editor.closeFile(filePath, null);
		
		/*
		 * Closes the directory.
		 */
		editor.closeDir();
		
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}
	
	/*
	 * This method performs committing a deletion of a directory.
	 */
	private static SVNCommitInfo deleteDir(ISVNEditor editor, String dirPath)
	throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		editor.openRoot(-1);
		/*
		 * Deletes the subdirectory with all its contents.
		 * 
		 * dirPath is relative to the root directory.
		 */
		editor.deleteEntry(dirPath, -1);
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}
	
	/*
	 * This  method  performs how a directory in the repository can be copied to
	 * branch.
	 */
	private static SVNCommitInfo copyDir(ISVNEditor editor, String destDirPath,
			String srcDirPath, long revision) throws SVNException {
		/*
		 * Always called first. Opens the current root directory. It  means  all
		 * modifications will be applied to this directory until  a  next  entry
		 * (located inside the root) is opened/added.
		 * 
		 * -1 - revision is HEAD
		 */
		editor.openRoot(-1);
		/*
		 * Adds a new directory that is a copy of the existing one.
		 * 
		 * srcDirPath   -  the  source  directory  path (relative  to  the  root 
		 * directory).
		 * 
		 * destDirPath - the destination directory path where the source will be
		 * copied to (relative to the root directory).
		 * 
		 * revision    - the number of the source directory revision. 
		 */
		editor.addDir(destDirPath, srcDirPath, revision);
		/*
		 * Closes the just added copy of the directory.
		 */
		editor.closeDir();
		/*
		 * Closes the root directory.
		 */
		editor.closeDir();
		/*
		 * This is the final point in all editor handling. Only now all that new
		 * information previously described with the editor's methods is sent to
		 * the server for committing. As a result the server sends the new
		 * commit information.
		 */
		return editor.closeEdit();
	}
	
	/*
	 * This method is used to print out new information about the last
	 * successful commit.
	 */
	private static void printCommitInfo(SVNCommitInfo commitInfo) {
		/*
		 * The author of the last commit.
		 */
		System.out.println("The last author:" + commitInfo.getAuthor());
		/*
		 * The time moment when the changes were committed.
		 */
		System.out.println("Date:" + commitInfo.getDate().toString());
		/*
		 * And the new committed revision.
		 */
		System.out.println("Committed to revision "
				+ commitInfo.getNewRevision());
		System.out.println("");
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