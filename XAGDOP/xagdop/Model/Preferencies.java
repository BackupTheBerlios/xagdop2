package xagdop.Model;


public class Preferencies {
	private String server="";
	private String local="";
	private String lookNFeel="";
	private String langue="";
	
	
	public Preferencies(String server, String local, String lookNFeelName,String langue){
		this.server=server;
		this.local=local;
		this.lookNFeel=lookNFeelName;	
		this.langue=langue;
	}
	
	
	public String getServer() {
		return server;
	}

	public String getLocal() {
		return local;
	}
	
	public String getLangue() {
		return langue;
	}


	public String getLookNFeel() {
		return lookNFeel;
	}
	
	
}
