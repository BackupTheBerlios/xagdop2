package xagdop.Thread;

import javax.swing.JOptionPane;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CProject;

public class ThreadProject extends Thread{

	ThreadWait tWait;
	String nomProjet;
	String descProjet;
	
	public ThreadProject(String nP, String descP,ThreadWait wait) {
		this.nomProjet = nP;
		this.descProjet = descP;
		this.tWait = wait;
	}
	
	public void run() {
		
		try {
			CProject project= new CProject();
			project.createProject(nomProjet,descProjet);
			JOptionPane.showMessageDialog(null ,"Le projet "+nomProjet+" a bien ete cree ", "Validation" , 1) ;
		}
		catch (SVNException ex) {
			JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
		}
		
		catch(Exception ex){
    		JOptionPane.showMessageDialog(null ,"Le projet "+nomProjet+" n'a pu etre cree ", "Validation" , 1) ;
		}
		finally {
			tWait.arreter();
		}
	}
}
