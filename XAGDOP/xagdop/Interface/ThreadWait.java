package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

import javax.swing.JProgressBar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class ThreadWait extends Thread {


	JDialog JD = new JDialog();
	//	 Creation de la ProgressBar
	//JProgressBar PB_wait = new JProgressBar();
	//JD.getContentPane().add(PB_wait);
	JPanel panel = new JPanel();
	JLabel label = new JLabel("toto sa mere en rut ki suce les ours");
	JProgressBar progressBar = new JProgressBar();
	
	
	public ThreadWait(JFrame jf){	
		JD = new JDialog(jf,"Encours",true);
		//JD.setTitle("En cours");
		
	//	Stop = false;
		//PB_wait.add(JD);
		//JD.getContentPane().add(PB_wait);
		//JD.setLocation(300,300);		
		//JD.validate();		
		//JD.pack();
		//JD.setVisible(true);
		
	}

	
		
	
	
		// Traitement du thread
	
	public void run() {
	

		this.init();


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
		
		
		JD.getContentPane().add(panel);
		JD.setTitle("En cours");		
		JD.setSize(300, 100);
		
		panel.add(label);
		JD.getContentPane().add(label);
		
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		progressBar.setPreferredSize(new Dimension(250, 20));
		JPanel progressPanel = new JPanel();
		progressPanel.add(progressBar);
		progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JD.add(progressPanel, BorderLayout.CENTER);
		JD.setLocation(300,300);
		JD.setVisible(true);
		JD.pack();
	}
	
	
}
