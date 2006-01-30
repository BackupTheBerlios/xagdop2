package xagdop.Svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.ISVNWorkspaceMediator;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.DependenciesParser;



public class SvnCommit{
	protected SVNCommitClient svnCC;
	public SvnCommit(String url, String name, String password) throws SVNException{
		SVNRepository repository = SvnConnect.getInstance(url,name,password).getRepository();
		svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
	}
	public SvnCommit() throws SVNException{
		SVNRepository repository = SvnConnect.getInstance().getRepository();
		svnCC = new SVNCommitClient(repository.getAuthenticationManager(),SVNWCUtil.createDefaultOptions(true) );
	}
	
	
	public void createProject(String projectName, String description) throws SVNException {
		//SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		if(!SvnHistory.isUnderVersion(new File(IPreferences.getDefaultPath()))){
			SvnUpdate svnu = new SvnUpdate() ;
			svnu.checkOut((CTreeNode)XAGDOP.getInstance().getTree().getSelectedNode().getRoot());
		}
		File project = new File(IPreferences.getDefaultPath()+projectName);
		if(!project.exists())
			project.mkdir();
		File icon = new File(IPreferences.getDefaultPath()+projectName+File.separator+"icones");
		if(!icon.exists())
			icon.mkdir();
		FileWriter dependencies;
		try {
			dependencies = new FileWriter(project.getAbsolutePath()+File.separator+"dependencies.xml");
			dependencies.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><files><dependencies></files></dependencies>");
			dependencies.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//wcClient.doAdd(project,false,false,false,true);
		
		//sendFile(project,description);
		
	}
	
	
	public SVNCommitInfo createProjec(String projectName, String description) throws SVNException {
		
		SVNRepository repository = SvnConnect.getInstance().getRepository();
		String dirPath = projectName;
		ISVNEditor editor = null;
		SVNCommitInfo commitInfo = null;

		try {
			editor = repository.getCommitEditor(description,new WorkspaceMediator());
		} catch (SVNException svne) {
			try{
				return editor.closeEdit();
			} catch (SVNException e) {
				e.printStackTrace();
				//System.exit(0);
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
	
	
	
	
	/**
	 * @param node Noeud à envoyer
	 * @return Un tableau de file, contenant l'ensemble des fichiers et des dossiers à envoyer.
	 * @throws SVNException
	 * Calcule tous les fichiers parents à envoyer qui ne sont pas versionnés 
	 */
	public File[] fileToAdd(CTreeNode node) throws SVNException{
		ArrayList fileToCommit = new ArrayList();
		//Creation du fichier equilavent au noeud passé en parametre
		File toCommit = new File(node.getLocalPath());
		SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		//Test si le fichier est sous une version controlée
		if(!SvnHistory.isUnderVersion(toCommit)){
			
			//Création d'une liste qui contient tous les fichiers à envoyer
			while(!SvnHistory.isUnderVersion(toCommit)){
			//tant que le file n'est pas sous une version controlée on l'ajoute à la liste
				fileToCommit.add(toCommit);
				//Récuperation du fichier parent
				toCommit = toCommit.getParentFile();
				//Si on est arrivé au niveau du projet alors on arrete d'ajouter des nouveaux fichiers dans la liste
				if(((CTreeNode)node.getRoot()).getLocalPath().compareTo(toCommit.getPath())==0)
					break;
			}
			//Récuperation du chier de départ
			toCommit = new File(node.getLocalPath());
			//Test si le File est un fichier ou un dossier
			if(toCommit.isFile()){
				
				//Commande equivalente à svn add, qui ajoute le fichier non versionné au repository, ainsi que tout les fichiers parents qui sont non versionnés
				wcClient.doAdd(toCommit,false, false, true, false);
				
			}
		
			//Test si c'est un dossier
			if(toCommit.isDirectory()){
				//Commande equivalente à svn add, qui ajoute le dossier ainsi que tous les fichiers parents non versionnés et les les sous répertoires 
				wcClient.doAdd(toCommit,false, false, true, true);
			}
			//Transformation de la liste en tableau
			File[] file = new File[fileToCommit.size()];
			return (File[])fileToCommit.toArray(file);
		}
		
		return null;
		
		
	}
	
	
	/**
	 * @param file : Fichier de base
	 * @throws SVNException
	 * Si un dossier est versionné, la fonction récupére tous les sous fichiers et sous dossiers non versionnés et les ajoute au repository
	 */
	public void doAdd(File file) throws SVNException{
		SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		//Verification si le fichier est bien un dossier
		if(file.isDirectory()){
			//Récuperation des fichiers qui sont autorisés à etre envoyés
			File[] fileInDirectory = file.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					File directory = new File(dir.getAbsolutePath()+"/"+name); 
					if(directory.isDirectory()&&!directory.isHidden())
						return true;
					//if(XAGDOP.getInstance().getUser().is)
					if(name.endsWith(".pre")||name.endsWith(".pog")||name.endsWith(".apes"))
						return true;

					return false;
				}
		
			});
			//Pour chaque fichier test si il est versionné ou non
			for(int i = 0; i< fileInDirectory.length;i++){			
				if(!SvnHistory.isUnderVersion(fileInDirectory[i])){
					//Si il n'est pas versionné, ajout de ce dernier au repository
					wcClient.doAdd(fileInDirectory[i],false, false, false, true);
				}
				else
					//Si le fichier est un repertoire, on reéfectue le traitement
					if(fileInDirectory[i].isDirectory())
						doAdd(fileInDirectory[i]);
			}
		}else
			//Si on a un fichier, et qu'il n'est pas versionné on l'ajoute au repository
			if(!SvnHistory.isUnderVersion(file))
					wcClient.doAdd(file,false, false, false, false);
	}
	
	
	
	
	
	
	
	
	/**
	 * @param node : Noeud representant les fichiers à envoyer
	 * @param commitMessage : Message qui commente les modifications faites
	 * @throws SVNException
	 * Envoi les modifications faites sur les fichiers ainsi que les nouveaux fichiers crées
	 */
	public void commit(CTreeNode node, String commitMessage) throws SVNException{
		File toCommit = new File(node.getLocalPath());
		//Si on doit envoyer un dossier
		if(toCommit.isDirectory()){
			File[] fileInDirectory ;
			//Si le dossier n'est pas sous une version controlée
			if(!SvnHistory.isUnderVersion(toCommit)){
				/*CTreeNode tmp = (CTreeNode)node.getRoot();
				svnCC.doImport(toCommit,SVNURL.parseURIDecoded(SvnConnect.getInstance().getUrl()+toCommit.getPath().replaceAll(tmp.getLocalPath(),"")),commitMessage,true);*/
				//On recupere tout les fichiers parents à envoyer
				fileInDirectory = fileToAdd(node);
				
			}else{
				//Liste les fichiers que l'utilisateur à le droit d'envoyer
				fileInDirectory = toCommit.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						File directory = new File(dir.getAbsolutePath()+"/"+name); 
						if(directory.isDirectory()&&!directory.isHidden())
							return true;
						if(name.endsWith(".xml")||name.endsWith(".pre")||name.endsWith(".pog")||name.endsWith(".apes"))
							return true;

						return false;
				}
			
			});
				//Ajout des fichiers non versionnés dans le cas où le dossier est versionné
				doAdd(toCommit);			
			}
			//Envoi des fichiers
			svnCC.doCommit(fileInDirectory,false,commitMessage, false, true);
		}else{
			
			if(!SvnHistory.isUnderVersion(toCommit)){
				/*CTreeNode tmp = (CTreeNode)node.getRoot();
				svnCC.doImport(toCommit,SVNURL.parseURIDecoded(SvnConnect.getInstance().getUrl()+toCommit.getPath().replaceAll(tmp.getLocalPath(),"")),commitMessage,false);*/
				//Ajout du fichier et des parents non versionnés, puis envoi de ces fichiers
				svnCC.doCommit(fileToAdd(node),false,commitMessage, false, false);
			}else{
				//Si le fichiers est versionné, on l'envoi
				sendFile(toCommit,commitMessage);
			}
		}
		//Si le fichiers des dépendances est modifié on l'envoi en même temps
		if(SvnHistory.isModified(DependenciesParser.getInstance().getFile()))
			sendFile(DependenciesParser.getInstance().getFile(),"");
		
	}
	
	
	/**
	 * @param name : Fichier à envoyer
	 * @param commitMessage : Message d'envoi
	 * @throws SVNException
	 */
	public void sendFile(File name, String commitMessage) throws SVNException{
		ArrayList fileInDirectory = new ArrayList();
		fileInDirectory.add(name);
		/*if(SvnHistory.isModified(DependenciesParser.getInstance().getFile()))
			fileInDirectory.add(DependenciesParser.getInstance().getFile());*/
		File[] file = new File[fileInDirectory.size()];
		svnCC.doCommit((File[])fileInDirectory.toArray(file),false,commitMessage, true, false);
		
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