
package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class IeppNitParser extends Parser {

	private File ieppnitFile;

	public IeppNitParser(File file) {

		this.ieppnitFile = file;
		
		try {
			loadTreeInMemory(ieppnitFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Fonction recherchant tous les fichiers Apes référencés dans le fichier iepp courant
	 * @return une liste de String, qui correspondent aux noms des fichiers Apes
	 */
	public ArrayList getApes()
	{
		ArrayList list = new ArrayList();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//fichier";
		NodeList apesList = null;
		
		try {
			apesList = (NodeList)xpath.evaluate(expression, this.doc, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			
			e.printStackTrace();
		}
		if ( apesList != null ) {
			Element apes = null;
			for(int i=0; i<apesList.getLength(); i++)
			{
				apes = (Element)apesList.item(i);
				list.add(apes.getTextContent());
			}
		}
		else {
			System.out.println("Pb IeppNitParser dans getApes"); 
		
		}		
		return list;
	}


	
	
}
