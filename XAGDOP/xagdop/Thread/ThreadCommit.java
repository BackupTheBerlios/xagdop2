package xagdop.Thread;

import java.util.ArrayList;
import javax.xml.xpath.XPathExpressionException;
import org.tmatesoft.svn.core.SVNException;
import xagdop.Controleur.CCommit;
import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IProjectTree;
import xagdop.Interface.IWaiting;
import xagdop.Interface.XAGDOP;
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
		} catch (SVNException e1) {
			ErrorManager.getInstance().display();
		} catch (XPathExpressionException e2) {
			ErrorManager.getInstance().display();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			ErrorManager.getInstance().display();
			
		}finally{
			try {
				((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFirst(node.getProject());
			} catch (SVNException e) {
			
			}
		
			IWaiting.getInstance().arreter();			
		}
		
	}
	
	
}
