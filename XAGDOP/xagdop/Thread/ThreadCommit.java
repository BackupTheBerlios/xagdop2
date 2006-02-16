package xagdop.Thread;

import java.util.ArrayList;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CTreeNode;
import xagdop.Svn.SvnCommit;
import xagdop.Util.ErrorManager;




public class ThreadCommit extends Thread {

	CTreeNode node;
	ThreadWait tWait;
	ArrayList list;
	String comment;
	public ThreadCommit(CTreeNode treeNode,String comment,ThreadWait tWait){	
		this.node = treeNode ;
		this.comment = comment ;
		this.tWait = tWait;
	}
	
	public void run() {
		
		
		try {
			SvnCommit svnC = new SvnCommit();
			svnC.commit(this.node,comment);
		} catch (SVNException e) {
			ErrorManager.getInstance().display();
		}catch (Exception e){
			ErrorManager.getInstance().display();
		}finally{
			tWait.arreter();			
		}
		
	}
	
	
}
