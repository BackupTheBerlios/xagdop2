package xagdop.Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import xagdop.Model.Users;
import xagdop.Parser.UsersParser;
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
	private UsersParser UParser = new UsersParser();
	int i=0;
	
	
	private IUser(){
		init();
	}
	
	
	private void init(){
		
		setTitle("Identification");
		setSize(300, 200);
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		userIDLabel = new JLabel(Bundle.getText("iuser.label.id"));
		passwordLabel = new JLabel(Bundle.getText("iuser.label.password"));
		panel.add(userIDLabel);
		userID = new JTextField(Bundle.getText("iuser.Jtextfield.id"));
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(8);

		panel.add(userID);
		panel.add(passwordLabel);
		panel.add(password);

	
		
		JButton valide = new JButton(Bundle.getText("iuser.button.ok"));
		JButton cancel = new JButton(Bundle.getText("iuser.button.cancel"));
		panel.add(valide);
		panel.add(cancel);
		//Creation de la fenetre
		setResizable(true) ;
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setSize(200,200);
		setLocation(300,200);
		setVisible(true);
		pack();
		
		valide.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	Users user = UParser.getUser(userID.getText(),password.getText());
				    	if (user != null){
				    		IPreferences.setDefaultPath(IPreferences.getDefaultPath()+user.getLogin()+"/");
				    		XAGDOP.getInstance().setUser(user);
				    		XAGDOP.getInstance().setVisible(true);
				    		XAGDOP.getInstance().refreshButton();
				    		IU.setVisible(false);
				    	}
				    	else{
				    		JOptionPane.showMessageDialog(null ,"Login incorrect", "Validation" , 1) ;
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
		

		

	}
		    
	public static IUser getIU() {
		if (IU==null){
			IU = new IUser(); 
		}
		
		return IU;
	}
}