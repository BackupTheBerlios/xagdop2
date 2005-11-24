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
import javax.swing.WindowConstants;

public class ITeamManagement extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1080162447493236178L;
	
	private static ITeamManagement IT = null;

	private JButton ButtonOK=new JButton();
    private JButton ButtonCancel=new JButton();
    private JCheckBox AnalystCheck=new JCheckBox();
    private JCheckBox ArchitectCheck = new JCheckBox();
    private JCheckBox RedacterCheck = new JCheckBox();
    private JComboBox UserListCombo = new JComboBox();
    private JLabel AnalystLabel = new JLabel();
    private JLabel ArchitectLabel = new JLabel();
    private JLabel RedacterLabel = new JLabel();
    private JLabel UserLabel = new JLabel();
    private JPanel newPanel = new JPanel();
	GridBagConstraints gridBagConstraints;
	
	
	private ITeamManagement(){
		init();
	}
	
	
	private void init(){ 

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newPanel.setLayout(new GridBagLayout());

        newPanel.setMinimumSize(new Dimension(296, 130));
        UserLabel.setText("Selectionner l'utilisateur");
        
        UserLabel.setText("Selectionner l'utilisateur");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        newPanel.add(UserLabel, gridBagConstraints);
        UserLabel.getAccessibleContext().setAccessibleName("Choisir l'utilisateur");

        UserListCombo.setName("null");
        UserListCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //UserListComboActionPerformed(evt);
            }
        });

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        newPanel.add(UserListCombo, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        newPanel.add(AnalystCheck, gridBagConstraints);

       

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        newPanel.add(ArchitectCheck, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        newPanel.add(RedacterCheck, gridBagConstraints);

        ArchitectLabel.setText("Architecte");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        newPanel.add(ArchitectLabel, gridBagConstraints);

        RedacterLabel.setText("Redacteur");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        newPanel.add(RedacterLabel, gridBagConstraints);

        AnalystLabel.setText("Analyste");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        newPanel.add(AnalystLabel, gridBagConstraints);

        ButtonOK.setText("Ok");
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //ButtonOKActionPerformed(evt);
            }
        });

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonOK, gridBagConstraints);

        ButtonCancel.setText("Annuler");
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //ButtonCancelActionPerformed(evt);
            }
        });

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        newPanel.add(ButtonCancel, gridBagConstraints);

        getContentPane().add(newPanel, new GridBagConstraints());

        pack();
        
        
        
        
        
        
        
        
        
        
        
        
        
		setTitle("Affectation de l'equipe");
		//setSize(300, 200);
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static ITeamManagement getIT() {
		if (IT==null){
			IT = new ITeamManagement(); 
		}
		
		return IT;
	}
}