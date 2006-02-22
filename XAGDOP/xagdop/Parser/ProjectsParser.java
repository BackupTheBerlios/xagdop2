package xagdop.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.tmatesoft.svn.core.SVNException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xagdop.Model.Project;
import xagdop.Model.User;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;

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
	 * @throws Exception 
	 * @throws IOException 
	 * @throws SVNException 
	 */
	public static ProjectsParser getInstance() throws SVNException, IOException, Exception {
		if (PPInstance == null)
			PPInstance = new ProjectsParser();
		return PPInstance;
	}
	
	
	/**
	 * Constructeur de la classe
	 * @throws SVNException, Exception 
	 *
	 */
	private ProjectsParser() throws SVNException, Exception, IOException
	{		
			SvnUpdate svnu = new SvnUpdate();
			if((projectXML = svnu.getProjectFile())!=null)
			{loadTreeInMemory(projectXML);}
			else{
				ErrorManager.getInstance().setErrTitle("Fichier inconnu");
				ErrorManager.getInstance().setErrMsg("Fichier XML inconnu, contacter l'administrateur.\n");
				throw new IOException();
			}
			//projectXML = new File("xagdop/ressources/XML/projects.xml"); //debug
	
	}

	/**
	 * Permet de recuperer la description d'un projet
	 * @param projectName Nom du projet dont on souhaite recuperer la description
	 * @return
	 * @throws XPathExpressionException 
	 */
	public String getProjectDescription(String projectName) throws XPathExpressionException, NullPointerException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( elem != null ) {
			return elem.getAttribute(ProjectsParser.ATTR_DESC);
		}
		else {
			ErrorManager.getInstance().setErrTitle("Projet inconnu");
			ErrorManager.getInstance().setErrMsg("Le projet "+projectName+" est inconnu.\n");
			throw new NullPointerException();
		}
		
	}
	
	
	/**
	 * Permet de redefinir la description d'un projet
	 * @param projectName Nom du projet
	 * @param newDescr Nouvelle description
	 * @throws Exception 
	 */
	public void setProjectDescription(String projectName, String newDescr) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( elem != null ) {
			elem.setAttribute(ProjectsParser.ATTR_DESC, newDescr);
			saveDocument(projectXML);
		}
		else {
			ErrorManager.getInstance().setErrTitle("Projet inconnu");
			ErrorManager.getInstance().setErrMsg("Le projet "+projectName+" est inconnu.\n");
			throw new NullPointerException(); 
		}
	}

	
	/**
	 * Permet de savoir si un utilisateur X est associe a un projet Y 
	 * @param pName Nom du projet
	 * @param login Login de l'utilisateur
	 * @return TRUE si l'utilisateur est associe au projet, FALSE sinon 
	 * @throws XPathExpressionException 
	 */
	public boolean isUserInProject(String pName, String login) throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
				
		String expression = "//project[@name='"+pName+"']/user[@login='"+login+"']";
		Element elem = null;
			
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( elem != null ){
			return true;			
		}
		else{
			return false;
		}
	}
		
	
	/**
	 * Permet de savoir si un projet existe
	 * @param projectName
	 * @return
	 * @throws XPathExpressionException 
	 */
	public boolean isProject(String projectName) throws XPathExpressionException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
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
	 * @throws Exception 
	 */
	public boolean addProject(String projectName, User user, String description) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
		UsersParser userP = UsersParser.getInstance();
		
		if(userP.isUser(user.getLogin()))
		{
			Element elem = null;
			Element newElem = doc.createElement("project");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
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
				ErrorManager.getInstance().setErrTitle("Fichier XML invalide");
				ErrorManager.getInstance().setErrMsg("Le fichier projet est invalide, veuillez contacter l'administrateur.\n");
				throw new NullPointerException(); 
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ user.getLogin() +" est inconnu.\n");
			throw new NullPointerException();
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
	 * @throws Exception 
	 */
	public void addUser(String projectName, String login, boolean pmanager, boolean architect, boolean analyst,boolean redactor) throws Exception
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
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
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
			}
			else {
				ErrorManager.getInstance().setErrTitle("Projet inconnu");
				ErrorManager.getInstance().setErrMsg("Le projet "+ projectName +" est inconnu.\n");
				throw new NullPointerException();
			}
		}
		else{
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
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
	 * @throws Exception 
	 */
	public boolean addUser(String projectName, User user, boolean pmanager, boolean architect, boolean analyst, boolean redactor) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser userP = UsersParser.getInstance();
		if(userP.isUser(user.getLogin()))
		{
			Element elem = null;
			Element newElem = doc.createElement("user");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
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
				
				return true;
			}
			else {
				ErrorManager.getInstance().setErrTitle("Projet inconnu");
				ErrorManager.getInstance().setErrMsg("Le projet "+ projectName +" est inconnu.\n");
				throw new NullPointerException();
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ user.getLogin() +" est inconnu.\n");
			throw new NullPointerException();
		}
	}
	

	/**
	 * Ajout d'un utilisateur a un projet en fixant les droits par defaut
	 * @param projectName
	 * @param login
	 * @throws Exception 
	 */
	public void addUser(String projectName, String login) throws Exception
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
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_LOGIN, login);
				elem.appendChild(newElem);
				setRights(projectName,login, false , false, false, false);
				saveDocument(projectXML);
				publish(projectXML);
			}
			else {
				ErrorManager.getInstance().setErrTitle("Projet inconnu");
				ErrorManager.getInstance().setErrMsg("Le projet "+ projectName +" est inconnu.\n");
				throw new NullPointerException();
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
		}
	}

	
	/**
	 * Retrait d'un utilisateur associe a un projet
	 * @param projectName Nom du projet
	 * @param login Login de l'utilisateur a associer
	 * @throws Exception 
	 */
	public void removeUser(String projectName, String login) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		String expr = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		UsersParser user = UsersParser.getInstance();
		
		if(user.isUser(login))
		{
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
			if ( elem != null ) {
				elem.removeChild(oldElem);
				saveDocument(projectXML);
				publish(projectXML);
			}
			else {
				ErrorManager.getInstance().setErrTitle("Projet inconnu");
				ErrorManager.getInstance().setErrMsg("Le projet "+ projectName +" est inconnu.\n");
				throw new NullPointerException();
			}
		}		
		else
		{
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
		}
		
	}
	
	
	/**
	 * Suppression d'un projet
	 * @param projectName Nom du projet a supprimer
	 * @throws Exception 
	 */
	public void removeProject(String projectName) throws Exception
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
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( elem != null ) {
			elem.removeChild(oldElem);
			saveDocument(projectXML);
			publish(projectXML);
		}
		else {
			ErrorManager.getInstance().setErrTitle("Projet inconnu");
			ErrorManager.getInstance().setErrMsg("Le projet "+ projectName +" est inconnu.\n");
			throw new NullPointerException();
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
	 * @throws Exception 
	 */
	public void setRights(String projectName, String login, boolean pmanager, boolean architect, boolean analyst, boolean redactor) throws Exception
	{	
		setRight(projectName,login,ProjectsParser.RIGHT_PMANAGER,pmanager);
		setRight(projectName,login,ProjectsParser.RIGHT_ARCHITECT,architect);
		setRight(projectName,login,ProjectsParser.RIGHT_ANALYST,analyst);
		setRight(projectName,login,ProjectsParser.RIGHT_REDACTOR,redactor);
		publish(projectXML);
	}
	
	
	/**
	 * Recupere la liste des droits d'un utilisateur sur un projet
	 * @param projecName Nom du projet
	 * @param login 
	 * @return Arraylist contenant dans l'ordre les droits [pmanager,architect,analyst,redactor]
	 * @throws XPathExpressionException 
	 */
	public ArrayList getRights(String projectName, String login) throws XPathExpressionException{
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
	 * @throws Exception 
	 */
	private void setRight(String projectName, String login, String right, boolean value) throws Exception{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
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
					ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
					ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
					throw new XPathExpressionException(expression);
				}
				//il faut ajouter la balise
				if (elem == null) {
					expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights";
					try {
						elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
						ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
						throw new XPathExpressionException(expression);
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
					ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
					ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
					throw new XPathExpressionException(expression);
				}
				//il faut retirer la balise
				if (elem != null) { 
					String parentExpr = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights";
					Element parentElem = null;
					try {
						parentElem = (Element)xpath.evaluate(parentExpr, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
						ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
						throw new XPathExpressionException(expression);
					}
					parentElem.removeChild(elem);
					saveDocument(projectXML);
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
		}
	}

	
	/**
	 * Permet de savoir si un utilisateur dispose d'un droit particulier sur un projet
	 * @param projectName Nom du projet
	 * @param login Login de l'utilisateur
	 * @param right Droit a verifier
	 * @return true si le droit est present, false sinon
	 * @throws XPathExpressionException 
	 */
	public boolean getRight(String projectName, String login, String right) throws XPathExpressionException{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		
		boolean res = false;
		Element user = null;
		
		try {
			user = (Element)xpath.evaluate(expression, doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		//on verifie que l'utilisateur existe
		if ( user != null ) {
			String expr = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + right;
			Element ok = null;
			
			try {
				ok = (Element)xpath.evaluate(expr, doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
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
		
		else {
			ErrorManager.getInstance().setErrTitle("Ne travaille plus sur ce projet");
			ErrorManager.getInstance().setErrMsg("Vous ne travaillez plus sur ce projet, remettez ? jour vos projets.\n");
			throw new NullPointerException(); 
		}
		return res;
	}
	

	/**
	 * Construit un objet Project a partir des informations du projet contenues dans le fichier XML
	 * Et permet donc de connaitre les droits de chaque utilisateur sur le projet
	 * @param projectName Nom du projet a construire
	 * @return
	 * @throws XPathExpressionException 
	 * @throws IOException 
	 */
	public Project buildProject(String projectName) throws XPathExpressionException, IOException
	{	
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		

		String exprRight="";
		
		
		Element projectNode = null;
		
		Project project = new Project(projectName);
		try {
			projectNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}	
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
				//R??cup??ration du noeud user i
				nodeUser = usersNodeList.item(i);
				//R??cup??ration de tous les attributs du noeud user i
				map = nodeUser.getAttributes();
				
				if(map!=null){		
					//R??cup??ration de l'attribut login sous forme de Node
					nodeLogin = map.getNamedItem(ATTR_LOGIN);						
					if(nodeLogin!=null){
						//R??cup??ration de la valeur de l'attribut login en String
						String login = nodeLogin.getNodeValue();
						
						//R??cup??ration de la balise rights de l'utilisateur i
						exprRight = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights";
						try {
							rights = (Element)xpath.evaluate(exprRight, this.doc, XPathConstants.NODE);}
						catch (XPathExpressionException e) {
							e.printStackTrace(); }
						
						//Si l'utilisateur a bien des droits
						if (rights != null)
						{
							//R??initialisation de la liste et des droits
							ArrayList lrights = new ArrayList();
							boolean pmanager = false;
							boolean architect = false;
							boolean analyst = false;
							boolean redactor = false;
							
							//R??cup??ration de tous les balises filles si il y en a
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
		else {
			ErrorManager.getInstance().setErrTitle("Fichier XML invalide");
			ErrorManager.getInstance().setErrMsg("Le fichier XML est invalide, contacter l'administrateur.\n");
			throw new IOException(); 
		}
		
		return project;
	}

	
	
	/**
	 * Permet de recuperer une ArrayList des noms des projets auquel appartient un utilisateur
	 * @param login Login de l'utilisateur
	 * @return ArrayList de String
	 * @throws XPathExpressionException 
	 */
	public ArrayList getProjects(String login) throws XPathExpressionException{
		ArrayList listRes = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[user[@login='"+login+"']]";
		NodeList projectsNode = null;
		
		try {
			projectsNode = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
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
		
		return listRes;
	}


	public File getProjectXML() {
		return projectXML;
	}
	
}
