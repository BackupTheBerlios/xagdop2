package xagdop.Controleur;

import javax.management.InstanceNotFoundException;


import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;



public class CAffect {
	
	public void affecter(String project,String login, boolean pmanager,boolean archi, boolean redactor, boolean analyst) throws Exception,InstanceNotFoundException
	{
		ProjectsParser PP = new ProjectsParser();
		UsersParser UP = new UsersParser();
		int id = UP.getId(login);
		if (id==0)
			throw new InstanceNotFoundException();
		else
		{
			if (PP.exist(project,login))
				throw new Exception();
			else
		
			{
				String pMana;
				String arch;
				String redac;
				String anal;
				
			//			Call to XML Parser
				
				
			//	Apply for Redacteur Value
				if (pmanager)
					pMana = "true";
				else
					pMana = "false";
				
				if (archi)
					arch = "true";
				else
					arch = "false";
				
				if (redactor)
					redac = "true";
				else
					redac = "false";
				
				if (analyst)
					anal = "true";
				else
					anal = "false";
				
				
				PP.addUser(project, id, pMana, arch, anal,redac);

			}
		}	
	}
		
	
	
}