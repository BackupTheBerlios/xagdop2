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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.AbstractTableModel;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Model.User;
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
	private static XAGDOP xag = null;
	protected  User user;
	protected JPanel panel = new JPanel();
	protected JMenuBar menuBar = new JMenuBar();
	protected JMenuBar menuBar2 = new JMenuBar();
	
	protected JButton commit;
	protected JButton update;
	protected JButton projet;
	protected JButton equipe;
	protected JButton delProject;
	protected JButton preferences;
	protected JButton admin;
	protected JTable tableVersion;
	
	/***************************/
	 protected ICheckOut Ico;
	/***************************/


	/*
	 * Initialisation of Menu
	 * 
	 */
	protected JMenu menuFile = new JMenu(Bundle.getText("main.menu.file"));
	protected JMenuItem fileMenuQuit = new JMenuItem(Bundle.getText("main.menu.file.quit"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/menu_quit.gif"))) ;
	
	protected JMenu menuEdite = new JMenu(Bundle.getText("main.menu.edit"));
	protected JMenuItem menuEditeCommit = new JMenuItem(Bundle.getText("main.menu.edite.commit"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/envoyer.jpg")));
	protected JMenuItem menuEditeUpdate = new JMenuItem(Bundle.getText("main.menu.edite.update"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/reload.png")));
	protected JMenuItem menuEditeCheck = new JMenuItem(Bundle.getText("main.menu.edite.checkout"));
	
	protected JMenu menuConf = new JMenu(Bundle.getText("main.menu.parameters"));
	protected JMenuItem menuConfPreferences = new JMenuItem(Bundle.getText("main.menu.parameters.preferences"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/tools.jpg")));
	
	protected JMenu menuProjet = new JMenu(Bundle.getText("main.menu.project"));
	protected JMenuItem menuProjetTeam = new JMenuItem(Bundle.getText("main.menu.project.team"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/equipe.jpeg")));
	protected JMenuItem menuProjetCreate = new JMenuItem(Bundle.getText("main.menu.project.newProject"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/new-tab.png")));
	protected JMenuItem menuProjetDelete = new JMenuItem(Bundle.getText("main.menu.project.delProject"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
	protected JMenuItem menuProjetPreferences = new JMenuItem("Fichier Preferences");
	protected JMenuItem menuProjetComposantCreate = new JMenuItem("Creation Composant");
	
	protected JMenu menuHelp = new JMenu(Bundle.getText("main.menu.help"));
	protected JMenuItem menuHelpAbout = new JMenuItem(Bundle.getText("main.menu.help.about"));
	
	//End of Menu Initialisation
	
	// TODO
	// Effacer apres le travail des L3
	//Menu for L3
	//JMenu menuL3 = new JMenu("Menu L3");
	//JMenuItem menuL3Admin = new JMenuItem("Fenetre ADMIN");
	//JMenuItem menuL3TM = new JMenuItem("Fenetre Team Management");
	
	
	
	protected CTreeNode currentNode;
	
	protected IProjectTree tree;
	
	private XAGDOP(){
		
		//init();
		//testsParsers tests = new testsParsers(); 
	}
	public void showFrame(){
		init();
		setVisible(true);
		refreshButton();
	}

	private void init(){
		
		//Image for button
		java.net.URL imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/envoyer.jpg");
		commit = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/equipe.jpeg");
		equipe = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/folder_new.png");
		projet = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/delete.jpg");
		delProject = new JButton(new ImageIcon(imageURL));
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/reload.png");
		update = new JButton(new ImageIcon(imageURL));		
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/configure.png");
		preferences = new JButton(new ImageIcon(imageURL));		
		admin= new JButton("ADMIN");
		
		JPanel pan = (JPanel)this.getContentPane();
		JPanel pan2 = new JPanel(new BorderLayout());
		pan.setLayout(new BorderLayout());
		
		
		//Ajout des bouton sur le panel
		update.setMargin(new Insets(0,0,0,0));
		update.setToolTipText("Update");
		commit.setMargin(new Insets(0,0,0,0));
		commit.setToolTipText("Commit");
		projet.setMargin(new Insets(0,0,0,0));
		projet.setToolTipText(Bundle.getText("main.menu.project.newProject"));
		delProject.setMargin(new Insets(0,0,0,0));
		delProject.setToolTipText(Bundle.getText("main.menu.project.delProject"));
		equipe.setMargin(new Insets(0,0,0,0));
		equipe.setToolTipText(Bundle.getText("main.menu.project.team"));
		preferences.setMargin(new Insets(0,0,0,0));
		preferences.setToolTipText(Bundle.getText("main.menu.paramaters.preferences"));
		admin.setMargin(new Insets(0,0,0,0));
		
		
		update.addActionListener(new actionUpdate());
		
		menuFile.setMnemonic('F') ;
		menuEdite.setMnemonic('E') ;
		
		fileMenuQuit.setMnemonic('Q') ;
		fileMenuQuit.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e)
			{
				int confirmQuit = JOptionPane.showConfirmDialog(null , Bundle.getText("main.confirmQuit.label") , Bundle.getText("main.confirmQuit.title") , JOptionPane.YES_NO_OPTION);
				if (confirmQuit == JOptionPane.OK_OPTION )
				{
					System.exit(0) ;
				}
				
			}
				}) ;
		
		
		
		
		
		menuProjetTeam.addActionListener(new openIUser());
		menuProjetTeam.setMnemonic('T');
		menuProjetCreate.addActionListener(new openIprojet());
		menuProjetCreate.setMnemonic('C');
		menuProjetDelete.setMnemonic('E');
		menuProjetDelete.addActionListener(new delProject());
		menuProjetPreferences.addActionListener(new openFilePreferences());
		menuProjetComposantCreate.addActionListener(new openComposantCreate());
		
		menuEditeUpdate.addActionListener(new actionUpdate());
		menuEditeUpdate.setMnemonic('T');
//		TODO FonctionCommit		
		menuEditeCommit.addActionListener(new actionCommit());
		menuEditeCommit.setMnemonic('C');
//		TODO FonctionCheck		
		menuEditeCheck.addActionListener(new actionUpdate());
		menuEditeCheck.setMnemonic('K');
		
		menuConfPreferences.addActionListener(new openIPreferences());
		menuConfPreferences.setMnemonic('P');
		
		
//		admin.addActionListener(new ActionListener()
//				{
//			public void actionPerformed(ActionEvent e)
//			{
//				IJAdmin ijadmin = IJAdmin.getIJA();
//				ijadmin.setVisible(true);
//				
//			}
//				}) ;
//		
//		equipe.addActionListener(new ActionListener() { 
//			public void actionPerformed (ActionEvent e)  {
//				
//				
//				
//				String projectName = tree.getSelectedNode().getName();
//				System.out.println(projectName);
//				if (XAGDOP.getInstance().getUser().isPManager(projectName))
//				{
//					IJTeamManagement iteam = new IJTeamManagement(projectName);
//					//iteam.refreshCombo();
//					iteam.setVisible(true);
//				}
//				else
//				{
//					JOptionPane.showMessageDialog(null ,"pas le droit :P", "Plop plop plop" , 1) ;
//					
//				}
//
//
//				
//			}});
//		
		
		
		menuHelpAbout.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent e)
			{
				IAbout iabout = IAbout.getIA();
				iabout.setVisible(true);
				
			}
				}) ;
			
		menuFile.add(fileMenuQuit);
		menuProjet.add(menuProjetTeam);
		menuProjet.add(menuProjetCreate);
		menuProjet.add(menuProjetDelete);
		menuProjet.add(menuProjetPreferences);
		menuProjet.add(menuProjetComposantCreate);
		
		menuEdite.add(menuEditeUpdate);
		menuEdite.add(menuEditeCommit);
		menuEdite.add(menuEditeCheck);
		menuHelp.add(menuHelpAbout);
		menuConf.add(menuConfPreferences);
		
		
		//TODO
		//A changer apres le travail des L3
		//menuL3.add(menuL3Admin);
		//menuL3.add(menuL3TM);
		
		
		
		
		menuBar.add(menuFile);
		menuBar.add(menuEdite);
		menuBar.add(menuProjet);
		menuBar.add(menuConf);
		menuBar.add(menuHelp);
		
		//TODO 
		// a supprimer apres le travail des L3
		//menuBar.add(menuL3);
		
		
		menuBar2.add(commit);
		menuBar2.add(update);
		menuBar2.add(Box.createHorizontalStrut(45)) ;
		menuBar2.add(projet);
		menuBar2.add(delProject);
		menuBar2.add(equipe);
		menuBar2.add(Box.createHorizontalStrut(45)) ;
		menuBar2.add(preferences);
		menuBar2.add(Box.createHorizontalStrut(30)) ;
		menuBar2.add(admin);
		
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
		projet.addActionListener (new openIprojet());
		equipe.addActionListener (new openIUser());
		//****************************
		commit.addActionListener (new actionCommit());
		//****************************
		delProject.addActionListener(new delProject());
		preferences.addActionListener(new openIPreferences());
		admin.addActionListener(new openIAdmin());
		setTitle(Bundle.getText("main.title"));
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Initialisation des boutons
		equipe.setEnabled(false);
		menuProjetTeam.setEnabled(false);
		Ico = new ICheckOut();
		//Cr??ation des parsers
		/*System.out.println("Parser");
		DependenciesParser.getInstance();
		UsersParser.getInstance();
		ProjectsParser.getInstance();
		System.out.println("Fin Parser");
		CRole role = new CRole("Blabla");
		ArrayList test = role.getViewFileRight();
		System.out.println(test.toString());*/
		
		
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
	
	/*
	 * Action listener associes aux differents boutons
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	class openIAdmin implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			IJAdmin ijadmin = IJAdmin.getIJA();
			ijadmin.setVisible(true);
		}
	}
	
	class openIprojet implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IProject projet = IProject.getIP();
			projet.setVisible(true);
		}
		
	}


	
	class openIUser implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			
			
			String projectName = tree.getSelectedNode().getName();
			
			if (XAGDOP.getInstance().getUser().isPManager(projectName))
			{
				IJTeamManagement ijteam = IJTeamManagement.getIJTM(projectName);				
				ijteam.refreshUsers();
				ijteam.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null ,"pas le droit :P", "Plop plop plop" , 1) ;
				
			}


			
		}
		
	}
//	****************************
	class actionCommit implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
				ICommit IC = new ICommit(tree.getSelectedNode());
		}
	}
//	****************************
	class actionUpdate implements ActionListener {
		public void actionPerformed(ActionEvent e){
				((CTree)tree.getModel()).refresh(tree.getSelectedNode());
		}
	}
	

	class openComposantCreate implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IComposantCreate cc = IComposantCreate.getICC();
			cc.setVisible(true);
		}
	}
	
	
	class openIPreferences implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IPreferences preferences = IPreferences.getIPref();
			preferences.setVisible(true);
		}
	}
	class openFilePreferences implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IPreferencesFile preferences = IPreferencesFile.getInstance();
			preferences.setVisible(true);
		}
	}
	
	class delProject implements ActionListener { 
			public void actionPerformed (ActionEvent e)  {
				int confirmSupp = JOptionPane.showConfirmDialog(null , Bundle.getText("main.confirmSupp.label") , Bundle.getText("main.confirmSupp.title") , JOptionPane.YES_NO_OPTION);
				if (confirmSupp == JOptionPane.OK_OPTION )
				{
					CProject cp = new CProject();
					cp.deleteProject(tree.getSelectedNode());
				}
			}
	}
	
	/**
	 * @return Returns the singleton.
	 */
	public static XAGDOP getInstance() {
		if (xag==null){
			xag = new XAGDOP(); 
		}
		
		return xag;
	}
	

	
	/*
	 * Return Tree of XAGDOP.java
	 * 
	 */
	public IProjectTree getTree() {
		return tree;
	}

	/*
	 * Set the current User
	 */
	public void setUser(User newUser) {
		user = newUser;
	}
	/*
	 * Get the current User
	 */
	public User getUser() {
		return user;
	}
	
	/*
	 * Permet de raffraichir l'arbre
	 */
	public void refreshTree(){
		((CTree)tree.getModel()).refreshFromLocal(tree.getSelectedNode());
	}
	
	
	/*
	 * Permet de raffraichir les boutons en fonctions des droits de l'utilisateur
	 */
	public void refreshButton(){
		if (getUser().isPcreator()){
			projet.setEnabled(true);
			menuProjetCreate.setEnabled(true);
		}
		else
		{
			projet.setEnabled(false);
			menuProjetCreate.setEnabled(false);			
			
		}
		if (getUser().isAdmin()){
			admin.setEnabled(true);
		}
		else
		{
			admin.setEnabled(false);
		}
				
		
	}
	
	/*
	 * Acces au projet courant
	 */
	public CTreeNode getCurrentNode() {
		return currentNode;
	}
	/*
	 * Modification du projet courant
	 */
	public void setCurrentNode(CTreeNode currentNode) {
		this.currentNode = currentNode;
	}
	
	
}
