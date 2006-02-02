package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xagdop.Model.Project;
import xagdop.Model.User;
import xagdop.Svn.SvnUpdate;

public class ProjectsParser extends Parser{

	private File projectXML;
	private static ProjectsParser PPInstance = null;
	
	public static final String RIGHT_PMANAGER = "pmanager";
	public static final String RIGHT_ARCHITECT = "architect";
	public static final String RIGHT_ANALYST = "analyst";
	public static final String RIGHT_REDACTOR = "redactor";	
	public static final String ATTR_NAME = "name";
	public static final String ATTR_LOGIN = "login";
	public static final String ATTR_DESC = "desc";
	
	/**
	 * Cette fonction est l'implementation du pattern singleton. Elle permet l'utilisation d'un 
	 * objet ProjectsParser unique en memoire. Elle cree l'objet s'il n'existe pas deja
	 * @return objet ProjectsParser
	 */
	public static ProjectsParser getInstance() {
		if (PPInstance == null)
			PPInstance = new ProjectsParser();
		return PPInstance;
	}
	
	
	/**
	 * Constructeur de la classe
	 *
	 */
	private ProjectsParser()
	{
		try {
			
			SvnUpdate svnu = new SvnUpdate();
			if((projectXML = svnu.getProjectFile())==null)
				System.out.println("Erreur");
				 
			//projectXML = new File("xagdop/ressources/XML/projects.xml"); //debug
			loadTreeInMemory(projectXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	/**
	 * Permet de recuperer la description d'un projet
	 * @param projectName Nom du projet dont on souhaite recuperer la description
	 * @return
	 */
	public String getProjectDescription(String projectName){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		String res = "";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			res = elem.getAttribute(ProjectsParser.ATTR_DESC);
			return res;
		}
		else {
			System.out.println("Recuperation de la description du projet "+ projectName + " impossible!"); 
			return res;
		}
		
	}
	
	
	/**
	 * Permet de redefinir la description d'un projet
	 * @param projectName Nom du projet
	 * @param newDescr Nouvelle description
	 */
	public void setProjectDescription(String projectName, String newDescr)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(ProjectsParser.ATTR_DESC, newDescr);
			saveDocument(projectXML);
		}
		else {
			System.out.println("setProjectDescription: Modification de la description du projet "+ projectName + " impossible!"); 
		}
	}

	
	/**
	 * Permet de savoir si un utilisateur X est associe a un projet Y 
	 * @param pName Nom du projet
	 * @param login Login de l'utilisateur
	 * @return TRUE si l'utilisateur est associ? au projet, FALSE sinon 
	 */
	public boolean isUserInProject(String pName, String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
				
		String expression = "//project[@name='"+pName+"']/user[@login='"+login+"']";
		Element elem = null;
			
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) 
			return true;			
		else 
			return false;
	}
		
	
	/**
	 * Permet de savoir si un projet existe
	 * @param projectName
	 * @return
	 */
	public boolean isProject(String projectName){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			return true;
		}
		else{
			return false;
		}
			
			
			
	}


	
	/**
	 * Creation d'un projet
	 * @param projectName
	 * @param user
	 * @param description
	 * @return
	 */
	public boolean addProject(String projectName, User user, String description)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
		UsersParser userP = UsersParser.getInstance();
		
		if(!userP.isUser(user.getLogin()))
		{
			return false;
		}
		else
		{
			Element elem = null;
			Element newElem = doc.createElement("project");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_NAME, projectName);
				newElem.setAttribute(ATTR_DESC, description);			
				elem.appendChild(newElem);

				addUser(projectName, user, true, false, false, false);
				saveDocument(projectXML);
				return true;
			}
			else {
				System.out.println("Ajout de l'utilisateur "+user.getLogin()+" impossible!");
				return false;
			}
		}
	}
	
	
	/**
	 * Ajout d'un utilisateur a un projet
	 * @param projectName Nom du projet
	 * @param login
	 * @param chef
	 * @param archi
	 * @param analyste
	 * @param redacteur
	 */
	public void addUser(String projectName, String login, boolean pmanager, boolean architect, boolean analyst,boolean redactor)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser user = UsersParser.getInstance();
		
		if(user.isUser(login))
		{
			Element elem = null;
			Element newElem = doc.createElement("user");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_LOGIN, login);
				//Creation du node rights
				Element rightsNode = doc.createElement("rights");
				newElem.appendChild(rightsNode);
				// Ajout des balises
				if (pmanager) {
					Element newRight = doc.createElement(RIGHT_PMANAGER);
					rightsNode.appendChild(newRight);
				}
				if (architect) {
					Element newRight = doc.createElement(RIGHT_ARCHITECT);
					rightsNode.appendChild(newRight);
				}
				if (analyst) {
					Element newRight = doc.createElement(RIGHT_ANALYST);
					rightsNode.appendChild(newRight);
				}
				if (redactor) {
					Element newRight = doc.createElement(RIGHT_REDACTOR);
					rightsNode.appendChild(newRight);
				}
				
				elem.appendChild(newElem);
				saveDocument(projectXML);
				System.out.println("Ajout de l'utilisateur "+login+" effectue");//debug
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}
		else{
			System.out.println("Ajout de l'utilisateur "+login+" impossible!(user inexistant)");
		}
	}
	
	
	/**
	 * Ajoute un utilisateur a un projet existant en fixant ses droits au sein du projet (en passant un User)
	 * @param projectName
	 * @param user
	 * @param chef
	 * @param archi
	 * @param analyste
	 * @param redacteur
	 * @return
	 */
	public boolean addUser(String projectName, User user, boolean pmanager, boolean architect, boolean analyst, boolean redactor)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser userP = UsersParser.getInstance();
		if(!userP.isUser(user.getLogin()))
		{
			System.out.println("addUser: L'utilisateur "+user.getLogin()+" n'existe pas!!");//debug
			return false;
		}
		else
		{
			Element elem = null;
			Element newElem = doc.createElement("user");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_LOGIN, user.getLogin());
				//Creation du node rights
				Element rightsNode = doc.createElement("rights");
				newElem.appendChild(rightsNode);
				// Ajout des balises
				if (pmanager) {
					Element newRight = doc.createElement(RIGHT_PMANAGER);
					rightsNode.appendChild(newRight);
				}
				if (architect) {
					Element newRight = doc.createElement(RIGHT_ARCHITECT);
					rightsNode.appendChild(newRight);
				}
				if (analyst) {
					Element newRight = doc.createElement(RIGHT_ANALYST);
					rightsNode.appendChild(newRight);
				}
				if (redactor) {
					Element newRight = doc.createElement(RIGHT_REDACTOR);
					rightsNode.appendChild(newRight);
				}
				
				elem.appendChild(newElem);
				saveDocument(projectXML);
				
				System.out.println("Ajout de l'utilisateur "+user.getLogin()+" effectue");//debug
				return true;
			}
			else {
				System.out.println("Ajout de l'utilisateur "+user.getLogin()+" impossible!");
				return false;
			}
		}
	}
	

	/**
	 * Ajout d'un utilisateur a un projet en fixant les droits par defaut
	 * @param projectName
	 * @param login
	 */
	public void addUser(String projectName, String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser user = UsersParser.getInstance();
		
		if(!user.isUser(login))
		{
			System.out.println("L'utilisateur "+login+" n'existe pas!");
		}
		else
		{
			Element elem = null;
			Element newElem = doc.createElement("user");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_LOGIN, login);
				elem.appendChild(newElem);
				setRights(projectName,login, false , false, false, false);
				saveDocument(projectXML);
				System.out.println("Ajout de l'utilisateur "+login+" effectue");//debug
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}
	}

	
	/**
	 * Retrait d'un utilisateur associe a un projet
	 * @param projectName Nom du projet
	 * @param login Login de l'utilisateur a associer
	 */
	public void removeUser(String projectName, String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		String expr = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		UsersParser user = UsersParser.getInstance();
		
		if(!user.isUser(login))
		{
			System.out.println("L'utilisateur "+login+" n'existe pas!");
		}
		else
		{
			Element elem = null;
			Element oldElem = null;
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
				oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				elem.removeChild(oldElem);
				saveDocument(projectXML);
				System.out.println("Suppression de l'utilisateur "+login+" effectuee!"); 
			}
			else {
				System.out.println("Suppression de l'utilisateur "+login+" impossible!"); 
			}
		}		
		
	}
	
	
	/**
	 * Suppression d'un projet
	 * @param projectName Nom du projet a supprimer
	 */
	public void removeProject(String projectName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String expr = "//project[@name='"+projectName+"']";
		
		
		Element elem = null;
		Element oldElem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.removeChild(oldElem);
			saveDocument(projectXML);
			System.out.println("Suppression du projet "+projectName+" effectuee"); 
		}
		else {
			System.out.println("Suppression du projet "+projectName+" impossible!"); 
		}
		
		
	}
	
	
	/**
	 * Fixe les droits d'un utlilisateur associe a un projet
	 * @param projectName Nom du projet
	 * @param login
	 * @param pmanager
	 * @param archi
	 * @param redac
	 * @param analyst
	 */
	public void setRights(String projectName, String login, boolean pmanager, boolean architect, boolean analyst, boolean redactor)
	{	
		setRight(projectName,login,ProjectsParser.RIGHT_PMANAGER,pmanager);
		setRight(projectName,login,ProjectsParser.RIGHT_ARCHITECT,architect);
		setRight(projectName,login,ProjectsParser.RIGHT_ANALYST,analyst);
		setRight(projectName,login,ProjectsParser.RIGHT_REDACTOR,redactor);
	}
	
	
	/**
	 * Recupere la liste des droits d'un utilisateur sur un projet
	 * @param projecName Nom du projet
	 * @param login 
	 * @return Arraylist contenant dans l'ordre les droits [pmanager,architect,analyst,redactor]
	 */
	public ArrayList getRights(String projectName, String login){
		ArrayList listRes = new ArrayList();
		listRes.add( Boolean.valueOf(getRight(projectName,login,RIGHT_PMANAGER)) );
		listRes.add( Boolean.valueOf(getRight(projectName,login,RIGHT_ARCHITECT)) );
		listRes.add( Boolean.valueOf(getRight(projectName,login,RIGHT_ANALYST)) );
		listRes.add( Boolean.valueOf(getRight(projectName,login,RIGHT_REDACTOR)) );
		return listRes;
	}
	
	
	/**
	 * Permet d'ajouter ou retirer un droit a un utilisateur associe a un projet
	 * @param projecName Nom du projet
	 * @param login Login de l'utilisateur
	 * @param right Chaine representant le droit a mettre a jour.
	 * @param value Nouvelle valeur du droit
	 */
	private void setRight(String projectName, String login, String right, boolean value){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		//si l'utilisateur est bien present
		if ( elem != null ) {
			//Cas TRUE: on ajoute la balise correspondante si elle n'existe pas deja
			if (value) { 
				expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + right;
				try {
					elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
				}
				catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				//il faut ajouter la balise
				if (elem == null) {
					expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights";
					try {
						elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						e.printStackTrace();
					}
					Element newRight = doc.createElement(right);
					elem.appendChild(newRight);
					saveDocument(projectXML);
				}
			}
			//Cas FALSE: On retire la balise correspondante si elle existe
			else { 
				expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + right;
				try {
					elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
				}
				catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				//il faut retirer la balise
				if (elem != null) { 
					String parentExpr = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights";
					Element parentElem = null;
					try {
						parentElem = (Element)xpath.evaluate(parentExpr, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						e.printStackTrace();
					}
					parentElem.removeChild(elem);
					saveDocument(projectXML);
				}
			}
		}
		else {
			System.out.println("Modification droit "+ right + " pour l'utilisateur "+login+ " impossible!");
		}
	}

	
	/**
	 * Permet de savoir si un utilisateur dispose d'un droit particulier sur un projet
	 * @param projectName Nom du projet
	 * @param login Login de l'utilisateur
	 * @param right Droit a verifier
	 * @return true si le droit est present, false sinon
	 */
	public boolean getRight(String projectName, String login, String right){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		
		boolean res = false;
		Element user = null;
		
		try {
			user = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		//on verifie que l'utilisateur existe
		if ( user != null ) {
			String expr = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + right;
			Element ok = null;
			
			try {
				ok = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			
			//si la balise existe alors l'utilisateur a le droit
			if ( ok != null){
				res = true; 
			}
			//Sinon non
			else{
				res = false;
			}
		}	
		/*
		else {
			System.out.println("Recuperation du droit "+ right + " pour l'utilisateur "+login+ " pour le projet "+projectName+" impossible!"); 
		}*/
		return res;
	}
	

	/**
	 * Construit un objet Project a partir des informations du projet contenues dans le fichier XML
	 * Et permet donc de connaitre les droits de chaque utilisateur sur le projet
	 * @param projectName Nom du projet a construire
	 * @return
	 */
	public Project buildProject(String projectName)
	{	
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		
		
		
		String exprRight="";
		
		
		Element projectNode = null;
		
		Project project = new Project(projectName);
		try {
			projectNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			//Si le projet a bien des utilisateurs
			if(projectNode.hasChildNodes()){

				
				NodeList usersNodeList;
				//Recuperation de la liste de tous les noeuds user du projet
				usersNodeList = projectNode.getChildNodes();
				
				Node nodeUser = null;
				Node nodeLogin = null;
				Element rights = null;
				
				NamedNodeMap map = null;
				NodeList allRights = null;
				//Parcours de la liste des user
				for (int i=0; i<usersNodeList.getLength(); i++)
				{
				
					//Récupération du noeud user i
					nodeUser = usersNodeList.item(i);
					//Récupération de tous les attributs du noeud user i
					map = nodeUser.getAttributes();
					
					if(map!=null){		
						//Récupération de l'attribut login sous forme de Node
						nodeLogin = map.getNamedItem(ATTR_LOGIN);						
						if(nodeLogin!=null){
							//Récupération de la valeur de l'attribut login en String
							String login = nodeLogin.getNodeValue();
							
							//Récupération de la balise rights de l'utilisateur i
							exprRight = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights";
							try {
								rights = (Element)xpath.evaluate(exprRight, this.doc, XPathConstants.NODE);}
							catch (XPathExpressionException e) {
								e.printStackTrace(); }
							
							//Si l'utilisateur a bien des droits
							if (rights != null)
							{
								//Réinitialisation de la liste et des droits
								ArrayList lrights = new ArrayList();
								boolean pmanager = false;
								boolean architect = false;
								boolean analyst = false;
								boolean redactor = false;
								
								//Récupération de tous les balises filles si il y en a
								if(rights.hasChildNodes())
								{								
									allRights = rights.getChildNodes();
									//Parcours de toutes les balises filles
									
									for(int j=0; j<allRights.getLength(); j++)
									{										
										//Si on trouve une balise pmanager										
										if((allRights.item(j).getNodeName()).equals(RIGHT_PMANAGER))
										{pmanager = true;}
										
										//Si on trouve une balise analyst	
										else if((allRights.item(j).getNodeName()).equals(RIGHT_ANALYST))
										{analyst = true;}
										
										//Si on trouve une balise architect	
										else if((allRights.item(j).getNodeName()).equals(RIGHT_ARCHITECT))
										{architect = true;}
										
										//Si on trouve une balise redactor	
										else if((allRights.item(j).getNodeName()).equals(RIGHT_REDACTOR))
										{redactor = true;}
										
									}
									
								}
								
								//Construction de l'ArrayList des droits
								lrights.add(new Boolean(pmanager));
								lrights.add(new Boolean(architect));
								lrights.add(new Boolean(analyst));
								lrights.add(new Boolean(redactor));
								//On ajoute dans la HashMap de l'objet de type Project l'utilisateur et ses droits
								project.addUser(login, lrights);
							}
						}
					}
				}
			}			
		}
		catch (XPathExpressionException e) {
			System.out.println("buildProject: Le projet "+projectName+" n'existe pas.");
			e.printStackTrace();
		}
		return project;
	}

	
	
	/**
	 * Permet de recuperer une ArrayList des noms des projets auquel appartient un utilisateur
	 * @param login Login de l'utilisateur
	 * @return ArrayList de String
	 */
	public ArrayList getProjects(String login){
		ArrayList listRes = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[user[@login='"+login+"']]";
		NodeList projectsNode = null;
		
		try {
			projectsNode = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if(projectsNode!=null)
		{
			
			NamedNodeMap mapAttributes;
			for(int i=0; i < projectsNode.getLength(); i++)
			{
				if(projectsNode.item(i)!=null)
				{
					if(projectsNode.item(i).hasAttributes())
					{
						mapAttributes = projectsNode.item(i).getAttributes();
						listRes.add(mapAttributes.getNamedItem(ATTR_NAME).getNodeValue());
					}
				}
			}
		}
		//System.out.println(listRes.toString());
		return listRes;
	}
	
}
