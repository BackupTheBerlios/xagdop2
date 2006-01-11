package xagdop.Interface;

import javax.swing.JFrame;


public class IUserCreate extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6591847623691362372L;

	
	/**
	 * 
	 */
	private static IUserCreate IUC = null;

	private IUserCreate(){
		init();
	}
	
	
	private void init(){
			
         
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IUserCreate getIUC() {
		if (IUC==null){
			IUC = new IUserCreate(); 
		}
		
		return IUC;
	}
}