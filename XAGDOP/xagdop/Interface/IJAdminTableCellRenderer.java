package xagdop.Interface;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class IJAdminTableCellRenderer extends JButton implements TableCellRenderer
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
		this.setSize(new Dimension(this.getIcon().getIconHeight(),this.getIcon().getIconWidth()));
		return this;
	}
}
