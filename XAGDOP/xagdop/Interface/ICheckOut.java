package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.AbstractTableModel;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Parser.ProjectsParser;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

public class ICheckOut extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ICheckOut ICO = null;
	private JLabel JLBTitle;
	private JTable JTProjets;
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel JPMain = new JPanel();
	private Box BActions = new Box(BoxLayout.Y_AXIS);
	private JPanel JPTable = new JPanel();
	private JButton JBSelectAll;
	private JButton JBDeselectAll;
	private JButton JBOk;
	private JButton JBCancel;
	
	
	public ICheckOut()
	{
		ICO = this;
		this.setTitle(Bundle.getText("icheckout.title"));
		//this.setIconImage(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")).getImage());
		JLBTitle = new JLabel(Bundle.getText("icheckout.label"));
		JBSelectAll = new JButton(Bundle.getText("icheckout.selectall"));
		JBDeselectAll = new JButton(Bundle.getText("icheckout.deselectall"));
		JBOk = new JButton(Bundle.getText("button.ok"));
		JBCancel = new JButton(Bundle.getText("button.cancel"));
		this.init();
	}
	
	public void init()
	{
		this.setLayout(gbl);
		this.JPMain.setLayout(gbl);
		this.JPTable.setLayout(gbl);
		
		this.initTable();
		this.initButtons();
				
		//this.donnerContrainte(gbc,0,0,1,1,100,20,GridBagConstraints.BOTH);
		this.BActions.add(this.JBSelectAll);
		this.BActions.add(Box.createRigidArea(new Dimension(0,10)));
		//this.donnerContrainte(gbc,0,2,1,1,100,20,GridBagConstraints.BOTH);
		this.BActions.add(this.JBDeselectAll);
		this.BActions.add(Box.createRigidArea(new Dimension(0,10)));
		//this.donnerContrainte(gbc,0,4,1,1,100,20,GridBagConstraints.BOTH);
		this.BActions.add(this.JBOk);
		this.BActions.add(Box.createRigidArea(new Dimension(0,10)));
		//this.donnerContrainte(gbc,0,6,1,1,100,20,GridBagConstraints.BOTH);
		this.BActions.add(this.JBCancel);
		
		

		this.donnerContrainte(gbc,0,0,1,1,100,100);
		this.JPTable.add(this.JLBTitle,gbc);
		this.donnerContrainte(gbc,0,1,1,1,100,100);
		this.JPTable.add(new JScrollPane(this.JTProjets),gbc);
		
		this.donnerContrainte(gbc,0,0,1,1,100,100);
		gbc.anchor = GridBagConstraints.WEST;
		this.JPMain.add(this.JPTable,gbc);
		this.donnerContrainte(gbc,1,0,1,1,100,100);
		gbc.anchor = GridBagConstraints.EAST;
		this.JPMain.add(this.BActions);
		
		this.JPTable.setSize(new Dimension(350,200));
		this.JPMain.setSize(new Dimension(350,250));
		this.getContentPane().add(this.JPMain,gbc);
		//this.pack();
		this.setSize(new Dimension(480,250));
		this.setVisible(true);
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
    
    public void initTable()
    {
//    	Recuperation des projets et creation de la table
		ArrayList data = null;
		try {
			data = ProjectsParser.getInstance().getProjects(XAGDOP.getInstance().getUser().getLogin());
		} catch (XPathExpressionException e) {
			ErrorManager.getInstance().display();
		} catch (SVNException e) {
			ErrorManager.getInstance().display();
		} catch (IOException e) {
			ErrorManager.getInstance().display();
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		this.JTProjets = new JTable(new ProjectTableModel(data));
		
		//Suppression de l'affichage de la grille et de l'entete
		this.JTProjets.setShowGrid(false);
		this.JTProjets.setTableHeader(null);
		
		//JTable non redimensionnable
		this.JTProjets.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.JTProjets.setSize(new Dimension(300,200));
        
        //Colonnes non redimensionnables
		this.JTProjets.getColumnModel().getColumn(0).setResizable(false);
		this.JTProjets.getColumnModel().getColumn(1).setResizable(false);
        
        //Configuration des tailles des colonnes
		this.JTProjets.getColumnModel().getColumn(0).setMaxWidth(20);
		this.JTProjets.getColumnModel().getColumn(1).setMinWidth(105);
		
		//Changement de la couleur de fond
		this.JTProjets.setBackground(this.JPMain.getBackground());
		
		JTProjets.setPreferredScrollableViewportSize(JTProjets.getSize());
    }
    
    public void initButtons()
    {
    	this.JBSelectAll.setMaximumSize(this.JBDeselectAll.getMaximumSize());
    	this.JBSelectAll.addActionListener(new ActionListener()
    			{

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						((ProjectTableModel)ICO.getJTProjets().getModel()).selectAll();
						ICO.getJTProjets().updateUI();
						ICO.getJTProjets().setBackground(new ColorUIResource(238,238,238));
						//a modifier parce ke c pas bien cod? qd mm
					}
    		
    			});
    	this.JBDeselectAll.addActionListener(new ActionListener()
    			{

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						((ProjectTableModel)ICO.getJTProjets().getModel()).deselectAll();
						ICO.getJTProjets().updateUI();
						ICO.getJTProjets().setBackground(new ColorUIResource(238,238,238));
					}
    		
    			});
		this.JBOk.setMaximumSize(this.JBDeselectAll.getMaximumSize());
		this.JBOk.addActionListener(new ActionListener()
    			{

					public void actionPerformed(ActionEvent a) {
						// TODO Auto-generated method stub
						SvnUpdate svnU;
						ArrayList projets = ((ProjectTableModel)ICO.getJTProjets().getModel()).getProjectsToCheckOut();
						if(projets.size()!= 0)
						{
							try
							{
								svnU = new SvnUpdate();
								svnU.checkOut(projets);
							}catch(SVNException s){ErrorManager.getInstance().display();} 
							catch (XPathExpressionException e) {
								// TODO Auto-generated catch block
								ErrorManager.getInstance().display();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								ErrorManager.getInstance().display();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								ErrorManager.getInstance().display();
							}
						}
						ICO.setVisible(false);
						ICO.dispose();
					}
					
    			});
		this.JBCancel.setMaximumSize(this.JBDeselectAll.getMaximumSize());
		this.JBCancel.addActionListener(new ActionListener()
    			{

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						ICO.setVisible(false);
						ICO.dispose();
					}
    		
    			});
    }
    
    /**
	 * @return Returns the singleton.
	 */
	public static ICheckOut getIJCO() {
		if (ICO==null){
			ICO = new ICheckOut(); 
		}
		
		return ICO;
	}
	
	public JTable getJTProjets()
	{
		return this.JTProjets;
	}
    
    class ProjectTableModel extends AbstractTableModel
    {
    	
		private static final long serialVersionUID = 1L;
		
		private Object[] columnName = {"",""};
    	private Object[][] rowData;
    	
    	/**
		 * 
		 * @param data: Liste des projets du systeme dont a acces l'utilisateur courant
		 * @docRoot Ce constructeur appelle la fonction <i>init</i>
		 */
    	public ProjectTableModel(ArrayList data)
    	{    		
    		this.init(data);
    	}
    	
    	/**
		 * 
		 * @param data: Liste des projets du systeme dont a acces l'utilisateur courant
		 * Fonction qui initialise le modele et remplit la structure de donnees avec
		 * les elements necessaires
		 */
    	public void init(ArrayList data)
    	{
    		int index = 0;
    		this.rowData = new Object[data.size()][2];
    		Iterator i = data.iterator();
    		
    		while (i.hasNext())
    		{
    			String str = ((String)i.next());
    			this.rowData[index][0] = new Boolean(true);
    			this.rowData[index][1] = str;
    			index++;
    		}
    		
    	}
    	
    	/**
		 * @param: row - ligne 
		 * @param: col - colonne
		 * Methode utilisee pour definir si une cellule de la JTable 
		 * dont les coordonnees sont passees en parametres est modifiable
		 * ou non
		 */
	    public boolean isCellEditable(int row, int col) 
	    {
	    	if(col==1)
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

	    /**
		 * Retourne le nombre de lignes de la structure de donnees
		 */
		public int getRowCount() {
			// TODO Auto-generated method stub
			return this.rowData.length;
		}

		/**
		 * Retourne le nombre de colonnes de la structure de donnees
		 */
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return this.columnName.length;
		}
		
		/**
		 * @param: row - ligne a laquelle on recupere la donnee
		 * @param: col - colonne a laquelle on recupere la donnee
		 * Retourne la valeur de la cellule dont les coordonnees sont
		 * passees en parametres.
		 */
		public Object getValueAt(int col, int row) {
			// TODO Auto-generated method stub
			return this.rowData[col][row];
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
	    
	    public ArrayList getProjectsToCheckOut()
	    {
	    	ArrayList projets = new ArrayList();
	    	for(int i = 0; i<this.getRowCount();i++)
	    	{
	    		if (((Boolean)this.rowData[i][0]).booleanValue() == true)
	    		{
	    			projets.add(new String(((String)this.rowData[i][1])));
	    		}
	    	}
	    	return projets;
	    }
	    
	    public void selectAll()
	    {
	    	for(int i = 0; i<this.getRowCount();i++)
	    	{
	    		if (((Boolean)this.rowData[i][0]).booleanValue() == false)
	    		{
	    			this.rowData[i][0] = new Boolean(true);
	    		}
	    	}
	    }
	    
	    public void deselectAll()
	    {
	    	for(int i = 0; i<this.getRowCount();i++)
	    	{
	    		if (((Boolean)this.rowData[i][0]).booleanValue() == true)
	    		{
	    			this.rowData[i][0] = new Boolean(false);
	    		}
	    	}
	    }
    }
}
