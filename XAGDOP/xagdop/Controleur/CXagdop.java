package xagdop.Controleur;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import xagdop.Interface.IIdentification;
import xagdop.Interface.Configuration.IWelcome;
import xagdop.Parser.PreferenciesParser;


public class CXagdop {

public static void main(String args[]){
		//Verification du premier lancement de l'application
		
	//Si premier lancement -> IWelcome
		//System.getProperty(System.)
		File f1 = new File(System.getProperty("user.home")+File.separator+".xagdop");
		
		if (!f1.exists())
		{
			IWelcome iWel = new IWelcome();
			iWel.setVisible(true);
		}
		else
		{
			//SInon -> IIdentification
			try {
				UIManager.setLookAndFeel(CPreferencies.getLNFClassName(PreferenciesParser.getInstance().getLNF()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IIdentification.getInstance();
		}

	}

}