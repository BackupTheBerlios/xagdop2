package xagdop.Controleur;

import java.io.File;

import xagdop.Interface.IIdentification;
import xagdop.Interface.Configuration.IWelcome;


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
			IIdentification.getInstance();
		}

	}

}