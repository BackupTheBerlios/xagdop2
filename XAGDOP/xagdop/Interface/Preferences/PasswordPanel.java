package xagdop.Interface.Preferences;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import xagdop.ressources.Bundle;

public class PasswordPanel extends PreferencePanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labFormerPass;
	private JLabel labNewPass1;
	private JLabel labNewPass2;
	private static JPasswordField pfFormerPass;
	private static JPasswordField pfNewPass1;
	private static JPasswordField pfNewPass2;
		
	public PasswordPanel(){
		initUI();
	}
	
	protected void initUI() 
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(getPanelDescription(Bundle.getText("ipreferences.password.title")), BorderLayout.NORTH);
		this.add(getPanelCentral(), BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));
	}
	
	private JPanel getPanelCentral()
	{ 
		JPanel mPanelCentral = new JPanel();
    	mPanelCentral.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	c.fill = GridBagConstraints.HORIZONTAL;
    	
//    	create the border
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder(loweredetched, Bundle.getText("ipreferences.password.grpBox"));
	
    	labFormerPass = new JLabel(Bundle.getText("ipreferences.password.formerPassword"));
    	labNewPass1 = new JLabel(Bundle.getText("ipreferences.password.newPassword"));
    	labNewPass2 = new JLabel(Bundle.getText("ipreferences.password.newPasswordConf"));
    	
    	pfFormerPass = new JPasswordField(20);
    	pfFormerPass.addKeyListener(new myKeyAdapter());
    	pfNewPass1 = new JPasswordField(20);
    	pfNewPass1.addKeyListener(new myKeyAdapter());
    	pfNewPass2 = new JPasswordField(20);
    	pfNewPass2.addKeyListener(new myKeyAdapter());
    	
    	c.insets = new Insets(10,5,30,5);
    	c.gridx = 0;
    	c.gridy = 0;
    	mPanelCentral.add(labFormerPass, c);
    	
    	c.gridx = 1;
    	c.gridy = 0;
    	mPanelCentral.add(pfFormerPass, c);
    	
    	c.insets = new Insets(10,5,5,5);
    	c.gridx = 0;
    	c.gridy = 1;
    	mPanelCentral.add(labNewPass1, c);
    	
    	c.gridx = 1;
    	c.gridy = 1;
    	mPanelCentral.add(pfNewPass1, c);
    	
    	c.gridx = 0;
    	c.gridy = 2;
    	mPanelCentral.add(labNewPass2, c);
    	
    	c.gridx = 1;
    	c.gridy = 2;
    	mPanelCentral.add(pfNewPass2, c);
    	
    	c.gridx = 0;
    	c.gridy = 3;
    	c.weighty = 180;
    	c.weightx = 3;
    	mPanelCentral.add(Box.createVerticalGlue(), c);
    	mPanelCentral.setBorder(titleStyle);
    	
		return mPanelCentral;
	}
	
	private class myKeyAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			if (pfNewPass1.getPassword().length != 0
					&& pfNewPass2.getPassword().length != 0
					&& pfFormerPass.getPassword().length != 0)
			{
				IPreferences.prefHasChanged(IPreferences.PASSWORD_REF, IPreferences.ADD);
			}
			else
				IPreferences.prefHasChanged(IPreferences.PASSWORD_REF, IPreferences.DEL);
		}
	}
	
	public static boolean isPasswordCorrect() {
		String pass1 = new String(pfNewPass1.getPassword());
		String pass2 = new String(pfNewPass2.getPassword());
		return pass1.equals(pass2);
	}
	
	public static String getNewPassword() {
		return new String(pfNewPass1.getPassword());
	}
	
	public static String getFormerPassword() {
		return new String(pfFormerPass.getPassword());
	}
	
	public static void eraseFields() {
		pfNewPass1.setText("");
		pfFormerPass.setText("");
		pfNewPass2.setText("");
	}
}