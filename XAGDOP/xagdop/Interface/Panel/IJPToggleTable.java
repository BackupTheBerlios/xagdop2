package xagdop.Interface.Panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import xagdop.Interface.XAGDOP;
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
		//jt.setDefaultEditor(JButton.class, new VersionTableCellEditor());
		jt.getColumn("Version").setMaxWidth(50);
		jt.getColumn("Auteur").setMaxWidth(80);
		jt.getColumn("Date").setMaxWidth(80);
		this.setBackground(Color.WHITE);
		
	}
	public class VersionTableCellRenderer extends DefaultTableCellRenderer
	{
		
		private static final long serialVersionUID = 1L;
		private JButton jButton;
		public VersionTableCellRenderer()
		{
			jButton = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/delete.jpg")));
			jButton.setToolTipText(Bundle.getText("main.menu.project.getVersion"));
			jButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) 
				{
					//int out = JOptionPane.showOptionDialog(null,new String("Etes-vous sur de vouloir recuperer cette version ?"),"Recuperation d'une version antérieure",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
					int out = JOptionPane.showOptionDialog(null,new String(Bundle.getText("ijptoogletable.conf.msg")),Bundle.getText("ijptoogletable.conf.title"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
					if(out == JOptionPane.YES_OPTION);
				}
			});
			jButton.setBorderPainted(false);
			jButton.setOpaque(true);
		}
		
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean arg2, boolean arg3, int arg4, int arg5) 
		{
			System.out.println("blabla");
			return jButton;
		}
	}
	
}