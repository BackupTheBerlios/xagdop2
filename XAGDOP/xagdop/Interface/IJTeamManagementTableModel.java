package xagdop.Interface;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import xagdop.Parser.ProjectsParser;
import xagdop.Model.Project;
import xagdop.Model.User;


public class IJTeamManagementTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomColonne[] = {"Users","Architecte","Analyste","Redacteur","PManager","Desaffecter"};
	private Object[][] rowData;
	private ArrayList users;
	private ProjectsParser projetParser;
	private Project projet;
	private String nP;
	
	public IJTeamManagementTableModel(ArrayList usersParser, String nomProjet) {
	    this.nP = nomProjet;
		
		projetParser = ProjectsParser.getInstance(); 		
		this.nP = nomProjet;				
		projet = projetParser.buildProject(nomProjet);	
		this.init();
			
		
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
	
	public void init() {
		ArrayList users = this.projet.getUsersLogin();
		
		this.rowData = new Object[users.size()][this.getColumnCount()];
		Iterator it = users.iterator();
		int i = 0;
		while (it.hasNext())
		{
			String o = ((String)it.next());
			System.out.println(o);
			rowData[i][0] =	o;
			rowData[i][1] = new Boolean(this.projet.isArchitecte(o));
			rowData[i][2] = new Boolean(this.projet.isAnalyst(o));
			rowData[i][3] = new Boolean(this.projet.isRedacteur(o));
			rowData[i][4] = new Boolean(this.projet.isManager(o));
			rowData[i][5] = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
			i++;	
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
