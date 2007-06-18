package ui.uiManagement;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import src.util.ArrayListStorageContainer;
import src.util.CandidatClient;
import src.util.ElecteurClient;
import src.util.MandatProxy;
import src.util.MessageDialogBox;
import ui.util.MaVList;
import MaV.Electeur;
import MaV.Mandat;
import controller.MaVComparator;
import controller.MaVListModel;
import controller.MaVSearchable;
/**
 * @author nephos
 *
 */
public class ElecteurManagement extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8598685457321999040L;
	private JPanel jContentPane = null;
	private Button bAdd = null;
	private MaVList listElecteur = null;  //  @jve:decl-index=0:visual-constraint="613,10"
	private Button bDel = null;
	private Button bQuit = null;
	private JPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="443,13"
	private JLabel lInsee = null;
	private JLabel lNom = null;
	private JLabel insee = null;
	private JLabel lPrenom = null;
	private JTextField jtNom = null;
	private JTextField jtPrenom = null;
	private Button button = null;
	private JTextField jTextField = null;
	private JScrollPane jScrollPane_List = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


	private MaVListModel jListModelList = new MaVListModel();

	private String strOrderAttribute = "";
	private MaVComparator myComparator = null;
	private JPanel jPanelImage;
	private ElecteurClient elect = null ;

	/**
	 * This method initializes 
	 * 
	 */
	public ElecteurManagement() {
		super();
		initialize();
	}
	public ElecteurManagement(MaVComparator mc, MaVSearchable ms, ArrayListStorageContainer list ) {
		super();
		initialize();
		setComparator( mc, "");
		setSearchable( ms, "");
		setLists(list);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(640, 420));
		this.setPreferredSize(new Dimension(640, 420));
		this.setContentPane(getJContentPane());
		this.setTitle("Edition des Electeurs");
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
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.weighty = 1.0;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.gridheight=2;


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

			jContentPane.add(getJTextField4(), gridBagConstraints17);
			jContentPane.add(getButton(), gridBagConstraints);
			jContentPane.add(getList(), gridBagConstraints2);
			jContentPane.add(getButton1(), gridBagConstraints3);
			jContentPane.add(getButton2(), gridBagConstraints4);
			jContentPane.add(getJPanel(), gridBagConstraints7);

			jContentPane.setMinimumSize(new Dimension(200,320));
		}
		return jContentPane;
	}

	private JTextField getJTextField4() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					jTextField_FindLeftPersonList_keyReleased(e);
				}
			});
		}
		return jTextField;
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
			bAdd.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					listElecteur.setSelectedIndex(-1);
					//CandidatClient cand = new CandidatClient("", "", -1, "");
					//jListModelList.addElement(cand);
					jListModelList.sort(myComparator, "", true);
					//getJPanel().completePanel(cand);
					getJPanelImage().setVisible(false);
					getJPanel().setVisible(true);
				}

			});
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
		if (listElecteur == null) {
			listElecteur = new MaVList(jListModelList);
			listElecteur.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1) {
						//((PanelCompany)getJPanelEdit()).set((ECSSCompany)listOfCompany.getModel().getElementAt(index));
						if(listElecteur.getSelectedIndex() == -1){
							getJPanelImage().setVisible(true);
							getJPanel().setVisible(false);
						}else{
							completePanel((((ElecteurClient)jListModelList.getElementAt(listElecteur.getSelectedIndex()))));
							getJPanelImage().setVisible(false);
							getJPanel().setVisible(true);
						}
					}
				}
			});
			jScrollPane_List.getViewport().add(listElecteur);
			//jScrollPane_List.setPreferredSize(new Dimension(120,230));
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
			bDel.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(listElecteur.getSelectedIndex() != -1){
						if(askConfirmation()){
							getJPanelImage().setVisible(true);
							getJPanel().setVisible(false);
						}
					}
				}
			});
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

	private JPanel getJPanelImage() {
		if (jPanelImage == null) {
			jPanelImage = new JPanel();
			jPanelImage.setPreferredSize(new Dimension(450,250));
		}
		return jPanelImage;
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
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.gridwidth = 1;
			gridBagConstraints9.gridy = 3;
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
			insee = new JLabel();
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
			lInsee = new JLabel();
			lInsee.setText("N° Insee : ");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setSize(new Dimension(416, 406));
			jPanel.add(lInsee, gridBagConstraints1);
			jPanel.add(lNom, gridBagConstraints5);
			jPanel.add(insee, gridBagConstraints6);
			jPanel.add(lPrenom, gridBagConstraints8);
			jPanel.add(getButton3(), gridBagConstraints22);
			jPanel.setMinimumSize(new Dimension(240, 280));
			jPanel.add(getJTextField(), gridBagConstraints12);
			jPanel.add(getJTextField1(), gridBagConstraints13);
			jPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		return jPanel;
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
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton3() {
		if (button == null) {
			button = new Button();
			button.setLabel("Enregistrer Electeur");
		}
		return button;
	}


	public void setComparator(MaVComparator myComparator_,
			String strOrderAttribute_) {
		myComparator = myComparator_;
		strOrderAttribute = strOrderAttribute_;
	}

	public void setLists(ArrayListStorageContainer _list){
		ArrayListStorageContainer _listTmp = new ArrayListStorageContainer();
		for(int i = 0 ; i < _list.size(); i++){
			_listTmp.add(new ElecteurClient((Electeur)_list.get(i)));
		}
		jListModelList.setData(_listTmp);
		//jListModel_List.setData(ListOfPersonSmart.getInstance().getAllPersons());
		jListModelList.getData().addDataListListener(jListModelList);
		if (myComparator != null) {
			jListModelList.sort(myComparator, strOrderAttribute,
					myComparator.getOrder());
		}
	}


	public void setSearchable(MaVSearchable mySearchableDelegationObject_,
			String strSearchAttribute_) {

		listElecteur.setSearchDelegationObject(mySearchableDelegationObject_,
				strSearchAttribute_);
	}


	private void jTextField_FindLeftPersonList_keyReleased(KeyEvent e) {
		listElecteur.searchText(jTextField.getText());
	}

	private boolean askConfirmation(){
		if(MessageDialogBox.showConfirmDialog(this, "Confirmation", "Etes-vous sur de vouloir supprimer ce candidat ?")){
			return true;
		}
		return false;
	}
	
	public void completePanel(ElecteurClient _elect){
		elect = _elect;
		insee.setText(Integer.toString(_elect.getElect().insee));
		jtNom.setText(_elect.getElect().nom);
		jtPrenom.setText(_elect.getElect().prenom);
	
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"


