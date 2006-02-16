/*
 * IconfLocalPath.java
 *
 * Created on 16 février 2006, 17:00
 */

package xagdop.Interface;

import java.awt.BorderLayout;
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
import javax.swing.WindowConstants;

/**
 *
 * @author  Drez
 */
public class IConfLocalPath extends  JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables declaration
    private  JLabel LocalPathLabel;
    private  JButton buttonNext;
    private  JButton buttonPrevious;
    private  JLabel helpLocalPathLabel;
    private  JPanel jPanel1;
    private  JTextField jTextField1;
    // End of variables declaration
    
    
    
    /** Creates new form IconfLocalPath */
    public IConfLocalPath() {
        initComponents();
    }
    
   
    private void initComponents() {
         GridBagConstraints gridBagConstraints;

        jPanel1 = new  JPanel();
        jTextField1 = new  JTextField();
        helpLocalPathLabel = new  JLabel();
        LocalPathLabel = new  JLabel();
        buttonPrevious = new  JButton();
        buttonNext = new  JButton();

        
        jPanel1.setLayout(new  GridBagLayout());
        setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE);
        jTextField1.setColumns(15);
        gridBagConstraints = new  GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new  Insets(4, 4, 4, 4);
        jPanel1.add(jTextField1, gridBagConstraints);

        helpLocalPathLabel.setText("Il faut que vous donniez l'endroit o\u00f9 se situeront vos fichiers de projets sur l'ordinateur local.");
        gridBagConstraints = new  GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new  Insets(4, 4, 4, 4);
        jPanel1.add(helpLocalPathLabel, gridBagConstraints);

        LocalPathLabel.setText("Chemin Local :");
        gridBagConstraints = new  GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new  Insets(4, 4, 4, 4);
        jPanel1.add(LocalPathLabel, gridBagConstraints);

        buttonPrevious.setText("Previous");
        buttonPrevious.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });

        gridBagConstraints = new  GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new  Insets(4, 4, 4, 4);
        jPanel1.add(buttonPrevious, gridBagConstraints);

        buttonNext.setText("Next");
        buttonNext.addActionListener(new  ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        gridBagConstraints = new  GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new  Insets(4, 4, 4, 4);
        jPanel1.add(buttonNext, gridBagConstraints);

        getContentPane().add(jPanel1,  BorderLayout.WEST);

        pack();
        setSize(600,600);
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
