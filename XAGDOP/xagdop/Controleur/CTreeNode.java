
package xagdop.Controleur;

import java.io.File;
import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;


import xagdop.Interface.IPreferences;



/**
 * class representing a node of a tree
 *
 */


public class CTreeNode extends DefaultMutableTreeNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean modified = false;
	
	public CTreeNode(Object userObject,CTreeNode parent, boolean isLeaf)
	{
		super(userObject,!isLeaf);
		//setParent(parent);
		if(parent!=null)
			parent.add(this);
	}
	public CTreeNode(Object userObject,CTreeNode parent){
		super(userObject);
		//setParent(parent);
		if(parent!=null)
			parent.add(this);
	}
	
	public CTreeNode(Object userObject){
		super(userObject);
	}
	

	public boolean isProject(){
		if(((File)getUserObject()).getParentFile().equals(new File(IPreferences.getDefaultPath())))
			return true;
		
		return false;
		
	/*	if(!isRoot())
			return false;
		if(getParent()==getRoot())
			return true;
		return false;*/
	}

	public CTreeNode getProject(){
		if(this.isRoot())
			return null;
		CTreeNode node = this;
		while(!node.isProject()){

			node = (CTreeNode)node.getParent();
			
		}
		//System.out.println(node.getName());
		return node;
	}
	
	public boolean isLeaf(){
		return ((File)getUserObject()).isFile();
	}
	
	public String getLocalPath() {
		return ((File)getUserObject()).getAbsolutePath();
	}
	
	public void setAllIsModified(){
		CTreeNode node = this;
		while(!node.isProject()&&!node.isModified()){
			node = (CTreeNode)node.getParent();
			node.setIsModified(true);
		}
		((CTreeNode)node.getParent()).setIsModified(true);
		modified = true;
	}
	
	public void setIsModified(boolean value){
		modified = true;
	}
	
	public boolean isModified(){
		return modified;
	}

	public String getName()
	{
		//if(!versioned&&!isRoot())
			//return "> "+getUserObject().toString();
		return ((File)getUserObject()).getName();
	}
	
	public String toString()
	{
		if(modified&&!isRoot())
			return "> "+((File)getUserObject()).getName();
		
		/*if(isRoot())
			return "ProjectList";*/
		
		return ((File)getUserObject()).getName();
		//return getName();
	}
	
	
}
