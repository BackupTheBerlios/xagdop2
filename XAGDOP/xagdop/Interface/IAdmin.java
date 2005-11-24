package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IAdmin extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048783116748619019L;
	private static IAdmin IA = null;
	
	private JCheckBox AdminCheck = new JCheckBox();
    private JLabel AdminLabel = new JLabel();
    private JButton ButtonApply = new JButton();
    private JButton ButtonCancel = new JButton();
    private JButton ButtonOK = new JButton();
    private JButton ButtonCreateUser = new JButton();
    private JCheckBox PManagerCheck = new JCheckBox();
    private JLabel PManagerLabel = new JLabel();
    private JLabel UserLabel = new JLabel();
    private JComboBox UserListCombo = new JComboBox();
    private JPanel newPanel = new JPanel();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();

    
    
	private IAdmin(){
		init();
	}
	
	
	private void init(){
        getContentPane().setLayout(new GridBagLayout());

       
        newPanel.setLayout(new GridBagLayout());

        newPanel.setMinimumSize(new Dimension(296, 130));
        UserLabel.setText("Selectionner l'utilisateur");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        newPanel.add(UserLabel, gridBagConstraints);
        UserLabel.getAccessibleContext().setAccessibleName("Choisir l'utilisateur");

        
        UserListCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //UserListComboActionPerformed(evt);
            }
        });

       
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        newPanel.add(UserListCombo, gridBagConstraints);

        AdminCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //AdminCheckActionPerformed(evt);
            }
        });

        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        newPanel.add(AdminCheck, gridBagConstraints);

       
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        newPanel.add(PManagerCheck, gridBagConstraints);

        AdminLabel.setText("Administrateur");
        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        newPanel.add(AdminLabel, gridBagConstraints);

        PManagerLabel.setText("Chef de Projet");
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        newPanel.add(PManagerLabel, gridBagConstraints);

        ButtonOK.setText("Ok");
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               // ButtonOKActionPerformed(evt);
            }
        });

        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonOK, gridBagConstraints);

        ButtonCancel.setText("Annuler");
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //ButtonCancelActionPerformed(evt);
            }
        });

        
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonCancel, gridBagConstraints);

        ButtonApply.setText("Appliquer");
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonApply, gridBagConstraints);

        ButtonCreateUser.setText("Creer Nouvel User");
        newPanel.add(ButtonCreateUser, new GridBagConstraints());

        getContentPane().add(newPanel, new GridBagConstraints());

        pack();
		setTitle("Administration");
		//setSize(300, 200);
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IAdmin getIA() {
		if (IA==null){
			IA = new IAdmin(); 
		}
		
		return IA;
	}
}