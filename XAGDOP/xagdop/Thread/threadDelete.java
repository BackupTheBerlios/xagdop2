package xagdop.Thread;

import javax.swing.JOptionPane;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTreeNode;

public class threadDelete extends Thread{
	
	ThreadWait tWait;
	CTreeNode node;
	public threadDelete(CTreeNode nod, ThreadWait Wait){
		this.node = nod;
		this.tWait = Wait;
	}

	public void run() {
		
		try {
			CProject cp = new CProject();
			cp.deleteProject(node);
			
			if(!node.isProject())
				JOptionPane.showMessageDialog(null ,"Le fichier "+node.getName()+" sera supprim? lors du prochain commit", "Validation" , 1) ;
			else
				JOptionPane.showMessageDialog(null ,"Le projet "+node.getName()+" est supprim??");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			tWait.arreter();
		}
	}
	
	
}
