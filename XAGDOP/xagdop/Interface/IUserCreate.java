package xagdop.Interface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.management.InstanceNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import xagdop.Controleur.CUser;
import xagdop.ressources.Bundle;


public class IUserCreate extends JFrame{

	private static final long serialVersionUID = 6591847623691362372L;
	private static IUserCreate IUC = null;
	private JPanel panel;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JLabel passConfLabel;
	private JTextField userID;
	private JPasswordField password;
	private JPasswordField passConf;
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	int i=0;
	

	private IUserCreate(){
		init();
	}
	
	
	private void init(){
		
		final CUser CU = new CUser();
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
	
		
		/*Creation de l'identifiant */
		userIDLabel = new JLabel(Bundle.getText("iuser.label.id"));
		userID = new JTextField();
		userID.setColumns(8);
		
		/*Creation du mot de passe */
		passwordLabel = new JLabel(Bundle.getText("iuser.label.password"));
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(8);
		
		/*Creation de la confirmation du mot de passe */
		passConfLabel = new JLabel(Bundle.getText("iuser.label.passconf"));
		passConf = new JPasswordField();
		passConf.setEchoChar('*');
		passConf.setColumns(8);
	
		
		/* AFFICHAGE DES CHAMPS */
		
		/*Pour L'identifiant*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets= new Insets(5,5,5,15);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panel.add(userIDLabel, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets= new Insets(5,0,5,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(userID, gridBagConstraints);
	
        /*Pour le mot de passe*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets= new Insets(0,5,5,15);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panel.add(passwordLabel, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets= new Insets(0,0,5,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(password, gridBagConstraints);
		
        /*Pour la confirmation*/
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets= new Insets(0,5,5,15);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        panel.add(passConfLabel, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets= new Insets(0,0,5,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(passConf, gridBagConstraints);
		
		
		/*Creation des boutons*/
		JButton valide = new JButton(Bundle.getText("button.ok"));
		JButton cancel = new JButton(Bundle.getText("button.cancel"));
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets= new Insets(0,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(valide, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets= new Insets(0,0,0,0);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(cancel, gridBagConstraints);

        /*Les actions des boutons*/
		valide.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					if(CU.creerUser(userID.getText(),new String(password.getPassword()),new String(passConf.getPassword()))){
						IUC.setVisible(false);
						IJAdmin.getIJA().refreshUsers();
						IUC = null;
					}
				}catch (InstanceNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				}
			}
		}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	IUC.dispose();
		    	IUC = null;
		    }
		}) ;
		
		//Creation de la fenetre
		setTitle(Bundle.getText("iusercreate.title"));
		setSize(405, 200);
		setBounds(300,200,405,200);
		setResizable(true) ;
		getContentPane().add(panel);
		setLocation(300,200);
		setVisible(true);
		pack();
         
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IUserCreate getIUC() {
		if (IUC==null){
			IUC = new IUserCreate(); 
		}
		
		return IUC;
	}
}