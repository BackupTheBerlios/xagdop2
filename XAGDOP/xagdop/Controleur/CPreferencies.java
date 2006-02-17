package xagdop.Controleur;

import java.awt.Component;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import xagdop.Interface.XAGDOP;
import xagdop.Parser.PreferenciesParser;
import xagdop.Parser.UsersParser;
import xagdop.ressources.Bundle;


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
	
	/**
	 * Retourne le chemin local
	 */
	public static String getLocalPath(){
		return PreferenciesParser.getInstance().buildPreferencies().getLocal();	
	}
	
	
	/**
	 * Fixe le chemin local
	 * @param path 
	 */
	public static void setLocalPath(String path){
		try {
			PreferenciesParser.getInstance().setLocalPath(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Retourne l'URL du serveur
	 * @return
	 */
	public static String getServerPath(){
		return PreferenciesParser.getInstance().buildPreferencies().getServer();			
	}
	
	
	/**
	 * Fixe l'URL du serveur
	 * @param serv
	 */
	public static void setServerPath(String serv){
		try {
			PreferenciesParser.getInstance().setServer(serv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * Retourne le LookNFeel par defaut defini dans les preferences
	 * @return
	 */
	public static String getDefaultLNF(){
		return PreferenciesParser.getInstance().buildPreferencies().getLookNFeel();	
	}
	
	
	/**
	 * Fixe le LookNFeel par defaut dans les preferences
	 * @param lnf
	 */
	public static void setDefaultLNF(String lnf){
		try {
			UIManager.setLookAndFeel(CPreferencies.getLNFClassName(lnf));
			Frame[] tabFrame = JFrame.getFrames();
			for(int i=0; i<tabFrame.length; i++){
				SwingUtilities.updateComponentTreeUI(tabFrame[i]);	
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			PreferenciesParser.getInstance().setLNF(lnf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Permet d'obtenir la liste des noms des LookNFeel disponibles sur la machine.
	 * @return ArrayList de String contenant le nom du LNF
	 */
	public static ArrayList getAllLNF(){
		ArrayList availlLNF = new ArrayList();
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for(int i=0; i < info.length; i++){
			//System.out.println(info[i].getName());
			availlLNF.add(info[i].getName());
		}
		return availlLNF;
	}
	
	
	/**
	 * Retourne le langage de la locale par defaut
	 * @return
	 */
	public static Locale getDefaultLocale(){
		//System.out.println("getDefaultLocale->"+PreferenciesParser.getInstance().buildPreferencies().getLangue());//debug
		return PreferenciesParser.getInstance().buildPreferencies().getLang();
	}
	
	
	/**
	 * Fixe la locale par defaut. Ne fait rien si la locale n'existe pas.
	 * @param locale Nom de la locale a appliquer.
	 */
	public static void setDefaultLocale(Locale locale){
		//System.out.println("CPref.setDefaultLocale( Lang: "+locale.getLanguage()+"   Pays: "+locale.getCountry()+ " )");//debug
		try {
			PreferenciesParser.getInstance().setLang(locale);
			//Bundle.setCurrentLocale(locale);
		}
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	
	/**
	 * Retourne les langages des locales disponibles
	 * @return ArrayList de String
	 */
	public static ArrayList getAllLocale(){
		//??? en dur car pas trouve comment faire pour lister uniquement les locales dont un fichier de ressource est disponible
		ArrayList res = new ArrayList();
		res.add(Locale.FRANCE);
		res.add(Locale.UK);
		//System.out.println(res);//debug
		return res;
	}
	
	/**
	 * Si les mots de passe concordent, met a jour le mot de passe de l'utilisateur passe en parametre.
	 * @param login Login de l'utilisateur
	 * @param oldPasswd Ancien mot de passe
	 * @param newPasswd Nouveau mot de passe
	 * @return
	 */
	public static boolean submitPasswd(String login, String oldPasswd, String newPasswd){
		boolean result=false;
		UsersParser UP= UsersParser.getInstance();
		try {
			if(UP.getUserByLogin(login).getPasswd().equals(oldPasswd)){
				UP.setAttribute(login, UsersParser.ATTR_PASSWD, newPasswd);
				result=true;
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * Si les mots de passe concordent, met a jour le mot de passe de l'utilisateur courant.
	 * @param oldPasswd Ancien mot de passe
	 * @param newPasswd Nouveau mot de passe
	 * @return
	 */
	public static boolean submitPasswd(String oldPasswd, String newPasswd){
		String login = XAGDOP.getInstance().getUser().getLogin();		
		boolean result=false;
		UsersParser UP= UsersParser.getInstance();
		
		try {
			//System.out.println("Login: "+login);
			//System.out.println("User.login: "+UP.getUserByLogin(login).getLogin());
			if(CEncrypt.testPassword(oldPasswd,UP.getUserByLogin(login).getPasswd())){
				UP.setAttribute(login, UsersParser.ATTR_PASSWD, CEncrypt.getEncodedPassword(newPasswd));
				System.out.println("New Pass: "+UP.getAttribute(login, UsersParser.ATTR_PASSWD));
				result=true;
				System.out.println("Ancien mod de passe ok");
			}
			else{
				System.out.println("Ancien mod de passe erone");
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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