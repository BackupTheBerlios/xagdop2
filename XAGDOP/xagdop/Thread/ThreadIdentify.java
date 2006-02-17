package xagdop.Thread;

import xagdop.Controleur.CUser;
import xagdop.Interface.IIdentification;
import xagdop.Interface.XAGDOP;
import xagdop.Util.ErrorManager;




public class ThreadIdentify extends Thread {


	ThreadWait tWait;
	String userID;
	String password;
	IIdentification ident;
	
	
	public ThreadIdentify(String text, String password, IIdentification ident, ThreadWait tWait) {
		this.tWait = tWait;
		this.userID = text;
		this.password = password;
		this.ident = ident;
		
	}

	public void run() {

		try 
		{
			CUser CU = new CUser();
			if (CU.verifUser(this.userID,this.password))
			{
				ident.setVisible(false);	
				XAGDOP.getInstance().showFrame();
			}
		}
		catch (Exception e1) 
		{
			ErrorManager.getInstance().setErrMsg("L'utilisateur n'existe pas");
			ErrorManager.getInstance().setErrTitle("Probleme d'identification");
			ErrorManager.getInstance().display();
		}
		finally
		{
			tWait.arreter();
		}
		
	}
	
	
}
