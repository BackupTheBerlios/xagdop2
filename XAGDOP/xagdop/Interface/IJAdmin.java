package xagdop.Interface;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Graphics;



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
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;


import xagdop.Controleur.CAdmin;

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
    private static JTable JT;
    private JScrollPane JSP;
    
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
            	int j =JT.getRowCount();
        
        		int i=0;
        		while(i<j)
        		{
        			
        		
        			cadmin.Apply(users,users.getId((String)JT.getValueAt(i,0)),((Boolean)JT.getValueAt(i,2)).booleanValue(),((Boolean)JT.getValueAt(i,1)).booleanValue());
        			i++;  
        		}
        	
            	
            	(IJAdmin.getIJA()).dispose();
              }
        });
        
        /*gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonOK, gridBagConstraints);*/

        ButtonCancel.setText("Annuler");
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                IJA.dispose();
                IJA = null;
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
        		getContentPane().validate();
        		System.out.println("new user");
        		//refreshUsers();
        	}
        });
        
        //newPanel.add(ButtonCreateUser, new GridBagConstraints());*/
        //
        System.out.println("creation jtable");
        JT = new JTable(new IJAdminTableModel(users.getAllUsers()));
        JT.setPreferredScrollableViewportSize(new Dimension(500, 200));
        JT.setDefaultRenderer(JButton.class, new IJAdminTableCellRenderer());
        //JT.setDefaultEditor(JButton.class, new IJAdminTableCellEditor());
        GridBagConstraints gbc = new GridBagConstraints();
        this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
        JSP = new JScrollPane(JT);
        newPanel.add(JSP,gbc);
        //JT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //JT.setCellSelectionEnabled(true);
        JT.addMouseListener(new MouseListener()
        	{
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					int rowToDelete;
					int idUser;
					if(IJAdmin.getIJAT().isColumnSelected(((IJAdminTableModel)IJAdmin.getIJAT().getModel()).getColumnCount()-1))
						{
						JOptionPane JOP = new JOptionPane();
						JOptionPane.showOptionDialog(JT,new String("Etes-vous sûr de vouloir supprimer cet utilisateur ?"),new String("Suppression d'un utilisateur"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
						System.out.println(JOP.getValue().toString());
						if(((String)JOP.getValue()).equals("non"))
							{
							rowToDelete = IJAdmin.getIJAT().getSelectedRow();
							System.out.println("user : "+((String)IJAdmin.getIJAT().getModel().getValueAt(rowToDelete,0)));
							idUser = users.getId(((String)IJAdmin.getIJAT().getModel().getValueAt(rowToDelete,0)));
							System.out.println("row: "+rowToDelete+" id: "+idUser);
							users.removeUser(idUser);
							IJAdmin.getIJA().refreshUsers();
							}
						}
				}

				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
				}

				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
        	
        	});
        this.donnerContrainte(gbc,0,1,1,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(ButtonOK, gbc);
        
        this.donnerContrainte(gbc,1,1,1,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(ButtonCancel,gbc);
        
        this.donnerContrainte(gbc,2,1,1,1,100,100,GridBagConstraints.BOTH);
        newPanel.add(ButtonCreateUser, gbc);
        
        
        getContentPane().add(newPanel, new GridBagConstraints());
        pack();
		setTitle("Administration");
		//setSize(300, 200);
		
	}
	
	public void refresh(){
		AdminCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isAdmin());
		PManagerCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isPmanager());
		
		
		}
	
	public void refreshUsers()
	{
		GridBagConstraints gbc = new GridBagConstraints();
        this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
		//Graphics g = this.getGraphics();
		this.users = new UsersParser();
		((IJAdminTableModel)this.JT.getModel()).init(this.users.getAllUsers());
		this.remove(newPanel);
		this.newPanel.remove(JSP);
		
		//this.JT = new JTable(new IJAdminTableModel(this.users.getAllUsers()));
		JSP = new JScrollPane(JT);
		this.newPanel.add(JSP,gbc);
		this.getContentPane().add(newPanel, new GridBagConstraints());
		this.pack();
		//this.validate();
		//((IJAdminTableModel)this.JT.getModel()).init();
		//this.update(g);
		this.getContentPane().validate();
		//g.dispose();
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
	
	/**
	 * @return Returns the JTable.
	 */
	public static JTable getIJAT() 
	{
		return JT;
	}
}