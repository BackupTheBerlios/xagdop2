package uiManagement;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CandidatManagement extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8598685457321999040L;
	private JPanel jContentPane = null;
	private Button bAdd = null;
	private List listCandidat = null;  //  @jve:decl-index=0:visual-constraint="613,10"
	private Button bDel = null;
	private Button bQuit = null;
	private JPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="616,13"
	private JLabel lId = null;
	private JLabel lNom = null;
	private JLabel id = null;
	private JLabel lPrenom = null;
	private JLabel lAge = null;
	private JLabel lProfession = null;
	private List listMandats = null;
	private JTextField jtNom = null;
	private JTextField jtPrenom = null;
	private JTextField jtAge = null;
	private JTextField jtProfession = null;
	private JLabel lMandats = null;
	private JLabel lAns = null;
	private JButton bAddMandat = null;
	private JButton bDelMandat = null;
	private JButton bEditMandat = null;
	private Button button = null;
	/**
	 * This method initializes 
	 * 
	 */
	public CandidatManagement() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(620, 580));
        this.setContentPane(getJContentPane());
        this.setTitle("Edition des candidats");
		setVisible(true);
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints7.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 0.0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridheight = 1;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.gridy = 1;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getButton(), gridBagConstraints);
			jContentPane.add(getList(), gridBagConstraints2);
			jContentPane.add(getButton1(), gridBagConstraints3);
			jContentPane.add(getButton2(), gridBagConstraints4);
			jContentPane.add(getJPanel(), gridBagConstraints7);
		}
		return jContentPane;
	}

	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton() {
		if (bAdd == null) {
			bAdd = new Button();
			bAdd.setLabel("Ajouter");
		}
		return bAdd;
	}

	/**
	 * This method initializes list	
	 * 	
	 * @return java.awt.List	
	 */
	private List getList() {
		if (listCandidat == null) {
			listCandidat = new List();
		}
		return listCandidat;
	}

	/**
	 * This method initializes button1	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton1() {
		if (bDel == null) {
			bDel = new Button();
			bDel.setLabel("Supprimer");
		}
		return bDel;
	}

	/**
	 * This method initializes button2	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton2() {
		if (bQuit == null) {
			bQuit = new Button();
			bQuit.setLabel("Quitter");
		}
		return bQuit;
	}

	/**
	 * This method initializes jPanel
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridx = 3;
			gridBagConstraints22.insets = new Insets(30, 10, 10, 10);
			gridBagConstraints22.gridy = 11;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 3;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints20.anchor = GridBagConstraints.CENTER;
			gridBagConstraints20.gridy = 9;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.anchor = GridBagConstraints.CENTER;
			gridBagConstraints19.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints19.weightx = 1.0;
			gridBagConstraints19.gridwidth = 2;
			gridBagConstraints19.gridy = 9;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.anchor = GridBagConstraints.CENTER;
			gridBagConstraints18.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.gridy = 9;
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 2;
			gridBagConstraints17.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints17.anchor = GridBagConstraints.WEST;
			gridBagConstraints17.gridy = 3;
			lAns = new JLabel();
			lAns.setText("ans");
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.insets = new Insets(15, 5, 5, 5);
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridwidth = 1;
			gridBagConstraints16.gridy = 6;
			lMandats = new JLabel();
			lMandats.setText("Mandats éffectués :");
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.BOTH;
			gridBagConstraints15.gridy = 5;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints15.gridwidth = 3;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.gridy = 3;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints14.gridwidth = 1;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints13.gridwidth = 4;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints12.gridwidth = 3;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 7;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.weighty = 1.0;
			gridBagConstraints11.gridheight = 1;
			gridBagConstraints11.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints11.gridwidth = 5;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.gridwidth = 1;
			gridBagConstraints10.gridy = 5;
			lProfession = new JLabel();
			lProfession.setText("Profession : ");
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.gridwidth = 1;
			gridBagConstraints9.gridy = 3;
			lAge = new JLabel();
			lAge.setText("Age : ");
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.anchor = GridBagConstraints.EAST;
			gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints8.gridwidth = 1;
			gridBagConstraints8.gridy = 2;
			lPrenom = new JLabel();
			lPrenom.setText("Prenom : ");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 0;
			id = new JLabel();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.gridwidth = 1;
			gridBagConstraints5.gridy = 1;
			lNom = new JLabel();
			lNom.setText("Nom : ");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.gridwidth = 1;
			gridBagConstraints1.gridy = 0;
			lId = new JLabel();
			lId.setText("Id candidat : ");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setSize(new Dimension(209, 169));
			jPanel.add(lId, gridBagConstraints1);
			jPanel.add(lNom, gridBagConstraints5);
			jPanel.add(id, gridBagConstraints6);
			jPanel.add(lPrenom, gridBagConstraints8);
			jPanel.add(lAge, gridBagConstraints9);
			jPanel.add(lProfession, gridBagConstraints10);
			jPanel.add(getList1(), gridBagConstraints11);
			jPanel.add(getJTextField(), gridBagConstraints12);
			jPanel.add(getJTextField1(), gridBagConstraints13);
			jPanel.add(getJTextField2(), gridBagConstraints14);
			jPanel.add(getJTextField3(), gridBagConstraints15);
			jPanel.add(lMandats, gridBagConstraints16);
			jPanel.add(lAns, gridBagConstraints17);
			jPanel.add(getJButton(), gridBagConstraints18);
			jPanel.add(getJButton1(), gridBagConstraints19);
			jPanel.add(getJButton2(), gridBagConstraints20);
			jPanel.add(getButton3(), gridBagConstraints22);
		}
		return jPanel;
	}

	/**
	 * This method initializes list1	
	 * 	
	 * @return java.awt.List	
	 */
	private List getList1() {
		if (listMandats == null) {
			listMandats = new List();
		}
		return listMandats;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jtNom == null) {
			jtNom = new JTextField();
			jtNom.setSize(120, 10);
		}
		return jtNom;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jtPrenom == null) {
			jtPrenom = new JTextField();
		}
		return jtPrenom;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField2() {
		if (jtAge == null) {
			jtAge = new JTextField();
			jtAge.setPreferredSize(new Dimension(60, 19));
		}
		return jtAge;
	}

	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField3() {
		if (jtProfession == null) {
			jtProfession = new JTextField();
		}
		return jtProfession;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (bAddMandat == null) {
			bAddMandat = new JButton("Ajouter Mandat");
			//bAddMandat.setLabel();
		}
		return bAddMandat;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (bDelMandat == null) {
			bDelMandat = new JButton("Supprimer Mandat");
		}
		return bDelMandat;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (bEditMandat == null) {
			bEditMandat = new JButton("Editer Mandat");
		}
		return bEditMandat;
	}

	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton3() {
		if (button == null) {
			button = new Button();
			button.setLabel("Enregistrer Candidat");
		}
		return button;
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
