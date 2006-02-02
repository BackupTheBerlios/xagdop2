package xagdop.JUnit;

import javax.management.InstanceNotFoundException;

import xagdop.Controleur.CUser;
import xagdop.Model.User;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class CUserTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CUser.verifUser(String, String)'
	 */
	public void testVerifUser() throws InstanceNotFoundException, Exception {
		
		//Creer un nouveau utilisateur
		UsersParser up = UsersParser.getInstance();
		CUser cu= new CUser();
		cu.creerUser("titi","totopass","totopass");
		
	
		
		//Tests du mot de passe
		assertTrue(cu.verifUser("titi","totopass"));
		assertFalse(cu.verifUser("titi","titipass"));
	
		//Supprimer l'utilsateur
		up.removeUser("titi");
		

	}

	/*
	 * Test method for 'xagdop.Controleur.CUser.creerUser(String, String, String)'
	 */
	public void testCreerUser() throws InstanceNotFoundException, Exception  {
		
		//Creer un nouveau utilisateur
		UsersParser up = UsersParser.getInstance();
		CUser cu= new CUser();
		cu.creerUser("titi","totopass","totopass");
		
		//Test si l'utilisateur a bien été créé
		assertTrue(up.isUser("titi")==true);
		
		//supprimer l'utilisateur
		up.removeUser("titi");
	}

}
