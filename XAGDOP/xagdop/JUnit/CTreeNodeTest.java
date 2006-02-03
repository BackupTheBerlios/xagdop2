package xagdop.JUnit;

import java.io.File;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IPreferences;
import junit.framework.TestCase;

public class CTreeNodeTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.isProject()'
	 */
	public void testIsProject() {

	}

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.getProject()'
	 */
	public void testGetProject() {

	}

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.getLocalPath()'
	 */
	public void testGetLocalPath() {
		CTreeNode CTN = new CTreeNode("Projects List",IPreferences.getDefaultPath(),false); 
		assertEquals(CTN.getLocalPath()+File.separator,IPreferences.getDefaultPath()); 
	}

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.setLocalPath(String)'
	 */
	public void testSetLocalPath() {
		CTreeNode CTN = new CTreeNode("Projects List",false); 
		CTN.setLocalPath(IPreferences.getDefaultPath());
		assertEquals(CTN.getLocalPath()+File.separator,IPreferences.getDefaultPath()); 


	}

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.setIsModified(boolean)'
	 */
	public void testSetIsModified() {
		//A la creation l'attribut modified est defini false par defaut
		CTreeNode CTN = new CTreeNode("Projects List",false);
		
		//Modification de l'attribut modified a true
		CTN.setIsModified(true);
		
		//Test
		assertTrue(CTN.isModified());
		
	

	}

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.isModified()'
	 */
	public void testIsModified() {
		//A la creation l'attribut modified est defini false par defaut
		CTreeNode CTN = new CTreeNode("Projects List",false);
		
		assertTrue(!CTN.isModified());

	}

	/*
	 * Test method for 'xagdop.Controleur.CTreeNode.getName()'
	 */
	public void testGetName() {
		CTreeNode CTN = new CTreeNode("Projects List",false);
		assertEquals(CTN.getName(),"Projects List");
	}

}
