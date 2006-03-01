package xagdop.Thread;

import xagdop.Parser.UsersParser;
import xagdop.Util.ErrorManager;

public class ThreadAdmin extends Thread {
	
	ThreadWait tWait;

	public ThreadAdmin(ThreadWait tWait) {
		this.tWait = tWait;
	}
	
	public void run() {
		
		try{
			
			UsersParser.getInstance().publish(UsersParser.getInstance().getUsersXML() );
		} catch (Exception e) {
			ErrorManager.getInstance().display();
		}
		finally{
			tWait.arreter();
		}
	}
	
	
	
}
