package xagdop.Interface;


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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CProject;
import xagdop.ressources.Bundle;


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
		TADesc= new TextArea(4,31);
		
				
		setTitle(Bundle.getText("iproject.title"));
		setSize(261, 235);
		JPanelProjectTopContainer = new JPanel() ;
		
		JPanelProjectTopContainer.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		    
		JPanelProjectTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		//projetTopContainer.setBackground(black) ;
		//JPanelProjectTopContainer.add(Box.createHorizontalStrut(15)) ;

		JPanelProjectTopContainer.add(Box.createHorizontalStrut(72)) ;
		JlabelNameProject = new JLabel(Bundle.getText("iproject.label.projectname"));
		JlabelNameProject.setToolTipText(Bundle.getText("iproject.tooltip.projectname")) ;
		JPanelProjectTopContainer.add(JlabelNameProject);
		JPanelProjectTopContainer.add(TFNp);
		JPanelProjectTopContainer.add(Box.createHorizontalStrut(72)) ;
		JPanelProjectTopContainer.add(new JLabel(Bundle.getText("iproject.label.description")));
		JPanelProjectTopContainer.add(TADesc);
		
		JButton valider = new JButton(Bundle.getText("iproject.button.ok"));
		valider.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	CProject project= new CProject();			    	
				    	try{
				    		project.createProject(TFNp.getText(),TADesc.getText());
				    		JOptionPane.showMessageDialog(null ,"Le projet "+TFNp.getText()+" a bien ete cree ", "Validation" , 1) ;
				    		TADesc.setText("");
				    		TFNp.setText("");
				    	}catch(SVNException ex){
				    		JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
				    	}
				    	catch(Exception ex){
				    		JOptionPane.showMessageDialog(null ,"Le projet "+TFNp.getText()+" n'a pu etre cree ", "Validation" , 1) ;
				    	}finally{
				    		IP.setVisible(false);
				    	}
				    }
				}) ;
		JPanelProjectTopContainer.add(Box.createHorizontalStrut(80)) ;
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