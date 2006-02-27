package xagdop.Interface.Help;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import xagdop.Interface.SvnInterface.IProject;
import xagdop.ressources.Bundle;

//import com.sun.java.util.jar.pack.Instruction.Switch;

public class MiddlePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static MiddlePanel mp= null;
	protected JPanel JMiddlePanel;
	protected JLabel JDesc;
	
	public MiddlePanel(int value) {
		String title=null;
		String desc=null;
		
		switch(value)
		{
			case 11: 
				System.out.println("11");
				title=Bundle.getText("ihelp.projects.create");
				desc=Bundle.getText("ihelp.desc.addproject");
				init(title,desc);
				break;
			default: break;
		}
		init(title,desc);
	}

	private void init(String title, String desc) {
		JMiddlePanel = new JPanel();
		JMiddlePanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		
		//Creation d un cadre dans la fenetre
		Border cadre = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder(cadre,title);
        
		JDesc = new JLabel(desc);
		JMiddlePanel.add(JDesc);
		this.add(JMiddlePanel);
		
	}

}
