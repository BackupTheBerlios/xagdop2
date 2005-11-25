
package xagdop.Controleur;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

import xagdop.Identity;


/**
 * class representing a node of a tree
 *
 */


public class CTreeNode extends DefaultMutableTreeNode implements Serializable
{
	String localPath;
	String repositoryPath;
	
	public CTreeNode(Object userObject, boolean isLeaf)
	{
		super(userObject);		
	}


	public CTreeNode(Object userObject, CTreeNode parent)
	{
		super(userObject);
		setParent(parent);
	}
	

	public CTreeNode(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}

	public CTreeNode(String localPath, String repositoryPath) {
		this.localPath = localPath;
		this.repositoryPath = repositoryPath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public String getName()
	{
		return getUserObject().toString();
	}
	
	public String toString()
	{
		return getName();
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
