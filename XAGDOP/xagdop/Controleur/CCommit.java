package xagdop.Controleur;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.IProjectTree;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.IeppNitParser;
import xagdop.Parser.POGParser;
import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnHistory;




public class CCommit{
	
	
	public CCommit(CTreeNode currentNode) throws SVNException{
		recCommit(currentNode,".apes");
		recCommit(currentNode,".pog");
		recCommit(currentNode,".iepp");
	}

	
	
	//Permet d'envoyer le fichier contenu dans le Noeud
	public void beforeCommit(CTreeNode node) throws SVNException{
	
		//Recuperation du nom de fichier a envoyer
		String nameOfFile = node.getName();
		
		
		String pathToRoot = ((CTree)(XAGDOP.getInstance().getTree().getModel())).treePathName(node);
		
		//R?cup?ration du fichier a envoyer
		File toCommit = new File(node.getLocalPath());
		
		
		

			
			
			//-----------------------------------------
			//Si le fichier est un apes -->
			//-----------------------------------------
			if (nameOfFile.endsWith(".apes"))
			{
				
				
				sendApesFile(toCommit,node,pathToRoot);
				
			}
			
			//-----------------------------------------
			//Si le fichier est un pog -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".pog"))
			{
				
				sendPogFile(toCommit,node,pathToRoot);
				
				
				
						
			}
			
//			-----------------------------------------
			//Si le fichier est un iepp -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".iepp")) 
			{
				sendIeppFile(toCommit,node,pathToRoot);
			}
			//Cas d'erreur
			else
			{
				
			}
			
		
		
	}
	
	/*
	 * Permet de rajouter un fichier Iepp au fichier de dependances
	 * 
	 * 
	 */
	protected void sendIeppFile(File toCommit, CTreeNode node, String pathToRoot) {
	
		
		DependenciesParser DP = DependenciesParser.getInstance();
		
			//Modifier l'interieur du fichier pog en
			//mettant en relatif le chemin du fichier apes dependant
			
			//Ouverture du parser du fichier POG Correspondant
			IeppNitParser INP = new IeppNitParser(toCommit);	
			//Recuperation du chemin absolue du fichier apes d?pendant
			ArrayList pathDependantApesFile = INP.getApes();
			
//			Initialisation du parcours
			int i = 0;

			

			//On parcours la liste
			for (i=0;i<pathDependantApesFile.size();i++)
			{
					DP.addIeppToApes(node.getProject().getName()+File.separator+pathDependantApesFile.get(i),pathToRoot);
			}
			
			
			/*
			//On le rajoute dans les pog sans model
			ArrayList pathDependantPogFile = INP.getPog();
			
//			Initialisation du parcours


			

			//On parcours la liste
			for (i=0;i<pathDependantApesFile.size();i++)
			{
					DP.addIeppToApes(node.getProject().getName()+File.separator+pathDependantApesFile.get(i),pathToRoot);
			}
			
			*/
		
			
	
		
	}



	protected void sendPogFile(File toCommit, CTreeNode node, String pathToRoot) throws SVNException {

		
		DependenciesParser DP = DependenciesParser.getInstance();
		
		
		//	Si le fichier est tout neuf
		if (!SvnHistory.isUnderVersion(toCommit))
		{
		
			//Modifier l'interieur du fichier pog en
			//mettant en relatif le chemin du fichier apes dependant
			
			//Ouverture du parser du fichier POG Correspondant
			POGParser POGP = new POGParser(toCommit);	
			//Recuperation du chemin absolue du fichier apes d?pendant
			String pathDependantApesFile = POGP.getPathApesFile();
			
			if (!pathDependantApesFile.equals(""))
			{
				//Recuperation du path absolue de la racine des projets
				String pathGlobal = ((CTreeNode)((IProjectTree)XAGDOP.getInstance().getTree()).getModel().getRoot()).getLocalPath();
				//Recuperation du pathToRoot du fichier Apes en 
				//Enlevant les premiers caracteres correspondant au debut du chemin absolue
				String pathSemiGlobal = pathDependantApesFile.substring(pathGlobal.length()+1);
			
				//Ajout dans le DependenciesParser du Pog correspondant
				//addPog(Apes,Pog)
				DP.addPog(pathSemiGlobal,pathToRoot);
			}
			else
			{
				DP.addPog(pathToRoot);
			}
			//TODO
			//Appelle a la methode qui permet de calculer le chemin relatif
			//relative(Pog,Apes)
			//String relativeToApes = relative(node.getLocalPath(),pathDependantApesFile);
			
			//Modification du fichier POG	
			//POGP.setApesPathToRelative(relativeToApes);
			
			
			
			
			
		}
			
			
		//Si le fichier est anciens
		else if (SvnHistory.isModified(toCommit))
		{
			//Indiquer qu'il faut modifier les fichiers Iepp dependant

			//On recupere la liste de tous les iepp dependant de l'apes
			
			ArrayList allIepp = DP.getIeppFromPog(pathToRoot);
			
			
			
			//Initialisation du parcours
			int j = 0;

			

			//On parcours la liste
			for (j=0;j<allIepp.size();j++)
			{
				//On indique qu'il faut mettre a jour tous les fichiers
				//Qui sont d?pendants du fichier apes que l'on veux envoyer
				//A condition qu'il ne soit pas deja a modifier
				if (!DP.isToUpdate((String)allIepp.get(j)))
				{
					//Ajouter dans la liste
					DP.addToUpdate((String)allIepp.get(j));
				}
			}
			
			
			
			//On essaie de supprimer le fichier de la liste des fichiers ? modifier
			DP.delToUpdate(pathToRoot);
			
								
		}
		//Cas d'erreur
		//Le fichier n'est pas nouveau, et il n'a pas ?t? modifi?
		else
		{
			
		}
		
		
	}



	public void recCommit(CTreeNode node,String extention) throws SVNException
	{
		//On regarde si le node est un fils
		if (!node.getAllowsChildren())
		{
			if (node.getName().endsWith(extention))
			{
				beforeCommit(node);
			}
		}
		//C'est un dossier
		else
		{
			//On parcours tous les fils
			for(int i=0;i<node.getChildCount();i++)
			{
				//On applique recCommit a tous les fils du dossier
				recCommit((CTreeNode)node.getChildAt(i),extention);
				
			}
			
		}
		
		
		
		
	}
	
	protected void sendApesFile(File toCommit,CTreeNode node, String pathToRoot)
	{
		
		DependenciesParser DP = DependenciesParser.getInstance();
		//Si le fichier est tout neuf
		if (!SvnHistory.isUnderVersion(toCommit))
		{
			//Pas d'entrainement de verification de fichier pog, car fichier tout nouveau

			//Rajout dans le fichier de d?pendances le fichier apes que l'on veut envoyer
			
			DP.addApes(pathToRoot);
			
			
			
								
		}
//		Si le fichier est ancien
		else if (node.isModified())
		{

			//On vient de modifier le fichier apes
			//Il faut donc dire de changer les pog dependants
			
			
			//On recupere la liste de tous les pogs dependant de l'apes
			
			ArrayList allPog = DP.getPogFromApes(pathToRoot);
			
			
			
			//Initialisation du parcours
			int i = 0;

			

			//On parcours la liste
			for (i=0;i<allPog.size();i++)
			{
				//On indique qu'il faut mettre a jour tous les fichiers
				//Qui sont d?pendants du fichier apes que l'on veux envoyer
				//A condition qu'il ne soit pas deja a modifier
				if (!DP.isToUpdate((String)allPog.get(i)))
				{
					//Ajouter dans la liste
					DP.addToUpdate((String)allPog.get(i));
				}
			}
			
			//On vient de modifier le fichier apes
			//Il faut donc dire de changer les iepp dependants
			
			
			//On recupere la liste de tous les iepp dependant de l'apes
			
			ArrayList allIepp = DP.getIeppFromApes(pathToRoot);
			
			
			
			//Initialisation du parcours
			int j = 0;

			

			//On parcours la liste
			for (j=0;j<allIepp.size();j++)
			{
				//On indique qu'il faut mettre a jour tous les fichiers
				//Qui sont d?pendants du fichier apes que l'on veux envoyer
				//A condition qu'il ne soit pas deja a modifier
				if (!DP.isToUpdate((String)allIepp.get(j)))
				{
					//Ajouter dans la liste
					DP.addToUpdate((String)allIepp.get(j));
				}
			}
			
			
			
			
			
			
			

		}
		
			//Cas d'erreur
		else
		{
		
		}
		
	}
	
	public void commitFile(CTreeNode currentNode,String comment){
		

		SvnCommit svnC = null;
		try {
			
			svnC = new SvnCommit();
			svnC.commit(currentNode,comment);
		} catch (SVNException e1) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
			e1.printStackTrace();
		}
		
	}
	
	
	
	
	
	
}