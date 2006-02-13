package xagdop.Thread;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.ThreadWait;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;




public class ThreadUpdate extends Thread {

	CTreeNode node;
	ThreadWait TW;
	public ThreadUpdate(CTreeNode treeNode,ThreadWait TW){	
		this.node = treeNode ;
		this.TW = TW;
	}
	
	public void run() {
		SvnUpdate svnu;
		try {
			svnu = new SvnUpdate();
			svnu.checkOut(new File(node.getLocalPath()));
			//((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFromLocal(node);
			TW.arreter();
		} catch (SVNException e) {
			// TODO Auto-generated catch block
	/*
			e.printStackTrace();
			System.out.println("Erreur dans le thread Update");*/
			ErrorManager.getInstance().display();
		}catch (Exception e){
		/*	e.printStackTrace();
			System.out.println("Erreur dans le thread Update");*/
			ErrorManager.getInstance().display();
		}finally{
		
		
		}
		
	}
	
	
}
