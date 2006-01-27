package xagdop.Interface;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


import xagdop.Controleur.CAdmin;

import xagdop.Model.Users;
import xagdop.Parser.UsersParser;
import xagdop.ressources.Bundle;

public class IJAdmin extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048783116748619019L;
	private static IJAdmin IJA = null;
	
    private JButton ButtonCancel = new JButton();
    private JButton ButtonOK = new JButton();
    private JButton ButtonCreateUser = new JButton();
    private JPanel newPanel = new JPanel();
    private UsersParser users;
    private CAdmin cadmin;
    private JTable JT;
    private JScrollPane JSP;
    private GridBagConstraints gbc = new GridBagConstraints();
    
    /**
     * Constructeur de la classe <CODE>IJAdmin</CODE>
     * Appelle la fonction <CODE>init()</CODE>
     *
     */
	private IJAdmin(){
		init();
	}

	/**
	 * Fonction d'initialisation de la fenetre.
	 *
	 */
	private void init(){
        getContentPane().setLayout(new GridBagLayout());
        cadmin = new CAdmin();
        users = UsersParser.getInstance();        
        
        //Layout manager GridBagLayout
        //Configuration de la taille minimale
        newPanel.setLayout(new GridBagLayout());
        newPanel.setMinimumSize(new Dimension(296, 130));

        //Creation du bouton OK et configuration de l'action a effectuer
        ButtonOK.setText(Bundle.getText("button.ok"));
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	int j =JT.getRowCount();
        
        		int i=0;
        		while(i<j)
        		{
        			//Mise a jour des attributs de chaque utilisateur
        			cadmin.Apply(users,((String)JT.getValueAt(i,0)),((Boolean)JT.getValueAt(i,2)).booleanValue(),((Boolean)JT.getValueAt(i,1)).booleanValue());
        			i++;  
        		}      	
            	
            	(IJAdmin.getIJA()).dispose();
            	IJA = null;
              }
        });

        //Creation du bouton Cancel et configuration de l'action a effectuer
        ButtonCancel.setText(Bundle.getText("button.cancel"));
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//Rafraichissement et destruction de la fenetre
            	IJA.refreshUsers();
                IJA.dispose();
                IJA = null;

            }
        });

        //Creation du bouton Creer User et configuration de l'action a effectuer
        ButtonCreateUser.setText(Bundle.getText("ijadmin.createUser"));
        ButtonCreateUser.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		//Creation de la fenetre de creation d'un utilisateur
        		IUserCreate IUC = IUserCreate.getIUC();
        		IUC.setVisible(true);
        		getContentPane().validate();
        	}
        });
        
        //Creation de la JTable avec la liste des utilisateurs en parametres
        JT = new JTable(new IJAdminTableModel(users.getAllUsers()));
        
        //Changement du modele de rendu par defaut et du modele d'edition par defaut
        JT.setDefaultRenderer(JButton.class, new IJAdminTableCellRenderer());
        JT.setDefaultEditor(JButton.class, new IJAdminTableCellEditor(this));
        
        //JTable non redimensionnable
        JT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JT.setSize(new Dimension(450,200));
        JT.setPreferredScrollableViewportSize(JT.getSize());
        
        //Colonnes non redimensionnables
        JT.getColumnModel().getColumn(0).setResizable(false);
        JT.getColumnModel().getColumn(1).setResizable(false);
        JT.getColumnModel().getColumn(2).setResizable(false);
        JT.getColumnModel().getColumn(3).setResizable(false);
        
        //Configuration des tailles des colonnes
        JT.getColumnModel().getColumn(0).setMinWidth(200);
        JT.getColumnModel().getColumn(1).setMinWidth(105);
        JT.getColumnModel().getColumn(2).setMinWidth(105);
        JT.getColumnModel().getColumn(3).setMaxWidth(40);
        
        //Ajout de la JTable dans le panel d'affichage
        this.donnerContrainte(this.gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
        JSP = new JScrollPane(JT);
        newPanel.add(JSP,this.gbc);
        
        //Ajout du bouton OK dans le panel d'affichage
        this.donnerContrainte(this.gbc,0,1,1,1,100,100,GridBagConstraints.NONE);
        newPanel.add(ButtonOK, this.gbc);
        
        //Ajout du bouton Cancel dans le panel d'affichage
        this.donnerContrainte(this.gbc,1,1,1,1,100,100,GridBagConstraints.NONE);
        newPanel.add(ButtonCancel,this.gbc);
        
        //Ajout du bouton CreateUser dans le panel d'affichage
        this.donnerContrainte(this.gbc,2,1,1,1,100,100,GridBagConstraints.NONE);
        newPanel.add(ButtonCreateUser, this.gbc);
        
        //Ajout du panel a la fenetre
        getContentPane().add(newPanel, new GridBagConstraints());
        pack();
		setTitle(Bundle.getText("ijadmin.title"));
		
		//Fenetre non redimensionnable
		this.setResizable(false);
		
	}
	
	/*public void refresh(){
		//AdminCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isAdmin());
		//PManagerCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isPmanager());
		
		
		}*/
	
	/**
	 * Methode de rafraichissement de l'affichage des utilisateurs dans la table
	 * Fonction appelee apres un ajout ou une suppression.
	 */
	public void refreshUsers()
	{
		GridBagConstraints gbc = new GridBagConstraints();
        this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
        
		this.users = UsersParser.getInstance();
		((IJAdminTableModel)this.JT.getModel()).init(this.users.getAllUsers());
		JT.setDefaultEditor(JButton.class, new IJAdminTableCellEditor(this));
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
	 * Fonction qui retourne l'instance de JTable prsente dans cette fenetre.
	 * @return <CODE>JTable</CODE>
	 */
	public JTable getTable()
	{
		return this.JT;
	}
	
	/**
	 *	Fonction qui retourne l'instance de <CODE>UsersParser</CODE>.
	 * @return <CODE>UsersParser</CODE> Le parser XML permettant de faire
	 * des modifications au niveau des utilisateurs (ajout, modif, suppr.).
	 */
	public UsersParser getUsers()
	{
		return this.users;
	}
	
	/**
	 * 
	 * @author Clavreul Mickael
	 * @docRoot Classe representant le modele de donnees de la JTable
	 * utilisee dans IJAdmin.
	 * Ce modele de donnees definit les colonnes necessaires
	 * et recupere les donnees a partir de la liste des utilisateurs
	 * creee par le parser.
	 *
	 */
	class IJAdminTableModel extends AbstractTableModel 
	{
		private static final long serialVersionUID = 1L;

		private String[] columnNames = {Bundle.getText("ijadmin.jtable.user"),
				Bundle.getText("ijadmin.jtable.pmanager"),
				Bundle.getText("ijadmin.jtable.admin"), 
				""};
		
		private Object[][] rowData;
		private ArrayList users;
		
		/**
		 * 
		 * @param users: Liste des utilisateurs du systeme creee par le parser XML
		 * @docRoot Ce constructeur appelle la fonction <i>init</i>
		 */
		public IJAdminTableModel(ArrayList users)
		{
			this.init(users);
		}
		
		/**
		 * 
		 * @param users: Liste des utilisateurs du systeme creee par le parser XML
		 * Fonction qui initialise le modele et remplit la structure de donnees avec
		 * les elements necessaires
		 */
		public void init(ArrayList users)
		{
			this.users = users;
			this.rowData = new Object[this.users.size()][4];
			Iterator iter = this.users.iterator();
			int i=0;
			while(iter.hasNext())
			{
				Object o = iter.next();
				this.rowData[i][0] = ((Users)o).getLogin();
				//System.out.println(this.rowData[i][0]);
				this.rowData[i][1] = new Boolean(((Users)o).isPcreator());
				this.rowData[i][2] = new Boolean(((Users)o).isAdmin());
				this.rowData[i][3] = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
				i++;
			}
		}

		/**
		 * Retourne le nombre de lignes de la structure de donnees
		 */
		public int getRowCount() 
		{
			// TODO Auto-generated method stub
			return this.rowData.length;
		}

		/**
		 * Retourne le nombre de colonnes de la structure de donnees
		 */
		public int getColumnCount() 
		{
			// TODO Auto-generated method stub
			return this.columnNames.length;
		}

		/**
		 * @param: row - ligne a laquelle on recupere la donnee
		 * @param: col - colonne a laquelle on recupere la donnee
		 * Retourne la valeur de la cellule dont les coordonnees sont
		 * passees en parametres.
		 */
		public Object getValueAt(int row, int col) 
		{
			// TODO Auto-generated method stub
			return this.rowData[row][col];
		}
		
		/**
		 * @param: col - colonne a laquelle on recupere la donnee
		 * Retourne le nom de la colonne dont l'indice est passe 
		 * en parametre.
		 * Cette fonction est utilisee par le JScrollPane pour afficher
		 * titres de colonne
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		/*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell.  If we didn't implement this method,
	     * then columns which contain booleans would contain text 
	     * ("true"/"false"), rather than a check box and the last column
	     * would contain a string representation of the JButton defined.
	     */
	    public Class getColumnClass(int c) 
	    {
	        return this.getValueAt(0, c).getClass();
	    }

	    //
	    /**
		 * @param: row - ligne 
		 * @param: col - colonne
		 * Methode utilisee pour definir si une cellule de la JTable 
		 * dont les coordonnees sont passees en parametres est modifiable
		 * ou non
		 */
	    public boolean isCellEditable(int row, int col) 
	    {
	    	if(col==0)
	            return false;
	    	else
	    		return true;
	    }
	    
	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
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
	
	/**
	 * 
	 * @author Clavreul Mickael
	 * @docRoot Classe definissant le mode d'edition du bouton
	 * supprimer present dans la table.
	 * Cette classe implemente ActionListener en vue d'etre capable
	 * de recevoir des evenements et ainsi provoquer l'action correspondante.
	 *
	 */
	class IJAdminTableCellEditor extends AbstractCellEditor
	                         implements TableCellEditor,
				            ActionListener {
		
		private static final long serialVersionUID = 1L;
		protected static final String EDIT = "edit";
		
	    private JButton JB;
	    private IJAdmin IJA;
	    
	    
	    /**
		 * 
		 * @param IJA: Fenetre principale contenant la JTable dont cet editeur
		 * etend le comportement.
		 * @docRoot Ce constructeur initialise un bouton qui va servir pour
		 * recuperer les actions de l'utilisateur.
		 */
	    public IJAdminTableCellEditor(IJAdmin IJA) 
	    {

	    	JB = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
	    	JB.setActionCommand(EDIT);
	    	JB.addActionListener(this);
	    	JB.setBorderPainted(false);
	        this.IJA = IJA;
	    }

	    /**
	     * Handles events from the delete button inside the JTable
	     */
	    public void actionPerformed(ActionEvent e) 
	    {
	    	if (EDIT.equals(e.getActionCommand())) 
	        {
	    		this.deleteUser();
	        }
	    }

	    /**
	     * Don't need cell value in this processing.
	     */
	    public Object getCellEditorValue() {
	        return null;
	    }

	    /**
	     * Implement the one method defined by TableCellEditor.
	     */
	    public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) 
	    {
	        return JB;
	    }
	    
	    /**
	     * Called when user clicks on delete button.
	     * Displays an confirmation dialog to confirm user's choice
	     * Finally delete user selected from the system.
	     */
	    public void deleteUser()
	    {
	    	int rowToDelete;
	    	int out = JOptionPane.showOptionDialog(this.IJA.getTable(),new String("Etes-vous sur de vouloir supprimer cet utilisateur ?"),new String("Suppression d'un utilisateur"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
			if(out == JOptionPane.YES_OPTION)
			{
				rowToDelete =this.IJA.getTable().getSelectedRow();
				this.IJA.getUsers().removeUser(((String)this.IJA.getTable().getModel().getValueAt(rowToDelete,0)));
				IJAdmin.getIJA().refreshUsers();
			}
	    }
	}
	
	/**
	 * 
	 * @author Clavreul Mickael
	 * @docRoot Classe de rendu permettant d'afficher des boutons
	 * dans la JTable.
	 * Cette classe etend JButton et implemente TableCellRenderer
	 */
	class IJAdminTableCellRenderer extends JButton implements TableCellRenderer
	{

		private static final long serialVersionUID = 1L;
		
		/**
		 *
		 * @docRoot Ce constructeur initialise les instances de cet objet opaques.
		 */
		public IJAdminTableCellRenderer()
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
}