package xagdop.Controleur;


import xagdop.Parser.UsersParser;


public class CAdmin {

public void Apply(UsersParser UP,String user,boolean admin, boolean pmanager){
		UP.setAttribute(user,"pmanager",pmanager);
		UP.setAttribute(user,"admin",admin);
	}

}