package xagdop.Model;

import java.io.IOException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Parser.ProjectsParser;

public class User {
	private String login;
	private String passwd;
	private boolean admin;
	private boolean pcreator;
	
	public User(String log, String pass, boolean adm, boolean pcreat)
	{
		login = log;
		passwd = pass;
		admin = adm;
		pcreator = pcreat;
	}
	
	
	// Utilise pour le debug
	public String toString() {
		StringBuffer sb;
		sb = new StringBuffer("\nlogin : " + login);
		sb.append("\npass : " + passwd);
		if (admin) sb.append("admin");
		if (pcreator) sb.append("pcreator");
		return sb.toString();
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public boolean isPcreator() {
		return pcreator;
	}
	public void setPmanager(boolean pcreat) {
		this.pcreator = pcreat;
	}
	
	public boolean isArchitect(String pName) throws SVNException, IOException, Exception
	{
		ProjectsParser projet = ProjectsParser.getInstance();
		//return ((Boolean)projet.getAttribute(pName, ProjectsParser.RIGHT_ARCHITECT, this.login)).booleanValue();
		return ((Boolean)(projet.getRights(pName,this.login).get(1))).booleanValue();
	}

	public boolean isRedactor(String pName) throws SVNException, IOException, Exception
	{
		ProjectsParser projet = ProjectsParser.getInstance();
		//return ((Boolean)projet.getAttribute(pName, ProjectsParser.RIGHT_REDACTOR, this.login)).booleanValue();
		return ((Boolean)(projet.getRights(pName,this.login).get(3))).booleanValue();
	}

	public boolean isAnalyst(String pName) throws SVNException, IOException, Exception
	{
		ProjectsParser projet = ProjectsParser.getInstance();
		//return ((Boolean)projet.getAttribute(pName, ProjectsParser.RIGHT_ANALYST, this.login)).booleanValue();
		return ((Boolean)(projet.getRights(pName,this.login).get(2))).booleanValue();
	}

	public boolean isPManager(String pName) throws SVNException, IOException, Exception
	{
		ProjectsParser projet = ProjectsParser.getInstance();
		//return ((Boolean)projet.getAttribute(pName, ProjectsParser.RIGHT_PMANAGER, this.login)).booleanValue();
		return ((Boolean)(projet.getRights(pName,this.login).get(0))).booleanValue();
	}

}
