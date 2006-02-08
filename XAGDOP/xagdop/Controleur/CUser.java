package xagdop.Controleur;


import java.io.File;

import javax.management.InstanceNotFoundException;
import javax.swing.JOptionPane;

import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
import xagdop.Model.User;
import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

public class CUser {
	
	public CUser(){
	}
	
	public boolean verifUser(String login, String passwd) throws Exception{
		UsersParser uParser = UsersParser.getInstance();
		
		User user = uParser.getUser(login,CEncrypt.getEncodedPassword(passwd));
    	if (user != null){
    		IPreferences.setDefaultPath(IPreferences.getDefaultPath()+user.getLogin()+File.separator);
    		XAGDOP.getInstance().setUser(user);
    		return true;
    	}
    	else{
    		return false;
    	}
		
	}
	
	public void createUser(String login, String passwd, String passwdconf) throws Exception,InstanceNotFoundException
	{
		UsersParser up = UsersParser.getInstance();
		if (passwd.equals(passwdconf)){
    		if (passwd.length()>3){
    			if (!up.isUser(login)){
    				up.addUser(login,CEncrypt.getEncodedPassword(passwd));
    				JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.create.text"), Bundle.getText("iusercreate.title"), 1) ;
    			}
    			else{
    				ErrorManager.getInstance().setErrMsg(Bundle.getText("cuser.exist.text"));
    				ErrorManager.getInstance().setErrTitle(Bundle.getText("iusercreate.title"));
    				throw new Exception();
    			}
    		}
    		else{
    			ErrorManager.getInstance().setErrMsg(Bundle.getText("cuser.length.text"));
    			ErrorManager.getInstance().setErrTitle(Bundle.getText("iusercreate.title"));
    			throw new Exception();    		
    		}
		}
		else{
			ErrorManager.getInstance().setErrMsg(Bundle.getText("cuser.notequals.text"));
			ErrorManager.getInstance().setErrTitle(Bundle.getText("iusercreate.title"));
			throw new Exception();   
		}
	}
}
	
