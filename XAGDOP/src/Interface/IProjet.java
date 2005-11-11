package src.Interface;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class IProjet extends JFrame{
	
	private static IProjet IP = null;
	

	private IProjet(){
		init();
	}
	
	
	private void init(){
		TextField np;

		TextArea desc;
				
		setTitle("Creer un projet");
		setSize(300, 400);
		JPanel projetTopContainer = new JPanel() ;
		
			
		
		projetTopContainer.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		

		    
		projetTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		//projetTopContainer.setBackground(black) ;
		projetTopContainer.add(Box.createHorizontalStrut(15)) ;


		JLabel nomProjet = new JLabel("Nom du projet");
		nomProjet.setToolTipText("<HTML><BODY BGCOLOR=#E3E3E3><FONT COLOR=#0000AB>Nom du projet sur le depot SubVersion</FONT></BODY></HTML>") ;
		projetTopContainer.add(nomProjet);
		projetTopContainer.add(np = new TextField(30));
		projetTopContainer.add(new JLabel("Description du projet"));
		projetTopContainer.add(desc = new TextArea(4,30));
		
		JButton valider = new JButton("valider");
		valider.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
					//nouveauProjet();
				    }
				}) ;

		projetTopContainer.add(valider);
		
		//Creation de la fenetre
		setResizable(true) ;

		setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE) ;
		
		projetTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		getContentPane().add(projetTopContainer , BorderLayout.CENTER) ;
		setLocation(new Point(500 , 200)) ;


		 
         
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IProjet getIP() {
		if (IP==null){
			IP = new IProjet(); 
		}
		
		return IP;
	}
}