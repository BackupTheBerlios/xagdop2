package xagdop.Thread;

import xagdop.Interface.IWaiting;

import xagdop.Parser.ProjectsParser;





public class ThreadRemoveUser extends Thread {



	ProjectsParser PP;
	String user;
	String projectName;
	
	public ThreadRemoveUser(ProjectsParser PP,String projectName, String user) {

		this.PP = PP;
		this.user = user;
		this.projectName = projectName;
	}

	public void run() {
			try {
				PP.removeUser(this.projectName,this.user);
			} catch (Exception e) {
				// :p
			}finally{
				IWaiting.getInstance().arreter();
			}
	}
	
	
}
