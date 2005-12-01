package xagdop.Interface;

import javax.swing.JFrame;

public class IPreferences extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7906721154083119869L;
	private static IPreferences IPref = null;
	private static String defaultPath="xagdop/"; 
	
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


	public static void setDefaultPath(String defaultPath) {
		IPreferences.defaultPath = defaultPath;
	}
}