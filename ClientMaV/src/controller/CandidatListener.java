package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.uiVote.EffectuerVote;

public class CandidatListener implements ActionListener {
	private EffectuerVote fen;
	private String nomCandidat;
	private int idCandidat;
	
	public CandidatListener(EffectuerVote f, String n, int id)	{
		fen = f;
		nomCandidat = n;
		idCandidat = id;
	}
	public void actionPerformed (ActionEvent e) {
         //Invoked when an action occurs. 		
		fen.setCandidatSelectionne(nomCandidat);
		fen.setIdCandidatSelectionne(idCandidat);
	}
}
