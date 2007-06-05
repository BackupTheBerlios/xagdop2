/**
 * 
 */
package ui.uiManagement;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.util.dtpicker.DTPicker;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author nephos
 *
 */
public class MandatManagement extends JFrame{

	private JPanel jContentPane = null;
	private JLabel lTitre = null;
	private JLabel lDateDebut = null;
	private JLabel lDateFin = null;
	private JTextField jtTitre = null;
	private DTPicker dtDebut = null;
	private DTPicker dtFin = null;
	private JPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="10,302"
	private JButton jButton = null;
	private JButton jButton1 = null;
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	public  MandatManagement() {
		super();
		initialize();

	}
	
	private void initialize() {
		setSize(new Dimension(402, 285));
		setTitle("Mandat");
		setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 3;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints21.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.weighty = 1.0;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.weighty = 1.0;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.anchor = GridBagConstraints.EAST;
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.EAST;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.gridy = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getTitre(), gridBagConstraints);
			jContentPane.add(getDateFin(), gridBagConstraints1);
			jContentPane.add(getDateDebut(), gridBagConstraints2);
			jContentPane.add(getJtTitre(), gridBagConstraints3);
			jContentPane.add(getDTFin(), gridBagConstraints4);
			jContentPane.add(getDTDebut(), gridBagConstraints5);
			jContentPane.add(getJPanel(), gridBagConstraints21);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getTitre() {
		if (lTitre == null) {
			lTitre = new JLabel();
			lTitre.setText("Titre : ");
		}
		return lTitre;
	}
	
	private JLabel getDateDebut() {
		if (lDateDebut == null) {
			lDateDebut = new JLabel();
			lDateDebut.setText("Date de début : ");
		}
		return lDateDebut;
	}
	
	private JLabel getDateFin() {
		if (lDateFin == null) {
			lDateFin = new JLabel();
			lDateFin.setText("Date de Fin : ");
		}
		return lDateFin;
	}
	
	private JTextField getJtTitre() {
		if (jtTitre == null) {
			jtTitre = new JTextField();
		}
		return jtTitre;
	}
	
	private DTPicker getDTDebut(){
		if (dtDebut == null) {
			dtDebut = new DTPicker();
		}
		return dtDebut;
	}
	
	private DTPicker getDTFin(){
		if (dtFin == null) {
			dtFin = new DTPicker();
		}
		return dtFin;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 12, 0, 1);
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.ipadx = 0;
			gridBagConstraints7.ipady = 0;
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 11);
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.ipadx = 0;
			gridBagConstraints6.ipady = 0;
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.gridx = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJButton(), gridBagConstraints6);
			jPanel.add(getJButton1(), gridBagConstraints7);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton("Valider");
			//jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton("Annuler");
			jButton1.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
				
			});
			//jButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return jButton1;
	}

}