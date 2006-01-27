package xagdop.JUnit;

import java.util.ArrayList;

import junit.framework.TestCase;
import xagdop.Model.*;
import xagdop.Parser.ProjectsParser;


public class UsersTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Users.isAdmin()'
	 */
	public void testIsAdmin() {
		
		//Creation d'un utilisateur
		Users us = new Users("toto","totopass",true,false);
		
		//Tests
		assertTrue(us.isAdmin()==true);
		assertFalse(us.isAdmin()==false);
	}

	/*
	 * Test method for 'xagdop.Model.Users.setAdmin(boolean)'
	 */
	public void testSetAdmin() {
		//Creations d'un utilisateur
		Users us = new Users("toto","totopass",true,false);
		
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
		Users us = new Users("toto","totopass",true,false);
		
		//Tests
		assertEquals(us.getLogin(),"toto");
		assertFalse(us.getLogin()!="toto");

	}

	/*
	 * Test method for 'xagdop.Model.Users.setLogin(String)'
	 */
	public void testSetLogin() {
		//Creation d'un utilisateur
		Users us = new Users("tata","totopass",true,false);
		
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
		Users us = new Users("toto","totopass",true,false);
		
		//Tests
		assertEquals(us.getPasswd(),"totopass");
		assertFalse(us.getPasswd()!="totopass");
	}

	/*
	 * Test method for 'xagdop.Model.Users.setPasswd(String)'
	 */
	public void testSetPasswd() {
		//Creation d'un utilisateur
		Users us = new Users("toto","totopass",true,false);
		
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
		Users us = new Users("toto","totopass",true,false);
		
        //Tests
		assertTrue(us.isPcreator()==false);
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.setPmanager(boolean)'
	 */
	public void testSetPmanager() {
		//	Creation d'un utilisateur
		Users us = new Users("toto","totopass",true,false);
		
		//donner l'attribut Pmanager a toto
		us.setPmanager(true);
		
		//Tests
		assertTrue(us.isPcreator()==true);
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.isArchitect(String)'
	 */
	public void testIsArchitect() {
//		Users us = new Users("toto","totopass",true,false);
//		ProjectsParser pp= new ProjectsParser();
//		ArrayList list= new ArrayList();
//		ArrayList id =new ArrayList();
//		Projects p = new Projects("projTest",list,id);
//		pp.addProject("projTest",us,"desc");
//
//		pp.addUser("projTest",us,true,true,true,true);
//		
//		
//		assertTrue(us.isArchitect("projTest")==true);
		
		}

	/*
	 * Test method for 'xagdop.Model.Users.isRedactor(String)'
	 */
	public void testIsRedactor() {

	}

	/*
	 * Test method for 'xagdop.Model.Users.isAnalyst(String)'
	 */
	public void testIsAnalyst() {

	}

	/*
	 * Test method for 'xagdop.Model.Users.isPManager(String)'
	 */
	public void testIsPManager() {
		
		
	}

}
