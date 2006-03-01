package xagdop.Controleur;

import java.io.IOException;

import xagdop.Interface.IWaiting;
import xagdop.Parser.ProjectsParser;
import xagdop.Thread.ThreadRemoveUser;

public class CTeamManagement{
	
	String _projectName;
	public void Apply(String login, boolean architect, boolean analyst, boolean redacter,boolean pmanager) throws IOException, Exception{
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
		
		ProjectsParser.getInstance().setRights(this._projectName, login, pmanager,architect,analyst,redacter);
		
		
	}
	
	public CTeamManagement(String projectName){
		this._projectName=projectName;
		
	}

	
	public void disaffectUser(ProjectsParser PP,String user) throws Exception{
		//Remove the user from the project
    	IWaiting iWait = IWaiting.getInstance();
    	iWait.demarrer();
		ThreadRemoveUser tru = new ThreadRemoveUser(PP,this._projectName,user);
		tru.start();
	}
			
			
			
			
	public String get_projectName() {
		return _projectName;
	}

	public void set_projectName(String name) {
		_projectName = name;
	}
	
	
	
}