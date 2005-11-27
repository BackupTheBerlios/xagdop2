package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.*;

import xagdop.Model.Users;

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
	
	private String fichierXML = "xagdop/users.xml";
	
	public static final String ATTR_LOGIN = "login";
	public static final String ATTR_PASSWD = "passwd";
	public static final String ATTR_MANAGER = "pmanager";
	public static final String ATTR_ADMIN = "admin";
	public static final String ATTR_NUM = "num";
	
	public UsersParser()
	{
		try {
			chargerArbreEnMemoire(new File(fichierXML));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chargerArbreEnMemoire(File fichier) throws Exception {
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		this.db = dbf.newDocumentBuilder();
		this.doc = db.parse(fichier);
	}
	
	
	public boolean isUser(int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//child::id[attribute::num="+idUser+"]";
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
			System.out.println("L'utilisateur "+idUser+" n'existe pas!");    
			return false;
		}
	}
	

	
	public Object getAttribute(int idUser, String attr)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//child::id[attribute::num="+idUser+"]";
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
			System.out.println("Récupération de l'attribut "+ attr + " pour l'utilisateur "+idUser+ " impossible!"); 
			return res;
		}		
	}

	public void setAttribute(int idUser,String attr ,String newValue)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//child::id[attribute::num="+idUser+"]";
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
			System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+idUser+ " impossible!"); 
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
				newElem.setAttribute("num", Integer.toString(idUser));
				newElem.setAttribute("login", login);
				newElem.setAttribute("passwd", passwd);
				newElem.setAttribute("chef", chef);
				newElem.setAttribute("admin", admin);
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
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
				newElem.setAttribute("num", Integer.toString(idUser));
				newElem.setAttribute("login", login);
				newElem.setAttribute("passwd", passwd);
				newElem.setAttribute("chef", chef);
				newElem.setAttribute("admin", "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
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
				newElem.setAttribute("num", Integer.toString(idUser));
				newElem.setAttribute("login", login);
				newElem.setAttribute("passwd", passwd);
				newElem.setAttribute("chef", "false");
				newElem.setAttribute("admin", "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
			
	}
	
	public void removeUser(int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String expr = "//child::id[attribute::num="+idUser+"]";
		
		
		if(!isUser(idUser))
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
				System.out.println("Suppression de l'utilisateur "+idUser+" effectuée!"); 
			}
			else {
				System.out.println("Suppression de l'utilisateur "+idUser+" impossible!"); 
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
				System.out.println("Des fils");
				
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
				System.out.println("Pas de fils");
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

	}
}
