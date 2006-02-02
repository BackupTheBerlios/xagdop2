package xagdop.Parser;

import java.io.File;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import xagdop.Model.Preferencies;

public class PreferenciesParser extends Parser{
	private File preferenciesXML;
	private static PreferenciesParser PrfPInstance = null;
	private static String SERV_URL = "url";
	private static String LNF_NAME = "lnf";
	private static String LANG_NAME = "langue";
	
	
	private PreferenciesParser()
	{
		try {
			preferenciesXML = new File("xagdop/ressources/XML/preferencies.xml");
			loadTreeInMemory(preferenciesXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static PreferenciesParser getInstance() {
		if (PrfPInstance == null)
			PrfPInstance = new PreferenciesParser();
		return PrfPInstance;
	}
	
	/**
	 * Construit un objet Preferencies a partir du fichier XML
	 * @return L'objet Preferencies
	 */
	public Preferencies buildPreferencies(){
		String server="";
		String lnf="";
		String lang="";
		Element elem=null;
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		//recuperation de l'URL du serveur
		String expression = "//server";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			server= elem.getAttribute(PreferenciesParser.SERV_URL);
		}
		
		//recuperation du LookNFeel
		expression = "//lookNFeel";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			lnf = elem.getAttribute(PreferenciesParser.LNF_NAME);
		}
		
		//recuperation de la langue
		expression = "//langue";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			lang= elem.getAttribute(PreferenciesParser.LANG_NAME);
		}
		

		Preferencies pref = new Preferencies(server,lnf,lang);
		return pref;
	}
	

	
	
}
