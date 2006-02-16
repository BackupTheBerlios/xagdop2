package xagdop.Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Parser.DependenciesParser;
import xagdop.Svn.SvnRemove;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;


public class IProjectTree extends JTree implements  TreeModelListener
{
	private static final long serialVersionUID = 1L;
	
	private JPopupMenu popup = new JPopupMenu();
	
	protected CTreeNode selectedNode; 
	protected CTreeNode currentProject;
	
	
	public IProjectTree()
	{
		try {
			CTreeNode root = new CTreeNode(new File(IPreferences.getDefaultPath()),null);
			CTree treeModel= new CTree(root);
			setModel(treeModel);
		} catch (SVNException e) {
			ErrorManager.getInstance().display();
		}
		selectedNode = (CTreeNode) getModel().getRoot();
		setEditable(true);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		ITreeCellRenderer renderer = new ITreeCellRenderer();
		setCellRenderer(renderer);
		//setCellEditor(new ITreeCellEditor(this, renderer));
		setToggleClickCount(0);
		addTreeWillExpandListener(new ITreeWillExpandListener());
		//setExpandsSelectedPaths(true);
		setExpandsSelectedPaths(false);
		//setInvokesStopCellEditing(true);
		
		
		
		PopupListener popupL = new PopupListener(popup);
		addMouseListener(popupL);
		
		if(getModel()!=null)
		{
			getModel().addTreeModelListener(this);
		}
		
	}
	
	public CTreeNode getSelectedNode(){
		if ( selectedNode == null )
		{
			return (CTreeNode) ((CTree)getModel()).getRoot();
			
		}
		else
		{
			return selectedNode;
		}
	}
	
	/*
	 * Acces au projet courant
	 */
	public CTreeNode getCurrentProject() {
		return currentProject;
	}
	/*
	 * Modification du projet courant
	 */
	public void setCurrentProject(CTreeNode currentProject) {
		this.currentProject = currentProject;
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
		return false;
	}
	
	private Icon associateIcon(Object value)
	{
		try {
			return ((CTree)getModel()).associateIcon(value);
		} catch (XPathExpressionException e) {
			ErrorManager.getInstance().display();
		} catch (IOException e) {
			ErrorManager.getInstance().display();
		}
		return null;
	}
	protected void processMouseEvent(MouseEvent e) 
	{
		//catch an exception throws by BasicTreeUI$MouseHandler.handleSelection(BasicTreeUI.java:2815)
		//the exception is throwed when you editing a TreeNode and stop editing by clicking in the tree
		try{ super.processMouseEvent(e); }catch(Throwable t){}
	}
	
	public void treeNodesInserted(TreeModelEvent event)
	{
		updateUI(); 
	}
	
	
	public void treeNodesRemoved(TreeModelEvent event)
	{
		 TreePath path = event.getTreePath();
    	 CTreeNode current = (CTreeNode) path.getLastPathComponent();
		((CTree)getModel()).reload(current);
		updateUI(); 
	}
	
	public void treeNodesChanged(TreeModelEvent event) 
	{
		 TreePath path = event.getTreePath();
    	 CTreeNode current = (CTreeNode) path.getLastPathComponent();
		((CTree)getModel()).reload(current);
			updateUI(); 
	}
	
	public void treeStructureChanged(TreeModelEvent event)
	{
		updateUI(); 
	}
	
	
	
	public class PopupListener extends MouseAdapter {
		private JPopupMenu popup = null;
		private JMenuItem menuRefresh = new JMenuItem(Bundle.getText("tree.popup.menu.refresh"));
		//private JMenuItem menuUpdate = new JMenuItem(Bundle.getText("tree.popup.menu.update"));
		private JMenuItem menuCommit = new JMenuItem(Bundle.getText("tree.popup.menu.commit"));
		private JMenuItem menuRename = new JMenuItem(Bundle.getText("tree.popup.menu.rename"));
		private JMenuItem menuDelete = new JMenuItem(Bundle.getText("tree.popup.menu.delete"));
		private JMenuItem menuProperty = new JMenuItem(Bundle.getText("tree.popup.menu.property"));
		private JMenuItem menuRefrechFL = new JMenuItem(Bundle.getText("tree.popup.menu.refreshfromlocal"));
		
		public PopupListener(JPopupMenu pop) {
			
			popup = pop;
			menuDelete.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					
					try {
						SvnRemove svnR = new SvnRemove();
						svnR.delete(selectedNode);
						((CTree)getModel()).remove(selectedNode);
						//((CTree)getModel()).fireTreeNodesRemoved(new TreeModelEvent(selectedNode,new TreePath(selectedNode)));
						//treeNodesRemoved(new TreeModelEvent(selectedNode,new TreePath(selectedNode)));
					} catch (SVNException e1) {
						ErrorManager.getInstance().display();
					}
					JOptionPane.showMessageDialog(null ,"Le fichier "+selectedNode.getName()+" sera supprim? lors du prochain commit", "Validation" , 1) ;
					
					
				}
				
			});
			menuProperty.addActionListener(new openIProjectPreferences());
			menuCommit.addActionListener(new openICommit());
			menuRefrechFL.addActionListener( new ActionListener() {
			public void actionPerformed (ActionEvent e){
				try {
					((CTree)getModel()).refreshFromLocal(selectedNode);
				} catch (SVNException e1) {
					ErrorManager.getInstance().display();
				}
			}
		}
		);
			
			menuRefresh.addActionListener( new ActionListener() {
				public void actionPerformed (ActionEvent e){
					if(getSelectedNode().isRoot())
						new ICheckOut();
					else
						((CTree)getModel()).refresh(selectedNode);
				}
			}
			);
			//menuUpdate.addActionListener(this);
			
			popup.add(menuRefrechFL);
			popup.addSeparator();
			//popup.add(menuUpdate);
			popup.add(menuRefresh);
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
				new ICommit(getSelectedNode());
			}
		}
		
		
		public void mouseReleased(MouseEvent me) {
			TreePath pathClicked = getPathForLocation(me.getX(),me.getY());
			//DefaultMutableTreeNode node = (DefaultMutableTreeNode)getLastSelectedPathComponent();
			
			int selRow = getRowForLocation(me.getX(), me.getY());
				
				if(selRow != -1 && me.getClickCount()==1){
					me.consume();
					selectedNode = (CTreeNode)pathClicked.getLastPathComponent();
				}else
					selectedNode = (CTreeNode) ((CTree)getModel()).getRoot();;
		
			//getSelectionModel().addSelectionPath(new TreePath(node.getPath()));
			
			if ((SwingUtilities.isLeftMouseButton(me))&&(!isPathSelected(pathClicked))) {		
					
					clearSelection();
			} 
			
			if (selectedNode.isProject())
			{
				try {
					if ((XAGDOP.getInstance()).getUser().isPManager(selectedNode.getName()))
					{
						XAGDOP.getInstance().delProject.setEnabled(true);
						XAGDOP.getInstance().equipe.setEnabled(true);
						XAGDOP.getInstance().menuProjetTeam.setEnabled(true);
						
					}else
						XAGDOP.getInstance().delProject.setEnabled(false);
				} catch (Exception e) {
					//System.out.println("Management");
					ErrorManager.getInstance().display();
				}
			}
			else
			{
					XAGDOP.getInstance().delProject.setEnabled(true);
					XAGDOP.getInstance().equipe.setEnabled(false);
					XAGDOP.getInstance().menuProjetTeam.setEnabled(false);
			}
				
				
				//changement du noeud courrant
			if(!selectedNode.isRoot())
				//rechargement de larbre en memoire				
				try {
					DependenciesParser.getInstance().setFile(selectedNode.getProject().getName());
				} catch (NullPointerException e) {
					e.printStackTrace();
					ErrorManager.getInstance().display();
				} catch (Exception e) {
					e.printStackTrace();
					ErrorManager.getInstance().display();
				}
			
				
			if ((SwingUtilities.isRightMouseButton(me))&&(isPathSelected(pathClicked))) {		
					popup.show(me.getComponent(), me.getX(), me.getY());				
			} 
		}
	} 
	
	
	private class ITreeWillExpandListener implements TreeWillExpandListener {
        public void treeWillExpand(TreeExpansionEvent evt) {
         
        	
        	 TreePath path = evt.getPath();
        	 CTreeNode current = (CTreeNode) path.getLastPathComponent();
        	 if (current.getProject()!=currentProject)
 			{
 				//changement du noeud courant
        		 setCurrentProject(current.getProject());
 				//XAGDOP.getInstance().setCurrentNode(current.getProject());
 				//rechargement de larbre en memoire				
 				try {
					DependenciesParser.getInstance().setFile(current.getProject().getName());
				} catch (NullPointerException e) {

					ErrorManager.getInstance().display();
				} catch (Exception e) {

					ErrorManager.getInstance().display();
				}
 			}	
        	
        }
    
        public void treeWillCollapse(TreeExpansionEvent evt){
        	 TreePath path = evt.getPath();
        	 CTreeNode current = (CTreeNode) path.getLastPathComponent();
        	 if (current.getProject()!=currentProject)
 			{
 				//changement du noeud courant
        		 setCurrentProject(current.getProject());
 				//XAGDOP.getInstance().setCurrentNode(current.getProject());
 				//rechargement de larbre en memoire				
 				try {
					DependenciesParser.getInstance().setFile(current.getProject().getName());
				} catch (NullPointerException e) {
					ErrorManager.getInstance().display();
				} catch (Exception e) {
					ErrorManager.getInstance().display();
				}
 			}	
        	
        }
    }
	
	
	
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
	
}
