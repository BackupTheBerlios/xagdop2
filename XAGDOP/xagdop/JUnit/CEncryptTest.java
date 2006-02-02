package xagdop.JUnit;

import java.security.NoSuchAlgorithmException;

import xagdop.Controleur.CEncrypt;
import junit.framework.TestCase;

public class CEncryptTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CEncrypt.getEncodedPassword(String)'
	 */
	public void testGetEncodedPassword() {
		assertTrue(CEncrypt.getEncodedPassword("titi").equals("5d933eef19aee7da192608de61b6c23d"));
		assertFalse(CEncrypt.getEncodedPassword("JUnit").equals("5d933eef19aee7da192608de61b6c23d"));
		}

	/*
	 * Test method for 'xagdop.Controleur.CEncrypt.testPassword(String, String)'
	 */
	public void testTestPassword() throws NoSuchAlgorithmException {
		assertTrue(CEncrypt.testPassword("titi",CEncrypt.getEncodedPassword("titi")));

	}

}
