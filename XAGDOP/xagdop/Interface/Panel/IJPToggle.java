package xagdop.Interface.Panel;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import xagdop.Interface.XAGDOP;


public class IJPToggle extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon IIlogo = null;
	private JLabel JLText = null;
	
	public IJPToggle()
	{
		this.setLayout(new FlowLayout());
		IIlogo = new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP9.jpg"));
		JLText = new JLabel(IIlogo);
		this.setBackground(Color.WHITE);
		this.add(JLText);
	}
}