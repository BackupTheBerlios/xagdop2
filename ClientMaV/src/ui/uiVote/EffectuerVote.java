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

import controller.CandidatListener;
import controller.EffectuerVoteListener;


public class EffectuerVote extends JFrame {


	private static final long serialVersionUID = -3066723065684758861L;

	// Nombre de candidats
	private int nbCandidats = 5;
	
	// Nom de l'�lecteur
	private String nomVotant = "Nicolas Ricard";
	
	// Listeners
	EffectuerVoteListener effectuerVoteListener;
	CandidatListener candidatListener;
	
	//annonce introduction
	JLabel introduction;
	// Candidat selectionne
	JTextField candidatSelectionne;
	// Vote
	JButton Voter;

	
	
	/**
	 * This method initializes 
	 * 
	 */
	public EffectuerVote() {
		super();
		initialize();
		
		EffectuerVoteListener ef = new EffectuerVoteListener(this);
		this.addWindowListener(ef);

	}
	
	public static void main(String[] args) {
		EffectuerVote toto = new EffectuerVote();
		System.out.println("bou");
	}
	

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		setTitle("Votez");
		Container c;
	
		
		//D�finition de la fenetre elle meme
		this.setBounds(10,10,300,300);
		this.setSize(new Dimension(650, 350)); 
		this.setResizable(false);
		
		//Cr�ation des composants et insertion dans la fen�tre
		c = this.getContentPane();
		
		//placement des diff�rents �l�ments
		c.setLayout(new GridBagLayout());

		
		// premi�re ligne
		introduction = new JLabel("Bonjour "+nomVotant+", veuillez proc�der au vote s'il vous plait");
		c.add(introduction, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
				,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		
		// deuxi�me ligne
		Container deuxiemeLigne = new Container();
		deuxiemeLigne.setLayout(new GridLayout(1,2));
		
		candidatSelectionne = new JTextField();
		candidatSelectionne.setText("...");
		deuxiemeLigne.add(candidatSelectionne);
		
		Voter = new JButton("Votez d�finitivement pour ce candidat");
		Voter.setBackground(Color.ORANGE); 
		//Voter.setEnabled(false);
		deuxiemeLigne.add(Voter);
		
		c.add(deuxiemeLigne, new GridBagConstraints(1, 2, 2, 2, 2.0, 2.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		
		

		// troisieme ligne
		Container troisiemeLigne = new Container();
		troisiemeLigne.setLayout(new GridLayout(nbCandidats,2));
		
		Color couleurTexte;
		for (int i=0;i<nbCandidats;i++) {
			JButton candidat = new JButton("Candidat N�"+(i+1));
			
			candidatListener = new CandidatListener(this,candidat.getText());
			candidat.addActionListener(candidatListener);
			
			JButton info = new JButton("Plus d'informations sur ce candidat");
			
			if (i%2!=0)
				couleurTexte = Color.CYAN.darker();
			else
				couleurTexte = Color.BLUE.darker();
			
			candidat.setForeground(couleurTexte); 
			info.setForeground(couleurTexte); 
			
			troisiemeLigne.add(candidat);
			
			troisiemeLigne.add(info);
		}
		
		c.add(troisiemeLigne, new GridBagConstraints(1, 5, 5, 5, 0.0, 2.0
				,GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	
		
	
		
		
		setVisible(true);
	}
	
	public void setCandidatSelectionne(String nom){
		candidatSelectionne.setText(nom);
	}
}
