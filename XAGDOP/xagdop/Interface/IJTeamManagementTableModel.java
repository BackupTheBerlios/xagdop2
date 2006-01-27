package xagdop.Interface;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import xagdop.Parser.ProjectsParser;
import xagdop.Model.Projects;
import xagdop.Model.Users;


public class IJTeamManagementTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomColonne[] = {"Users","Architecte","Analyste","Redacteur","PManager","Desaffecter"};
	private Object[][] rowData;
	private ArrayList users;
	private ProjectsParser projetParser;
	private Projects projet;
	private String nP;
	
	public IJTeamManagementTableModel(ArrayList usersParser, String nomProjet) {
	    this.nP = nomProjet;
		this.users = usersParser;
		projetParser = ProjectsParser.getInstance(); 
		
		this.nP = nomProjet;
		
		//nomProjet = this.projet.getProjectName();
		projet = projetParser.buildProject(nomProjet);
	
		this.init(users);
			
		
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
	
	public void init(ArrayList ff) {
		this.users = ff;
		this.rowData = new Object[users.size()][this.getColumnCount()];
		Iterator it = users.iterator();
		int i = 0;
		while (it.hasNext())
		{
			Object o = it.next();
			rowData[i][0] =	((Users)o).getLogin();
			rowData[i][1] = new Boolean(((Users)o).isArchitect(this.nP));
			rowData[i][2] = new Boolean(((Users)o).isAnalyst(this.nP));
			rowData[i][3] = new Boolean(((Users)o).isRedactor(this.nP));
			rowData[i][4] = new Boolean(((Users)o).isPManager(this.nP));
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
