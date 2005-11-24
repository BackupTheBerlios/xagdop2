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
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTreeNode;
import xagdop.ressources.Bundle;

public class IProjectPreferences extends JFrame {
	
	//private CProject cpro = new CProject();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1560367300092065010L;
	private static IProjectPreferences IPP = null;
	protected TextField TFLP; 
	protected TextField TFRP;
	protected JLabel JlabelProjectRepositoryPath;
	protected JLabel JlabelProjectLocalPath;
	protected JPanel JPanelProjectTopContainer;
	protected IProjectTree tree = null;	
	private IProjectPreferences(){
		init();
	}
	
	
	private void init(){
		TFLP = new TextField(30);
		TFRP = new TextField(30);
			
		setTitle(Bundle.getText("iprojectpreference.title"));
		setSize(261, 235);
		
		JPanelProjectTopContainer = new JPanel() ;
		
		JPanelProjectTopContainer.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		    
		JPanelProjectTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
	
		//Initialisation du chemin local
		JPanelProjectTopContainer.add(Box.createHorizontalStrut(72)) ;
		JlabelProjectLocalPath = new JLabel(Bundle.getText("iprojectpreference.label.localpath"));
		JlabelProjectLocalPath.setToolTipText(tree.getSelectedNode().getLocalPath()) ;
		JPanelProjectTopContainer.add(JlabelProjectLocalPath);
		JPanelProjectTopContainer.add(TFLP);
		
		//Initialisation du chemin sur le serveur
		JlabelProjectRepositoryPath = new JLabel(Bundle.getText("iprojectpreference.label.repositorypath"));
		JlabelProjectRepositoryPath.setToolTipText(tree.getSelectedNode().getRepositoryPath()) ;
		JPanelProjectTopContainer.add(Box.createHorizontalStrut(72)) ;
		JPanelProjectTopContainer.add(JlabelProjectRepositoryPath);
		JPanelProjectTopContainer.add(TFRP);
		
		JButton valide = new JButton(Bundle.getText("iprojectpreference.button.ok"));
		JButton cancel = new JButton(Bundle.getText("iprojectpreference.button.cancel"));
		
		valide.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	CTreeNode treeN= tree.getSelectedNode();			    	
				    	TFRP.setText(treeN.getRepositoryPath());
				    	TFLP.setText(treeN.getLocalPath());
				       	IPP.setVisible(false);
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	   	IPP.setVisible(true);
		    }
		}) ;
		
		JPanelProjectTopContainer.add(Box.createHorizontalStrut(80)) ;
		JPanelProjectTopContainer.add(valide);
		
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
	public static IProjectPreferences getIPP() {
		if (IPP==null){
			IPP = new IProjectPreferences(); 
		}
		
		return IPP;
	}
}
