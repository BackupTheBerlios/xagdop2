package xagdop.Controleur;

import java.util.ArrayList;

import xagdop.Interface.XAGDOP;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;

public class CRole {
	
	protected ArrayList viewFile;
	protected ArrayList forbidenViewDirectory;
	protected ArrayList writeFile;
	protected ArrayList forbidenWriteDirectory;
	protected boolean projectManager = false;
	protected boolean analyst = false;
	protected boolean architect = false;
	protected boolean redactor = false;
	
	public CRole(String _project){
		viewFile = new ArrayList();
		writeFile = new ArrayList();
		forbidenViewDirectory = new ArrayList();
		forbidenWriteDirectory = new ArrayList();
		if(_project!=""){
			User current = XAGDOP.getInstance().getUser();
			//System.out.println(current.getLogin()+" : "+_project);
			ProjectsParser pp = ProjectsParser.getInstance();
			//Project project = pp.buildProject(_project);
			//madeRole(project.isManager(current.getLogin()),project.isAnalyst(current.getLogin()),project.isArchitect(current.getLogin()),project.isRedactor(current.getLogin()));
			ArrayList right = pp.getRights(_project,current.getLogin());
			projectManager = ((Boolean)right.get(0)).booleanValue();
			architect = ((Boolean)right.get(1)).booleanValue();
			analyst = ((Boolean)right.get(2)).booleanValue();
			redactor = ((Boolean)right.get(3)).booleanValue();
			madeRole();
		}
		
	}
	
	
	private void madeRole(){
		
		
		writeFile.add(".xml");
		viewFile.add(".pref");
		/*System.out.println("Manager : "+projectManager);
		System.out.println("Archi : "+architect);
		System.out.println("Redactor : "+redactor);
		System.out.println("analyst : "+analyst);*/
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
		//viewFile.add("lib");
		
		viewFile.add(".pog");
		viewFile.add(".apes");
		//forbidenViewDirectory.add("lib");
	}
	
	protected void addArchitectRight(){
		writeFile.add(".iepp");
		viewFile.add(".apes");
		viewFile.add(".pog");
	
	}	
	
	protected void addRedactorRight(){
		writeFile.add(".pog");
		viewFile.add(".pog");
		
	}
	
	public ArrayList getViewFileRight(){
		return viewFile;
	}
	
	public ArrayList getForbidenViewDirectoryRight(){
		return forbidenViewDirectory;
	}
	
	public ArrayList getWriteFileRight(){
		return writeFile;
	}
	
	public ArrayList getForbidenWriteDirectoryRight(){
		return forbidenWriteDirectory;
	}


	public boolean isAnalyst() {
		return analyst;
	}


	public boolean isArchitect() {
		return architect;
	}


	public boolean isProjectManager() {
		return projectManager;
	}


	public boolean isRedactor() {
		return redactor;
	}
	
}
