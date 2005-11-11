package src.Interface;

import javax.swing.JFrame;

public class IPreferences extends JFrame{
	
	private static IPreferences IPref = null;
	
	private IPreferences(){
		init();
	}
	
	
	private void init(){
		
		setTitle("Choix des pr�f�rences");
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
}