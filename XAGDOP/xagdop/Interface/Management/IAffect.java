package xagdop.Interface.Management;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.management.InstanceNotFoundException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.xpath.XPathExpressionException;

import xagdop.Controleur.CAffect;
import xagdop.Model.User;
import xagdop.Parser.ProjectsParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;
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
		Dimension d = new Dimension();
		
		d.height = 20;
		d.width = 80;
		
		// Creation d un cadre dans la fenetre
		Border cadre = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder(cadre, Bundle.getText("iaffect.cadre"));
        
		userList = new JComboBox();
		userList.setMinimumSize(d);
		
		
        jPanel1.setLayout(new GridBagLayout());
        jPanel1.setBorder(titleStyle);

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
							ErrorManager.getInstance().display();
						}
					}catch(InstanceNotFoundException i){
						i.printStackTrace();
						JOptionPane.showMessageDialog(null ,Bundle.getText("iaffect.userNotExist.text"), Bundle.getText("iaffect.FrameErreur.text") , 1) ;
					}
            		catch (Exception e) {
						//Error of create
            			//e.printStackTrace();
						JOptionPane.showMessageDialog(null ,Bundle.getText("iaffect.userExist.text"), Bundle.getText("iaffect.FrameErreur.text") , 1) ;
					}
            		IA.dispose();
            		IA = null;
            		
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        jPanel1.add(OkButton, gridBagConstraints);

        CancelButton.setText(Bundle.getText("button.cancel"));
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IA.dispose();
                IA = null;
            }
        });
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        jPanel1.add(CancelButton, gridBagConstraints);

        AnalystCheck.setText(Bundle.getText("iaffect.analyst"));
             
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(AnalystCheck, gridBagConstraints);

        ArchiCheck.setText(Bundle.getText("iaffect.architect"));

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(ArchiCheck, gridBagConstraints);

        RedacteurCheck.setText(Bundle.getText("iaffect.redactor"));

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(RedacteurCheck, gridBagConstraints);

        ChefCheck.setText(Bundle.getText("iaffect.manager"));

        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(ChefCheck, gridBagConstraints);

        // Initialisation de la comboBox des users        
        initCombo();
        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        jPanel1.add(userList, gridBagConstraints);

        EnterLabel.setText(Bundle.getText("iaffect.userLogin.text"));

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10,5,15,0);
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

        setLocation(300,300);
        setSize(400,300);
		setResizable(false);
		pack();
        setVisible(true);
		
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
	
	/**
	 * Fonction d'initialisation de la comboBox des utilisateurs
	 * Permet d'afficher les utilisateurs dans la comboBox qui
	 * ne sont pas dans le projet
	 */
	public void initCombo() {
	
		// Liste deroulante des utilisateurs sans ceux du projet				
		ArrayList listUser= null;
		
		try {
			// Recuperation de tous les utilisateurs 
			listUser = UsersParser.getInstance().getAllUsers();			
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// initialisation de la comboBox
		userList.removeAllItems();
		userList.setSize(70,30);
		
		for (int i=0;i<listUser.size();i++)
		{	
			try {
				// test sur l'appartenance d un utilisateur a un projet
				if (!ProjectsParser.getInstance().isUserInProject(projectName,((String)((User)listUser.get(i)).getLogin())))		
				{	
					// Ajout a la comboBox des utilisateurs n appartenant pas au projet 
					userList.addItem(((User)listUser.get(i)).getLogin());					
				}				
			}
			catch (Exception e) {
				e.printStackTrace();
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
	