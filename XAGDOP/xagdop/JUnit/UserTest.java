package xagdop.JUnit;

import java.util.ArrayList;

import junit.framework.TestCase;
import xagdop.Model.*;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;


public class UserTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Users.isAdmin()'
	 */
	public void testIsAdmin() {
		
		//Creation d'un utilisateur
		User us = new User("toto","totopass",true,false);
		
		//Tests
		assertTrue(us.isAdmin()==true);
		assertFalse(us.isAdmin()==false);
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
		assertTrue(us.isAdmin()==false);
		assertFalse(us.isAdmin()==true);
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
		assertTrue(us.isPcreator()==false);
		
		
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
		assertTrue(us.isPcreator()==true);
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.isArchitect(String)'
	 */
	public void testIsArchitect() {
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
		assertTrue(usr.isArchitect("projTest")==false);

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");
	}

	/*
	 * Test method for 'xagdop.Model.Users.isRedactor(String)'
	 */
	public void testIsRedactor() {
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
		assertTrue(usr.isRedactor("projTest")==false);

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");

	}

	/*
	 * Test method for 'xagdop.Model.Users.isAnalyst(String)'
	 */
	public void testIsAnalyst() {
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
		assertTrue(usr.isAnalyst("projTest")==true);

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");

	}

	/*
	 * Test method for 'xagdop.Model.Users.isPManager(String)'
	 */
	public void testIsPManager() {
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
		assertTrue(usr.isPManager("projTest")==true);

		//Supprimer l'utilisateur de UsersParsers
		usrp.removeUser(usr.getLogin());
		
		//Supprimer le projet de ProjectsParsers
		pp.removeProject("projTest");
		
		
	}

}
