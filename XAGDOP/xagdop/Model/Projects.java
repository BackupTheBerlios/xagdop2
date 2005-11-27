package xagdop.Model;

import java.util.ArrayList;

public class Projects {

	private String projectName;
	
	//La liste contient pour chaque id de User, la liste des droits
	//manager, architecte, analyste, rédacteur
	private ArrayList users;
	//La liste des identifiants des utilisateurs travaillant sur le projet
	private ArrayList usersId;
	
	public Projects(String name, ArrayList list, ArrayList id)
	{
		projectName = name;
		users = list;
		usersId = id;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ArrayList getUsersId() {
		return usersId;
	}

	public void setUsersId(ArrayList usersId) {
		this.usersId = usersId;
	}
	
	public boolean isManager(int idUser)
	{
		boolean res = false;
		int index = usersId.indexOf(new Integer(idUser));
		ArrayList droits = (ArrayList)users.get(index);
		

		if(((Boolean)droits.get(0)).booleanValue()) {
			res = true;
		}
		
		return res;
	}
	
	public boolean isArchitecte(int idUser)
	{
		boolean res = false;
		int index = usersId.indexOf(new Integer(idUser));
		ArrayList droits = (ArrayList)users.get(index);
		
		if(((Boolean)droits.get(1)).booleanValue()) {
			res = true;
		}
		
		return res;
	}
	
	public boolean isAnalyst(int idUser)
	{
		boolean res = false;
		int index = usersId.indexOf(new Integer(idUser));
		ArrayList droits = (ArrayList)users.get(index);
		
		if(((Boolean)droits.get(2)).booleanValue()) {
			res = true;
		}
		
		return res;
	}
	
	public boolean isRedacteur(int idUser)
	{
		boolean res = false;
		int index = usersId.indexOf(new Integer(idUser));
		ArrayList droits = (ArrayList)users.get(index);
		
		if(((Boolean)droits.get(3)).booleanValue()) {
			res = true;
		}
		
		return res;
	}
}
