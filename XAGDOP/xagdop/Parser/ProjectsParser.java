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

import xagdop.Model.Projects;
import xagdop.Model.Users;
import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnUpdate;

public class ProjectsParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private File projectXML;
	
	public static final String ATTR_ARCHI = "archi";
	public static final String ATTR_ANALYST = "analyste";
	public static final String ATTR_REDACTEUR = "redacteur";	
	public static final String ATTR_MANAGER = "pmanager";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_URLREPO = "urlRepo";
	public static final String ATTR_IDUSER = "id";	
	public static final String ATTR_DESC = "desc";
	
	public ProjectsParser()
	{
		try {
			SvnUpdate svnu = new SvnUpdate();
			if((projectXML = svnu.getProjectFile())==null)
				System.out.println("Erreur");
			chargerArbreEnMemoire(projectXML);
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
	
	public void refresh()
	{
		try {
			chargerArbreEnMemoire(projectXML);
		} catch (Exception e) {
			//System.out.println("CACA");
		}
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
			System.out.println("R??cup??ration de l'attribut "+ attr + " impossible!"); 
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

			System.out.println("R??cup??ration de l'attribut "+ attr + " pour l'utilisateur "+idUser+" impossible!"); 
			return Boolean.FALSE;
		}		
	}
	
	public boolean exist(String pName, String login)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		UsersParser users = new UsersParser();
		int id = users.getId(login);
		
		String expression = "//project[@name=\""+pName+"\"]//user[@id="+id+"]";
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
	
	public boolean addUser(String projectName, Users user, boolean chef, boolean archi, boolean analyste, boolean redacteur)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";
		
		UsersParser userP = new UsersParser();
		
		if(!userP.isUser(user.getId()))
		{
			//System.out.println("L'utilisateur n'existe pas!!");
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
				newElem.setAttribute(ATTR_IDUSER, Integer.toString(user.getId()));
				newElem.setAttribute(ATTR_MANAGER, Boolean.toString(chef));
				newElem.setAttribute(ATTR_ARCHI, Boolean.toString(archi));
				newElem.setAttribute(ATTR_ANALYST, Boolean.toString(analyste));
				newElem.setAttribute(ATTR_REDACTEUR, Boolean.toString(redacteur));
				elem.appendChild(newElem);
				saveDocument();
				return true;
			}
			else {
				System.out.println("Ajout de l'utilisateur "+user.getLogin()+" impossible!");
				return false;
			}
		}
	}
	
	public boolean addProject(String projectName, Users user, String description)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		
		UsersParser userP = new UsersParser();
		
		if(!userP.isUser(user.getId()))
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
				System.out.println("Suppression de l'utilisateur "+idUser+" effectu??e!"); 
			}
			else {
				System.out.println("Suppression de l'utilisateur "+idUser+" impossible!"); 
			}
		}		
		
	}
	
	public void removeProject(String projectName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//*";
		String expr = "//project[@name=\""+projectName+"\"]";
		
		
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
			System.out.println("Suppression du projet "+projectName+" effectu??e!"); 
		}
		else {
			System.out.println("Suppression du projet "+projectName+" impossible!"); 
		}
		
		
	}
	
	public Projects getAllUsers(String pName)
	{
		ArrayList usersList = new ArrayList();
		ArrayList userRights;
		ArrayList usersId = new ArrayList();
		
		//Projects projet;
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+pName+"\"]";
		int num = 0;
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
				Node nodeN = null;
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
						
						nodeN = map.getNamedItem(ATTR_IDUSER);
						nodeAn = map.getNamedItem(ATTR_ANALYST);
						nodeMan = map.getNamedItem(ATTR_MANAGER);
						nodeArchi = map.getNamedItem(ATTR_ARCHI);
						nodeRedac = map.getNamedItem(ATTR_REDACTEUR);
						
						if(nodeN!=null){		
							
							num = Integer.parseInt(nodeN.getNodeValue());			
							
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
							
							usersId.add(new Integer(num));
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
		return new Projects(pName, usersList, usersId);
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
			SvnCommit svnc = new SvnCommit();
			svnc.sendFile(projectXML);
			chargerArbreEnMemoire(projectXML);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
