package xagdop.Thread;

import javax.swing.JTable;

import xagdop.Controleur.CTeamManagement;
import xagdop.Util.ErrorManager;




public class ThreadTeamManagement extends Thread {


	ThreadWait tWait;
	JTable JT;
	String nP;
	public ThreadTeamManagement(JTable JT,String nP, ThreadWait tWait) {
		this.tWait = tWait;
		this.JT = JT;
		this.nP = nP;
	}

	public void run() {
		CTeamManagement CTeamM = new CTeamManagement(this.nP);
		int j =JT.getRowCount();            	
		int i=0;
		try {
		while(i<j)
		{

				CTeamM.Apply((String)JT.getValueAt(i,0),((Boolean)JT.getValueAt(i,1)).booleanValue(),((Boolean)JT.getValueAt(i,2)).booleanValue(),((Boolean)JT.getValueAt(i,3)).booleanValue(),((Boolean)JT.getValueAt(i,4)).booleanValue());
			
			i++;  
		}        					
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		finally{
			tWait.arreter();
		}
	}
	
	
}
