package xagdop.Interface.Preferences;

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

import xagdop.Controleur.CPreferencies;
import xagdop.ressources.Bundle;

public class LanguagePanel extends PreferencePanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox mLangComboBox;
	private ArrayList mLang_available;
	public static LocaleXtended mLanguage;
		
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
		int mLanguagePos = -1;
		
		//create the border
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleStyle = BorderFactory.createTitledBorder(loweredetched, Bundle.getText("ipreferences.language.grpBox"));
		
		mLanguage = new LocaleXtended(CPreferencies.getDefaultLocale()); //prefMan.getProperty(mPropertyKey);
		// debug
		System.out.println(CPreferencies.getDefaultLocale());
		
		
    	mLang_available = CPreferencies.getAllLocale();
    	
    	mLangComboBox = new JComboBox();//mLang_available);
    	for (int i = 0; i < mLang_available.size(); i++) {
    		LocaleXtended tempLoc = new LocaleXtended((Locale)mLang_available.get(i));
    		//mLangComboBox.addItem((Locale)mLang_available.get(i) );
    		mLangComboBox.addItem(tempLoc);
    		if (tempLoc.toString().equals(mLanguage.toString())) 
    			{ mLanguagePos = i; System.out.println("ok"); }
    		// debug
    		if (tempLoc.equals(mLanguage)) System.out.println("pareil");
    		else System.out.println("pas pareil " + tempLoc.toString() + " " + mLanguage.toString());
    	}
    	//mLangComboBox.setSelectedItem(mLanguage);
    	mLangComboBox.setSelectedIndex(mLanguagePos);
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
	
	public class LocaleXtended {
		public Locale loc;
		public LocaleXtended(Locale l) { loc = l ;}
		public String toString() {
			return loc.getDisplayLanguage();
		}
	}
	
	public void jChoice(){
		//IPreferences.prefHasChanged(IPreferences.LANGUAGE_REF);
		mLanguage = (LocaleXtended) mLangComboBox.getSelectedItem();
    	//prefMan.setProperty(mPropertyKey, mLookandFeel);
    	IPreferences.prefHasChanged(IPreferences.LANGUAGE_REF, IPreferences.ADD);
    }
	
	public static Locale getLanguage() {
		return mLanguage.loc;
	}
}
