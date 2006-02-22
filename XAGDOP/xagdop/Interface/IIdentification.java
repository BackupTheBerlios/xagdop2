package xagdop.Interface;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import xagdop.Controleur.CPreferencies;
import xagdop.Thread.ThreadIdentify;
import xagdop.ressources.Bundle;

public class IIdentification extends JFrame{
	
	private static final long serialVersionUID = 1L;
	/*Definitions des variables */
	private static IIdentification ident = null;
	private JPanel panel;
	private JLabel welcomlabel;
	private JLabel logo;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JTextField userID;
	private JPasswordField password;
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	private IIdentification(){
		init();

	}

	private void init(){
		Bundle.setCurrentLocale(CPreferencies.getDefaultLocale());
		
		/*Creation du panel */
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setMinimumSize(new Dimension(300, 200));
		
		
		/*Creation de l'identifiant */
		userIDLabel = new JLabel(Bundle.getText("iuser.label.id"));
		userIDLabel.setForeground(Color.red);
		userIDLabel.setFont(new Font("", Font.BOLD,14));
		//userID = new JTextField(Bundle.getText("iuser.Jtextfield.id"));
		userID = new JTextField("claire");
		userID.setColumns(12);
		
		/*Creation du mot de passe */
		passwordLabel = new JLabel(Bundle.getText("iuser.label.password"));
		passwordLabel.setFont(new Font("", Font.BOLD,14));
		passwordLabel.setForeground(Color.red);
		password = new JPasswordField("pass");
		//password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(12);
		
		/*Creation de l'image */
		welcomlabel = new JLabel(Bundle.getText("iuser.label.welcom"));
		welcomlabel.setFont(new Font("", Font.BOLD,23));
		welcomlabel.setForeground(Color.red);
		
		/*Affichage du mot de passe*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth =2;
        gridBagConstraints.gridheight=1;
        gridBagConstraints.insets= new Insets(15,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(welcomlabel,gridBagConstraints);
		
		/*Affichage du label de l'identifiant*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets= new Insets(70,60,5,10);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridwidth =1;
        gridBagConstraints.gridheight=1;
        panel.add(userIDLabel, gridBagConstraints);
        
        /*Affichage du champs de l'identifiant*/
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets= new Insets(70,20,5,5);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(userID, gridBagConstraints);

        /*Affichage du label du mot de passe*/
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets= new Insets(20,60,5,10);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panel.add(passwordLabel, gridBagConstraints);
        
        /*Affichage du champs du mot de passe*/
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets= new Insets(20,20,5,5);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(password, gridBagConstraints);
      
	
		/*Creation des boutons*/
		JButton valide = new JButton(Bundle.getText("button.ok"));
		JButton cancel = new JButton(Bundle.getText("button.cancel"));
		
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets= new Insets(120,60,5,10);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(valide, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;//2;//3;
        gridBagConstraints.insets= new Insets(120,20,5,5);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(cancel, gridBagConstraints);
		
        
        /*Creation de l'image */
        logo = new JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP9.jpg"))) ;
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth =2;
        gridBagConstraints.gridheight=4;
        gridBagConstraints.insets= new Insets(0,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(logo,gridBagConstraints);
        
        
        /*Action relative aux boutons*/		
		valide.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	
				    	IWaiting iWait = IWaiting.getInstance();
				    	iWait.demarrer();
				    	ThreadIdentify ti = new ThreadIdentify(userID.getText(),new String(password.getPassword()),ident);
				    	ti.start();
				    	
					
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	System.exit(0) ; 	
		    }
		}) ;
		
		/*Creation de la fenetre*/
		setTitle(Bundle.getText("iuser.title"));
		setResizable(false) ;
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(300,200);
		setVisible(true);
		pack();
	}
		    
	public static IIdentification getInstance() {
		if (ident==null){
			ident = new IIdentification(); 
		}
		
		return ident;
	}
	

	
}