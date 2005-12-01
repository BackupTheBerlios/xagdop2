package xagdop.Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.ressources.Bundle;


public class IProjectTree extends JTree implements  TreeModelListener
{
	private static final long serialVersionUID = 1L;
	
	private JPopupMenu popup = new JPopupMenu();
	
	protected CTreeNode selectedNode; 
	
	
	public IProjectTree()
	{
		super(new CTree());
		selectedNode = (CTreeNode) getModel().getRoot();
		setEditable(true);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		ITreeCellRenderer renderer = new ITreeCellRenderer();
		setCellRenderer(renderer);
		setCellEditor(new ITreeCellEditor(this, renderer));
		setToggleClickCount(0);
		setExpandsSelectedPaths(true);
		setInvokesStopCellEditing(true);
		
		
		
		PopupListener popupL = new PopupListener(popup);
		addMouseListener(popupL);
		
		if(getModel()!=null)
		{
			getModel().addTreeModelListener(this);
		}
		
	}
	
	public class PopupListener extends MouseAdapter {
		private JPopupMenu popup = null;
		private JMenuItem menuRefresh = new JMenuItem(Bundle.getText("tree.popup.menu.refresh"));
		private JMenuItem menuUpdate = new JMenuItem(Bundle.getText("tree.popup.menu.update"));
		private JMenuItem menuCommit = new JMenuItem(Bundle.getText("tree.popup.menu.commit"));
		private JMenuItem menuRename = new JMenuItem(Bundle.getText("tree.popup.menu.rename"));
		private JMenuItem menuDelete = new JMenuItem(Bundle.getText("tree.popup.menu.delete"));
		private JMenuItem menuProperty = new JMenuItem(Bundle.getText("tree.popup.menu.property"));
		
		public PopupListener(JPopupMenu pop) {
			
			popup = pop;
			menuProperty.addActionListener(new openIProjectPreferences());
			menuCommit.addActionListener(new openICommit());
			//menuUpdate.addActionListener(this);
			popup.add(menuRefresh);
			popup.addSeparator();
			popup.add(menuUpdate);
			popup.add(menuCommit);
			popup.addSeparator();
			popup.add(menuRename);
			popup.add(menuDelete);
			popup.addSeparator();
			popup.add(menuProperty);

		}
		class openIProjectPreferences implements ActionListener {
			public void actionPerformed (ActionEvent e){
				IProjectPreferences iprojectpreferences = IProjectPreferences.getIPP();
				iprojectpreferences.setVisible(true);
			}
		}
		class openICommit implements ActionListener {
			public void actionPerformed (ActionEvent e){
				ICommit icom = ICommit.getIC();
				icom.setVisible(true);
			}
		}
		
		
		public void mouseReleased(MouseEvent me) {
			TreePath pathClicked = getPathForLocation(me.getX(),me.getY());
			//DefaultMutableTreeNode node = (DefaultMutableTreeNode)getLastSelectedPathComponent();
			
			int selRow = getRowForLocation(me.getX(), me.getY());
			if(selRow != -1){
				
				if(selRow != -1 && me.getClickCount()==1){
					me.consume();
					selectedNode = (CTreeNode)pathClicked.getLastPathComponent();
				}
			}
			
			
			
			//getSelectionModel().addSelectionPath(new TreePath(node.getPath()));
			
			if ((SwingUtilities.isLeftMouseButton(me))&&(!isPathSelected(pathClicked))) {		
					selectedNode = (CTreeNode)getModel().getRoot();		
					clearSelection();
			} 
			
			if ((SwingUtilities.isRightMouseButton(me))&&(isPathSelected(pathClicked))) {		
					popup.show(me.getComponent(), me.getX(), me.getY());				
			} 
		}
	} 
	
	
	public CTreeNode getSelectedNode(){
		return selectedNode;
	}
	
	
	public void setModel(TreeModel newModel)
	{
		if(getModel()!=null)
		{
			getModel().removeTreeModelListener(this);
		}
		
		super.setModel(newModel);
		
		if(getModel()!=null)
		{
			getModel().addTreeModelListener(this);
		}
	}
	
	public boolean isPathEditable(TreePath path)
	{
		if(path.getLastPathComponent() == getModel().getRoot())
		{
			return false;
		}
		
		return super.isPathEditable(path);
	}
	
	private Icon associateIcon(Object value)
	{
		return ((CTree)getModel()).associateIcon(value);
	}
	/*
	private JPopupMenu associateMenu(Object value)
	{
		return ((CTree)getModel()).associateMenu(value);
	}
	
	 private ICentralPanel associatePanel(Object value)
	 {
	 return ((ICentralPanel)getModel()).associatePanel(value);
	 }
	 */
	private class ITreeCellRenderer extends DefaultTreeCellRenderer
	{
		private static final long serialVersionUID = 1L;
		
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus)
		{
			Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			if( value instanceof CTreeNode )
			{
				CTreeNode node = (CTreeNode)value;
				setIcon(associateIcon(node));
			}
			
			return c;
		}
		
	}
	
	private class ITreeCellEditor extends DefaultTreeCellEditor implements CellEditorListener
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
		
		
		public void editingStopped(ChangeEvent e)
		{
		}
		
		
		public void editingCanceled(ChangeEvent arg0)
		{            
		}
	}
	
	
	protected void processMouseEvent(MouseEvent e) 
	{
		//catch an exception throws by BasicTreeUI$MouseHandler.handleSelection(BasicTreeUI.java:2815)
		//the exception is throwed when you editing a TreeNode and stop editing by clicking in the tree
		try{ super.processMouseEvent(e); }catch(Throwable t){}
	}
	
	public void treeNodesInserted(TreeModelEvent event)
	{
		// display the new project inserted  
		Object tabTreeNode [] = event.getTreePath().getPath(); 
		if(tabTreeNode.length == 1)
		{
			if(event.getChildIndices() != null)
			{/*
			ITreeNode inserted = (ITreeNode)getModel().getChild(tabTreeNode[0],event.getChildIndices()[event.getChildIndices().length-1]);
			Context.getInstance().getListProjects().setCurrentProject((Project)inserted.getUserObject());
			SoapCentralPanel centralTabbedPane = associatePanel(inserted);
			((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralTabbedPane);
			setSelectionPath(new TreePath(inserted.getPath()));*/
			}
		}
		updateUI();
	}
	
	
	public void treeNodesRemoved(TreeModelEvent event)
	{
		ICentralPanel centralTabbedPane ;
		
		// retrieve the parent node of the node removed
		TreePath parentPath = event.getTreePath() ;
		if (getRowForPath(parentPath) == 0)
		{
			/*  try 
			 {
			 // get the last project in the treenode and display the centralTabbePane
			  SoapTreeNode lastChild = (SoapTreeNode)((SoapTreeNode)parentPath.getLastPathComponent()).getLastChild();
			  centralTabbedPane = associatePanel(lastChild);
			  ((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralTabbedPane);
			  }
			  catch (Exception e)
			  {
			  //case when it is the root
			   Context.getInstance().getListProjects().setCurrentProject(null);
			   centralTabbedPane = associatePanel((SoapTreeNode)parentPath.getLastPathComponent());
			   ((SoapFrame)Context.getInstance().getTopLevelFrame()).openCentralPanel(centralTabbedPane);
			   }*/
		}
		updateUI(); 
	}
	
	public void treeNodesChanged(TreeModelEvent e) 
	{
		updateUI(); 
	}
	
	public void treeStructureChanged(TreeModelEvent arg0)
	{
		
	}
}
