package xagdop.Interface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CComposantCreate;
import xagdop.Controleur.CTree;
import xagdop.Controleur.CTreeNode;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;


public class IComposantCreate extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6458234244148394589L;

	private JPanel panel;
	private JLabel userIDLabel;
	private JTextField userID;
	private String _nomProjet;
	
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	int i=0;
	

	public IComposantCreate(String nomProjet){
		init();
		this._nomProjet = nomProjet;
	}
	
	
	private void init(){
		
		
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
				try {
					CComposantCreate.composantCreate(userID.getText(),_nomProjet);
					CTree ct = ((CTree)((IProjectTree)XAGDOP.getInstance().getTree()).getModel()); 
					try {
						ct.refreshFromLocal((CTreeNode) ct.getRoot());
					} catch (SVNException e1) {
							ErrorManager.getInstance().setErrTitle("Erreur de Raffraichissement");
							ErrorManager.getInstance().setErrMsg("Erreur lors du raffraichissement du repertoire local");
							ErrorManager.getInstance().display();
					}
					
					dispose();
					
				} catch (IOException e1) {
					ErrorManager.getInstance().display();
				}
			}
		}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	dispose();
		    	
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
	
}