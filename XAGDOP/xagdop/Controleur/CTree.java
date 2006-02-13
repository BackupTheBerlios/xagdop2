
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

import xagdop.Interface.ICentralPanel;
import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
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
		//File directory = (File)((CTreeNode)parent).getUserObject();
		File[] children = listFile((CTreeNode)parent);
		CTreeNode node = new CTreeNode(children[index],(CTreeNode)parent);
		try {
			if(!SvnHistory.isUnderVersion(children[index])||SvnHistory.isModified(children[index])){
				node.setAllIsModified();
				/*while(!node.isRoot()){
				 fireTreeNodesChanged(new TreeModelEvent(node,new TreePath(node)));
				 node = (CTreeNode)node.getParent();
				 }*/
			}
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
	
	public int getChildCount( Object parent ) {
		File fileSysEntity = (File)((CTreeNode)parent).getUserObject();
		if ( fileSysEntity.isDirectory() ) {
			File[] children = listFile((CTreeNode)parent);
			return children.length;
		}
		else {
			return 0;
		}
	}
	
	protected File[] listFile(CTreeNode current){
		//final CTreeNode node = current;
		File localFiles = (File)current.getUserObject();
		File[] allFiles = localFiles.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				
				
				File directory = new File(dir.getAbsolutePath()+File.separator+name);
				//System.out.println(dir.getName()+" : "+name+" : "+directory.isHidden());
				
				if(directory.isHidden()||name.endsWith(".xml"))
					return false;
				else return true;
			}
			/*if(directory.isDirectory()){
			 if(!node.isRoot()){
			 
			 
			 try {
			 CRole role = new CRole(node.getProject().getName());
			 if(!role.isArchitect()&&directory.getName().startsWith("lib"))
			 return false;
			 
			 } catch (Exception e) {
			 ErrorManager.getInstance().display();
			 }
			 
			 
			 }
			 return true;
			 }
			 
			 //System.out.println("bla");
			  try {
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
			   } catch (Exception e) {
			   ErrorManager.getInstance().display();
			   }
			   
			   return false;
			   
			   }
			   */
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
	
	
	
	
	
	public CTree (CTreeNode test) throws SVNException
	{
		super(test);
		//refreshFromLocal(mRoot);
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
	
	
	public String treePathName(CTreeNode node){
		if(mRoot.getLocalPath().length()+2+node.getProject().getName().length()>node.getLocalPath().length())
			return "";
		
		return node.getLocalPath().substring(mRoot.getLocalPath().length()+node.getProject().getName().length()+2,node.getLocalPath().length()); 
	}
	
	/*public String treePathName(String file){
	 
	 //System.out.println(node.getLocalPath().substring(0,mRoot.getLocalPath().length()+1));
	  
	  return file.substring(mRoot.getLocalPath().length()+1,file.length()); 
	  }*/
	
	public String relativePath(String apesFile, String pogFile){
		
		if(!apesFile.startsWith(mRoot.getLocalPath()))
			return apesFile;
		
		if(!pogFile.startsWith(mRoot.getLocalPath()))
			return apesFile;
		apesFile = apesFile.replaceFirst(mRoot.getLocalPath()+File.separator,"");
		pogFile = pogFile.replaceFirst(mRoot.getLocalPath()+File.separator,"");
		String[] apesSlit  = apesFile.split(File.separator);
		String[] pogSlit = pogFile.split(File.separator);
		String res="";
		int tmp = 0;
		for(int i = Math.min(apesSlit.length,pogSlit.length); i>0;i--){
			if(apesSlit[i-1].compareTo(pogSlit[i-1])!=0)
				tmp = i-1;
		}
		for(int i = tmp+1;i<pogSlit.length;i++)
			res = res.concat(".."+File.separator);
		for(int i = tmp;i<apesSlit.length;i++)
			res = res.concat(apesSlit[i]+File.separator);
		
		res = res.substring(0,res.length()-1);
		return res;
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
	
	public void refreshFromLocal(CTreeNode parent) throws SVNException{
		
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
					break;
				}
			}
			if(!exist){
				remove(node);
			}
			exist=false;
		}
		
		
		
		
		
		
		
		for(int i = 0; i < allFiles.length;i++){
			for(int j =	0 ; j < parent.getChildCount() ; j++){
				if(((CTreeNode)parent.getChildAt(j)).getName().equals(allFiles[i].getName())){
					node = ((CTreeNode)parent.getChildAt(j));
					exist = true;
					break;
				}
				
			}
			if(!exist){
				node = new CTreeNode(allFiles[i]);
				insertNodeInto(node,parent,parent.getChildCount());

			}
			exist=false;
			
			if(allFiles[i].isDirectory())
				refreshFromLocal(node);
		}
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
	 * @throws IOException 
	 */
	public Icon associateIcon(Object node) throws XPathExpressionException, IOException 
	{
		ImageIcon icon;
		URL imageURL;
		if(((CTreeNode)node).isLeaf()){
			if(DependenciesParser.getInstance().isToUpdate(treePathName((CTreeNode)node))){
				
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
