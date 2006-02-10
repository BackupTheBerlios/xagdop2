package xagdop.JUnit;



import junit.framework.TestCase;
import xagdop.Controleur.CAffect;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;


public class CAffectTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CAffect.affecter(String, String, boolean, boolean, boolean, boolean)'
	 */
	public void testAffecter()  {
		CAffect CA = new CAffect();
		
		//Creer un nouveau utilisateur
		User us = new User("toto","totopass",true,false);
		UsersParser usrp=null;
		
			usrp = UsersParser.getInstance();
			try {
				usrp.addUser(us);
			
			
			
			//Creer un nouveau projet
			ProjectsParser pp = ProjectsParser.getInstance();
			
			//Tests
			CA.affecter("projTest",us.getLogin(),true,false,true,false);
			assertTrue(us.isRedactor("projTest"));
			assertTrue(!us.isAnalyst("projTest"));
			assertTrue(us.isPManager("projTest"));
			assertTrue(!us.isArchitect("projTest"));
			
			//Supprimer l'utilisateur
			
			usrp.removeUser("toto");
			
			//Supprimer le projet
			pp.removeProject("projTest");
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   
	}

}
