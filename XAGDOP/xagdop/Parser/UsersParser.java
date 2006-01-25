package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.*;

import xagdop.Model.Users;
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


public class UsersParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	
	private File fichierXML ;
	
	public static final String ATTR_LOGIN = "login";
	public static final String ATTR_PASSWD = "passwd";
	public static final String ATTR_MANAGER = "pmanager";
	public static final String ATTR_ADMIN = "admin";
	public static final String ATTR_NUM = "num";
	
	public UsersParser()
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
	
	private void loadTreeInMemory(File fichier) throws Exception {
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		this.db = dbf.newDocumentBuilder();
		this.doc = db.parse(fichier);
	}
	
	
	public boolean isUser(int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@num="+idUser+"]";
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
			//System.out.println("L'utilisateur "+idUser+" n'existe pas!");    
			return false;
		}
	}
	
	public boolean isUser(String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@login=\""+login+"\"]";
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
			//System.out.println("L'utilisateur "+login+" n'existe pas!");    
			return false;
		}
	}	

	public Users getUserByLogin(String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@login=\""+login+"\"]";
		Element elem = null;
		Users user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
				String passwd = elem.getAttribute(ATTR_PASSWD);
				int numId = Integer.parseInt(elem.getAttribute(ATTR_NUM));
				boolean admin = false;
				if(elem.getAttribute(ATTR_ADMIN).equalsIgnoreCase("true"))
					admin = true;
				boolean pman = false;
				if(elem.getAttribute(ATTR_MANAGER).equalsIgnoreCase("true"))
					pman = true;
				user = new Users(login, passwd, numId, admin, pman);
				return user;		
		}
		else {
			//System.out.println("L'utilisateur "+login+" n'existe pas!");    
			return user;
		}
	}
	
	public Users getUser(String login, String passwd)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@login=\""+login+"\"][@passwd=\""+passwd+"\"]";
		Element elem = null;
		Users user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {				
				int numId = Integer.parseInt(elem.getAttribute(ATTR_NUM));
				boolean admin = false;
				if(elem.getAttribute(ATTR_ADMIN).equalsIgnoreCase("true"))
					admin = true;
				boolean pman = false;
				if(elem.getAttribute(ATTR_MANAGER).equalsIgnoreCase("true"))
					pman = true;
				user = new Users(login, passwd, numId, admin, pman);
				return user;		
		}
		else {
			//System.out.println("L'utilisateur "+login+" n'existe pas!");    
			return user;
		}
	}
	
	public Users getUserById(int id)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@num="+id+"]";
		Element elem = null;
		Users user = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
				String passwd = elem.getAttribute(ATTR_PASSWD);
				String login = elem.getAttribute(ATTR_LOGIN);
				boolean admin = false;
				if(elem.getAttribute(ATTR_ADMIN).equalsIgnoreCase("true"))
					admin = true;
				boolean pman = false;
				if(elem.getAttribute(ATTR_MANAGER).equalsIgnoreCase("true"))
					pman = true;
				user = new Users(login, passwd, id, admin, pman);
				return user;		
		}
		else {
			//System.out.println("L'utilisateur "+id+" n'existe pas!");    
			return user;
		}
	}
	
	public Object getAttribute(int idUser, String attr)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@num="+idUser+"]";
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
			if(ATTR_LOGIN.equalsIgnoreCase(attr) || ATTR_PASSWD.equalsIgnoreCase(attr))
				return res;
			else
			{
				if(res.equals("true"))
					return Boolean.TRUE;
				else
					return Boolean.FALSE;
			}
		
		}
		else {
			//System.out.println("R??cup??ration de l'attribut "+ attr + " pour l'utilisateur "+idUser+ " impossible!"); 
			return res;
		}		
	}
	public int getId(String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@login=\""+login+"\"]";
		int res=0;
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			res = Integer.parseInt(elem.getAttribute(ATTR_NUM));
				return res;
			}
		else {
			//System.out.println("R??cup??ration de l'attribut "+ ATTR_NUM + " pour l'utilisateur "+login+ " impossible!"); 
			return res;
		}		
	}
	public void setAttribute(int idUser,String attr ,String newValue)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//id[@num="+idUser+"]";
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
			//System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+idUser+ " impossible!"); 
		}
	}
	
	

	public void addUser(int idUser, String login, String passwd, String chef, String admin)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
			Element elem = null;
			Element newElem = doc.createElement("id");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_NUM, Integer.toString(idUser));
				newElem.setAttribute(ATTR_LOGIN, login);
				newElem.setAttribute(ATTR_PASSWD, passwd);
				newElem.setAttribute(ATTR_MANAGER, chef);
				newElem.setAttribute(ATTR_ADMIN, admin);
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				//System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
			
	}
	
	public void addUser(int idUser, String login, String passwd, String chef)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
			Element elem = null;
			Element newElem = doc.createElement("id");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_NUM, Integer.toString(idUser));
				newElem.setAttribute(ATTR_LOGIN, login);
				newElem.setAttribute(ATTR_PASSWD, passwd);
				newElem.setAttribute(ATTR_MANAGER, chef);
				newElem.setAttribute(ATTR_ADMIN, "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				//System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
			
	}
	
	public void addUser(int idUser, String login, String passwd)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
			Element elem = null;
			Element newElem = doc.createElement("id");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_NUM, Integer.toString(idUser));
				newElem.setAttribute(ATTR_LOGIN, login);
				newElem.setAttribute(ATTR_PASSWD, passwd);
				newElem.setAttribute(ATTR_MANAGER, "false");
				newElem.setAttribute(ATTR_ADMIN, "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				//System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
			
	}
	
	public boolean addUser(Users user)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
			Element elem = null;
			Element newElem = doc.createElement("id");
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				newElem.setAttribute(ATTR_NUM, Integer.toString(user.getId()));
				newElem.setAttribute(ATTR_LOGIN, user.getLogin());
				newElem.setAttribute(ATTR_PASSWD, user.getPasswd());
				newElem.setAttribute(ATTR_ADMIN, Boolean.toString(user.isAdmin()));
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(user.isPmanager()));
				elem.appendChild(newElem);
				saveDocument();
				return true;
			}
			else {
				//System.out.println("Ajout de l'utilisateur "+user.getId()+" impossible!");
				return false;
			}
			
	}
	
	public void removeUser(int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String expr = "//id[@num="+idUser+"]";
		
		
		if(!isUser(idUser))
		{
			////System.out.println("L'utilisateur n'existe pas!!");
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
				//System.out.println("Suppression de l'utilisateur "+idUser+" effectu??e!"); 
			}
			else {
				//System.out.println("Suppression de l'utilisateur "+idUser+" impossible!"); 
			}
		}		
		
	}
	
	public ArrayList getAllUsers()
	{
		ArrayList usersList = new ArrayList();
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String login = null;
		String passwd  = null;
		int num = 0;
		boolean pmanager = false;
		boolean admin = false;
		
		Element usersNode = null;
		NodeList usersNodeList;
		
		
		try {
			usersNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			if(usersNode.hasChildNodes()){
				
				Node nodeAll = null;
				Node nodeN = null;
				Node nodeL = null;
				Node nodeP = null;
				Node nodeA = null;
				Node nodeM = null;
				
				
				
				NamedNodeMap map = null;
				usersNodeList = usersNode.getChildNodes();
				for (int i=0; i<usersNodeList.getLength(); i++)
				{
					nodeAll = usersNodeList.item(i);
					map = nodeAll.getAttributes();
					if(map!=null){
						nodeL = map.getNamedItem(ATTR_LOGIN);
						nodeN = map.getNamedItem(ATTR_NUM);
						nodeP = map.getNamedItem(ATTR_PASSWD);
						nodeA = map.getNamedItem(ATTR_ADMIN);
						nodeM = map.getNamedItem(ATTR_MANAGER);
						
						if(nodeL!=null){
													
							login = nodeL.getNodeValue();
							num = Integer.parseInt(nodeN.getNodeValue());
							passwd = nodeP.getNodeValue();		
							if(nodeA.getNodeValue().equalsIgnoreCase("true"))
								admin = true;
							else
								admin = false;
							if(nodeM.getNodeValue().equalsIgnoreCase("true"))
								pmanager = true;
							else
								pmanager = false;
							
							Users user = new Users(login, passwd, num, admin, pmanager);
							usersList.add(user);
						}
					}

				}
			}
			else {
				//System.out.println("Pas de fils");
			}
		
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		
		return usersList;
	}
	

	
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
			svnc.sendFile(fichierXML,"");
			loadTreeInMemory(fichierXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
