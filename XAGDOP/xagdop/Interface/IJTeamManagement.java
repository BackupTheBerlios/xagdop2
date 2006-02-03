package xagdop.Interface;

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

import xagdop.Controleur.CTeamManagement;
import xagdop.Model.Project;
import xagdop.Parser.ProjectsParser;
import xagdop.ressources.Bundle;
import xagdop.Interface.IAffect;

public class IJTeamManagement extends JFrame{
	
	/**
	 * 
	 */
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
	private CTeamManagement CTeamM;	
	private String nP;
	
	private IJTeamManagement(String ProjectName){
		nP = ProjectName;		
		init();
	}	
	
	private void init(){
		getContentPane().setLayout(new GridBagLayout());
		nP = this.getNomProjet();
		this.projet = ProjectsParser.getInstance().buildProject(nP);
		System.out.println(nP);
		CTeamM = new CTeamManagement(nP);		
		
		newPanel.setLayout(new GridBagLayout());
		
		newPanel.setMinimumSize(new Dimension(296, 130));
		
		ButtonOK.setText(Bundle.getText("ijteam.jtable.ok"));
		ButtonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				int j =JT.getRowCount();            	
				int i=0;
				while(i<j)
				{
					CTeamM.Apply((String)JT.getValueAt(i,0),((Boolean)JT.getValueAt(i,1)).booleanValue(),((Boolean)JT.getValueAt(i,2)).booleanValue(),((Boolean)JT.getValueAt(i,3)).booleanValue(),((Boolean)JT.getValueAt(i,4)).booleanValue());
					i++;  
				}        					
				(IJAdmin.getIJA()).dispose();				
				
				//fermer la fenetre
				dispose();
			}
		});	// Fin bouton OK
		
		ButtonAffecter.setText(Bundle.getText("ijteam.jtable.affecter"));
		ButtonAffecter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				IAffect IA = IAffect.getIA();
				IA.setLocation(300,300);
				IA.setProjectName(nP);
				IA.setVisible(true);
				IJTM.refreshUsers();             
				getContentPane().validate();	    
			}
		}); // Fin bouton Affecter
		
		
		ButtonCancel.setText(Bundle.getText("button.cancel"));
		ButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				//Fermer la fenetre
				dispose();
			}
		}); // Fin bouton Cancel
		
		
		JT = new JTable(new IJTeamManagementTableModel(projet));
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
		
		this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
		JSP = new JScrollPane(JT);
		newPanel.add(JSP,gbc);
		this.donnerContrainte(gbc,0,1,1,1,100,100,GridBagConstraints.NONE);
		newPanel.add(ButtonOK, gbc);
		this.donnerContrainte(gbc,1,1,1,1,100,100,GridBagConstraints.NONE);
		newPanel.add(ButtonAffecter,gbc);
		this.donnerContrainte(gbc,2,1,1,1,100,100,GridBagConstraints.NONE);
		newPanel.add(ButtonCancel,gbc);
		
		
		getContentPane().add(newPanel, new GridBagConstraints());
		pack();
		this.getContentPane().validate();
		super.setTitle(Bundle.getText("main.button.team"));
		super.setVisible(true);
	}// Fin Init
	
	
	public void refreshUsers()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
		
		this.projet = ProjectsParser.getInstance().buildProject(nP);
		((IJTeamManagementTableModel)this.JT.getModel()).init(projet);
		JT.setDefaultEditor(JButton.class, new IJTeamManagementTableCellEditor(this,this.nP));
		this.remove(newPanel);
		this.newPanel.remove(JSP);
		
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
	
	public static IJTeamManagement getIJTM(String nP) {
		
		if (IJTM==null){
			IJTM = new IJTeamManagement(nP); 
		}
		IJTM.setNomProjet(nP);
		
		return IJTM;
	}
	
	public JTable getTable() {
		return this.JT;
	}
	
	public void setProject(Project newProject){
		projet = newProject;
	}
	
}

class IJTeamManagementTableCellEditor extends AbstractCellEditor 
implements TableCellEditor, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6769375527688617834L;
	protected static final String EDIT = "edit";
	
	private JButton JB;
	private String nomProjet;
	
	
	public IJTeamManagementTableCellEditor(IJTeamManagement IJTM, String projet) 
	{
		this.nomProjet = projet;
		JB = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		JB.setActionCommand(EDIT);
		JB.addActionListener(this);
		JB.setBorderPainted(false);
	}
	
	
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (EDIT.equals(e.getActionCommand())) {
			this.desaffecterUser();			
		}
	}
	
	public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) 
	{
		return JB;
	}
	
	public void desaffecterUser() {
		
		int rowToDelete;
		
		int out = JOptionPane.showOptionDialog(IJTeamManagement.getIJTM(this.nomProjet).getTable(),new String(Bundle.getText("ijteam.jtableSuppression.text")),new String(Bundle.getText("ijteam.jtableSuppressionUser.text")),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
		if(out == JOptionPane.YES_OPTION) 
		{
			rowToDelete =IJTeamManagement.getIJTM(this.nomProjet).getTable().getSelectedRow();		
			ProjectsParser.getInstance().removeUser(this.nomProjet,((String)IJTeamManagement.getIJTM(this.nomProjet).getTable().getModel().getValueAt(rowToDelete,0)));
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
		this.setIcon(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		this.setSize(new Dimension(this.getIcon().getIconHeight(),this.getIcon().getIconWidth()));
		return this;
	}
}


class IJTeamManagementTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomColonne[] = {"Users","Architecte","Analyste","Redacteur","PManager","Desaffecter"};
	private Object[][] rowData;
		
	public IJTeamManagementTableModel( Project myProject) {
			
		this.init(myProject);		
	}
	
	public int getRowCount() {
		return this.rowData.length;
	}
	
	public int getColumnCount() {
		return this.nomColonne.length;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.rowData[rowIndex][columnIndex];
	}
	
	public Class getColumnClass(int colonne) {
		return getValueAt(0,colonne).getClass();
	}
	
	public String getColumnName(int colonne) {
		return this.nomColonne[colonne];
	}
	
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
