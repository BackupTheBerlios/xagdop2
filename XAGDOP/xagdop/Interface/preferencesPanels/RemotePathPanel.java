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

import xagdop.Interface.IPreferences;
import xagdop.ressources.Bundle;
import xagdop.Controleur.CPreferencies;

/**
 * a class which allows to create a panel to select the default path
 *
 */
public class RemotePathPanel extends PreferencePanel
{
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
		public void keyTyped(KeyEvent e) {
			if (mDirectoryPath.getText().length() != 0)
				IPreferences.prefHasChanged(IPreferences.REMOTE_PATH_REF, IPreferences.ADD);
			else
				IPreferences.prefHasChanged(IPreferences.REMOTE_PATH_REF, IPreferences.DEL);
		}
	}
	
	public static String getRemotePath() {
		return mDirectory;
	}
}
