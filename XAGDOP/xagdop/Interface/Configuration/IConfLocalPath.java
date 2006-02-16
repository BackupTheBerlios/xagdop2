/*
 * IconfLocalPath.java
 *
 * Created on 16 f�vrier 2006, 17:00
 */

package xagdop.Interface.Configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author  Drez
 */
public class IConfLocalPath extends  JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Variables declaration*/
    private  JLabel LocalPathLabel;
    private  JButton buttonNext;
    private  JButton buttonPrevious;
    private  JLabel helpLocalPathLabel;
    private  JPanel panel;
    private  JTextField jTextField1;
  
    /** Creates new form IconfLocalPath */
    public IConfLocalPath() {
        initComponents();
    }
    
   
    private void initComponents() {
        
    	GridBagConstraints gridBagConstraints;
    	gridBagConstraints = new  GridBagConstraints();
        
    	/* Creation du panel*/
    	panel = new  JPanel();
    	panel.setLayout(new  GridBagLayout());
    	panel.setBackground(Color.white);

        /* Phrase d'introduction*/
        helpLocalPathLabel = new JLabel();
        helpLocalPathLabel.setText("<html>Il faut que vous donniez l'endroit o&ugrave; se situeront <br> vos fichiers de projets sur l'ordinateur local.</html>");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new Insets(15,10,10,10);
        panel.add(helpLocalPathLabel, gridBagConstraints);
        
        /*Affichage du label du chemin */
        LocalPathLabel = new  JLabel();
        LocalPathLabel.setText("Chemin Local :");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new  Insets(15,10,10,30);
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        panel.add(LocalPathLabel, gridBagConstraints);

        /*Affichage du champs du chemin */     
        jTextField1 = new  JTextField();
        jTextField1.setColumns(15); 
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new  Insets(15,0,10,10);
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        panel.add(jTextField1, gridBagConstraints);

        /*Bouton precedent*/   
        buttonPrevious = new  JButton();
        buttonPrevious.setText("Previous");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new  Insets(0,0,10,0);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonPrevious, gridBagConstraints);
        buttonPrevious.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });
        
        /*Bouton suivant*/ 
        buttonNext = new  JButton();
        buttonNext.setText("Next");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new  Insets(15,0,10,10);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonNext, gridBagConstraints);
        buttonNext.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
        
        /*Creation de la fenetre*/ 
        getContentPane().add(panel,BorderLayout.WEST);
        pack();

    }


    private void buttonNextActionPerformed( ActionEvent evt) {
		IConfServer iclp = new IConfServer();
		iclp.setVisible(true);
		this.setVisible(false);
    }

    private void buttonPreviousActionPerformed( ActionEvent evt) {
    		IWelcome iw = new IWelcome();
    		iw.setVisible(true);
    		this.setVisible(false);
    }
    


    
}
