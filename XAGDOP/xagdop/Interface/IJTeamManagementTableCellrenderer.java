package xagdop.Interface;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author JULLIEN Antoine
 * @docRoot Classe de rendu permettant d'afficher des boutons
 * dans la JTable.
 * Cette classe etend JButton et implemente TableCellRenderer
 */
public class IJTeamManagementTableCellrenderer extends JButton implements TableCellRenderer
{

	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * @docRoot Ce constructeur initialise les instances de cet objet opaques.
	 */
	public IJTeamManagementTableCellrenderer()
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


