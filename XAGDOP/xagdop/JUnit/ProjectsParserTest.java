package xagdop.JUnit;

import java.util.ArrayList;

import xagdop.Model.Projects;
import xagdop.Model.Users;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class ProjectsParserTest extends TestCase {

	/*
	 * Test KO
	 */
	public void testGetAttributeStringString() {
		Users usr=new Users("loginTest","passTest",true,true);
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr,"descrTest");
		
		System.out.println("testGetAttributeStringStringOK: "+(String)(pp.getAttribute("projTest",ProjectsParser.ATTR_DESC)));
		assertTrue((String)(pp.getAttribute("projTest",ProjectsParser.ATTR_DESC))=="descrTest"); //??? ECHEC
		
		pp.removeProject("projTest");
	}

	/*
	 * Test KO
	 */
	public void testGetAttributeStringStringString() {
		Users usr1=new Users("loginTest","passTest",true,true);
		Users usr2=new Users("loginTest2","passTest2",true,true);
		Users usr3=new Users("loginTest3","passTest3",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr1);
		up.addUser(usr2);
		up.addUser(usr3);
		
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,true,true,true);
		pp.addUser("projTest",usr3,false,false,false,false);
		
		//attributs de l'utilisateur initial
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ANALYST,usr1.getLogin())==Boolean.valueOf(false));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ARCHI,usr1.getLogin())==Boolean.valueOf(false));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_MANAGER,usr1.getLogin())==Boolean.valueOf(false)); //??? ECHEC
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_REDACTEUR,usr1.getLogin())==Boolean.valueOf(false));
		
		//attributs de l'utilisateur 2
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ANALYST,usr2.getLogin())==Boolean.valueOf(true));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ARCHI,usr2.getLogin())==Boolean.valueOf(true));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_MANAGER,usr2.getLogin())==Boolean.valueOf(true));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_REDACTEUR,usr2.getLogin())==Boolean.valueOf(true));

		//attributs de l'utilisateur 3
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ANALYST,usr3.getLogin())==Boolean.valueOf(false));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ARCHI,usr3.getLogin())==Boolean.valueOf(false));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_MANAGER,usr3.getLogin())==Boolean.valueOf(false));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_REDACTEUR,usr3.getLogin())==Boolean.valueOf(false));

		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		up.removeUser(usr3.getLogin());
		pp.removeProject("projTest");
	}

	/*
	 * Test OK
	 */
	public void testIsUserInProject() {
		Users usr=new Users("loginTest","passTest",true,true);
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr,"descrTest");
		
		//System.out.println("isUserInProjectOK: "+pp.isUserInProject("projTest","loginTest"));
		assertTrue(pp.isUserInProject("projTest","loginTest"));
		
		//System.out.println("isUserInProjectKO: "+pp.isUserInProject("projTest","loginTestKO"));
		assertFalse(pp.isUserInProject("projTest","loginTestKO"));

		pp.removeProject("projTest");
	}

	/*
	 * Test A FAIRE
	 */
	public void testSetAttributeStringStringString() {

	}

	/*
	 * Test A FAIRE
	 */
	public void testSetAttributeStringStringStringString() {

	}


	/*
	 * Test OK
	 */
	public void testAddUserStringUsersBooleanBooleanBooleanBoolean() {
		Users usr1=new Users("loginTest","passTest",true,true);
		Users usr2=new Users("loginTest2","passTest2",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,false,true,false);
		
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ANALYST,usr2.getLogin())==Boolean.valueOf(true));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_ARCHI,usr2.getLogin())==Boolean.valueOf(false));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_MANAGER,usr2.getLogin())==Boolean.valueOf(true));
		assertTrue((Boolean)pp.getAttribute("projTest",ProjectsParser.ATTR_REDACTEUR,usr2.getLogin())==Boolean.valueOf(false));
		
		up.removeUser(usr1.getLogin());
		up.removeUser(usr2.getLogin());
		pp.removeProject("projTest");
	}

	/*
	 * Test A REPASSER apres correction de isProject
	 */
	public void testAddProject() {
		Users usr=new Users("loginTest","passTest",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr);
		
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr,"descrTest");
		
		assertTrue(pp.isProject("projTest"));
		
		up.removeUser(usr.getLogin());
		pp.removeProject("projTest");
	}

	/*
	 * Test OK
	 */
	public void testAddUserStringString() {
		Users usr1=new Users("loginTest","passTest",true,true);
		Users usr2=new Users("loginTest2","passTest2",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest","loginTest2");
		
		Projects proj=pp.buildProject("projTest");				
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
	public void testRemoveUser() {
		Users usr1=new Users("loginTest","passTest",true,true);
		Users usr2=new Users("loginTest2","passTest2",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= new ProjectsParser();
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
	 * Test A REPASSER apres correction de isProject
	 */
	public void testRemoveProject() {
		Users usr=new Users("loginTest","passTest",true,true);
		UsersParser up=new UsersParser();
		up.addUser(usr);
		ProjectsParser pp= new ProjectsParser();
		
		pp.addProject("projTest",usr,"descrTest");
		assertTrue(pp.isProject("projTest"));
		pp.removeProject("projTest");
		assertFalse(pp.isProject("projTest"));
		
		up.removeUser(usr.getLogin());		
	}

	/*
	 * Test OK 'xagdop.Parser.ProjectsParser.buildProject(String)'
	 */
	public void testBuildProject() {
		Users usr1=new Users("loginTest","passTest",true,true);
		Users usr2=new Users("loginTest2","passTest2",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr1);
		up.addUser(usr2);
		
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr1,"descrTest");
		pp.addUser("projTest",usr2,true,true,true,true);
		
		Projects proj=pp.buildProject("projTest");
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

	/*
	 * Test KO
	 */
	public void testIsProjectString() {
		Users usr=new Users("loginTest","passTest",true,true);
		
		UsersParser up=new UsersParser();
		up.addUser(usr);
		
		ProjectsParser pp= new ProjectsParser();
		pp.addProject("projTest",usr,"descrTest");
		
		assertTrue(pp.isProject("projTest"));
		pp.removeProject("projTest");
		assertFalse(pp.isProject("projTest"));//??? ECHEC
		
		up.removeUser(usr.getLogin());
	}
	
	/*
	 * Test A FAIRE 'xagdop.Parser.ProjectsParser.saveDocument()'
	 */
	public void testSaveDocument() {

	}

}
