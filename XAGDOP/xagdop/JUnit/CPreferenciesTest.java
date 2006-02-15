package xagdop.JUnit;

import java.util.Locale;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;

import xagdop.Controleur.CPreferencies;
import xagdop.Model.Preferencies;
import xagdop.Parser.PreferenciesParser;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class CPreferenciesTest extends TestCase {


	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getLocalPath()'
	 */
	public void testGetLocalPath() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String localTest = "/test/local";
		CPreferencies.setLocalPath(localTest);
		assertEquals(CPreferencies.getLocalPath(),localTest);
		assertNotSame(CPreferencies.getLocalPath(),"PIRATE");
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setLocalPath(String)'
	 */
	public void testSetLocalPath() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();		
		
		//corps du test
		final String localTest = "/test/local";
		final String localTest2 = "/test/local2";
		CPreferencies.setLocalPath(localTest);
		assertEquals(CPreferencies.getLocalPath(),localTest);
		CPreferencies.setLocalPath(localTest2);
		assertEquals(CPreferencies.getLocalPath(),localTest2);
		assertNotSame(CPreferencies.getLocalPath(),"PIRATE");
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getServerPath()'
	 */
	public void testGetServerPath() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String serverTest = "/test/server";
		CPreferencies.setServerPath(serverTest);
		assertEquals(CPreferencies.getServerPath(),serverTest);
		assertNotSame(CPreferencies.getServerPath(),"PIRATE");
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setServerPath(String)'
	 */
	public void testSetServerPath() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();		
		
		//corps du test
		final String serverTest = "/test/server";
		final String serverTest2 = "/test/server2";
		CPreferencies.setServerPath(serverTest);
		assertEquals(CPreferencies.getServerPath(),serverTest);
		CPreferencies.setServerPath(serverTest2);
		assertEquals(CPreferencies.getServerPath(),serverTest2);
		assertNotSame(CPreferencies.getServerPath(),"PIRATE");
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getDefaultLNF()'
	 */
	public void testGetDefaultLNF() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String lnfTest = "Metal";
		CPreferencies.setDefaultLNF(lnfTest);
		assertEquals(CPreferencies.getDefaultLNF(),lnfTest);
		assertNotSame(CPreferencies.getDefaultLNF(),"PIRATE");
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setDefaultLNF(String)'
	 */
	public void testSetDefaultLNF() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();
		
		//corps du test
		final String lnfTest = "LNF1";
		final String lnfTest2 = "LNF2";
		CPreferencies.setDefaultLNF(lnfTest);
		assertEquals(CPreferencies.getDefaultLNF(),lnfTest);
		CPreferencies.setDefaultLNF(lnfTest2);
		assertEquals(CPreferencies.getDefaultLNF(),lnfTest2);
		assertNotSame(CPreferencies.getDefaultLNF(),"PIRATE");
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}


	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getDefaultLocale()'
	 */
	public void testGetDefaultLocale() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		//System.out.println("-----testGetDefaultLocale-----");//debug
		final Locale localeTest = Locale.FRENCH;
		CPreferencies.setDefaultLocale(localeTest);
		assertEquals(CPreferencies.getDefaultLocale(),localeTest);
		assertNotSame(CPreferencies.getDefaultLocale(),Locale.ENGLISH);
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setDefaultLocale(String)'
	 */
	public void testSetDefaultLocale() throws Exception {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();		
		
		//corps du test
		//System.out.println("-----testSetDefaultLocale-----");//debug
		final Locale localeTest = Locale.FRENCH;
		final Locale localeTest2 = Locale.ENGLISH;
		CPreferencies.setDefaultLocale(localeTest);
		assertEquals(CPreferencies.getDefaultLocale(),localeTest);
		CPreferencies.setDefaultLocale(localeTest2);
		assertEquals(CPreferencies.getDefaultLocale(),localeTest2);
		assertNotSame(CPreferencies.getDefaultLocale(),localeTest);
		
		//restauration du contexte
		PreferenciesParser.getInstance().setPreferencies(savePref);
	}


	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.submitPasswd(String, String, String)'
	 */
	public void testSubmitPasswd() {
		String login="essaiPassWd";
		String oldPass="PassWd";
		String newPass="NewPassWd";
		String checkPass="";
		
		//creation d'un nouvel utilisateur
		try {
			UsersParser.getInstance().addUser(login,oldPass);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//tentative changement de mot de passe incorect
		assertFalse(CPreferencies.submitPasswd(login,"wrongPass",newPass));
		
		//changement de son mot de passe
		assertTrue(CPreferencies.submitPasswd(login,oldPass,newPass));
		
		//verifications
		try {
			checkPass = (String)UsersParser.getInstance().getAttribute(login,UsersParser.ATTR_PASSWD);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(checkPass,newPass);
		
		//supression de l'utilisateur cree
		try {
			UsersParser.getInstance().removeUser("login");
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
