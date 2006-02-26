package xagdop.Interface.Management;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import xagdop.Interface.XAGDOP;
import xagdop.Model.Project;
import xagdop.Parser.ProjectsParser;
import xagdop.Thread.ThreadTeamManagement;
import xagdop.Thread.ThreadWait;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

/**
 * 
 * @author Antoine JULLIEN
 * Classe permettant la gestion des equipes d un projet avec une JTable
 *
 */

public class IJTeamManagement extends JFrame{
	
	private static final long serialVersionUID = -1080162447493236178L;
	private static IJTeamManagement IJTM= null;	
	
	private JButton ButtonCancel = new JButton();
	private JButton ButtonOK = new JButton();
	private JButton ButtonAffecter = new JButton();
	private JPanel newPanel = new JPanel();
	private JTable JT;
	private JScrollPane JSP;
	private GridBagConstraints gbc = new GridBagConstraints();
	private Project projet;
	private String nP;
	
	/**
	 * Constructeur de la classe IJTeamManagement
	 * @param	String: le nom du projet
	 * Appel  la fonction init()
	 */
	
	private IJTeamManagement(String ProjectName){
		nP = ProjectName;		
		init();
	}	
	
	/**
	 * fonction init() permettant l affichage de la fenetre
	 */
	private void init(){
		getContentPane().setLayout(new GridBagLayout());
		nP = this.getNomProjet();
		try {
			this.projet = ProjectsParser.getInstance().buildProject(nP);
		} catch (Exception e1) {
			ErrorManager.getInstance().display();
		}
		newPanel.setLayout(new GridBagLayout());		
		newPanel.setMinimumSize(new Dimension(296, 130));
		
//		 Creation du bouton OK et son action associee
		ButtonOK.setText(Bundle.getText("ijteam.jtable.ok"));
		ButtonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				ThreadWait tWait = new ThreadWait(null);
				tWait.start();
				ThreadTeamManagement tTeamM = new ThreadTeamManagement(JT,nP,tWait);
				tTeamM.start();
								//fermer la fenetre
				dispose();
			}
		});	// Fin bouton OK
		
//		 Creation du bouton Affecter et son action associee
		ButtonAffecter.setText(Bundle.getText("ijteam.jtable.affecter"));
		ButtonAffecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				IAffect IA = IAffect.getIA(nP);				
				IA.setProjectName(nP);
				//IJTM.refreshUsers();             
				getContentPane().validate();	    
			}
		}); // Fin bouton Affecter
		
		// Creation du bouton Cancel et son action associee
		ButtonCancel.setText(Bundle.getText("button.cancel"));
		ButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Fermer la fenetre
				dispose();
			}
		}); // Fin bouton Cancel
		
		// Creation de la JTable
		JT = new JTable(new IJTeamManagementTableModel(projet));
		// Changement du modele de rendu et d edition par dedaut
		JT.setDefaultRenderer(JButton.class, new IJTeamManagementTableCellrenderer());
		JT.setDefaultEditor(JButton.class, new IJTeamManagementTableCellEditor(this,this.nP));
		
		JT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JT.setSize(new Dimension(650,200));
		JT.setPreferredScrollableViewportSize(JT.getSize());
		JT.getColumnModel().getColumn(0).setResizable(false);
		JT.getColumnModel().getColumn(1).setResizable(false);
		JT.getColumnModel().getColumn(2).setResizable(false);
		JT.getColumnModel().getColumn(3).setResizable(false);
		JT.getColumnModel().getColumn(4).setResizable(false);
		JT.getColumnModel().getColumn(5).setResizable(false);
		
		JT.getColumnModel().getColumn(0).setMinWidth(200);
		JT.getColumnModel().getColumn(1).setMinWidth(105);
		JT.getColumnModel().getColumn(2).setMinWidth(105);
		JT.getColumnModel().getColumn(3).setMaxWidth(105);
		JT.getColumnModel().getColumn(4).setMaxWidth(105);        
		JT.getColumnModel().getColumn(5).setMaxWidth(100);	// colonne desaffecter
		
		// Ajout de la JTable dans le panel
		this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
		JSP = new JScrollPane(JT);
		newPanel.add(JSP,gbc);
		// Ajout du bouton OK dans le panel		
		this.donnerContrainte(gbc,0,1,1,1,100,100,GridBagConstraints.NONE);
		newPanel.add(ButtonOK, gbc);
		// Ajout du bouton Affecter dans le panel
		this.donnerContrainte(gbc,1,1,1,1,100,100,GridBagConstraints.NONE);
		newPanel.add(ButtonAffecter,gbc);
		// Ajout du bouton Cancel dans la panel
		this.donnerContrainte(gbc,2,1,1,1,100,100,GridBagConstraints.NONE);
		newPanel.add(ButtonCancel,gbc);
		
		// Ajout du panel a la fenetre
		getContentPane().add(newPanel, new GridBagConstraints());
		pack();
		this.getContentPane().validate();
		super.setTitle(Bundle.getText("main.button.team"));
		super.setVisible(true);
	}// Fin Init
	
	/**
	 * Fonction refreshUser()
	 * Permet de rafraichir la Jtable avec ses composants
	 */
	public void refreshUsers()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
		
		try {
			this.projet = ProjectsParser.getInstance().buildProject(nP);
		
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		((IJTeamManagementTableModel)this.JT.getModel()).init(projet);
		JT.setDefaultEditor(JButton.class, new IJTeamManagementTableCellEditor(this,this.nP));
		this.remove(newPanel);
		this.newPanel.remove(JSP);
		
		// Instanciation du JScrollPane et ajout au panel
		JSP = new JScrollPane(JT);
		this.newPanel.add(JSP,gbc);
		this.getContentPane().add(newPanel, new GridBagConstraints());
		this.pack();
		this.getContentPane().validate();
	}
	
	
	/** Panel de message de fin de transaction
	 * @param gbc <CODE>GridBagConstraints</CODE> represente la contrainte qui va prendre les
	 * valeurs specifiees
	 * @param gx <CODE>int</CODE> represente la colonne dans laquelle l'?l?ment va etre place
	 * @param gy <CODE>int</CODE> represente la ligne dans laquelle l'?l?ment va etre place
	 * @param gw <CODE>int</CODE> represente le nombre de colonnes sur lesquelles l'?l?ment va etre place
	 * @param gh <CODE>int</CODE> represente le nombre de lignes sur lesquelles l'?l?ment va etre place
	 * @param wx <CODE>int</CODE> poucentage de place utilise dans sa colonne
	 * @param wy <CODE>int</CODE> poucentage de place utilise dans sa ligne
	 */
	public void donnerContrainte(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy)
	{
		gbc.gridx=gx;
		gbc.gridy=gy;
		gbc.gridwidth=gw;
		gbc.gridheight=gh;
		gbc.weightx=wx;
		gbc.weighty=wy;
		gbc.fill=GridBagConstraints.NONE;
	}
	
	/** Panel de message de fin de transaction
	 * @param gbc <CODE>GridBagConstraints</CODE> represente la contrainte qui va prendre les
	 * valeurs specifiees
	 * @param gx <CODE>int</CODE> represente la colonne dans laquelle l'?l?ment va etre place
	 * @param gy <CODE>int</CODE> represente la ligne dans laquelle l'?l?ment va etre place
	 * @param gw <CODE>int</CODE> represente le nombre de colonnes sur lesquelles l'?l?ment va etre place
	 * @param gh <CODE>int</CODE> represente le nombre de lignes sur lesquelles l'?l?ment va etre place
	 * @param wx <CODE>int</CODE> poucentage de place utilise dans sa colonne
	 * @param wy <CODE>int</CODE> poucentage de place utilise dans sa ligne
	 * @param constraint <CODE>int</CODE> contrainte de redimensionnement
	 */    
	public void donnerContrainte(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy, int constraint)
	{
		gbc.gridx=gx;
		gbc.gridy=gy;
		gbc.gridwidth=gw;
		gbc.gridheight=gh;
		gbc.weightx=wx;
		gbc.weighty=wy;
		gbc.fill=constraint;
	}
	
	
	public void setNomProjet(String nomProjet) {
		this.nP = nomProjet;
	}
	
	
	public String getNomProjet() {
		return nP;
	}
	
	/**
	 * @return Returns the singleton.
	 */
	public static IJTeamManagement getIJTM(String nP) {
		
		if (IJTM==null){
			IJTM = new IJTeamManagement(nP); 
		}
		IJTM.setNomProjet(nP);
		
		return IJTM;
	}
	
	/**
	 * Fonction de retour de l'instance de JTable 
	 * @return JTable
	 */
	public JTable getTable() {
		return this.JT;
	}
	
	public void setProject(Project newProject){
		projet = newProject;
	}
	
}

/**
 * 
 * @author Antoine JULLIEN
 * Classe d edition du bouton supprimer appartenant a la JTable.
 * Cette classe recoit des evenements et execute l'action actionPerformed
 * permettant de desaffecter un utilisateur d un projet
 *
 */
class IJTeamManagementTableCellEditor extends AbstractCellEditor 
implements TableCellEditor, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6769375527688617834L;
	protected static final String EDIT = "edit";
	
	private JButton JB;
	private String nomProjet;
	
	/**
	 * Constructeur de la classe IJTeamManagementTableCellEditor
	 * @param IJTM
	 * @param projet
	 */
	public IJTeamManagementTableCellEditor(IJTeamManagement IJTM, String projet) 
	{
		this.nomProjet = projet;
		JB = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/delete.jpg")));
		JB.setActionCommand(EDIT);
		JB.addActionListener(this);
		JB.setBorderPainted(false);
	}
	
	/**
	 * 
	 */
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Action permettant de desaffecter un utilisateur d un projet
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (EDIT.equals(e.getActionCommand())) {
			this.desaffecterUser();			
		}
	}
	
	/**
	 * 
	 */
	public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) 
	{
		return JB;
	}
	
	/**
	 * Methode desaffecterUser()
	 * Methode permettant de supprimer un utilisateur d un projet	 *
	 */
	public void desaffecterUser() {
		
		int rowToDelete;// ligne a supprimer dans la Jtable
		
		int out = JOptionPane.showOptionDialog(IJTeamManagement.getIJTM(this.nomProjet).getTable(),new String(Bundle.getText("ijteam.jtableSuppression.text")),new String(Bundle.getText("ijteam.jtableSuppressionUser.text")),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
		if(out == JOptionPane.YES_OPTION) 
		{
			rowToDelete =IJTeamManagement.getIJTM(this.nomProjet).getTable().getSelectedRow();		
			try {
				// Suppression
				ProjectsParser.getInstance().removeUser(this.nomProjet,((String)IJTeamManagement.getIJTM(this.nomProjet).getTable().getModel().getValueAt(rowToDelete,0)));
			} catch (Exception e) {
				ErrorManager.getInstance().display();
			}
			// Rafraichir la JTable
			IJTeamManagement.getIJTM(this.nomProjet).refreshUsers();			
		}
	}	
}

/**
 * 
 * @author JULLIEN Antoine
 * @docRoot Classe de rendu permettant d'afficher des boutons
 * dans la JTable.
 * Cette classe etend JButton et implemente TableCellRenderer
 */
class IJTeamManagementTableCellrenderer extends JButton implements TableCellRenderer
{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * @docRoot Ce constructeur initialise les instances de cet objet opaques.
	 */
	public IJTeamManagementTableCellrenderer()
	{
		this.setOpaque(true);
	}
	
	/**
	 * Methode surchargee qui retourne l'objet a afficher dans une cellule
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean arg2, boolean arg3, int arg4, int arg5) 
	{
		this.setIcon(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/delete.jpg")));
		this.setSize(new Dimension(this.getIcon().getIconHeight(),this.getIcon().getIconWidth()));
		return this;
	}
}

/**
 * 
 * @author Antoine JULLIEN
 * Classe IJTeamManagementTableModel qui represente la modele de donnees 
 * de la JTable pour IJTeamManagement
 * Cette classe recupere les donnees du ProjectParser pour avoir 
 * les utilisateurs d un projet
 */
class IJTeamManagementTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private String nomColonne[] = {"Users","Architecte","Analyste","Redacteur","PManager","Desaffecter"};
	private Object[][] rowData;
		
	/**
	 * Constructeur de la classe IJTeamManagementTableModel
	 * @param myProject
	 * Appel a la fonction init()
	 */
	public IJTeamManagementTableModel( Project myProject) {
			
		this.init(myProject);		
	}
	
	/**
	 * Retourne le nombre de lignes de la structure
	 */
	public int getRowCount() {
		return this.rowData.length;
	}
	
	/**
	 * Retourne le nombre de colonnes de la structure
	 */
	public int getColumnCount() {
		return this.nomColonne.length;
	}
	
	/**
	 * Fonction qui retourne l objet de la cellule (rowIndex, ColumnIndex)
	 * @param rowIndex: le numero de la ligne
	 * @param columnIndex: le numero de colonne
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.rowData[rowIndex][columnIndex];
	}
	
	/**
	 * 
	 */
	public Class getColumnClass(int colonne) {
		return getValueAt(0,colonne).getClass();
	}
	
	/**
	 * Fonction qui retourne le nom de la colonne 
	 * @param: colonne: indice de la colonne dont on veut le nom
	 * @return: String: le nom de la colonne
	 */
	public String getColumnName(int colonne) {
		return this.nomColonne[colonne];
	}
	
	/**
	 * Fonction init() qui permet d'initialiser le model avec les diffrentes 
	 * donnees recuperees dans ProjectParser
	 * @param projet
	 */
	public void init(Project projet) {
		ArrayList users = projet.getUsersLogin();
		this.rowData = new Object[users.size()][this.getColumnCount()];
		for(int i = 0;i<users.size();i++){			
			rowData[i][0] =	(String)users.get(i);
			rowData[i][1] = new Boolean(projet.isArchitect((String)users.get(i)));
			rowData[i][2] = new Boolean(projet.isAnalyst((String)users.get(i)));
			rowData[i][3] = new Boolean(projet.isRedactor((String)users.get(i)));
			rowData[i][4] = new Boolean(projet.isManager((String)users.get(i)));
			rowData[i][5] = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));				
		}
	}
	
	public boolean isCellEditable(int row, int column) {
		if (column != 0) 
			return true;
		else
			return false;
	}
	
	/**
	 * @param value: Object to put at a specific location
	 * @param row: row where to put value
	 * @param col: column where to put value
	 * 
	 * Update the model when data are changed and send an TableCell event
	 */
	public void setValueAt(Object value, int row, int col) 
	{
		if(col<this.getColumnCount() && row<this.getRowCount())
		{
			this.rowData[row][col] = value;
			this.fireTableCellUpdated(row, col);
		}
	}
}
