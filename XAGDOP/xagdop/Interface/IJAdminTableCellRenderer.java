package xagdop.Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class IJAdminTableCellRenderer extends JButton implements TableCellRenderer, ActionListener 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IJAdminTableCellRenderer()
	{
		this.setOpaque(true);
	}
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
		
		return this;
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		System.out.println("delete user");
	}

}
