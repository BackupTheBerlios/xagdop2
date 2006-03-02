package xagdop.Controleur;



import javax.management.InstanceNotFoundException;

import xagdop.Interface.Management.IJTeamManagement;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;



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
					ErrorManager.getInstance().setErrMsg("L'utilisateur "+login+" ne fais pas parti du projet "+project+".\nVeuillez contacter un administrateur.");
					ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
					throw new Exception();
				}
		}else
		{
			ErrorManager.getInstance().setErrMsg("L'utilisateur "+login+" n'existe pas.");
			ErrorManager.getInstance().setErrTitle("Utilisateur inconnu");
			throw new InstanceNotFoundException();
		}
		
	}
		
}