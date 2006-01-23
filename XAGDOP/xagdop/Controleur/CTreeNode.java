
package xagdop.Controleur;

import java.io.File;
import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

import xagdop.Identity;
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
	protected String localPath;
	protected boolean modified = false;
	
	public CTreeNode(Object userObject, boolean isLeaf)
	{
		super(userObject,!isLeaf);
		File localFilePath = new File(IPreferences.getDefaultPath());
		this.localPath = localFilePath.getAbsolutePath();
	}


/*	public CTreeNode(Object userObject, CTreeNode parent, boolean isLeaf)
	{
		super(userObject,!isLeaf);
		setParent(parent);
		File localFilePath = new File(IPreferences.getDefaultPath());
		this.localPath = localFilePath.getAbsolutePath();
	}*/


	public CTreeNode(Object userObject, String _localPath,boolean isLeaf) {
		super(userObject,!isLeaf);
		File localFilePath = new File(_localPath);
		this.localPath = localFilePath.getAbsolutePath();
	}
	
/*	public CTreeNode(Object userObject, CTreeNode parent, String _localPath,boolean isLeaf)	{
		super(userObject,!isLeaf);
		setParent(parent);
		File localFilePath = new File(_localPath);
		this.localPath = localFilePath.getAbsolutePath();
	}*/
	public boolean isProject(){
		if(isRoot())
			return false;
		if(getParent()==getRoot())
			return true;
		return false;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		File localFilePath = new File(localPath);
		this.localPath = localFilePath.getAbsolutePath();
	}
	
	public void setIsModified(boolean value){
		modified = value;
	}
	
	public boolean isModified(){
		return modified;
	}

	public String getName()
	{
		//if(!versioned&&!isRoot())
			//return "> "+getUserObject().toString();
		
		return getUserObject().toString();
	}
	
	public String toString()
	{
		if(modified&&!isRoot())
			return "> "+getUserObject().toString();
		
		return getUserObject().toString();
		//return getName();
	}
	
	public String getID()
	{
		if( getUserObject() instanceof Identity )
		{
			return ((Identity)getUserObject()).getID();
		}
		return null;
	}	
}
