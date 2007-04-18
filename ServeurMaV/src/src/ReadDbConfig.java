package src;



import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;



/**
 * Can read the XML configuration file to extract the ORB configuration
 * or the list of classes that the Server needs
 */
public class ReadDbConfig extends DefaultHandler {

	/**
	 * Log4J
	 */


	/**
	 * config file's path
	 */
	private String configFile = ReadDbConfig.class.getResource("../config/repository_JDBC_connexion.xml").getFile();

	/**
	 * database server ip address
	 */
	private String strDbProtocol = null;
	private String strDbSubProtocol = null;
	private String strDbDriverName = null;
	private String strDbAlias = null;
	/**
	 * login for a readonly use on the database
	 */
	private String strReadOnlyLogin = null;

	/**
	 * password for a readonly use on the database
	 */
	private String strReadOnlyPassword = null;


	// The current element read in the XML Stream
	private String strCurrent = "";

	protected static ReadDbConfig myReadDbConfig = null;

	/**
	 * method used by the parser to extract elements from the XML file
	 *
	 * @param strUri_
	 * @param strLocal_
	 * @param strQName_
	 * @param attributes_
	 */
	public void startElement(String strUri_, String strLocal_, String strQName_,
			Attributes attributes_) {


		if (
				strLocal_.equals("driver.name") ||
				strLocal_.equals("url.protocol") ||
				strLocal_.equals("url.subprotocol") ||
				strLocal_.equals("url.dbalias") ||
				strLocal_.equals("user.name") ||
				strLocal_.equals("user.passwd")
		)
			strCurrent = strLocal_;
		else
			strCurrent = "";
	}

	public void characters(char[] ch, int start, int length) {

		String strRead = "";

		// Si le courant n'est pas vide
		if (!this.strCurrent.equals("")) {
			//Stocker la valeur compris dans le tag
			strRead = String.valueOf(ch, start, length);
			// Si c'est user.name
			if (strCurrent.equals("driver.name"))
				// Sauvegarder la valuer
				strDbDriverName = strRead;

			if (strCurrent.equals("url.protocol"))
				// Sauvegarder la valuer
				strDbProtocol = strRead;

			if (strCurrent.equals("url.subprotocol"))
				// Sauvegarder la valuer
				strDbSubProtocol = strRead;

			if (strCurrent.equals("user.name"))
				// Sauvegarder la valuer
				strReadOnlyLogin = strRead;

			// Si c'est user.password
			else if (strCurrent.equals("user.passwd"))
				// Sauvehgarder la valeur
				strReadOnlyPassword = strRead;
			// Si c'est dbalias
			// Alors analyser la chaine pour decouper
			else if (strCurrent.equals("url.dbalias")) {
				// Trouver le @
				// Decoupez en token ":"
				strDbAlias = strRead;

			}
		}

		strCurrent = "";
	}

	/**
	 * returns a string containing the database server ip address
	 *
	 * @return String, the database server ip address
	 */

	/**
	 * returns a string containing the database readonly use login
	 *
	 * @return String, the database readonly use login
	 */
	public String getReadOnlyLogin(){
		return strReadOnlyLogin;
	}

	/**
	 * returns a string containing the database readonly use password
	 *
	 * @return String, the database readonly use password
	 */
	public String getReadOnlyPassword(){
		return strReadOnlyPassword;
	}

	public String getUrl(){
		return strDbProtocol + ":" + strDbSubProtocol + ":" + strDbAlias ;
	}
	/**
	 * returns a string containing the database port number
	 *
	 * @return String, the database port number
	 */

	public static ReadDbConfig getInstance(){
		if(myReadDbConfig == null)
			return new ReadDbConfig();

		return myReadDbConfig;
	}
	/**
	 * Reads the XML file and extracts the configuration and the classes
	 * that the Server needs
	 */
	private ReadDbConfig() {
		try {

			// Create a Xerces SAX Parser
			SAXParser parser = new SAXParser();

			// Set Content Handler
			parser.setContentHandler(this);

			// Parse the Document
			try {
				parser.parse(configFile);
			}
			catch (SAXException saxException) {
				saxException.printStackTrace();
			}
			catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}


	public String getDbDriverName() {
		return strDbDriverName;
	}

	public String getDbAlias() {
		return strDbAlias;
	}

	public String getDbProtocol() {
		return strDbProtocol;
	}

	public String getDbSubProtocol() {
		return strDbSubProtocol;
	}

}

