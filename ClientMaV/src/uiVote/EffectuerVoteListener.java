package uiVote;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EffectuerVoteListener implements WindowListener {
	
	private EffectuerVote fen;
	
	public EffectuerVoteListener(EffectuerVote f)	{
		fen = f;
	}	

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {
		System.exit(0); // Arrêt de l'application
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}    

}