package xagdop.Model;

import java.util.Locale;


public class Preferencies {
	private String server="";
	private String local="";
	private String lookNFeel="";
	private Locale langue = Locale.getDefault();
	
	
	public Preferencies(String server, String local, String lookNFeelName,Locale langue){
		this.server=server;
		this.local=local;
		this.lookNFeel=lookNFeelName;	
		this.langue= Locale.getDefault();
	}
	
	
	public String getServer() {
		return server;
	}

	public String getLocal() {
		return local;
	}
	
	public Locale getLangue() {
		return langue;
	}


	public String getLookNFeel() {
		return lookNFeel;
	}
	
	
}
