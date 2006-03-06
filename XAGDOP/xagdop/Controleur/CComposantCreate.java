package xagdop.Controleur;

import java.io.File;
import java.io.IOException;

import xagdop.Interface.Preferences.IPreferences;
import xagdop.Util.ErrorManager;




public class CComposantCreate {
	
	public CComposantCreate(){
	}
	
	public static void composantCreate(String nomComposant,String projectName) throws IOException
	{
		//Creation du Repertoire dans le bon repertoire
		File f = new File(IPreferences.getDefaultPath()+projectName);
		if (!f.mkdir())
		{
			ErrorManager.getInstance().setErrMsg("La cr?ation du composant n'a pu se faire");
			ErrorManager.getInstance().setErrTitle("Cr?ation de composant impossible.");
			throw new IOException();
		}
		File fLib= new File(f,"bib"+File.separator+nomComposant);
		if (!fLib.exists())
		
			fLib.mkdir();
			
		
		File fImage= new File(f,"image");
		if (!fImage.exists())	
			fImage.mkdir();
		
	}
	
}