package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xagdop.Model.Project;
import xagdop.Model.User;
import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnUpdate;

public class ProjectsParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
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
			//projectXML = new File("xagdop/Parser/projects.xml"); //debug
			loadTreeInMemory(projectXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Charge en memoire l'arbre associe a un fichier XML 
	 * @param fichier Fichier ? charger
	 * @throws Exception
	 */
	private void loadTreeInMemory(File fichier) throws Exception {
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		this.db = dbf.newDocumentBuilder();
		this.doc = db.parse(fichier);
	}
	
	
	/**
	 * Charge a nouveau le fichier en memoire 
	 */	
	public void refresh()
	{
		try {
			loadTreeInMemory(projectXML);
		} catch (Exception e) {
			//System.out.println("refresh");
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
			saveDocument();
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
				saveDocument();
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
				saveDocument();
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
				saveDocument();
				
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
				saveDocument();
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
				saveDocument();
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
			saveDocument();
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
					saveDocument();
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
					saveDocument();
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
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		//on verifie que l'utilisateur existe
		if ( elem != null ) {
			expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + right;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if ( elem != null){
				res = true; 
			}
			else{
				res = false;
			}
		}	
		else {
			System.out.println("Recuperation du droit "+ right + " pour l'utilisateur "+login+ " impossible!"); 
		}
		return res;
	}
	
	
	/**
	 * Construit un objet Project a partir des informations du projet contenues dans le fichier XML
	 * @param projectName Nom du projet a construire
	 * @return
	 */
	public Project buildProject(String projectName)
	{
		ArrayList userRights= new ArrayList();
		ArrayList usersRights = new ArrayList();
		ArrayList usersLogin= new ArrayList();
		
		//Project projet;
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		String login="";
		boolean pmanager = false;
		boolean architect = false;
		boolean analyst = false;
		boolean redactor = false;
		
		String exprRight="";
		Element elem = null;
		Element usersNode = null;
		NodeList usersNodeList;
		
		try {
			usersNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			if(usersNode.hasChildNodes()){
				Node nodeAll = null;
				Node nodeLogin = null;
				
				NamedNodeMap map = null;
				usersNodeList = usersNode.getChildNodes();
				for (int i=0; i<usersNodeList.getLength(); i++)
				{
					nodeAll = usersNodeList.item(i);
					map = nodeAll.getAttributes();
					
					if(map!=null){
						
						nodeLogin = map.getNamedItem(ATTR_LOGIN);						
						if(nodeLogin!=null){		
							login = nodeLogin.getNodeValue();
							
							//on recupere eventuellement la balise pmanager
							exprRight = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + RIGHT_PMANAGER;
							try {
								elem = (Element)xpath.evaluate(exprRight, this.doc, XPathConstants.NODE);}
							catch (XPathExpressionException e) {
								e.printStackTrace(); }
							if (elem != null) pmanager=true;
							
							//on recupere eventuellement la balise architect
							exprRight = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + RIGHT_ARCHITECT;
							try {
								elem = (Element)xpath.evaluate(exprRight, this.doc, XPathConstants.NODE);}
							catch (XPathExpressionException e) {
								e.printStackTrace(); }
							if (elem != null) architect=true;
							
							//on recupere eventuellement la balise analyst
							exprRight = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + RIGHT_ANALYST;
							try {
								elem = (Element)xpath.evaluate(exprRight, this.doc, XPathConstants.NODE);}
							catch (XPathExpressionException e) {
								e.printStackTrace(); }
							if (elem != null) analyst=true;
							
							//on recupere eventuellement la balise redactor
							exprRight = "//project[@name='"+projectName+"']/user[@login='"+login+"']/rights/" + RIGHT_REDACTOR;
							try {
								elem = (Element)xpath.evaluate(exprRight, this.doc, XPathConstants.NODE);}
							catch (XPathExpressionException e) {
								e.printStackTrace(); }
							if (elem != null) redactor=true;
						
							//on fixe les droit de l'utilisateur courant
							userRights.add(new Boolean(pmanager));
							userRights.add(new Boolean(architect));
							userRights.add(new Boolean(analyst));
							userRights.add(new Boolean(redactor));
							
							//on ajoute simultanement dans les deux liste resultat le login de l'utilsateur et des droits
							usersLogin.add(login);
							usersRights.add(userRights);
						}
					}
				}
			}
			else {
				System.out.println("Pas de fils");
			}
			
		}
		catch (XPathExpressionException e) {
			System.out.println("buildProject: Le projet "+projectName+" n'existe pas.");
			e.printStackTrace();
		}
		return new Project(projectName, usersRights, usersLogin);
	}

	
	/**
	 * Permet de recuperer une ArrayList des noms des projets auquel appartient un utilisateur
	 * @param login Login de l'utilisateur
	 * @return ArrayList de String
	 */
	public ArrayList getProjects(String login){
		ArrayList listRes = new ArrayList();
		
		
		//a coder
		
		
		return listRes;
	}
	
	
	/**
	 * Genere le fichier XML a partir de l'arbre en memoire
	 *
	 */
	public void saveDocument()
	{
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} 
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(projectXML));
			
			SvnCommit svnc = new SvnCommit();
			svnc.sendFile(projectXML,""); 
			
			loadTreeInMemory(projectXML);
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
