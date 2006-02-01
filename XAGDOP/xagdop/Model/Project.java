package xagdop.Model;

import java.util.ArrayList;

public class Project {

	private String projectName;
	
	//La liste contient pour chaque User, la liste des droits
	//manager, architecte, analyste, redacteur sous forme de booleans
	private ArrayList rights;
	//La liste des logins des utilisateurs travaillant sur le projet
	private ArrayList usersLogin;
	
	public Project(String name, ArrayList rights, ArrayList logins)
	{
		this.projectName = name;
		this.rights = rights;
		this.usersLogin = logins;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ArrayList getUsersLogin() {
		return usersLogin;
	}

	public void setUsersLogin(ArrayList usersLogins) {
		this.usersLogin = usersLogins;
	}
	
	public boolean isManager(String login)
	{
		boolean res = false;
		int index = usersLogin.indexOf(login);
		ArrayList droits = (ArrayList)rights.get(index);

		if(((Boolean)droits.get(0)).booleanValue()) {
			res = true;
		}
		return res;
	}
	
	public boolean isArchitect(String login)
	{
		boolean res = false;
		int index = usersLogin.indexOf(login);
		ArrayList droits = (ArrayList)rights.get(index);
		
		if(((Boolean)droits.get(1)).booleanValue()) {
			res = true;
		}
		return res;
	}
	
	public boolean isAnalyst(String login)
	{
		boolean res = false;
		int index = usersLogin.indexOf(login);
		ArrayList droits = (ArrayList)rights.get(index);
		
		if(((Boolean)droits.get(2)).booleanValue()) {
			res = true;
		}
		return res;
	}
	
	public boolean isRedactor(String login)
	{
		boolean res = false;
		int index = usersLogin.indexOf(login);
		ArrayList droits = (ArrayList)rights.get(index);
		
		if(((Boolean)droits.get(3)).booleanValue()) {
			res = true;
		}
		return res;
	}
}
