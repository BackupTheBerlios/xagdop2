/*
 * Acceuil.java
 *
 * Created on 16 février 2006, 16:41
 */

package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

/**
 *
 * @author  Drez
 */
public class IWelcome extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7319233017920832803L;
	 // Variables declaration
    private  JButton buttonNext;
   
    private  JLabel logoLabel;
    private  JPanel jPanel1;
    
    private	 JTextPane welcomeText;
    // End of variables declaration
	
	/** Creates new form Acceuil */
	
	
	
	
    public IWelcome() {
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jPanel1 = new  JPanel();
        logoLabel = new  JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP3.jpg")));
        
        
        buttonNext = new  JButton();

        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setLayout(new GridBagLayout());

        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        jPanel1.add(logoLabel, gridBagConstraints);

        welcomeText = new JTextPane();
        welcomeText.setText("Bienvenue dans XAGDOP. Vous devez configurer l'application pour pouvoir vous en servir. \n Veuillez cliquer sur suivant pour acceder a la fenetre de configuration de l'application");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(welcomeText, gridBagConstraints);

        
       

        buttonNext.setText("Suivant");
       
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        jPanel1.add(buttonNext, gridBagConstraints);
        buttonNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        getContentPane().add(jPanel1,  BorderLayout.CENTER);

        pack();
        setSize(600,600);
    }
    
    private void buttonNextActionPerformed(ActionEvent evt) {
    			IConfLocalPath iclp = new IConfLocalPath();
    			iclp.setVisible(true);
    			this.setVisible(false);
    	    }

    
}
