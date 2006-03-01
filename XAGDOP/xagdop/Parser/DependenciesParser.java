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
	 * Fonction recherchant tous les fichiers .apes associes au fichier .iepp passe en parametre
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
	 * Fonction recherchant tous les fichiers .iepp associes au fichier .apes passe en parametre
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
	 * Fonction permettant d'ajouter une balise apes
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
				//publish((File)dependencies.get(currentProject));
			}
			
		}
	}	

	public void addApes(String apesName, boolean onServer) throws Exception, NullPointerException
	{		
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
		
		if(test_elem == null)
		{
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

			if ( racine != null ) {

				Element newApes = doc.createElement("apes");
				newApes.setAttribute("fileNameApes", apesName);	
				racine.appendChild(newApes);
				
				if(onServer){
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
						Element elemOnServer = doc.createElement("onServer");	
						racine.appendChild(elemOnServer);
					}
				}
				saveDocument((File)dependencies.get(currentProject));	
				//publish((File)dependencies.get(currentProject));
			}
			else
			{
				ErrorManager.getInstance().setErrTitle("Pb Racine du fichier");
				ErrorManager.getInstance().setErrMsg("Fichier XML invalide, contacter rapidement l'administrateur.\n");
				throw new NullPointerException();
			}
			
		}
	}	

	public void setApesOnServer(boolean server, String apesName) throws Exception,NullPointerException
	{
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
		
		if(apes!=null)
		{
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
			
			if(server)
			{
				if(onServer == null)
				{
				Element elemOnServer = doc.createElement("onServer");	
				apes.appendChild(elemOnServer);
				}
			}
			else
			{				
				if(onServer != null)
				{
					apes.removeChild(onServer);
				}
			}
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Apes inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Apes : "+ apesName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}
	

	public boolean getApesOnServer(String apesName) throws Exception,NullPointerException
	{
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
		
		if(apes!=null)
		{
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
				return true;
			}
			else
			{				
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

	
	public void addPog(String pogName) throws Exception
	{
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
		
		if(test_elem == null)
		{
			String expression = "//dependencies";

			Element elem = null;
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

			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));
				//publish((File)dependencies.get(currentProject));
			}
			
		}
	}	
	
	public void addToUpdate(String filePath) throws Exception
	{
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
		if(test_elem == null)
		{
			String expression = "//toUpdate";
		
			Element elem = null;
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
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
				//publish((File)dependencies.get(currentProject));
			}
		}
		
	}		
	
	public void addToCreate(String filePath) throws Exception
	{
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
		if(test_elem == null)
		{
			String expression = "//toCreate";

			Element elem = null;
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
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
				//publish((File)dependencies.get(currentProject));
			}
		}
		
	}		

	
	public void addPog(String apesName, String pogName) throws Exception
	{
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
		
		if(test_elem == null)
		{
		
			String expression = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]";
			
			Element elem = null;
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
			
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
				//publish((File)dependencies.get(currentProject));
			}
			
		}

	}		

	
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
	
	public void addIeppToPog(String pogName, String ieppName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]/iepp[@fileNameIepp=\""+ieppName+"\"]";
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
				expression = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";
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
	
	public void delToUpdate(String filePath) throws Exception
	{
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
	}

	public void delToCreate(String filePath) throws Exception
	{
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
	}

	
	public void delApes(String filePath) throws Exception
	{
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
	}
	
	public void delPog(String filePath) throws Exception
	{
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
		if (child != null) {
			parent = child.getParentNode();
			parent.removeChild(child);
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
	}
	

	
	public void delIepp(String filePath, String apesName) throws Exception
	{
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
		if ( apes != null && oldElem != null) {
			apes.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			//publish((File)dependencies.get(currentProject));
		}
	}

	
	public void addFile (String projectName, File file){
		if(dependencies.get(projectName)==null)
			dependencies.put(projectName, file);
	}
	public void refreshFiles () throws IOException{
		CDependencies cdep = new CDependencies();
		dependencies = cdep.getDependenciesFiles();
	}
	
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

