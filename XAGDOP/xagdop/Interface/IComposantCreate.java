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

import xagdop.Controleur.CComposantCreate;
import xagdop.Controleur.CUser;
import xagdop.ressources.Bundle;


public class IComposantCreate extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6458234244148394589L;
	private static IComposantCreate ICC = null;
	private JPanel panel;
	private JLabel userIDLabel;
	private JLabel passwordLabel;
	private JLabel passConfLabel;
	private JTextField userID;
	private JPasswordField password;
	private JPasswordField passConf;
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	int i=0;
	

	private IComposantCreate(){
		init();
	}
	
	
	private void init(){
		
		final CComposantCreate CC = new CComposantCreate();
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
	
		
		/*Creation du composant*/
		userIDLabel = new JLabel("Nom du composant");
		userID = new JTextField();
		userID.setColumns(8);
		
		
		
		/* AFFICHAGE DES CHAMPS */
		
		/*Pour le nom du composant*/
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
			
			}
		}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	ICC.dispose();
		    	ICC = null;
		    }
		}) ;
		
		//Creation de la fenetre
		setTitle("Creation d'un composant");
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
	public static IComposantCreate getICC() {
		if (ICC==null){
			ICC = new IComposantCreate(); 
		}
		
		return ICC;
	}
}