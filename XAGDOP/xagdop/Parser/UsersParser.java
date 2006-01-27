package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.*;

import xagdop.Model.User;
import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnUpdate;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

/**
 * fournit les attributs et methodes necessaires pour extraire, inserer ou modifier
 * des donnees du fichier XML contentant les donnees utilisateur
 */
public class UsersParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private static UsersParser UPInstance = null;
	
	private File fichierXML ;
	
	public static final String ATTR_LOGIN = "login";
	public static final String ATTR_PASSWD = "passwd";
	public static final String ATTR_PCREATOR = "pcreator";
	public static final String ATTR_ADMIN = "admin";
	
	/**
	 * Cette fonction est l'implementation du pattern singleton. Elle permet l'utilisation d'un 
	 * objet UsersParser unique en memoire. Elle cree l'objet s'il n'existe pas deja
	 * @return objet UsersParser
	 */
	public static UsersParser getInstance() {
		if (UPInstance == null)
			UPInstance = new UsersParser();
		return UPInstance;
	}
	
	/**
	 * constructeur de la classe UsersParser
	 *
	 */
	private UsersParser()
	{
		try {
			SvnUpdate svnu = new SvnUpdate(); 
			fichierXML = svnu.getUsersFile();
			loadTreeInMemory(fichierXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge l'arbre du ficher en memoire
	 * @param fichier charge en memoire
	 * @throws probleme de memoire ou d'acces fichier
	 */
	private void loadTreeInMemory(File fichier) throws Exception {
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		this.db = dbf.newDocumentBuilder();
		this.doc = db.parse(fichier);
	}
	
	/**
	 * Verifie qu' un utilisateur existe
	 * @param idUser nom de l'utilisateur
	 * @return vrai : utilisateur existe, faux utilisateur n'existe pas
	 */
	public boolean isUser(String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
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
		else {
			System.out.println("L'utilisateur "+login+" n'existe pas!");    
			return false;
		}
	}
	
	/**
	 * renvoie l'utilisateur correspondant au login fourni en entree
	 * @param login
	 * @return 
	 */
	public User getUserByLogin(String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
		Element elem = null;
		User user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			String passwd = elem.getAttribute(ATTR_PASSWD);
			
			// On regarde si l'utilisateur est admin
			expression = "//user[@login='"+login+"']/roles/admin";
			boolean admin = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if (elem != null) admin = true;
			
			// On regarde si l'utilisateur est pcreator			
			expression = "//user[@login='"+login+"']/roles/pcreator";
			boolean pcreat = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if (elem != null) pcreat = true;
			user = new User(login, passwd, admin, pcreat);
			return user;		
		}
		else {
			System.out.println("L'utilisateur "+login+" n'existe pas!");    
			return user;
		}
	}
	
	/**
	 * renvoie l'utilisateur correspondant au login et au password
	 * fournis en entree
	 * @param login
	 * @param passwd
	 * @return
	 */
	public User getUser(String login, String passwd)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"'][@passwd='"+passwd+"']";
		Element elem = null;
		User user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {				
			// On regarde si l'utilisateur est admin
			expression = "//user[@login='"+login+"']/roles/admin";
			boolean admin = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if (elem != null) admin = true;
			
			// On regarde si l'utilisateur est pcreator			
			expression = "//user[@login='"+login+"']/roles/pcreator";
			boolean pcreat = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			if (elem != null) pcreat = true;
			user = new User(login, passwd, admin, pcreat);
			return user;		
		}
		else {
			System.out.println("L'utilisateur "+login+" n'existe pas!");    
			return user;
		}
	}
	
	
	/**
	 * renvoie la valeur d'un attribut donne d'un utilisateur donne
	 * @userName nom d'utilisateur
	 * @attr attribut 
	 * @return String, ou Boolean indiquant la valeur de l'attribut. Attention, en cas de balise
	 * non existante, renvoie Boolean.FALSE et non NULL.
	 */
	public Object getAttribute(String login, String attr)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
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
			if(ATTR_LOGIN.equalsIgnoreCase(attr) || ATTR_PASSWD.equalsIgnoreCase(attr)) {
				res = elem.getAttribute(attr);
				return res;
			}
			else
			{	// L'utilisateur veut obtenir la valeur admin ou pcreator
				expression = "//user[@login='"+login+"']/roles/" + attr;
				try {
					elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
				}
				catch (XPathExpressionException e) {
					
					e.printStackTrace();
				}
				if ( elem != null)  // la balise recherchee existe
					return Boolean.TRUE;
				else 
					return Boolean.FALSE;
			}		
		}
		else {
			System.out.println("Recuperation de l'attribut "+ attr + " pour l'utilisateur "+login+ " impossible!"); 
			return res;
		}		
	}
	
	/**
	 * Met a jour l'attribut admin ou pcreator d'un utilisateur
	 * @param login utilisateur dont on modifie un attribut
	 * @param attr attribut a modifier
	 * @param newValue valeur booleenne indiquant si l'utilisateur acquiert cette propriete
	 */
	public void setAttribute(String login, String attr, boolean newValue) 
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			if (attr == ATTR_ADMIN || attr == ATTR_PCREATOR) { // l'attribut est admin ou pcreator
				if (newValue) { // on ajoute la balise correspondante si elle n'existe pas deja
					expression = "//user[@login='"+login+"']/roles/" + attr;
					try {
						elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						e.printStackTrace();
					}
					if (elem == null) { // il faut ajouter la balise						
						expression = "//user[@login='"+login+"']/roles";
						try {
							elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
						}
						catch (XPathExpressionException e) {
							e.printStackTrace();
						}
						Element newRole = doc.createElement(attr);
						elem.appendChild(newRole);
						saveDocument();
					}						
				}
				else { // On retire la balise correspondante si elle existe
					expression = "//user[@login='"+login+"']/roles/" + attr;
					try {
						elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						e.printStackTrace();
					}
					if (elem != null) { // il faut retirer la balise
						String parentExpr = "//user[@login='"+login+"']/roles";
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
			} // fin du if (attr != ATTR_LOGIN || attr != ATTR_PASSWD)
			else {
				System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+login+ " impossible!");
			}
		}
		else {
			System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+login+ " impossible!");
		}
	}
	
	/**
	 * Met a jour l'attribut login ou password d'un utilisateur
	 * @param login utilisateur dont on modifie un attribut
	 * @param attr attribut a modifier
	 * @param newValue nouvelle valeur de l'attribut modifie
	 */
	public void setAttribute(String login, String attr, String newValue)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			if (attr == ATTR_LOGIN || attr == ATTR_PASSWD)
				elem.setAttribute(attr, newValue);
			else { // l'attribut est admin ou pcreator
				System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+login+ " impossible!"); 
			}				
			saveDocument();
		}
		else {
			System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+login+ " impossible!"); 
		}
	}
	
	
	/**
	 * Ajoute un utilisateur, en specifiant ses attributs (identifiant, mot de passe) et ses 
	 * proprietes (admin, pcreator)
	 * @param login identifiant de l'utilisateur
	 * @param passwd mot de passe de l'utilisateur
	 * @param admin propriete admin
	 * @param pcreator propriete pcreator
	 */
	public void addUser(String login, String passwd, boolean admin, boolean pcreator)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
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
				newElem.setAttribute(ATTR_PASSWD, passwd);
				// Creation du node roles
				Element rolesNode = doc.createElement("roles");
				newElem.appendChild(rolesNode);
				// Ajout des balises
				if (pcreator) { // On ajoute la balise pcreator
					Element newRole = doc.createElement(ATTR_PCREATOR);
					rolesNode.appendChild(newRole);
				}
				if (admin) { // On ajoute la balise admin
					Element newRole = doc.createElement(ATTR_ADMIN);
					rolesNode.appendChild(newRole);
				}
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+login+" impossible!"); 
			}		
	}
	
	/**
	 * Ajoute un utilisateur, en specifiant ses attributs (identifiant, mot de passe). Ses 
	 * proprietes (admin, pcreator) sont false
	 * @param login identifiant de l'utilisateur
	 * @param passwd mot de passe de l'utilisateur
	 */	
	public void addUser(String login, String passwd)
	{
		addUser(login, passwd, false, false);
	}
	
	/**
	 * Ajoute un utilisateur a partir d'un objet Users dont on obtient les attributs 
	 * et proprietes
	 * @param user contient les donnees de l'utilisateur a ajouter
	 */
	public void addUser(User user)
	{
		addUser(user.getLogin(), user.getPasswd(), user.isAdmin(), user.isPcreator());
	}
	
	/**
	 * Supprime l'utilisateur dont l'identifiant est passe en parametre
	 * @param login identifiant de l'utilisateur a supprimer
	 */
	public void removeUser(String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String expr = "//user[@login='"+login+"']";
		
		
		if(!isUser(login))
		{
			//System.out.println("L'utilisateur n'existe pas!!");
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
				System.out.println("Suppression de l'utilisateur "+login+" effectu??e!"); 
			}
			else {
				System.out.println("Suppression de l'utilisateur "+login+" impossible!"); 
			}
		}		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList getAllUsers()
	{
		ArrayList usersList = new ArrayList();
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String login = null;
		String passwd  = null;
		boolean pcreator = false;
		boolean admin = false;
		String exprRole;
		Element elem = null;
		
		Element usersNode = null;
		NodeList usersNodeList;
		
		
		try {
			usersNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			if(usersNode.hasChildNodes()){				
				Node nodeAll = null;
				Node nodeL = null;
				Node nodeP = null;				
				
				NamedNodeMap map = null;
				usersNodeList = usersNode.getChildNodes();
				for (int i=0; i<usersNodeList.getLength(); i++)
				{
					nodeAll = usersNodeList.item(i);
					map = nodeAll.getAttributes();
					if(map!=null){
						nodeL = map.getNamedItem(ATTR_LOGIN);

						nodeP = map.getNamedItem(ATTR_PASSWD);
						
						if(nodeL!=null){
													
							login = nodeL.getNodeValue();
							passwd = nodeP.getNodeValue();
							exprRole = "//user[@login='"+login+"']/roles/" + ATTR_ADMIN;
							try {
								elem = (Element)xpath.evaluate(exprRole, this.doc, XPathConstants.NODE);
							}
							catch (XPathExpressionException e) {
								
								e.printStackTrace();
							}
							if (elem != null) admin = true;
							else admin = false;
							exprRole = "//user[@login='"+login+"']/roles/" + ATTR_PCREATOR;
							try {
								elem = (Element)xpath.evaluate(exprRole, this.doc, XPathConstants.NODE);
							}
							catch (XPathExpressionException e) {
								
								e.printStackTrace();
							}
							if (elem != null) pcreator = true;
							else pcreator = false;
							
							User user = new User(login, passwd, admin, pcreator);
							usersList.add(user);
						}
					}

				}
			}
			else {
				System.out.println("Pas de fils");
			}
		
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		
		return usersList;
	}
	

	/**
	 * Sauvegarde le document XML contenant les donnees utilisateur
	 *
	 */
	public void saveDocument()
	{
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(fichierXML));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SvnCommit svnc = new SvnCommit();
			svnc.sendFile(fichierXML, "");
			loadTreeInMemory(fichierXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
