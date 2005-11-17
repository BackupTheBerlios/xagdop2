/*
 * Created on 25 oct. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xagdop.Interface;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.AbstractTableModel;

import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.ressources.Bundle;

//import ressources.Bundle;

/**
 * @author tyriaux
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XAGDOP extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	JMenuBar menuBar = new JMenuBar();
	JMenuBar menuBar2 = new JMenuBar();
	
	JButton commit;
	JButton update;
	JButton projet;
	JButton equipe;
	JTable tableVersion;
	
	JMenu menu1 = new JMenu("Fichier");
	JMenu menu2 = new JMenu("Edition");
	JMenu menu3 = new JMenu("Parametres");
	JMenu menu4 = new JMenu("A propos");
	JMenu menu5 = new JMenu("?");
	IProjectTree tree;
	
	public XAGDOP(){
		init();
		
	}
	
	private void init(){
		
		
		java.net.URL imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/envoyer.jpg");
		commit = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/equipe.jpeg");
		equipe = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/synch.jpg");
		projet = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/update.jpg");
		update = new JButton(new ImageIcon(imageURL));		
		
		JPanel pan = (JPanel)this.getContentPane();
		JPanel pan2 = new JPanel(new BorderLayout());
		pan.setLayout(new BorderLayout());
		
		update.setMargin(new Insets(0,0,0,0));
		update.setToolTipText("Update");
		commit.setMargin(new Insets(0,0,0,0));
		commit.setToolTipText("Commit");
		projet.setMargin(new Insets(0,0,0,0));
		projet.setToolTipText("Creer Projet");
		equipe.setMargin(new Insets(0,0,0,0));
		equipe.setToolTipText("Affecter Equipe");
	
		update.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						tree.removeAll();
						((CTree)tree.getModel()).refresh((CTreeNode)((CTree)tree.getModel()).getRoot());
					}
					
				}
			);
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);
		menuBar.add(menu5);
		


		
		menuBar2.add(commit);
		menuBar2.add(update);
		menuBar2.add(projet);
		menuBar2.add(equipe);
		
	
		
		tableVersion = new JTable(new MyTableModel());
			
		tableVersion.setBorder(BorderUIResource.getBlackLineBorderUIResource()  );
		
		tree = new IProjectTree();
		JScrollPane mScroll = new JScrollPane();
		mScroll.setPreferredSize(new Dimension(150,450));
		
		//Create the tree
		Border border = BorderFactory.createLoweredBevelBorder();
		tree.setBorder(border);
		mScroll.getViewport().add(tree);
		
		pan2.add(new JScrollPane(tableVersion), BorderLayout.CENTER);
		pan2.add(mScroll, BorderLayout.WEST);
		pan2.add(menuBar2, BorderLayout.NORTH);
		pan.add(menuBar, BorderLayout.NORTH);
		pan.add(pan2,BorderLayout.CENTER);
		projet.addActionListener (new ouvrirIprojet());
		equipe.addActionListener (new ouvrirIUser());
		setTitle(Bundle.getText("main.title"));
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class MyTableModel extends AbstractTableModel {
	
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames ={"Version", "Date Modification", "Commentaires", "Auteur"};
	    private String[][]data  = new String[8][4];

	    public MyTableModel(){
	    	data[0][0]="1.7";
	    	data[1][0]="1.4";
	    	data[2][0]="1.0";
	    	
	    	data[0][1]="24/10/2005";
	    	data[1][1]="12/10/2005";
	    	data[2][1]="01/10/2005";
	    	
	    	data[0][2]="Correction orthographique";
	    	data[1][2]="Maj des rubriques";
	    	data[2][2]="Creation";
	    	
	    	data[0][3]="Jeremy";
	    	data[1][3]="Remy";
	    	data[2][3]="Remy";
	    	
	    }
	    
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	  	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }

	    public Class getColumnClass(int c) {
	        return String.class;
	    }

	}
	


	class ouvrirIprojet implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IProject projet = IProject.getIP();
			projet.setVisible(true);
	}

	}
	class ouvrirIUser implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IUser user = IUser.getIU();
			user.setVisible(true);
	}

	}

	class ouvrirIPreferences implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IPreferences preferences = IPreferences.getIPref();
			preferences.setVisible(true);
			}

	}
	
	public static void main(String args[]){
		
		
		Bundle.setCurrentLocale(Locale.FRENCH);
	
		XAGDOP frame = new XAGDOP();
		frame.setVisible(true);
	}
	
}
