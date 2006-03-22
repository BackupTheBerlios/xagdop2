package xagdop.Thread;

import java.util.ArrayList;

import xagdop.Controleur.CCommit;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IWaiting;
import xagdop.Parser.DependenciesParser;
import xagdop.Util.ErrorManager;




public class ThreadCommit extends Thread {
	
	CTreeNode node;
	
	ArrayList list;
	String comment;
	public ThreadCommit(CTreeNode treeNode,String comment){	
		this.node = treeNode ;
		this.comment = comment ;
		
	}
	
	public void run() {
		
		CCommit CC = null;
		try {
			CC = new CCommit(this.node);
			CC.DependancesSendInitialize(this.node);
			CC.commitFile(this.node,this.comment);
			DependenciesParser dp = DependenciesParser.getInstance();
			dp.publish(dp.getFile(this.node.getProject().getName()));
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ErrorManager.getInstance().display();
			
		}finally{
		/*	try {
				((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFirst(node.getProject());
			} catch (SVNException e) {
			
			}
		*/
			IWaiting.getInstance().arreter();			
		}
		
	}
	
	
}
