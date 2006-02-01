package xagdop.JUnit;

import java.util.ArrayList;

import xagdop.Model.*;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class ProjectsParserTest extends TestCase {

	/*
	 * Test OK
	 */
	public void testGetProjectDescriptionString() {
		User usr=new User("loginTest","passTest",true,true);
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr);
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"descrTest");
		
		System.out.println("testGetProjectDescriptionOK: "+(String)(pp.getProjectDescription("projTest")));
		assertTrue(((String)(pp.getProjectDescription("projTest"))).equals((String)"descrTest"));
		
		up.removeUser(usr.getLogin());
		pp.removeProject("projTest");
	}

	
	/*
	 * Test OK
	 */
	public void testSetProjectDescriptionStringString() {
		User usr=new User("loginTest","passTest",true,true);
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr);
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"descrTest");
		
		assertTrue(((String)(pp.getProjectDescription("projTest"))).equals((String)"descrTest"));
		pp.setProjectDescription("projTest","newDescr");
		assertFalse(((String)(pp.getProjectDescription("projTest"))).equals((String)"descrTest"));
		assertTrue(((String)(pp.getProjectDescription("projTest"))).equals((String)"newDescr"));
		
		up.removeUser(usr.getLogin());
		pp.removeProject("projTest");
	}

	
	/*
	 * Test OK
	 */
	public void testIsUserInProject() {
		User usr=new User("loginTest","passTest",true,true);
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"descrTest");
		
		//System.out.println("isUserInProjectOK: "+pp.isUserInProject("projTest","loginTest"));
		assertTrue(pp.isUserInProject("projTest","loginTest"));
		
		//System.out.println("isUserInProjectKO: "+pp.isUserInProject("projTest","loginTestKO"));
		assertFalse(pp.isUserInProject("projTest","loginTestKO"));

		up.removeUser(usr.getLogin());
		pp.removeProject("projTest");
	}


	/*
	 * Test OK
	 */
	public void testIsProjectString() {
		User usr=new User("loginTest","passTest",true,true);
		
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"descrTest");
		
		assertTrue(pp.isProject("projTest"));
		pp.removeProject("projTest");
		assertFalse(pp.isProject("projTest"));
		
		up.removeUser(usr.getLogin());
	}


	/*
	 * Test OK
	 */
	public void testAddUserStringUsersBooleanBooleanBooleanBoolean() {
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,false,true,false);
		
		ArrayList usr2Rights = pp.getRights("projTest",usr2.getLogin());
		assertTrue( (Boolean)usr2Rights.get(0)==Boolean.TRUE );
		assertTrue( (Boolean)usr2Rights.get(1)==Boolean.FALSE );
		assertTrue( (Boolean)usr2Rights.get(2)==Boolean.TRUE );
		assertTrue( (Boolean)usr2Rights.get(3)==Boolean.FALSE );
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}

	
	/*
	 * Test OK
	 */
	public void testAddUserStringStringBooleanBooleanBooleanBoolean() {
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2.getLogin(),true,false,true,false);
		
		ArrayList usr2Rights = pp.getRights("projTest",usr2.getLogin());
		assertTrue( (Boolean)usr2Rights.get(0)==Boolean.TRUE );
		assertTrue( (Boolean)usr2Rights.get(1)==Boolean.FALSE );
		assertTrue( (Boolean)usr2Rights.get(2)==Boolean.TRUE );
		assertTrue( (Boolean)usr2Rights.get(3)==Boolean.FALSE );
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}
	
	
	/*
	 * Test OK
	 */
	public void testAddUserStringString2() {
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest","loginTest2");
		
		Project proj=pp.buildProject("projTest");				
		ArrayList logins= proj.getUsersLogin();
		
		assertTrue(logins.contains("loginTest2"));
		assertFalse(logins.contains("loginTestKO"));
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}

	
	/*
	 * Test OK
	 */
	public void testAddUserStringString() {
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2.getLogin(),true,false,true,false);
		
		ArrayList usr2Rights = pp.getRights("projTest",usr2.getLogin());
		assertTrue( (Boolean)usr2Rights.get(0)==Boolean.TRUE );
		assertTrue( (Boolean)usr2Rights.get(1)==Boolean.FALSE );
		assertTrue( (Boolean)usr2Rights.get(2)==Boolean.TRUE );
		assertTrue( (Boolean)usr2Rights.get(3)==Boolean.FALSE );
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}
	
	
	/*
	 * Test OK
	 */
	public void testAddProject() {
		User usr=new User("loginTest","passTest",true,true);
		
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr,"descrTest");
		
		assertTrue(pp.isProject("projTest"));
		
		up.removeUser(usr.getLogin());
		pp.removeProject("projTest");
	}

	
	/*
	 * Test OK
	 */
	public void testRemoveUser() {
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,true,true,true);
		
		//suppression de l'utilisateur supplementaire
		assertTrue(pp.isUserInProject("projTest",usr2.getLogin()));
		pp.removeUser("projTest",usr2.getLogin());
		assertFalse(pp.isUserInProject("projTest",usr2.getLogin()));

		//suppression de l'utilisateur initial
		assertTrue(pp.isUserInProject("projTest",usr1.getLogin()));
		pp.removeUser("projTest",usr1.getLogin());
		assertFalse(pp.isUserInProject("projTest",usr1.getLogin()));
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}

	
	/*
	 * Test OK
	 */
	public void testRemoveProject() {
		User usr=new User("loginTest","passTest",true,true);
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr);
		ProjectsParser pp= ProjectsParser.getInstance();
		
		pp.addProject("projTest",usr,"descrTest");
		assertTrue(pp.isProject("projTest"));
		pp.removeProject("projTest");
		assertFalse(pp.isProject("projTest"));
		
		up.removeUser(usr.getLogin());		
	}

	
	/*
	 * Test OK
	 */
	public void testSetRightsStringStringBooleanBooleanBooleanBoolean(){
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,true,true,true);
		
		//on verifie les droits initiaux
		ArrayList oldRights = pp.getRights("projTest",usr2.getLogin());
		assertTrue(oldRights.get(0)==Boolean.TRUE);
		assertTrue(oldRights.get(1)==Boolean.TRUE);
		assertTrue(oldRights.get(2)==Boolean.TRUE);
		assertTrue(oldRights.get(3)==Boolean.TRUE);
		
		//on modifie les droits
		pp.setRights("projTest",usr2.getLogin(),false,false,false,false);
		ArrayList newRights = pp.getRights("projTest",usr2.getLogin());

		//on verifie les modifications
		assertTrue(newRights.get(0)==Boolean.FALSE);
		assertTrue(newRights.get(1)==Boolean.FALSE);
		assertTrue(newRights.get(2)==Boolean.FALSE);
		assertTrue(newRights.get(3)==Boolean.FALSE);
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");		
	}
	
	
	/*
	 * Test OK
	 */
	public void testGetRightsStringString(){
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up=UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,false,true,false);
		
		//on verifie les droits initiaux
		ArrayList oldRights = pp.getRights("projTest",usr2.getLogin());
		assertTrue(oldRights.get(0)==Boolean.TRUE);
		assertTrue(oldRights.get(1)==Boolean.FALSE);
		assertTrue(oldRights.get(2)==Boolean.TRUE);
		assertTrue(oldRights.get(3)==Boolean.FALSE);
			
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");		
	}

	
	/*
	 * Test OK
	 */
	public void testBuildProject() {
		User usr1=new User("loginTest","passTest",true,true);
		User usr2=new User("loginTest2","passTest2",true,true);
		
		UsersParser up= UsersParser.getInstance();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= ProjectsParser.getInstance();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,true,true,true);
		
		Project proj=pp.buildProject("projTest");
		assertTrue(proj.getProjectName()=="projTest");
				
		ArrayList logins= proj.getUsersLogin();
		assertTrue(logins.contains("loginTest"));
		assertTrue(logins.contains("loginTest2"));
		assertFalse(logins.contains("loginTestKO"));
		System.out.println(logins);
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}

}
