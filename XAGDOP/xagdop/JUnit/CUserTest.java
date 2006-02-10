package xagdop.JUnit;

import javax.management.InstanceNotFoundException;

import junit.framework.TestCase;
import xagdop.Controleur.CUser;
import xagdop.Parser.UsersParser;

public class CUserTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CUser.verifUser(String, String)'
	 */
	public void testVerifUser() throws InstanceNotFoundException, Exception {
		
		//Creer un nouveau utilisateur
		UsersParser up = UsersParser.getInstance();
		CUser cu= new CUser();
		cu.createUser("tovu","totopass","totopass");
		
	
		
		//Tests du mot de passe
		assertTrue(cu.verifUser("tovu","totopass"));	
	
		//Supprimer l'utilsateur
		up.removeUser("tovu");
		
		
		

	}

	/*
	 * Test method for 'xagdop.Controleur.CUser.creerUser(String, String, String)'
	 */
	public void testCreerUser() throws InstanceNotFoundException, Exception  {
		
		//Creer un nouveau utilisateur
		UsersParser up = UsersParser.getInstance();
		CUser cu= new CUser();
		cu.createUser("tavu","totopass","totopass");
		
		//Test si l'utilisateur a bien ?t? cr??
		assertTrue(up.isUser("tavu"));
		
		//supprimer l'utilisateur
		up.removeUser("tavu");
	}

}
