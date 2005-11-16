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


public class IProject extends JFrame{
	
	//private CProject cpro = new CProject();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1560367300092065010L;
	private static IProject IP = null;
	protected TextField TFNp; 
	protected TextArea TADesc;
	protected JLabel JlabelNameProject;
	protected JPanel JPanelProjectTopContainer;

	private IProject(){
		init();
	}
	
	
	private void init(){
		TFNp = new TextField(30);
		TADesc= new TextArea(4,30);
		
				
		setTitle("Creer un projet");
		setSize(300, 400);
		JPanelProjectTopContainer = new JPanel() ;
		
		JPanelProjectTopContainer.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		    
		JPanelProjectTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		//projetTopContainer.setBackground(black) ;
		JPanelProjectTopContainer.add(Box.createHorizontalStrut(15)) ;


		JlabelNameProject = new JLabel("Nom du projet");
		JlabelNameProject.setToolTipText("<HTML><BODY BGCOLOR=#E3E3E3><FONT COLOR=#0000AB>Nom du projet sur le depot SubVersion</FONT></BODY></HTML>") ;
		JPanelProjectTopContainer.add(JlabelNameProject);
		JPanelProjectTopContainer.add(TFNp);
		JPanelProjectTopContainer.add(new JLabel("Description du projet"));
		JPanelProjectTopContainer.add(TADesc);
		
		JButton valider = new JButton("valideSr");
		valider.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	CProject project= new CProject(TFNp.getText(),TADesc.getText());			    	
				    	if(project.createProject()==0){
				    		TADesc.setText("");
				    		TFNp.setText("");
				    	}
				    	IP.setVisible(false);
				    }
				}) ;

		JPanelProjectTopContainer.add(valider);
		
		//Creation de la fenetre
		setResizable(true) ;

		setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE) ;
		
		JPanelProjectTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		getContentPane().add(JPanelProjectTopContainer , BorderLayout.CENTER) ;
		setLocation(new Point(500 , 200)) ;


		 
         
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IProject getIP() {
		if (IP==null){
			IP = new IProject(); 
		}
		
		return IP;
	}
}