/**
 * 
 */
package ui.uiManagement;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.ArrayListStorageContainer;
import src.util.CandidatClient;
import src.util.MandatProxy;
import src.util.MessageDialogBox;
import src.util.UtilORB;
import ui.util.MaVList;
import MaV.ListeC;
import MaV.Mandat;
import controller.MaVListModel;

/**
 * @author nephos
 *
 */
public class CandidatPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanelAge = null;
	private JLabel lId = null;
	private JLabel lNom = null;
	private JLabel id = null;
	private JLabel lPrenom = null;
	private JLabel lAge = null;
	private JLabel lProfession = null;
	private MaVList listMandats = null;
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
	private CandidatClient cand = null;
	private MaVListModel model;
	private JFrame parent ;

	private JScrollPane jScrollPaneMandat = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	public CandidatPanel(JFrame _parent, MaVListModel _model) {
		super();
		model = _model;
		parent = _parent;
		initialize();
	}

	public CandidatPanel(JFrame _parent){
		super();
		parent = _parent;
		initialize();
	}


	private void initialize() {
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.gridx = 2;
		gridBagConstraints22.insets = new Insets(10, 10, 10, 10);
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
		setLayout(new GridBagLayout());
		setSize(new Dimension(500, 433));
		add(lId, gridBagConstraints1);
		add(lNom, gridBagConstraints5);
		add(id, gridBagConstraints6);
		add(lPrenom, gridBagConstraints8);
		add(lAge, gridBagConstraints9);
		add(lProfession, gridBagConstraints10);
		add(getList1(), gridBagConstraints11);
		add(lMandats, gridBagConstraints16);
		add(getJButton(), gridBagConstraints18);
		add(getJButton1(), gridBagConstraints19);
		add(getJButton2(), gridBagConstraints20);
		add(getButton3(), gridBagConstraints22);
		setMinimumSize(new Dimension(240, 280));
		add(getJTextField(), gridBagConstraints12);
		add(getJTextField1(), gridBagConstraints13);
		add(getJPanelAge(), gridBagConstraints14);
		add(getJTextField3(), gridBagConstraints15);
		setBorder(BorderFactory.createLineBorder(Color.black));
		//setPreferredSize(new Dimension(450,250));
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
	 * This method initializes list1	
	 * 	
	 * @return java.awt.List	
	 */
	private JScrollPane getList1() {
		if (listMandats == null) {
			listMandats = new MaVList(new MaVListModel());
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
			jtNom.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					if(model!=null){
						cand.getCand().nom(jtNom.getText());
						model.maj(model.getIndex(cand));
					}
				}
				public void keyTyped(KeyEvent e) {}
			});

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
			jtPrenom.addKeyListener(new KeyListener(){

				public void keyPressed(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					if(model!=null){
						cand.getCand().prenom(jtPrenom.getText());
						model.maj(model.getIndex(cand));
					}
				}
				public void keyTyped(KeyEvent e) {}
			});
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
			bAddMandat.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					MandatManagement mm = new MandatManagement(cand, (MaVListModel) listMandats.getModel());
					mm.setVisible(true);
				}

			});
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
			bDelMandat.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(listMandats.getSelectedIndex() != -1){
						if(askConfirmation()){
							MandatProxy tmp = (MandatProxy) ((MaVListModel)listMandats.getModel()).getElementAt(listMandats.getSelectedIndex());
							((MaVListModel)listMandats.getModel()).removeElement(tmp);
							cand.getCand().removeMandat(tmp.getMand().id());
						}
					}
				}
			});
		}
		return bDelMandat;
	}

	private boolean askConfirmation(){
		//System.out.println(get);
		if(MessageDialogBox.showConfirmDialog(parent, "Confirmation", "Etes-vous sur de vouloir supprimer ce mandat ?")){
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (bEditMandat == null) {
			bEditMandat = new JButton("Editer Mandat");
			bEditMandat.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					MandatManagement mm = new MandatManagement(cand, (MaVListModel) listMandats.getModel(),listMandats.getSelectedIndex());
					mm.setVisible(true);
				}

			});
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
			button.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					cand.getCand().nom(jtNom.getText());
					cand.getCand().prenom(jtPrenom.getText());
					cand.getCand().profession(jtProfession.getText());
					if(!jtAge.getText().equals(""))
						cand.getCand().age(Integer.parseInt(jtAge.getText()));
					else
						cand.getCand().age(-1);

					try {
						UtilORB.getListeC().saveCandidat(cand.getCand());
						MessageDialogBox.showMessageDialog(parent, "Enregistrement ok", "L'enregistrement s'est correctement éffectué");
					} catch (NotFound e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CannotProceed e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvalidName e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


			});
		}
		return button;
	}


	public void completePanel(CandidatClient _cand){
		cand = _cand;
		id.setText(Integer.toString(_cand.getCand().id()));
		jtNom.setText(_cand.getCand().nom());
		jtPrenom.setText(_cand.getCand().prenom());
		if(_cand.getCand().age()!=-1)
			jtAge.setText(Integer.toString(_cand.getCand().age()));
		else
			jtAge.setText("");
		jtProfession.setText(_cand.getCand().profession());

		Mandat[] mandats = _cand.getCand().getMandats();
		ArrayListStorageContainer lsc = new ArrayListStorageContainer();
		for(int i=0; i < mandats.length; i++){
			MandatProxy mp = new MandatProxy(mandats[i]);
			lsc.add(mp);
		}

		((MaVListModel)listMandats.getModel()).setData(lsc);

	}

	public void setEditable(boolean bEditable){
		jtNom.setEditable(bEditable);
		jtPrenom.setEditable(bEditable);
		jtAge.setEditable(bEditable);
		jtProfession.setEditable(bEditable);
		getJButton().setVisible(bEditable);
		getJButton1().setVisible(bEditable);
		getJButton2().setVisible(bEditable);
		getButton3().setVisible(bEditable);

	}

	public void clearPanel(){
		id.setText("");
		jtNom.setText("");
		jtPrenom.setText("");
		jtAge.setText("");
		jtProfession.setText("");
		ArrayListStorageContainer lsc = new ArrayListStorageContainer();
		lsc.clear();
		((MaVListModel)listMandats.getModel()).setData(lsc);
	}

}
