package xagdop.Model;


public class Preferencies {
	private String server="";
	private String lookNFeel="";
	private String langue="";
	
	
	public Preferencies(String server, String lookNFeelName,String langue){
		this.server=server;
		this.lookNFeel=lookNFeelName;	
		this.langue=langue;
	}
	
	


	public String getLangue() {
		return langue;
	}


	public String getLookNFeel() {
		return lookNFeel;
	}


	public String getServer() {
		return server;
	}
	
	
}
