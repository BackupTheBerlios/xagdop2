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
	
	
	public void setPreferencies(Preferencies pref){
		String serv= pref.getServer();
		String lnf= pref.getLookNFeel();
		String lang= pref.getLangue();		
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String exprServ = "//server";
		String exprLNF  = "//lookNFeel";
		String exprLang = "//langue";	
		Element elem = null;
		
		try { elem = (Element)xpath.evaluate(exprServ, this.doc, XPathConstants.NODE);}
		catch (XPathExpressionException e) { e.printStackTrace();}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.SERV_URL, serv);
		}
		else {
			System.out.println("Modification de l'URL du serveur a "+ serv + " impossible!"); 
		}
		
		try { elem = (Element)xpath.evaluate(exprLNF, this.doc, XPathConstants.NODE);}
		catch (XPathExpressionException e) { e.printStackTrace();}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LNF_NAME, lnf);
		}
		else {
			System.out.println("Modification du lnf a "+ lnf + " impossible!"); 
		}
		
		try { elem = (Element)xpath.evaluate(exprLang, this.doc, XPathConstants.NODE); }
		catch (XPathExpressionException e) { e.printStackTrace(); }
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LANG_NAME, lang);
		}
		else {
			System.out.println("Modification de la langue a "+ lang + " impossible!"); 
		}
		saveDocument(preferenciesXML);
		
	}
	
	
	public void setServer(String serv){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//server";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.SERV_URL, serv);
			saveDocument(preferenciesXML);
		}
		else {
			System.out.println("Modification de l'URL du serveur a "+ serv + " impossible!"); 
		}
	}

	
	
	public void setLNF(String lnf){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//lookNFeel";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LNF_NAME, lnf);
			saveDocument(preferenciesXML);
		}
		else {
			System.out.println("Modification du lnf a "+ lnf + " impossible!"); 
		}
	}

	
	
	public void setLang(String lang){
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//server";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LANG_NAME, lang);
			saveDocument(preferenciesXML);
		}
		else {
			System.out.println("Modification de la langue a "+ lang + " impossible!"); 
		}
	}

}
