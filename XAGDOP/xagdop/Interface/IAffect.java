package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;


import javax.management.InstanceNotFoundException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import xagdop.Controleur.CAffect;
import xagdop.Model.Project;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.ressources.Bundle;

public class IAffect extends JFrame
{
	private String projectName;
	
	 // Variables declaration - do not modify
    private JCheckBox AnalystCheck = new JCheckBox();
    private JCheckBox ArchiCheck = new JCheckBox();
    private JButton CancelButton = new JButton();
    private JCheckBox ChefCheck = new JCheckBox();
    private JLabel EnterLabel = new JLabel();
    //private JTextField LoginUser = new JTextField();
    private JButton OkButton = new JButton();
    private JCheckBox RedacteurCheck = new JCheckBox();
    private JPanel jPanel1 = new JPanel();
    private JSeparator jSeparator1 = new JSeparator();
    private JSeparator jSeparator2 = new JSeparator();
    private JComboBox userList;
    
    
    CAffect CA ;
    
    // End of variables declaration
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static IAffect IA;
	
	private IAffect(String nomProjet){
		projectName = nomProjet;
		init(projectName);
	}
	/**
	 * 
	 * 
	 */
	public void init(String myProjet){
		
		CA = new CAffect();
		GridBagConstraints gridBagConstraints;
		
		userList = new JComboBox();
		
        
        jPanel1.setLayout(new GridBagLayout());

        OkButton.setText(Bundle.getText("button.ok"));
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		try {
						CA.affecter(projectName,(String)userList.getSelectedItem(),ChefCheck.isSelected(),ArchiCheck.isSelected(),RedacteurCheck.isSelected(),AnalystCheck.isSelected());
						JOptionPane.showMessageDialog(null ,Bundle.getText("iaffect.userAdd.text"), Bundle.getText("iaffect.FrameReussite.text") , 1) ;
						try
						{							
							IJTeamManagement IJ = IJTeamManagement.getIJTM(projectName);
		            		IJ.refreshUsers();							
						}
						catch (Exception e1){
							System.out.println("plantouille");
						}
					}catch(InstanceNotFoundException i){
						i.printStackTrace();
						JOptionPane.showMessageDialog(null ,Bundle.getText("iaffect.userNotExist.text"), Bundle.getText("iaffect.FrameErreur.text") , 1) ;
					}
            		catch (Exception e) {
						//Error of create
            			e.printStackTrace();
						JOptionPane.showMessageDialog(null ,Bundle.getText("iaffect.userExist.text"), Bundle.getText("iaffect.FrameErreur.text") , 1) ;
					}
            		IA.dispose();
            		
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        jPanel1.add(OkButton, gridBagConstraints);

        CancelButton.setText(Bundle.getText("button.cancel"));
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IA.dispose();
            }
        });
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        jPanel1.add(CancelButton, gridBagConstraints);

        AnalystCheck.setText("Analyst");
             
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(AnalystCheck, gridBagConstraints);

        ArchiCheck.setText("Archi");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(ArchiCheck, gridBagConstraints);

        RedacteurCheck.setText("Redacteur");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(RedacteurCheck, gridBagConstraints);

        ChefCheck.setText("Chef");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(ChefCheck, gridBagConstraints);

        //userCheck.setColumns(10);
             
        // Initialisation de la comboBox des users
        
        initCombo();
        
        
        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel1.add(userList, gridBagConstraints);

        EnterLabel.setText(Bundle.getText("iaffect.userLogin.text"));

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel1.add(EnterLabel, gridBagConstraints);


        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jSeparator1, gridBagConstraints);

        jSeparator2.setOrientation(SwingConstants.VERTICAL);
        jSeparator2.setMinimumSize(new Dimension(0, 10));
        jSeparator2.setPreferredSize(new Dimension(2, 0));

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jSeparator2, gridBagConstraints);

       getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
        //IA.setTitle(IA.getProjectName());
		
	}
	
	
	
	/** 
	* @return Returns the singleton.
	 */
	public static IAffect getIA(String projetN) {
		if (IA==null){
			IA = new IAffect(projetN); 
		}
		
		return IA;
	}
	
	public void initCombo() {
	
		// Liste deroulante des utilisateurs sans ceux du projet
		
		//System.out.println("IAffectProjetName"+projectName);
		
		ArrayList listUser = UsersParser.getInstance().getAllUsers();
		Project monProjet = ProjectsParser.getInstance().buildProject(projectName);
		ArrayList projetUser = monProjet.getUsersLogin();
			
	
		System.out.println("NomDuProjet:"+projectName);
		
		for(int j=0; j<listUser.size(); j++)
		{
			for(int k=0; k<projetUser.size(); k++)
			{
				if (listUser.get(j)!=projetUser.get(k))
				{
					
					userList.addItem(((User)listUser.get(j)).getLogin());
					
				}
			}
		}
		
		
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
	