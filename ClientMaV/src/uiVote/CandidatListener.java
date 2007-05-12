package uiVote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CandidatListener implements ActionListener {
	private EffectuerVote fen;
	private String nomCandidat;
	
	public CandidatListener(EffectuerVote f, String n)	{
		fen = f;
		nomCandidat = n;
	}
	public void actionPerformed (ActionEvent e) {
         //Invoked when an action occurs. 		
		fen.candidatSelectionne.setText(nomCandidat);
	}
}
