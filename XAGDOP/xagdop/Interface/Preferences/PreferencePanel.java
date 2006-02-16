package xagdop.Interface.Preferences;

import javax.swing.JPanel;

public class PreferencePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel getPanelDescription(String title){
   		JPanel panelNorth = new PanelDescription(title);
   		return panelNorth;
    }
}
