package xagdop.Interface.Preferences;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
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
import xagdop.Interface.IWaiting;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.DependenciesParser;
import xagdop.Parser.UsersParser;
import xagdop.Svn.SvnConnect;
import xagdop.Svn.SvnHistory;
import xagdop.Thread.ThreadServerChanged;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

/**
 * 
 * @author Paul Fresquet
 * @desc Cette classe definit la fenetre principale de la fenetre des preferences.
 * Implemente la Design Pattern Singleton
 *
 */
public class IPreferences extends JFrame implements TreeSelectionListener{
	private static IPreferences IPref = null;
	private static String defaultPath;
	
	private static final long serialVersionUID = 1L;
	private JPanel mLastEast;
	private JPanel mCentralPanel;
	private JButton mOkButton;
	private JButton mCancelButton;
	
	private Box mBox;
	private JTree mTree;
	private JScrollPane mScroll;
	private static JButton mApplyButton;
	private LocalPathPanel localPathPanel;
	private RemotePathPanel remotePathPanel;
	
	// Constantes utilisees pour determiner les modifications faites par l'utilisateur
	public static final int LOCAL_PATH_REF = 1;
	public static final int REMOTE_PATH_REF = 2;
	public static final int LNF_REF = 3;
	public static final int LANGUAGE_REF = 4;
	public static final int PASSWORD_REF = 5;
	
	// Constantes utilisees pour la gestion des modifiations
	public static final int ADD = 0;
	public static final int DEL = 1;
	
	// Contient la liste des elements qui ont ete modifies
	private static ArrayList changedPrefList;
	
	/**
	 * Constructeur de IPreferences
	 * Prive, conformement au Design Pattern Singleton
	 */
	private IPreferences(){
		init();
		changedPrefList = new ArrayList();
	}	
	
	/**
	 * Initialise les composants graphiques
	 *
	 */
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
	
	/**
	 * Non utilisée
	 * @return defaultPath
	 */
	public static String getDefaultPath() {
		return defaultPath;
	}
	
	/**
	 * Non utilisee
	 * @param defaultPath
	 */
	public static void setDefaultPath(String defaultPath) {
		IPreferences.defaultPath = defaultPath;
	}
	
	/**
	 * 
	 * @author Paul Fresquet
	 * Classe permettant de lier un panel de preferences à un nom
	 */
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
	
	/**
	 * Creer les noeuds du TreeView
	 * @param top
	 */
	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;
		
		category = new DefaultMutableTreeNode(Bundle.getText("ipreferences.tree.paths"));
		top.add(category);
		
		//Repertoire Local de travail
		book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.localWorkspace.title"),localPathPanel = new
				LocalPathPanel()));
		category.add(book);
		
		//URL du serveur de travail
		book = new DefaultMutableTreeNode(new PanelInfo(Bundle.getText("ipreferences.serverUrl.title"), remotePathPanel = new
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
	
	/**
	 * Utilisee lorsqu'un nouvel element est selectionne dans le TreeView, affiche le panel
	 * correspondant le cas echeant
	 */
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
	
	/**
	 * Cree les boutons OK, Cancel, Appliquer sur la fenetre
	 *
	 */
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
	
	/**
	 * Ajoute le bouton a la fenetre, en y associant un mnemonique
	 * @param button bouton a ajouter
	 * @param mnemonique mnemonique associe
	 */
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
	
	/**
	 * Affiche le panel sur la droite de la fenetre 
	 * @param panel
	 */
	public void displayPanel (JPanel panel) {
		if(mCentralPanel != null)
			mLastEast.remove(mCentralPanel);
		mCentralPanel = panel;
		SwingUtilities.updateComponentTreeUI(panel);
		mLastEast.add(mCentralPanel, BorderLayout.CENTER);
		updateComponent();
		getContentPane().add(mLastEast, BorderLayout.EAST);
	}
	
	/**
	 * Redessine les composants du panel de droite
	 *
	 */
	public void updateComponent(){
		mLastEast.paintAll(mLastEast.getGraphics());
	}
	
	/**
	 * Enregistre le fait qu'un des parametres a change. ref correspond a l'option qui a change,
	 * 		action correspond au fait que l'option a change, ou qu'elle a retrouve sa valeur
	 * 		initiale
	 * @param ref
	 * @param action
	 */
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
	
	/**
	 * Annule les changements effectues
	 *
	 */
	private void cancelChanges() {
		PasswordPanel.eraseFields();
		exit();
	}
	
	/**
	 * Applique les changements effectues
	 *
	 */
	private void applyChanges() {
		Integer ref;
		for (int i = 0; i < changedPrefList.size(); i++) {
			ref = (Integer) changedPrefList.get(i);
			switch (ref.intValue()) {
			case LOCAL_PATH_REF: 
				//Refresh du panel
				
				File localPath = new File(LocalPathPanel.getLocalPath());
				if(localPath.exists()){
					try {
						if(!SvnConnect.getInstance().getRepositoryUUID().equals(SvnHistory.getRepositoryUUID(localPath))){
							ErrorManager.getInstance().setErrMsg(Bundle.getText("ipreferences.localWorkspace.wrongRep.msg"));
							ErrorManager.getInstance().setErrTitle(Bundle.getText("ipreferences.localWorkspace.wrongRep.title"));
							ErrorManager.getInstance().display();
							localPathPanel.setLocalPath(CPreferencies.getLocalPath());
							return;
						}
						File localPathUser = new File(localPath,XAGDOP.getInstance().getUser().getLogin());
						if(!SvnConnect.getInstance().getRepositoryUUID().equals(SvnHistory.getRepositoryUUID(localPathUser))){
							ErrorManager.getInstance().setErrMsg(Bundle.getText("ipreferences.localWorkspace.wrongRep.msg"));
							ErrorManager.getInstance().setErrTitle(Bundle.getText("ipreferences.localWorkspace.wrongRep.title"));
							ErrorManager.getInstance().display();
							localPathPanel.setLocalPath(CPreferencies.getLocalPath());
							break;
						}
						
					} catch (SVNException e) {
						ErrorManager.getInstance().display();
						return;
					}
					File localPathUser = new File(localPath,XAGDOP.getInstance().getUser().getLogin());
					setDefaultPath(localPathUser.getAbsolutePath()+File.separator);
					try {
						DependenciesParser.getInstance().refreshFiles();
					} catch (IOException e) {
						ErrorManager.getInstance().display();
					}
					((CTree)XAGDOP.getInstance().getTree().getModel()).setRoot(new CTreeNode(localPathUser));				
					XAGDOP.getInstance().refreshTree();
					CPreferencies.setLocalPath(LocalPathPanel.getLocalPath());
				}
				else{
					ErrorManager.getInstance().setErrMsg(Bundle.getText("ipreferences.localWorkspace.notExists.msg"));
					ErrorManager.getInstance().setErrTitle(Bundle.getText("ipreferences.localWorkspace.notExists.title"));
					ErrorManager.getInstance().display();
					break;
				}
				break;
			case REMOTE_PATH_REF: //CPreferencies.setServerPath(RemotePathPanel.getRemotePath());
				IWaiting iWait = IWaiting.getInstance();
				iWait.demarrer();
				ThreadServerChanged tv = new ThreadServerChanged(remotePathPanel);
				tv.start();

			break;
			case LNF_REF: CPreferencies.setDefaultLNF(LookAndFeelPanel.getLNF());
			break;
			case LANGUAGE_REF: CPreferencies.setDefaultLocale(LanguagePanel.getLanguage());
			JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.language.updated"));
			break;
			case PASSWORD_REF:
				if (!PasswordPanel.isPasswordCorrect()){
					JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.password.bothNotTheSame"));
					
				}
				else {
					if (!PasswordPanel.lengthPasswordCorrect()){
						JOptionPane.showMessageDialog(this,Bundle.getText("cuser.length.text"));
					}
					else {
						boolean bOk;
						bOk = CPreferencies.submitPasswd(PasswordPanel.getFormerPassword(), 
							PasswordPanel.getNewPassword());
						if (!bOk) 
							JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.password.formerNotGood"));
						else
						{
							JOptionPane.showMessageDialog(this, Bundle.getText("ipreferences.password.modifOK"));
							try {
								UsersParser.getInstance().publish(UsersParser.getInstance().getUsersXML());
							} catch (Exception e) {
								ErrorManager.getInstance().display();
							}
						}
						PasswordPanel.eraseFields();
					}
				}
			}
		}
		
		// Nettoyage du tableau
		changedPrefList.clear();
		mApplyButton.setEnabled(false);
	}
	
	/**
	 * On quitte cette fenetre
	 *
	 */
	private void exit(){
		dispose();
		IPref = null;
	}
}


