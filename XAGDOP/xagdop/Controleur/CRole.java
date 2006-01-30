package xagdop.Controleur;

import java.util.ArrayList;

import xagdop.Interface.XAGDOP;
import xagdop.Model.Project;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;

public class CRole {
	
	protected ArrayList viewFile;
	protected ArrayList viewDirectory;
	protected ArrayList writeFile;
	protected ArrayList writeDirectory;
	
	public CRole(String _project){
		viewFile = new ArrayList();
		writeFile = new ArrayList();
		viewDirectory = new ArrayList();
		writeDirectory = new ArrayList();
		if(_project!=""){
			User current = XAGDOP.getInstance().getUser();
			ProjectsParser pp = ProjectsParser.getInstance();
			Project project = pp.buildProject(_project);
			madeRole(project.isManager(current.getLogin()),project.isAnalyst(current.getLogin()),project.isArchitecte(current.getLogin()),project.isRedacteur(current.getLogin()));
		}
	}
	
	
	private  void madeRole(boolean projectManager, boolean analyst, boolean architect, boolean redactor){
		
		
		writeFile.add(".xml");
		
		if(projectManager)
			addManagerRight();
		
		if(analyst)
			addAnalystRight();
		
		if(architect)
			addArchitectRight();
		
		if(redactor)
			addRedactorRight();
	}
	
	protected void addManagerRight(){
		writeFile.add(".pref");
	}
	
	protected void addAnalystRight(){
		writeFile.add(".pog");
		writeFile.add(".apes");
		
		viewFile.add(".pog");
		viewFile.add(".apes");
		viewFile.add(".pref");
	}
	
	protected void addArchitectRight(){
		writeFile.add(".iepp");
		viewFile.add(".apes");
		viewFile.add(".pog");
		viewDirectory.add("lib");
		viewFile.add(".pref");
	}	
	
	protected void addRedactorRight(){
		writeFile.add(".pog");
		writeDirectory.add("lib*");
		viewFile.add(".pog");
		viewDirectory.add("lib*");
		viewFile.add(".pref");
		
	}
	
	public ArrayList getViewFileRight(){
		return viewFile;
	}
	
	public ArrayList getViewDirectoryRight(){
		return viewDirectory;
	}
	
	public ArrayList getWriteFileRight(){
		return writeFile;
	}
	
	public ArrayList getWriteDirectoryRight(){
		return writeDirectory;
	}
	
}
