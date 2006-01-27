package xagdop.Model;

import xagdop.Parser.ProjectsParser;

public class Users {
	private String login;
	private String passwd;
	private boolean admin;
	private boolean pcreator;
	
	public Users(String log, String pass, boolean adm, boolean pcreat)
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
	
	public boolean isArchitect(String pName)
	{
		ProjectsParser projet = new ProjectsParser();
		return ((Boolean)projet.getAttribute(pName, ProjectsParser.ATTR_ARCHI, this.login)).booleanValue();
	}

	public boolean isRedactor(String pName)
	{
		ProjectsParser projet = new ProjectsParser();
		return ((Boolean)projet.getAttribute(pName, ProjectsParser.ATTR_REDACTEUR, this.login)).booleanValue();
	}

	public boolean isAnalyst(String pName)
	{
		ProjectsParser projet = new ProjectsParser();
		return ((Boolean)projet.getAttribute(pName, ProjectsParser.ATTR_ANALYST, this.login)).booleanValue();
	}

	public boolean isPManager(String pName)
	{
		ProjectsParser projet = new ProjectsParser();
		return ((Boolean)projet.getAttribute(pName, ProjectsParser.ATTR_MANAGER, this.login)).booleanValue();
	}

}
