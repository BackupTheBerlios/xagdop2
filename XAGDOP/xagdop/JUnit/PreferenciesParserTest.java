package xagdop.JUnit;

import java.util.Locale;

import xagdop.Model.Preferencies;
import xagdop.Parser.PreferenciesParser;
import junit.framework.TestCase;

public class PreferenciesParserTest extends TestCase {

	/*
	 * Test method for 'xagdop.Parser.PreferenciesParser.buildPreferencies()'
	 */
	public void testBuildPreferencies() throws Exception {
		final String localTest = "/test/local";
		final String servTest = "/test/serv";
		final String lnfTest = "Metal";
		final Locale langTest = Locale.CANADA;

		Preferencies savePref = PreferenciesParser.getInstance().buildPreferencies();
		assertNotSame(savePref.getServer(),servTest);
		assertNotSame(savePref.getLocal(),localTest);
		assertNotSame(savePref.getLookNFeel(),lnfTest);
		assertNotSame(savePref.getLang(),langTest);

		PreferenciesParser.getInstance().setServer(servTest);
		PreferenciesParser.getInstance().setLocalPath(localTest);
		PreferenciesParser.getInstance().setLang(langTest);
		PreferenciesParser.getInstance().setLNF(lnfTest);		
		
		savePref = PreferenciesParser.getInstance().buildPreferencies();		
		assertEquals(savePref.getServer(),servTest);
		assertEquals(savePref.getLocal(),localTest);
		assertEquals(savePref.getLookNFeel(),lnfTest);
		assertEquals(savePref.getLang(),langTest);
	}

	/*
	 * Test method for 'xagdop.Parser.PreferenciesParser.setPreferencies(Preferencies)'
	 */
	public void testSetPreferencies() throws Exception {
		final String servTest = "/test/serv";
		final String localTest = "/test/local";
		final String lnfTest = "Metal";
		final Locale langTest = Locale.FRENCH;
		
		
		Preferencies pref = new Preferencies(servTest,localTest,lnfTest,langTest);
		PreferenciesParser.getInstance().setPreferencies(pref);
		assertEquals(PreferenciesParser.getInstance().getServer(),servTest);
		assertEquals(PreferenciesParser.getInstance().getLocalPath(),localTest);
		assertEquals(PreferenciesParser.getInstance().getLNF(),lnfTest);
		assertEquals(PreferenciesParser.getInstance().getLang(),langTest);

	}

	/*
	 * Test method for 'xagdop.Parser.PreferenciesParser.setServer(String)'
	 */
	public void testSetServer() throws Exception {
		String server = "/server1";
		String serverFalse = "/server2";
		
		assertNotSame(PreferenciesParser.getInstance().getServer(),server);
		PreferenciesParser.getInstance().setServer(server);
		assertEquals(PreferenciesParser.getInstance().getServer(),server);
		assertNotSame(PreferenciesParser.getInstance().getServer(),serverFalse);
	}

	/*
	 * Test method for 'xagdop.Parser.PreferenciesParser.setLocal(String)'
	 */
	public void testSetLocal() throws Exception {
		String local = "/local1";
		String localFalse = "/local2";
		
		assertNotSame(PreferenciesParser.getInstance().getLocalPath(),local);
		PreferenciesParser.getInstance().setLocalPath(local);
		assertEquals(PreferenciesParser.getInstance().getLocalPath(),local);
		assertNotSame(PreferenciesParser.getInstance().getLocalPath(),localFalse);
	}

	/*
	 * Test method for 'xagdop.Parser.PreferenciesParser.setLNF(String)'
	 */
	public void testSetLNF() throws Exception {
		String lnf = "lnf1";
		String lnfFalse = "lnf2";
		
		assertNotSame(PreferenciesParser.getInstance().getLNF(),lnf);
		PreferenciesParser.getInstance().setLNF(lnf);
		assertEquals(PreferenciesParser.getInstance().getLNF(),lnf);
		assertNotSame(PreferenciesParser.getInstance().getLNF(),lnfFalse);
	}

	/*
	 * Test method for 'xagdop.Parser.PreferenciesParser.setLang(String)'
	 */
	public void testSetLang() throws Exception {
		Locale lang = Locale.CANADA;
		Locale langFalse = Locale.FRENCH;
		
		assertNotSame(PreferenciesParser.getInstance().getLang(),lang);
		PreferenciesParser.getInstance().setLang(lang);
		assertEquals(PreferenciesParser.getInstance().getLang(),lang);
		assertNotSame(PreferenciesParser.getInstance().getLang(),langFalse);
	}
	
	public void testGetServer() throws Exception {
		String server = "/server1";
		PreferenciesParser.getInstance().setServer(server);
		assertEquals(PreferenciesParser.getInstance().getServer(),server);		
	}
	
	public void testGetLocal() throws Exception {
		String local = "/local";
		PreferenciesParser.getInstance().setLocalPath(local);
		assertEquals(PreferenciesParser.getInstance().getLocalPath(),local);
	}
	
	public void testGetLNF() throws Exception {
		String lnf = "lnf1";		
		PreferenciesParser.getInstance().setLNF(lnf);
		assertEquals(PreferenciesParser.getInstance().getLNF(),lnf);
	}
	
	public void testGetLang() throws Exception {
		Locale lang = Locale.CANADA;
		PreferenciesParser.getInstance().setLang(lang);
		assertEquals(PreferenciesParser.getInstance().getLang(),lang);			
	}

}
