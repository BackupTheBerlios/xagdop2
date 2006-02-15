package xagdop.Interface.preferencesPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import xagdop.Interface.IPreferences;
import xagdop.ressources.Bundle;
import xagdop.Controleur.CPreferencies;

public class LanguagePanel extends PreferencePanel{
	private JComboBox mLangComboBox;
	private ArrayList mLang_available;
	public static LocaleTheBest mLanguage;
		
	public LanguagePanel(){
		initUI();
	}
	
	protected void initUI() 
	{	
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		this.add(getPanelDescription(Bundle.getText("ipreferences.language.title")), BorderLayout.NORTH);
		this.add(getPanelCentral(), BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(5,5,20,5));
	}
	
	public JPanel getPanelCentral()
	{ 
		JPanel mPanelCentral = new JPanel();
    	mPanelCentral.setLayout(new FlowLayout());
		mLang_available = new ArrayList();
		
		
		//create the border
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder(loweredetched, Bundle.getText("ipreferences.language.grpBox"));
		
		mLanguage = new LocaleTheBest(CPreferencies.getDefaultLocale()); //prefMan.getProperty(mPropertyKey);
		
		
    	mLang_available = CPreferencies.getAllLocale();
    	
    	mLangComboBox = new JComboBox();//mLang_available);
    	for (int i = 0; i < mLang_available.size(); i++) {
    		LocaleTheBest tempLoc = new LocaleTheBest((Locale)mLang_available.get(i));
    		//mLangComboBox.addItem((Locale)mLang_available.get(i) );
    		mLangComboBox.addItem(tempLoc);
    	}
    	mLangComboBox.setSelectedItem(mLanguage);
    	mLangComboBox.setPreferredSize(new Dimension(200,30));
    	mLangComboBox.setEditable(false);
		mPanelCentral.setBorder(titleStyle);
		mLangComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jChoice();
			}
		});
		mPanelCentral.add(mLangComboBox);
		return mPanelCentral;
	}
	
	public class LocaleTheBest {
		public Locale loc;
		public LocaleTheBest(Locale l) { loc = l ;}
		public String toString() {
			return loc.getDisplayLanguage();
		}
	}
	
	public void jChoice(){
		//IPreferences.prefHasChanged(IPreferences.LANGUAGE_REF);
		mLanguage = (LocaleTheBest) mLangComboBox.getSelectedItem();
    	//prefMan.setProperty(mPropertyKey, mLookandFeel);
    	IPreferences.prefHasChanged(IPreferences.LANGUAGE_REF, IPreferences.ADD);
    }
	
	public static Locale getLanguage() {
		return mLanguage.loc;
	}
}
