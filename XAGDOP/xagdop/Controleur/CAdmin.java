package xagdop.Controleur;


import xagdop.Parser.UsersParser;


public class CAdmin {

public void Apply(UsersParser UP,int user,boolean admin, boolean pmanager){
		//Call to XML Parser
		String valeur="";
		
		//Apply for Redacteur Value
		if (pmanager)
			valeur="true";
		else
			valeur="false";
		
		UP.setAttribute(user,"pmanager",valeur);
		
		//Apply for Architect Value		
		if (admin)
			valeur="true";
		else
			valeur="false";
		
		UP.setAttribute(user,"admin",valeur);
		
		
		
		
		
		
		//Send file
		
	}

}