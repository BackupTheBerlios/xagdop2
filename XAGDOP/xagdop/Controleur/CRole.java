package xagdop.Controleur;

import java.util.ArrayList;

import xagdop.Interface.XAGDOP;
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
			//System.out.println(current.getLogin()+" : "+_project);
			ProjectsParser pp = ProjectsParser.getInstance();
			//Project project = pp.buildProject(_project);
			//madeRole(project.isManager(current.getLogin()),project.isAnalyst(current.getLogin()),project.isArchitect(current.getLogin()),project.isRedactor(current.getLogin()));
			ArrayList right = pp.getRights(_project,current.getLogin());
			madeRole(((Boolean)right.get(0)).booleanValue(),((Boolean)right.get(2)).booleanValue(),((Boolean)right.get(1)).booleanValue(),((Boolean)right.get(3)).booleanValue());
		}
		
	}
	
	
	private void madeRole(boolean projectManager, boolean analyst, boolean architect, boolean redactor){
		
		
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
		
		viewFile.add(".pog");
		viewFile.add(".apes");
	}
	
	protected void addArchitectRight(){
		writeFile.add(".iepp");
		viewFile.add(".apes");
		viewFile.add(".pog");
		viewDirectory.add("lib*");
	}	
	
	protected void addRedactorRight(){
		writeFile.add(".pog");
		writeDirectory.add("lib*");
		viewFile.add(".pog");
		viewDirectory.add("lib*");
		
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
