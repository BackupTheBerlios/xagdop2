package xagdop.Thread;

import javax.swing.JOptionPane;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IWaiting;

public class ThreadDelete extends Thread{
	
	
	CTreeNode node;
	public ThreadDelete(CTreeNode nod){
		this.node = nod;
		
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
			//e.printStackTrace();
		}
		finally {
			IWaiting.getInstance().arreter();
		}
	}
	
	
}
