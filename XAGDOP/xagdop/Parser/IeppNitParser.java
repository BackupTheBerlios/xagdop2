
package xagdop.Parser;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xagdop.Util.ErrorManager;


public class IeppNitParser {
	
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	
	private File ieppNitZip;
	private DataInputStream ieppNitFile;
	private String ieppNitName = "DefinitionProcessus.xml";

	public IeppNitParser(File file) throws Exception {

		this.ieppNitZip = file;
		this.ieppNitFile = findData(ieppNitName);
		
		if( ieppNitFile != null )
		{
			loadTreeInMemory();
		}
	}
	
	private void loadTreeInMemory() throws Exception{
		this.dbf = DocumentBuilderFactory.newInstance();
		this.dbf.setValidating(false);
		try {
			this.db = dbf.newDocumentBuilder();
			this.doc = db.parse(ieppNitFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ErrorManager.getInstance().setErrTitle("Erreur chargement arbre xml");
			ErrorManager.getInstance().setErrMsg("Erreur lors du chargement du fichier xml.\n");
			throw new Exception();
		}
		
	}
	
	/**
	 * Fonction recherchant tous les fichiers Apes references dans le fichier iepp courant
	 * @return une liste de String, qui correspondent aux noms des fichiers Apes
	 * @throws IOException 
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
			ErrorManager.getInstance().setErrTitle("Aucun Composant");
			ErrorManager.getInstance().setErrMsg("Aucun composant dans le fichier iepp.\n");
			throw new NullPointerException();
			
		}		
		return list;
	}


    /**
     * Recherche et ouvre le fichier de nom fileName dans le fichier zip
     */
    private DataInputStream findData(String fileName) throws IOException
    {   
        ZipInputStream zipFile = new ZipInputStream( new FileInputStream(new File(ieppNitZip.getAbsolutePath())));
        ZipEntry zipEntry = zipFile.getNextEntry();
        while( zipEntry != null )
        {
        	
        	if( zipEntry.getName().equals(fileName) )
            {
        		return new DataInputStream( new BufferedInputStream(zipFile) );
                 
            }
            else
            {
                zipEntry = zipFile.getNextEntry();
            }
        }
        zipFile.close();
        return null;
    }



	
}
