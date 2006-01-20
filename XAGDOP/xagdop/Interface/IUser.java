package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import xagdop.Controleur.CUser;
import xagdop.ressources.Bundle;

public class IUser extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3992012367920054698L;
	private static IUser IU = null;
	private JPanel panel;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JTextField userID;
	private JPasswordField password;
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    
	int i=0;
	
	
	private IUser(){
		init();
	}
	
	
	private void init(){
		
		final CUser CU = new CUser();
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setMinimumSize(new Dimension(300, 200));
		
		
		/*Creation du mot de passe */
		userIDLabel = new JLabel(Bundle.getText("iuser.label.id"));
		//userID = new JTextField(Bundle.getText("iuser.Jtextfield.id"));
		userID = new JTextField("user");
		userID.setColumns(8);
		
		/*Creation du mot de passe */
		passwordLabel = new JLabel(Bundle.getText("iuser.label.password"));
		password = new JPasswordField("pass");
		//password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(8);
		
		
		
		/*Affichage des champs*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        panel.add(userIDLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        panel.add(userID, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        panel.add(passwordLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        panel.add(password, gridBagConstraints);
      
	
		/*Creation des boutons*/
		JButton valide = new JButton(Bundle.getText("iuser.button.ok"));
		JButton cancel = new JButton(Bundle.getText("iuser.button.cancel"));
		
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        panel.add(valide, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        panel.add(cancel, gridBagConstraints);
		
        /*Action relative aux boutons*/		
		valide.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	if (CU.verifUser(userID.getText(),password.getText()))
				    	{
				    		IU.setVisible(false);		    		
				    	}
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	System.exit(0) ; 	
		    }
		}) ;
		
		//Creation de la fenetre
		setTitle(Bundle.getText("iuser.title"));
		setSize(250, 150);
		setResizable(false) ;
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(300,200);
		setVisible(true);
		pack();

	}
		    
	public static IUser getIU() {
		if (IU==null){
			IU = new IUser(); 
		}
		
		return IU;
	}
}