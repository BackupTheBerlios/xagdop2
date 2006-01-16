package xagdop.Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.management.InstanceNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import xagdop.Controleur.CUserCreate;
import xagdop.ressources.Bundle;


public class IUserCreate extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6591847623691362372L;
	private static IUserCreate IUC = null;
	private JPanel panel;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JLabel passConfLabel;
	private JTextField userID;
	private JPasswordField password;
	private JPasswordField passConf;
	int i=0;
	

	private IUserCreate(){
		init();
	}
	
	
	private void init(){
		setTitle("Creation d'un nouvel utilisateur");
		setSize(400, 200);
		panel = new JPanel();
		panel.setLayout(new GridLayout(4,3));
		
		userIDLabel = new JLabel(Bundle.getText("iuser.label.id"));
		userID = new JTextField();

		passwordLabel = new JLabel(Bundle.getText("iuser.label.password"));
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(8);
		
		passConfLabel = new JLabel(Bundle.getText("iuser.label.passconf"));
		passConf = new JPasswordField();
		passConf.setEchoChar('*');
		passConf.setColumns(8);
		
		panel.add(userIDLabel);
		panel.add(userID);
		panel.add(passwordLabel);
		panel.add(password);
		panel.add(passConfLabel);
		panel.add(passConf);
	
		
		JButton valide = new JButton(Bundle.getText("iuser.button.ok"));
		JButton cancel = new JButton(Bundle.getText("iuser.button.cancel"));
		panel.add(valide);
		panel.add(cancel);
		
		//Creation de la fenetre
		setResizable(true) ;
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(400,200);
		setVisible(true);
		pack();
		
		final CUserCreate CUC = new CUserCreate();
		
		
		valide.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	if ((password.getText()).equals(passConf.getText())){
				    		try {
								CUC.creerUser(2,userID.getText(),password.getText());
							} catch (InstanceNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}finally{
					    		IUC.setVisible(false);
					    	}
				    	}
				    	else{
				    		JOptionPane.showMessageDialog(null ,"Les deux mots de passe ne sont pas identiques", "Validation" , 1) ;
				    	}
				    	
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	IUC.dispose();
		    }
		}) ;
		
	
         
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