 /*
 * SOAP Supervising, Observing, Analysing Projects
 * Copyright (C) 2003-2004 SOAPteam
 * 
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package xagdop.Interface.preferencesPanels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import xagdop.ressources.Bundle;
import xagdop.Controleur.CPreferencies;
import xagdop.Interface.IPreferences;

/**
 * a class which allows to create a panel to select the default path
 *
 */
public class LocalPathPanel extends PreferencePanel
{
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
}
