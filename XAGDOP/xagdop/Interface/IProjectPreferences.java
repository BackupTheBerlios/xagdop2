package xagdop.Interface;



import java.awt.BorderLayout;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.WindowConstants;

import xagdop.Controleur.CProject;
import xagdop.Controleur.CTreeNode;
import xagdop.ressources.Bundle;

public class IProjectPreferences extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4722728578653189570L;
	//private CProject cpro = new CProject();
	
	/**
	 * 
	 */
	
	private static IProjectPreferences IPP = null;
	protected JTextField localPath; 
	protected JLabel repositoryPath;
	protected JLabel JlabelProjectRepositoryPath;
	protected JLabel JlabelProjectLocalPath;
	protected JPanel JPanelProjectTopContainer;
	protected IProjectTree tree = new IProjectTree();	
	
	private IProjectPreferences(){
		init();
	}
	
	
	private void init(){
		localPath = new JTextField(tree.getSelectedNode().getLocalPath());

		setTitle(Bundle.getText("iprojectpreference.title"));
		setSize(500,200);
		
		JPanelProjectTopContainer = new JPanel() ;
		JPanelProjectTopContainer.setLayout(new GridLayout(3,2)) ;
		JPanelProjectTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
	
		//Initialisation du chemin local
		JlabelProjectLocalPath = new JLabel(Bundle.getText("iprojectpreference.label.localpath"));
		JPanelProjectTopContainer.add(JlabelProjectLocalPath);
		JPanelProjectTopContainer.add(localPath);
		
		//Initialisation du chemin sur le serveur
		JlabelProjectRepositoryPath = new JLabel(Bundle.getText("iprojectpreference.label.repositorypath"));
		repositoryPath = new JLabel(tree.getSelectedNode().getRepositoryPath()) ;
		JPanelProjectTopContainer.add(JlabelProjectRepositoryPath);
		JPanelProjectTopContainer.add(repositoryPath);
		
		
		// Creation des 2 boutons
		JButton valide = new JButton(Bundle.getText("iprojectpreference.button.ok"));
		JButton cancel = new JButton(Bundle.getText("iprojectpreference.button.cancel"));
		
		valide.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	CTreeNode treeN= tree.getSelectedNode();			    	
				    	repositoryPath.setText(treeN.getRepositoryPath());
				    	localPath.setText(treeN.getLocalPath());
				       	IPP.setVisible(false);
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	IPP.dispose();   	
		    }
		}) ;

		JPanelProjectTopContainer.add(valide);
		JPanelProjectTopContainer.add(cancel);
		
		//Creation de la fenetre
		setResizable(true) ;

		setDefaultCloseOperation (WindowConstants.HIDE_ON_CLOSE) ;
		
		JPanelProjectTopContainer.setBorder(BorderFactory.createEtchedBorder()) ;
		getContentPane().add(JPanelProjectTopContainer , BorderLayout.CENTER) ;
		
	
		setLocation(200,200) ;


		 
         
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
