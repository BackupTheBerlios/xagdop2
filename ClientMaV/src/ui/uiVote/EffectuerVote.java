package ui.uiVote;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import src.util.UtilORB;
import ui.uiManagement.CandidatPanel;
import MaV.Candidat;
import MaV.Electeur;
import MaV.ListeC;
import MaV.Votant;
import controller.CandidatListener;
import controller.DetailCandidatListener;
import controller.EffectuerVoteListener;
import controller.VotezListener;


public class EffectuerVote extends JFrame {


	private static final long serialVersionUID = -3066723065684758861L;

	// Nombre de candidats
	private int nbCandidats = 0;
	
	// Nom de l electeur
	private String nomVotant = "";
	// Insee electeur
	private int numINSEEVotant = 0;
	private int codeVotant = 0;
	private Electeur electeur = null;
	
	private int idCandidatSelectionne = 0;
	
	Candidat[] tableauCandidats = null;
	

	
	//annonce introduction
	JLabel introduction;
	// Candidat selectionne
	JTextField candidatSelectionne;
	// Vote
	JButton voter;
	
	private CandidatPanel jPanel = null;

	
	
	/**
	 * Creer la fenetre qui permet de voter
	 * @param numInsee
	 */
	public EffectuerVote(int numInsee, int code) {
		super();
		
		numINSEEVotant = numInsee;
		codeVotant = code;
		
		Votant v;
		try {
			v = UtilORB.getVotant();
			electeur = v.verifierElecteur(numInsee,codeVotant);
			nomVotant = electeur.prenom + " " + electeur.nom;
			
			ListeC listeCandidats = UtilORB.getListeC();	
			tableauCandidats = listeCandidats.getAllCandidats();
			
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotProceed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidName e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize();
		EffectuerVoteListener ef = new EffectuerVoteListener(this);
		this.addWindowListener(ef);

	}
	
	/*
	public static void main(String[] args) {
		EffectuerVote toto = new EffectuerVote(1,1);
		System.out.println("bou");
	}
	*/

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		setTitle("Votez");
		Container c;
	
		
		//Definition de la fenetre elle meme
		this.setBounds(10,10,300,300);
		this.setSize(new Dimension(650, 350)); 
		this.setResizable(false);
		
		//Creation des composants et insertion dans la fen�tre
		c = this.getContentPane();
		
		//placement des differents elements
		c.setLayout(new GridBagLayout());

		
		// premiere ligne
		introduction = new JLabel("Bonjour "+nomVotant+", veuillez proceder au vote s'il vous plait");
		c.add(introduction, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
				,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		
		// deuxieme ligne
		Container deuxiemeLigne = new Container();
		deuxiemeLigne.setLayout(new GridLayout(1,2));
		
		candidatSelectionne = new JTextField();
		candidatSelectionne.setText("");
		candidatSelectionne.setEditable(false);
		deuxiemeLigne.add(candidatSelectionne);
		
		voter = new JButton("Votez definitivement pour ce candidat");
		voter.setBackground(Color.ORANGE); 
		
		voter.addActionListener(new VotezListener(this,numINSEEVotant));
		
		//Voter.setEnabled(false);
		deuxiemeLigne.add(voter);
		
		c.add(deuxiemeLigne, new GridBagConstraints(1, 2, 2, 2, 2.0, 2.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		
		

		// troisieme ligne
		Container troisiemeLigne = new Container();
		troisiemeLigne.setLayout(new GridLayout(nbCandidats,2));

		Candidat candidat = null;
		
		Color couleurTexte;
		for (int i=0;i<tableauCandidats.length;i++) {
			candidat = tableauCandidats[i];
			JButton candButton = new JButton(candidat.prenom() + " " + candidat.nom());
			
			candButton.addActionListener(new CandidatListener(this,candButton.getText(),candidat.id()));
			
			JButton info = new JButton("Plus d informations sur ce candidat");
			info.addActionListener(new DetailCandidatListener(this,candidat));
			
			if (i%2!=0)
				couleurTexte = Color.CYAN.darker();
			else
				couleurTexte = Color.BLUE.darker();
			
			candButton.setForeground(couleurTexte); 
			info.setForeground(couleurTexte); 
			
			troisiemeLigne.add(candButton);
			
			troisiemeLigne.add(info);
		}
		JButton candButton = new JButton("blanc");
		
		candButton.addActionListener(new CandidatListener(this,candButton.getText(),0));
		
		troisiemeLigne.add(candButton);
		
		
		c.add(troisiemeLigne, new GridBagConstraints(1, 5, 5, 5, 0.0, 2.0
				,GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	
		
	
		
		
		setVisible(true);
	}
	
	public void setCandidatSelectionne(String nom){
		candidatSelectionne.setText(nom);
	}

	public int getIdCandidatSelectionne() {
		return idCandidatSelectionne;
	}

	public void setIdCandidatSelectionne(int idCandidatSelectionne) {
		this.idCandidatSelectionne = idCandidatSelectionne;
	}

	public Electeur getElecteur() {
		return electeur;
	}

	public void setElecteur(Electeur electeur) {
		this.electeur = electeur;
	}

	public JTextField getCandidatSelectionne() {
		return candidatSelectionne;
	}

	public void setCandidatSelectionne(JTextField candidatSelectionne) {
		this.candidatSelectionne = candidatSelectionne;
	}

	public CandidatPanel getJPanel() {
		return jPanel;
	}

	public void setJPanel(CandidatPanel panel) {
		jPanel = panel;
	}
}
