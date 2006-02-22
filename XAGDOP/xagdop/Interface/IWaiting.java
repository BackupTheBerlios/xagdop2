package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
	

public class IWaiting extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel = new JPanel();
	JLabel label = new JLabel("bla");
	JProgressBar progressBar = new JProgressBar();
	public IWaiting(JFrame jf){	
		this.init();
	}

	public void arreter()
	{
		this.setVisible(false);
		
	}
	public void demarrer()
	{
		this.setVisible(true);	
	}
	
	public void init()
	{
		this.getContentPane().add(panel);
		this.setTitle("En cours");		
		this.setSize(300, 100);
		panel.add(label);
		this.getContentPane().add(label);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(false);
		progressBar.setPreferredSize(new Dimension(250, 20));
		JPanel progressPanel = new JPanel();
		progressPanel.add(progressBar);
		progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(progressPanel, BorderLayout.CENTER);
		this.setLocation(300,300);
		this.pack();
	}
}