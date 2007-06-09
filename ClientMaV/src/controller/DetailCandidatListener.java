package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import src.util.CandidatClient;
import ui.uiManagement.CandidatPanel;
import ui.uiVote.EffectuerVote;
import MaV.Candidat;

public class DetailCandidatListener implements ActionListener {
	private EffectuerVote fen;
	private Candidat candidat;
	
	public DetailCandidatListener(EffectuerVote f, Candidat cand)	{
		fen = f;
		candidat = cand;
	}
	public void actionPerformed (ActionEvent e) {
		CandidatClient cand = new CandidatClient(candidat);
	
		CandidatPanel myCandidatPanel = new CandidatPanel(null);
		myCandidatPanel.completePanel(cand);
		myCandidatPanel.setEditable(false);
		myCandidatPanel.setVisible(true);
		
		
		JFrame candidatDetails = new JFrame();
		candidatDetails.setSize(500, 500);
		candidatDetails.setContentPane(myCandidatPanel); 
		candidatDetails.setVisible(true);
	}
}
