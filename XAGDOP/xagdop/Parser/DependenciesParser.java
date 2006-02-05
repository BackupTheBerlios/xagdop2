package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xagdop.Controleur.CDependencies;


public class DependenciesParser extends Parser{



	/**Un fichier dépendances par projet
	*Donc une Hashmap, avec en clef le nom du projet, et associé un File
	*/
	private HashMap dependencies;
	private String currentProject;
	
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
			
			//CDependencies cdep = new CDependencies();
			//dependencies = cdep.getDependenciesFiles();
			
			/*** Pour le debuggage*/ 
			dependencies = new HashMap();
			dependencies.put("Test",new File("xagdop/ressources/XML/dependencies.xml"));
			/**/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public File getFile(String project)
	{
		return (File)dependencies.get(project);
		//return dependenciesXML;
	}
	
	public void setFile(String project)
	{
		try {
			currentProject = project;
			loadTreeInMemory((File)dependencies.get(project));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/*
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
	*/
	
	public ArrayList getIeppFromApes(String apesName)
	{
		ArrayList ieppList = new ArrayList();
		
		/**
		 * XPath expression to get the correct "apes" node  
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList allNodeList;
	
		try {
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
	
			/**
			 * If there is children node of the "apes" node   
			 */
			if(apesNode!=null){
				if(apesNode.hasChildNodes()){				
					Node nodeChild = null;			
					allNodeList = apesNode.getChildNodes();				
					/**
					 * For all children node
					 */					
					for (int i=0; i<allNodeList.getLength(); i++)
					{
						nodeChild = allNodeList.item(i);					
						/**
						 * If it is a "iepp" nodes   
						 */
						if(nodeChild!=null){
							if(nodeChild.getNodeName().equals("iepp")){					
								NamedNodeMap mapAttributes = null;
								/**
								 * Getting all node's attribute in a NamedNodeMap  
								 */
								mapAttributes = nodeChild.getAttributes();
								if(mapAttributes != null){					
									String fileName = mapAttributes.getNamedItem("fileNameIepp").getNodeValue();	
									ieppList.add(fileName);
								}
							}	
						}
					}
				}
			}
			else {
				System.out.println("Pb getIeppFromApes");
			}			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		//System.out.println(ieppList.toString());
		return ieppList;			
	}
	
	public ArrayList getIeppFromPog(String pogName)
	{
		ArrayList ieppList = new ArrayList();
		
		/**
		 * XPath expression to get the correct "pog" node  
		 */
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";

		Element pogNode = null;
		NodeList allNodeList;
	
		try {
			pogNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
	
			/**
			 * If there is children node of the "pog" node   
			 */
			if(pogNode!=null){
				if(pogNode.hasChildNodes()){				
					Node nodeChild = null;			
					allNodeList = pogNode.getChildNodes();				
					/**
					 * For all children node
					 */					
					for (int i=0; i<allNodeList.getLength(); i++)
					{
						nodeChild = allNodeList.item(i);					
						/**
						 * If it is a "iepp" nodes   
						 */
						if(nodeChild!=null){
							if(nodeChild.getNodeName().equals("iepp")){					
								NamedNodeMap mapAttributes = null;
								/**
								 * Getting all node's attribute in a NamedNodeMap  
								 */
								mapAttributes = nodeChild.getAttributes();
								if(mapAttributes != null){					
									String fileName = mapAttributes.getNamedItem("fileNameIepp").getNodeValue();	
									ieppList.add(fileName);
								}
							}	
						}
					}
				}
			}
			else {
				System.out.println("Pb getIeppFromPog");
			}			
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		//System.out.println(ieppList.toString());
		return ieppList;			
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
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
			else {
				System.out.println("Ajout du fichier Apes impossible!"); 
			}
		}
		else
		{
			//System.out.println("Fichier existant!");
		}
	}	
	
	public void addPog(String pogName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";
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
				saveDocument((File)dependencies.get(currentProject));
				publish((File)dependencies.get(currentProject));
			}
			else {
				System.out.println("Ajout du fichier Pog sans modele impossible!"); 
			}
		}
		else
		{
			//System.out.println("Fichier existant!");
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
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
			else {
				System.out.println("Pb addToUpdate!"); 
			}
		}
		else
		{
			//System.out.println("Fichier existant!");
		}
	}		
	
	public void addPog(String apesName, String pogName) 
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]/pog[@fileNamePog=\""+pogName+"\"]";
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
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
			else {
				System.out.println("Ajout du fichier Pog impossible!"); 
			}
		}
		else
		{
			//System.out.println("Fichier existant!");
		}
	}		
	/*
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
				saveDocument((File)dependencies.get(currentProject));	
			}
		else {
				System.out.println("Ajout du fichier Pre impossible!"); 
		}
	}
*/
	
	public void addIeppToApes(String apesName, String ieppName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element elem = null;
		Element newElem = doc.createElement("iepp");
		newElem.setAttribute("fileNameIepp", ieppName);	
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}

		if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
			}
		else {
				System.out.println("Pb addIeppToApes!"); 
		}
	}
	
	public void addIeppToPog(String pogName, String ieppName)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";

		Element elem = null;
		Element newElem = doc.createElement("iepp");
		newElem.setAttribute("fileNameIepp", ieppName);	
		try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
				
				e.printStackTrace();
		}

		if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
			}
		else {
				System.out.println("Pb addIeppToPog!"); 
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
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
		else {
			System.out.println("Pb delToUpdate!"); 
		}		
	}
	
	
	
	
}
