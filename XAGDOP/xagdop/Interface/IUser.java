package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import xagdop.Controleur.CTreeNode;
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
	int i=0;
	
	
	private IUser(){
		init();
	}
	
	
	private void init(){
		
		setTitle("Identification");
		setSize(300, 200);
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		userIDLabel = new JLabel("Userid");
		passwordLabel = new JLabel("Password");
		panel.add(userIDLabel);
		userID = new JTextField("Your UserID");
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setColumns(8);

		panel.add(userID);
		panel.add(passwordLabel);
		panel.add(password);

	
		
		JButton valide = new JButton(Bundle.getText("iprojectpreference.button.ok"));
		JButton cancel = new JButton(Bundle.getText("iprojectpreference.button.cancel"));
		
		valide.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	IU.setVisible(false);
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	IU.dispose();   	
		    }
		}) ;
		

		panel.add(valide);
		panel.add(cancel);
		//Creation de la fenetre
		setResizable(true) ;

		/*setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE) ;
		panel.setBorder(BorderFactory.createEtchedBorder()) ;
		getContentPane().add(panel , BorderLayout.CENTER) ;
		setLocation(new Point(500 , 200)) ;
		*/
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(200,200);
		setLocation(300,200);
		setVisible(true);
		pack();

	}
		    
		
	/**
	 * @return Returns the singleton.
	 */
	public static IUser getIU() {
		if (IU==null){
			IU = new IUser(); 
		}
		
		return IU;
	}
}