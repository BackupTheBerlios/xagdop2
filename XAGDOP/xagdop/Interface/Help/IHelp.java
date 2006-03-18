
package xagdop.Interface.Help;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import xagdop.Interface.XAGDOP;
import xagdop.ressources.Bundle;

public class IHelp extends JFrame implements TreeSelectionListener{

	private static final long serialVersionUID = 1L;
	private JEditorPane htmlPane;
	private JTree tree;
	private URL helpURL;
	private JButton buttonClose;
	private JPanel helpPanel;
	private JPanel buttonPanel;
	public IHelp(){
		
    //Create the nodes.
    DefaultMutableTreeNode top =
        new DefaultMutableTreeNode(Bundle.getText("ihelp.title"));
     createNodes(top);

    //Create a tree that allows one selection at a time.
    tree = new JTree(top);
    tree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);
    //Listen for when the selection changes.
    tree.addTreeSelectionListener(this);
    DefaultTreeCellRenderer monRenderer = new DefaultTreeCellRenderer();
    URL imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/iconHelp.jpg");
    URL imageURL1 = XAGDOP.class.getResource("/xagdop/ressources/Icon/blue.jpg");

	monRenderer.setOpenIcon(new ImageIcon(imageURL1));
    monRenderer.setClosedIcon(new ImageIcon(imageURL1));
    monRenderer.setLeafIcon(new ImageIcon(imageURL));
    tree.setCellRenderer(monRenderer);
    //Listen for when the selection changes.
    tree.addTreeSelectionListener(this);

    //Create the scroll pane and add the tree to it. 
    JScrollPane treeView = new JScrollPane();
    treeView.setPreferredSize(new Dimension(180,450));
    Border border = BorderFactory.createLoweredBevelBorder();
	tree.setBorder(border);
	treeView.getViewport().add(tree);
	
    //Create the HTML viewing pane.
    htmlPane = new JEditorPane();
    htmlPane.setEditable(false);
    initHelp();
    JScrollPane htmlView = new JScrollPane(htmlPane);
    
    //  Add the scroll panes to a split pane.
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(treeView);
    splitPane.setRightComponent(htmlView);
    Dimension minimumSize = new Dimension(100, 50);
    htmlView.setMinimumSize(minimumSize);
    treeView.setMinimumSize(minimumSize);
    splitPane.setDividerLocation(180);
    
    //treeView.setPreferredSize(new Dimension(100, 100)); 

    splitPane.setPreferredSize(new Dimension(600, 300));

    helpPanel = new JPanel();
    helpPanel.setLayout(new BorderLayout());
    helpPanel.add(splitPane,BorderLayout.CENTER);
    
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.setBackground(Color.white);
    buttonClose =new JButton(Bundle.getText("iabout.button.close"));
	buttonClose.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	});
	buttonPanel.add(buttonClose);
    //Add the split pane to this panel.
    getContentPane().add(helpPanel, BorderLayout.CENTER);
    getContentPane().add(buttonPanel,BorderLayout.SOUTH);
    setSize(700,700);
	setResizable(false);
	setVisible(true);
	}
	private void initHelp() {
		// TODO Auto-generated method stub
		String s = Bundle.getText("ihelp.index");
        helpURL = IHelp.class.getResource(s);
        if (helpURL == null) {
            System.err.println("Couldn't open help file: " + s);
        }

        displayURL(helpURL);
		
	}
	private void displayURL(URL url) {
		// TODO Auto-generated method stub
		try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
		htmlPane.setText("File Not Found");
                
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
	}
	private void createNodes(DefaultMutableTreeNode top) {
		// TODO Auto-generated method stub
		 DefaultMutableTreeNode category = null;
	        DefaultMutableTreeNode book = null;

	        //managing project
	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.projects"));
	        top.add(category);

	        	//Create a project
	        	book = new DefaultMutableTreeNode(new BookInfo
	        			(Bundle.getText("ihelp.projects.create"),
	        					Bundle.getText("ihelp.createproject")));
	        	category.add(book);
	        
	        	//Delete a project
	        	book = new DefaultMutableTreeNode(new BookInfo
	        			(Bundle.getText("ihelp.projects.delete"),
	        					Bundle.getText("ihelp.deleteproject")));
	        	category.add(book);
	        
	        //managing user in project
	        category = new DefaultMutableTreeNode(Bundle.getText("ihelp.userinproject"));
	        top.add(category);
	        
	        	//Add an user in a project
        		book = new DefaultMutableTreeNode(new BookInfo
        				(Bundle.getText("ihelp.userinproject.add"),
        						Bundle.getText("ihelp.add.userinproject")));
        		category.add(book);
        		
        		//Modify  user's role in a project
        		book = new DefaultMutableTreeNode(new BookInfo
        				(Bundle.getText("ihelp.userinproject.modify"),
        						Bundle.getText("ihelp.modify.userinproject")));
        		category.add(book);
        		
        		//Delete user in a project
        		book = new DefaultMutableTreeNode(new BookInfo
        				(Bundle.getText("ihelp.userinproject.delete"),
        						Bundle.getText("ihelp.delete.userinproject")));
        		category.add(book);
        		
        	//managing users
        	category = new DefaultMutableTreeNode(Bundle.getText("ihelp.user"));
    	        top.add(category);
	        
    	        //Create a user
    	        book = new DefaultMutableTreeNode(new BookInfo
        				(Bundle.getText("ihelp.user.add"),
        						Bundle.getText("ihelp.add.user")));
        		category.add(book);
        		
        		//Modify a user
        		book = new DefaultMutableTreeNode(new BookInfo
        				(Bundle.getText("ihelp.user.modify"),
        						Bundle.getText("ihelp.modify.user")));
        		category.add(book);
        		
        		//Delete a user
        		book = new DefaultMutableTreeNode(new BookInfo
        				(Bundle.getText("ihelp.user.delete"),
        						Bundle.getText("ihelp.delete.user")));
        		category.add(book);
    	        
	}
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getLastSelectedPathComponent();

		if (node == null) return;
			Object nodeInfo = node.getUserObject();
         if (node.isLeaf()) {
        	 BookInfo book = (BookInfo)nodeInfo;
        	 displayURL(book.bookURL);

         }
	
         
         }
	private class BookInfo {
        public String bookName;
        public URL bookURL;

        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = IHelp.class.getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }
	
        public String toString() {
            return bookName;
        }
	}
	
	

	}

