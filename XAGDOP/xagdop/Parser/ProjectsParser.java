package xagdop.Parser;

import java.io.File;

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
import org.w3c.dom.Node;

public class ProjectsParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	
	private String projectXML = "xagdop/projects.xml";
	
	public static String ATTR_ARCHI = "archi";
	public static String ATTR_ANALYST = "analyste";
	public static String ATTR_REDACTEUR = "redacteur";	
	public static String ATTR_MANAGER = "pmanager";
	public static String ATTR_NAME = "name";
	public static String ATTR_URLREPO = "urlRepo";
	public static String ATTR_IDUSER = "id";	
	
	public ProjectsParser()
	{
		try {
			chargerArbreEnMemoire(new File(projectXML));
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
	
	public Object getAttribute(String projectName,String attr)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
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
			System.out.println("Récupération de l'attribut "+ attr + " impossible!"); 
			return res;
		}		
	}
	
	public Object getAttribute(String projectName,String attr, int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]//user[@id="+idUser+"]";
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
			System.out.println("Récupération de l'attribut "+ attr + " pour l'utilisateur "+idUser+" impossible!"); 
			return res;
		}		
	}
	
	public void setAttribute(String projectName, String attr ,String newValue)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
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
	
	public void setAttribute(String projectName,int idUser, String attr ,String newValue)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]//user[@id="+idUser+"]";
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
			System.out.println("Modification de l'attribut "+ attr + " pour l'utilisateur "+idUser+" impossible!"); 
		}
	}

	
	public void addUser(String projectName, int idUser, String chef, String archi, String analyste, String redacteur)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		
		UsersParser user = new UsersParser();
		
		if(!user.isUser(idUser))
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
				newElem.setAttribute(ATTR_IDUSER, Integer.toString(idUser));
				newElem.setAttribute(ATTR_MANAGER, chef);
				newElem.setAttribute(ATTR_ARCHI, archi);
				newElem.setAttribute(ATTR_ANALYST, analyste);
				newElem.setAttribute(ATTR_REDACTEUR, redacteur);
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
		}
	}
	
	public void addUser(String projectName, int idUser, String chef)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		
		UsersParser user = new UsersParser();
		
		if(!user.isUser(idUser))
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
				newElem.setAttribute(ATTR_IDUSER, Integer.toString(idUser));
				newElem.setAttribute(ATTR_MANAGER, chef);
				newElem.setAttribute(ATTR_ARCHI, "false");
				newElem.setAttribute(ATTR_ANALYST, "false");
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
		}		
	}
	
	public void addUser(String projectName, int idUser, String chef, String archi)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		
		UsersParser user = new UsersParser();
		
		if(!user.isUser(idUser))
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
				newElem.setAttribute(ATTR_IDUSER, Integer.toString(idUser));
				newElem.setAttribute(ATTR_MANAGER, chef);
				newElem.setAttribute(ATTR_ARCHI, archi);
				newElem.setAttribute(ATTR_ANALYST, "false");
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
		}		
	}
	
	public void addUser(String projectName, int idUser, String chef, String archi, String analyste)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		
		UsersParser user = new UsersParser();
		
		if(!user.isUser(idUser))
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
				newElem.setAttribute(ATTR_IDUSER, Integer.toString(idUser));
				newElem.setAttribute(ATTR_MANAGER, chef);
				newElem.setAttribute(ATTR_ARCHI, archi);
				newElem.setAttribute(ATTR_ANALYST, analyste);
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
		}		
	}
	
	public void addUser(String projectName, int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		
		UsersParser user = new UsersParser();
		
		if(!user.isUser(idUser))
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
				newElem.setAttribute(ATTR_IDUSER, Integer.toString(idUser));
				newElem.setAttribute(ATTR_MANAGER, "false");
				newElem.setAttribute(ATTR_ARCHI, "false");
				newElem.setAttribute(ATTR_ANALYST, "false");
				newElem.setAttribute(ATTR_REDACTEUR, "false");
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout de l'utilisateur "+idUser+" impossible!"); 
			}
		}		
	}
	
	public void removeUser(String projectName, int idUser)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		String expr = "//project[@name=\""+projectName+"\"]//user[@id="+idUser+"]";
		UsersParser user = new UsersParser();
		
		if(!user.isUser(idUser))
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
			transformer.transform(new DOMSource(doc), new StreamResult(projectXML));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
