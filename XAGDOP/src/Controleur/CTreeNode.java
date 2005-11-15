
package src.Controleur;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;
import src.Identity;


/**
 * class representing a node of a tree
 *
 */


public class CTreeNode extends DefaultMutableTreeNode implements Serializable
{


	public CTreeNode(Object userObject, boolean isLeaf)
	{
		super(userObject);		
	}


	public CTreeNode(Object userObject, CTreeNode parent)
	{
		super(userObject);
		setParent(parent);
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
