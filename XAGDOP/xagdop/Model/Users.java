package xagdop.Model;

public class Users {
	private String login;
	private String passwd;
	private int id;
	private boolean admin;
	private boolean pmanager;
	
	public Users(String log, String pass, int numId, boolean adm, boolean pman)
	{
		login = log;
		passwd = pass;
		id = numId;
		admin = adm;
		pmanager = pman;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public boolean isPmanager() {
		return pmanager;
	}
	public void setPmanager(boolean pmanager) {
		this.pmanager = pmanager;
	}
}
