package xagdop.JUnit;

import junit.framework.TestCase;
import xagdop.Model.*;


import java.util.ArrayList;
public class ProjectTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Projects.getProjectName()'
	 */
	public void testGetProjectName() {
			//Declarations
			ArrayList list= new ArrayList();
			ArrayList id =new ArrayList();
			
			//Creation d'un nouveau  projet
			Project p = new Project("blabla",list,id);
			
			//Test
			assertEquals(p.getProjectName(),"blabla");
			assertFalse(p.getProjectName()=="blibli");
	}

	/*
	 * Test method for 'xagdop.Model.Projects.setProjectName(String)'
	 */
	public void testSetProjectName() {
		//Declarations
		ArrayList list= new ArrayList();
		ArrayList id =new ArrayList();
		
		//Creation d'un nouveau projet
		Project p = new Project("blabla",list,id);
		
		//Changement du nom du projet
		p.setProjectName("blibli");
		
		//Tests
		assertEquals(p.getProjectName(),"blibli");
		assertFalse(p.getProjectName()=="blabla");

	}

	/*
	 * Test method for 'xagdop.Model.Projects.getUsersLogin()'
	 */
	public void testGetUsersLogin() {
		
		//Declarations
		ArrayList list= new ArrayList();
		ArrayList id =new ArrayList();
		
		
		//Creation d'un nouveau projet
		Project p = new Project("blabla",list,id);
		
		//Ajouter un login
		id.add("toto");
		assertTrue((p.getUsersLogin()).get(0)=="toto");
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.setUsersLogin(ArrayList)'
	 */
	public void testSetUsersLogin() {
		
		//Declarations
		ArrayList list= new ArrayList();
		ArrayList id= new ArrayList();
		ArrayList id1 =new ArrayList();
		
		//Creation de projet
		Project p = new Project("blabla",list,id);
		//Ajouter un login
		id.add("tata");
		id1.add("toto");
		
		p.setUsersLogin(id1);
		assertEquals((p.getUsersLogin()).get(0),"toto");
		assertFalse((p.getUsersLogin()).get(0)=="tata");
	}

	/*
	 * Test method for 'xagdop.Model.Projects.isManager(int)'
	 */
	public void testIsManager() {
//		ArrayList list= new ArrayList();
//		ArrayList id= new ArrayList();
//		ProjectsParser pj = new ProjectsParser();
//		UsersParser up = new UsersParser();
//		Projects p = new Projects("blabla",list,id);
//		Users user = new Users("toto", "tata", 0, false, false);
//		pj.addProject("blbl",user,"blabla");
//		up.addUser(user);
//		pj.setAttribute("blbl",0,"pmanager","true");
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.isArchitecte(int)'
	 */
	public void testIsArchitecte() {

	}

	/*
	 * Test method for 'xagdop.Model.Projects.isAnalyst(int)'
	 */
	public void testIsAnalyst() {

	}

	/*
	 * Test method for 'xagdop.Model.Projects.isRedacteur(int)'
	 */
	public void testIsRedacteur() {

	}

}