package xagdop.Parser;

public class testsParsers {

	public testsParsers()
	{
		UsersParser users = new UsersParser();
		ProjectsParser projects = new ProjectsParser();
		
		/*
		String login;
		String passwd;*/
		String url;
		
		users.removeUser(8);
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
