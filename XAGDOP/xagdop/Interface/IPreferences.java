package xagdop.Interface;

import java.io.File;

import javax.swing.JFrame;

public class IPreferences extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7906721154083119869L;
	private static IPreferences IPref = null;
	private static String defaultPath="project/"; 
	
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
		/*File defaultPathUser = new File(defaultPath+XAGDOP.getInstance().getUser().getLogin());
		if(!defaultPathUser.exists())
			defaultPathUser.mkdir();*/
		//System.out.println(defaultPath+XAGDOP.getInstance().getUser().getLogin());
		return defaultPath;//+XAGDOP.getInstance().getUser().getLogin();
	}


	public static void setDefaultPath(String defaultPath) {
		IPreferences.defaultPath = defaultPath;
	}
}