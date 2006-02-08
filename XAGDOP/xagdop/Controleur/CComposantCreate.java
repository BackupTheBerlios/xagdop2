package xagdop.Controleur;

import java.io.File;
import java.io.IOException;

import xagdop.Interface.IProjectTree;
import xagdop.Interface.XAGDOP;
import xagdop.Util.ErrorManager;




public class CComposantCreate {
	
	public CComposantCreate(){
	}
	
	public static void composantCreate(String nomComposant,String nomProjet) throws IOException
	{
		//Recuperation du path global du root
		String pathGlobal = ((CTreeNode)((IProjectTree)XAGDOP.getInstance().getTree()).getModel().getRoot()).getLocalPath();
		
		String path = pathGlobal+File.separator+nomProjet+File.separator+nomComposant;
		//Creation du Repertoire dans le bon repertoire
		File f = new File(path);
		if (!f.mkdir())
		{
			ErrorManager.getInstance().setErrMsg("La cr?ation du composant n'a pu se faire");
			ErrorManager.getInstance().setErrTitle("Cr?ation de composant impossible.");
			throw new IOException();
		}
		path = path+File.separator+"lib"+nomComposant;
		File fLib= new File(path);
		if (!fLib.mkdir())
		{
			ErrorManager.getInstance().setErrMsg("La cr?ation du composant n'a pu se faire");
			ErrorManager.getInstance().setErrTitle("Cr?ation de composant impossible.");
			throw new IOException();
		}
		
	}
	
}