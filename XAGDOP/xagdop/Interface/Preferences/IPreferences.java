package xagdop.Interface.Preferences;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CPreferencies;
import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Interface.IProjectTree;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Svn.SvnConnect;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;


public class IPreferences extends JFrame implements TreeSelectionListener{
	/**
	 * 
	 */
	private static IPreferences IPref = null;
	private static String defaultPath;//= ((File)new File("project")).getAbsolutePath()+File.separator; 
	
	private static final long serialVersionUID = 1L;
	private JPanel mLastEast;
	private JPanel mCentralPanel;
	private JButton mOkButton;
	private JButton mCancelButton;
	
	private Box mBox;
	private JTree mTree;
	private JScrollPane mScroll;
	private static JButton mApplyButton;
	
	public static final int LOCAL_PATH_REF = 1;
	public static final int REMOTE_PATH_REF = 2;
	public static final int LNF_REF = 3;
	public static final int LANGUAGE_REF = 4;
	public static final int PASSWORD_REF = 5;
	
	public static final int ADD = 0;
	public static final int DEL = 1;
	
	private static ArrayList changedPrefList;
	
	private IPreferences(){
		init();
		changedPrefList = new ArrayList();
	}	
	
	private void init(){
		setTitle(Bundle.getText("ipreferences.title"));
		mLastEast = new JPanel();
		mCentralPanel = null;		
		
		mBox = Box.createHorizontalBox() ;
		
//		Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode(Bundle.getText("ipreferences.tree.root"));
        createNodes(top); 

        //Create a tree that allows one selection at a time.
        mTree = new JTree(top);
        mTree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        mTree.addTreeSelectionListener(this);

		mScroll = new JScrollPane();
		mScroll.setPreferredSize(new Dimension(180,450));
		
//		Create the tree
		Border border = BorderFactory.createLoweredBevelBorder();
		mTree.setBorder(border);
		mScroll.getViewport().add(mTree);
		
		
		//Create East Panel
		mLastEast.setLayout(new BorderLayout());
		createButtons();
		mLastEast.add(mBox, BorderLayout.SOUTH);
		mLastEast.setPreferredSize(new Dimension(500, 450));
		
//		add components
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mScroll, BorderLayout.WEST);
		getContentPane().add(mLastEast, BorderLayout.CENTER);
		
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exit();
			}
		});
		setSize(670,450);
		setResizable(false);
		setVisible(true);
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IPreferences getIPref() {
		if (IPref==null){
			IPref = new IPreferences(); 
		}
		return IPref;
	}


	public static String getDefaultPath() {
		return defaultPath;
	}

	public static void setDefaultPath(String defaultPath) {
		IPreferences.defaultPath = defaultPath;
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
	
	private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;

        category = new DefaultMutableTreeNode(Bundle.getText("ipreferences.tree.paths"));
        top.add(category);

        //Repertoire Local de travail
        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.localWorkspace.title"), new
        		LocalPathPanel()));
        category.add(book);
        
        //URL du serveur de travail
        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.serverUrl.title"), new
        		RemotePathPanel()));
        category.add(book);

        category = new DefaultMutableTreeNode(Bundle.getText("ipreferences.tree.printing")); // affichage
        top.add(category);
        
        //Look and Feel
        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.LNF.title"), new
        		LookAndFeelPanel()));
        category.add(book);
        
        //Language
        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.language.title"), new
        		LanguagePanel()));
        category.add(book);
        
        category = new DefaultMutableTreeNode(Bundle.getText("ipreferences.tree.userData"));
        top.add(category);

        //Mot de passe
        book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.password.title"), new
        		PasswordPanel()));
        category.add(book);
    }

	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        mTree.getLastSelectedPathComponent();

		if (node == null) return;
		
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
		PanelInfo pInfo = (PanelInfo)nodeInfo;
		displayPanel(pInfo.mPanel);
		}
	}
	
	protected void createButtons(){
		mOkButton = new JButton(Bundle.getText("button.ok"));
   		mCancelButton = new JButton(Bundle.getText("button.cancel"));
   		mApplyButton = new JButton(Bundle.getText("button.apply"));
   		mApplyButton.setEnabled(false);
   		mCancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cancelChanges();
				dispose();
			}
		});
   		mApplyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				applyChanges();
			}
		});
   		mOkButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				applyChanges();
				exit();
			}
		});
   		mBox.add(Box.createHorizontalGlue());
   	
   		addButton(mOkButton, Bundle.getChar("button.ok.mnemonic"));
   		mBox.add (Box.createRigidArea(new Dimension(10,0))); // ajoute un espace
   		addButton(mApplyButton, Bundle.getChar("button.apply.mnemonic"));
   		mBox.add (Box.createRigidArea(new Dimension(10,0))); // ajoute un espace  
   		addButton(mCancelButton, Bundle.getChar("button.cancel.mnemonic"));
	}
	
	protected void addButton(JButton button, char mnemonique)
	{
       	button.setMnemonic(mnemonique);
   		if ((button.getText()).length() < 10)
   			button.setPreferredSize(new Dimension(85,30));
       	//button.setMinimumSize(new Dimension(150,139));
   		//button.setMaximumSize(new Dimension(340,30));
   		button.setMargin(new Insets(2,5,2,5));
   		button.setActionCommand(button.getText());
   		mBox.add(button);
	}
	
	public void displayPanel (JPanel panel) {
		if(mCentralPanel != null)
			mLastEast.remove(mCentralPanel);
		mCentralPanel = panel;
		SwingUtilities.updateComponentTreeUI(panel);
		mLastEast.add(mCentralPanel, BorderLayout.CENTER);
		updateComponent();
		getContentPane().add(mLastEast, BorderLayout.EAST);
	}
	
	public void updateComponent(){
   		mLastEast.paintAll(mLastEast.getGraphics());
	}
	
	public static void prefHasChanged(int ref, int action) {
		Integer myInt = new Integer(ref);
		if (action == ADD) {
			if (!changedPrefList.contains(myInt)) changedPrefList.add(myInt);
		}
		else if (action == DEL)
			if (changedPrefList.contains(myInt)) {
				
				int pos = changedPrefList.indexOf(myInt);
				//changedPrefList.remove(myInt);
				changedPrefList.remove(pos);
			}
		
		if (changedPrefList.size() != 0)
			mApplyButton.setEnabled(true);
		else
			mApplyButton.setEnabled(false);
	}
	
	private void cancelChanges() {
		PasswordPanel.eraseFields();
		exit();
	}
	
	private void applyChanges() {
		Integer ref;
		for (int i = 0; i < changedPrefList.size(); i++) {
			ref = (Integer) changedPrefList.get(i);
			switch (ref.intValue()) {
			case LOCAL_PATH_REF: CPreferencies.setLocalPath(LocalPathPanel.getLocalPath());
					//Refresh du panel
						try {
						((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFirst((CTreeNode)((XAGDOP.getInstance().getTree()).getModel().getRoot()));
					} catch (SVNException e) {
						ErrorManager.getInstance().setErrMsg("Probleme d'adresse locale");
						ErrorManager.getInstance().setErrTitle("Probleme chemin local");
						ErrorManager.getInstance().display();
						
					}
					break;
			case REMOTE_PATH_REF: CPreferencies.setServerPath(RemotePathPanel.getRemotePath());
					//Refresh des fichiers de projet et de user
					try{			
							//On remet le nouveau path
							SvnConnect.getInstance().setUrl(RemotePathPanel.getRemotePath());
							//On s'y reconnecte
							SvnConnect.getInstance().connect();
							//On fait un update
							SvnUpdate svnu = new SvnUpdate();
							//On recupere les fichiers user et project
							svnu.getFiles();
								//On remet a jour les arbres en memoire
								ProjectsParser.getInstance();
								UsersParser.getInstance();
							//On fait un refresh du local
							((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()).refreshFirst((CTreeNode)((XAGDOP.getInstance().getTree()).getModel().getRoot()));
						}catch (Exception e1)
						{
							ErrorManager.getInstance().setErrMsg("Probleme lors du changement du chemin distant");
							ErrorManager.getInstance().setErrTitle("Probleme chemin distant");
							ErrorManager.getInstance().display();
						}
			
					break;
			case LNF_REF: CPreferencies.setDefaultLNF(LookAndFeelPanel.getLNF());
					break;
			case LANGUAGE_REF: CPreferencies.setDefaultLocale(LanguagePanel.getLanguage());
					JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.language.updated"));
					break;
			case PASSWORD_REF:
				if (!PasswordPanel.isPasswordCorrect())
					JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.password.bothNotTheSame"));
				else {
					boolean bOk;
					bOk = CPreferencies.submitPasswd(PasswordPanel.getFormerPassword(), 
							PasswordPanel.getNewPassword());
					if (!bOk) JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.password.formerNotGood"));
					PasswordPanel.eraseFields();
				}
			}
		}
		
		// Nettoyage du tableau
		changedPrefList.clear();
		mApplyButton.setEnabled(false);
	}
	
	private void exit(){
		dispose();
		IPref = null;
	}
}


