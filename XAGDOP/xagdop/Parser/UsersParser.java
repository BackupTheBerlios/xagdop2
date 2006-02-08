package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNException;
import org.w3c.dom.*;

import xagdop.Model.User;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;

import javax.xml.xpath.*;

/**
 * fournit les attributs et methodes necessaires pour extraire, inserer ou modifier
 * des donnees du fichier XML contentant les donnees utilisateur
 */
public class UsersParser extends Parser{

	private static UsersParser UPInstance = null;
	
	private File usersXML ;
	
	public static final String ATTR_LOGIN = "login";
	public static final String ATTR_PASSWD = "passwd";
	public static final String ATTR_PCREATOR = "pcreator";
	public static final String ATTR_ADMIN = "admin";
	
	/**
	 * Cette fonction est l'implementation du pattern singleton. Elle permet l'utilisation d'un 
	 * objet UsersParser unique en memoire. Elle cree l'objet s'il n'existe pas deja
	 * @return objet UsersParser
	 * @throws Exception 
	 */
	public static UsersParser getInstance()  throws Exception{
		if (UPInstance == null){
			UPInstance = new UsersParser();
		}
		return UPInstance;
	}
	
	/**
	 * constructeur de la classe UsersParser
	 * @throws Exception 
	 *
	 */
	private UsersParser() throws Exception
	{
			SvnUpdate svnu = new SvnUpdate(); 
			usersXML = svnu.getUsersFile(); 
			//fichierXML = new File("xagdop/Parser/users.xml"); //debug
			loadTreeInMemory(usersXML);
	}
	
	

	
	/**
	 * Verifie qu'un utilisateur existe
	 * @param idUser nom de l'utilisateur
	 * @return vrai : utilisateur existe, faux utilisateur n'existe pas
	 * @throws XPathExpressionException 
	 */
	public boolean isUser(String login) throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
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
		else
		{
			return false;
		}
	}
	
	/**
	 * renvoie l'utilisateur correspondant au login fourni en entree
	 * @param login
	 * @return 
	 * @throws XPathExpressionException 
	 */
	public User getUserByLogin(String login) throws XPathExpressionException, NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
		Element elem = null;
		User user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
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
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			if (elem != null) admin = true;
			
			// On regarde si l'utilisateur est pcreator			
			expression = "//user[@login='"+login+"']/roles/pcreator";
			boolean pcreat = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			if (elem != null) pcreat = true;
			user = new User(login, passwd, admin, pcreat);
			return user;
		}   
		else
		{
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
		}

	}
	
	/**
	 * renvoie l'utilisateur correspondant au login et au password
	 * fournis en entree
	 * @param login
	 * @param passwd
	 * @return
	 * @throws XPathExpressionException 
	 */
	public User getUser(String login, String passwd) throws XPathExpressionException, NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"'][@passwd='"+passwd+"']";
		Element elem = null;
		User user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( elem != null ) {				
			// On regarde si l'utilisateur est admin
			expression = "//user[@login='"+login+"']/roles/admin";
			boolean admin = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			if (elem != null) admin = true;
			
			// On regarde si l'utilisateur est pcreator			
			expression = "//user[@login='"+login+"']/roles/pcreator";
			boolean pcreat = false;
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			if (elem != null) pcreat = true;
			user = new User(login, passwd, admin, pcreat);
			return user;		
		}
		else {
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" avec le mot de passe "+passwd + " est inconnu.\n");
			throw new NullPointerException();
		}
	}
	
	
	/**
	 * renvoie la valeur d'un attribut donne d'un utilisateur donne
	 * @userName nom d'utilisateur
	 * @attr attribut 
	 * @return String, ou Boolean indiquant la valeur de l'attribut. Attention, en cas de balise
	 * non existante, renvoie Boolean.FALSE et non NULL.
	 * @throws XPathExpressionException 
	 */
	public Object getAttribute(String login, String attr) throws XPathExpressionException, NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
		String res = "";
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
					ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
					ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
					throw new XPathExpressionException(expression);
				}
				if ( elem != null)  // la balise recherchee existe
					return Boolean.TRUE;
				else 
					return Boolean.FALSE;
			}		
		}
		else {
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
		}		
	}
	
	/**
	 * Met a jour l'attribut admin ou pcreator d'un utilisateur
	 * @param login utilisateur dont on modifie un attribut
	 * @param attr attribut a modifier
	 * @param newValue valeur booleenne indiquant si l'utilisateur acquiert cette propriete
	 * @throws Exception 
	 * @throws SVNException 
	 */
	public void setAttribute(String login, String attr, boolean newValue) throws SVNException, Exception, NullPointerException 
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
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
			if (attr == ATTR_ADMIN || attr == ATTR_PCREATOR) { // l'attribut est admin ou pcreator
				if (newValue) { // on ajoute la balise correspondante si elle n'existe pas deja
					expression = "//user[@login='"+login+"']/roles/" + attr;
					try {
						elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
						ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
						throw new XPathExpressionException(expression);
					}
					if (elem == null) { // il faut ajouter la balise						
						expression = "//user[@login='"+login+"']/roles";
						try {
							elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
						}
						catch (XPathExpressionException e) {
							ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
							ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
							throw new XPathExpressionException(expression);
						}
						Element newRole = doc.createElement(attr);
						elem.appendChild(newRole);
						saveDocument(usersXML);
						
						publish(usersXML);
						
					}						
				}
				else { // On retire la balise correspondante si elle existe
					expression = "//user[@login='"+login+"']/roles/" + attr;
					try {
						elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
						ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
						throw new XPathExpressionException(expression);
					}
					if (elem != null) { // il faut retirer la balise
						String parentExpr = "//user[@login='"+login+"']/roles";
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
						saveDocument(usersXML);
					}
				}
			} // fin du if (attr != ATTR_LOGIN || attr != ATTR_PASSWD)
		}
		else {
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException();
		}
	}
	
	/**
	 * Met a jour l'attribut login ou password d'un utilisateur
	 * @param login utilisateur dont on modifie un attribut
	 * @param attr attribut a modifier
	 * @param newValue nouvelle valeur de l'attribut modifie
	 * @throws Exception 
	 */
	public void setAttribute(String login, String attr, String newValue) throws Exception, NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//user[@login='"+login+"']";
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
			if (attr == ATTR_LOGIN || attr == ATTR_PASSWD){
				elem.setAttribute(attr, newValue);
						
			saveDocument(usersXML);
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
			throw new NullPointerException(); 
		}
	}
	
	
	/**
	 * Ajoute un utilisateur, en specifiant ses attributs (identifiant, mot de passe) et ses 
	 * proprietes (admin, pcreator)
	 * @param login identifiant de l'utilisateur
	 * @param passwd mot de passe de l'utilisateur
	 * @param admin propriete admin
	 * @param pcreator propriete pcreator
	 * @throws Exception, NullPointerException 
	 */
	public void addUser(String login, String passwd, boolean admin, boolean pcreator) throws Exception, NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
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
				saveDocument(usersXML);
			}
			else {
				ErrorManager.getInstance().setErrTitle("Fichier XML invalide");
				ErrorManager.getInstance().setErrMsg("Le fichier utilisateur est invalide, veuillez contacter l'administrateur.\n");
				throw new NullPointerException(); 
			}		
	}
	
	/**
	 * Ajoute un utilisateur, en specifiant ses attributs (identifiant, mot de passe). Ses 
	 * proprietes (admin, pcreator) sont false
	 * @param login identifiant de l'utilisateur
	 * @param passwd mot de passe de l'utilisateur
	 * @throws Exception 
	 * @throws NullPointerException 
	 */	
	public void addUser(String login, String passwd) throws NullPointerException, Exception
	{
		addUser(login, passwd, false, false);
	}
	
	/**
	 * Ajoute un utilisateur a partir d'un objet Users dont on obtient les attributs 
	 * et proprietes
	 * @param user contient les donnees de l'utilisateur a ajouter
	 * @throws Exception 
	 * @throws NullPointerException 
	 */
	public void addUser(User user) throws NullPointerException, Exception
	{
		addUser(user.getLogin(), user.getPasswd(), user.isAdmin(), user.isPcreator());
	}
	
	/**
	 * Supprime l'utilisateur dont l'identifiant est passe en parametre
	 * @param login identifiant de l'utilisateur a supprimer
	 * @throws Exception 
	 * @throws DOMException 
	 * @throws XPathExpressionException 
	 */
	public void removeUser(String login) throws XPathExpressionException, DOMException, Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String expr = "//user[@login='"+login+"']";
		
		
		if(isUser(login))
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
				saveDocument(usersXML);
			}
			else {
				ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
				ErrorManager.getInstance().setErrMsg("L'utilisateur "+ login +" est inconnu.\n");
				throw new NullPointerException();
			}
		}		
		
	}
	
	/**
	 * 
	 * @return
	 * @throws XPathExpressionException 
	 */
	public ArrayList getAllUsers() throws XPathExpressionException
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
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}	
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
							ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
							ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
							throw new XPathExpressionException(expression);
						}
						if (elem != null) admin = true;
						else admin = false;
						exprRole = "//user[@login='"+login+"']/roles/" + ATTR_PCREATOR;
						try {
							elem = (Element)xpath.evaluate(exprRole, this.doc, XPathConstants.NODE);
						}
						catch (XPathExpressionException e) {
							ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
							ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
							throw new XPathExpressionException(expression);
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
			ErrorManager.getInstance().setErrTitle("Fichier XML invalide");
			ErrorManager.getInstance().setErrMsg("Le fichier des utilisateurs est invalide, contacter l'administrateur.\n");
			throw new XPathExpressionException(expression);
		}
	
		return usersList;
	}

	public File getUsersXML() {
		return usersXML;
	}
	
}
