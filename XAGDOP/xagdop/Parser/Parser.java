package xagdop.Parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.tmatesoft.svn.core.SVNException;
import org.w3c.dom.Document;

import xagdop.Svn.SvnCommit;
import xagdop.Util.ErrorManager;

public class Parser {

	protected DocumentBuilderFactory dbf;

	protected DocumentBuilder db;

	protected Document doc;

	protected void loadTreeInMemory(File file) throws Exception{
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		try {
			this.db = dbf.newDocumentBuilder();
			this.doc = db.parse(file);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ErrorManager.getInstance().setErrTitle("Erreur chargement arbre xml");
			ErrorManager.getInstance().setErrMsg("Erreur lors du chargement du fichier xml "+ file.getName()+".\n");
			throw new Exception();
		}
		
	}
	
	protected void refresh(File file) throws Exception
	{
			loadTreeInMemory(file);
	}
	
	protected void saveDocument(File file) throws Exception
	{	
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(file));
		}
		catch (Exception e) {
			ErrorManager.getInstance().setErrTitle("Enregistrement du XML impossible");
			ErrorManager.getInstance().setErrMsg("Enregistrement du XML impossible.\n");
			throw new Exception();
		}
	
	}
	
	public void publish(File file) throws SVNException, Exception {
		SvnCommit svnc;	
			svnc = new SvnCommit();
			svnc.sendFile(file,"");	
			loadTreeInMemory(file);
		
	}

}
