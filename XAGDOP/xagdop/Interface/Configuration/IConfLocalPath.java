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
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import xagdop.Interface.XAGDOP;
import xagdop.Interface.Preferences.SmartChooser;
import xagdop.ressources.Bundle;



public class IConfLocalPath extends  JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Variables declaration*/
    private  JLabel LocalPathLabel;
    private  JButton buttonNext;
    private  JButton buttonPrevious;
    private  JButton buttonBrowse;
    private  JLabel helpLocalPathLabel;
    private  JLabel logoLabel;
    private  JPanel panel;
    private  JTextField jTextField1;
    private  String defaultLocalPath;
    protected static String mDirectory = "";
    /** Creates new form IconfLocalPath */
    public IConfLocalPath(String defaultLocalPath) {
        initComponents();
        this.defaultLocalPath = defaultLocalPath;
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
        gridBagConstraints.insets= new Insets(0,10,0,30);
        gridBagConstraints.fill = GridBagConstraints.CENTER;
        panel.add(logoLabel, gridBagConstraints);
    	
    	
        /* Phrase d'introduction*/
        helpLocalPathLabel = new JLabel();
        helpLocalPathLabel.setText(Bundle.getText("iconflocalpath.localpath"));
        helpLocalPathLabel.setFont(new Font(null,0,14));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new Insets(15,10,20,30);
        panel.add(helpLocalPathLabel, gridBagConstraints);
        
        /*Affichage du label du chemin */
        LocalPathLabel = new  JLabel();
        LocalPathLabel.setText(Bundle.getText("iconflocalpath.label.chemin"));
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new  Insets(15,10,40,5);
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        panel.add(LocalPathLabel, gridBagConstraints);

        /*Affichage du champs du chemin */     
        jTextField1 = new  JTextField();
        jTextField1.setColumns(15); 
        jTextField1.setText(this.defaultLocalPath);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new  Insets(15,0,40,10);
        gridBagConstraints.anchor =  GridBagConstraints.WEST;
        panel.add(jTextField1, gridBagConstraints);
        
        /*Bouton parcourir*/ 
        buttonBrowse = new  JButton(Bundle.getText("button.browse"));
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight =1 ;
        gridBagConstraints.insets = new  Insets(15,0,40,10);
        gridBagConstraints.anchor =  GridBagConstraints.SOUTHEAST;
        panel.add(buttonBrowse, gridBagConstraints);
        buttonBrowse.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                buttonBrowseActionPerformed(evt);
            }

			
        });
        
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
        setSize(600,300);
        setLocation(200,200);
        setResizable(false) ;

    }


    private void buttonNextActionPerformed( ActionEvent evt) {
		//Appel au parser des prferences
    	
//		CPreferencies.setLocalPath(jTextField1.getName());
		//On fais passer le local path
    	IConfServer iclp = new IConfServer(jTextField1.getText());
		iclp.setVisible(true);
		this.setVisible(false);
    }

    private void buttonPreviousActionPerformed( ActionEvent evt) {
    	IWelcome iw = new IWelcome();
    	iw.setVisible(true);
    	this.setVisible(false);
    }
    
    private void buttonBrowseActionPerformed(ActionEvent evt) {
    
    		SmartChooser chooser = SmartChooser.getChooser();
    		chooser.setAcceptAllFileFilterUsed(true);
    		System.out.println("PropertyDirectory"+mDirectory);
    		chooser.setDirectory(mDirectory);
    		chooser.setDialogTitle(Bundle.getText("choosePath")); 
    		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    		
    		chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
    		
    		int result = chooser.showDialog (this, Bundle.getText("open"));
    		if (result == JFileChooser.APPROVE_OPTION )
    		{
    		    if(chooser.getSelectedFile().exists() && chooser.getSelectedFile().isDirectory())
    			{
    		    	mDirectory = chooser.getSelectedFile().getAbsolutePath();
    		    	jTextField1.setText(mDirectory);
    			}
    			else{
    				return;
    		}
    		}
    	
	}

    
}
