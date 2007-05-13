package ui.uiManagement;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CandidatManagement extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8598685457321999040L;
	private JPanel jContentPane = null;
	private JPanel jPanelAge = null;
	private Button bAdd = null;
	private List listCandidat = null;  //  @jve:decl-index=0:visual-constraint="613,10"
	private Button bDel = null;
	private Button bQuit = null;
	private JPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="443,13"
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
	private JTextField jTextField = null;
	private JScrollPane jScrollPane_List = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	private JScrollPane jScrollPaneMandat = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        this.setSize(new Dimension(660, 490));
        this.setPreferredSize(new Dimension(660, 490));
        this.setContentPane(getJContentPane());
        this.setTitle("Edition des candidats");
        this.setMinimumSize(new Dimension(320,240));
		setVisible(true);
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints17.gridy = 0;
			gridBagConstraints17.weightx = 1.0;
			gridBagConstraints17.gridwidth = 2;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.insets = new Insets(5, 5, 5, 5);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints7.gridheight = 2;
			gridBagConstraints7.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.anchor = GridBagConstraints.EAST;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.gridy = 2;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 1;
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
			gridBagConstraints.gridy = 2;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.setMinimumSize(new Dimension(200,320));
			jContentPane.add(getButton(), gridBagConstraints);
			jContentPane.add(getList(), gridBagConstraints2);
			jContentPane.add(getButton1(), gridBagConstraints3);
			jContentPane.add(getButton2(), gridBagConstraints4);
			jContentPane.add(getJPanel(), gridBagConstraints7);
			jContentPane.add(getJTextField4(), gridBagConstraints17);
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
	private JScrollPane getList() {
		if (listCandidat == null) {
			listCandidat = new List();
			listCandidat.setPreferredSize(new Dimension(120,230));
			jScrollPane_List.getViewport().add(listCandidat);
		}
		return jScrollPane_List;
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

	
	private JPanel getJPanelAge() {
		if (jPanelAge == null) {
			jPanelAge = new JPanel();
			jPanelAge.setLayout(new BorderLayout());
			lAns = new JLabel();
			lAns.setText(" ans");
			jPanelAge.add(getJTextField2(),BorderLayout.WEST);
			jPanelAge.add(lAns,BorderLayout.CENTER);
		}
		return jPanelAge;
	}
	
	/**
	 * This method initializes jPanel
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.gridx = 2;
			gridBagConstraints22.insets = new Insets(30, 10, 10, 10);
			gridBagConstraints22.gridy = 10;
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.gridx = 2;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints20.anchor = GridBagConstraints.CENTER;
			gridBagConstraints20.gridy = 8;
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.anchor = GridBagConstraints.CENTER;
			gridBagConstraints19.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints19.weightx = 1.0;
			gridBagConstraints19.gridwidth = 1;
			gridBagConstraints19.gridy = 8;
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.anchor = GridBagConstraints.CENTER;
			gridBagConstraints18.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints18.weightx = 1.0;
			gridBagConstraints18.gridy = 8;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.insets = new Insets(15, 5, 5, 5);
			gridBagConstraints16.anchor = GridBagConstraints.WEST;
			gridBagConstraints16.gridy = 5;
			lMandats = new JLabel();
			lMandats.setText("Mandats éffectués :");
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints15.gridy = 4;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints14.gridy = 3;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints14.gridwidth = 1;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints13.gridwidth = 1;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints12.gridwidth = 1;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 7;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.weighty = 1.0;
			gridBagConstraints11.gridheight = 1;
			gridBagConstraints11.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints11.gridwidth = 3;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridx = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.gridwidth = 1;
			gridBagConstraints10.gridy = 4;
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
			gridBagConstraints6.gridx = 1;
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
			jPanel.setSize(new Dimension(416, 406));
			jPanel.add(lId, gridBagConstraints1);
			jPanel.add(lNom, gridBagConstraints5);
			jPanel.add(id, gridBagConstraints6);
			jPanel.add(lPrenom, gridBagConstraints8);
			jPanel.add(lAge, gridBagConstraints9);
			jPanel.add(lProfession, gridBagConstraints10);
			jPanel.add(getList1(), gridBagConstraints11);
			jPanel.add(lMandats, gridBagConstraints16);
			jPanel.add(getJButton(), gridBagConstraints18);
			jPanel.add(getJButton1(), gridBagConstraints19);
			jPanel.add(getJButton2(), gridBagConstraints20);
			jPanel.add(getButton3(), gridBagConstraints22);
			jPanel.setMinimumSize(new Dimension(240, 280));
			jPanel.add(getJTextField(), gridBagConstraints12);
			jPanel.add(getJTextField1(), gridBagConstraints13);
			jPanel.add(getJPanelAge(), gridBagConstraints14);
			jPanel.add(getJTextField3(), gridBagConstraints15);
			jPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			jPanel.setPreferredSize(new Dimension(450,250));
		}
		return jPanel;
	}

	/**
	 * This method initializes list1	
	 * 	
	 * @return java.awt.List	
	 */
	private JScrollPane getList1() {
		if (listMandats == null) {
			listMandats = new List();
			jScrollPaneMandat.getViewport().add(listMandats);
		}
		return jScrollPaneMandat;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jtNom == null) {
			jtNom = new JTextField();
			jtNom.setPreferredSize(new Dimension(180, 19));
			jtNom.setMinimumSize(new Dimension(180, 19));
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
			jtPrenom.setPreferredSize(new Dimension(180, 19));
			jtPrenom.setMinimumSize(new Dimension(180, 19));
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
			jtAge.setPreferredSize(new Dimension(61, 19));
			jtAge.setMinimumSize(new Dimension(61, 19));
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
			jtProfession.setPreferredSize(new Dimension(180, 19));
			jtProfession.setMinimumSize(new Dimension(180, 19));
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

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField4() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
