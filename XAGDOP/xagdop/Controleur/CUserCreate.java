package xagdop.Controleur;

import javax.management.InstanceNotFoundException;
import javax.swing.JOptionPane;

import xagdop.Parser.UsersParser;

public class CUserCreate {
		
		public void creerUser(int idUser, String login, String passwd) throws Exception,InstanceNotFoundException
		{
			UsersParser UP = new UsersParser();
		
			if (!UP.isUser(UP.getId(login))){
				UP.addUser(idUser,login,passwd);
			}
			else{
				JOptionPane.showMessageDialog(null ,"Login deja utilise", "Validation" , 1) ;
			}
		}
}
