package xagdop.Parser;

import java.io.File;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;

public class POGParser extends Parser{

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
			String path = elem.getTextContent();
			path = path.substring(4);
			return path;
		}
		else {
			//System.out.println("Recuperation du chemin acces au fichier apes associe impossible!"); 
			return null;
		}		
	}
	
	public void setApesPathToRelative(String pathRelative) throws Exception
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
			saveDocument(pogFile);
		}
		else {
			//System.out.println("Pb setApesPathToRelative!"); 
		}	
	}
	

	
}
