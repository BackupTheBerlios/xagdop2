package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import xagdop.Controleur.CTeamManagement;
import xagdop.Controleur.CTreeNode;
import xagdop.Model.Projects;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;

public class ITeamManagement extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1080162447493236178L;
	
	private static ITeamManagement IT = null;
	private JButton ButtonOK=new JButton();
    private JButton ButtonCancel=new JButton();
    private JButton ButtonApply = new JButton();
    private JCheckBox AnalystCheck=new JCheckBox();
    private JCheckBox ArchitectCheck = new JCheckBox();
    private JCheckBox RedacterCheck = new JCheckBox();
    private JComboBox UserListCombo ;
    private JLabel AnalystLabel = new JLabel();
    private JLabel ArchitectLabel = new JLabel();
    private JLabel RedacterLabel = new JLabel();
    private JLabel UserLabel = new JLabel();
    private JPanel newPanel = new JPanel();
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	private ProjectsParser projects;
	private Projects projet;
	private UsersParser users;
	private CTeamManagement CTeamM; 
	
	private String nomProjet;
	
	private ITeamManagement(){
		init();
	}
	
	
	private void init(){ 
		CTeamM = new CTeamManagement("Projet1");
		
		

        
		
		        projects = new ProjectsParser();
		        projet = projects.getAllUsers("Projet1");
		        
		        ArrayList list = projet.getUsersId();
		        UserListCombo = new JComboBox() ;   	 	
		   	 	
		        users = new UsersParser();
		        
		    	//Remplissage de la combobox avec les valeurs de la list
		      	for(int i=0; i<list.size(); i++)
		    	{
		      		UserListCombo.addItem(users.getAttribute(((Integer)list.get(i)).intValue(),"login"));
				}
		   	 
		 	 
		
		
		
		
		
		
        getContentPane().setLayout(new GridBagLayout());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newPanel.setLayout(new GridBagLayout());

        newPanel.setMinimumSize(new Dimension(296, 130));
        UserLabel.setText("Selectionner l'utilisateur");
        UserLabel.setText(nomProjet);
        
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        newPanel.add(UserLabel, gridBagConstraints);
        
        
        
        UserListCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Changement des checkBox avec les droits
            	refresh();
            	
            	
            	
            	
            	//UserListComboActionPerformed(evt);
            }
        });

      
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        newPanel.add(UserListCombo, gridBagConstraints);

        
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        newPanel.add(AnalystCheck, gridBagConstraints);

       

        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        newPanel.add(ArchitectCheck, gridBagConstraints);

        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        newPanel.add(RedacterCheck, gridBagConstraints);

        ArchitectLabel.setText("Architecte");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        newPanel.add(ArchitectLabel, gridBagConstraints);

        RedacterLabel.setText("Redacteur");

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        newPanel.add(RedacterLabel, gridBagConstraints);

        AnalystLabel.setText("Analyste");

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        newPanel.add(AnalystLabel, gridBagConstraints);

        ButtonOK.setText("Ok");
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CTeamM.Apply(projects,users.getId((String)UserListCombo.getSelectedItem()),ArchitectCheck.isSelected(),AnalystCheck.isSelected(),RedacterCheck.isSelected());
           	    
                //fermer la fenetre
            	IT.dispose();
            }
        });


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        newPanel.add(ButtonOK, gridBagConstraints);

        ButtonCancel.setText("Annuler");
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 //Fermer la fenetre
            	IT.dispose();
            }
        });


        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        newPanel.add(ButtonCancel, gridBagConstraints);
   
        
        ButtonApply.setText("Appliquer");
        ButtonApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	 CTeamM.Apply(projects,users.getId((String)UserListCombo.getSelectedItem()),ArchitectCheck.isSelected(),AnalystCheck.isSelected(),RedacterCheck.isSelected());
            	 //projects.refresh();
            	 projet = projects.getAllUsers("Projet1");
            }
        });


        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        //gridBagConstraints.gridwidth = 1;
        newPanel.add(ButtonApply, gridBagConstraints);
        getContentPane().add(newPanel, new GridBagConstraints());

        pack();
        refresh();
   	 
		setTitle("Affectation de l'equipe");
		
		//setSize(300, 200);
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static ITeamManagement getIT(CTreeNode ctreenode) {
		if (IT==null){
			IT = new ITeamManagement(); 
		}
		IT.setNomProjet(ctreenode.getName()) ;
		return IT;
	}
	
	public void refresh(){
		
		AnalystCheck.setSelected(projet.isAnalyst(users.getId((String)UserListCombo.getSelectedItem())));
		RedacterCheck.setSelected(projet.isRedacteur(users.getId((String)UserListCombo.getSelectedItem())));
		ArchitectCheck.setSelected(projet.isArchitecte(users.getId((String)UserListCombo.getSelectedItem())));
		
		
	}


	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	
}