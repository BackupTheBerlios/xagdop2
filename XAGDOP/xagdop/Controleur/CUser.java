package xagdop.Controleur;


import javax.swing.JOptionPane;
import xagdop.Interface.IPreferences;
import xagdop.Interface.XAGDOP;
import xagdop.Model.Users;
import xagdop.Parser.UsersParser;

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
    		JOptionPane.showMessageDialog(null ,"Login incorrect", "Validation" , 1) ;
    		return false;
    	}
		
	}
}
	
