/*
 * Acceuil.java
 *
 * Created on 16 f???vrier 2006, 16:41
 */

package xagdop.Interface.Configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import xagdop.Interface.XAGDOP;
import xagdop.ressources.Bundle;

public class IWelcome extends JFrame {

	private static final long serialVersionUID = 7319233017920832803L;
	/*Variables declaration*/
    private  JButton buttonNext;
    private  JButton buttonCancel;
    private  JLabel logoLabel;
    private  JPanel panel;
    private	 JLabel welcomeText;

	/** Creates new form Acceuil */
	
    public IWelcome() {
        initComponents();
    }
    
    private void initComponents() {
    	Bundle.setCurrentLocale(Locale.FRANCE);
        /*Construction du panel */
    	GridBagConstraints gridBagConstraints;
        gridBagConstraints = new GridBagConstraints();
        panel = new  JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        
        /*Le logo */
        logoLabel = new  JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP10.jpg")));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =2 ;
        gridBagConstraints.insets= new Insets(0,10,0,100);
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        panel.add(logoLabel, gridBagConstraints);
        
        
        /*Le texte de bienvenue */
        welcomeText = new JLabel();
        welcomeText.setText(Bundle.getText("iwelcome.welcome"));
        welcomeText.setFont(new Font(null,0,14));
		gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets= new Insets(0,0,0,30);
        gridBagConstraints.anchor =  GridBagConstraints.CENTER;
        panel.add(welcomeText, gridBagConstraints);

        /*Le bouton suivant */
        buttonNext = new  JButton();
        buttonNext.setText(Bundle.getText("button.next"));
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1; 
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets= new Insets(70,0,5,5);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonNext, gridBagConstraints);
       /*Action associee au bouton */
        buttonNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
       
        /*Le bouton annuler */
        buttonCancel = new JButton(Bundle.getText("button.cancel"));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1; 
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets= new Insets(70,70,5,10);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonCancel, gridBagConstraints);
       /*Action associee au bouton */
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.exit(0) ; 
            }
        });
        
        /*Creation de la fenetre */
        getContentPane().add(panel,  BorderLayout.CENTER);
        setSize(620,320);
        setLocation(200,200);
        setTitle(Bundle.getText("iwelcome.title"));
        setResizable(false) ;
    }
    
    private void buttonNextActionPerformed(ActionEvent evt) {
    			IConfLocalPath iclp = new IConfLocalPath("");
    			iclp.setVisible(true);
    			this.setVisible(false);
    	    }
}
