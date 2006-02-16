package xagdop.JUnit;

import junit.framework.TestCase;

public class CRoleTest extends TestCase {

	 
	
	/*
	 * Test method for 'xagdop.Controleur.CRole.getViewFileRight()'
	 */
/*	public void testGetViewFileRight() {
		//Declarations
		try {
			ProjectsParser pp = ProjectsParser.getInstance();
			UsersParser usrp = null;
			User us  = null;
			
			usrp = UsersParser.getInstance();
			//Creer d'un utilisateur
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
			
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
			
			usrp.removeUser(us.getLogin());
			
			//Supprimer le projet
			pp.removeProject("projTest");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CRole.getViewDirectoryRight()'
	 */
	/*public void testGetViewDirectoryRight() {
		//Declarations
		try {
			ProjectsParser pp = ProjectsParser.getInstance();
			UsersParser usrp = null;
			User us = null;
			
			usrp = UsersParser.getInstance();
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
			
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
			usrp.removeUser(us.getLogin());
			
			//Supprimer le projet
			pp.removeProject("projTest");
			
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CRole.getWriteFileRight()'
	 */
/*	public void testGetWriteFileRight() {
		//Declarations
		try {
			ProjectsParser pp = ProjectsParser.getInstance();
			UsersParser usrp= null;
			User us = null;
			
			usrp = UsersParser.getInstance();
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
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
			usrp.removeUser(us.getLogin());
			
			//Supprimer le projet
			pp.removeProject("projTest");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}*/

	

}
