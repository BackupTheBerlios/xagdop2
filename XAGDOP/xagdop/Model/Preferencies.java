package xagdop.Model;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Preferencies {
	private String server="";
	private String lookNFeel="";
	private String langue="";
	
	
	public Preferencies(String server, String lookNFeelName,String langue){
		this.server=server;
		this.lookNFeel=lookNFeelName;	
		this.langue=langue;
	}
	
	
	/**
	 * Permet d'obtenir la liste des noms des classes de LookNFeel disponibles sur la machine.(pour fixer)
	 * @return ArrayList de String contenant le nom du LNF
	 */
	public static ArrayList getAvaillableLNFName(){
		ArrayList availlLNF = new ArrayList();
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for(int i=0; i < info.length; i++){
			System.out.println(info[i].getName());
			availlLNF.add(info[i].getName());
		}
		return availlLNF;
	}	
	
	
	/**
	 * Permet d'obtenir le nom de la classe LNF correspondant a un nom de LNF
	 * @param name Nom du LNF
	 * @return Nom de la classe du LNF
	 */
	public static String getLNFClassName (String name)
	{
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		int i = 0;
		while (i < info.length && !(info[i].getName().equals(name))){
			i++;
		}
		if (i < info.length){
			return info[i].getClassName();
		}
		else{
			return "";	
		}
	}
			
	
	/**
	 * Applique un LNF a un composant graphique
	 * @param className nom de la classe du LNF
	 * @param comp Composant a mettre a jour
	 */
	public static void changeLookandFeel (String className, Component comp)
	{
		try {
		    UIManager.setLookAndFeel(className);
	  		SwingUtilities.updateComponentTreeUI(comp);
		}	
		catch (InstantiationException ex) {ex.printStackTrace();}
		catch (ClassNotFoundException ex) {ex.printStackTrace();}
		catch (UnsupportedLookAndFeelException ex) {ex.printStackTrace();}
		catch (IllegalAccessException ex) { ex.printStackTrace(); }
	}


	public String getLangue() {
		return langue;
	}


	public String getLookNFeel() {
		return lookNFeel;
	}


	public String getServer() {
		return server;
	}
	
	
}
