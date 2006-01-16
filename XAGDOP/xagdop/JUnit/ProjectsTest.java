package xagdop.JUnit;

import junit.framework.TestCase;
import xagdop.Model.*;
import java.util.ArrayList;
public class ProjectsTest extends TestCase {



	/*
	 * Test method for 'xagdop.Model.Projects.getProjectName()'
	 */
	public void testGetProjectName() {
			ArrayList list= new ArrayList();
			ArrayList id =new ArrayList();
			
			Projects p = new Projects("blabla",list,id);
			assertEquals(p.getProjectName(),"blabla");
			assertFalse(p.getProjectName()=="blibli");
	}

	/*
	 * Test method for 'xagdop.Model.Projects.setProjectName(String)'
	 */
	public void testSetProjectName() {
		ArrayList list= new ArrayList();
		ArrayList id =new ArrayList();
		
		Projects p = new Projects("blabla",list,id);
		p.setProjectName("blibli");
		assertEquals(p.getProjectName(),"blibli");
		assertFalse(p.getProjectName()=="blabla");

	}

	/*
	 * Test method for 'xagdop.Model.Projects.getUsersId()'
	 */
	public void testGetUsersId() {
		ArrayList list= new ArrayList();
		ArrayList id =new ArrayList();
		Projects p = new Projects("blabla",list,id);
		id.add("toto");
		
		assertEquals((p.getUsersId()).get(0),"toto");
		assertFalse((p.getUsersId()).get(0)!="toto");
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.setUsersId(ArrayList)'
	 */
	public void testSetUsersId() {
		ArrayList list= new ArrayList();
		ArrayList id= new ArrayList();
		ArrayList id1 =new ArrayList();
		Projects p = new Projects("blabla",list,id);
		id.add("tata");
		id1.add("toto");
		
		p.setUsersId(id1);
		assertEquals((p.getUsersId()).get(0),"toto");
		assertFalse((p.getUsersId()).get(0)=="tata");
		
	}

	/*
	 * Test method for 'xagdop.Model.Projects.isManager(int)'
	 */
	public void testIsManager() {
		
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
