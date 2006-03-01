package xagdop.Thread;

import java.io.File;
import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IProjectTree;
import xagdop.Interface.XAGDOP;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;




public class ThreadUpdate extends Thread {

	CTreeNode node;
	ThreadWait tWait;
	ArrayList list;
	public ThreadUpdate(CTreeNode treeNode,ThreadWait TW){	
		this.node = treeNode ;
		tWait = TW;
	}
	public ThreadUpdate(ArrayList listProject, ThreadWait TW){	
		list = listProject;
		node = XAGDOP.getInstance().getTree().getSelectedNode();
		tWait = TW;
	}
	
	public void run() {
		SvnUpdate svnu;
		try {
			svnu = new SvnUpdate();
			if(list==null){
				svnu.checkOut(new File(node.getLocalPath()));
	//			((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFromLocal(node);
			}
			else{
				svnu.checkOut(list);
	//			((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFirst(node);
			}
			
			
		} catch (SVNException e) {

			ErrorManager.getInstance().display();

		}catch (Exception e){

			ErrorManager.getInstance().display();

		}finally{
			tWait.arreter();
		}
		
	}
	
	
}
