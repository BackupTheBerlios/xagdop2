package xagdop.Controleur;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import xagdop.Parser.PreferenciesParser;
import xagdop.Parser.UsersParser;


public class CPreferencies {
	/*
	 * 
	 * Attributs de la classe
	 * 
	 * 
	 */
	
	/*
	 * Constructeur
	 */
	public CPreferencies(){
		
	}
	
	/*
	 * Methodes de la classe
	 */
	
	public static String getLocalPath(){
		
		return "toto";
	}
	
	public static void setLocalPath(String path){
		
	}
	
	
	public void getServerPath(String serv){
	
	}
	
	public void setServerPath(String serv) throws Exception{
		PreferenciesParser PrefP = PreferenciesParser.getInstance();
		PrefP.setServer(serv);		
	}
	
	
	public static String getDefaultLNF(){
		
		
		return "Metal";
	}
	
	
	public static void setDefaultLNF(String lnf){
		

	}
	
	
	/**
	 * Permet d'obtenir la liste des noms des LookNFeel disponibles sur la machine.
	 * @return ArrayList de String contenant le nom du LNF
	 */
	public static ArrayList getAllLNF(){
		ArrayList availlLNF = new ArrayList();
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for(int i=0; i < info.length; i++){
			System.out.println(info[i].getName());
			availlLNF.add(info[i].getName());
		}
		return availlLNF;
	}
	
	
	public static String getDefaultLocale(){
		
		return "French";
	}
	
	
	public static void setDefaultLocale(String locale){
		
	}
	
	
	public static ArrayList getAllLocale(){
		ArrayList res = new ArrayList();
		return res;
	}
	
	
	public static boolean submitPasswd(String login, String oldPasswd, String newPasswd){
		return true;
	}
	
	
	
	
	public void setLang(Locale loc) throws Exception{
		PreferenciesParser PrefP = PreferenciesParser.getInstance();
		PrefP.setLang(loc.getLanguage());
	}
	
	

	
	
	public void setLNF(String lnf) throws Exception{
		PreferenciesParser PrefP = PreferenciesParser.getInstance();
		PrefP.setLNF(lnf);
		
		
	}
	
	
	public void setPasswd(String login, String pass) throws Exception{
		UsersParser UP= UsersParser.getInstance();
		UP.setAttribute(login, UsersParser.ATTR_PASSWD , pass);
		
	}
	
	
	
	
	/**
	 * Permet d'obtenir le nom de la classe LNF correspondant a un nom de LNF
	 * @param name Nom du LNF
	 * @return Nom de la classe du LNF
	 */
	private static String getLNFClassName (String name)
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
	public static void changeLookandFeel (String name, Component comp)
	{
		try {
		    UIManager.setLookAndFeel(CPreferencies.getLNFClassName(name));
	  		SwingUtilities.updateComponentTreeUI(comp);
		}	
		catch (InstantiationException ex) {ex.printStackTrace();}
		catch (ClassNotFoundException ex) {ex.printStackTrace();}
		catch (UnsupportedLookAndFeelException ex) {ex.printStackTrace();}
		catch (IllegalAccessException ex) { ex.printStackTrace(); }
	}
	
	
	
}