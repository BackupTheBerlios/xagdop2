
package src.Controleur;


import java.awt.Component;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

import src.Identity;
import src.Interface.ICentralPanel;
import src.Svn.SvnConnect;
import src.Svn.SvnDisplayRepositoryTree;


/**
 * This adapter allows to display the working project list in a JTree
 *
 */

public class CTree implements TreeModel
{
    private EventListenerList mListenerList = new EventListenerList();
    private CTreeNode mRoot = new CTreeNode("Projets", false);; 
    
    public CTree()
    {
        super();       
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
	
	/**
	 * Recursive search by the id of an element
	 * 
	 * @param id the id of the element to find
	 * @param current the current node
	 * @return the corresponding node or null
	 */
	private CTreeNode findWithID(String id, CTreeNode current)
	{
		if( current instanceof Identity && ((Identity)current).getID().equals(id) )
		{
			return current;
		}
		
		for(int i=0; i<getChildCount(current); i++)
		{
			CTreeNode node = (CTreeNode)getChild(current, i);
			
			node = findWithID(id, node);
			
			if(node!=null)
			{
				return node;
			}
		}
		
		return null;
	}
	
	/**
	 * Find an element by its id
	 * 
	 * @param id the id of the element to find
	 * @return the corresponding node or null
	 */
	public CTreeNode findWithID(String id)
	{
		return findWithID(id, mRoot);
	}
	
	
	
	/**
	 * Adds nodes in the tree
	 * 
	 * @param elements the nodes to insert
	 * @param parents the parents of each nodes
	 * @param extras 
	 */
	protected void handleInsert(Object[] elements,	Object[] parents,Map extras)
	{
	    if (elements.length < 1)
	        return ;
		//System.out.println("tree insert ");
	    CTreeNode node = null;
	    CTreeNode parent = null;
	    if(elements == null || parents == null || elements.length != parents.length) return;
	    
	    for (int i = 0; i < elements.length; i++)
        {
	        // retrieve the node identify by his ID
	        node = (CTreeNode)findWithID(((Identity)elements[i]).getID());	
	        // retrieve his parents
			parent = (CTreeNode)findWithID(((Identity)parents[i]).getID());
			//System.out.println("handleInsert : node : "+(node==null?"null":node.getName())+" parent :"+(parent==null?"null":(parent.getName()+parent.getID())));
			// it is a new node 
			if( node == null )
			{
				node = new CTreeNode( elements[i], true );
			}
			// test if the parent exists
			if( parent != null )
			{	
				if( node.getParent() == null )
				{
					parent.add(node);
				}
				// insert the element 
				fireTreeNodesInserted( this, parent.getPath(), new int[]{ parent.getIndex(node) }, new Object[]{ node });
			}	    
		}
	}
	
	/**
	 * remove nodes from the tree
	 * 
	 * @param elements the nodes to remove
	 * @param parents the parents of each nodes
	 * @param extras 
	 */
	protected void handleRemove(Object[] elements,	Object[] parents,Map extras)
	{
		CTreeNode node = null;
		CTreeNode parent = null;
		
		if (elements.length < 1)
			return ;
		
		if(elements == null || parents == null || elements.length != parents.length) 
			return;
		
		for (int i = 0; i < elements.length; i++)
		{
			//	      retrieve the node identify by his ID
			node = (CTreeNode)findWithID(((Identity)elements[i]).getID());
			//	      retrieve his parents
			parent = (CTreeNode)findWithID(((Identity)parents[i]).getID());
			
			if( node == null )
			{
				node = new CTreeNode( elements[i], true );
			}
			if( parent != null )
			{	
				parent.remove(node) ; 
				// insert the element 
				fireTreeNodesRemoved( this, parent.getPath(), new int[]{ parent.getIndex(node) }, new Object[]{ node });
			}	    
		}
	}
	
	public void refresh(CTreeNode node){
		//node.removeAllChildren();
		SvnConnect svnC = new SvnConnect();
		SVNRepository repository = svnC.connect(); 	
		SvnDisplayRepositoryTree listeroot = new SvnDisplayRepositoryTree();
		try
		{
			Collection liste_p = listeroot.listEntries(repository, "");	
			
			Iterator iterator;
			Enumeration child = node.children();
			SVNDirEntry entry;
			boolean toRemove=true;
			
			//Suppression de ceux qui ont disparus
			while (child.hasMoreElements()) {
				iterator = liste_p.iterator();
				CTreeNode tmp = (CTreeNode)child.nextElement();
				
				while(iterator.hasNext()){
					entry = (SVNDirEntry) iterator.next();
					System.out.println(tmp.getName()+" : "+entry.getName());
					if(entry.getName().equals(tmp.getName())){
						toRemove=false;
						break;
					}		
					else
						toRemove=true;
				}
				
				
				
				if(toRemove)
					node.remove(tmp);		
			}

			
			iterator = liste_p.iterator();
			boolean exist=false;
			
			//Ajout des nouveaux
			while (iterator.hasNext()) {
				child = node.children();
				entry = (SVNDirEntry) iterator.next();
				
				while(child.hasMoreElements()){
					if(((CTreeNode)child.nextElement()).getName().equals(entry.getName())){
						exist=true;
						break;
						}
					else
						exist=false;
				}
				
				
				
				if(!exist){
					node.add(new CTreeNode(entry.getName(), false));
					exist = false;
				}
			
			}
			
			
			
			Object[] path = {node};
			fireTreeNodesInserted(this, path, null, null);
		}
		catch (SVNException svne)
		{
			System.out.println("Exception SVNException!!");
			System.out.println(svne.toString());
			System.out.println(svne.getMessage());
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
		//for(int i=listeners.length-2; i>=0; i-=2)
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
	 * Notify all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 * @see EventListenerList
	 */
	protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					try{
					e=new TreeModelEvent(source, path, childIndices, children);
					}catch(Throwable t){}
				}
				((TreeModelListener)listeners[i+1]).treeNodesChanged(e);
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
	protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children)
	{
		Object[] listeners = mListenerList.getListenerList();
		TreeModelEvent e = null;
		
		for(int i=listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==TreeModelListener.class)
			{
				if(e==null)
				{
					e=new TreeModelEvent(source, path, childIndices, children);
				}
				((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
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
	 */
	public Icon associateIcon(Object node)
	{
		return new ImageIcon();
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
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                              boolean leaf, int row, boolean hasFocus)
		{
			Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);			
			return c;
		}
		
	}

	public class ITreeCellEditor extends DefaultTreeCellEditor implements CellEditorListener
	{
		private CTreeNode mCurrentNode = null;
		
		public ITreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer)
		{
			super(tree, renderer);
			addCellEditorListener(this);
		}
		
		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                              boolean leaf, int row)
		{
			Component c = super.getTreeCellEditorComponent(tree, value, sel, expanded, leaf, row);
			editingIcon = associateIcon(value);
			
			if( editingComponent instanceof JTextField && value instanceof CTreeNode )
			{
				mCurrentNode = (CTreeNode)value;
				((JTextField)editingComponent).selectAll();
			}
			
			return c;
		}

        /* (non-Javadoc)
         * @see javax.swing.event.CellEditorListener#editingCanceled(javax.swing.event.ChangeEvent)
         */
        public void editingCanceled(ChangeEvent arg0)
        {
            // TODO Auto-generated method stub
            
        }

        /* (non-Javadoc)
         * @see javax.swing.event.CellEditorListener#editingStopped(javax.swing.event.ChangeEvent)
         */
        public void editingStopped(ChangeEvent arg0)
        {
            // TODO Auto-generated method stub
            
        }
	}

    public void valueForPathChanged(TreePath arg0, Object arg1)
    {
        
    }
}
