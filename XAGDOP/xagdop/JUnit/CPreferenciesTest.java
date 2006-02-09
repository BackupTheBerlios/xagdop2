package xagdop.JUnit;

import java.util.Locale;

import xagdop.Controleur.CPreferencies;
import xagdop.Model.Preferencies;
import xagdop.Parser.PreferenciesParser;
import junit.framework.TestCase;

public class CPreferenciesTest extends TestCase {


	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getLocalPath()'
	 */
	public void testGetLocalPath() {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String localTest = "/test/local";
		CPreferencies.setLocalPath(localTest);
		assertEquals(CPreferencies.getLocalPath(),localTest);
		assertNotSame(CPreferencies.getLocalPath(),"PIRATE");
		
		//restauration du contexte
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setLocalPath(String)'
	 */
	public void testSetLocalPath() {
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
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getServerPath()'
	 */
	public void testGetServerPath() {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String serverTest = "/test/server";
		CPreferencies.setServerPath(serverTest);
		assertEquals(CPreferencies.getServerPath(),serverTest);
		assertNotSame(CPreferencies.getServerPath(),"PIRATE");
		
		//restauration du contexte
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setServerPath(String)'
	 */
	public void testSetServerPath() {
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
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getDefaultLNF()'
	 */
	public void testGetDefaultLNF() {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String lnfTest = "Metal";
		CPreferencies.setDefaultLNF(lnfTest);
		assertEquals(CPreferencies.getDefaultLNF(),lnfTest);
		assertNotSame(CPreferencies.getDefaultLNF(),"PIRATE");
		
		//restauration du contexte
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setDefaultLNF(String)'
	 */
	public void testSetDefaultLNF() {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();		
		
		//corps du test
		final String lnfTest = "/test/server";
		final String lnfTest2 = "/test/server2";
		CPreferencies.setDefaultLNF(lnfTest);
		assertEquals(CPreferencies.getDefaultLNF(),lnfTest);
		CPreferencies.setDefaultLNF(lnfTest2);
		assertEquals(CPreferencies.getDefaultLNF(),lnfTest2);
		assertNotSame(CPreferencies.getDefaultLNF(),"PIRATE");
		
		//restauration du contexte
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getAllLNF()'
	 */
	public void testGetAllLNF() {
		assertTrue(false);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getDefaultLocale()'
	 */
	public void testGetDefaultLocale() {
		//sauvegarde du contexte
		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();

		//corps du test
		final String localeTest = Locale.FRANCE.getLanguage();
		CPreferencies.setDefaultLocale(localeTest);
		assertEquals(CPreferencies.getDefaultLocale(),localeTest);
		assertNotSame(CPreferencies.getDefaultLocale(),Locale.ENGLISH.getLanguage());
		
		//restauration du contexte
		try {
			PreferenciesParser.getInstance().setPreferencies(savePref);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.setDefaultLocale(String)'
	 */
	public void testSetDefaultLocale() {
		assertTrue(false);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.getAllLocale()'
	 */
	public void testGetAllLocale() {
		assertTrue(false);
	}

	/*
	 * Test method for 'xagdop.Controleur.CPreferencies.submitPasswd(String, String, String)'
	 */
	public void testSubmitPasswd() {
		assertTrue(false);
	}


}
