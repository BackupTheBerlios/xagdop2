package xagdop.Controleur;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.IProjectTree;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.POGParser;
import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnHistory;




public class CCommit{
	
	
	public CCommit(CTreeNode currentNode){
		recCommit(currentNode,".apes");
		recCommit(currentNode,".pog");
	}

	
	
	//Permet d'envoyer le fichier contenu dans le Noeud
	public void beforeCommit(CTreeNode node){
		DependenciesParser DP = DependenciesParser.getInstance();
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

				//Si le fichier est tout neuf
				if (!SvnHistory.isUnderVersion(toCommit))
				{
					//Pas d'entrainement de verification de fichier pog, car fichier tout nouveau

					//Rajout dans le fichier de d?pendances le fichier apes que l'on veut envoyer
					
					DP.addApes(pathToRoot);
					
					
					
										
				}
//				Si le fichier est ancien
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
					

				}
				
					//Cas d'erreur
				else
				{
					System.out.println("Le fichier Apes n'est ni ancien ni nouveau ( c'est louche ) nom du fichier :"+nameOfFile);
				}
				
				
				
				
				
			}
			
			
			
			
			//-----------------------------------------
			//Si le fichier est un pog -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".pog"))
			{
				
				//Si le fichier est tout neuf
				if (!SvnHistory.isUnderVersion(toCommit))
				{
				
					//Modifier l'interieur du fichier pog en
					//mettant en relatif le chemin du fichier apes dependant
					
					//Ouverture du parser du fichier POG Correspondant
					POGParser POGP = new POGParser(toCommit);	
					//Recuperation du chemin absolue du fichier apes d?pendant
					String pathDependantApesFile = POGP.getPathApesFile();
					//Recuperation du path absolue de la racine des projets
					String pathGlobal = ((CTreeNode)((IProjectTree)XAGDOP.getInstance().getTree()).getModel().getRoot()).getLocalPath();
					//Recuperation du pathToRoot du fichier Apes en 
					//Enlevant les premiers caracteres correspondant au debut du chemin absolue
					String pathSemiGlobal = pathDependantApesFile.substring(pathGlobal.length()+1);
					//Ajout dans le DependenciesParser du Pog correspondant
					//addPog(Apes,Pog)
					DP.addPog(pathSemiGlobal,pathToRoot);
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
				
					//On essaie de supprimer le fichier de la liste des fichiers ? modifier
					DP.delToUpdate(pathToRoot);
					
										
				}
				//Cas d'erreur
				//Le fichier n'est pas nouveau, et il n'a pas ?t? modifi?
				else
				{
					System.out.println("Le fichier Pog n'est ni ancien ni nouveau ( c'est louche ) nom du fichier :"+nameOfFile);
				}
						
			}
			else
			{
				System.out.println("Ce n'est pas un fichier Apes, ni POG  nom du fichier:"+nameOfFile);
			}
			
		
		
	}
	
	
	public void recCommit(CTreeNode node,String extention)
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