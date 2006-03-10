package xagdop.Controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.xpath.XPathExpressionException;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.IeppNitParser;
import xagdop.Parser.POGParser;
import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnHistory;





public class CCommit{
	

	public CCommit(CTreeNode currentNode) throws Exception{

	}

	public void DependancesSendInitialize(CTreeNode currentNode)throws Exception{
		recCommit(currentNode,".apes");
		recCommit(currentNode,".pog");
		recCommit(currentNode,".epg");
		recCommit(currentNode,".iepp");
		if(!currentNode.isProject()&&SvnHistory.isUnderVersion((File)currentNode.getProject().getUserObject()))
			DependenciesParser.getInstance().publish(DependenciesParser.getInstance().getFile(currentNode.getProject().getName()));
	}
	
	
	//Permet d'envoyer le fichier contenu dans le Noeud
	public void beforeCommit(CTreeNode node) throws Exception{
	
		//Recuperation du nom de fichier a envoyer
		String nameOfFile = node.getName();
		String pathToRoot = CFile.treePathName(node);
		//R?cup?ration du fichier a envoyer
		File toCommit = new File(node.getLocalPath());
//		System.out.println(nameOfFile);
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
			//Si le fichier est un epg -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".epg"))
			{		
				sendEpgFile(toCommit,node,pathToRoot);
			}
			//-----------------------------------------
			//Si le fichier est un iepp -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".iepp")) 
			{
//				System.out.println("iepp File ");
				sendIeppFile(toCommit,node,pathToRoot);
			}
	}
	
	/*
	 * Permet de rajouter un fichier Iepp au fichier de dependances
	 * 
	 * 
	 */

	private void sendEpgFile(File toCommit, CTreeNode node, String pathToRoot) throws XPathExpressionException, NullPointerException, Exception {
		// TODO Auto-generated method stub
			DependenciesParser dp = DependenciesParser.getInstance();
			//	Si le fichier est tout neuf
			if (!SvnHistory.isUnderVersion(toCommit))
			{
				String pathToPog = pathToRoot;
				pathToPog = pathToPog.substring(0,pathToPog.length()-3);
				pathToPog += "pog";
					if (!DependenciesParser.getInstance().isPog(pathToPog))
					{
						//Si le fichier apes n'est pas present, on le rajoute en virtuel
						dp.addPog(pathToPog);
						dp.setPogOnServer(false,pathToPog);
						//Et on indique qu'on l'a rajoute en virtuel
						dp.addToCreate(pathToPog);
					}
					//On rajoute le pog dans le dependencies parser !
					dp.addEpg(pathToPog,pathToRoot);
			}
			//Si le fichier est anciens
			else if (SvnHistory.isModified(toCommit))
			{
				//Indiquer qu'il faut modifier les fichiers Iepp dependant
				//On recupere la liste de tous les iepp dependant du pog dependant de l'epg
				ArrayList allIepp = dp.getIeppFromPog(pathToRoot);
				//Initialisation du parcours
				int j = 0;
				//On parcours la liste
				for (j=0;j<allIepp.size();j++)
				{
					//On indique qu'il faut mettre a jour tous les fichiers
					//Qui sont d?pendants du fichier apes que l'on veux envoyer
					//A condition qu'il ne soit pas deja a modifier
					if (!dp.isToUpdate((String)allIepp.get(j)))
					{
						//Ajouter dans la liste
						dp.addToUpdate((String)allIepp.get(j));
					}
				}
				//On essaie de supprimer le fichier de la liste des fichiers ? modifier
				dp.delToUpdate(pathToRoot);
			}
					
			

	}

	protected void sendIeppFile(File toCommit, CTreeNode node, String pathToRoot) throws Exception {

			String adressApes;
			DependenciesParser dp = DependenciesParser.getInstance();
			//Ouverture du parser du fichier Iepp Correspondant

			
			IeppNitParser inp = new IeppNitParser(toCommit);	
			//Recuperation du chemin absolue des fichier apes d?pendants
			ArrayList pathDependantApesFile = inp.getApes();
			//Initialisation du parcours
			int i = 0;
			//On parcours la liste
			for (i=0;i<pathDependantApesFile.size();i++)
			{
				adressApes = (String) pathDependantApesFile.get(i);
				//On verifie que le APES est present sur le serveur
	//			System.out.println(adressApes);
				if (!dp.isApes(adressApes))
				{
					//Si le fichier Apes n'est pas present on le rajoute
					dp.addApes(adressApes,false);
					//Et on le met aussi dans les fichiers ?? cr??er
					dp.addToCreate(adressApes);
				}	
				//On rajoute le Iepp au fichier Apes
				dp.addIeppToApes(adressApes,pathToRoot);
			}
//			System.out.println("Path To Root"+pathToRoot);
//			Si le fichier est anciens
			if (SvnHistory.isModified(toCommit))
			{
				dp.delToUpdate(pathToRoot);
			}
			
			//On le rajoute dans les pog sans model
			ArrayList pathDependantEpgFile = inp.getEpg();
			//Initialisation du parcours

			//On parcours la liste
			for (i=0;i<pathDependantEpgFile.size();i++)
			{
				//Si le epg n'est pas present
				if (dp.isEpg((String)pathDependantEpgFile.get(i)))
				{
					//On rajoute l'epg dans le dependence
					sendEpgFile(toCommit,node,pathToRoot);
				}
				dp.addIeppToEpg(node.getProject().getName()+File.separator+pathDependantEpgFile.get(i),pathToRoot);
					
			}
			
			
		
			
	
		
	}



	protected void sendPogFile(File toCommit, CTreeNode node, String pathToRoot) throws Exception {

		DependenciesParser dp = DependenciesParser.getInstance();
		
		
	
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
				String pathToApes = pathToRoot;
				pathToApes = pathToApes.substring(0,pathToApes.length()-3);
				pathToApes += "apes";
				if (!DependenciesParser.getInstance().isApes(pathToApes))
				{
					//Si le fichier apes n'est pas present, on le rajoute en virtuel
					dp.addApes(pathToApes,false);
					//Et on indique qu'on l'a rajoute en virtuel
					dp.addToCreate(pathToApes);
				}
				//On rajoute le pog dans le dependencies parser !
				dp.addPog(pathToApes,pathToRoot);
				
			}

			else
			{
				dp.addPog(pathToRoot);
			}
			
		}
			
			
		//Si le fichier est anciens
		else if (SvnHistory.isModified(toCommit))
		{
			//Indiquer qu'il faut modifier les fichiers Iepp dependant

			//On recupere la liste de tous les iepp dependant de l'apes
			
			ArrayList allIepp = dp.getIeppFromPog(pathToRoot);
			
			
			
			//Initialisation du parcours
			int j = 0;

			

			//On parcours la liste
			for (j=0;j<allIepp.size();j++)
			{
				//On indique qu'il faut mettre a jour tous les fichiers
				//Qui sont d?pendants du fichier apes que l'on veux envoyer
				//A condition qu'il ne soit pas deja a modifier
				if (!dp.isToUpdate((String)allIepp.get(j)))
				{
					//Ajouter dans la liste
					dp.addToUpdate((String)allIepp.get(j));
				}
			}
			
			
			
			//On essaie de supprimer le fichier de la liste des fichiers ? modifier
			dp.delToUpdate(pathToRoot);
			
								
		}
				
		
	}



	public void recCommit(CTreeNode node,String extention) throws Exception
	{
		//On regarde si le node est un fils
		if (node.isLeaf())
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
	
	protected void sendApesFile(File toCommit,CTreeNode node, String pathToRoot) throws Exception
	{
		
		DependenciesParser dp = DependenciesParser.getInstance();
		//Si le fichier est tout neuf
		if (!SvnHistory.isUnderVersion(toCommit))
		{
			if (dp.isApes(pathToRoot))
			{
				//Le fichier est present sur le fichier de dependance
				//Mais il n'est pas present sur le serveur
				dp.setApesOnServer(true,pathToRoot);
				//L'enlever du ToCreate
				dp.delToCreate(pathToRoot);
			}
			else
			{
				//Pas d'entrainement de verification de fichier pog, car fichier tout nouveau
				//Rajout dans le fichier de d?pendances le fichier apes que l'on veut envoyer
		//		System.out.println("sdfkjsdklfjl");
				dp.addApes(pathToRoot);
			}
		}
//		Si le fichier est ancien
		else if (node.isModified())
		{

			//On vient de modifier le fichier apes
			//Il faut donc dire de changer les pog dependants
			//On recupere la liste de tous les pogs dependant de l'apes
			ArrayList allPog = dp.getPogFromApes(pathToRoot);
			
			//Initialisation du parcours
			int i = 0;


			//On parcours la liste
			for (i=0;i<allPog.size();i++)
			{
				//On indique qu'il faut mettre a jour tous les fichiers
				//Qui sont d?pendants du fichier apes que l'on veux envoyer
				//A condition qu'il ne soit pas deja a modifier

				//if (!DP.isToUpdate((String)allPog.get(i)))
				//{

					//Ajouter dans la liste
					//dp.addToUpdate((String)allPog.get(i));

					//System.out.println((String)allPog.get(i));
			//	}


				//Ajouter dans la liste
					dp.addToUpdate((String)allPog.get(i));

			}
			//On vient de modifier le fichier apes
			//Il faut donc dire de changer les iepp dependants
			//On recupere la liste de tous les iepp dependant de l'apes
			ArrayList allIepp = dp.getIeppFromApes(pathToRoot);
			//Initialisation du parcours
			int j = 0;
		//On parcours la liste
			for (j=0;j<allIepp.size();j++)
			{
				//On indique qu'il faut mettre a jour tous les fichiers
				//Qui sont d?pendants du fichier apes que l'on veux envoyer
				//A condition qu'il ne soit pas deja a modifier
				if (!dp.isToUpdate((String)allIepp.get(j)))
				{
					//Ajouter dans la liste
					dp.addToUpdate((String)allIepp.get(j));
				}
			}			

		}
	}
	
	public void commitFile(CTreeNode currentNode,String comment) throws Exception{
	//	ThreadWait tWait = new ThreadWait(XAGDOP.getInstance());
	//	tWait.start();
	//	ThreadCommit commit = new ThreadCommit(currentNode,comment,tWait);
	//	commit.start();
		
		SvnCommit svnC = new SvnCommit();
			svnC.commit(currentNode,comment);
	}
	
	
	public void DependencesRemoveInitialize(CTreeNode currentNode) throws Exception{
		recRemove(currentNode,".iepp");
		recRemove(currentNode,".pog");
		recRemove(currentNode,".apes");
		if(!currentNode.isProject()&&SvnHistory.isUnderVersion((File)currentNode.getProject().getUserObject()))
			DependenciesParser.getInstance().publish(DependenciesParser.getInstance().getFile(currentNode.getProject().getName()));
	}
		
	public void recRemove(CTreeNode node,String extention) throws Exception
	{
		//On regarde si le node est un fils
		if (node.isLeaf())
		{
			if (node.getName().endsWith(extention))
			{
				beforeRemove(node);
			}
		}
		//C'est un dossier
		else
		{
			//On parcours tous les fils
			for(int i=0;i<node.getChildCount();i++)
			{
				//On applique recCommit a tous les fils du dossier
				recRemove((CTreeNode)node.getChildAt(i),extention);
			}
			
		}
		
		
		
		
	}
	//	Permet d'envoyer le fichier contenu dans le Noeud
	public void beforeRemove(CTreeNode node) throws Exception{
	
		//Recuperation du nom de fichier a envoyer
		String nameOfFile = node.getName();
		String pathToRoot = CFile.treePathName(node);
		//R?cup?ration du fichier a envoyer
		//File toCommit = new File(node.getLocalPath());
		
			//-----------------------------------------
			//Si le fichier est un apes -->
			//-----------------------------------------
			if (nameOfFile.endsWith(".apes"))
			{		
				removeApesFile(pathToRoot);	
			}
			//-----------------------------------------
			//Si le fichier est un pog -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".pog"))
			{		
				removePogFile(pathToRoot);
			}
			//-----------------------------------------
			//Si le fichier est un iepp -->
			//-----------------------------------------
			else if (nameOfFile.endsWith(".iepp")) 
			{
				removeIeppFile(pathToRoot);
			}
	}
	
	/*
	 * Remove Apes file
	 */
	protected void removeApesFile(String pathToRoot) throws Exception
	{
			DependenciesParser dp = DependenciesParser.getInstance() ;
			//On verifie que le apes ne possede pas de fils ( pog ou iepp )
			ArrayList allPog = dp.getPogFromApes(pathToRoot);
			ArrayList allIepp = dp.getIeppFromApes(pathToRoot);
			int taille = allPog.size()+allIepp.size();
			if (taille>0)
			{
				dp.setApesOnServer(false,pathToRoot);
				dp.addToCreate(pathToRoot);
			}
			else
			{
				dp.delApes(pathToRoot);
				dp.delToCreate(pathToRoot);
				dp.delToUpdate(pathToRoot);
				
			}		
	}
	
	
	/*
	 * Remove Iepp File
 	*/
	protected void removeIeppFile(String pathToRoot) throws IOException, XPathExpressionException, NullPointerException, Exception
	{	
		DependenciesParser dp = DependenciesParser.getInstance();
		ArrayList apesDependant = dp.getApesFromIepp(pathToRoot);
		for (int i=0;i<apesDependant.size();i++)
		{
			ArrayList allPog = dp.getPogFromApes((String) apesDependant.get(i));
			ArrayList allIepp = dp.getIeppFromApes((String) apesDependant.get(i));
			int taille = allPog.size()+allIepp.size();
			//Si il n'y a pas d'autre fils, et que le fichier Apes n'est pas sur le serveur
			if ((taille==1)&&(!dp.getApesOnServer((String)apesDependant.get(i))))
			{
				dp.delApes((String) apesDependant.get(i));
				dp.delToCreate((String) apesDependant.get(i));
				dp.delToUpdate((String) apesDependant.get(i));
				dp.delToCreate(pathToRoot);
				dp.delToUpdate(pathToRoot);
				
			}
		
			//Si il y a des fils, on ne supprime que le pog
			else  
			{
				dp.delIepp(pathToRoot,(String)apesDependant.get(i));
				dp.delToUpdate(pathToRoot);
			}
		}
	}
	/*
	 * Remove Pog File
	 */
	protected void removePogFile(String pathToRoot) throws IOException, XPathExpressionException, NullPointerException,Exception
	{
		
		DependenciesParser dp = DependenciesParser.getInstance();

		String apesDependant = dp.getApesFromPog(pathToRoot);

		
		

		ArrayList allPog = dp.getPogFromApes(apesDependant);
		ArrayList allIepp = dp.getIeppFromApes(apesDependant);
		int taille = allPog.size()+allIepp.size();
		//Si il n'y a pas d'autre fils, et que le fichier Apes n'est pas sur le serveur
		if ((taille==1)&&(!dp.getApesOnServer(apesDependant)))
		{
			dp.delApes(apesDependant);
			dp.delToCreate(apesDependant);
			dp.delToUpdate(apesDependant);
			dp.delToUpdate(pathToRoot);
			dp.delToCreate(pathToRoot);
		}
		
		//Si il y a des fils, on ne supprime que le pog
		else  
		{
			//System.out.println(pathToRoot);
			dp.delPog(pathToRoot);
			dp.delToUpdate(pathToRoot);
		}
	}
}