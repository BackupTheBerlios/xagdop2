package xagdop.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {

	private String projectName;
	
	//Contient pour chaque personne travaillant sur le projet la liste de ses droits sur le projet
	//Donc en clef login, et la valeur associ??e une ArrayList contenant des booleans pour les droits
	//pmanager, architect, analyst, redactor 
	private HashMap usersAndRightsOnProject;
	
	public Project(String name)
	{
		this.projectName = name;
		usersAndRightsOnProject = new HashMap();
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public ArrayList getUsersLogin() {		
		return new ArrayList(usersAndRightsOnProject.keySet());
	}

	/*
	public void setUsersLogin(ArrayList usersLogins) {
		this.usersLogin = usersLogins;
	}*/
	
	public boolean isManager(String login)
	{
		ArrayList lrights = (ArrayList)usersAndRightsOnProject.get(login);	
		return ((Boolean)lrights.get(0)).booleanValue();
	}
	
	public boolean isArchitect(String login)
	{
		ArrayList lrights = (ArrayList)usersAndRightsOnProject.get(login);	
		return ((Boolean)lrights.get(1)).booleanValue();
	}

	public boolean isAnalyst(String login)
	{
		ArrayList lrights = (ArrayList)usersAndRightsOnProject.get(login);	
		return ((Boolean)lrights.get(2)).booleanValue();
	}

	public boolean isRedactor(String login)
	{
		ArrayList lrights = (ArrayList)usersAndRightsOnProject.get(login);	
		return ((Boolean)lrights.get(3)).booleanValue();
	}
	
	public void addUser(String login, ArrayList lrights)
	{
		usersAndRightsOnProject.put(login, lrights);
	}
}
