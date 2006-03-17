package xagdop.Interface.Help;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import xagdop.Interface.XAGDOP;
import xagdop.ressources.Bundle;

public class Help extends JFrame implements TreeSelectionListener{

	private static final long serialVersionUID = 1L;
	private JEditorPane htmlPane;
	private JTree tree;
	private URL helpURL;
	public Help(){
   // super(new GridLayout(1,0));

    //Create the nodes.
    DefaultMutableTreeNode top =
        new DefaultMutableTreeNode("The Java Series");
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
    splitPane.setDividerLocation(200);
    
    //treeView.setPreferredSize(new Dimension(100, 100)); 

    splitPane.setPreferredSize(new Dimension(600, 300));

    //Add the split pane to this panel.
    getContentPane().add(splitPane, BorderLayout.CENTER);
    setSize(700,700);
	setResizable(false);
	setVisible(true);
	}
	private void initHelp() {
		// TODO Auto-generated method stub
		String s = Bundle.getText("test1");
        helpURL = Help.class.getResource(s);
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

	        category = new DefaultMutableTreeNode("Books for Java Programmers");
	        top.add(category);

	        //original Tutorial
	        book = new DefaultMutableTreeNode(new BookInfo
	            ("The Java Tutorial: A Short Course on the Basics",
	            		Bundle.getText("test1")));
	        category.add(book);
	        //Tutorial Continued
	        book = new DefaultMutableTreeNode(new BookInfo
	            ("The Java Tutorial Continued: The Rest of the JDK",
	            Bundle.getText("test2")));
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
            bookURL = Help.class.getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }
	
        public String toString() {
            return bookName;
        }
	}
	
	/*private static final long serialVersionUID = 1L;
	private JPanel rightPanel;
    private JSplitPane JSpane;
    private JTree tree;
    
    public Help(){
    	init();
    }
	private void init() {
		// TODO Auto-generated method stub
		JSpane = new JSplitPane();
		rightPanel = null;
		
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
	        
	        //visualiser un fichier selon les roles
	        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ihelp.files.view"), new
	        		MiddlePanel(24)));
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
		public class PanelInfo {
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
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getLastSelectedPathComponent();

		if (node == null) return;
		
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
		PanelInfo pInfo = (PanelInfo)nodeInfo;
		displayPanel(pInfo.mPanel);
		System.out.println(pInfo.mPanel);
		JSpane.setRightComponent(pInfo.mPanel);
		}
	}
	private void displayPanel(JPanel panel) {
		// TODO Auto-generated method stub
		if(rightPanel != null)
			JSpane.remove(rightPanel);
		rightPanel=panel;
		SwingUtilities.updateComponentTreeUI(panel);
		JSpane.setRightComponent(rightPanel);
		updateComponent();
	}
	private void updateComponent() {
		// TODO Auto-generated method stub
		JSpane.paintAll(JSpane.getGraphics());
	}
	*/

	}
