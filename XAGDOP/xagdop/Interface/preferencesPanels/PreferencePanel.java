package xagdop.Interface.preferencesPanels;

import javax.swing.JPanel;

public class PreferencePanel extends JPanel {
	
	protected JPanel getPanelDescription(String title){
   		JPanel panelNorth = new PanelDescription(title);
   		return panelNorth;
    }
}
