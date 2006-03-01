package xagdop.Parser;

import java.io.File;
import java.io.IOException;
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
import xagdop.Util.ErrorManager;


public class DependenciesParser extends Parser{

	/**Un fichier dependances par projet
	*Donc une Hashmap, avec en clef le nom du projet, et associe un File
	*/
	private HashMap dependencies;
	private String currentProject;
	
	private static DependenciesParser dependenciesInstance = null; 
	
	public static DependenciesParser getInstance() throws IOException
	{
		if(dependenciesInstance==null)
		{
			dependenciesInstance = new DependenciesParser();
		}
		return dependenciesInstance;
	}
	
	private DependenciesParser() throws IOException
	{
		
			CDependencies cdep = new CDependencies();
			dependencies = cdep.getDependenciesFiles();
			
		/*** Pour le debuggage		
			dependencies = new HashMap();
			dependencies.put("Test",new File("xagdop/ressources/XML/dependencies.xml"));
		*/	
	}

	public File getFile(String project) throws NullPointerException
	{
		if((File)dependencies.get(project)!=null)
		{
			return (File)dependencies.get(project);
		}
		else
		{
			ErrorManager.getInstance().setErrMsg("Projet "+ project + " inexistant \n");
			ErrorManager.getInstance().setErrTitle("Projet inexistant");
			throw new NullPointerException();
		}	
		
	}
	
	public void setFile(String project) throws NullPointerException, Exception
	{
			currentProject = project;
			if((File)dependencies.get(project)!=null)
			{
				loadTreeInMemory((File)dependencies.get(project));			
			}
			else
			{
				ErrorManager.getInstance().setErrMsg("Projet "+ project + " inexistant \n");
				ErrorManager.getInstance().setErrTitle("Projet inexistant");
				throw new NullPointerException();
			}		
	}
	
	public ArrayList getPogFromApes(String apesName) throws XPathExpressionException, NullPointerException
	{
		ArrayList pogList = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";

		Element apesNode = null;
		NodeList pogNodeList;
			
		try {
			apesNode = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
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
			ErrorManager.getInstance().setErrTitle("Fichier Apes Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier Apes "+ apesName +" est inconnu\n");
			throw new NullPointerException();
		}			
		
		return pogList;			
	}
	
	
	public ArrayList getApesFromIepp(String ieppName) throws XPathExpressionException, NullPointerException
	{
		ArrayList apesList = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[iepp[@fileNameIepp=\""+ieppName+"\"]]";

		NodeList apesNodeList = null;
			
		try {
			apesNodeList = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if(apesNodeList!=null){
			Element apes = null;
			NamedNodeMap mapAttributes;
			for (int i=0; i<apesNodeList.getLength(); i++)
			{
				apes = (Element)apesNodeList.item(i);
				mapAttributes = apes.getAttributes();
				if(mapAttributes != null){					
					apesList.add(mapAttributes.getNamedItem("fileNameApes").getNodeValue());
				}
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier IEPP Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier IEPP "+ ieppName +" est inconnu\n");
			throw new NullPointerException();
		}			

		return apesList;			
	}

		
	public String getApesFromPog(String pogName) throws XPathExpressionException, NullPointerException
	{
	
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[pog[@fileNamePog=\""+pogName+"\"]]";

		Node apesNode = null;
			
		try {
			apesNode = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if(apesNode!=null){
			NamedNodeMap mapAttributes;
			mapAttributes = apesNode.getAttributes();
			if(mapAttributes != null){					
				return (mapAttributes.getNamedItem("fileNameApes").getNodeValue());
			}
		}
		else {
			ErrorManager.getInstance().setErrTitle("Fichier POG Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier POG "+ pogName +" est inconnu\n");
			throw new NullPointerException();
		}			

		return null;
	}
	
	
	public ArrayList getIeppFromApes(String apesName) throws XPathExpressionException, NullPointerException
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
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
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
			ErrorManager.getInstance().setErrTitle("Fichier Apes Inconnu");
			ErrorManager.getInstance().setErrMsg("Le fichier Apes "+ apesName +" est inconnu\n");
			throw new NullPointerException();
		}			
		
		
		return ieppList;			
	}
	
	public ArrayList getIeppFromPog(String pogName) throws XPathExpressionException
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
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
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
			
		
		return ieppList;			
	}
	
	public boolean isToUpdate(String filePath) throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toUpdate/file[@path=\""+filePath+"\"]";
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
		else {
			return false;
		}
	}

	public boolean isApes(String apesName) throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element apes = null;
		try {
			apes = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( apes != null ) {			
				return true;		
		}
		else {
			return false;
		}
	}
	
	public void addApes(String apesName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
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
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}

			if ( elem != null ) {
				elem.appendChild(newElem);
				
				expression = "//apes[@fileNameApes='"+ apesName +"']";
				newElem = null;
				try {
					newElem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
				}
				catch (XPathExpressionException e) {
					ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
					ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
					throw new XPathExpressionException(expr_test);
				}
				if(newElem!=null)
				{
					Element elemOnServer = doc.createElement("onServer");	
					newElem.appendChild(elemOnServer);
				}
				
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
			
		}
	}	

	public void addApes(String apesName, boolean onServer) throws Exception, NullPointerException
	{		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		
		if(test_elem == null)
		{
			String expression = "//dependencies";
			Node racine = null;
			try {
					racine = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}

			if ( racine != null ) {

				Element newApes = doc.createElement("apes");
				newApes.setAttribute("fileNameApes", apesName);	
				racine.appendChild(newApes);
				
				if(onServer){
					expression = "//apes[@fileNameApes='"+ apesName +"']";
					racine = null;
					try {
						racine = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
					}
					catch (XPathExpressionException e) {
						ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
						ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
						throw new XPathExpressionException(expr_test);
					}
					if(racine!=null)
					{
						Element elemOnServer = doc.createElement("onServer");	
						racine.appendChild(elemOnServer);
					}
				}
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
			else
			{
				ErrorManager.getInstance().setErrTitle("Pb Racine du fichier");
				ErrorManager.getInstance().setErrMsg("Fichier XML invalide, contacter rapidement l'administrateur.\n");
				throw new NullPointerException();
			}
			
		}
	}	

	public void setApesOnServer(boolean server, String apesName) throws Exception,NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element apes = null;		
		try {
			apes = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		
		if(apes!=null)
		{
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//apes[@fileNameApes=\""+apesName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if(server)
			{
				if(onServer == null)
				{
				Element elemOnServer = doc.createElement("onServer");	
				apes.appendChild(elemOnServer);
				}
			}
			else
			{				
				if(onServer != null)
				{
					apes.removeChild(onServer);
				}
			}
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Apes inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Apes : "+ apesName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}
	

	public boolean getApesOnServer(String apesName) throws Exception,NullPointerException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]";
		Element apes = null;		
		try {
			apes = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		
		if(apes!=null)
		{
			xpath = XPathFactory.newInstance().newXPath();
			
			expression = "//apes[@fileNameApes=\""+apesName+"\"]/onServer";
			Element onServer = null;		
			try {
				onServer = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if(onServer != null)
			{
				return true;
			}
			else
			{				
				return false;
			}
		}
		else
		{
			ErrorManager.getInstance().setErrTitle("Pb Apes inconnu");
			ErrorManager.getInstance().setErrMsg("Fichier Apes : "+ apesName +" inconnu.\n");
			throw new NullPointerException();
		}		
	}

	
	public void addPog(String pogName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
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
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}

			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));
				publish((File)dependencies.get(currentProject));
			}
			
		}
	}	
	
	public void addToUpdate(String filePath) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//file[@path=\""+filePath+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		if(test_elem == null)
		{
			String expression = "//toUpdate";
		
			Element elem = null;
			Element newElem = doc.createElement("file");
			newElem.setAttribute("path", filePath);
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
		}
		
	}		
	
	public void addToCreate(String filePath) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//file[@path=\""+filePath+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
		}
		if(test_elem == null)
		{
			String expression = "//toCreate";

			Element elem = null;
			Element newElem = doc.createElement("file");
			newElem.setAttribute("path", filePath);
			
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
		}
		
	}		

	
	public void addPog(String apesName, String pogName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		String expr_test = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]/pog[@fileNamePog=\""+pogName+"\"]";
		Element test_elem = null;		
		try {
			test_elem = (Element)xpath.evaluate(expr_test, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
			throw new XPathExpressionException(expr_test);
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
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expr_test +" Incorrecte");
				throw new XPathExpressionException(expr_test);
			}
			
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
				publish((File)dependencies.get(currentProject));
			}
			
		}

	}		

	
	public void addIeppToApes(String apesName, String ieppName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//apes[@fileNameApes=\""+apesName+"\"]/iepp[@fileNameIepp=\""+ieppName+"\"]";
		Node iepp = null;
		
		try {
			iepp = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if(iepp == null)
		{
			Node apes = null;
			Element newElem = doc.createElement("iepp");
			newElem.setAttribute("fileNameIepp", ieppName);	
			try {
				apes = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			
			if ( apes != null ) {
				apes.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
			}
		}
	}
	
	public void addIeppToPog(String pogName, String ieppName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/pog[@fileNamePog=\""+pogName+"\"]/iepp[@fileNameIepp=\""+ieppName+"\"]";
		Node iepp = null;
		
		try {
			iepp = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if(iepp == null)
		{
			Element elem = null;
			Element newElem = doc.createElement("iepp");
			newElem.setAttribute("fileNameIepp", ieppName);	
			try {
				elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			}
			catch (XPathExpressionException e) {
				ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
				ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
				throw new XPathExpressionException(expression);
			}
			
			if ( elem != null ) {
				elem.appendChild(newElem);
				saveDocument((File)dependencies.get(currentProject));	
			}
		}
	}
	
	public void delToUpdate(String filePath) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toUpdate";
		String expr = "//toUpdate/file[@path=\""+filePath+"\"]";
				
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
	}

	public void delToCreate(String filePath) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toCreate";
		String expr = "//toCreate/file[@path=\""+filePath+"\"]";
				
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
	}

	
	public void delApes(String filePath) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies";
		String expr = "//apes[@fileNameApes=\""+filePath+"\"]";
				
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
	}
	
	public void delPog(String filePath) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/apes";
		String expr = "//apes/pog[@fileNamePog=\""+filePath+"\"]";
				
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
		if ( elem != null && oldElem != null) {
			elem.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
	}
	
	
	public void delIepp(String filePath, String apesName) throws Exception
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//dependencies/apes[@fileNameApes=\""+apesName+"\"]";
		String expr = "//apes[@fileNameApes=\""+apesName+"\"]/iepp[@fileNameIepp=\""+filePath+"\"]";
				
		Node apes = null;
		Element oldElem = null;
		
		try {
			apes = (Node)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
			oldElem = (Element)xpath.evaluate(expr, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( apes != null && oldElem != null) {
			apes.removeChild(oldElem);
			saveDocument((File)dependencies.get(currentProject));	
			publish((File)dependencies.get(currentProject));
		}
	}

	
	public void addFile (String projectName, File file){
		dependencies.put(projectName, file);
	}
	public void refreshFiles () throws IOException{
		CDependencies cdep = new CDependencies();
		dependencies = cdep.getDependenciesFiles();
	}
	
	public ArrayList getAllToUpdate() throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toUpdate/file";
		NodeList allToUpdate = null;
		ArrayList list = new ArrayList();
		
		try {
			allToUpdate = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( allToUpdate != null ) {
			for(int i=0; i<allToUpdate.getLength(); i++)
			{				
				list.add(allToUpdate.item(i).getAttributes().getNamedItem("path").getNodeValue());
			}
		}
		return list;
		
	}
	
	public ArrayList getAllToCreate() throws XPathExpressionException
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//toCreate/file";
		NodeList allToCreate = null;
		ArrayList list = new ArrayList();
		
		try {
			allToCreate = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			ErrorManager.getInstance().setErrTitle("Expression XPath Incorrecte");
			ErrorManager.getInstance().setErrMsg("Expression XPath "+ expression +" Incorrecte");
			throw new XPathExpressionException(expression);
		}
		if ( allToCreate != null ) {
			for(int i=0; i<allToCreate.getLength(); i++)
			{				
				list.add(allToCreate.item(i).getAttributes().getNamedItem("path").getNodeValue());
			}
		}
		return list;
		
	}
	
}

