
package xagdop.Interface.Preferences;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
public class LocalPathPanel extends PreferencePanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static String mDirectory = "";
	protected JButton mBrowse ;
	protected JTextField mDirectoryPath ;
	
		
	public LocalPathPanel(){
		initUI();
	}
	
	protected void initUI() 
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(getPanelDescription(Bundle.getText("ipreferences.localWorkspace.title")), BorderLayout.NORTH);
		this.add(getPanelCentral(), BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));
	}
	
	public JPanel getPanelCentral()
	{ 
		JPanel Pane = new JPanel();
		
		//create the border
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder( loweredetched, Bundle.getText("ipreferences.localWorkspace.grpBox"));
		Pane.setBorder(titleStyle);
		
		//add a textField
		mDirectory = CPreferencies.getLocalPath();
		Pane.setLayout(new FlowLayout());
		mDirectoryPath = new JTextField(30);
		mDirectoryPath.setEditable(false) ;
		mDirectoryPath.setText(mDirectory);
		Pane.add(mDirectoryPath, FlowLayout.LEFT);
		
		
		//add a button
		mBrowse = new JButton(Bundle.getText("button.browse"));
		mBrowse.setEnabled(true) ;
		Pane.add(mBrowse);
		mBrowse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jButton_pressed();
			}
		});
		return Pane;
	}
		
	public void jButton_pressed(){
		SmartChooser chooser = SmartChooser.getChooser();
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setDirectory(mDirectory);
		chooser.setDialogTitle(Bundle.getText("iconflocalpath.choosePath")); 
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		chooser.setApproveButtonMnemonic(KeyEvent.VK_O);
		
		int result = chooser.showDialog (this, Bundle.getText("iconflocalpath.open"));
		if (result == JFileChooser.APPROVE_OPTION )
		{
		    if(chooser.getSelectedFile().exists() && chooser.getSelectedFile().isDirectory())
			{
		    	mDirectory = chooser.getSelectedFile().getAbsolutePath();
		    	mDirectoryPath.setText(mDirectory);
		    	IPreferences.prefHasChanged(IPreferences.LOCAL_PATH_REF, IPreferences.ADD);
			}
			else{
				return;
		}
		}
	}
	
	public static String getLocalPath() {
		return mDirectory;
	}

	public void setLocalPath(String directory) {
		mDirectory = directory;
		mDirectoryPath.setText(mDirectory);
	}
	
}
