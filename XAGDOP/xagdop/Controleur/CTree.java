
package xagdop.Controleur;


import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.ICentralPanel;
import xagdop.Interface.IPreferences;
import xagdop.Interface.ThreadWait;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.DependenciesParser;
import xagdop.Svn.SvnHistory;
import xagdop.Thread.ThreadUpdate;


/**
 * This adapter allows to display the working project list in a JTree
 *
 */

public class CTree implements TreeModel
{
	private EventListenerList mListenerList = new EventListenerList();
	private CTreeNode mRoot = new CTreeNode("Projects List",IPreferences.getDefaultPath(),false); 
	
	public CTree() throws SVNException
	{
		super();
		refreshFromLocal(mRoot);
	}
	
	/**
	 * Set the root of this tree model
	 *
	 * @param root the new root
	 */
	public void setRoot(CTreeNode root)
	{
		if( mRoot == null || mRoot.getUserObject() != root )
		{	
			mRoot = root;
		}
	}
	
	
	
	/**
	 * Get the root of the tree model
	 *
	 * @return the root
	 */
	public Object getRoot()
	{
		return mRoot;
	}
	
	/**
	 * Get a child of a tree node by giving its index
	 *
	 * @param parent the node
	 * @param index the index of the child
	 * @return the chil or null if it does not exists
	 */
	public Object getChild(Object parent, int index)
	{	
		if( !(parent instanceof CTreeNode) )
		{
			return null;
		}
		TreeNode node = (TreeNode)parent;
		
		if( index < 0 || index >= node.getChildCount() )
		{
			return null;
		}
		
		return ((TreeNode)parent).getChildAt(index);
	}
	
	/**
	 * Get the number of child of a node
	 *
	 * @param parent the node
	 * @return the number of child
	 */
	public int getChildCount(Object parent)
	{
		if( parent instanceof TreeNode )
		{
			return ((TreeNode)parent).getChildCount();
		}
		return -1;
	}
	
	/**
	 * Check if a node is a leaf of the tree
	 *
	 * @param node the node
	 * @return true if the node is a leaf, false otherwise
	 */
	public boolean isLeaf(Object node)
	{
		return ((TreeNode)node).isLeaf();
	}
	
	
	/**
	 * Find the index of a child in a node
	 *
	 * @param parent the node
	 * @param child the child to evaluate
	 * @return the index of the child or -1 if child is not in parent
	 */
	public int getIndexOfChild(Object parent, Object child)
	{
		if( parent instanceof TreeNode && child instanceof TreeNode )
		{	
			return ((TreeNode)parent).getIndex((TreeNode)child);
		}
		return -1;
	}
	
	public String treePathName(CTreeNode node){
		//System.out.println(node.getLocalPath().substring(0,mRoot.getLocalPath().length()+1));
		return node.getLocalPath().substring(mRoot.getLocalPath().length()+1,node.getLocalPath().length()); 
	}
	
	public String treePathName(String file){
		//System.out.println(node.getLocalPath().substring(0,mRoot.getLocalPath().length()+1));
		return file.substring(mRoot.getLocalPath().length()+1,file.length()); 
	}
	
	public String relativePath(String apesFile, String pogFile){
		
		if(!apesFile.startsWith(mRoot.getLocalPath()))
			return apesFile;
		
		if(!pogFile.startsWith(mRoot.getLocalPath()))
			return apesFile;
		apesFile = apesFile.replaceFirst(mRoot.getLocalPath()+File.separator,"");
		pogFile = pogFile.replaceFirst(mRoot.getLocalPath()+File.separator,"");
		//System.out.println(apesFile.replaceFirst(mRoot.getLocalPath()+"/","")+" : "+
		//pogFile.replaceFirst(mRoot.getLocalPath()+"/",""));
		String[] apesSlit  = apesFile.split(File.separator);
		String[] pogSlit = pogFile.split(File.separator);
		String res="";
		int tmp = 0;
		for(int i = Math.min(apesSlit.length,pogSlit.length); i>0;i--){
			//System.out.println(apesSlit[i-1]);
			if(apesSlit[i-1].compareTo(pogSlit[i-1])!=0)
				tmp = i-1;
		}
		//System.out.println(tmp+" : "+pogSlit.length+" : "+apesSlit.length);
		for(int i = tmp+1;i<pogSlit.length;i++)
			res = res.concat(".."+File.separator);
		for(int i = tmp;i<apesSlit.length;i++)
			res = res.concat(apesSlit[i]+File.separator);
		
		res = res.substring(0,res.length()-1);
		//System.out.println(res);
		return res;
	}
	
	public TreeNode[] getPathToRoot(CTreeNode node)
	{
		return node.getPath();
	}
	/**
	 * Add a listener to this tree model
	 *
	 * @param l the listener to add
	 */
	public void addTreeModelListener(TreeModelListener l)
	{
		mListenerList.remove(TreeModelListener.class, l);
		mListenerList.add(TreeModelListener.class, l);
	}
	
	/**
	 * Remove a listener from this tree model
	 *
	 * @param l the listener to add
	 */
	public void removeTreeModelListener(TreeModelListener l)
	{
		mListenerList.remove(TreeModelListener.class, l);
	}
	
	
	public void refresh(CTreeNode node) {
//		try {
		ThreadWait TW = new ThreadWait(XAGDOP.getInstance());
		TW.start();
		
		ThreadUpdate TU = new ThreadUpdate(node,TW);
		TU.start();
		//SvnUpdate svnu = new SvnUpdate();
		//svnu.checkOut(node);
		
		//Enumeration expandPath = XAGDOP.getInstance().getTree().getExpandedDescendants(XAGDOP.getInstance().getTree().getLeadSelectionPath());
		//refreshFromLocal(node);
		/*while(expandPath.hasMoreElements()){
		 XAGDOP.getInstance().getTree().expandPath((TreePath)expandPath.nextElement());	
		 }*/
		
		
		
		//TW.arreter() ;	// rendre la JDialog invisible
		
		
		/*		} catch (SVNException e) {
		 // TODO Auto-generated catch block
		  e.printStackTrace();
		  }
		  */	
		
	}
	
	public void refreshFromLocal(CTreeNode parent) throws SVNException{
		final CTreeNode node = parent;
		File localFiles = new File(parent.getLocalPath());
		parent.removeAllChildren();
		
		File[] allFiles = localFiles.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				//System.out.println("Accept File");

				File directory = new File(dir.getAbsolutePath()+File.separator+name);
				
				
				if(directory.isDirectory()&&!directory.isHidden()){
					if(!node.isRoot()){
						
						CRole role = new CRole(node.getProject().getName());
						if(!role.isArchitect()&&directory.getName().startsWith("lib"))
							return false;
							
					}
					return true;
				}
				
				
				
				if(!node.isRoot()){		
					//System.out.println("bla");
					CRole role = new CRole(node.getProject().getName());
					ArrayList view = role.getViewFileRight();
					//System.out.println("bla : "+view.size());
					if(dir.getName().startsWith("lib")||dir.getName().equals("icones"))
						return true;
					int i = 0;
					while(i < view.size()){
						if(name.endsWith((String)view.get(i)))
							return true;
						i++;
					}
				}
				
				return false;
				
			}
			
		});
		//System.out.println("Fin Accept File");
		int i = 0;
		if(allFiles!=null){
			while(i<allFiles.length){
				CTreeNode tmp ;
				if(allFiles[i].isDirectory())
					tmp = new CTreeNode(allFiles[i].getName(),allFiles[i].getAbsolutePath(),false);
				else
					tmp = new CTreeNode(allFiles[i].getName(),allFiles[i].getAbsolutePath(),true);
				
					if(!SvnHistory.isUnderVersion(allFiles[i])||SvnHistory.isModified(allFiles[i]))
						tmp.setIsModified(true);
				
				
				
				
				if(tmp.isModified()&&!allFiles[i].isHidden())
					parent.setIsModified(true);
				
				if(!allFiles[i].isHidden()/*&&SvnHistory.isCurrentRepository(allFiles[i])*/)
					parent.add(tmp);
				
				
				
				if(allFiles[i].isDirectory()&&!allFiles[i].isHidden()/*&&SvnHistory.isCurrentRepository(allFiles[i])*/)
					refreshFromLocal(tmp);
				
				i++;
				
			}
			if(parent.isModified()&&((CTreeNode)parent.getParent())!=null)
				((CTreeNode)parent.getParent()).setIsModified(true);
			Object[] path = {parent};
			fireTreeNodesInserted(this, path, null, null);
			
		}
		
	}
	
	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=0; i<listeners.length; i+=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					try{
						e=new TreeModelEvent(source, path, childIndices, children);
					}catch(Throwable t){}
				}
				if ((TreeModelListener)listeners[i+1] != null)
				{
					((TreeModelListener)listeners[i+1]).treeNodesInserted(e);
				}
			}
		}
	}
	
	
	/**
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=0; i<listeners.length; i+=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					e=new TreeModelEvent(source, path, childIndices, children);
				}
				( (TreeModelListener) listeners[i+1]).treeNodesRemoved(e);
			}
		}
	}
	
	
	/**
	 * Associate a central panel to a node
	 *
	 * @param node the node to consider
	 * @return a popup menu for the operations on this node
	 */
	public ICentralPanel associatePanel(Object node)
	{
		return new ICentralPanel();
		
	}
	
	/**
	 * Associate an icon to a node
	 *
	 * @param node the node to consider
	 * @return an icon representing the node
	 * @throws XPathExpressionException 
	 * @throws XPathExpressionException 
	 */
	public Icon associateIcon(Object node) throws XPathExpressionException 
	{
		ImageIcon icon;
		URL imageURL;
		if(!((CTreeNode)node).getAllowsChildren()){
			if(DependenciesParser.getInstance().isToUpdate(treePathName(((CTreeNode)node).getLocalPath()))){
				imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/lab_err1.gif");
				icon = new ImageIcon(imageURL);
			}else{
				imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/view.gif");
				icon = new ImageIcon(imageURL);
			}
		}
		else{
			imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/prj.gif");
			icon = new ImageIcon(imageURL);
		}
		return icon;
	}
	
	/**
	 * Associate a menu to a node
	 *
	 * @param node the node to consider
	 * @return a popup menu for the operations on this node
	 */
	public JPopupMenu associateMenu(Object node)
	{ 
		return new JPopupMenu();
	}
	
	
	/**
	 * Execute an action associated to a node
	 *
	 * @param node the node to consider
	 */
	public void elementAction(Object node)
	{
		
	}
	
	public class ITreeCellRenderer extends DefaultTreeCellRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus)
		{
			Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);			
			return c;
		}
		
	}
	
	public void valueForPathChanged(TreePath arg0, Object arg1)
	{
		
	}
}
