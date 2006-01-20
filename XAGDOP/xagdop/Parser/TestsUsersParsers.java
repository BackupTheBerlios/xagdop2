package xagdop.Parser;

import java.util.ArrayList;

import xagdop.Model.Users;

public class TestsUsersParsers {
	public static void main(String[] args) {
		UsersParser up = new UsersParser();
		Users user;
		Object obj;
		
		/*
		// isUser
		if (up.isUser("claire")) System.out.println("[isUser] claire est user") ;
		up.isUser("bernard"); // utilisateur non enregistre --> message erreur
		
		// getUserByLogin
		user = up.getUserByLogin("claire");
		System.out.println(user) ;
		
		// getUser
		user = up.getUser("claire", "pass");
		System.out.println(user) ;
		
		// getAttribute
		obj = up.getAttribute("jeremy", "admin");
		if (obj instanceof Boolean) {
			Boolean Bobj = (Boolean) obj;
			if (Bobj.booleanValue()) System.out.println("[getAttribute] admin") ;
			else System.out.println("[getAttribute] non admin") ;
		}
		*/
		
		/*
		// setAttribute
		up.setAttribute("jeremy", "passwd", "pass");
		
		// addUser
		up.addUser("michel", "michpass");
		up.addUser("gaspard", "gasspass", true);
		up.addUser("philemon", "philpass", false, true);
		*/
		//up.addUser()
		
		// removeUser
		//up.removeUser("rene");
		
		ArrayList al = up.getAllUsers();
		for (int i = 0; i < al.size(); i++) {
			user = (Users) al.get(i);
			System.out.println(user.toString());
		}
	}
}
