package xagdop.Interface.Help;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import xagdop.ressources.Bundle;

public class Help extends JFrame implements TreeSelectionListener{
	

	private static final long serialVersionUID = 1L;
	private JPanel rightPanel;
    private JSplitPane JSpane;
    private JTree tree;
    
    public Help(){
    	init();
    }
	private void init() {
		// TODO Auto-generated method stub
		JSpane = new JSplitPane();
		rightPanel = new JPanel();
		
		 DefaultMutableTreeNode top =
	            new DefaultMutableTreeNode(Bundle.getText("ihelp.tree.root"));
		 createNode(top);
		 tree = new JTree(top);
		 JSpane.setLeftComponent(tree);
	     JSpane.setRightComponent(rightPanel);

	     getContentPane().add(JSpane, BorderLayout.CENTER);
	 	addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exit();
			}

			private void exit() {
				// TODO Auto-generated method stub
				dispose();
			}

		});
		setSize(700,700);
		setResizable(false);
		setVisible(true);
	}

		private void createNode(DefaultMutableTreeNode top) {
		    DefaultMutableTreeNode category = null;
	        DefaultMutableTreeNode book = null;

	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.projects"));
	        top.add(category);

	        //creer un projet
	        book = new DefaultMutableTreeNode(Bundle.getText("ihelp.projects.create"));
	        category.add(book);
	        
	        //supprimer un projet
	        book = new DefaultMutableTreeNode(Bundle.getText("ihelp.projects.delete"));
	        category.add(book);

	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.files")); 
	        top.add(category);

	        //ajouter un fichier ou repertoire
	        book = new DefaultMutableTreeNode(Bundle.getText("ihelp.files.add"));
	        category.add(book);

	        //mettre a jour un fichier
	        book = new DefaultMutableTreeNode(Bundle.getText("ihelp.files.refresh"));
	        category.add(book);
	        
	        //recuperer  un fichier
	        book = new DefaultMutableTreeNode(Bundle.getText("ihelp.files.get"));
	        category.add(book);
	        
	        
	        
	
		
	}
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
