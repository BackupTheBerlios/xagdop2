package xagdop.Controleur;


import org.tmatesoft.svn.core.SVNException;

import xagdop.Parser.UsersParser;


public class CAdmin {

public void Apply(UsersParser UP,String user,boolean admin, boolean pmanager) throws NullPointerException, SVNException, Exception{
		UP.setAttribute(user,"pcreator",pmanager);
		UP.setAttribute(user,"admin",admin);
	}

}