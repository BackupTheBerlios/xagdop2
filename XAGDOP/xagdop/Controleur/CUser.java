package xagdop.Controleur;


import javax.management.InstanceNotFoundException;
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
	
	public boolean creerUser(String login, String passwd, String passwdconf) throws Exception,InstanceNotFoundException
	{
		UsersParser UP = new UsersParser();
		int idUser = 2;
		if (passwd.equals(passwdconf)){
    		
			if (!UP.isUser(UP.getId(login))){
				UP.addUser(idUser,login,passwd);
				JOptionPane.showMessageDialog(null ,"Utilisateur creer", "Validation" , 1) ;
	    		return true;
			}
			else{
				JOptionPane.showMessageDialog(null ,"Login deja utilise", "Validation" , 1) ;
				return false;
			}
		}
		else{
    		JOptionPane.showMessageDialog(null ,"Les deux mots de passe ne sont pas identiques", "Validation" , 1) ;
    		return false;
		}
	}
}
	
