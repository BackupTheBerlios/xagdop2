package xagdop.JUnit;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;

import junit.framework.TestCase;
import xagdop.Controleur.CRole;
import xagdop.Interface.XAGDOP;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;

public class CRoleTest extends TestCase {

	 
	
	/*
	 * Test method for 'xagdop.Controleur.CRole.getViewFileRight()'
	 */
	public void testGetViewFileRight() {
		//Declarations
		ProjectsParser pp = ProjectsParser.getInstance();
		UsersParser usrp = null;
		User us  = null;
		try {
			usrp = UsersParser.getInstance();
//			Creer d'un utilisateur
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		
		
		//Creer un projet
		pp.addProject("projTest",us,"projDesc");
		
		//Ajouter l'utilisateur au projet
		pp.addUser("projTest",us,true,false,true,false);
		
		XAGDOP.getInstance().setUser(us);
		CRole cr= new CRole("projTest");
		
		
		//Tests
		assertEquals((cr.getViewFileRight()).get(0),".pref");
		assertEquals((cr.getViewFileRight()).get(1),".pog");
		assertEquals((cr.getViewFileRight()).get(2),".apes");
		
		
	
		//Supprimer l'utilisateur
		try {
			usrp.removeUser(us.getLogin());
		} catch (XPathExpressionException e) {
			ErrorManager.getInstance().display();
		} catch (DOMException e) {
			ErrorManager.getInstance().display();
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		//Supprimer le projet
		pp.removeProject("projTest");
		

	}

	/*
	 * Test method for 'xagdop.Controleur.CRole.getViewDirectoryRight()'
	 */
	public void testGetViewDirectoryRight() {
		//Declarations
		ProjectsParser pp = ProjectsParser.getInstance();
		UsersParser usrp = null;
		User us = null;
		try {
			usrp = UsersParser.getInstance();
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		//Creer d'un utilisateur
		
		
		//Creer un projet
		pp.addProject("projTest",us,"projDesc");
		
		//Ajouter l'utilisateur au projet
		pp.addUser("projTest",us,false,true,false,true);
		
		XAGDOP.getInstance().setUser(us);
		CRole cr= new CRole("projTest");
		
		//Tests
		assertEquals((cr.getForbidenViewDirectoryRight()).get(0),"lib");
		assertEquals((cr.getForbidenViewDirectoryRight()).get(1),"lib");
		
		
		
	
		//Supprimer l'utilisateur
		try {
			usrp.removeUser(us.getLogin());
		} catch (XPathExpressionException e) {
			ErrorManager.getInstance().display();
		} catch (DOMException e) {
			ErrorManager.getInstance().display();
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		//Supprimer le projet
		pp.removeProject("projTest");
		

	}

	/*
	 * Test method for 'xagdop.Controleur.CRole.getWriteFileRight()'
	 */
	public void testGetWriteFileRight() {
		//Declarations
		ProjectsParser pp = ProjectsParser.getInstance();
		UsersParser usrp= null;
		User us = null;
		try {
			usrp = UsersParser.getInstance();
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		//Creer d'un utilisateur
		
		
		//Creer un projet
		pp.addProject("projTest",us,"projDesc");
		
		//Ajouter l'utilisateur au projet
		pp.addUser("projTest",us,true,false,true,false);
		
		XAGDOP.getInstance().setUser(us);
		CRole cr= new CRole("projTest");
		
		//Tests
		assertEquals((cr.getWriteFileRight()).get(0),".xml");
		assertEquals((cr.getWriteFileRight()).get(1),".pref");
		assertEquals((cr.getWriteFileRight()).get(2),".pog");
		
		
	
		//Supprimer l'utilisateur
		try {
			usrp.removeUser(us.getLogin());
		} catch (XPathExpressionException e) {
			ErrorManager.getInstance().display();
		} catch (DOMException e) {
			ErrorManager.getInstance().display();
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		//Supprimer le projet
		pp.removeProject("projTest");
		

	}

	

}
