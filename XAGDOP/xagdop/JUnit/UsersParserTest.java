package xagdop.JUnit;

import java.util.ArrayList;

import junit.framework.TestCase;
import xagdop.Model.User;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;

public class UsersParserTest extends TestCase {

	/*
	 * Test method for 'xagdop.Parser.UsersParser.getInstance()'
	 */
	public void testGetInstance() {
		UsersParser Up;
		try {
			Up = UsersParser.getInstance();
			assertNotNull(Up);
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.isUser(String)'
	 */
	public void testIsUser() {
		UsersParser Up;
		try {
			Up = UsersParser.getInstance();
			assertTrue(Up.isUser("claire"));
			assertFalse(Up.isUser("francois"));
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.getUserByLogin(String)'
	 */
	public void testGetUserByLogin() {
		UsersParser Up;
		try {
			Up = UsersParser.getInstance();
			User user = null;
			User user2 = null;
			user = Up.getUserByLogin("claire");
			assertNotNull(user);
			user2 = Up.getUserByLogin("remy");
			assertNotNull(user2);
			assertNotSame(user, user2);
			assertEquals("claire", user.getLogin());
			assertTrue(user.isPcreator());
			assertTrue(user.isAdmin());
			assertEquals("pass", user.getPasswd());
			assertFalse(user2.isPcreator());
			assertFalse(user2.isAdmin());
			user2 = Up.getUserByLogin("gremy");
			assertNull(user2);
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.getUser(String, String)'
	 */
	public void testGetUser() {
		UsersParser Up;
		try {
			Up = UsersParser.getInstance();
			User user = null;
			user = Up.getUserByLogin("claire");
			assertNotNull(user);
			assertEquals("claire", user.getLogin());
			assertTrue(user.isPcreator());
			assertTrue(user.isAdmin());
			assertEquals("pass", user.getPasswd());
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.getAttribute(String, String)'
	 */
	public void testGetAttribute() {
		UsersParser Up;
		try {
			Up = UsersParser.getInstance();
			assertEquals("claire", Up.getAttribute("claire", "login"));
			assertEquals("pass", Up.getAttribute("claire", "passwd"));
			assertEquals(Boolean.TRUE, Up.getAttribute("claire", "pcreator"));
			assertEquals(Boolean.TRUE, Up.getAttribute("claire", "admin"));
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.setAttribute(String, String, boolean)'
	 */
	public void testSetAttributeStringStringBoolean() {
		UsersParser Up;
		try {
			Up = UsersParser.getInstance();
			Up.setAttribute("claire", "admin", false);
			assertEquals(Boolean.FALSE, Up.getAttribute("claire", "admin"));
			Up.setAttribute("claire", "admin", true);
			Up.setAttribute("claire", "pcreator", false);
			assertEquals(Boolean.FALSE, Up.getAttribute("claire", "pcreator"));
			Up.setAttribute("claire", "pcreator", true);
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.setAttribute(String, String, String)'
	 */
	public void testSetAttributeStringStringString() {
		try {
		UsersParser Up = UsersParser.getInstance();
		Up.setAttribute("claire", "login", "clairee");
		assertEquals("clairee", Up.getAttribute("clairee", "login"));
		Up.setAttribute("clairee", "login", "claire");
		Up.setAttribute("claire", "passwd", "claire");
		assertEquals("claire", Up.getAttribute("claire", "passwd"));
		Up.setAttribute("claire", "passwd", "pass");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.addUser(String, String, boolean, boolean)'
	 */
	public void testAddUserStringStringBooleanBoolean() {
		try {
		UsersParser Up = UsersParser.getInstance();
		Up.addUser("rene", "renepass", false, true);
		assertTrue(Up.isUser("rene"));
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.addUser(String, String)'
	 */
	public void testAddUserStringString() {
		try{
		UsersParser Up = UsersParser.getInstance();
		Up.addUser("bilou", "renepass");
		assertTrue(Up.isUser("rene"));
		
	} catch (Exception e) {
		ErrorManager.getInstance().display();
	}
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.addUser(User)'
	 */
	public void testAddUserUser() {
		try{
		User user = new User("bernard", "pass", true, true);
		UsersParser Up = UsersParser.getInstance();
		Up.addUser(user);
		assertTrue(Up.isUser("bernard"));
		assertEquals(Boolean.TRUE, Up.getAttribute("bernard", "admin"));
		assertEquals(Boolean.TRUE, Up.getAttribute("bernard", "pcreator"));
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.removeUser(String)'
	 */
	public void testRemoveUser() {
		try{
		UsersParser Up = UsersParser.getInstance();
		Up.removeUser("bernard");
		assertFalse(Up.isUser("bernard"));
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}

	/*
	 * Test method for 'xagdop.Parser.UsersParser.getAllUsers()'
	 */
	public void testGetAllUsers() {
		try{
		UsersParser Up = UsersParser.getInstance();
		ArrayList l = Up.getAllUsers();
		assertTrue(l.size() == 6);
		assertEquals(((User) l.get(0)).getLogin(), "claire");
		assertEquals(((User) l.get(1)).getLogin(), "cecile");
		assertEquals(((User) l.get(2)).getLogin(), "remy");
		assertEquals(((User) l.get(3)).getLogin(), "jeremy");
		User user = (User) l.get(0);
		assertEquals("claire", user.getLogin());
		assertTrue(user.isPcreator());
		assertTrue(user.isAdmin());
		assertEquals("pass", user.getPasswd());
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}
}
