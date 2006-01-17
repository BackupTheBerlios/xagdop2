package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar; 

import sun.awt.windows.ThemeReader;

public class ThreadWait extends Thread {

	JDialog JD ;
	int i=0;
	JLabel label = new JLabel("i");
	JPanel panel = new JPanel();
	JProgressBar progressBar = new JProgressBar();
	private boolean Stop;
	
	public ThreadWait(JFrame jf){	
		JD = new JDialog(jf,"Encours",true);
	}
	
	public void run() {
		
	
		// Traitement du thread
		
		System.out.println("TOTO");
		this.init();
		//i=0;
		//Stop = false;
		//while (!Stop)
			//{
			//i++;
			//label.setText(new Integer(i).toString());
		  	//label.validate();
		  	
			//}
		
		
		System.out.println("balbla");
		//JD.setVisible(false);
		//Stop = false;
		
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
	
	public void init()
	{
		
		JD.setSize(300, 300);
		panel.add(label);
		//panel.add(progressBar);
		panel.add(label);
		
		//JD.getContentPane().add(progressBar);
		JD.getContentPane().add(label);
		
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		progressBar.setPreferredSize(new Dimension(250, 20));
		JPanel progressPanel = new JPanel();
		progressPanel.add(progressBar);
		progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JD.add(progressPanel, BorderLayout.CENTER);
		JD.pack();
	}
	
	
}
