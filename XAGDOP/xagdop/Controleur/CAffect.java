package xagdop.Controleur;

import javax.management.InstanceNotFoundException;


import xagdop.Interface.IJTeamManagement;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;



public class CAffect {
	
	public void affecter(String project,String login, boolean pmanager,boolean archi, boolean redactor, boolean analyst) throws Exception,InstanceNotFoundException
	{
		ProjectsParser PP = ProjectsParser.getInstance();
		UsersParser UP = UsersParser.getInstance();
		if (!UP.isUser(login))
			throw new InstanceNotFoundException();
		else
		{			
				if (!PP.isUserInProject(project,login))
				{
				System.out.println("good");
				try {
					PP.addUser(project, login, pmanager, archi, analyst,redactor);
					IJTeamManagement.getIJTM(project).refreshUsers();
				}
				catch (Exception e)
				{e.printStackTrace();}
				
				}
				else
					throw new Exception();
		}	
	}
		
}