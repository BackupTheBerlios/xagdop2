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

import xagdop.Svn.SvnCommit;

public class DependenciesParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private File dependenciesXML;
	
	private static DependenciesParser dependenciesInstance = null; 
	
	public static DependenciesParser getInstance()
	{
		if(dependenciesInstance==null)
		{
			dependenciesInstance = new DependenciesParser();
		}
		return dependenciesInstance;
	}
	
	private DependenciesParser()
	{
		try {
			/*SvnUpdate svnu = new SvnUpdate();
			if((dependenciesXML = svnu.getProjectFile())==null)
				System.out.println("Erreur");*/
			dependenciesXML = new File("xagdop/Parser/dependencies.xml");	//debug Attention ? ne pas le commit
			loadTreeInMemory(dependenciesXML);
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
	
	public void refresh()
	{
		try {
			loadTreeInMemory(dependenciesXML);
		} catch (Exception e) {
		}
	}
	
	public String getApesName(String projectName, String pogName)
	{
		
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]/*/*/pog[@fileNamePog=\""+pogName+"\"]";

		Element pogNode = null;
		
		try {
			pogNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			if(pogNode != null){
				Element nodeApes = (Element)pogNode.getParentNode();
				return nodeApes.getAttribute("fileNameApes");
			}
			else {
				System.out.println("Pas de fichier pog trouve!");
			}
			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		return null;
		
		
				
	}
	
	public String getPogName(String projectName, String preName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]/*/*/*/pre[@fileNamePre=\""+preName+"\"]";
		
		Element preNode = null;
			
		try {
			preNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			if(preNode != null){
				Element nodePog = (Element)preNode.getParentNode();
				return nodePog.getAttribute("fileNamePog");
			}
			else {
				System.out.println("Pas de fichier pre trouve!");
			}
			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		return null;
				
	}
	
	public void addProject(String projectName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//";

		Element elem = null;
		Element newElem = doc.createElement("project");
			
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}
		if ( elem != null ) {
				newElem.setAttribute("name", projectName);
				saveDocument();
			}
		else {
				System.out.println("Ajout du projet impossible!"); 
		}
	}		

	
	public void addApes(String projectName, String apesName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]";

		Element elem = null;
		Element newElem = doc.createElement("apes");
			
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}
		//Il faut que le projet existe
		if ( elem != null ) {
				newElem.setAttribute("fileNameApes", apesName);
				saveDocument();
			}
		else {
				System.out.println("Ajout du fichier Apes impossible!"); 
		}
	}		
	
	public void addPog(String projectName, String apesName, String pogName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]/*/apes[@fileNameApes=\""+apesName+"\"]";

		Element elem = null;
		Element newElem = doc.createElement("pog");
			
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}
		//Il faut que le projet existe
		if ( elem != null ) {
				newElem.setAttribute("fileNamePog", pogName);
				saveDocument();
			}
		else {
				System.out.println("Ajout du fichier Pog impossible!"); 
		}
	}		
	
	public void addPre(String projectName, String apesName, String pogName, String preName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//project[@name=\""+projectName+"\"]/*/apes[@fileNameApes=\""+apesName+"\"]/pog[@fileNamePog\""+pogName+"\"]";

		Element elem = null;
		Element newElem = doc.createElement("pre");
			
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}
		//Il faut que le projet existe
		if ( elem != null ) {
				newElem.setAttribute("fileNamePre", preName);
				saveDocument();
			}
		else {
				System.out.println("Ajout du fichier Pre impossible!"); 
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
			transformer.transform(new DOMSource(doc), new StreamResult(dependenciesXML));
			SvnCommit svnc = new SvnCommit();
			svnc.sendFile(dependenciesXML,"");
			loadTreeInMemory(dependenciesXML);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
}
