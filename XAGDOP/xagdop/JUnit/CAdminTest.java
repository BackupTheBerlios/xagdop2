package xagdop.JUnit;

import xagdop.Controleur.CAdmin;
import xagdop.Model.Users;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class CAdminTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CAdmin.Apply(UsersParser, int, boolean, boolean)'
	 */
	public void testApply() {
		//Declaration
		UsersParser usrp = new UsersParser();
		CAdmin cadmin = new CAdmin();
		
		//Creation d'un nouveau utilisateur
		Users usr = new Users("toto","totopass",1,true,false);
		
		//Ajouter un nouveau utilisateur dans UsersParsers
		usrp.addUser(usr);
		
		//Modification des attributs de l'utilisateur
		cadmin.Apply(usrp,usr.getId(),false,true);
		
		//Tests
		assertTrue((usrp.getUserById(usr.getId())).isPmanager()==true);
		assertTrue((usrp.getUserById(usr.getId())).isAdmin()==false);

	}

}
