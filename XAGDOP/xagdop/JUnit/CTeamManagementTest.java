package xagdop.JUnit;

import junit.framework.TestCase;


import xagdop.Controleur.CTeamManagement;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;

public class CTeamManagementTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CTeamManagement.Apply(ProjectsParser, String, boolean, boolean, boolean, boolean)'
	 */
	public void testApply() {
		//Ajouter un nouveau utilisateur  dans UsersParser
		UsersParser usrp= null;
		User us = null;
		try {
			usrp = UsersParser.getInstance();
			us = new User("toto","totopass",false,true);
			usrp.addUser(us);
			
			//Cr?er un nouveau utilisateur et un projet
			ProjectsParser pp= ProjectsParser.getInstance();
			pp.addProject("projTest",us,"descrTest");
			
			//D?clarer CteamManagement  avec le projet
			CTeamManagement CT = new CTeamManagement("projTest");
			
			//Appliquer  les droits de l'utilisateur pour le projet
			CT.Apply(us.getLogin(),true,false,true,false);
			
			//Test
			assertTrue(us.isAnalyst("projTest")==false);
			
			//Supprimer l'utilisateur de UsersParsers
			
			usrp.removeUser(us.getLogin());
			
			//Supprimer le projet de ProjectsParsers
			pp.removeProject("projTest");
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
	}

	/*
	 * Test method for 'xagdop.Controleur.CTeamManagement.disaffectUser(ProjectsParser, String)'
	 */
	public void testDisaffectUser(){
		//Ajouter des utilisateurs
		User usr=new User("todis","totoPass",true,true);
		User usr1=new User("tadis","tataPass",true,false);
		UsersParser up=null;
		try {
			up = UsersParser.getInstance();
			up.addUser(usr);
			up.addUser(usr1);

			//Cr?er un projet
			ProjectsParser pp= ProjectsParser.getInstance();
			pp.addProject("projTest",usr,"descrTest");
			
			//D?clarer CteamManagement  avec le projet
			CTeamManagement CT = new CTeamManagement("projTest");
			
			
			//Ajouter l'utilisateur urs1 au projet
			pp.addUser(CT.get_projectName(),usr.getLogin());
			pp.addUser(CT.get_projectName(),usr1.getLogin());
					
			
			//Supprimer l'utilisateur usr du projet
			CT.disaffectUser(pp,usr.getLogin());
			
			
			
			//Tests
			//assertTrue(pp.isUserInProject(CT.get_projectName(),usr1.getLogin()));
			//assertFalse(!pp.isUserInProject(CT.get_projectName(),usr.getLogin()));
	
			//Supprimer les utilisateurs de UsersParsers
			
			up.removeUser(usr.getLogin());
			up.removeUser(usr1.getLogin());
			
			

			//Supprimer le projet de ProjectsParsers
			pp.removeProject("projTest");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ErrorManager.getInstance().display();
		}
		
	}

	/*
	 * Test method for 'xagdop.Controleur.CTeamManagement.get_projectName()'
	 */
	public void testGet_projectName() {
		
		//D?clarer CteamManagement  avec le projet
		CTeamManagement CT = new CTeamManagement("projTest");
		assertEquals(CT.get_projectName(),"projTest");
	}

	/*
	 * Test method for 'xagdop.Controleur.CTeamManagement.set_projectName(String)'
	 */
	public void testSet_projectName() {
		//D?clarer CteamManagement  avec le projet
		CTeamManagement CT = new CTeamManagement("projTest");
		
		//Changer le nom du projet
		CT.set_projectName("nouvoProjTest");
		
		//Tests  
		assertEquals(CT.get_projectName(),"nouvoProjTest");
		assertFalse(CT.get_projectName()=="projTest");

	}

}
