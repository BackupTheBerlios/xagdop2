package xagdop.Controleur;

import xagdop.Parser.ProjectsParser;

public class CTeamManagement{
	
	String _projectName;
	public void Apply(ProjectsParser PP,String login, boolean architect, boolean analyst, boolean redacter,boolean pmanager){
		//Call to XML Parser
		//String valeur="";
		
		/*
		//Apply for Redacteur Value
		if (redacter)
			valeur="true";
		else
			valeur="false";
		
		PP.setAttribute(this._projectName,user,"redacteur",valeur);
		
		//Apply for Architect Value		
		if (architect)
			valeur="true";
		else
			valeur="false";
		
		PP.setAttribute(this._projectName,user,"archi",valeur);
		
		
		//Apply for Analyst Value
		if (analyst)
			valeur="true";
		else
			valeur="false";
		
		PP.setAttribute(this._projectName,user,"analyste",valeur);
		
		//Apply for Analyst Value
		if (pmanager)
			valeur="true";
		else
			valeur="false";
		
		PP.setAttribute(this._projectName,user,"pmanager",valeur);
		
		*/
		
		PP.setRights(this._projectName, login, pmanager,architect,analyst,redacter);
		
		
		//Send file
		
	}
	
	public CTeamManagement(String projectName){
		this._projectName=projectName;
		
	}

	
	public void disaffectUser(ProjectsParser PP,String user){
		//Remove the user from the project
		
		PP.removeUser(this._projectName,user);
	
	
	
	
	
	
	
	}
			
			
			
			
	public String get_projectName() {
		return _projectName;
	}

	public void set_projectName(String name) {
		_projectName = name;
	}
	
	
	
}