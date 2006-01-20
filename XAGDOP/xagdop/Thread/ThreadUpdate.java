package xagdop.Thread;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IProject;
import xagdop.Interface.IProjectTree;
import xagdop.Interface.ThreadWait;
import xagdop.Interface.XAGDOP;
import xagdop.Svn.SvnUpdate;




public class ThreadUpdate extends Thread {

	CTreeNode node;
	ThreadWait TW;
	public ThreadUpdate(CTreeNode treenode,ThreadWait TW){	
		this.node = treenode ;
		this.TW = TW;
	}
	
	public void run() {
		SvnUpdate svnu;
		try {
			svnu = new SvnUpdate();
			svnu.checkOut(node);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
	
			e.printStackTrace();
			
		}
			
			((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFromLocal(node);
		
			
		TW.arreter();
	}
	
	
}
