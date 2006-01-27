package xagdop.JUnit;

import java.util.ArrayList;

import javax.management.InstanceNotFoundException;

import xagdop.Controleur.CAffect;
import xagdop.Model.*;
import xagdop.Parser.UsersParser;
import junit.framework.TestCase;

public class CAffectTest extends TestCase {

	/*
	 * Test method for 'xagdop.Controleur.CAffect.affecter(String, String, boolean, boolean, boolean, boolean)'
	 */
	public void testAffecter() {
		CAffect CA = new CAffect();
		ArrayList list= new ArrayList();
		ArrayList id =new ArrayList();
		UsersParser UP = UsersParser.getInstance();
		Project p = new Project("blabla",list,id);
		User us = new User("toto","totopass",true,false);
		
		try {
			CA.affecter(p.getProjectName(),us.getLogin(),true,false,true,false);
			assertTrue(p.isAnalyst(us.getLogin())==false);
			assertFalse(p.isManager(us.getLogin())==false);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		   
	}

}
