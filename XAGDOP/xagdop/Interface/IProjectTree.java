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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Interface.SvnInterface.ICheckOut;
import xagdop.Interface.SvnInterface.ICommit;
import xagdop.Model.DirectoryModel;
import xagdop.Parser.DependenciesParser;
import xagdop.Svn.SvnHistory;
import xagdop.Thread.ThreadVersion;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;


public class IProjectTree extends JTree implements  TreeModelListener, TreeSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	private JPopupMenu popup = new JPopupMenu();
	
	protected CTreeNode selectedNode; 
	protected CTreeNode currentProject;
	protected DirectoryModel model;
	
	
	public IProjectTree()
	{
		try {
			CTreeNode root = new CTreeNode(new File(IPreferences.getDefaultPath()),null);
			CTree treeModel= new CTree(root);
			setModel(treeModel);
		} catch (SVNException e) {
			ErrorManager.getInstance().display();
		}
		model = new DirectoryModel((CTreeNode) getModel().getRoot());
		selectedNode = (CTreeNode) getModel().getRoot();
		setEditable(true);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		addTreeSelectionListener(this);
		ITreeCellRenderer renderer = new ITreeCellRenderer();
		setCellRenderer(renderer);
		setToggleClickCount(0);
		addTreeWillExpandListener(new ITreeWillExpandListener());
		setExpandsSelectedPaths(false);
		
		
		
		PopupListener popupL = new PopupListener(popup);
		addMouseListener(popupL);
		
		if(getModel()!=null)
		{
			getModel().addTreeModelListener(this);
		}
		
	}
	
	public DirectoryModel getDirModel(){
		return model;
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
		 TreePath path = event.getTreePath();
    	 CTreeNode current = (CTreeNode) path.getLastPathComponent();
		((CTree)getModel()).reload(current);
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
		private JMenuItem menuCommit = new JMenuItem(Bundle.getText("tree.popup.menu.commit"));
		private JMenuItem menuRename = new JMenuItem(Bundle.getText("tree.popup.menu.rename"));
		private JMenuItem menuDelete = new JMenuItem(Bundle.getText("tree.popup.menu.delete"));
		private JMenuItem menuVersion = new JMenuItem(Bundle.getText("tree.popup.menu.version"));
		private JMenuItem menuRefrechFL = new JMenuItem(Bundle.getText("tree.popup.menu.refreshfromlocal"));
		
		public PopupListener(JPopupMenu pop) {
			
			popup = pop;
			menuDelete.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					
					try {
						int confirmSupp = JOptionPane.showConfirmDialog(null , Bundle.getText("main.confirmSupp.label") , Bundle.getText("main.confirmSupp.title") , JOptionPane.YES_NO_OPTION);
						if (confirmSupp == JOptionPane.OK_OPTION )
						{
							CProject cp = new CProject();
							cp.deleteProject(selectedNode);
							if(!selectedNode.isProject())
								JOptionPane.showMessageDialog(null ,"Le fichier "+selectedNode.getName()+" sera supprim? lors du prochain commit", "Validation" , 1) ;
							else
								JOptionPane.showMessageDialog(null ,"Le projet "+selectedNode.getName()+" est supprim??");
							//((CTree)getModel()).remove(selectedNode);
							((CTree)getModel()).refreshFirst((CTreeNode)((CTree)getModel()).getRoot());
						}
					}catch (Exception ex) {
						ex.printStackTrace();
						ErrorManager.getInstance().display();
					}
					
					
					
				}
				
			});
			menuCommit.addActionListener(new openICommit());
			menuRefrechFL.addActionListener( new ActionListener() {
			public void actionPerformed (ActionEvent e){
				try {
					((CTree)getModel()).refreshFirst(selectedNode);
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
			
			
			menuVersion.addActionListener( new ActionListener() {
				public void actionPerformed (ActionEvent e){
					
					IWaiting iWait = IWaiting.getInstance();
			    	iWait.demarrer();
					ThreadVersion tv = new ThreadVersion(selectedNode,model);
					tv.start();
					
				}
			});
			
			popup.add(menuRefrechFL);
			popup.addSeparator();
			popup.add(menuRefresh);
			popup.add(menuCommit);
			popup.addSeparator();
			popup.add(menuRename);
			popup.add(menuDelete);
			popup.addSeparator();
			popup.add(menuVersion);

		}
		
		
		
		class openICommit implements ActionListener {
			public void actionPerformed (ActionEvent e){
				new ICommit(getSelectedNode());
			}
		}
		
		
		public void mouseReleased(MouseEvent me) {
			TreePath pathClicked = getPathForLocation(me.getX(),me.getY());
			
			int selRow = getRowForLocation(me.getX(), me.getY());
				
				if(selRow != -1) //&& me.getClickCount()==1)
					selectedNode = (CTreeNode)pathClicked.getLastPathComponent();
				else
					selectedNode = (CTreeNode) ((CTree)getModel()).getRoot();;
		
			
			if ((SwingUtilities.isLeftMouseButton(me))&&(!isPathSelected(pathClicked))) {		
					
					clearSelection();
			} 
			
			if(!SvnHistory.isUnderVersion((File)selectedNode.getUserObject())&&!selectedNode.isRoot()){
				menuRefresh.setEnabled(false);
				XAGDOP.getInstance().allowUpdate(false);
			}
				
			else{
				menuRefresh.setEnabled(true);
				XAGDOP.getInstance().allowUpdate(true);
			}
				
			
			
			if (selectedNode.isProject())
			{
				try {
					if ((XAGDOP.getInstance()).getUser().isPManager(selectedNode.getName()))
					{
						XAGDOP.getInstance().delProject.setEnabled(true);
						XAGDOP.getInstance().equipe.setEnabled(true);
						XAGDOP.getInstance().menuProjetTeam.setEnabled(true);
						
					}else{
						XAGDOP.getInstance().equipe.setEnabled(false);
						XAGDOP.getInstance().menuProjetTeam.setEnabled(false);
						XAGDOP.getInstance().delProject.setEnabled(false);
						
					}
						
				} catch (Exception e) {
					ErrorManager.getInstance().display();
				}
				this.menuCommit.setEnabled(true);
			}
			else
			{
					XAGDOP.getInstance().delProject.setEnabled(true);
					XAGDOP.getInstance().equipe.setEnabled(false);
					XAGDOP.getInstance().menuProjetTeam.setEnabled(false);
			}
				
				
				//changement du noeud courrant
			if(!selectedNode.isRoot())
			{
				XAGDOP.getInstance().menuEditeCommit.setEnabled(true);
				this.menuCommit.setEnabled(true);
				//rechargement de larbre en memoire				
				try {
					DependenciesParser.getInstance().setFile(selectedNode.getProject().getName());
				} catch (NullPointerException e) {
					ErrorManager.getInstance().display();
				} catch (Exception e) {
					ErrorManager.getInstance().display();
				}
			}
			else
			{
				XAGDOP.getInstance().menuEditeCommit.setEnabled(false);
				this.menuCommit.setEnabled(false);
			}
			
				
			if ((SwingUtilities.isRightMouseButton(me))){//&&(isPathSelected(pathClicked))) {
				if(getRowForLocation(me.getX(), me.getY())!=-1)
				{
					setSelectionRow(getRowForLocation(me.getX(), me.getY()));
					popup.show(me.getComponent(), me.getX(), me.getY());
				}
			} 
			
			XAGDOP.getInstance().setNoPanel();
			
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
	
	 public void valueChanged( TreeSelectionEvent e ) {
       
        
     }
	
}
