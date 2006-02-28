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
		super(jt);//this.setLayout(new ScrollPaneLayout());
		this.setBackground(Color.WHITE);
		
	}
	
}