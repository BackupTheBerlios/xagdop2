package src.Interface;

import javax.swing.JFrame;

public class IUser extends JFrame{
	
	private static IUser IU = null;
	
	private IUser(){
		init();
	}
	
	
	private void init(){
		
		setTitle("Affectation de l'equipe");
		setSize(300, 200);
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IUser getIU() {
		if (IU==null){
			IU = new IUser(); 
		}
		
		return IU;
	}
}