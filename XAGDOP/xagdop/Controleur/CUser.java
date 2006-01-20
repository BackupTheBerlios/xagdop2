package xagdop.Controleur;


import javax.management.InstanceNotFoundException;
import javax.swing.JOptionPane;
import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
import xagdop.Model.Users;
import xagdop.Parser.UsersParser;
import xagdop.ressources.Bundle;

public class CUser {
	
	public CUser(){
	}
	
	public boolean verifUser(String login, String passwd){
		UsersParser UParser = new UsersParser();
		Users user = UParser.getUser(login,passwd);
    	if (user != null){
    		IPreferences.setDefaultPath(IPreferences.getDefaultPath()+user.getLogin()+"/");
    		XAGDOP.getInstance().setUser(user);
    		XAGDOP.getInstance().setVisible(true);
    		XAGDOP.getInstance().refreshButton();
    		return true;
    	}
    	else{
    		JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.notexist.text"), Bundle.getText("iuser.title"), 1) ;
    		return false;
    	}
		
	}
	
	public boolean creerUser(String login, String passwd, String passwdconf) throws Exception,InstanceNotFoundException
	{
		UsersParser UP = new UsersParser();
		int idUser = UP.getAllUsers().size();
		if (passwd.equals(passwdconf)){
    		if (passwd.length()>3){
    			if (!UP.isUser(UP.getId(login))){
    				UP.addUser(idUser,login,passwd);
    				JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.create.text"), Bundle.getText("iusercreate.title"), 1) ;
    				return true;
    			}
    			else{
    				JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.exist.text"), Bundle.getText("iusercreate.title") , 1) ;
    				return false;
    			}
    		}
    		else{
    			JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.length.text"), Bundle.getText("iusercreate.title") , 1) ;
				return false;
    		}
		}
		else{
    		JOptionPane.showMessageDialog(null ,Bundle.getText("cuser.notequals.text"), Bundle.getText("iusercreate.title") , 1) ;
    		return false;
		}
	}
}
	
