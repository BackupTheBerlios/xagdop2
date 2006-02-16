/*
 * Acceuil.java
 *
 * Created on 16 f�vrier 2006, 16:41
 */

package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author  Drez
 */
public class IWelcome extends JFrame {

	private static final long serialVersionUID = 7319233017920832803L;
	/*Variables declaration*/
    private  JButton buttonNext;
    private  JLabel logoLabel;
    private  JPanel panel;
    private	 JLabel welcomeText;

	/** Creates new form Acceuil */
	
    public IWelcome() {
        initComponents();
    }
    
    private void initComponents() {
        /*Construction du panel */
    	GridBagConstraints gridBagConstraints;
        gridBagConstraints = new GridBagConstraints();
        panel = new  JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        
        /*Le texte de bienvenue */
        welcomeText = new JLabel();
        welcomeText.setText("<html>Bienvenue dans XAGDOP.<br> Vous devez configurer l'application pour pouvoir vous en servir. <br> Veuillez cliquer sur suivant pour acceder a la fenetre de configuration de l'application</html>");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets= new Insets(15,10,0,10);
        gridBagConstraints.anchor =  GridBagConstraints.CENTER;
        panel.add(welcomeText, gridBagConstraints);

        /*Le bouton suivant */
        buttonNext = new  JButton();
        buttonNext.setText("Suivant");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1; 
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets= new Insets(15,0,10,10);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonNext, gridBagConstraints);
       /*Action associée au bouton */
        buttonNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
       
        /*Le logo */
        logoLabel = new  JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP2.jpg")));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets= new Insets(15,0,0,0);
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        panel.add(logoLabel, gridBagConstraints);
        
        /*Creation de la fenetre */
        getContentPane().add(panel,  BorderLayout.CENTER);
        pack();
        setResizable(false) ;
    }
    
    private void buttonNextActionPerformed(ActionEvent evt) {
    			IConfLocalPath iclp = new IConfLocalPath();
    			iclp.setVisible(true);
    			this.setVisible(false);
    	    }
}
