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

import org.tmatesoft.svn.core.SVNException;
import org.w3c.dom.Document;

import xagdop.Svn.SvnCommit;

public class Parser {

	protected DocumentBuilderFactory dbf;

	protected DocumentBuilder db;

	protected Document doc;

	protected void loadTreeInMemory(File file) throws Exception {
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		this.db = dbf.newDocumentBuilder();
		this.doc = db.parse(file);
	}
	
	protected void refresh(File file)
	{
		try {
			loadTreeInMemory(file);
		} catch (Exception e) {
		}
	}
	
	protected void saveDocument(File file)
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
			transformer.transform(new DOMSource(doc), new StreamResult(file));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	protected void publish(File file){
		SvnCommit svnc;
		try {
			svnc = new SvnCommit();
			svnc.sendFile(file,"");
		} catch (SVNException e1) {
			// TODO Auto-generated catch block
			System.out.println("Pb de connexion");
			e1.printStackTrace();
		}	
		try {
			loadTreeInMemory(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Pb de chargement en memoire");
			e.printStackTrace();
		}
	}

}
