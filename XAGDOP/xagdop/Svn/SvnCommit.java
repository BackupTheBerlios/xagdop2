package xagdop.Svn;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CRole;
import xagdop.Controleur.CTreeNode;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;



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
	
	
	/**
	 * @param node Noeud ?? envoyer
	 * @return Un tableau de file, contenant l'ensemble des fichiers et des dossiers ?? envoyer.
	 * @throws SVNException 
	 * @throws SVNException
	 * Calcule tous les fichiers parents ?? envoyer qui ne sont pas versionn??s 
	 */
	public File[] fileToAdd(CTreeNode node) throws SVNException{
		ArrayList fileToCommit = new ArrayList();
		//Creation du fichier equilavent au noeud pass?? en parametre
		File toCommit = new File(node.getLocalPath());
		SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		//Test si le fichier est sous une version control??e
		if(!SvnHistory.isUnderVersion(toCommit)){
			
			//Cr??ation d'une liste qui contient tous les fichiers ?? envoyer
			while(!SvnHistory.isUnderVersion(toCommit)){
			//tant que le file n'est pas sous une version control??e on l'ajoute ?? la liste
				fileToCommit.add(toCommit);
				//R??cuperation du fichier parent
				toCommit = toCommit.getParentFile();
				//Si on est arriv?? au niveau du projet alors on arrete d'ajouter des nouveaux fichiers dans la liste
				if(((CTreeNode)node.getRoot()).getLocalPath().compareTo(toCommit.getPath())==0)
					break;
			}
			//R??cuperation du chier de d??part
			toCommit = new File(node.getLocalPath());
			//Test si le File est un fichier ou un dossier
			if(toCommit.isFile()){
				
				//Commande equivalente ?? svn add, qui ajoute le fichier non versionn?? au repository, ainsi que tout les fichiers parents qui sont non versionn??s
				try {
					wcClient.doAdd(toCommit,false, false, true, false);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+toCommit.getName()+" a ??chou??.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
					throw e;
				}
				
			}
		
			//Test si c'est un dossier
			if(toCommit.isDirectory()){
				//Commande equivalente ?? svn add, qui ajoute le dossier ainsi que tous les fichiers parents non versionn??s et les les sous r??pertoires 
				try {
					wcClient.doAdd(toCommit,false, false, true, true);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+toCommit.getName()+" a ??chou??.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
					throw e;
				}
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
	 * @throws SVNException 
	 * @throws SVNException
	 * Si un dossier est versionn??, la fonction r??cup??re tous les sous fichiers et sous dossiers non versionn??s et les ajoute au repository
	 */
	public void doAdd(File file,String project) throws SVNException{
		final String proj = project;
		SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
		//Verification si le fichier est bien un dossier
		if(file.isDirectory()){
			//R??cuperation des fichiers qui sont autoris??s ?? etre envoy??s
			File[] fileInDirectory = file.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return CRole.getInstance().canShow(dir,proj);
				}
		
			});
			//Pour chaque fichier test si il est versionn?? ou non
			for(int i = 0; i< fileInDirectory.length;i++){			
				if(!SvnHistory.isUnderVersion(fileInDirectory[i])){
					//Si il n'est pas versionn??, ajout de ce dernier au repository
					try {
						wcClient.doAdd(fileInDirectory[i],false, false, false, true);
					} catch (SVNException e) {
						ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+fileInDirectory[i].getName()+" a ??chou??.\n"+e.getCause());
						ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
						throw e;
					}
				}
				else
					//Si le fichier est un repertoire, on re??fectue le traitement
					if(fileInDirectory[i].isDirectory())
						doAdd(fileInDirectory[i],project);
			}
		}else
			//Si on a un fichier, et qu'il n'est pas versionn?? on l'ajoute au repository
			if(!SvnHistory.isUnderVersion(file))
				try {
					wcClient.doAdd(file,false, false, false, false);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+file.getName()+" a ??chou??.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
					throw e;
				}
	}
	
	
	
	
	
	
	
	
	/**
	 * @param node : Noeud representant les fichiers ?? envoyer
	 * @param commitMessage : Message qui commente les modifications faites
	 * @throws SVNException
	 * Envoi les modifications faites sur les fichiers ainsi que les nouveaux fichiers cr??es
	 * @throws Exception 
	 */
	public void commit(CTreeNode node, String commitMessage) throws Exception{
		final CTreeNode nodeC = node;
		File toCommit = new File(node.getLocalPath());
		//Si on doit envoyer un dossier
		if(toCommit.isDirectory()){
			File[] fileInDirectory ;
			//Si le dossier n'est pas sous une version control??e
			if(!SvnHistory.isUnderVersion(toCommit)){
				//On recupere tout les fichiers parents ?? envoyer
				fileInDirectory = fileToAdd(node);
				
			}else{
				//Liste les fichiers que l'utilisateur ?? le droit d'envoyer
				fileInDirectory = toCommit.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
//						File directory = new File(dir.getAbsolutePath()+"/"+name); 
//						if(directory.isDirectory()&&!directory.isHidden())
//							return true;
//						if(name.endsWith(".xml")||name.endsWith(".iepp")||name.endsWith(".pog")||name.endsWith(".apes"))
//							return true;
//
//						return false;
						return CRole.getInstance().canShow(dir,nodeC.getProject().getName());
				}
			
			});
				//Ajout des fichiers non versionn??s dans le cas o?? le dossier est versionn??
				doAdd(toCommit,node.getProject().getName());			
			}
			//Envoi des fichiers
			try {
				svnCC.doCommit(fileInDirectory,false,commitMessage, false, true);
			} catch (SVNException e) {
				ErrorManager.getInstance().setErrMsg("L'envoi du dossier "+toCommit.getName()+" a ??chou??.\n"+e.getCause());
				ErrorManager.getInstance().setErrTitle("Envoi impossible");
				throw e;
			}
		}else{
			
			if(!SvnHistory.isUnderVersion(toCommit)){
				//Ajout du fichier et des parents non versionn??s, puis envoi de ces fichiers
				try {
					svnCC.doCommit(fileToAdd(node),false,commitMessage, false, false);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'envoi du fichier "+toCommit.getName()+" a ??chou??.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Envoi impossible");
					throw e;
				}
			}else{
				//Si le fichiers est versionn??, on l'envoi
				sendFile(toCommit,commitMessage);
			}
		}
		//Si le fichiers des d??pendances est modifi?? on l'envoi en m??me temps
		if(SvnHistory.isModified(UsersParser.getInstance().getUsersXML())||SvnHistory.isModified(ProjectsParser.getInstance().getProjectXML()))
			sendXMLFile();
		if(SvnHistory.isModified(DependenciesParser.getInstance().getFile(node.getProject().getName())))
			sendFile(DependenciesParser.getInstance().getFile(node.getProject().getName()),"");
		CRole.getInstance().refreshRole();
	}
	
	public void commitRemoveProject(CTreeNode toRemove,String commitMessage) throws SVNException{
		ArrayList fileInDirectory = new ArrayList();
		fileInDirectory.add((File)toRemove.getUserObject());
		File[] file = new File[fileInDirectory.size()];
		try {
			svnCC.doCommit((File[])fileInDirectory.toArray(file),false,commitMessage, false, true);
		} catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("La suppression du projet "+toRemove.getName()+" a ??chou??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Suppression impossible");
			throw e;
		}
	}
	
	public void sendXMLFile() throws Exception{
		ArrayList fileInDirectory = new ArrayList();
		fileInDirectory.add(UsersParser.getInstance().getUsersXML());
		fileInDirectory.add(ProjectsParser.getInstance().getProjectXML());
		File[] file = new File[fileInDirectory.size()];
		try{
			svnCC.doCommit((File[])fileInDirectory.toArray(file),false,"Administration du projet", true, false);
		}catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("L'envoi des fichiers XML a ??chou??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Envoi impossible");
			throw e;
		}
	}
	
	/**
	 * @param name : Fichier ?? envoyer
	 * @param commitMessage : Message d'envoi
	 * @throws SVNException
	 */
	public void sendFile(File name, String commitMessage) throws SVNException{
		ArrayList fileInDirectory = new ArrayList();
		fileInDirectory.add(name);
		File[] file = new File[fileInDirectory.size()];
		try{
			if(!SvnHistory.isUnderVersion(name)){
				SVNWCClient wcClient = new SVNWCClient(SvnConnect.getInstance().getRepository().getAuthenticationManager(), SVNWCUtil.createDefaultOptions(true));
				wcClient.doAdd(name,false, false, false, true);	
			}
			svnCC.doCommit((File[])fileInDirectory.toArray(file),false,commitMessage, true, true);
		}catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("L'envoi du fichier "+name.getName()+" a ??chou??.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Envoi impossible");
			throw e;
		}
	}
	
}