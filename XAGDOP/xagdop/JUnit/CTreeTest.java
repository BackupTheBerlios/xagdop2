package xagdop.JUnit;

import org.tmatesoft.svn.core.SVNException;

import junit.framework.TestCase;
import xagdop.Controleur.*;
import xagdop.Util.ErrorManager;
public class CTreeTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CTree.CTree()'
	 */
	public void testCTree() {

	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.setRoot(CTreeNode)'
	 */
	public void testSetRoot() {
			CTreeNode CTN = new CTreeNode("blabla",false) ;
			CTree CT = null;
			try {
				CT = new CTree();
				CT.setRoot(CTN);
				assertTrue(CTN==(CTreeNode)CT.getRoot());
			} catch (SVNException e) {
				ErrorManager.getInstance().display();
			}
			
			
			
			
	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.getRoot()'
	 */
	public void testGetRoot() {
			CTreeNode CTN = new CTreeNode("blabla",false) ;
			CTree CT;
			try {
				CT = new CTree();
				CT.setRoot(CTN);
				assertTrue(CTN==(CTreeNode)CT.getRoot());
			} catch (SVNException e) {
				ErrorManager.getInstance().display();
			}
			
			
			
	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.getChild(Object, int)'
	 */
	public void testGetChild() {
//		CTreeNode parent= new CTreeNode("toto",false);
//		CTreeNode CTN = new CTreeNode("blabla",false);
//		CTree CT = new CTree();
//		CT.setRoot(parent);
//		parent.add(CTN);
		//assertEquals(((CTreeNode)CT.getChild(parent,0)),(CTreeNode)CTN);

	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.getChildCount(Object)'
	 */
	public void testGetChildCount() {
//		CTreeNode parent= new CTreeNode("toto",false);
//		CTreeNode CTN = new CTreeNode("blabla",false);
//		CTree CT = new CTree();
//		CT.setRoot(parent);
//		assertEquals(CT.getChildCount(parent),1);

	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.isLeaf(Object)'
	 */
	public void testIsLeaf() {
		
	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.getIndexOfChild(Object, Object)'
	 */
	public void testGetIndexOfChild() {

	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.getPathToRoot(CTreeNode)'
	 */
	public void testGetPathToRoot() {

	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.addTreeModelListener(TreeModelListener)'
	 */
	public void testAddTreeModelListener() {

	}

	/*
	 * Test method for 'xagdop.Controleur.CTree.removeTreeModelListener(TreeModelListener)'
	 */
	public void testRemoveTreeModelListener() {

	}

}
