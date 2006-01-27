package xagdop.JUnit;

import xagdop.Controleur.CAdmin;
import xagdop.Model.User;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class CAdminTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CAdmin.Apply(UsersParser, int, boolean, boolean)'
	 */
	public void testApply() {
		//Declaration
		UsersParser usrp = UsersParser.getInstance();
		CAdmin cadmin = new CAdmin();
		
		//Creation d'un nouveau utilisateur
		User usr = new User("toto","totopass",true,false);
		
		//Ajouter un nouveau utilisateur dans UsersParsers
		usrp.addUser(usr);
		
		//Modification des attributs de l'utilisateur
		cadmin.Apply(usrp,usr.getLogin(),false,true);
		
		//Tests
		assertTrue((usrp.getUserByLogin(usr.getLogin())).isPcreator()==true);
		assertTrue((usrp.getUserByLogin(usr.getLogin())).isAdmin()==false);

		//Supprimer l'utilisateur ajoute a UsersParser
		usrp.removeUser(usr.getLogin());
	}

}
