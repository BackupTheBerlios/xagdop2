package xagdop.Interface;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import xagdop.Parser.ProjectsParser;
import xagdop.Model.Projects;


public class IJTeamManagementTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomColonne[] = {"Users","Redacteur","Analyste","PManager","Desaffecter"};
	private Object[][] rowData;
	private ArrayList users;
	private ProjectsParser projetParser;
	private Projects projet;
	
	public IJTeamManagementTableModel(ArrayList usersParser, String nomProjet) {
	
		this.users = usersParser;
		projetParser = new ProjectsParser(); 
		
		nomProjet = this.projet.getProjectName();
		projet = projetParser.getAllUsers(nomProjet);
		
		//ArrayList usersID = projet.getUsersId();
		
		
		this.rowData = new Object[users.size()][5];
		Iterator it = users.iterator();
	//	int i = 0;
		while (it.hasNext())
		{
			
		//	rowData[i][0] =	username.getAttribute(usersID,"login");	// users
		//	rowData[i][1] = 							// Redacteur
		//	rowData[i][2] = 							// Analyste
		//	rowData[i][3] =  							// Pmanager
		//	rowData[i][4] =								// Bouton desaffecter  
			
		}
		
		
		
		
		
		
		
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
	
	
}
