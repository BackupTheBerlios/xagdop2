package xagdop.Controleur;


import java.io.File;

import javax.management.InstanceNotFoundException;
import javax.swing.JOptionPane;

import xagdop.Interface.XAGDOP;
import xagdop.Interface.Preferences.IPreferences;
import xagdop.Model.User;
import xagdop.Parser.PreferenciesParser;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

public class CUser {
	
	public CUser(){
	}
	
	/*Lors de l'identification de l'utilisateur, on verifie s'il existe */
	public boolean verifUser(String login, String passwd) throws Exception{
		UsersParser uParser = UsersParser.getInstance();
		
		/*On recupere l'utilisateur qui possède le login et le mot de passe inscrit */
		User user = uParser.getUser(login,CEncrypt.getEncodedPassword(passwd));
    	if (user != null){
    		/* Si l'utilisateur existe, on se connecte a l'application */
    		IPreferences.setDefaultPath(PreferenciesParser.getInstance().buildPreferencies().getLocal()+File.separator+user.getLogin()+File.separator);
    		XAGDOP.getInstance().setUser(user);
    		return true;
    	}
    	else{
    		return false;
    	}
		
	}
	
	/*Fonction permettant de créer un nouvel utilisateur avec un login et un mot de passe */
	public void createUser(String login, String passwd, String passwdconf) throws Exception,InstanceNotFoundException
	{
		UsersParser up = UsersParser.getInstance();
		
		/* On vérifie si les deux mots de passe sont identiques */
		if (passwd.equals(passwdconf)){
		
			/* On vérifie la longueur du mot de passe*/
			if (passwd.length()>3){
    			
				/* On vérifie si le login est déjà attribué*/
				if (!up.isUser(login)){
    				up.addUser(login,CEncrypt.getEncodedPassword(passwd));
    				JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.create.text"), Bundle.getText("iusercreate.title"), 1) ;
    			}
    			else{
    				/* On renvoi une exception car le login existe déja */
    				ErrorManager.getInstance().setErrMsg(Bundle.getText("cuser.exist.text"));
    				ErrorManager.getInstance().setErrTitle(Bundle.getText("iusercreate.title"));
    				throw new Exception();
    			}
    		}
    		else{
    			/* On renvoi une exception car le mot de passe ne contient pas au minimum 4 caractères*/
    			ErrorManager.getInstance().setErrMsg(Bundle.getText("cuser.length.text"));
    			ErrorManager.getInstance().setErrTitle(Bundle.getText("iusercreate.title"));
    			throw new Exception();    		
    		}
		}
		else{
			/* On renvoi une exception car la confirmation du mot de passe est différente du mot de passe*/
			ErrorManager.getInstance().setErrMsg(Bundle.getText("cuser.notequals.text"));
			ErrorManager.getInstance().setErrTitle(Bundle.getText("iusercreate.title"));
			throw new Exception();   
		}
	}
}
	
