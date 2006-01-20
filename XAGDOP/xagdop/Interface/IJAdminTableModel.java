package xagdop.Interface;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import xagdop.Model.Users;

public class IJAdminTableModel extends AbstractTableModel 
{
	private String[] columnNames = {"Nom Utilisateur",
            "Chef de Projet",
            "Administrateur", "Supprimer"};
	
	private Object[][] rowData;
	private ArrayList users;
	
	public IJAdminTableModel(ArrayList users)
	{
		/*this.rowData[0][0]=new String("");
		this.rowData[0][1]=new Boolean(false);
		this.rowData[0][2]=new Boolean(false);
		this.rowData[0][3]=new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		this.rowData[1][0]=new String("");
		this.rowData[1][1]=new Boolean(false);
		this.rowData[1][2]=new Boolean(false);
		this.rowData[1][3]=new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		this.rowData[2][0]=new String("");
		this.rowData[2][1]=new Boolean(false);
		this.rowData[2][2]=new Boolean(false);
		this.rowData[2][3]=new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		this.rowData[3][0]=new String("");
		this.rowData[3][1]=new Boolean(false);
		this.rowData[3][2]=new Boolean(false);
		this.rowData[3][3]=new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));*/
		this.init(users);
	
	}
	
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
			System.out.println("login: "+this.rowData[i][0]);
			this.rowData[i][1] = new Boolean(((Users)o).isPmanager());
			this.rowData[i][2] = new Boolean(((Users)o).isAdmin());
			this.rowData[i][3] = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
			i++;
		}
	}
	
	/*private String[] columnNames = {"Nom Utilisateur",
            "Chef de Projet",
            "Administrateur"};
	
	private Object[][] rowData = 
			{{new String(""), "", new Boolean(false)},
			{new String(""), "", new Boolean(false)},
			{new String(""), "", new Boolean(false)},
			{new String(""), "", new Boolean(false)},
			{new String(""), "", new Boolean(false)},};*/

	public int getRowCount() 
	{
		// TODO Auto-generated method stub
		return this.rowData.length;
	}

	public int getColumnCount() 
	{
		// TODO Auto-generated method stub
		return this.columnNames.length;
	}

	public Object getValueAt(int row, int col) 
	{
		// TODO Auto-generated method stub
		return this.rowData[row][col];
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) 
    {
        return this.getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) 
    {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col > 2) 
        {
            return false;
        } 
        else 
        {
            return true;
        }
    }
    
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) 
    {
        this.rowData[row][col] = value;
        this.fireTableCellUpdated(row, col);
    }
}
