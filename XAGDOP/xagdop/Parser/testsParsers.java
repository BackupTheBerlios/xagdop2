package xagdop.Parser;

import java.io.File;



public class testsParsers {

	public testsParsers()
	{
		POGParser p = new POGParser(new File("/home/claire/tests/Phlancement.pog"));
		p.setApesPathToRelative("tests/Phlancement.apes");
		/*
		DependenciesParser dep = DependenciesParser.getInstance();
		
		ArrayList list = dep.getPogFromApes("pabe.apes");
		for(int i=0; i<list.size(); i++)
		 {
			 System.out.println("Pog : " + list.get(i));
			 
		 }
		
		dep.delToUpdate("bla.apes");
		
		
		
		/*
		list = dep.getPreFromPog("pabe.pog");
		for(int i=0; i<list.size(); i++)
		 {
			 System.out.println("Pre : " + list.get(i));
			 
		 }
		
		list = dep.getPreFromApes("Phlancement.apes");
		for(int i=0; i<list.size(); i++)
		 {
			 System.out.println("getPreFromApes : " + list.get(i));
			 
		 }
		//UsersParser users = new UsersParser();

		//ProjectsParser projects = new ProjectsParser();
		
	
		String login;
		String passwd;
		String url;

		String passwd;
		String url;
		
		//users.removeUser(8);
		//projects.removeProject("Projet2");
		 //Projects projet = projects.getAllUsers("Projet1");

		 ArrayList list = projet.getUsersId();

		 
		 for(int i=0; i<list.size(); i++)
		 {
			 System.out.println("Id user : " + (((Integer)list.get(i)).intValue()));
			 System.out.println("Manager : " + projet.isManager(((Integer)list.get(i)).intValue()));
			 System.out.println("Architecte : " + projet.isArchitecte(((Integer)list.get(i)).intValue()));
			 System.out.println("Analyste : " + projet.isAnalyst(((Integer)list.get(i)).intValue()));
			 System.out.println("Redacteur : " + projet.isRedacteur(((Integer)list.get(i)).intValue()));
			 System.out.println("---------------------------------------------------");
		 }
	/*
		login = (String)users.getAttribute(1, UsersParser.ATTR_LOGIN);
		System.out.println("Login de 1 : "+login);
		users.setAttribute(1, UsersParser.ATTR_LOGIN,"oooooo");
		login = (String)users.getAttribute(1, UsersParser.ATTR_LOGIN);
		System.out.println("Login de 1 : "+login);
		
		passwd = (String)users.getAttribute(1, UsersParser.ATTR_PASSWD);
		System.out.println("passwd de 1 : "+passwd);
		users.setAttribute(1, UsersParser.ATTR_PASSWD,"PasswdDeOooooo");
		passwd = (String)users.getAttribute(1, UsersParser.ATTR_PASSWD);
		System.out.println("passwd de 1 : "+passwd);
		
		if(!users.isUser(8)){
			users.addUser(8, "User8", "Pass8");
			System.out.println("Ajout de User8 ");
			login = (String)users.getAttribute(8, UsersParser.ATTR_LOGIN);
			System.out.println("Login de 8 : "+login);
			passwd = (String)users.getAttribute(8, UsersParser.ATTR_PASSWD);
			System.out.println("passwd de 8 : "+passwd);
		}
		else
		{
			System.out.println("User8 existe deja");
		}
	
		url = (String)projects.getAttribute("Projet1", ProjectsParser.ATTR_URLREPO);
		System.out.println("URL de Projet1 : "+url);
		
		projects.addUser("Projet2",6, "true", "true", "false");
		projects.addUser("Projet2",9);
		if(((Boolean)projects.getAttribute("Projet2",ProjectsParser.ATTR_ANALYST,1)).booleanValue())
		{
			System.out.println("User1 analyste");
		}
		else
		{
			System.out.println("User1 pas analyste");
		}

		projects.removeUser("Projet1",2);*/
	}
	
}
