/*
 * Created on 25 oct. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package xagdop.Interface;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTree;
import xagdop.Interface.Configuration.IWelcome;
import xagdop.Interface.Management.IJAdmin;
import xagdop.Interface.Management.IJTeamManagement;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Interface.Preferences.IPreferencesFile;
import xagdop.Interface.SvnInterface.ICheckOut;
import xagdop.Interface.SvnInterface.ICommit;
import xagdop.Interface.SvnInterface.IComposantCreate;
import xagdop.Interface.SvnInterface.IProject;
import xagdop.Model.User;
import xagdop.Util.ErrorManager;
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
	protected User user;
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


	/*
	 * Initialisation of Menu
	 * 
	 */
	protected JMenu menuFile = new JMenu(Bundle.getText("main.menu.file"));
	protected JMenuItem fileMenuQuit = new JMenuItem(Bundle.getText("main.menu.file.quit"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/quit.gif"))) ;
	
	protected JMenu menuEdite = new JMenu(Bundle.getText("main.menu.edit"));
	protected JMenuItem menuEditeCommit = new JMenuItem(Bundle.getText("main.menu.edite.commit"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/envoyer.jpg")));
	protected JMenuItem menuEditeUpdate = new JMenuItem(Bundle.getText("main.menu.edite.update"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/update11.jpg")));
	
	protected JMenu menuShow = new JMenu(Bundle.getText("main.menu.show"));
	protected JCheckBoxMenuItem menuShowProblems = new JCheckBoxMenuItem(Bundle.getText("main.menu.show.problems"));
	
	protected JMenu menuConf = new JMenu(Bundle.getText("main.menu.parameters"));
	protected JMenuItem menuConfPreferences = new JMenuItem(Bundle.getText("main.menu.parameters.preferences"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/configure.png")));
	
	protected JMenu menuProjet = new JMenu(Bundle.getText("main.menu.project"));
	protected JMenuItem menuProjetTeam = new JMenuItem(Bundle.getText("main.menu.project.team"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/equipe.jpeg")));
	protected JMenuItem menuProjetCreate = new JMenuItem(Bundle.getText("main.menu.project.newProject"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/folder_new.png")));
	protected JMenuItem menuProjetDelete = new JMenuItem(Bundle.getText("main.menu.project.delProject"), new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/delete.jpg")));
	protected JMenuItem menuProjetPreferences = new JMenuItem("Fichier Preferences");
	protected JMenuItem menuProjetComposantCreate = new JMenuItem("Creation Composant");
	
	protected JMenu menuHelp = new JMenu(Bundle.getText("main.menu.help"));
	protected JMenuItem menuHelpAbout = new JMenuItem(Bundle.getText("main.menu.help.about"));
	
	//End of Menu Initialisation
	
	protected IProjectTree tree;
	
	private XAGDOP(){
		
		//init();
		//testsParsers tests = new testsParsers(); 
	}
	public void showFrame(){
		init();
		setVisible(true);
		refreshButton();
		//Ouverture de la fenetre de problemes
		//IProblemsList ipl = new IProblemsList();
		//ipl.setVisible(true);
	
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
		imageURL = XAGDOP.class.getResource("/xagdop/ressources/Icon/update11.jpg");
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
		
		menuShowProblems.addActionListener(new openIProblemsList());
		
		
		
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

		
		menuConfPreferences.addActionListener(new openIPreferences());
		menuConfPreferences.setMnemonic('P');
		

		
		
		menuHelpAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				IWelcome iabout = new IWelcome();
				iabout.setVisible(true);
				
			}
		}) ;
			
		menuFile.add(fileMenuQuit);
		menuProjet.add(menuProjetTeam);
		menuProjet.add(menuProjetCreate);
		menuProjet.add(menuProjetDelete);
		menuProjet.add(menuProjetPreferences);
		menuProjet.add(menuProjetComposantCreate);
		menuShow.add(menuShowProblems);
		menuEdite.add(menuEditeUpdate);
		menuEdite.add(menuEditeCommit);
		menuHelp.add(menuHelpAbout);
		menuConf.add(menuConfPreferences);
		
		
		menuBar.add(menuFile);
		menuBar.add(menuEdite);
		menuBar.add(menuShow);
		menuBar.add(menuProjet);
		menuBar.add(menuConf);
		menuBar.add(menuHelp);
		
		
	//	menuBar2.add(commit);
	//	menuBar2.add(Box.createHorizontalStrut(15)) ;
		
		menuBar2.add(projet);
		//menuBar2.add(Box.createHorizontalStrut(5)) ;
		menuBar2.add(delProject);
		menuBar2.add(Box.createHorizontalStrut(15)) ;

		menuBar2.add(update);
		menuBar2.add(Box.createHorizontalStrut(15)) ;
		
		menuBar2.add(equipe);
		//menuBar2.add(Box.createHorizontalStrut(5)) ;
		menuBar2.add(admin);
		menuBar2.add(Box.createHorizontalStrut(15)) ;
		
		menuBar2.add(preferences);
		
		
		//tableVersion = new JTable(new MyTableModel());
		
		//tableVersion.setBorder(BorderUIResource.getBlackLineBorderUIResource()  );
		tree = new IProjectTree();
		
        JTable table = new JTable( tree.getDirModel() );
        table.setShowHorizontalLines( false );
        table.setShowVerticalLines( false );
        table.setIntercellSpacing( new Dimension( 0, 2 ) );
        //table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        table.getColumn( "Type" ).setCellRenderer( new DirectoryRenderer() );
        table.getColumn( "Type" ).setMaxWidth( 32 );
        table.getColumn( "Type" ).setMinWidth( 32 );

        JScrollPane treeScroller = new JScrollPane( tree );
        JScrollPane tableScroller = new JScrollPane( table );
        treeScroller.setMinimumSize( new Dimension( 0, 0 ) );
        tableScroller.setMinimumSize( new Dimension( 0, 0 ) );
        tableScroller.setBackground( Color.white );
        JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                                               treeScroller,
                                               tableScroller );
        splitPane.setDividerLocation(200);
        splitPane.setContinuousLayout( true );
//      Create the tree
		Border border = BorderFactory.createLoweredBevelBorder();
		tree.setBorder(border);
		
        pan2.add( splitPane , BorderLayout.CENTER);
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
			
			try {
				if (XAGDOP.getInstance().getUser().isPManager(projectName))
				{
					IJTeamManagement ijteam = IJTeamManagement.getIJTM(projectName);				
					ijteam.refreshUsers();
					ijteam.setVisible(true);
				}
				else
				{
					ErrorManager.getInstance().setErrMsg("Vous n'avez pas les droits pour faire ceci.");
					ErrorManager.getInstance().setErrTitle("Gestion des equipes");
					ErrorManager.getInstance().display();
				}
			} catch (Exception e1) {
				ErrorManager.getInstance().display();
			}


			
		}
		
	}
//	****************************
	class actionCommit implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
				new ICommit(tree.getSelectedNode());
		}
	}
//	****************************
	class actionUpdate implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
//			((CTree)tree.getModel()).refresh(tree.getSelectedNode());
			if(tree.getSelectedNode().isRoot())
				new ICheckOut();
			else
				((CTree)tree.getModel()).refresh(tree.getSelectedNode());
		}
	}
	

	class openComposantCreate implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			if (tree.getSelectedNode().isProject())
			{
				IComposantCreate cc = new IComposantCreate(tree.getSelectedNode().getName());
				cc.setVisible(true);	
			}
			else
			{
				//System.out.println("ce n'est pas un projet ! ");
			}
		}
	}
	class openIProblemsList implements ActionListener { 
		public void actionPerformed (ActionEvent e)  {
			IProblemsList ipbl = IProblemsList.getInstance();
			ipbl.setVisible(true);
			menuShowProblems.setSelected(true);
			ipbl.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE) ;
			ipbl.addWindowListener(new WindowAdapter()
			    {
				public void windowClosing(WindowEvent e) // when closing unchecks the matching checkbox
				{
				    menuShowProblems.setSelected(false) ;
				}
				
			    }) ;
			
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
					
					try {
						cp.deleteProject(tree.getSelectedNode());
					} catch (Exception e1) {
						e1.printStackTrace();
						ErrorManager.getInstance().display();
					}
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
		try {
			((CTree)tree.getModel()).refreshFromLocal(tree.getSelectedNode());
		} catch (SVNException e) {
			ErrorManager.getInstance().display();
		}
	}
	
	
	/*
	 * Permet de raffraichir les boutons en fonctions des droits de l'utilisateur
	 */
	public void refreshButton(){
		if (getUser().isPcreator()){
			projet.setEnabled(true);
			menuProjetCreate.setEnabled(true);
			delProject.setEnabled(true);
			menuProjetDelete.setEnabled(true);
		}
		else
		{
			projet.setEnabled(false);
			menuProjetCreate.setEnabled(false);			
			delProject.setEnabled(false);
			menuProjetDelete.setEnabled(false);
		}
		if (getUser().isAdmin()){
			admin.setEnabled(true);
		}
		else
		{
			admin.setEnabled(false);
		}
				
		
	}
	
	  public class DirectoryRenderer extends DefaultTableCellRenderer {
	        /**
	    	 * 
	    	 */
	    	private static final long serialVersionUID = 1L;

	    	public Component getTableCellRendererComponent(JTable table, Object value,
	                                                       boolean isSelected, boolean hasFocus, int row, int column) {

	            if ( value != null && value instanceof Icon ) {
	               super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
	               setIcon( (Icon)value );
	               setText( "" );
	               return this;
	            }
	            else {
	               setIcon( null );
	            }

	            return super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
	        }
	    }
	
}
