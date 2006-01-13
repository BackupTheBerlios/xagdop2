package xagdop.Interface;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ThreadWait extends Thread {

	JDialog JD ;
	int i=0;
	JLabel label = new JLabel("i");
	JPanel panel = new JPanel();
	private boolean Stop;
	
	public ThreadWait(JFrame jf){
		JD = new JDialog(jf,"Encours",true);
		//JD.setTitle("En cours");
		JD.setSize(536, 500);
		panel.add(label);
		JD.getContentPane().add(panel);
		
		
		
		
	}
	
	public void run() {
		JD.setVisible(true);
		i=0;
		Stop = false;
		while (!Stop)
			{
			i++;
			label.setText(new Integer(i).toString());
			label.validate();
			}
			
		System.out.println("balbla");
		JD.setVisible(false);
		Stop = false;
    }

	public void setStop(boolean stop) {
		this.Stop = stop;
	}
	public void arreter()
	{
		JD.setVisible(false);
		
		
	}
	public void demarrer()
	{
		JD.setVisible(true);	
	}
	
}
