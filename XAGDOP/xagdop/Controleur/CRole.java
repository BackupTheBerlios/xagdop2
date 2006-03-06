package xagdop.Controleur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.XAGDOP;
import xagdop.Model.Project;
import xagdop.Parser.ProjectsParser;
import xagdop.Util.ErrorManager;

public class CRole {
	
	protected HashMap projectsList;
	protected static CRole role;
	
	
	
	protected ArrayList viewFile;
	protected ArrayList writeFile;
	
	
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
		projectsList.clear();
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
		
		viewFile.add(".pog");
		viewFile.add(".apes");
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
	
	public ArrayList getWriteFileRight(String project){
		ArrayList tmp = (ArrayList)projectsList.get(project);
		if(tmp != null)
			return (ArrayList) tmp.get(1);
		return null;
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
	
	
	public boolean canShow(File file, String project){
		
		
		if(file.isHidden())
			return false;
		
		if(projectsList.get(project)==null)
			return false;
		
		if(file.isDirectory())
			return showWriteDirectory(file,project);
		else
			return showFile(file,project);
		
		
	}
	
	public boolean canSend(File file, String project){
		
		
		if(file.isHidden())
			return false;
		
		if(projectsList.get(project)==null)
			return false;
		
		if(file.isDirectory())
			return showWriteDirectory(file,project);
		else
			return sendFile(file,project);
		
		
	}
	
	
	
	
	private boolean showFile(File file, String project) {
		if(file.getAbsolutePath().contains(File.separator+"icones"+File.separator))
			if(file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg")||file.getName().endsWith(".png"))
				return true;
			else 
				return false;
		
		if(file.getAbsolutePath().contains(File.separator+"bib"+File.separator))
			return true;
		
		if(file.getAbsolutePath().contains(File.separator+"images"+File.separator))
			if(file.getName().endsWith(".gif")||file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg")||file.getName().endsWith(".png"))
				return true;
		
		
		if(file.getParent().equals("css"))
			if(file.getName().endsWith(".css")||file.getName().endsWith(".html"))
				return true;
			else 
				return false;
		
		
		
		
		int i = 0;
		ArrayList view = getViewFileRight(project);
		if(view!=null){
			while(i < view.size()){
				if(file.getName().endsWith((String)view.get(i)))
					return true;
				i++;
			}
		}
		return false;
	}
	
	
	private boolean sendFile(File file, String project) {
		if(file.getAbsolutePath().contains(File.separator+"icones"+File.separator))
			if(file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg")||file.getName().endsWith(".png"))
				return true;
			else 
				return false;
		
		if(file.getAbsolutePath().contains(File.separator+"bib"+File.separator))
			return true;
		
		if(file.getAbsolutePath().contains(File.separator+"images"+File.separator))
			if(file.getName().endsWith(".gif")||file.getName().endsWith(".jpg")||file.getName().endsWith(".jpeg")||file.getName().endsWith(".png"))
				return true;
		
		
		if(file.getParent().equals("css"))
			if(file.getName().endsWith(".css")||file.getName().endsWith(".html"))
				return true;
			else 
				return false;
		
		
		int i = 0;
		ArrayList write = getWriteFileRight(project);
		if(write!=null){
			while(i < write.size()){
				if(file.getName().endsWith((String)write.get(i)))
					return true;
				i++;
			}
		}
		return false;
	}
	
	
	private boolean showWriteDirectory(File file, String project) {
		
		if(file.getName().equals("export")||file.getName().equals("website"))
			return false;
		
		if(file.getName().equals("bib")||file.getName().equals("images"))
			if(isRedactor(project))
				return true;
			else 
				return false;
		
		if(file.getName().equals("images"))
			if(isArchitect(project))
				return true;
			else 
				return false;
		
		return true;
	}
	
	
}

