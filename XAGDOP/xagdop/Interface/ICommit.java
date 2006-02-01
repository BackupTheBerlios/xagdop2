package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;


import xagdop.Controleur.CCommit;
import xagdop.Controleur.CTreeNode;
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
		JTAComment.setLineWrap(true);

		JlabelComment = new JLabel(Bundle.getText("icommit.label.comment"));
		
		// Initialisation de la popup
		setTitle(Bundle.getText("icommit.title"));
		setSize(330,250);
	
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanelProjectTopContainer.setLayout(new GridBagLayout());
		JPanelProjectTopContainer.setMinimumSize(new Dimension(296, 130));

        //Affichage du champ de saisie des commentaires
        //gridBagConstraints.gridx = 0;
        //gridBagConstraints.gridy = 1;
        //gridBagConstraints.gridwidth = 2;
        this.donnerContrainte(gridBagConstraints,0,2,2,1,100,100,GridBagConstraints.NONE);
        JPanelProjectTopContainer.add(new JScrollPane(JTAComment), gridBagConstraints);

        // creation des boutons de validation et d'annulation
        valide = new JButton(Bundle.getText("icommit.button.ok"));
		cancel = new JButton(Bundle.getText("icommit.button.cancel"));
		
		valide.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    		CCommit CC = new CCommit(currentNode);

				    		CC.commitFile(currentNode,JTAComment.getText());

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
		
		
        //gridBagConstraints.gridx = 0;
        //gridBagConstraints.gridy = 2;
        //gridBagConstraints.gridwidth = 1;
        this.donnerContrainte(gridBagConstraints,0,3,1,1,50,50,GridBagConstraints.NONE);
        JPanelProjectTopContainer.add(valide, gridBagConstraints);


        //gridBagConstraints.gridx = 1;
       // gridBagConstraints.gridy = 2;
        //gridBagConstraints.gridwidth = 1;
        this.donnerContrainte(gridBagConstraints,1,3,1,1,50,50,GridBagConstraints.NONE);
        JPanelProjectTopContainer.add(cancel, gridBagConstraints);

//      Affichage du label
        //gridBagConstraints.gridx = 0;
        //gridBagConstraints.gridy = 0;
        //gridBagConstraints.gridwidth = 2;
        this.donnerContrainte(gridBagConstraints,0,0,2,1,100,100,GridBagConstraints.NONE);
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        JPanelProjectTopContainer.add(JlabelComment, gridBagConstraints);
        
        getContentPane().add(JPanelProjectTopContainer, new GridBagConstraints());

        //pack();
        setLocation(200,200) ;
        setModal(true);
        setVisible(true);
	
	}
	
	/** Panel de message de fin de transaction
     * @param gbc <CODE>GridBagConstraints</CODE> represente la contrainte qui va prendre les
     * valeurs specifiees
     * @param gx <CODE>int</CODE> represente la colonne dans laquelle l'?l?ment va etre place
     * @param gy <CODE>int</CODE> represente la ligne dans laquelle l'?l?ment va etre place
     * @param gw <CODE>int</CODE> represente le nombre de colonnes sur lesquelles l'?l?ment va etre place
     * @param gh <CODE>int</CODE> represente le nombre de lignes sur lesquelles l'?l?ment va etre place
     * @param wx <CODE>int</CODE> poucentage de place utilise dans sa colonne
     * @param wy <CODE>int</CODE> poucentage de place utilise dans sa ligne
     */
	public void donnerContrainte(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy)
    {
		gbc.gridx=gx;
		gbc.gridy=gy;
		gbc.gridwidth=gw;
		gbc.gridheight=gh;
		gbc.weightx=wx;
		gbc.weighty=wy;
		gbc.fill=GridBagConstraints.NONE;
    }
    
    /** Panel de message de fin de transaction
     * @param gbc <CODE>GridBagConstraints</CODE> represente la contrainte qui va prendre les
     * valeurs specifiees
     * @param gx <CODE>int</CODE> represente la colonne dans laquelle l'?l?ment va etre place
     * @param gy <CODE>int</CODE> represente la ligne dans laquelle l'?l?ment va etre place
     * @param gw <CODE>int</CODE> represente le nombre de colonnes sur lesquelles l'?l?ment va etre place
     * @param gh <CODE>int</CODE> represente le nombre de lignes sur lesquelles l'?l?ment va etre place
     * @param wx <CODE>int</CODE> poucentage de place utilise dans sa colonne
     * @param wy <CODE>int</CODE> poucentage de place utilise dans sa ligne
     * @param constraint <CODE>int</CODE> contrainte de redimensionnement
     */    
    public void donnerContrainte(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy, int constraint)
    {
    	gbc.gridx=gx;
    	gbc.gridy=gy;
    	gbc.gridwidth=gw;
    	gbc.gridheight=gh;
    	gbc.weightx=wx;
    	gbc.weighty=wy;
    	gbc.fill=constraint;
    }
}
