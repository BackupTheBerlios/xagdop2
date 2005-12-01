package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class IAffect extends JFrame
{
	
	
	 // Variables declaration - do not modify
    private JCheckBox AnalystCheck = new JCheckBox();
    private JCheckBox ArchiCheck = new JCheckBox();
    private JButton CancelButton = new JButton();
    private JCheckBox ChefCheck = new JCheckBox();
    private JLabel EnterLabel = new JLabel();
    private JTextField LoginUser = new JTextField();
    private JButton OkButton = new JButton();
    private JCheckBox RedacteurCheck = new JCheckBox();
    private JPanel jPanel1 = new JPanel();
    private JSeparator jSeparator1 = new JSeparator();
    private JSeparator jSeparator2 = new JSeparator();
    // End of variables declaration
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static IAffect IA;
	
	private IAffect(){
		init();
	}
	/**
	 * 
	 * 
	 */
	public void init(){
		
		
		GridBagConstraints gridBagConstraints;


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setLayout(new GridBagLayout());

        OkButton.setText("Ok");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //OkButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        jPanel1.add(OkButton, gridBagConstraints);

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //CancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        jPanel1.add(CancelButton, gridBagConstraints);

        AnalystCheck.setText("Analyst");
             
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(AnalystCheck, gridBagConstraints);

        ArchiCheck.setText("Archi");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(ArchiCheck, gridBagConstraints);

        RedacteurCheck.setText("Redacteur");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(RedacteurCheck, gridBagConstraints);

        ChefCheck.setText("Chef");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        jPanel1.add(ChefCheck, gridBagConstraints);

        LoginUser.setColumns(10);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel1.add(LoginUser, gridBagConstraints);

        EnterLabel.setText("Entrez un login");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel1.add(EnterLabel, gridBagConstraints);


        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jSeparator1, gridBagConstraints);

        jSeparator2.setOrientation(SwingConstants.VERTICAL);
        jSeparator2.setMinimumSize(new Dimension(0, 10));
        jSeparator2.setPreferredSize(new Dimension(2, 0));

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jSeparator2, gridBagConstraints);

       // getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
		
	}
	
	
	
	/** 
	* @return Returns the singleton.
	 */
	public static IAffect getIA() {
		if (IA==null){
			IA = new IAffect(); 
		}
		
		return IA;
	}
}
	