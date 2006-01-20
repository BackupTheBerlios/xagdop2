package xagdop.Parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
			chargerArbreEnMemoire(pogFile);
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
			chargerArbreEnMemoire(pogFile);
		} catch (Exception e) {
			//System.out.println("CACA");
		}
	}
	
	public String getPathApesFile()
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//proprietes/chemin_icones";
		Element elem = null;
		
		System.out.println(expression);
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
	
}
