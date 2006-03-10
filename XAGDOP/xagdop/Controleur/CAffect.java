package xagdop.Controleur;



import javax.management.InstanceNotFoundException;

import xagdop.Interface.Management.IJTeamManagement;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;



public class CAffect {
	
	public void affecter(String project,String login, boolean pmanager,boolean archi, boolean redactor, boolean analyst) throws Exception,InstanceNotFoundException
	{
		ProjectsParser pp = ProjectsParser.getInstance();
		UsersParser up = UsersParser.getInstance();
		if (up.isUser(login))
		{			
				if (!pp.isUserInProject(project,login))
				{	
					pp.addUser(project, login, pmanager, archi, analyst,redactor);
					IJTeamManagement.getIJTM(project).refreshUsers();
				}
				else{
					ErrorManager.getInstance().setErrMsg(Bundle.getText("controleur.theUser") + " " +login+ " " + Bundle.getText("caffect.userNotBelongs")+ " "+project+"\n" + Bundle.getText("controleur.contactAdmin"));
					ErrorManager.getInstance().setErrTitle(Bundle.getText("controleur.userUnknown"));
					throw new Exception();
				}
		}else
		{
			ErrorManager.getInstance().setErrMsg(Bundle.getText("controleur.theUser") + " " +login+" " + Bundle.getText("caffect.userNotExists"));
			ErrorManager.getInstance().setErrTitle(Bundle.getText("controleur.userUnknown"));
			throw new InstanceNotFoundException();
		}
	}			
}