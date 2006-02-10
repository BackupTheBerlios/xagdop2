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
		cu.createUser("toto","totopass","totopass");
		
	
		
		//Tests du mot de passe
		assertTrue(cu.verifUser("toto","totopass"));	
	
		//Supprimer l'utilsateur
		up.removeUser("toto");
		
		
		

	}

	/*
	 * Test method for 'xagdop.Controleur.CUser.creerUser(String, String, String)'
	 */
	public void testCreerUser() throws InstanceNotFoundException, Exception  {
		
		//Creer un nouveau utilisateur
		UsersParser up = UsersParser.getInstance();
		CUser cu= new CUser();
		cu.createUser("tata","totopass","totopass");
		
		//Test si l'utilisateur a bien ?t? cr??
		assertTrue(up.isUser("tata"));
		
		//supprimer l'utilisateur
		up.removeUser("tata");
	}

}
