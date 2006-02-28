package xagdop.Interface.Panel;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTable;
public class IJPToggleTable extends JScrollPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IJPToggleTable(JTable jt)
	{
		super(jt);
		jt.getColumn("Version").setMaxWidth(50);
		jt.getColumn("Auteur").setMaxWidth(80);
		jt.getColumn("Date").setMaxWidth(80);
		jt.getColumn("Commentaires").setPreferredWidth(120);
		this.setBackground(Color.WHITE);
		
	}
	
}