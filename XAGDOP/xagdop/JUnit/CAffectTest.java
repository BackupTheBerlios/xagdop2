package xagdop.JUnit;



import javax.management.InstanceNotFoundException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;

import xagdop.Controleur.CAffect;
import xagdop.Model.*;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;

import junit.framework.TestCase;

public class CAffectTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CAffect.affecter(String, String, boolean, boolean, boolean, boolean)'
	 */
	public void testAffecter() {
		CAffect CA = new CAffect();
		
		//Creer un nouveau utilisateur
		User us = new User("toto","totopass",true,false);
		UsersParser usrp=null;
		try {
			usrp = UsersParser.getInstance();
			usrp.addUser(us);
		} catch (Exception e1) {
			ErrorManager.getInstance().display();
		}
		
		
		//Creer un nouveau projet
		ProjectsParser pp = ProjectsParser.getInstance();
		pp.addProject("projTest",us,"projDescr");

		try {
			//Tests
			CA.affecter("projTest",us.getLogin(),true,false,true,false);
			assertTrue(us.isRedactor("projTest"));
			assertTrue(!us.isAnalyst("projTest"));
			assertTrue(us.isPManager("projTest"));
			assertTrue(!us.isArchitect("projTest"));
			
		} catch (InstanceNotFoundException e) {
			ErrorManager.getInstance().display();
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		
		//Supprimer l'utilisateur
		try {
			usrp.removeUser("toto");
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
