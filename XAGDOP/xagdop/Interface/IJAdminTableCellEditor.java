package xagdop.Interface;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import xagdop.Interface.XAGDOP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IJAdminTableCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener
{
	private JButton jb;
	
	public IJAdminTableCellEditor()
	{
		this.jb = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
	}
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCellEditable(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean shouldSelectCell(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return false;
	}

	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		
	}

	public void addCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void removeCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
