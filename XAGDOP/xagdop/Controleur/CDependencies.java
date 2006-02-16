package xagdop.Controleur;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

import xagdop.Interface.Preferences.IPreferences;
import xagdop.Util.ErrorManager;



public class CDependencies {
	/*
	 * 
	 * Attributs de la classe
	 * 
	 * 
	 */
		
	
	/*
	 * Constructeur
	 */
	public CDependencies(){
	
	}
	
	/*
	 * Methode de la classe
	 */
	
	/**
	 * @return
	 * @throws IOException 
	 */
	public HashMap getDependenciesFiles() throws IOException{
		HashMap dependencies = new HashMap();
		
		File projectsDirectory = new File(IPreferences.getDefaultPath());
		File[] projects = projectsDirectory.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				File directory = new File(dir+File.separator+name);
				if(directory.isDirectory()&&!directory.isHidden())
					return true;
				return false;
			}
		});
		
		File dependencyFile;
		if(projects!=null){
			for(int i= 0; i < projects.length;i++){
				dependencyFile = new File(projects[i]+File.separator+"dependencies.xml");
				if(dependencyFile.exists()){
					if(!dependencyFile.canWrite()){
						File tmp = new File(projects[i]+File.separator+"dependencies.xml.new");		 
						try {
							CFile.copy(dependencyFile,tmp);
						} catch (IOException e) {
							ErrorManager.getInstance().setErrMsg("Le fichier est en lecture seule et ne peut ?tre ?crit.");
							ErrorManager.getInstance().setErrTitle("Fichier inaccessible");
							throw new IOException();
						}
						if(!dependencyFile.delete()) {
							ErrorManager.getInstance().setErrMsg("Le fichier est en lecture seule et ne peut ?tre ?crit.");
							ErrorManager.getInstance().setErrTitle("Fichier inaccessible");
							throw new IOException();
						}	
					tmp.renameTo(dependencyFile);
					}
					dependencies.put(projects[i].getName(),dependencyFile);
				}
			}
		}
	
		
		return dependencies;
	}
	
	public void setFileReadOnly(){

		File projectsDirectory = new File(IPreferences.getDefaultPath());
		File[] projects = projectsDirectory.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				File directory = new File(dir+File.separator+name);
				if(directory.isDirectory()&&!directory.isHidden())
					return true;
				return false;
			}
		});
		
		File dependencyFile;
		if(projects!=null){
			for(int i= 0; i < projects.length;i++){
				dependencyFile = new File(projects[i]+File.separator+"dependencies.xml");
				if(dependencyFile.exists()){
					dependencyFile.setReadOnly();
				}
			}
		}
	}
	
	
	
}