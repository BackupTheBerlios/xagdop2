package xagdop.Interface;


import java.io.File;

import javax.swing.JFrame;

public class IPreferences extends JFrame{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static IPreferences IPref = null;
	private static String defaultPath= ((File)new File("project")).getAbsolutePath()+File.separator; 
	private static String rootPath=((File)new File("project")).getAbsolutePath()+File.separator; 
	
	private IPreferences(){
		
		init();
	}
	
	
	private void init(){
		
		setTitle("Choix des pr???f???rences");
		setSize(300, 200);
		setVisible(true);
		
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IPreferences getIPref() {
		if (IPref==null){
			IPref = new IPreferences(); 
		}
		
		return IPref;
	}


	public static String getDefaultPath() {
		return defaultPath;
	}

	public static String getRootPath() {
		return rootPath;
	}

	public static void setDefaultPath(String defaultPath) {
		IPreferences.defaultPath = defaultPath;
	}
}