package xagdop.Interface.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Interface.XAGDOP;
import xagdop.Model.DirectoryModel;
import xagdop.Svn.SvnUpdate;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;
public class IJPToggleTable extends JScrollPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IJPToggleTable(JTable jt)
	{
		super(jt);
		jt.setDefaultRenderer(JButton.class, new VersionTableCellRenderer());
		jt.setDefaultEditor(JButton.class, new VersionTableCellEditor());
		jt.getColumn("Version").setMaxWidth(50);
		jt.getColumn("Auteur").setMaxWidth(80);
		jt.getColumn("Date").setMaxWidth(80);
		jt.getColumn("Récuperer").setMaxWidth(70);
		this.setBackground(Color.WHITE);
		
	}
	public class VersionTableCellRenderer extends DefaultTableCellRenderer
	{
		
		private static final long serialVersionUID = 1L;
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean arg2, boolean arg3, int arg4, int arg5) 
		{
			return (JButton)value;
		}
	}
	
	public class VersionTableCellEditor extends AbstractCellEditor implements TableCellEditor,	ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JButton button;
				
		public VersionTableCellEditor() {
			button = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/update11.jpg")));
			button.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
//			int out = JOptionPane.showOptionDialog(null,new String("Etes-vous sur de vouloir recuperer cette version ?"),"Recuperation d'une version antérieure",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
			int out = JOptionPane.showOptionDialog(null,new String(Bundle.getText("ijptoogletable.conf.msg")),Bundle.getText("ijptoogletable.conf.title"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
			if(out == JOptionPane.YES_OPTION){
				
				try {
					SvnUpdate svnu = new SvnUpdate();
					JTable table = ((JTable)((JButton)e.getSource()).getParent());
					svnu.update(((DirectoryModel)table.getModel()).getRevsion(table.getSelectedRow()),(File)XAGDOP.getInstance().getTree().getSelectedNode().getUserObject());
				} catch (SVNException e1) {
					ErrorManager.getInstance().display();
					//e1.printStackTrace();
				}
			}
				
		}
		
//		Implement the one CellEditor method that AbstractCellEditor doesn't.
		public Object getCellEditorValue() {
			return button;
		}
		
//		Implement the one method defined by TableCellEditor.
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			return button;
		}
	}
	
}