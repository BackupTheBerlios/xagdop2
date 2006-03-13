
package xagdop.Interface.Preferences;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import xagdop.Controleur.CPreferencies;
import xagdop.ressources.Bundle;

/**
 * a class which allows to create a panel to select the default path
 *
 */
public class RemotePathPanel extends PreferencePanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//mPropertyKey --> key and mDirectory --> path
	protected static String mDirectory = "";
	protected JTextField mDirectoryPath ;
		
	
	public RemotePathPanel(){
		initUI();
	}
	
	protected void initUI() 
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(getPanelDescription(Bundle.getText("ipreferences.serverUrl.title")), BorderLayout.NORTH);
		this.add(getPanelCentral(), BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));
	}
	
	public JPanel getPanelCentral()
	{ 
		JPanel Pane = new JPanel();
		
		//create the border
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder( loweredetched, Bundle.getText("ipreferences.serverUrl.grpBox"));
		Pane.setBorder(titleStyle);
		
		//add a textField
		mDirectory = CPreferencies.getServerPath();
		Pane.setLayout(new FlowLayout());
		mDirectoryPath = new JTextField(30);
		mDirectoryPath.setEditable(true) ;
		mDirectoryPath.setText(mDirectory);
		Pane.add(mDirectoryPath, FlowLayout.LEFT);
		mDirectoryPath.addKeyListener(new myKeyAdapter());
		
		return Pane;
	}
	
	private class myKeyAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			mDirectory = (mDirectoryPath.getText()).trim();
			if (mDirectoryPath.getText().length() != 0)
				IPreferences.prefHasChanged(IPreferences.REMOTE_PATH_REF, IPreferences.ADD);
			else
				IPreferences.prefHasChanged(IPreferences.REMOTE_PATH_REF, IPreferences.DEL);
		}
	}
	
	public static String getRemotePath() {
		return mDirectory;
	}

	public void setRemotePath(String serverPath) {
		mDirectory=serverPath;
		mDirectoryPath.setText(mDirectory);
		
	}
}
