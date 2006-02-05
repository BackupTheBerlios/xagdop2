package xagdop.Svn;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
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
	
	
	public void createProject(String projectName, String description) throws SVNException, IOException {
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
			dependencies.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><files><dependencies></dependencies><toUpdate></toUpdate></files>");
			dependencies.close();
		} catch (IOException e) {
			ErrorManager.getInstance().setErrMsg("Création du fichier des dépendances.\nVeuillez vérifier les droits du dossier.");
			ErrorManager.getInstance().setErrTitle("Création de projet impossible");
			throw e;
		}
		
		
		//wcClient.doAdd(project,false,false,false,true);
		
		//sendFile(project,description);
		
	}
	
	
	
	
	/**
	 * @param node Noeud à envoyer
	 * @return Un tableau de file, contenant l'ensemble des fichiers et des dossiers à envoyer.
	 * @throws SVNException 
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
				try {
					wcClient.doAdd(toCommit,false, false, true, false);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+toCommit.getName()+" a échoué.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
					throw e;
				}
				
			}
		
			//Test si c'est un dossier
			if(toCommit.isDirectory()){
				//Commande equivalente à svn add, qui ajoute le dossier ainsi que tous les fichiers parents non versionnés et les les sous répertoires 
				try {
					wcClient.doAdd(toCommit,false, false, true, true);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+toCommit.getName()+" a échoué.\n"+e.getCause());
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
					try {
						wcClient.doAdd(fileInDirectory[i],false, false, false, true);
					} catch (SVNException e) {
						ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+fileInDirectory[i].getName()+" a échoué.\n"+e.getCause());
						ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
						throw e;
					}
				}
				else
					//Si le fichier est un repertoire, on reéfectue le traitement
					if(fileInDirectory[i].isDirectory())
						doAdd(fileInDirectory[i]);
			}
		}else
			//Si on a un fichier, et qu'il n'est pas versionné on l'ajoute au repository
			if(!SvnHistory.isUnderVersion(file))
				try {
					wcClient.doAdd(file,false, false, false, false);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'ajout du fichier "+file.getName()+" a échoué.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Ajout de fichier impossible");
					throw e;
				}
	}
	
	
	
	
	
	
	
	
	/**
	 * @param node : Noeud representant les fichiers à envoyer
	 * @param commitMessage : Message qui commente les modifications faites
	 * @throws SVNException 
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
			try {
				svnCC.doCommit(fileInDirectory,false,commitMessage, false, true);
			} catch (SVNException e) {
				ErrorManager.getInstance().setErrMsg("L'envoi du dossier "+toCommit.getName()+" a échoué.\n"+e.getCause());
				ErrorManager.getInstance().setErrTitle("Envoi impossible");
				throw e;
			}
		}else{
			
			if(!SvnHistory.isUnderVersion(toCommit)){
				//Ajout du fichier et des parents non versionnés, puis envoi de ces fichiers
				try {
					svnCC.doCommit(fileToAdd(node),false,commitMessage, false, false);
				} catch (SVNException e) {
					ErrorManager.getInstance().setErrMsg("L'envoi du fichier "+toCommit.getName()+" a échoué.\n"+e.getCause());
					ErrorManager.getInstance().setErrTitle("Envoi impossible");
					throw e;
				}
			}else{
				//Si le fichiers est versionné, on l'envoi
				sendFile(toCommit,commitMessage);
			}
		}
		//Si le fichiers des dépendances est modifié on l'envoi en même temps
		/*if(SvnHistory.isModified(DependenciesParser.getInstance().getFile()))
			sendFile(DependenciesParser.getInstance().getFile(),"");*/
		
	}
	
	
	/**
	 * @param name : Fichier à envoyer
	 * @param commitMessage : Message d'envoi
	 * @throws SVNException
	 */
	public void sendFile(File name, String commitMessage) throws SVNException{
		ArrayList fileInDirectory = new ArrayList();
		fileInDirectory.add(name);
		File[] file = new File[fileInDirectory.size()];
		try{
			svnCC.doCommit((File[])fileInDirectory.toArray(file),false,commitMessage, true, false);
		}catch (SVNException e) {
			ErrorManager.getInstance().setErrMsg("L'envoi du fichier "+name.getName()+" a échoué.\n"+e.getCause());
			ErrorManager.getInstance().setErrTitle("Envoi impossible");
			throw e;
		}
	}
	
}