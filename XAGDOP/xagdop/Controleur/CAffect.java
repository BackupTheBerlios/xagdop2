package xagdop.Controleur;

import javax.management.InstanceNotFoundException;


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
			if (PP.isProject(project))
				throw new Exception();
			else
		
			{
				
				PP.addUser(project, login, pmanager, archi, analyst,redactor);

			}
		}	
	}
		
	
	
}