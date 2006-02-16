package xagdop.Controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import xagdop.Interface.IPreferences;

public class CFile {
	/*
	 * 
	 * Attributs de la classe
	 * 
	 * 
	 */
	
	/*
	 * Constructeur
	 */
	public CFile(){
		
	}
	
	/*
	 * Methode de la classe
	 */
//	 Copies src file to dst file.
    // If the dst file does not exist, it is created
    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
	
    public static void deleteDirectory(File dir){
		int i = 0 ;
		if(dir.isDirectory())
		{
			File[] allFile = dir.listFiles();
			while(i<allFile.length){
				if(allFile[i].isDirectory())
					deleteDirectory(allFile[i]);
				allFile[i].delete();
				i++;
			}
		}
		dir.delete();
		
	}
    
    public static String treePathName(CTreeNode node){
		if(IPreferences.getDefaultPath().length()+1+node.getProject().getName().length()>node.getLocalPath().length())
			return "";
		
		return node.getLocalPath().substring(IPreferences.getDefaultPath().length()+node.getProject().getName().length()+1,node.getLocalPath().length()); 
	}
	
	/*public String treePathName(String file){
	 
	 //System.out.println(node.getLocalPath().substring(0,mRoot.getLocalPath().length()+1));
	  
	  return file.substring(mRoot.getLocalPath().length()+1,file.length()); 
	  }*/
	
	public static String relativePath(String apesFile, String pogFile){
		
		if(!apesFile.startsWith(IPreferences.getDefaultPath()))
			return apesFile;
		
		if(!pogFile.startsWith(IPreferences.getDefaultPath()))
			return apesFile;
		apesFile = apesFile.replaceFirst(IPreferences.getDefaultPath(),"");
		pogFile = pogFile.replaceFirst(IPreferences.getDefaultPath(),"");
		String[] apesSlit  = apesFile.split(File.separator);
		String[] pogSlit = pogFile.split(File.separator);
		String res="";
		int tmp = 0;
		for(int i = Math.min(apesSlit.length,pogSlit.length); i>0;i--){
			if(apesSlit[i-1].compareTo(pogSlit[i-1])!=0)
				tmp = i-1;
		}
		for(int i = tmp+1;i<pogSlit.length;i++)
			res = res.concat(".."+File.separator);
		for(int i = tmp;i<apesSlit.length;i++)
			res = res.concat(apesSlit[i]+File.separator);
		
		res = res.substring(0,res.length()-1);
		return res;
	}
	
}