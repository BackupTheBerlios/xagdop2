package xagdop.Interface.Panel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class IJPToggleTable extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IJPToggleTable(JTable jt)
	{
		this.setLayout(new FlowLayout());
		this.setBackground(Color.WHITE);
		this.add(new JScrollPane(jt));
	}
	
}