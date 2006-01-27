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
	
	public static final String ATTR_ARCHI = "archi";
	public static final String ATTR_ANALYST = "analyste";
	public static final String ATTR_REDACTEUR = "redacteur";	
	public static final String ATTR_MANAGER = "pmanager";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_URLREPO = "urlRepo";
	public static final String ATTR_LOGIN = "login";
	public static final String ATTR_DESC = "desc";
	
	public static ProjectsParser getInstance() {
		if (PPInstance == null)
			PPInstance = new ProjectsParser();
		return PPInstance;
	}
	
	private ProjectsParser()
	{
		try {
			SvnUpdate svnu = new SvnUpdate();
			if((projectXML = svnu.getProjectFile())==null)
				System.out.println("Erreur");
			//projectXML = new File("./projects.xml");	//debug Attention ? ne pas le commit
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
	 * Recupere la valeur d'un attribut d'un projet
	 * @param projectName
	 * @param attr
	 * @return
	 */
	public Object getAttribute(String projectName,String attr)
	{
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
			res = elem.getAttribute(attr);
			return res;
		}
		else {
			System.out.println("Recuperation de l'attribut "+ attr + " impossible!"); 
			return res;
		}		
	}
	
	
	/**
	 * Recupere la valeur d'un attribut d'un utilisateur associ? ? un projet
	 * @param projectName
	 * @param attr
	 * @param login
	 * @return
	 */
	public Object getAttribute(String projectName,String attr, String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		String res = "";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			res = elem.getAttribute(attr);
			if(res.equals("true"))
				return Boolean.TRUE;
			else
				return Boolean.FALSE;			
			
		}
		else {
			System.out.println("Recuperation de l'attribut "+ attr + " pour l'utilisateur "+login+" impossible!"); 
			return Boolean.FALSE;
		}		
	}
	
	
	/**
	 * Permet de savoir si un utilisateur X est associ? ? un projet Y 
	 * @param pName Nom du projet
	 * @param login Login de l'utilisateur
	 * @return TRUE si l'utilisateur est associ? au projet, FALSE sinon 
	 */
	public boolean isUserInProject(String pName, String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
				
		String expression = "//project[@name='"+pName+"']/user[@login='"+login+"']";
		//boolean res = false;
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
	 * Fixe la valeur d'un attribut d'un projet
	 * @param projectName
	 * @param attr
	 * @param newValue
	 */
	public void setAttribute(String projectName, String attr ,String newValue)
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
			elem.setAttribute(attr, newValue);
			saveDocument();
		}
		else {
			System.out.println("Modification de l'attribut "+ attr + " impossible!"); 
		}
	}
	
	
	/**
	 * Fixe la valeur d'un attribut d'un utilisateur associ? ? un projet
	 * @param projectName
	 * @param login
	 * @param attr
	 * @param newValue
	 */
	public void setAttribute(String projectName,String login, String attr, String newValue)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']/user[@login='"+login+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(attr, newValue);
			saveDocument();
			System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+login+" effectuee");//debug
		}
		else {
			System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+login+" impossible!"); 
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
			//System.out.println("L'utilisateur n'existe pas!!");
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
				newElem.setAttribute(ATTR_URLREPO, "");				
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
	 * Ajout d'un utilisateur ? un projet
	 * @param projectName
	 * @param login
	 * @param chef
	 */
	public void addUser(String projectName, String login, boolean chef)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser user = UsersParser.getInstance();
		
		if(!user.isUser(login))
		{
			//System.out.println("L'utilisateur n'existe pas!!");
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
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(chef));
				newElem.setAttribute(ATTR_ARCHI, "false");
				newElem.setAttribute(ATTR_ANALYST, "false");
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
				System.out.println("Ajout de l'utilisateur "+login+" effectue");
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}		
	}
	
	
	/**
	 * Ajout d'un utilisateur ? un projet
	 * @param projectName
	 * @param login
	 * @param chef
	 * @param archi
	 */
	public void addUser(String projectName, String login, boolean chef, boolean archi)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser user = UsersParser.getInstance();
		
		if(!user.isUser(login))
		{
			System.out.println("L'utilisateur n'existe pas!!");
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
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(chef));
				newElem.setAttribute(ATTR_ARCHI, Boolean.toString(archi));
				newElem.setAttribute(ATTR_ANALYST, "false");
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
				System.out.println("Ajout de l'utilisateur "+login+" effectue");//debug
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}		
	}
	

	/**
	 * Ajout d'un utilisateur ? un projet
	 * @param projectName
	 * @param login
	 * @param chef
	 * @param archi
	 * @param analyste
	 */
	public void addUser(String projectName, String login, boolean chef, boolean archi, boolean analyste)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser user = UsersParser.getInstance();
		
		if(!user.isUser(login))
		{
			//System.out.println("L'utilisateur n'existe pas!!");
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
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(chef));
				newElem.setAttribute(ATTR_ARCHI, Boolean.toString(archi));
				newElem.setAttribute(ATTR_ANALYST, Boolean.toString(analyste));
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
				System.out.println("Ajout de l'utilisateur "+login+" effectue");//debug
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}		
	}
	
	
	/**
	 * Ajout d'un utilisateur ? un projet
	 * @param projectName
	 * @param login
	 * @param chef
	 * @param archi
	 * @param analyste
	 * @param redacteur
	 */
	public void addUser(String projectName, String login, boolean chef, boolean archi, boolean analyste,boolean redacteur)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		
		UsersParser user = UsersParser.getInstance();
		
		if(!user.isUser(login))
		{
			//System.out.println("L'utilisateur n'existe pas!!");
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
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(chef));
				newElem.setAttribute(ATTR_ARCHI, Boolean.toString(archi));
				newElem.setAttribute(ATTR_ANALYST, Boolean.toString(analyste));
				newElem.setAttribute(ATTR_REDACTEUR, Boolean.toString(redacteur));
				elem.appendChild(newElem);
				saveDocument();
				System.out.println("Ajout de l'utilisateur "+login+" effectue");//debug
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}		
	}
	

	/**
	 * Ajoute un utilisateur ? un projet existant en fixant ses droits au sein du projet (en passant un User)
	 * @param projectName
	 * @param user
	 * @param chef
	 * @param archi
	 * @param analyste
	 * @param redacteur
	 * @return
	 */
	public boolean addUser(String projectName, User user, boolean chef, boolean archi, boolean analyste, boolean redacteur)
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
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(chef));
				newElem.setAttribute(ATTR_ARCHI, Boolean.toString(archi));
				newElem.setAttribute(ATTR_ANALYST, Boolean.toString(analyste));
				newElem.setAttribute(ATTR_REDACTEUR, Boolean.toString(redacteur));
				elem.appendChild(newElem);
				saveDocument();
				System.out.println("Ajout de l'utilisateur "+user.getLogin()+" effectu?e");//debug
				return true;
			}
			else {
				System.out.println("Ajout de l'utilisateur "+user.getLogin()+" impossible!");
				return false;
			}
		}
	}
	

	/**
	 * Ajout d'un utilisateur ? un projet
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
				newElem.setAttribute(ATTR_MANAGER, "false");
				newElem.setAttribute(ATTR_ARCHI, "false");
				newElem.setAttribute(ATTR_ANALYST, "false");
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
				System.out.println("Ajout de l'utilisateur "+login+" effectue");//debug
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}
		}		
	}
	
	
	/**
	 * Retrait d'un utilisateur associ? ? un projet
	 * @param projectName
	 * @param idUser
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
	 * @param projectName
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
	 * Ancien getAllUsers
	 * @param projectName
	 * @return
	 */
	public Project buildProject(String projectName)
	{
		ArrayList usersList = new ArrayList();
		ArrayList userRights= new ArrayList();
		ArrayList usersLogin= new ArrayList();
		
		//Project projet;
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name='"+projectName+"']";
		String login="";
		boolean pmanager = false;
		boolean archi = false;
		boolean redac = false;
		boolean analyst = false;
		
		Element usersNode = null;
		NodeList usersNodeList;
		
		
		try {
			usersNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			if(usersNode.hasChildNodes()){
				
				Node nodeAll = null;
				Node nodeLogin = null;
				Node nodeAn = null;
				Node nodeMan = null;
				Node nodeArchi = null;
				Node nodeRedac = null;
				
				NamedNodeMap map = null;
				usersNodeList = usersNode.getChildNodes();
				for (int i=0; i<usersNodeList.getLength(); i++)
				{
					nodeAll = usersNodeList.item(i);
					map = nodeAll.getAttributes();
					
					if(map!=null){
						
						nodeLogin = map.getNamedItem(ATTR_LOGIN);
						nodeAn = map.getNamedItem(ATTR_ANALYST);
						nodeMan = map.getNamedItem(ATTR_MANAGER);
						nodeArchi = map.getNamedItem(ATTR_ARCHI);
						nodeRedac = map.getNamedItem(ATTR_REDACTEUR);
						
						if(nodeLogin!=null){		
							
							login = nodeLogin.getNodeValue();
							
							if(nodeAn.getNodeValue().equalsIgnoreCase("true"))
								analyst = true;
							else
								analyst = false;
							
							if(nodeMan.getNodeValue().equalsIgnoreCase("true"))
								pmanager = true;
							else
								pmanager = false;
							
							if(nodeArchi.getNodeValue().equalsIgnoreCase("true"))
								archi = true;
							else
								archi = false;
							
							if(nodeRedac.getNodeValue().equalsIgnoreCase("true"))
								redac = true;
							else
								redac = false;
							
							userRights = new ArrayList();
							
							userRights.add(new Boolean(pmanager));
							userRights.add(new Boolean(archi));
							userRights.add(new Boolean(analyst));
							userRights.add(new Boolean(redac));
							
							usersList.add(userRights);
							
							usersLogin.add(login);
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
		return new Project(projectName, usersList, usersLogin);
	}
	

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
