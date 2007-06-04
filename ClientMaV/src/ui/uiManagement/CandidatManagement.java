package ui.uiManagement;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.ArrayListStorageContainer;
import src.util.CandidatClient;
import src.util.MessageDialogBox;
import src.util.UtilORB;
import ui.util.MaVList;
import MaV.Candidat;
import MaV.ListeC;
import controller.MaVComparator;
import controller.MaVListModel;
import controller.MaVSearchable;

public class CandidatManagement extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8598685457321999040L;
	private JPanel jContentPane = null;
	private JPanel jPanelImage = null;
	private Button bAdd = null;
	private MaVList listCandidat = null;  //  @jve:decl-index=0:visual-constraint="613,10"
	private Button bDel = null;
	private Button bQuit = null;
	private CandidatPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="443,13"
	private JTextField jTextField = null;
	private JScrollPane jScrollPane_List = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


	private MaVListModel jListModelList = new MaVListModel();

	private String strOrderAttribute = "";
	private MaVComparator myComparator = null;

	/**
	 * This method initializes 
	 * 
	 */
	public CandidatManagement() {
		super();
		initialize();
	}
	public CandidatManagement(MaVComparator mc, MaVSearchable ms, ArrayListStorageContainer list ) {
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

		this.setContentPane(getJContentPane());
		this.setTitle("Edition des candidats");
		this.setMinimumSize(new Dimension(320,240));
		this.setSize(new Dimension(750, 490));
		this.setPreferredSize(new Dimension(750, 490));
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
			jContentPane.add(getJPanelImage(), gridBagConstraints7);
			jContentPane.add(getJPanel(), gridBagConstraints7);
			jContentPane.add(getJTextField4(), gridBagConstraints17);
			getJPanelImage().setVisible(true);
			getJPanel().setVisible(false);

		}
		return jContentPane;
	}


	private JPanel getJPanelImage() {
		if (jPanelImage == null) {
			jPanelImage = new JPanel();
			jPanelImage.setPreferredSize(new Dimension(450,250));
		}
		return jPanelImage;
	}
	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton() {
		if (bAdd == null) {
			bAdd = new Button();
			bAdd.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					listCandidat.setSelectedIndex(-1);
					CandidatClient cand = new CandidatClient("", "", -1, "");
					jListModelList.addElement(cand);
					jListModelList.sort(myComparator, "", true);
					getJPanel().completePanel(cand);
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
		if (listCandidat == null) {
			listCandidat = new MaVList(jListModelList);
			listCandidat.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1) {
						//((PanelCompany)getJPanelEdit()).set((ECSSCompany)listOfCompany.getModel().getElementAt(index));
						if(listCandidat.getSelectedIndex() == -1){
							getJPanelImage().setVisible(true);
							getJPanel().setVisible(false);
						}else{
							getJPanel().completePanel((((CandidatClient)jListModelList.getElementAt(listCandidat.getSelectedIndex()))));
							getJPanelImage().setVisible(false);
							getJPanel().setVisible(true);
						}
					}
				}
			});
			jScrollPane_List.getViewport().add(listCandidat);
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
			bDel.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					if(listCandidat.getSelectedIndex() != -1){
						if(askConfirmation()){
							CandidatClient tmp = (CandidatClient)jListModelList.getElementAt(listCandidat.getSelectedIndex());
							getJPanelImage().setVisible(true);
							getJPanel().setVisible(false);
							try {
								ListeC listCand = UtilORB.getListeC();
								listCand.deleteCandidat(tmp.getCand().id());
								jListModelList.removeElement(tmp);
							} catch (NotFound e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (CannotProceed e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InvalidName e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//listCand
						}
					}
				}
			});

			bDel.setLabel("Supprimer");
		}
		return bDel;
	}

	private boolean askConfirmation(){
		if(MessageDialogBox.showConfirmDialog(this, "Confirmation", "Etes-vous sur de vouloir supprimer ce candidat ?")){
			return true;
		}
		return false;
	}

	/**
	 * This method initializes button2	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton2() {
		if (bQuit == null) {
			bQuit = new Button();
			bQuit.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			bQuit.setLabel("Quitter");
		}
		return bQuit;
	}




	/**
	 * This method initializes jPanel
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private CandidatPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new CandidatPanel();
			jPanelImage.setPreferredSize(new Dimension(450,250));
		}
		return jPanel;
	}



	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
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


	public void setComparator(MaVComparator myComparator_,
			String strOrderAttribute_) {
		myComparator = myComparator_;
		strOrderAttribute = strOrderAttribute_;
	}

	public void setLists(ArrayListStorageContainer _list){
		ArrayListStorageContainer _listTmp = new ArrayListStorageContainer();
		for(int i = 0 ; i < _list.size(); i++){
			_listTmp.add(new CandidatClient((Candidat)_list.get(i)));
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

		listCandidat.setSearchDelegationObject(mySearchableDelegationObject_,
				strSearchAttribute_);
	}


	private void jTextField_FindLeftPersonList_keyReleased(KeyEvent e) {
		listCandidat.searchText(jTextField.getText());
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
