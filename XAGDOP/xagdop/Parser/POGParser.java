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

public class POGParser {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private File pogFile;
	
	public POGParser(File fichier) {
		pogFile = fichier;
		try {
			loadTreeInMemory(pogFile);
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
			loadTreeInMemory(pogFile);
		} catch (Exception e) {
			//System.out.println("CACA");
		}
	}
	
	public String getPathApesFile()
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//proprietes/chemin_icones";
		Element elem = null;
		
		//System.out.println(expression);
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( elem != null ) {
			return elem.getTextContent();
		}
		else {
			System.out.println("Recuperation du chemin acces au fichier apes associe impossible!"); 
			return null;
		}		
	}
	
	public void setApesPathToRelative(String pathRelative)
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//proprietes/chemin_icones";
		Element path = null;
		
		try {
			path = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if(path!=null)
		{
			path.setTextContent(pathRelative);
			saveDocument();
		}
		else {
			System.out.println("Pb setApesPathToRelative!"); 
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
			transformer.transform(new DOMSource(doc), new StreamResult(pogFile));
			//SvnCommit svnc = new SvnCommit();
			//svnc.sendFile(dependenciesXML,"");
			loadTreeInMemory(pogFile);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
