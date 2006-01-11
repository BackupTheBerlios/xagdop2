package xagdop.Interface;

import javax.swing.JDialog;

public class ThreadWait extends Thread {

	JDialog JD = new JDialog();
	public boolean Stop;
	public ThreadWait(){
		JD.setTitle("En cours");
		Stop = false;
	}
	
	public void run() {
		JD.setVisible(true);
		while (!Stop)
		{		}
		JD.setVisible(false);
    }
	
	
}
