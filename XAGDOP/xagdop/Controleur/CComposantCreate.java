package xagdop.Controleur;

import java.io.File;
import java.io.IOException;

import xagdop.Interface.Preferences.IPreferences;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;




public class CComposantCreate {
	
	public CComposantCreate(){
	}
	
	public static void composantCreate(String nomComposant,String projectName) throws IOException
	{
		//Creation du Repertoire dans le bon repertoire
		System.out.println(IPreferences.getDefaultPath()+projectName);
		File project = new File(IPreferences.getDefaultPath(),projectName);
		File compo = new File(project,nomComposant);
		if (!compo.mkdir())
		{
			ErrorManager.getInstance().setErrMsg(Bundle.getText("ccomposantcreate.err.msg"));
			ErrorManager.getInstance().setErrTitle(Bundle.getText("ccomposantcreate.err.title"));
			throw new IOException();
		}
		
		File fLib= new File(project,"bib"+File.separator+nomComposant);
		if (!fLib.exists())
			fLib.mkdir();
			
		
		File fImage= new File(compo,"image");
		if (!fImage.exists())	
			fImage.mkdir();
		
	}
	
}