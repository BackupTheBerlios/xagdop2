
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
	private static final long serialVersionUID = 3471931069630056896L;
	protected String localPath;
	protected boolean modified = false;
	
	public CTreeNode(Object userObject, boolean isLeaf)
	{
		super(userObject);
		File localFilePath = new File(IPreferences.getDefaultPath());
		this.localPath = localFilePath.getAbsolutePath();
	}


	public CTreeNode(Object userObject, CTreeNode parent)
	{
		super(userObject);
		setParent(parent);
		File localFilePath = new File(IPreferences.getDefaultPath());
		this.localPath = localFilePath.getAbsolutePath();
	}
	

	public CTreeNode(Object userObject) {
		super(userObject);
		File localFilePath = new File(IPreferences.getDefaultPath());
		this.localPath = localFilePath.getAbsolutePath();
	}

	public CTreeNode(Object userObject, String _localPath) {
		super(userObject);
		File localFilePath = new File(_localPath);
		this.localPath = localFilePath.getAbsolutePath();
	}
	
	public CTreeNode(Object userObject, CTreeNode parent, String _localPath)	{
		super(userObject);
		setParent(parent);
		File localFilePath = new File(_localPath);
		this.localPath = localFilePath.getAbsolutePath();
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
