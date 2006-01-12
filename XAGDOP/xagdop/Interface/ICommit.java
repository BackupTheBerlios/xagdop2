package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CTreeNode;
import xagdop.Svn.SvnCommit;
import xagdop.ressources.Bundle;


public class ICommit extends JDialog {

	private static final long serialVersionUID = 3235581234662502451L;
	protected JTextArea JTAComment; 
	protected JLabel JlabelComment;
	protected GridBagConstraints gridBagConstraints = new GridBagConstraints();
	protected JPanel JPanelProjectTopContainer = new JPanel();
	protected JButton cancel;
	protected JButton valide;
	protected CTreeNode currentNode;
	
	public ICommit(CTreeNode current){
		//System.out.println(current.getLocalPath());
		currentNode = current;
		init();
		
	}
	private void init(){
		getContentPane().setLayout(new GridBagLayout());
		JTAComment = new JTextArea(10,20);

		JlabelComment = new JLabel(Bundle.getText("icommit.label.comment"));
		
		// Initialisation de la popup
		setTitle(Bundle.getText("icommit.title"));
		setSize(500,300);
	
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanelProjectTopContainer.setLayout(new GridBagLayout());
		JPanelProjectTopContainer.setMinimumSize(new Dimension(296, 130));
        
		//Affichage du label
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        JPanelProjectTopContainer.add(JlabelComment, gridBagConstraints);
       
        //Affichage des commentaires
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        JPanelProjectTopContainer.add(JTAComment, gridBagConstraints);

        // creation des boutons de validation et d'annulation
        valide = new JButton(Bundle.getText("icommit.button.ok"));
		cancel = new JButton(Bundle.getText("icommit.button.cancel"));
		
		valide.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
						SvnCommit svnC = null;
						try {
							svnC = new SvnCommit();
							svnC.commit(currentNode,JTAComment.getText());
						} catch (SVNException e1) {
							JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
							e1.printStackTrace();
						}

						dispose();
				    }
				}) ;
		
		cancel.addActionListener(new ActionListener()
				{
		    public void actionPerformed(ActionEvent e)
		    {
		    	dispose();   	
		    }
		}) ;
		
		
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        JPanelProjectTopContainer.add(valide, gridBagConstraints);


        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        JPanelProjectTopContainer.add(cancel, gridBagConstraints);

        getContentPane().add(JPanelProjectTopContainer, new GridBagConstraints());

        pack();
        setLocation(200,200) ;
        setModal(true);
        setVisible(true);
	
	}
}
