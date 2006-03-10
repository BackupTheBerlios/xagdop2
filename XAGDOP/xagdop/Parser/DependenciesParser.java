package xagdop.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xagdop.Controleur.CDependencies;
import xagdop.Util.ErrorManager;


/**
 * @author Claire
 * Classe qui gere tous les acces au fichier xml dependencies.xml
 *
 */
public class DependenciesParser extends Parser{

	/**
	 * Un fichier dependencies.xml par projet
	 * Donc une Hashmap, avec en clef le nom du projet, et associe un File
	 * qui pointe sur le fichier dependencies.xml associe au rpojet
	 */
	private HashMap dependencies;
	
	/**
	 * Attribut qui permet de connaitre le projet courant
	 */
	private String currentProject;
	
	
	/**
	 * Attribut static pour l'implementation du pattern singleton
	 */
	private static DependenciesParser dependenciesInstance = null; 
	
	/**
	 * fonction static qui permet de recuperer l'unique instance de la classe
	 */
	public static DependenciesParser getInstance() throws IOException
	{
		if(dependenciesInstance==null)
		{
			dependenciesInstance = new DependenciesParser();
		}
		return dependenciesInstance;
	}
	
	/**
	 * Constructeur
	 * @throws IOException
	 */
	private DependenciesParser() throws IOException
	{
		
		/**
		 * Recuperation de la liste des fichiers dependencies.xml existants
		 * Et affectation de l'attribut dependencies
		 */
		CDependencies cdep = new CDependencies();
		dependencies = cdep.getDependenciesFiles();
			
		/*** Pour le debuggage*		
			dependencies = new HashMap();
			dependencies.put("Test",new File("xagdop/ressources/XML/dependencies.xml"));
		/**/	
	}

	/**
	 * Getter retournant le File dependencies.xml du projet passe en parametre
	 * @param project
	 * @return
	 * @throws NullPointerException
	 */
	public File getFile(String project) throws NullPointerException
	{
		if((File)dependencies.get(project)!=null)
		{
			return (File)dependencies.get(project);
		}
		else
		{
			ErrorManager.getInstance().setErrMsg("Projet "+ project + " inexistant \n");
			ErrorManager.getInstance().setErrTitle("Projet inexistant");
			throw new NullPointerException();
		}	
		
	}
	

	/**
	 * Setter permettant de charger en memoire le fichier dependencies.xml
	 * associe au nouveau projet courant
	 * @param project
	 * @throws NullPointerException
	 * @throws Exception
	 */
	public void setFile(String project) throws NullPointerException, Exception
	{
		/**
		 * Changement du projet courant
		 */
		currentProject = project;
		
		/**
		 * Recuperation du File associe au projet passe en parametre
		 */
		if((File)dependencies.get(project)!=null)
		{
			/**
			 * Chargement du fichier dependencies.xml
			 */
			loadTreeInMemory((File)dependencies.get(project));			
		}
		else
		{
			ErrorManager.getInstance().setErrMsg("Projet "+ project + " inexistant \n");
			ErrorManager.getInstance().setErrTitle("Projet inexistant");
			throw new NullPointerException();
		}		
	}
	

	/**
	 * Fonction recherchant tous les fichiers .pog associes au fichier .apes passe en parametre 
	 * @param apesName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public ArrayList getPogFromApes(String apesName) throws XPathExpressionException, NullPointerException
	{
		/**
		 * Liste resultat, retournee vide si aucun fichier .pog
		 */
		ArrayList pogList = new ArrayList();
		
		/**
		 * Creation de l'expression XPath recherchant la balise apes
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList pogNodeList;
			
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si le noeud apes recherche existe bien
		 */
		if(apesNode != null){
			
			/**
			 * Dans le cas ou la balise apes possede des balises filles
			 * donc des .pog et/ou des .iepp
			 */
			if(apesNode.hasChildNodes()){
				
				Node nodeAll = null;
				Node attributePath = null;
				NamedNodeMap mapAttributes = null;
				
				/**
				 * Recuperation de toutes les balises filles
				 * Et parcours de toutes ces balises
				 */
				pogNodeList = apesNode.getChildNodes();
				for (int i=0; i<pogNodeList.getLength(); i++)
				{
					/**
					 * Pour chaque noeud recuperation de tous ces attributs
					 */
					nodeAll = pogNodeList.item(i);
					mapAttributes = nodeAll.getAttributes();
					
					/**
					 * Si le noeud possede bien au moins un attribut
					 */
					if(mapAttributes != null){					
						/**
						 * Recuperation de la valeur de l'attribut fileNamePog
						 */
						attributePath = mapAttributes.getNamedItem("fileNamePog");
						
						/**
						 * Si la valeur de l'attribut n'est pas null
						 */
						if(attributePath!=null){
							/**
							 * Ajout du chemin du fichier .pog dans la liste resultat
							 */
							pogList.add(attributePath.getNodeValue());
						}
					}					
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier Apes Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier Apes "+ apesName +" est inconnu\n");
			throw new NullPointerException();
		}			
		
		return pogList;			
	}

	/**
	 * Fonction recherchant tous les fichiers .epg associes au fichier .pog passe en parametre
	 * @param pogName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public ArrayList getEpgFromPog(String pogName) throws XPathExpressionException, NullPointerException
	{
		/**
		 * Liste resultat, retournee vide si aucun fichier .epg
		 */
		ArrayList epgList = new ArrayList();
		
		/**
		 * Creation de l'expression XPath recherchant la balise pog
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//pog[@fileNamePog=\""+pogName+"\"]";

		Element pogNode = null;
		NodeList epgNodeList;
			
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			pogNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si le noeud pog recherche existe bien
		 */
		if(pogNode != null){
			
			/**
			 * Dans le cas ou la balise pog possede des balises filles
			 * donc des .epg
			 */
			if(pogNode.hasChildNodes()){
				
				Node nodeAll = null;
				Node attributePath = null;
				NamedNodeMap mapAttributes = null;
				
				/**
				 * Recuperation de toutes les balises filles
				 * Et parcours de toutes ces balises
				 */
				epgNodeList = pogNode.getChildNodes();
				for (int i=0; i<epgNodeList.getLength(); i++)
				{
					/**
					 * Pour chaque noeud recuperation de tous ces attributs
					 */
					nodeAll = epgNodeList.item(i);
					mapAttributes = nodeAll.getAttributes();
					
					/**
					 * Si le noeud possede bien au moins un attribut
					 */
					if(mapAttributes != null){					
						/**
						 * Recuperation de la valeur de l'attribut fileNameEpg
						 */
						attributePath = mapAttributes.getNamedItem("fileNameEpg");
						
						/**
						 * Si la valeur de l'attribut n'est pas null
						 */
						if(attributePath!=null){
							/**
							 * Ajout du chemin du fichier .epg dans la liste resultat
							 */
							epgList.add(attributePath.getNodeValue());
						}
					}					
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier Pog Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier Pog "+ pogName +" est inconnu\n");
			throw new NullPointerException();
		}			
		
		return epgList;			
	}
	

	/**
	 * Fonction recherchant tous les fichiers .epg associes au fichier .apes passe en parametre 
	 * @param apesName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public ArrayList getEpgFromApes(String apesName) throws XPathExpressionException, NullPointerException
	{
		/**
		 * Liste resultat, retournee vide si aucun fichier .epg
		 */
		ArrayList epgList = new ArrayList();
		
		/**
		 * Creation de l'expression XPath recherchant la balise pog
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList pogNodeList;
			
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si le noeud apes recherche existe bien
		 */
		if(apesNode != null){
			
			/**
			 * Dans le cas ou la balise apes possede des balises filles
			 * donc des .pog et / ou des .iepp 
			 */
			if(apesNode.hasChildNodes()){
				
				Node nodeChild = null;
				
				
				/**
				 * Recuperation de toutes les balises filles
				 * Et parcours de toutes ces balises
				 */
				pogNodeList = apesNode.getChildNodes();
				for (int i=0; i<pogNodeList.getLength(); i++)
				{
					nodeChild = pogNodeList.item(i);
					/**
					 * Pour chaque noeud test si c'est une balise pog
					 */
					if(nodeChild.getNodeName().equals("pog"))
					{				
						/**
						 * Test si la balise pog a une balise fille
						 * Donc une balise epg
						 */
						if(nodeChild.hasChildNodes())
						{
							Node nodeEpg = null;
							Node attributePath = null;
							NamedNodeMap mapAttributes = null;
							
							NodeList epgNodeList = nodeChild.getChildNodes();
							for(int j=0; j<epgNodeList.getLength(); j++)
							{								
								/**
								 * Pour chaque noeud recuperation de tous ces attributs
								 */
								nodeEpg = epgNodeList.item(j);
								mapAttributes = nodeEpg.getAttributes();
								
								/**
								 * Si le noeud possede bien au moins un attribut
								 */
								if(mapAttributes != null){					
									/**
									 * Recuperation de la valeur de l'attribut fileNameEpg
									 */
									attributePath = mapAttributes.getNamedItem("fileNameEpg");
									
									/**
									 * Si la valeur de l'attribut n'est pas null
									 */
									if(attributePath!=null){
										/**
										 * Ajout du chemin du fichier .epg dans la liste resultat
										 */
										epgList.add(attributePath.getNodeValue());
									}
								}	
							}
						}
					}
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier Pog Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier Pog "+ apesName +" est inconnu\n");
			throw new NullPointerException();
		}			
		
		return epgList;			
	}
	

	/**
	 * Fonction recherchant tous les fichiers .apes associes au fichier .iepp passe en parametre
	 * @param ieppName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public ArrayList getApesFromIepp(String ieppName) throws XPathExpressionException, NullPointerException
	{
		/**
		 * Liste resultat, vide si aucun fichier .apes trouve
		 */
		ArrayList apesList = new ArrayList();
		
		/**
		 * Creation de l'expression XPath recherchant tous les noeuds apes ayant un noeud fils
		 * iepp avec l'attribut fileNameIepp egal au parametre
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[iepp[@fileNameIepp=\""+ieppName+"\"]]";

		NodeList apesNodeList = null;
			
		try {
			/**
			 * Evaluation de l'expression XPath  
			 */
			apesNodeList = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Fonction recherchant tous les fichiers .apes associes au fichier .iepp passe en parametre
		 * Si le resultat est null c'est que le fichier iepp est inconnu
		 */
		if(apesNodeList!=null){
			Element apes = null;
			
			/**
			 * Recuperation et parcours de la liste des attributs de chaque noeud apes trouve
			 */
			NamedNodeMap mapAttributes;
			for (int i=0; i<apesNodeList.getLength(); i++)
			{
				apes = (Element)apesNodeList.item(i);
				mapAttributes = apes.getAttributes();
				/**
				 * Ajout dans la liste resultat de la valeur de l'attribut fileNameApes 
				 * Si la liste des attributs n'est pas null
				 */
				if(mapAttributes != null){					
					apesList.add(mapAttributes.getNamedItem("fileNameApes").getNodeValue());
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier IEPP Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier IEPP "+ ieppName +" est inconnu\n");
			throw new NullPointerException();
		}			

		return apesList;			
	}


	/**
	 * Fonction recherchant le fichier .apes associe au fichier .pog passe en parametre
	 * @param pogName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public String getApesFromPog(String pogName) throws XPathExpressionException, NullPointerException
	{
	
		/**
		 * Creation de l'expression XPath recherchant tous les noeuds apes ayant un noeud fils
		 * pog avec l'attribut fileNamePog egal au parametre
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[pog[@fileNamePog=\""+pogName+"\"]]";

		Node apesNode = null;
			
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			apesNode = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		
		/**
		 * Si aucun noeud apes n'est trouve, c'est que le fichier pog passe en parametre est inconnu
		 */
		if(apesNode!=null){
			NamedNodeMap mapAttributes;
			mapAttributes = apesNode.getAttributes();
			
			/**
			 * Recuperation de la liste des attributs, et si la liste est non null
			 * Retour de la valeur de l'attribut fileNameApes
			 */
			if(mapAttributes != null){					
				return (mapAttributes.getNamedItem("fileNameApes").getNodeValue());
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier POG Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier POG "+ pogName +" est inconnu\n");
			throw new NullPointerException();
		}			

		return null;
	}
	

	/**
	 * Fonction recherchant le fichier .apes associe au fichier .epg passe en parametre
	 * @param epgName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public String getApesFromEpg(String epgName) throws XPathExpressionException, NullPointerException
	{
	
		/**
		 * Creation de l'expression XPath recherchant tous les noeuds apes ayant un noeud petit fils
		 * epg avec l'attribut fileNameEpg egal au parametre
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[pog[epg[@fileNameEpg=\""+epgName+"\"]]]";

		Node apesNode = null;
			
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			apesNode = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		
		/**
		 * Si aucun noeud apes n'est trouve, c'est que le fichier epg passe en parametre est inconnu
		 */
		if(apesNode!=null){
			NamedNodeMap mapAttributes;
			mapAttributes = apesNode.getAttributes();
			
			/**
			 * Recuperation de la liste des attributs, et si la liste est non null
			 * Retour de la valeur de l'attribut fileNameApes
			 */
			if(mapAttributes != null){					
				return (mapAttributes.getNamedItem("fileNameApes").getNodeValue());
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier EPG Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier EPG "+ epgName +" est inconnu\n");
			throw new NullPointerException();
		}			

		return null;
	}

	/**
	 * Fonction recherchant tous les fichiers .iepp associes au fichier .apes passe en parametre
	 * @param apesName
	 * @return
	 * @throws XPathExpressionException
	 * @throws NullPointerException
	 */
	public ArrayList getIeppFromApes(String apesName) throws XPathExpressionException, NullPointerException
	{
		/**
		 * Liste resultat, retournee vide si aucune balise iepp trouvee
		 */
		ArrayList ieppList = new ArrayList();
		
		/**
		 * XPath expression to get the correct "apes" node  
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList allNodeList;
	
		try {
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * If there is children node of the "apes" node   
		 */
		if(apesNode!=null){
			if(apesNode.hasChildNodes()){				
				Node nodeChild = null;			
				allNodeList = apesNode.getChildNodes();				
				/**
				 * For all children node
				 */					
				for (int i=0; i<allNodeList.getLength(); i++)
				{
					nodeChild = allNodeList.item(i);					
					/**
					 * If it is a "iepp" nodes   
					 */
					if(nodeChild!=null){
						if(nodeChild.getNodeName().equals("iepp")){					
							NamedNodeMap mapAttributes = null;
							/**
							 * Getting all node's attribute in a NamedNodeMap
							 * Et ajoute la valeur de l'attribut fileNameIepp si la map est non null  
							 */
							mapAttributes = nodeChild.getAttributes();
							if(mapAttributes != null){					
								String fileName = mapAttributes.getNamedItem("fileNameIepp").getNodeValue();	
								ieppList.add(fileName);
							}
						}	
					}
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier Apes Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier Apes "+ apesName +" est inconnu\n");
			throw new NullPointerException();
		}			
		
		
		return ieppList;			
	}
	

	/**
	 * Fonction recherchant tous les fichiers .iepp associes au fichier .pog passe en parametre
	 * @param pogName
	 * @return
	 * @throws XPathExpressionException
	 */
	public ArrayList getIeppFromPog(String pogName) throws XPathExpressionException
	{
		ArrayList ieppList = new ArrayList();
		
		/**
		 * XPath expression to get the correct "pog" node  
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";
		
		Element pogNode = null;
		NodeList allNodeList;
	
		try {
			pogNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * If there is children node of the "pog" node   
		 */
		if(pogNode!=null){
			if(pogNode.hasChildNodes()){				
				Node nodeChild = null;			
				allNodeList = pogNode.getChildNodes();				
				/**
				 * For all children node
				 */					
				for (int i=0; i<allNodeList.getLength(); i++)
				{
					nodeChild = allNodeList.item(i);					
					/**
					 * If it is a "iepp" nodes   
					 */
					if(nodeChild!=null){
						if(nodeChild.getNodeName().equals("iepp")){					
							NamedNodeMap mapAttributes = null;
							/**
							 * Getting all node's attribute in a NamedNodeMap  
							 */
							mapAttributes = nodeChild.getAttributes();
							if(mapAttributes != null){					
								String fileName = mapAttributes.getNamedItem("fileNameIepp").getNodeValue();	
								ieppList.add(fileName);
							}
						}	
					}
				}
			}
		}
			
		
		return ieppList;			
	}
	

	/**
	 * Fonction qui renvoie TRUE si le fichier passe en parametre est a mettre a jour
	 * @param filePath
	 * @return
	 * @throws XPathExpressionException
	 */
	public boolean isToUpdate(String filePath) throws XPathExpressionException
	{
		/**
		 * Creation de l'expression XPath
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toUpdate/file[@path=\""+filePath+"\"]";
		Element elem = null;
		 
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		
		/**
		 * Si le fichier est trouve dans la liste des noeuds fils de toUpdate, on renvoie TRUE
		 */
		if ( elem != null ) {			
				return true;		
		}
		/**
		 * Si le fichier n'est pas trouve dans la liste des noeuds fils de toUpdate, on renvoie FALSE
		 */
		else {
			return false;
		}
	}

	/**
	 * Fonction qui retourne TRUE si le fichier passe en parametre existe, FALSE sinon
	 * @param apesName
	 * @return
	 * @throws XPathExpressionException
	 */
	public boolean isApes(String apesName) throws XPathExpressionException
	{
		/**
		 * Creation de l'expression XPath
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element apes = null;
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			apes = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si une balise est trouvee on renvoie TRUE
		 */
		if ( apes != null ) {			
				return true;		
		}
		/**
		 * Sinon on renvoie FALSE
		 */
		else {
			return false;
		}
	}
	
	/**
	 * Fonction qui retourne TRUE si le fichier passe en parametre existe, FALSE sinon
	 * @param apesName
	 * @return
	 * @throws XPathExpressionException
	 */
	public boolean isPog(String pogName) throws XPathExpressionException
	{
		/**
		 * Creation de l'expression XPath
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//pog[@fileNamePog=\""+pogName+"\"]";
		Element pog = null;
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			pog = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si une balise est trouvee on renvoie TRUE
		 */
		if ( pog != null ) {			
				return true;		
		}
		/**
		 * Sinon on renvoie FALSE
		 */
		else {
			return false;
		}
	}
	/**
	 * Fonction qui retourne TRUE si le fichier passe en parametre existe, FALSE sinon
	 * @param apesName
	 * @return
	 * @throws XPathExpressionException
	 */
	public boolean isEpg(String epgName) throws XPathExpressionException
	{
		/**
		 * Creation de l'expression XPath
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//epg[@fileNameEpg=\""+epgName+"\"]";
		Element epg = null;
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			epg = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si une balise est trouvee on renvoie TRUE
		 */
		if ( epg != null ) {			
				return true;		
		}
		/**
		 * Sinon on renvoie FALSE
		 */
		else {
			return false;
		}
	}
	/**
	 * Fonction permettant d'ajouter une balise apes
	 * @param apesName
	 * @throws Exception
	 */
	public void addApes(String apesName) throws Exception
	{
		
		/**
		 * Creation de l'expression XPath
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element test_elem = null;		
		try {
			/**
			 * Evaluation de l'expression XPath
			 */
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		
		
		/**
		 * Si la balise apes n'existe pas deja on l'ajoute
		 */
		if(test_elem == null)
		{
			
			/**
			 * Expression XPath permettant de se positionner au niveau du noeud 
			 * parent du futur noeud apes
			 */
			String expression = "//dependencies";

			Element elem = null;
			/**
			 * Creation d'un nouvel element de nom apes
			 */
			Element newElem = doc.createElement("apes");
			newElem.setAttribute("fileNameApes", apesName);	
			try {
					elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}

			/**
			 * Si la balise parente est bien trouvee
			 */
			if ( elem != null ) {
				/**
				 * Ajout du nouvel element comme fils de la balise parente
				 */
				elem.appendChild(newElem);
				
				/**
				 * Recuperation du nouveau noeud pour lui ajouter le noeud fils onServer
				 */
				expression = "//apes[@fileNameApes='"+ apesName +"']";
				newElem = null;
				try {
					newElem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
				}
				catch (XPathExpressionException e) {
					ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
					ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
					throw new XPathExpressionException(expr_test);
				}
				if(newElem!=null)
				{
					Element elemOnServer = doc.createElement("onServer");	
					newElem.appendChild(elemOnServer);
				}
				
				saveDocument((File)dependencies.get(currentProject));	
			}
			
		}
	}	

	/**
	 * Fonction permettant d'ajouter un element apes, et si onServer = true, ajout egalement
	 * de l'element fils onServer au nouveau noeud apes
	 * @param apesName
	 * @param onServer
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public void addApes(String apesName, boolean onServer) throws Exception, NullPointerException
	{		
		/**
		 * Test si le noeud apes existe deja ou non
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		/**
		 * Si il n'existe pas
		 */
		if(test_elem == null)
		{
			/**
			 * Recuperation du noeud parent auquel ajouter le nouvel element
			 */
			String expression = "//dependencies";
			Node racine = null;
			try {
					racine = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			/**
			 * Si le noeud parent existe bien
			 */
			if ( racine != null ) {

				/**
				 * Creation du noeud a ajouter
				 */
				Element newApes = doc.createElement("apes");
				newApes.setAttribute("fileNameApes", apesName);	
				/**
				 * Ajout du nouvel element au noeud parent
				 */
				racine.appendChild(newApes);
				
				/**
				 * Si le parametre onServer = true
				 */
				if(onServer){
					/**
					 * Recuperation du noeud apes qui vient d'etre cree
					 */
					expression = "//apes[@fileNameApes='"+ apesName +"']";
					racine = null;
					try {
						racine = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
						ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
						throw new XPathExpressionException(expr_test);
					}
					if(racine!=null)
					{
						/**
						 * Creation du noeud onServer
						 */
						Element elemOnServer = doc.createElement("onServer");
						/**
						 * Ajout du noeud onServer au noeud apes
						 */
						racine.appendChild(elemOnServer);
					}
				}
				/**
				 * Sauvegarde du document
				 */
				saveDocument((File)dependencies.get(currentProject));	

			}
			else
			{
				ErrorManager.getInstance().setErrTitle("Pb Racine du fichier");
				ErrorManager.getInstance().setErrMsg("Fichier XML invalide, contacter rapidement l'administrateur.\n");
				throw new NullPointerException();
			}
			
		}
	}	

	/**
	 * Ajoute ou supprime la balise onServer d'un noeud apes
	 * @param server
	 * @param apesName
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public void setApesOnServer(boolean server, String apesName) throws Exception,NullPointerException
	{
		/**
		 * Verification si le noeud apes existe
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element apes = null;		
		try {
			apes = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si le noeud existe
		 */
		if(apes!=null)
		{
			/**
			 * Verification si le noeud onServer existe deja
			 */
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//apes[@fileNameApes=\""+apesName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			/**
			 * si le parametre server = true
			 */
			if(server)
			{	/**
				 * Et que le noeud onServer n'existe pas
				 */
				if(onServer == null)
				{
					/**
					 * Creation de l'element onServer
					 */
					Element elemOnServer = doc.createElement("onServer");
					/**
					 * Ajout de l'element onServer au noeud apes
					 */
					apes.appendChild(elemOnServer);
				}
			}
			else
			{				
				/**
				 * Sinon si le parametre server = false
				 * Et que l'element onServer existe
				 */
				if(onServer != null)
				{
					/**
					 * Suppression de l'element onServer
					 */
					apes.removeChild(onServer);
				}
			}
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	

		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Apes inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Apes : "+ apesName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}
	
	
	/**
	 * @param server
	 * @param pogName
	 * @throws Exception
	 * @throws NullPointerException
	 * Fonction ajoutant la balise onServer si server = true
	 */

	public void setPogOnServer(boolean server, String pogName) throws Exception,NullPointerException
	{
		/**
		 * Verification que la balise pog existe
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//pog[@fileNamePog=\""+pogName+"\"]";
		Element pog = null;		
		try {
			pog = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		
		/**
		 * Si la balise existe
		 */
		if(pog!=null)
		{
			/**
			 * Verification si la balise onServer est deja presente ou non
			 */
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//pog[@fileNamePog=\""+pogName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			/**
			 * Si le parametre server = true
			 */
			if(server)
			{
				/**
				 * Et que la balise onServer n'est pas presente
				 */
				if(onServer == null)
				{
					/**
					 * Ajout de la balise onServer a la balise pog
					 */
					Element elemOnServer = doc.createElement("onServer");	
					pog.appendChild(elemOnServer);
				}
			}
			else
			{				
				/**
				 * Sinon, si la balise onServer est presente 
				 * Et que server = false
				 */
				if(onServer != null)
				{
					/**
					 * Suppression de la balise onServer
					 */
					pog.removeChild(onServer);
				}
			}
			/**
			 * Sauvegarde des modifications
			 */
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Pog inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Pog : "+ pogName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}

	/**
	 * Fonction retournant true si la balise onServer est presente, sinon false
	 * @param apesName
	 * @return
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public boolean getApesOnServer(String apesName) throws Exception,NullPointerException
	{
		/**
		 * Verification que la balise apes existe
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element apes = null;		
		try {
			apes = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si la balise apes existe
		 */
		if(apes!=null)
		{
			/**
			 * Test si la balise onServer est presente ou non
			 */
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//apes[@fileNameApes=\""+apesName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if(onServer != null)
			{
				/**
				 * La balise onServer est presente
				 * On retourne true
				 */
				return true;
			}
			else
			{	
				/**
				 * Sinon on retourne false
				 */
				return false;
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Apes inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Apes : "+ apesName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}

	/**
	 * Fonction retournant true si la balise onServer est presente, sinon false
	 * @param pogName
	 * @return
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public boolean getPogOnServer(String pogName) throws Exception,NullPointerException
	{
		/**
		 * Verification que la balise pog existe
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//pog[@fileNamePog=\""+pogName+"\"]";
		Element pog = null;		
		try {
			pog = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si la balise pog existe
		 */
		if(pog!=null)
		{
			/**
			 * Test si la balise onServer est presente ou non
			 */
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//pog[@fileNamePog=\""+pogName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if(onServer != null)
			{
				/**
				 * La balise onServer est presente
				 * On retourne true
				 */
				return true;
			}
			else
			{				
				/**
				 * Sinon on retourne false
				 */
				return false;
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Pog inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Pog : "+ pogName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}
	/**
	 * Fonction retournant true si la balise onServer est presente, sinon false
	 * @param pogName
	 * @return
	 * @throws Exception
	 * @throws NullPointerException
	 */
	public boolean getEpgOnServer(String epgName) throws Exception,NullPointerException
	{
		/**
		 * Verification que la balise pog existe
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//epg[@fileNameEpg=\""+epgName+"\"]";
		Element epg = null;		
		try {
			epg = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si la balise pog existe
		 */
		if(epg!=null)
		{
			/**
			 * Test si la balise onServer est presente ou non
			 */
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//epg[@fileNamePog=\""+epgName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if(onServer != null)
			{
				/**
				 * La balise onServer est presente
				 * On retourne true
				 */
				return true;
			}
			else
			{				
				/**
				 * Sinon on retourne false
				 */
				return false;
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Epg inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Epg : "+ epgName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}
	/**
	 * Focntion permettant de creer un noeud pog qui ne depend pas d'un noeud apes
	 * Pour les pog sans modele
	 * @param pogName
	 * @throws Exception
	 */
	public void addPog(String pogName) throws Exception
	{
		/**
		 * Verification que le noeud n'existe pas deja
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		
		/**
		 * Si il n'existe pas
		 */
		if(test_elem == null)
		{
			/**
			 * Recuperation du noeud parent
			 */
			String expression = "//dependencies";

			Element elem = null;
			/**
			 * Creation du nouvel element
			 */
			Element newElem = doc.createElement("pog");
			newElem.setAttribute("fileNamePog", pogName);	
			try {
					elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			/**
			 * Si le noeud parent est bien trouve
			 */
			if ( elem != null ) {
				/**
				 * Ajout du nouvel element
				 */
				elem.appendChild(newElem);
				/**
				 * Sauvegarde du document
				 */
				saveDocument((File)dependencies.get(currentProject));
			}
			
		}
	}	
	
	/**
	 * Fonction permettant d'ajouter un noeud au bloc toUpdate 
	 * @param filePath
	 * @throws Exception
	 */
	public void addToUpdate(String filePath) throws Exception
	{
		/**
		 * Verification que le noeud a ajoute n'est pas deja present
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//file[@path=\""+filePath+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		/**
		 * Si il n'existe pas
		 */
		if(test_elem == null)
		{
			/**
			 * Recuperation du noeud parent
			 */
			String expression = "//toUpdate";
		
			Element elem = null;
			/**
			 * Creation du nouvel element
			 */
			Element newElem = doc.createElement("file");
			newElem.setAttribute("path", filePath);
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			/**
			 * Si le noeud parent est bien trouve
			 */
			if ( elem != null ) {
				/**
				 * Ajout du nouvel element
				 */
				elem.appendChild(newElem);
				/**
				 * Sauvegarde du document
				 */
				saveDocument((File)dependencies.get(currentProject));	
				
			}
		}
		
	}		
	
	/**
	 * Fonction permettant d'ajouter un noeud au bloc toCreate
	 * @param filePath
	 * @throws Exception
	 */
	public void addToCreate(String filePath) throws Exception
	{
		/**
		 * Verification que le noeud a ajoute n'est pas deja present
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//file[@path=\""+filePath+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		/**
		 * Si il n'existe pas
		 */
		if(test_elem == null)
		{
			/**
			 * Recuperation du noeud parent
			 */
			String expression = "//toCreate";

			Element elem = null;
			/**
			 * Creation du nouvel element
			 */
			Element newElem = doc.createElement("file");
			newElem.setAttribute("path", filePath);
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			/**
			 * Si le noeud parent est bien trouve
			 */
			if ( elem != null ) {
				/**
				 * Ajout du nouvel element
				 */
				elem.appendChild(newElem);
				/**
				 * Sauvegarde du document
				 */
				saveDocument((File)dependencies.get(currentProject));	
			}
		}
		
	}		

	
	/**
	 * Fonction permettant d'ajouter un noeud pog a un noeud apes donne
	 * @param apesName
	 * @param pogName
	 * @throws Exception
	 */
	public void addPog(String apesName, String pogName) throws Exception
	{
		/**
		 * Test si la balise apes existe
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]/pog[@fileNamePog=\""+pogName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		/**
		 * Si il n'existe pas
		 */
		if(test_elem == null)
		{
			/**
			 * Recuperation du noeud parent
			 */
			String expression = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]";
			
			Element elem = null;
			/**
			 * Creation du nouvel element
			 */
			Element newElem = doc.createElement("pog");
			newElem.setAttribute("fileNamePog", pogName);	
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			/**
			 * Si le noeud parent est bien trouve
			 */
			if ( elem != null ) {
				/**
				 * Ajout du nouvel element
				 */
				elem.appendChild(newElem);
				/**
				 * Sauvegarde du document
				 */
				saveDocument((File)dependencies.get(currentProject));	
			}
			
		}

	}		
	
	/**
	 * Fonction permettant d'ajouter un noeud epg a un noeud pog donne
	 * @param pogName
	 * @param epgName
	 * @throws Exception
	 */
	public void addEpg(String pogName, String epgName) throws Exception
	{
		/**
		 * Test si la balise epg n'existe pas deja pour le pog donne
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//pog[@fileNamePog=\""+pogName+"\"]/epg[@fileNameEpg=\""+epgName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		
		/**
		 * Si la balise a ajouter n'existe pas encore
		 */
		if(test_elem == null)
		{
			/**
			 * Recuperation de la balise pog parente
			 */
			String expression = "//pog[@fileNamePog=\""+pogName+"\"]";
			
			Element pog = null;
			
			/**
			 * Creation de l'element a ajouter
			 */
			Element newEpg = doc.createElement("epg");
			newEpg.setAttribute("fileNameEpg", epgName);	
			
			try {
				pog = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			/**
			 * Si la balise parente est bien trouvee
			 */
			if ( pog != null ) {
				/**
				 * Ajout de la balise fille
				 */
				pog.appendChild(newEpg);
				/**
				 * Sauvegarde du document
				 */
				saveDocument((File)dependencies.get(currentProject));	
			}
			
		}

	}		

	
	/**
	 * @param apesName
	 * @param ieppName
	 * @throws Exception
	 */
	public void addIeppToApes(String apesName, String ieppName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]/iepp[@fileNameIepp=\""+ieppName+"\"]";
		
		
		Node iepp = null;
		
		try {
			iepp = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if(iepp == null)
		{
			Node apes = null;
			
			Element newElem = doc.createElement("iepp");
			newElem.setAttribute("fileNameIepp", ieppName);	
			try {
				expression =  "//apes[@fileNameApes=\""+apesName+"\"]";
				apes = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if ( apes != null ) {
				apes.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
			}
		}
	}
	
	/**
	 * @param pogName
	 * @param ieppName
	 * @throws Exception
	 */
	public void addIeppToEpg(String epgName, String ieppName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/pog/epg[@fileNameEpg=\""+epgName+"\"]/iepp[@fileNameIepp=\""+ieppName+"\"]";
		Node iepp = null;
		
		try {
			iepp = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if(iepp == null)
		{
			Element elem = null;
			Element newElem = doc.createElement("iepp");
			newElem.setAttribute("fileNameIepp", ieppName);	
			try {
				expression = "//dependencies/pog/epg[@fileNameEpg=\""+epgName+"\"]";
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
			}
		}
	}
	
	/**
	 * @param filePath
	 * @throws Exception
	 */
	public void delToUpdate(String filePath) throws Exception
	{
		/**
		 * Verification que la balise a supprimer existe ainsi que le noeud parent
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toUpdate";
		String expr = "//toUpdate/file[@path=\""+filePath+"\"]";
				
		Element elem = null;
		Element oldElem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si les deux noeud sont bien presents
		 */
		if ( elem != null && oldElem != null) {
			/**
			 * Suppression du noeud fils a partir du noeud parent
			 */
			elem.removeChild(oldElem);
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	
			
		}
	}

	/**
	 * @param filePath
	 * @throws Exception
	 */
	public void delToCreate(String filePath) throws Exception
	{
		/**
		 * Verification que la balise a supprimer existe ainsi que le noeud parent
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toCreate";
		String expr = "//toCreate/file[@path=\""+filePath+"\"]";
				
		Element elem = null;
		Element oldElem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si les deux noeud sont bien presents
		 */
		if ( elem != null && oldElem != null) {
			/**
			 * Suppression du noeud fils a partir du noeud parent
			 */
			elem.removeChild(oldElem);
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	
			
		}
	}

	
	/**
	 * @param filePath
	 * @throws Exception
	 */
	public void delApes(String filePath) throws Exception
	{
		/**
		 * Verification que la balise a supprimer existe ainsi que le noeud parent
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies";
		String expr = "//apes[@fileNameApes=\""+filePath+"\"]";
				
		Element elem = null;
		Element oldElem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si les deux noeuds sont bien presents
		 */
		if ( elem != null && oldElem != null) {
			/**
			 * Suppression du noeud fils a partir du noeud parent
			 */
			elem.removeChild(oldElem);
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	
			
		}
	}
	
	
	/**
	 * @param filePath
	 * @throws Exception
	 */
	public void delPog(String filePath) throws Exception
	{
		/**
		 * Verification que la balise a supprime existe bien
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expr = "//pog[@fileNamePog=\""+filePath+"\"]";
		
		Node parent = null;
		Element child = null;
		
		try {
			child = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			throw new XPathExpressionException(expr);
		}
		/**
		 * Si la balise a supprimer existe
		 */
		if (child != null) {
			/**
			 * Recuperation de la balise parente
			 */
			parent = child.getParentNode();
			/**
			 * Suppression de l'element fils a partir du noeud parent
			 */
			parent.removeChild(child);
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	
			
		}
	}
	
	/**
	 * @param filePath
	 * @throws Exception
	 */
	public void delEpg(String filePath) throws Exception
	{
		/**
		 * Verification que la balise a supprime existe bien
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expr = "//epg[@fileNameEpg=\""+filePath+"\"]";
		
		Node parent = null;
		Element child = null;
		
		try {
			child = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			throw new XPathExpressionException(expr);
		}
		/**
		 * Si la balise a supprimer existe
		 */
		if (child != null) {
			/**
			 * Recuperation de la balise parente
			 */
			parent = child.getParentNode();
			/**
			 * Suppression de l'element fils a partir du noeud parent
			 */
			parent.removeChild(child);
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	
			
		}
	}
	
	/**
	 * @param filePath
	 * @param apesName
	 * @throws Exception
	 */
	public void delIepp(String filePath, String apesName) throws Exception
	{
		/**
		 * Verification que la balise a supprimer existe ainsi que le noeud parent
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		String expr = "//apes[@fileNameApes=\""+apesName+"\"]/iepp[@fileNameIepp=\""+filePath+"\"]";
				
		Node apes = null;
		Element oldElem = null;
		
		try {
			apes = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		/**
		 * Si les deux elements sont bien presents
		 */
		if ( apes != null && oldElem != null) {
			/**
			 * Suppression du noeud fils a partir du noeud parent
			 */
			apes.removeChild(oldElem);
			/**
			 * Sauvegarde du document
			 */
			saveDocument((File)dependencies.get(currentProject));	
		
		}
	}

	
	/**
	 * @param projectName
	 * @param file
	 */
	public void addFile (String projectName, File file){
		if(dependencies.get(projectName)==null)
			dependencies.put(projectName, file);
	}
	
	
	/**
	 * @throws IOException
	 */
	public void refreshFiles () throws IOException{
		CDependencies cdep = new CDependencies();
		dependencies = cdep.getDependenciesFiles();
	}
	
	/**
	 * @return
	 * @throws XPathExpressionException
	 */
	public ArrayList getAllToUpdate() throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toUpdate/file";
		NodeList allToUpdate = null;
		ArrayList list = new ArrayList();
		
		try {
			allToUpdate = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( allToUpdate != null ) {
			for(int i=0; i<allToUpdate.getLength(); i++)
			{				
				list.add(allToUpdate.item(i).getAttributes().getNamedItem("path").getNodeValue());
			}
		}
		return list;
		
	}
	
	/**
	 * @return
	 * @throws XPathExpressionException
	 */
	public ArrayList getAllToCreate() throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toCreate/file";
		NodeList allToCreate = null;
		ArrayList list = new ArrayList();
		
		try {
			allToCreate = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( allToCreate != null ) {
			for(int i=0; i<allToCreate.getLength(); i++)
			{				
				list.add(allToCreate.item(i).getAttributes().getNamedItem("path").getNodeValue());
			}
		}
		return list;
		
	}
	
}

