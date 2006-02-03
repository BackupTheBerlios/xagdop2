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
import xagdop.Model.Project;
import xagdop.Parser.ProjectsParser;

public class ITeamManagement extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1080162447493236178L;
	
	private JButton ButtonOK=new JButton();
    private JButton ButtonCancel=new JButton();
    private JButton ButtonApply = new JButton();
    private JButton ButtonAffect = new JButton();
    private JCheckBox AnalystCheck=new JCheckBox();
    private JCheckBox ArchitectCheck = new JCheckBox();
    private JCheckBox RedacterCheck = new JCheckBox();
    private JCheckBox PManagerCheck = new JCheckBox();
    private JComboBox UserListCombo ;
    private JLabel AnalystLabel = new JLabel();
    private JLabel ArchitectLabel = new JLabel();
    private JLabel RedacterLabel = new JLabel();
    private JLabel UserLabel = new JLabel();
    private JLabel PManager = new JLabel();
    private JPanel newPanel = new JPanel();
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	private ProjectsParser projects;
	private Project projet;
	private CTeamManagement CTeamM; 
	
	private String nomProjet;
	public String nP;
	public ITeamManagement(String ProjectName){
		this.setNomProjet(ProjectName);
		init();
	}
	
	
	private void init(){ 
		
		nP = this.getNomProjet();
		CTeamM = new CTeamManagement(nP);
		
		

        
		
		        projects = ProjectsParser.getInstance();
		        projet = projects.buildProject(nP);
		        
		        ArrayList list = projet.getUsersLogin();
		        UserListCombo = new JComboBox() ;   	 	
		        
		    	//Remplissage de la combobox avec les valeurs de la list
		      	for(int i=0; i<list.size(); i++)
		    	{
		      		UserListCombo.addItem(list.get(i));
				}
		   	 
			 
		
		
		
		
		
		
        getContentPane().setLayout(new GridBagLayout());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newPanel.setLayout(new GridBagLayout());

        newPanel.setMinimumSize(new Dimension(296, 130));
        UserLabel.setText("Selectionner l'utilisateur");
        
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
        gridBagConstraints.gridwidth = 2;
        newPanel.add(UserListCombo, gridBagConstraints);

        ButtonAffect.setText("Affecter");
        ButtonAffect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                CTeamM.Apply(((String)UserListCombo.getSelectedItem()),ArchitectCheck.isSelected(),AnalystCheck.isSelected(),RedacterCheck.isSelected(),PManagerCheck.isSelected());
           	    IAffect IA = IAffect.getIA();
           	    IA.setProjectName(nP);
           	    IA.setVisible(true);
                //fermer la fenetre
            	dispose();
            }
        });
        
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        newPanel.add(ButtonAffect, gridBagConstraints);
        
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        newPanel.add(AnalystCheck, gridBagConstraints);

       
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        newPanel.add(PManagerCheck, gridBagConstraints);
        
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


        PManager.setText("PManager");
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        newPanel.add(PManager, gridBagConstraints);

        
        
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
                CTeamM.Apply(((String)UserListCombo.getSelectedItem()),ArchitectCheck.isSelected(),AnalystCheck.isSelected(),RedacterCheck.isSelected(),PManagerCheck.isSelected());
           	    
                //fermer la fenetre
            	dispose();
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
            	dispose();
            }
        });


        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        newPanel.add(ButtonCancel, gridBagConstraints);
   
        
        ButtonApply.setText("Appliquer");
        ButtonApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	 CTeamM.Apply(((String)UserListCombo.getSelectedItem()),ArchitectCheck.isSelected(),AnalystCheck.isSelected(),RedacterCheck.isSelected(),PManagerCheck.isSelected());
            	 //projects.refresh();
            	 projet = projects.buildProject("Projet1");
            }
        });


        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        newPanel.add(ButtonApply, gridBagConstraints);
        getContentPane().add(newPanel, new GridBagConstraints());

        pack();
        refresh();
   	 
		setTitle("Affectation de l'equipe");
		
		//setSize(300, 200);
		
	}

	
	public void refresh(){
		
		AnalystCheck.setSelected(projet.isAnalyst(((String)UserListCombo.getSelectedItem())));
		RedacterCheck.setSelected(projet.isRedactor(((String)UserListCombo.getSelectedItem())));
		ArchitectCheck.setSelected(projet.isArchitect(((String)UserListCombo.getSelectedItem())));
		PManagerCheck.setSelected(projet.isManager(((String)UserListCombo.getSelectedItem())));
		
	 	 
	}
	
	/*
	public void refreshCombo(){

		//Rajout des nouveaux users

	
		projects = new ProjectsParser();
        projet = projects.getAllUsers("Projet1");
        ArrayList list = projet.getUsersId();
		 
	
       
        users = new UsersParser();
	    	//Remplissage de la combobox avec les valeurs de la list
	      	for(int i=UserListCombo.getItemCount(); i<list.size(); i++)
	    	{
	      			UserListCombo.addItem(users.getAttribute(((Integer)list.get(i)).intValue(),"login"));
	    	}
	

	      	
	      	UserListCombo.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                //Changement des checkBox avec les droits
	            	refresh();
	            }
	        });
		}
		
	*/


	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}


	public String getNomProjet() {
		return nomProjet;
	}
	
}