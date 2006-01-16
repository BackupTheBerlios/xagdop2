package xagdop.JUnit;

import junit.framework.TestCase;
import xagdop.Model.*;

public class UsersTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Users.isAdmin()'
	 */
	public void testIsAdmin() {
		Users us = new Users("toto","totopass",1,true,false);
		assertTrue(us.isAdmin()==true);
		assertFalse(us.isAdmin()==false);
	}

	/*
	 * Test method for 'xagdop.Model.Users.setAdmin(boolean)'
	 */
	public void testSetAdmin() {
		Users us = new Users("toto","totopass",1,true,false);
		us.setAdmin(false);
		assertTrue(us.isAdmin()==false);
		assertFalse(us.isAdmin()==true);
	}

	/*
	 * Test method for 'xagdop.Model.Users.getId()'
	 */
	public void testGetId() {
		Users us = new Users("toto","totopass",1,true,false);
		assertTrue(us.getId()==1);
		assertFalse(us.getId()!=1);
	}

	/*
	 * Test method for 'xagdop.Model.Users.setId(int)'
	 */
	public void testSetId() {
		Users us = new Users("toto","totopass",1,true,false);
		us.setId(2);
		assertTrue(us.getId()==2);
		assertFalse(us.getId()==1);
	}

	/*
	 * Test method for 'xagdop.Model.Users.getLogin()'
	 */
	public void testGetLogin() {
		Users us = new Users("toto","totopass",1,true,false);
		assertEquals(us.getLogin(),"toto");
		assertFalse(us.getLogin()!="toto");

	}

	/*
	 * Test method for 'xagdop.Model.Users.setLogin(String)'
	 */
	public void testSetLogin() {
		Users us = new Users("tata","totopass",1,true,false);
		us.setLogin("toto");
		assertEquals(us.getLogin(),"toto");
		assertFalse(us.getLogin()=="tata");
	}

	/*
	 * Test method for 'xagdop.Model.Users.getPasswd()'
	 */
	public void testGetPasswd() {
		Users us = new Users("toto","totopass",1,true,false);
		assertEquals(us.getPasswd(),"totopass");
		assertFalse(us.getPasswd()!="totopass");
	}

	/*
	 * Test method for 'xagdop.Model.Users.setPasswd(String)'
	 */
	public void testSetPasswd() {
		Users us = new Users("toto","totopass",1,true,false);
		us.setPasswd("tatapass");
		assertEquals(us.getPasswd(),"tatapass");
		assertFalse(us.getPasswd()=="totopass");
	}

	/*
	 * Test method for 'xagdop.Model.Users.isPmanager()'
	 */
	public void testIsPmanager() {
		Users us = new Users("toto","totopass",1,true,false);
		assertTrue(us.isPmanager()==false);
		assertFalse(us.isPmanager()==true);
		
	}

	/*
	 * Test method for 'xagdop.Model.Users.setPmanager(boolean)'
	 */
	public void testSetPmanager() {
		Users us = new Users("toto","totopass",1,true,false);
		us.setPmanager(true);
		assertTrue(us.isPmanager()==true);
		assertFalse(us.isPmanager()==false);
	}

	/*
	 * Test method for 'xagdop.Model.Users.isArchitect(String)'
	 */
	public void testIsArchitect() {

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
