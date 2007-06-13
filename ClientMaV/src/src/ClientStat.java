package src;

import src.util.UtilORB;
import ui.uiStats.uiStats;

public class ClientStat {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Pour le callback
		UtilORB.registerStats();
		
		uiStats.getInstance();
		
	}
	

}
