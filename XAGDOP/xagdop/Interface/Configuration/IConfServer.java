/*
 * IconfLocalPath.java
 *
 * Created on 16 f???vrier 2006, 17:00
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
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CPreferencies;
import xagdop.Interface.IIdentification;
import xagdop.Interface.XAGDOP;
import xagdop.Parser.PreferenciesParser;
import xagdop.Svn.SvnConnect;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;


public class IConfServer extends  JFrame {

	private static final long serialVersionUID = 1L;
	/* Variables declaration*/
    private  JLabel LocalPathLabel;
    private  JButton buttonNext;
    private  JButton buttonPrevious;
    private  JLabel helpLocalPathLabel;
    private  JLabel logoLabel;
    private  JPanel panel;
    private  JTextField jTextField1;
    private	 String localPath;
    
    
    /** Creates new form IconfLocalPath */
    public IConfServer(String localPath) {
        initComponents();
        this.localPath = localPath;
    }
    
   
    private void initComponents() {
    	GridBagConstraints gridBagConstraints;
    	gridBagConstraints = new  GridBagConstraints();
        
    	/* Creation du panel*/
    	panel = new  JPanel();
    	panel.setLayout(new  GridBagLayout());
    	panel.setBackground(Color.white);

  
    	/*Le logo */
        logoLabel = new  JLabel(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/LogoXAGDOP10.jpg")));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =3 ;
        gridBagConstraints.insets= new Insets(0,10,0,70);
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        panel.add(logoLabel, gridBagConstraints);
    	
    	
        /* Phrase d'introduction*/
        helpLocalPathLabel = new JLabel();
        helpLocalPathLabel.setText(Bundle.getText("iconfserver.adresse"));
        helpLocalPathLabel.setFont(new Font(null,0,14));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new Insets(15,10,20,30);
        panel.add(helpLocalPathLabel, gridBagConstraints);
        
        /*Affichage du label du chemin */
        LocalPathLabel = new  JLabel();
    	LocalPathLabel.setText(Bundle.getText("iconfserver.label.serveur"));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new  Insets(15,10,40,30);
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        panel.add(LocalPathLabel, gridBagConstraints);

        /*Affichage du champs du chemin */     
        jTextField1 = new  JTextField("svn://");
        jTextField1.setColumns(20); 
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new  Insets(15,0,40,10);
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        panel.add(jTextField1, gridBagConstraints);

        /*Bouton precedent*/   
        buttonPrevious = new  JButton();
        buttonPrevious.setText(Bundle.getText("button.previous"));
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
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
        buttonNext.setText(Bundle.getText("button.next"));
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new  Insets(15,0,10,10);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonNext, gridBagConstraints);
        buttonNext.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
        
        /*Creation de la fenetre */
        getContentPane().add(panel,  BorderLayout.CENTER);
        setSize(620,320);
        setLocation(200,200);
        setTitle(Bundle.getText("iconfserver.title"));
        setResizable(false) ;

      
    }


    private void buttonNextActionPerformed( ActionEvent evt) {

    	//Creation du repertoire contenant les preferences
    	File f1 = new File(System.getProperty("user.home")+File.separator+".xagdop");
		f1.mkdir();
		try {
			if(jTextField1.getText().endsWith("/"))
				SvnConnect.getInstance(jTextField1.getText(),"XAGDOP","blabla");
			else
				SvnConnect.getInstance(jTextField1.getText()+"/","XAGDOP","blabla");
			PreferenciesParser.createPreferencies();
	    	//Appel au parser des prferences
	    	CPreferencies.setServerPath(jTextField1.getText());
	    	CPreferencies.setLocalPath(this.localPath);
			//Fermeture de la fenetre, ouverture de la fenetre d'identification
			IIdentification.getInstance();
			this.dispose();
		} catch (SVNException e) {
			ErrorManager.getInstance().display();
		}
		
		
    }

    private void buttonPreviousActionPerformed( ActionEvent evt) {
    	IConfLocalPath iclp = new IConfLocalPath(this.localPath);
    	iclp.setVisible(true);
    	this.setVisible(false);
    }
    


    
}
