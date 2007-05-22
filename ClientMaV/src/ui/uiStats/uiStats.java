package ui.uiStats;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;

public class uiStats extends JFrame {

	private JPanel jPanel = null;

	/**
	 * This method initializes 
	 * 
	 */
	public uiStats() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(486, 297));
        this.setContentPane(getJPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setName("stats");
        this.setTitle("Statistiques");
        this.setVisible(true);
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
		}
		return jPanel;
	}

	


}  //  @jve:decl-index=0:visual-constraint="10,10"
