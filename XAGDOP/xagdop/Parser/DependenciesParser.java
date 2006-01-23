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

import xagdop.Svn.SvnCommit;
import xagdop.Svn.SvnUpdate;

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
			
			SvnUpdate svnu = new SvnUpdate();
			if((dependenciesXML = svnu.getDependenciesFile())==null)
				System.out.println("Erreur");
				
			//dependenciesXML = new File("xagdop/Parser/dependencies.xml");	//debug Attention ? ne pas le commit
			loadTreeInMemory(dependenciesXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadTreeInMemory(File fichier) throws Exception {
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

	public File getFile()
	{
		return dependenciesXML;
	}
	
	public ArrayList getPogFromApes(String apesName)
	{
		ArrayList pogList = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList pogNodeList;
			
		try {
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			
			if(apesNode != null){
			
			if(apesNode.hasChildNodes()){
				
				Node nodeAll = null;
				Node attributePath = null;
				NamedNodeMap mapAttributes = null;
				
				pogNodeList = apesNode.getChildNodes();
				for (int i=0; i<pogNodeList.getLength(); i++)
				{
					nodeAll = pogNodeList.item(i);
					mapAttributes = nodeAll.getAttributes();
					if(mapAttributes != null){					
						attributePath = mapAttributes.getNamedItem("fileNamePog");
					
						if(attributePath!=null){
							pogList.add(attributePath.getNodeValue());
						}
					}					
				}
			}
			}
			else {
				System.out.println("Pb getPogFromApes");
			}			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		return pogList;			
	}
	
	public ArrayList getPreFromPog(String pogName)
	{
		ArrayList preList = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();

		String expression = "//pog[@fileNamePog=\""+pogName+"\"]";


		Element pogNode = null;
		NodeList preNodeList;
			
		try {
			pogNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			if(pogNode!=null){
				if(pogNode.hasChildNodes()){
					
					Node nodeAll = null;
					Node attributePath = null;
					NamedNodeMap mapAttributes = null;
					
					preNodeList = pogNode.getChildNodes();
					for (int i=0; i<preNodeList.getLength(); i++)
					{
						nodeAll = preNodeList.item(i);
						mapAttributes = nodeAll.getAttributes();
						if(mapAttributes != null){					
							attributePath = mapAttributes.getNamedItem("fileNamePre");
							
							if(attributePath!=null){
								preList.add(attributePath.getNodeValue());
							}
						}					
					}
				}
			}
			else {
				System.out.println("Pb getPreFromPog");
			}			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		return preList;			
	}
	
	public ArrayList getPreFromApes(String apesName)
	{
		ArrayList preList = new ArrayList();
		
		/**
		 * XPath expression to get the correct "apes" node  
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList preNodeList;
		NodeList pogNodeList;
		
		
		
		try {
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			

			/**
			 * If there is "pog" nodes, which are children of the "apes" node   
			 */
			if(apesNode!=null){
				if(apesNode.hasChildNodes()){				
					Node nodePog = null;			
					pogNodeList = apesNode.getChildNodes();
					
					/**
					 * For all children node "pog"  
					 */
					
					for (int i=0; i<pogNodeList.getLength(); i++)
					{
						nodePog = pogNodeList.item(i);
						
						/**
						 * If there is "pre" nodes, which are children of the "pog" node   
						 */
						if(nodePog!=null){
							if(nodePog.hasChildNodes()){					
								Node nodePre = null;
								Node attributePath = null;
								NamedNodeMap mapAttributes = null;
								
								preNodeList = nodePog.getChildNodes();
								/**
								 * For all children node "pre"  
								 */
								for (int j=0; j<preNodeList.getLength(); j++)
								{	
									nodePre = preNodeList.item(j);
									/**
									 * Getting all node's attribute in a NamedNodeMap  
									 */
									mapAttributes = nodePre.getAttributes();
									if(mapAttributes != null){					
										attributePath = mapAttributes.getNamedItem("fileNamePre");							
										if(attributePath!=null){
											/**
											 * Add the selected attribute in the result ArrayList  
											 */
											preList.add(attributePath.getNodeValue());
										}
									}
								}
							}	
						}
					}
				}
			}
			else {
				System.out.println("Pb getPreFromApes");
			}			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		return preList;			
	}
	
	
	public boolean isToUpdate(String filePath)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toupdate/file[@path=\""+filePath+"\"]";
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
			return false;
		}
	}

	public void addApes(String apesName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		
		if(test_elem == null)
		{
			String expression = "//dependencies";

			Element elem = null;
			Element newElem = doc.createElement("apes");
			newElem.setAttribute("fileNameApes", apesName);	
			try {
					elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}

			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument();			
			}
			else {
				System.out.println("Ajout du fichier Apes impossible!"); 
			}
		}
		else
		{
			System.out.println("Fichier existant!");
		}
	}	
	
	public void addToUpdate(String filePath)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//file[@path=\""+filePath+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		
		if(test_elem == null)
		{
		
			String expression = "//toupdate";
			
			Element elem = null;
			Element newElem = doc.createElement("file");
			newElem.setAttribute("path", filePath);
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Pb addToUpdate!"); 
			}
		}
		else
		{
			System.out.println("Fichier existant!");
		}
	}		
	
	public void addPog(String apesName, String pogName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//pog[@fileNamePog=\""+pogName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		
		if(test_elem == null)
		{
		
			String expression = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]";
			
			Element elem = null;
			Element newElem = doc.createElement("pog");
			newElem.setAttribute("fileNamePog", pogName);	
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				
				e.printStackTrace();
			}
			
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument();
			}
			else {
				System.out.println("Ajout du fichier Pog impossible!"); 
			}
		}
		else
		{
			System.out.println("Fichier existant!");
		}
	}		
	
	public void addPre(String apesName, String pogName, String preName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]/pog[@fileNamePog=\""+pogName+"\"]";

		Element elem = null;
		Element newElem = doc.createElement("pre");
		newElem.setAttribute("fileNamePre", preName);	
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}

		if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument();
			}
		else {
				System.out.println("Ajout du fichier Pre impossible!"); 
		}
	}

	public void delToUpdate(String filePath)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toupdate";
		String expr = "//toupdate/file[@path=\""+filePath+"\"]";
				
		Element elem = null;
		Element oldElem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument(); 
		}
		else {
			System.out.println("Pb delToUpdate!"); 
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
