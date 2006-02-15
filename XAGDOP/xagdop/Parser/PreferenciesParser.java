package xagdop.Parser;

import java.io.File;
import java.util.Locale;

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
	private static String LOCAL_URL = "url";
	private static String LNF_NAME = "name";
	private static String LANG_NAME = "name";
	
	
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
		String local="";
		String lnf="";
		Locale lang = Locale.getDefault();

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
		
		//recuperation de l'URL locale
		expression = "//local";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			local= elem.getAttribute(PreferenciesParser.LOCAL_URL);
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
			lang = new Locale(elem.getAttribute(PreferenciesParser.LANG_NAME));
		}
		
		Preferencies pref = new Preferencies(server,local,lnf,lang);
		return pref;
	}
	
	
	public void setPreferencies(Preferencies pref) throws Exception{
		String serv= pref.getServer();
		String local= pref.getLocal();
		String lnf= pref.getLookNFeel();
		String lang= pref.getLang().getLanguage();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		String exprServ = "//server";
		String exprLoc = "//local";
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
		
		try { elem = (Element)xpath.evaluate(exprLoc, this.doc, XPathConstants.NODE);}
		catch (XPathExpressionException e) { e.printStackTrace();}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LOCAL_URL, local);
		}
		else {
			System.out.println("Modification de l'URL locale a "+ local + " impossible!"); 
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
	
	
	public void setServer(String serv) throws Exception{
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

	
	
	
	public void setLocalPath(String localPath) throws Exception{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//local";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LOCAL_URL, localPath);
			saveDocument(preferenciesXML);
		}
		else {
			System.out.println("Modification de l'URL locale a "+ localPath + " impossible!"); 
		}
	}
	
	
	public void setLNF(String lnf) throws Exception{
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

	
	
	public void setLang(Locale lang) throws Exception{
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "//langue";
		Element elem = null;
		
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			elem.setAttribute(PreferenciesParser.LANG_NAME, lang.getLanguage());
			saveDocument(preferenciesXML);
		}
		else {
			System.out.println("Modification de la langue a "+ lang.getLanguage() + " impossible!"); 
		}
	}

	
	public String getServer(){
		String server = "";
		Element elem=null;
		XPath xpath = XPathFactory.newInstance().newXPath();

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
		return server;
	
	}
	
	public String getLocalPath(){
		String local = "";
		Element elem=null;
		XPath xpath = XPathFactory.newInstance().newXPath();		
		
		String expression = "//local";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			local= elem.getAttribute(PreferenciesParser.LOCAL_URL);
		}
		
		return local;
	}
	
	public Locale getLang(){
		Locale lang = Locale.getDefault();
		Element elem=null;
		XPath xpath = XPathFactory.newInstance().newXPath();		
		
		String expression = "//langue";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			lang = new Locale(elem.getAttribute(PreferenciesParser.LANG_NAME));
		}
		
		return lang;
	}
	
	public String getLNF(){
		String lnf = "";
		Element elem=null;
		XPath xpath = XPathFactory.newInstance().newXPath();		
		
		String expression = "//lookNFeel";
		try {
			elem = (Element)xpath.evaluate(expression, this.doc, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		if ( elem != null ) {
			lnf = elem.getAttribute(PreferenciesParser.LNF_NAME);
		}
		
		return lnf;
	}
	
	
}
