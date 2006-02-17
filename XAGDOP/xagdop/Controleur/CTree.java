
package xagdop.Controleur;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.XAGDOP;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Parser.DependenciesParser;
import xagdop.Svn.SvnHistory;
import xagdop.Thread.ThreadUpdate;
import xagdop.Thread.ThreadWait;


/**
 * This adapter allows to display the working project list in a JTree
 *
 */

public class CTree extends DefaultTreeModel
{
	private CTreeNode mRoot = new CTreeNode(new File(IPreferences.getDefaultPath()),null); 
	private static final long serialVersionUID = 1L;
	
	
	
	public Object getChild( Object parent, int index ) {
		System.out.println(parent.getClass());
		File[] children = listFile((CTreeNode)parent);
		CTreeNode node = new CTreeNode(children[index],(CTreeNode)parent);
		try {
			if(!SvnHistory.isUnderVersion(children[index])||SvnHistory.isModified(children[index])){
				node.setAllIsModified();
			}
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return node;
	}
	
	public int getChildCount( Object parent ) {
		if(parent instanceof CTreeNode){
			File fileSysEntity = (File)((CTreeNode)parent).getUserObject();
			if ( fileSysEntity.isDirectory() ) {
				File[] children = listFile((CTreeNode)parent);
				return children.length;
			}
			else {
				return 0;
			}
		}else 
			return 0;
		
	}
	
	protected File[] listFile(CTreeNode current){
		final CTreeNode node = current.getProject();
		final String project;
		if(node == null)
			project = "";
		else
			project = node.getName();
		
		File localFiles = (File)current.getUserObject();
		File[] allFiles = localFiles.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				File directory = new File(dir.getAbsolutePath()+File.separator+name);
				return CRole.getInstance().canShow(directory,project);
			}
		});
		return allFiles;
	}
	
	
	
	public void valueForPathChanged( TreePath path, Object newValue ) {
	}
	
	public int getIndexOfChild( Object parent, Object child ) {
		File fileSysEntity = (File)((CTreeNode)child).getUserObject();
		File[] children = listFile((CTreeNode)parent);
		int result = -1;
		
		for ( int i = 0; i < children.length; ++i ) {
			if ( fileSysEntity.getName().equals( children[i].getName() ) ) {
				result = i;
				break;
			}
		}
		
		return result;
	}
	
	
	
	
	
	public CTree (CTreeNode root) throws SVNException
	{
		super(root);
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
	 * Check if a node is a leaf of the tree
	 *
	 * @param node the node
	 * @return true if the node is a leaf, false otherwise
	 */
	public boolean isLeaf(Object node)
	{
		return ((CTreeNode)node).isLeaf();
	}
	
	
	public TreeNode[] getPathToRoot(CTreeNode node)
	{
		return node.getPath();
	}
	
	
	
	public void refresh(CTreeNode node) {
		ThreadWait TW = new ThreadWait(XAGDOP.getInstance());
		TW.start();
		
		ThreadUpdate TU = new ThreadUpdate(node,TW);
		TU.start();
		
	}
	
	public void refreshFirst(CTreeNode parent) throws SVNException{
		nodeStructureChanged(parent);
	}
	
	protected void refreshRemovedNode(CTreeNode parent){
		File[] allFiles = listFile(parent);
		CTreeNode node = null;
		boolean exist = false;
		for(int j =	0 ; j < parent.getChildCount() ; j++){
			for(int i = 0; i < allFiles.length;i++){
				
				
				if(!((CTreeNode)parent.getChildAt(j)).getName().equals(allFiles[i].getName())){
					node = ((CTreeNode)parent.getChildAt(j));
					exist = false;
				}
				else {
					exist = true;
					node = ((CTreeNode)parent.getChildAt(j));
					if(allFiles[i].isDirectory()){
						System.out.println("Je suis un directory");
						refreshRemovedNode(node);
					}
					break;
				}
				//System.out.println(allFiles[i]+" : "+node.getName());
			}
			if(!exist){
				System.out.println("Suppression : "+node.getName());
				//remove(node);
				fireTreeNodesRemoved(node,null,null,null);
			}
			
			exist=false;
		}
		
		
	
		
	}
	
	protected void refreshAddedNode(CTreeNode parent){
		System.out.println("RefreshAdd");
		File[] allFiles = listFile(parent);
		CTreeNode node = null;
		boolean exist = false;
		for(int i = 0; i < allFiles.length;i++){
			for(int j =	0 ; j < parent.getChildCount() ; j++){
				node = ((CTreeNode)parent.getChildAt(j));
				System.out.println(allFiles[i].getName()+" : "+node.getName()+" : "+i);
				if(((CTreeNode)parent.getChildAt(j)).getName().equals(allFiles[i].getName())){
					exist = true;
					break;
				}
				
			}
			System.out.println(exist+" : "+node.getName()+" : "+allFiles[i].getName());	
			if(!exist){
				System.out.println("Ajout");
				node = new CTreeNode(allFiles[i]);
				insertNodeInto(node,parent,parent.getChildCount());
			}else
			{
				TreePath tmp[] = new TreePath[1];
				 tmp[0] = new TreePath(node);
				fireTreeNodesInserted(node,tmp,null,null);
			}
			exist=false;
			
			if(allFiles[i].isDirectory())
				refreshAddedNode(node);
		}
	}
	
	public void refreshFromLocal(CTreeNode parent) throws SVNException{
		
		//refreshRemovedNode(parent);
		refreshAddedNode(parent);	
		
	}
	
	
	
	public void remove(CTreeNode node){		
		removeNodeFromParent(node);
	}
	
	
	/**
	 * Associate a central panel to a node
	 *
	 * @param node the node to consider
	 * @return a popup menu for the operations on this node
	 */
	/*public Panel associatePanel(Object node)
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
	 * @throws IOException 
	 */
	public Icon associateIcon(Object node) throws XPathExpressionException, IOException 
	{
		ImageIcon icon;
		URL imageURL;
		if(((CTreeNode)node).isLeaf()){
			if(DependenciesParser.getInstance().isToUpdate(CFile.treePathName((CTreeNode)node))){
				
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
	
	
}
