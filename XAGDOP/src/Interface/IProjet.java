package src.Interface;


import java.awt.BorderLayout;
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

import src.Controleur.*;


public class IProjet extends JFrame{
	
	//private CProject cpro = new CProject();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1560367300092065010L;
	private static IProjet IP = null;
	protected TextField TFNp; 
	protected TextArea TADesc;
	protected JLabel JlabelNomProjet;
	protected JPanel JPanelProjetTopContainer;

	private IProjet(){
		init();
	}
	
	
	private void init(){
		TFNp = new TextField(30);
		TADesc= new TextArea(4,30);
		
				
		setTitle("Creer un projet");
		setSize(300, 400);
		JPanelProjetTopContainer = new JPanel() ;
		
			
		
		JPanelProjetTopContainer.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		

		    
		JPanelProjetTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		//projetTopContainer.setBackground(black) ;
		JPanelProjetTopContainer.add(Box.createHorizontalStrut(15)) ;


		JlabelNomProjet = new JLabel("Nom du projet");
		JlabelNomProjet.setToolTipText("<HTML><BODY BGCOLOR=#E3E3E3><FONT COLOR=#0000AB>Nom du projet sur le depot SubVersion</FONT></BODY></HTML>") ;
		JPanelProjetTopContainer.add(JlabelNomProjet);
		JPanelProjetTopContainer.add(TFNp);
		JPanelProjetTopContainer.add(new JLabel("Description du projet"));
		JPanelProjetTopContainer.add(TADesc);
		
		JButton valider = new JButton("valider");
		valider.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
					CProject.nouveauProjet(TFNp.getText(),TADesc.getText());
				    }
				}) ;

		JPanelProjetTopContainer.add(valider);
		
		//Creation de la fenetre
		setResizable(true) ;

		setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE) ;
		
		JPanelProjetTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		getContentPane().add(JPanelProjetTopContainer , BorderLayout.CENTER) ;
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