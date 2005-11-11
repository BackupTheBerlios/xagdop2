package src.Interface;

import javax.swing.JFrame;

public class IIdentification extends JFrame{
	
	private static IIdentification Ident = null;
	
	private IIdentification(){
		init();
	}
	
	
	private void init(){
		
		setTitle("Identification");
		setSize(300, 200);
		
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IIdentification getIdent() {
		if (Ident==null){
			Ident = new IIdentification(); 
		}
		
		return Ident;
	}
}