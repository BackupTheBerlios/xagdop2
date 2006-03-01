package xagdop.Thread;

import javax.swing.JTable;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.XAGDOP;
import xagdop.Interface.Panel.IJPToggleTable;
import xagdop.Interface.IWaiting;
import xagdop.Model.DirectoryModel;





public class ThreadVersion extends Thread {


	DirectoryModel model;

	CTreeNode selectedNode;
	
	public ThreadVersion(CTreeNode selectedNode, DirectoryModel model) {

		this.model = model;
		this.selectedNode = selectedNode;
		
	}

	public void run() {
			try {


				JTable table = new JTable( model );
				IJPToggleTable tablePanel = new IJPToggleTable(table); 
				model.setDirectory(selectedNode);
				XAGDOP.getInstance().setAssociatePanel(tablePanel);
			} catch (Exception e) {
				// :p
			}
			IWaiting.getInstance().arreter();
	}
	
	
}
	





