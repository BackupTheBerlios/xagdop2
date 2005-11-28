package xagdop.Interface;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	protected JLabel JlabelRepositoryPath;
	protected JLabel JlabelLocalPath;
	
	protected GridBagConstraints gridBagConstraints = new GridBagConstraints();
	
	protected JPanel JPanelProjectTopContainer = new JPanel();
	protected IProjectTree tree = new IProjectTree();	
	protected JButton browse;
	protected JButton cancel;
	protected JButton valide;
	
	private IProjectPreferences(){
		init();
	}
	
	
	private void init(){
		getContentPane().setLayout(new GridBagLayout());
		localPath = new JTextField(30);
		//localPath = new JTextField(tree.getSelectedNode().getLocalPath());

		//Initialisation du chemin local
		JlabelLocalPath = new JLabel(Bundle.getText("iprojectpreference.label.localpath"));
		
		//Initialisation du chemin sur le serveur
		JlabelRepositoryPath = new JLabel(Bundle.getText("iprojectpreference.label.repositorypath"));
		repositoryPath = new JLabel(tree.getSelectedNode().getRepositoryPath()) ;
		
		// Initialisation de la popup
		setTitle(Bundle.getText("iprojectpreference.title"));
		setSize(500,300);
	
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanelProjectTopContainer.setLayout(new GridBagLayout());
		JPanelProjectTopContainer.setMinimumSize(new Dimension(296, 130));
        
		// Positionnement du local path
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        JPanelProjectTopContainer.add(JlabelLocalPath, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        JPanelProjectTopContainer.add(localPath, gridBagConstraints);

        // bouton parcourrir
        browse = new JButton(Bundle.getText("iprojectpreference.button.browse"));
       
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        JPanelProjectTopContainer.add(browse, gridBagConstraints);
        
        browse.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	 JFileChooser jc = new JFileChooser();
				    	/* int i = jc.showOpenDialog(this);
				    	 if(i != 1)
				    	 {
				    		 String filename = jc.getSelectedFile().getAbsolutePath() ;
				    	 }*/
				    }
				}) ;
        
        //Positionnement du repository path
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        JPanelProjectTopContainer.add(JlabelRepositoryPath, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
     //   gridBagConstraints.gridwidth = 2;
        JPanelProjectTopContainer.add(repositoryPath, gridBagConstraints);

        // creation des boutons de validation et d'annulation
        valide = new JButton(Bundle.getText("iprojectpreference.button.ok"));
		cancel = new JButton(Bundle.getText("iprojectpreference.button.cancel"));
		
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
		
		
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        JPanelProjectTopContainer.add(valide, gridBagConstraints);


        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        JPanelProjectTopContainer.add(cancel, gridBagConstraints);

        getContentPane().add(JPanelProjectTopContainer, new GridBagConstraints());

        pack();
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
