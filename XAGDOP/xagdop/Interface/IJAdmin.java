package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.*;
import javax.swing.DefaultCellEditor;

import xagdop.Controleur.CAdmin;
import xagdop.Model.Users;
import xagdop.Parser.UsersParser;

public class IJAdmin extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048783116748619019L;
	private static IJAdmin IJA = null;
	
	private JCheckBox AdminCheck = new JCheckBox();
    private JLabel AdminLabel = new JLabel();
    private JButton ButtonApply = new JButton();
    private JButton ButtonCancel = new JButton();
    private JButton ButtonOK = new JButton();
    private JButton ButtonCreateUser = new JButton();
    private JCheckBox PManagerCheck = new JCheckBox();
    private JLabel PManagerLabel = new JLabel();
    private JLabel UserLabel = new JLabel();
    private JComboBox UserListCombo = new JComboBox();
    private JPanel newPanel = new JPanel();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private UsersParser users;
    private CAdmin cadmin;
    private JTable JT;
    
	private IJAdmin(){
		init();
	}
	
	
	private void init(){
        getContentPane().setLayout(new GridBagLayout());
        cadmin = new CAdmin();

        users = new UsersParser();
        ArrayList list = users.getAllUsers();
        
        
        newPanel.setLayout(new GridBagLayout());

        newPanel.setMinimumSize(new Dimension(296, 130));
        /*UserLabel.setText("Selectionner l'utilisateur");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        newPanel.add(UserLabel, gridBagConstraints);
        UserLabel.getAccessibleContext().setAccessibleName("Choisir l'utilisateur");


        

    	//Remplissage de la combobox avec les valeurs de la list
      	for(int i=0; i<list.size(); i++)
    	{
      		UserListCombo.addItem(((Users)list.get(i)).getLogin());
		}
      	
      	
        UserListCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	refresh();
            	
            	
            	
            	
            }
        });

       
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        newPanel.add(UserListCombo, gridBagConstraints);


        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        newPanel.add(AdminCheck, gridBagConstraints);

       
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        newPanel.add(PManagerCheck, gridBagConstraints);

        AdminLabel.setText("Administrateur");
        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        newPanel.add(AdminLabel, gridBagConstraints);

        PManagerLabel.setText("Chef de Projet");
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        newPanel.add(PManagerLabel, gridBagConstraints);*/

        ButtonOK.setText("Ok");
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               cadmin.Apply(users,users.getId((String)UserListCombo.getSelectedItem()),AdminCheck.isSelected(),PManagerCheck.isSelected());
            }
        });

        
        /*gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonOK, gridBagConstraints);*/

        ButtonCancel.setText("Annuler");
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                IJA.dispose();
            }
        });

        
        /*gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonCancel, gridBagConstraints);*/

        ButtonApply.setText("Appliquer");
        
        /*gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;*/
        ButtonApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               cadmin.Apply(users,users.getId((String)UserListCombo.getSelectedItem()),AdminCheck.isSelected(),PManagerCheck.isSelected());
            }
        });
        //newPanel.add(ButtonApply, gridBagConstraints);

        ButtonCreateUser.setText("Creer Nouvel User");
        ButtonCreateUser.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		IUserCreate IUC = IUserCreate.getIUC();
        		IUC.setVisible(true);
        	}
        });
        
        //newPanel.add(ButtonCreateUser, new GridBagConstraints());*/
        //
        JT = new JTable(new IJAdminTableModel());
        JT.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.setUsersColumn(JT,JT.getColumnModel().getColumn(1));
        JT.getColumnModel().getColumn(0).setMaxWidth(0);
        JT.getColumnModel().getColumn(0).setMinWidth(0);
        JT.setDefaultRenderer(JButton.class, new IJAdminTableCellRenderer());
        
        GridBagConstraints gbc = new GridBagConstraints();
        this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(new JScrollPane(JT),gbc);
        
        this.donnerContrainte(gbc,0,1,1,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(ButtonOK, gbc);
        
        this.donnerContrainte(gbc,1,1,1,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(ButtonCancel,gbc);
        
        this.donnerContrainte(gbc,2,1,1,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(ButtonCreateUser, gbc);
        
        
        getContentPane().add(newPanel, new GridBagConstraints());
        
        //modif mick => JTable
        JTable JTMUsers = new JTable(new DefaultTableModel(5,5));
        newPanel.add(JTMUsers, new GridBagConstraints());
        pack();
		setTitle("Administration");
		//setSize(300, 200);
		
	}
	
	public void setUsersColumn(JTable table,TableColumn usersColumn) 
	{
		//Set up the editor for the sport cells.
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Snowboarding");
		comboBox.addItem("Rowing");
		comboBox.addItem("Knitting");
		comboBox.addItem("Speed reading");
		comboBox.addItem("Pool");
		comboBox.addItem("None of the above");
		usersColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		usersColumn.setCellRenderer(renderer);
	}
	
	/*public void setDeleteColumn(JTable table,TableColumn deleteColumn) 
	{
		//Set up the editor for the sport cells.
		JButton JBDelete = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		deleteColumn.setCellEditor(new IJAdminCellEditor());
		
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		deleteColumn.setCellRenderer(renderer);
	}*/
	
	public void refresh(){
		AdminCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isAdmin());
		PManagerCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isPmanager());
		
		
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
	
	/**
	 * @return Returns the singleton.
	 */
	public static IJAdmin getIJA() {
		if (IJA==null){
			IJA = new IJAdmin(); 
		}
		
		return IJA;
	}
}