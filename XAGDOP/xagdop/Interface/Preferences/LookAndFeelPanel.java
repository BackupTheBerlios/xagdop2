package xagdop.Interface.Preferences;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import xagdop.Controleur.CPreferencies;
import xagdop.ressources.Bundle;

public class LookAndFeelPanel extends PreferencePanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox mLaFComboBox;
	private ArrayList mLNF_available;
	public static String mLookandFeel = "";
		
	public LookAndFeelPanel(){
		initUI();
	}
	
	protected void initUI() 
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(getPanelDescription(Bundle.getText("ipreferences.LNF.title")), BorderLayout.NORTH);
		this.add(getPanelCentral(), BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));
	}
	
	public JPanel getPanelCentral()
	{ 
		JPanel mPanelCentral = new JPanel();
    	mPanelCentral.setLayout(new FlowLayout());
		mLNF_available = new ArrayList();
		
		
		//create the border
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder(loweredetched, Bundle.getText("ipreferences.LNF.grpBox"));
		
		mLookandFeel = CPreferencies.getDefaultLNF();
		mLNF_available = CPreferencies.getAllLNF();
		    	
    	mLaFComboBox = new JComboBox();
    	for (int i = 0; i < mLNF_available.size(); i++)
    		mLaFComboBox.addItem((String)mLNF_available.get(i) );
    	mLaFComboBox.setSelectedItem(mLookandFeel);
    	mLaFComboBox.setPreferredSize(new Dimension(300,30));
		mLaFComboBox.setEditable(false);
		mPanelCentral.setBorder(titleStyle);
		mLaFComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jChoice();
			}
		});
		mPanelCentral.add(mLaFComboBox);
		return mPanelCentral;
	}
	
	public void jChoice(){
    	mLookandFeel = (String)mLaFComboBox.getSelectedItem();
    	//prefMan.setProperty(mPropertyKey, mLookandFeel);
    	IPreferences.prefHasChanged(IPreferences.LNF_REF, IPreferences.ADD);
    }
	
	public static String getLNF() {
		return mLookandFeel;
	}
}
