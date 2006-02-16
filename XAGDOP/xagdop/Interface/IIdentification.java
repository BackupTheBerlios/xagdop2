package xagdop.Interface;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import xagdop.Controleur.CUser;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

public class IIdentification extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static IIdentification ident = null;
	private JPanel panel;
	private JLabel logodeux;
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
		
		
		panel = new JPanel();
		//equipe = new JButton(new ImageIcon(imageURL));
		
		panel.setLayout(new GridBagLayout());
		panel.setMinimumSize(new Dimension(300, 200));
		
		
		/*Creation du mot de passe */
		userIDLabel = new JLabel(Bundle.getText("iuser.label.id"));
		userIDLabel.setForeground(Color.white);
		//userID = new JTextField(Bundle.getText("iuser.Jtextfield.id"));
		userID = new JTextField("claire");
		userID.setColumns(12);
		
		/*Creation du mot de passe */
		passwordLabel = new JLabel(Bundle.getText("iuser.label.password"));
		passwordLabel.setForeground(Color.orange);
		password = new JPasswordField("pass");
		//password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(12);
		
		/*Creation de l'image */
		logodeux = new JLabel(Bundle.getText("iuser.title"));
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth =2;
        gridBagConstraints.gridheight=1;
        gridBagConstraints.insets= new Insets(15,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(logodeux,gridBagConstraints);
		
		
		
		/*Affichage des champs*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets= new Insets(35,60,5,10);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridwidth =1;
        gridBagConstraints.gridheight=1;
        panel.add(userIDLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets= new Insets(35,20,5,5);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
         panel.add(userID, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets= new Insets(20,60,5,10);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panel.add(passwordLabel, gridBagConstraints);
        
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
        gridBagConstraints.insets= new Insets(70,60,5,10);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(valide, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets= new Insets(70,20,5,5);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(cancel, gridBagConstraints);
		
        
        /*Creation de l'image */
        logo = new JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP4.jpg"))) ;
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
				    	CUser CU = new CUser();
				    	try {
							if (CU.verifUser(userID.getText(),new String(password.getPassword())))
							{
								ident.setVisible(false);	
								XAGDOP.getInstance().showFrame();
								
							}
						} catch (Exception e1) {

							//e1.printStackTrace();
							ErrorManager.getInstance().display();

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
		    



	public static IIdentification getInstance() {
		if (ident==null){
			ident = new IIdentification(); 
		}
		
		return ident;
	}
	

	public static void main(String args[]){
		Bundle.setCurrentLocale(Locale.FRENCH);
		IIdentification.getInstance();

	}
}