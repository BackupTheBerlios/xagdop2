package xagdop.Controleur;

import java.io.File;

import xagdop.Interface.IProjectTree;
import xagdop.Interface.XAGDOP;




public class CComposantCreate {
	
	public CComposantCreate(){
	}
	
	public static void composantCreate(String nomComposant,String nomProjet)
	{
		//Recuperation du path global du root
		String pathGlobal = ((CTreeNode)((IProjectTree)XAGDOP.getInstance().getTree()).getModel().getRoot()).getLocalPath();
		
		String path = pathGlobal+File.separator+nomProjet+File.separator+nomComposant;
		//Creation du Repertoire dans le bon repertoire
		File f = new File(path);
		if (f.mkdir())
		{
			System.out.println("oki ! ");
		}
		else
		{
			System.out.println("Non !");
		}
		path = path+File.separator+"lib"+nomComposant;
		File fLib= new File(path);
		if (fLib.mkdir())
		{
			System.out.println("oki ! ");
		}
		else
		{
			System.out.println("Non !");
		}
		
	}
	
}