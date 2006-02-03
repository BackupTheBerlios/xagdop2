package xagdop.JUnit;

import junit.framework.TestCase;
import xagdop.Model.*;
import xagdop.Parser.*;


import java.util.ArrayList;
public class ProjectTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Projects.getProjectName()'
	 */
	public void testGetProjectName() {
			
			
			//Creation d'un nouveau  projet
			Project p = new Project("blabla");
			
			//Test
			assertEquals(p.getProjectName(),"blabla");
			assertFalse(p.getProjectName()=="blibli");
	}

	/*
	 * Test method for 'xagdop.Model.Projects.setProjectName(String)'
	 */
	public void testSetProjectName() {
	
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
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
		ArrayList right= new ArrayList();
	
	
		
		
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
		//Ajouter un login
		p.addUser("toto",right);
		
		//Test
		assertTrue((p.getUsersLogin()).get(0)=="toto");
		
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.addUser(String, ArrayList)'
	 */
	public void testAddUser() {
		//Declarations
		ArrayList right= new ArrayList();
	
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
		//Ajouter un login
		p.addUser("toto",right);
		
		//Test
		assertTrue((p.getUsersLogin()).get(0)=="toto");
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.isManager(int)'
	 */
	public void testIsManager() {
		//Declarations
		ArrayList right= new ArrayList();
	
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
		//Affecter les droits
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		
		//Ajouter un login
		p.addUser("toto",right);
		
		assertTrue(p.isManager((String)(p.getUsersLogin()).get(0)));
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.isArchitecte(int)'
	 */
	public void testIsArchitecte() {
		//Declarations
		ArrayList right= new ArrayList();
	
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
		//Affecter les droits
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		
		//Ajouter un login
		p.addUser("toto",right);
		
		assertTrue(!p.isArchitect((String)(p.getUsersLogin()).get(0)));
		

	}

	/*
	 * Test method for 'xagdop.Model.Projects.isAnalyst(int)'
	 */
	public void testIsAnalyst() {
		//Declarations
		ArrayList right= new ArrayList();
	
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
		//Affecter les droits
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		
		//Ajouter un login
		p.addUser("toto",right);
		
		assertTrue(p.isAnalyst((String)(p.getUsersLogin()).get(0)));
		

	}

	/*
	 * Test method for 'xagdop.Model.Projects.isRedacteur(int)'
	 */
	public void testIsRedacteur() {
		//Declarations
		ArrayList right= new ArrayList();
	
		//Creation d'un nouveau projet
		Project p = new Project("blabla");
		
		//Affecter les droits
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		right.add(Boolean.valueOf(true));
		right.add(Boolean.valueOf(false));
		
		//Ajouter un login
		p.addUser("toto",right);
		
		assertTrue(!p.isRedactor((String)(p.getUsersLogin()).get(0)));
		
	}

}
