package uiLogin;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Login extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Login()
	{
		super("Veuillez vous identifier");
		init();
	}
	
	private void init() 
	{
	     //3. Optional: What happens when the frame closes?
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    //4. Create components and put them in the frame.
	    //...create emptyLabel...
	    GridBagLayout layoutGeneral = new GridBagLayout();
	    GridBagLayout layoutPetit = new GridBagLayout();
	    
	    setLayout(layoutGeneral);
	    
	    GridBagConstraints constraints = new GridBagConstraints(1,1,1,1,1.0,1.0,
	    		GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0);
	   
	   
	    JLabel info = new JLabel("Bonjour, veuillez vous identifier afin de procéder au vote. ");
	    layoutGeneral.setConstraints(info, constraints);
	    getContentPane().add(info, layoutGeneral);
	    
	    constraints.gridx = 1;
	    constraints.gridy = 3;
	    JButton ok = new JButton("Valider");
	    layoutGeneral.setConstraints(ok, constraints);
	    getContentPane().add(ok, layoutGeneral);
	    
	    constraints.gridx = 1;
	    constraints.gridy = 2;
	    
	  //  layoutGeneral.(layoutPetit, constraints);
	  //  getContentPane().add(layoutPetit, layoutGeneral);
	    
	    JLabel log = new JLabel("Numéro INSEE : ");
	    JLabel pass = new JLabel("Code : ");
	    getContentPane().add(pass, BorderLayout.WEST);

	    //5. Size the frame.
	    pack();

	    setSize(200, 100);
	    //6. Show it.
	    setVisible(true);

	}

}
