package xagdop.Controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;
import sun.security.jca.GetInstance;

import xagdop.Interface.XAGDOP;
import xagdop.Model.Project;
import xagdop.Parser.ProjectsParser;
import xagdop.Util.ErrorManager;

public class CRole {
	
	protected HashMap projectsList;
	protected static CRole role;
	
	
	
	protected ArrayList viewFile;
	protected ArrayList forbidenViewDirectory;
	protected ArrayList writeFile;
	protected ArrayList forbidenWriteDirectory;
	
	
	protected CRole() {
		
		projectsList = new HashMap();
		
		try {
			refreshRole();
		} catch (Exception e) {
			
			ErrorManager.getInstance().display();
		}
		
	}
	
	public static CRole getInstance() {
		if(role == null)
			role = new CRole();
		return role;
	}
	
	
	
	public void refreshRole() throws XPathExpressionException, SVNException, IOException, Exception{
		ArrayList projects = ProjectsParser.getInstance().getProjects(XAGDOP.getInstance().getUser().getLogin());
		for(int i = 0; i < projects.size() ; i++){
			//System.out.println((String)projects.get(i));
			Project tmpPj = ProjectsParser.getInstance().buildProject((String)projects.get(i));
			ArrayList right = new ArrayList();
			right.add(tmpPj);
			projectsList.put((String)projects.get(i),right);
			madeRole((String)projects.get(i));
			right.add(writeFile);
			right.add(viewFile);
			
			projectsList.put((String)projects.get(i),right);
		}
	}
	
	
	private void madeRole(String project){
		viewFile = new ArrayList();
		writeFile = new ArrayList();
		
		
		writeFile.add(".xml");
		viewFile.add(".pref");
		/*System.out.println("Manager : "+isProjectManager(project));
		 System.out.println("Archi : "+isArchitect(project));
		 System.out.println("Redactor : "+isRedactor(project));
		 System.out.println("analyst : "+isAnalyst(project));*/
		if(isProjectManager(project))
			addManagerRight();
		
		if(isAnalyst(project))
			addAnalystRight();
		
		if(isArchitect(project))
			addArchitectRight();
		
		if(isRedactor(project))
			addRedactorRight();
	}
	
	protected void addManagerRight(){
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
		viewFile.add(".iepp");
		viewFile.add(".apes");
		viewFile.add(".pog");
		
	}	
	
	protected void addRedactorRight(){
		writeFile.add(".pog");
		viewFile.add(".pog");
		
	}
	
	public ArrayList getViewFileRight(String project){
		ArrayList tmp = (ArrayList)projectsList.get(project);
		if(tmp != null){
			return (ArrayList) tmp.get(2);
		}
			
		return null;
	}
	
	public ArrayList getForbidenViewDirectoryRight(String project){
		return forbidenViewDirectory;
	}
	
	public ArrayList getWriteFileRight(String project){
		ArrayList tmp = (ArrayList)projectsList.get(project);
		if(tmp != null)
			return (ArrayList) tmp.get(1);
		return null;
	}
	
	public ArrayList getForbidenWriteDirectoryRight(String project){
		return forbidenWriteDirectory;
	}
	
	
	public boolean isAnalyst(String project) {
		ArrayList tmp = (ArrayList)projectsList.get(project);
		
		if(tmp != null)
			return ((Project)tmp.get(0)).isAnalyst(XAGDOP.getInstance().getUser().getLogin());
		return false;
	}
	
	
	public boolean isArchitect(String project) {
		ArrayList tmp = (ArrayList)projectsList.get(project);
		if(tmp != null)
			return ((Project)tmp.get(0)).isArchitect(XAGDOP.getInstance().getUser().getLogin());
		return false;
	}
	
	
	public boolean isProjectManager(String project) {
		ArrayList tmp = (ArrayList)projectsList.get(project);
		if(tmp != null)
			return ((Project)tmp.get(0)).isManager(XAGDOP.getInstance().getUser().getLogin());
		return false;
	}
	
	
	public boolean isRedactor(String project) {
		ArrayList tmp = (ArrayList)projectsList.get(project);
		if(tmp != null)
			return ((Project)tmp.get(0)).isRedactor(XAGDOP.getInstance().getUser().getLogin());
		return false;
	}
	
	
	public boolean canShow(File file,String project){
		if(file.isHidden())
			return false;
		
		
		if(file.isDirectory()){
			if(!isArchitect(project)&&file.getName().startsWith("lib"))
				return false;
			
			return true;
		}
		
		
		
			ArrayList view = getViewFileRight(project);
			//System.out.println("bla : "+view.size());
			if(file.getParentFile().getName().startsWith("lib")||file.getParentFile().getName().equals("icones"))
				return true;
			int i = 0;
			while(i < view.size()){
				if(file.getName().endsWith((String)view.get(i)))
					return true;
				i++;
			}
		
		
		return false;
		
	}
	
	
}

