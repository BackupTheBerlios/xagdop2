package xagdop.JUnit;

import junit.framework.TestCase;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;


public class UserTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Users.isAdmin()'
	 */
	public void testIsAdmin() {
		
		//Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//Tests
		assertTrue(us.isAdmin());
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.setAdmin(boolean)'
	 */
	public void testSetAdmin() {
		//Creations d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//definir si l'utilisateur est Admin ou pas
		us.setAdmin(false);
		
		//Tests
		assertTrue(!us.isAdmin());
		
	}




	/*
	 * Test method for 'xagdop.Model.Users.getLogin()'
	 */
	public void testGetLogin() {
		//Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//Tests
		assertEquals(us.getLogin(),"toto");
		assertFalse(us.getLogin()!="toto");

	}

	/*
	 * Test method for 'xagdop.Model.Users.setLogin(String)'
	 */
	public void testSetLogin() {
		//Creation d'un utilisateur
		User us = new User("tata","totopass",true,false);
		
		//Changer le login
		us.setLogin("toto");
		
		//Tests
		assertEquals(us.getLogin(),"toto");
		assertFalse(us.getLogin()=="tata");
	}

	/*
	 * Test method for 'xagdop.Model.Users.getPasswd()'
	 */
	public void testGetPasswd() {
		//	Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//Tests
		assertEquals(us.getPasswd(),"totopass");
		assertFalse(us.getPasswd()!="totopass");
	}

	/*
	 * Test method for 'xagdop.Model.Users.setPasswd(String)'
	 */
	public void testSetPasswd() {
		//Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//changer le mot de passe
		us.setPasswd("tatapass");
		
		//Tests
		assertEquals(us.getPasswd(),"tatapass");
		assertFalse(us.getPasswd()=="totopass");
	}

	/*
	 * Test method for 'xagdop.Model.Users.isPcreator()'
	 */
	public void testIsPcreator() {
		//	Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
        //Tests
		assertTrue(!us.isPcreator());
		assertTrue(us.isAdmin());
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.setPmanager(boolean)'
	 */
	public void testSetPmanager() {
		//	Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//donner l'attribut Pmanager a toto
		us.setPmanager(true);
		
		
		//Tests
		assertTrue(us.isPcreator());
		
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.isArchitect(String)'
	 */
	public void testIsArchitect() {
		try{
		//Creation d'un utilisateur
		User usr = new User("toto","totopass",true,false);

		//Ajouter un nouveau utilisateur dans UsersParsers
		UsersParser  usrp = UsersParser.getInstance();
		usrp.addUser(usr);
		
		//Ajouter un projet dans ProjectsParser
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"desc");
		
		//Affecter l'utilisateur au projTest
		pp.addUser("projTest",usr.getLogin(),true,false,true,false);
		
		//Test
		
		assertTrue(!usr.isArchitect("projTest"));
	
		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");
	} catch (Exception e) {
		ErrorManager.getInstance().display();
	}
	}

	/*
	 * Test method for 'xagdop.Model.Users.isRedactor(String)'
	 */
	public void testIsRedactor() {
		try{
		//Creation d'un utilisateur
		User usr = new User("toto","totopass",true,false);

		//Ajouter un nouveau utilisateur dans UsersParsers
		UsersParser  usrp = UsersParser.getInstance();
		usrp.addUser(usr);
		
		//Ajouter un projet dans ProjectsParser
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"desc");
		
		//Affecter l'utilisateur au projTest
		pp.addUser("projTest",usr.getLogin(),true,false,true,false);
		
		//Test
		assertTrue(!usr.isRedactor("projTest"));

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}

	}

	/*
	 * Test method for 'xagdop.Model.Users.isAnalyst(String)'
	 */
	public void testIsAnalyst() {
		try{
		//Creation d'un utilisateur
		User usr = new User("toto","totopass",true,false);

		//Ajouter un nouveau utilisateur dans UsersParsers
		UsersParser  usrp = UsersParser.getInstance();
		usrp.addUser(usr);
		
		//Ajouter un projet dans ProjectsParser
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"desc");
		
		//Affecter l'utilisateur au projTest
		pp.addUser("projTest",usr.getLogin(),true,false,true,false);
		
		//Test
		assertTrue(usr.isAnalyst("projTest"));

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}

	}

	/*
	 * Test method for 'xagdop.Model.Users.isPManager(String)'
	 */
	public void testIsPManager() {
		try{
		//Creation d'un utilisateur
		User usr = new User("toto","totopass",true,false);

		//Ajouter un nouveau utilisateur dans UsersParsers
		UsersParser  usrp = UsersParser.getInstance();
		usrp.addUser(usr);
		
		//Ajouter un projet dans ProjectsParser
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"desc");
		
		//Affecter l'utilisateur au projTest
		pp.addUser("projTest",usr.getLogin(),true,false,true,false);
		
		//Test
		assertTrue(usr.isPManager("projTest"));

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		
	}

}
