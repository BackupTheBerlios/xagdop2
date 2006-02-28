package xagdop.Interface.Help;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;



import xagdop.ressources.Bundle;

public class IHelp extends JFrame implements TreeSelectionListener{

	private IHelp ihp=null;
	
	private static final long serialVersionUID = 1L;
	private JPanel leftPanel;
	private JPanel centralPanel;
	private JButton buttonOk;
	private JButton buttonCancel;
	
	private Box mBox;
	private JTree tree;
	private JScrollPane scroll;
	private static JButton buttonApply;
	
	
	private static ArrayList changedHelpList;
	
	public IHelp(){
		init();
		changedHelpList = new ArrayList();
	}	
	private void init() {
		setTitle(Bundle.getText("ihelp.title"));
		leftPanel = new JPanel();
		centralPanel = null;
		
		mBox = Box.createHorizontalBox() ;
		
		
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode(Bundle.getText("ihelp.tree.root"));
        createNode(top);
        
        
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

      
        tree.addTreeSelectionListener(this);

		scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(180,450));
		

		Border border = BorderFactory.createLoweredBevelBorder();
		tree.setBorder(border);
		scroll.getViewport().add(tree);
		
		
		
		leftPanel.setLayout(new BorderLayout());
		createButtons();
		leftPanel.add(mBox, BorderLayout.SOUTH);
		leftPanel.setPreferredSize(new Dimension(500, 450));
		

		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll, BorderLayout.WEST);
		getContentPane().add(leftPanel, BorderLayout.CENTER);
		
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exit();
			}

		});
		setSize(700,700);
		setResizable(false);
		setVisible(true);
        
        
        

		
	}
	private void createButtons() {
		// TODO Auto-generated method stub
		
	}
	private class PanelInfo {
        public String pName; // nom du panel
        public JPanel mPanel; // le panel

        public PanelInfo(String pName, JPanel mPanel) {
            this.pName = pName;
            this.mPanel = mPanel;
        }

        public String toString() {
            return pName;
        }
    }
	private void createNode(DefaultMutableTreeNode top) {
		    DefaultMutableTreeNode category = null;
	        DefaultMutableTreeNode book = null;

	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.projects"));
	        top.add(category);

	        //creer un projet
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.projects.create"), new
	        		MiddlePanel(11)));
	        category.add(book);
	        
	        //supprimer un projet
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.projects.delete"), new
	        		MiddlePanel(12)));
	        category.add(book);

	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.files")); 
	        top.add(category);

	        //ajouter un fichier ou repertoire
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.files.add"), new
	        		MiddlePanel(21)));
	        category.add(book);

	        //mettre a jour un fichier
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.files.refresh"), new
	        		MiddlePanel(22)));
	        category.add(book);
	        
	        //recuperer  un fichier
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.files.get"), new
	        		MiddlePanel(23)));
	        category.add(book);
	        
	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.userinproject"));
	        top.add(category);

	        //ajouter utilisateur dans un projet
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.userinproject.add"), new
	        		MiddlePanel(31)));
	        category.add(book);
	        
	        //Modifier les roles d'un utilisateur dans un projet
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.userinproject.modify"), new
	        		MiddlePanel(32)));
	        category.add(book);
	        
	        //supprimer utilisateur dans un projet
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.userinproject.delete"), new
	        		MiddlePanel(33)));
	        category.add(book);
	        
	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.user"));
	        top.add(category);
	        
	        //ajouter utilisateur 
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.user.add"), new
	        		MiddlePanel(41)));
	        category.add(book);
	        
	        //modifier les droits de l'utilisateur
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.user.modify"), new
	        		MiddlePanel(42)));
	        category.add(book);
	        
	        //supprimer utilisateur 
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.user.delete"), new
	        		MiddlePanel(43)));
	        category.add(book);
	        
	        
	}
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getLastSelectedPathComponent();

		if (node == null) return;
		
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
		PanelInfo pInfo = (PanelInfo)nodeInfo;
		displayPanel(pInfo.mPanel);
		}
		
	}


	private void displayPanel(JPanel panel) {
		if(centralPanel != null)
			leftPanel.remove(centralPanel);
		centralPanel = panel;
		SwingUtilities.updateComponentTreeUI(panel);
		leftPanel.add(centralPanel, BorderLayout.CENTER);
		updateComponent();
		getContentPane().add(leftPanel, BorderLayout.EAST);
		
	}
	public void updateComponent(){
   		leftPanel.paintAll(leftPanel.getGraphics());
	}
	private void exit() {
		dispose();
		ihp = null;
		
	}

}
