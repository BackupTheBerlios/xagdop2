package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.MessageDialogBox;
import src.util.UtilORB;
import ui.uiLogin.Login;
import ui.uiVote.EffectuerVote;
import MaV.Votant;

public class VotezListener implements ActionListener {
	private EffectuerVote fen;
	private int idCandidat;
	private int inseeElecteur;
	
	public VotezListener(EffectuerVote f, int num)	{
		fen = f;
		inseeElecteur = num;
	}
	public void actionPerformed (ActionEvent e) {
         //Invoked when an action occurs. 
		idCandidat = fen.getIdCandidatSelectionne();
		try {
			Votant v = UtilORB.getVotant();
			//v.votePour(idCandidat, inseeElecteur, new VoteCallBackImpl());
			/** TODO 
			 * Decommenter quand le call back fonctionnera
			 * Et supprimer la ligne d'en dessous
			 */
			v.votePour(idCandidat, inseeElecteur, fen.getElecteur().bureau);
			MessageDialogBox.showMessageDialog(fen, "Vote pris en compte", "Merci d'avoir voté");
			fen.dispose();
			new Login();
		} catch (NotFound e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CannotProceed e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidName e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
