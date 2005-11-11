package src.Interface;

import javax.swing.JFrame;

public class IProjet extends JFrame{
	
	private static IProjet IP = null;
	
	private IProjet(){
		init();
	}
	
	
	private void init(){
		
		setTitle("Creer un projet");
		setSize(300, 200);
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IProjet getIP() {
		if (IP==null){
			IP = new IProjet(); 
		}
		
		return IP;
	}
}