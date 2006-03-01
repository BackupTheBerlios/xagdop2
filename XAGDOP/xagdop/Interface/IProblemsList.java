/*
 * ProblemsList.java
 *
 * Created on 10 f?vrier 2006, 19:38
 */

package xagdop.Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.AbstractTableModel;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Parser.DependenciesParser;
import xagdop.Parser.ProjectsParser;
import xagdop.ressources.Bundle;


/**
 *
 * @author  Drez
 */
public class IProblemsList extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2721910732360834605L;
	
	  private JPanel mainPanel;
	  private JTable problemsTable;
	  
	
	public IProblemsList() {
        initComponents();
    }
    
   
    private void initComponents() {
        mainPanel = new JPanel();
  

       
		
		
		
        
	    problemsTable = new JTable(new IProblemsListTableModel());

		problemsTable.setBorder(BorderUIResource.getBlackLineBorderUIResource()  );
        problemsTable.getColumnModel().getColumn(0).setResizable(false);
        problemsTable.getColumnModel().getColumn(0).setMaxWidth(30);
        problemsTable.getColumnModel().getColumn(1).setResizable(true);
        problemsTable.getColumnModel().getColumn(2).setResizable(true);
        problemsTable.getColumnModel().getColumn(3).setResizable(true);
        problemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        problemsTable.setSize(new Dimension(200,200));
        mainPanel.add(new JScrollPane(problemsTable), BorderLayout.CENTER);
        this.setSize(new Dimension(800,800));
        getContentPane().add(mainPanel, BorderLayout.CENTER);

       pack();
    }
    
    
	class IProblemsListTableModel extends AbstractTableModel 
	{
		

		/**
		 * 
		 */
		private static final long serialVersionUID = -6660241017263485617L;

		private String[] columnNames = {"",
				Bundle.getText("iproblemlist.colonne.description"),
				Bundle.getText("iproblemlist.colonne.ressource"), 
				Bundle.getText("iproblemlist.colonne.projet")};
		
		
		private ArrayList problems;
		
		/**
		 * 
		 * @param users: Liste des utilisateurs du systeme creee par le parser XML
		 * @docRoot Ce constructeur appelle la fonction <i>init</i>
		 */
		public IProblemsListTableModel()
		{
			this.init();
		}
		
		/**
		 * 
		 * @param users: Liste des utilisateurs du systeme creee par le parser XML
		 * Fonction qui initialise le modele et remplit la structure de donnees avec
		 * les elements necessaires
		 */
		public void init()
		{
			
			
			 //Initialisation des problemes
	        problems = new ArrayList();
	        String[] oneProblem = new String[]{null,null,null,null};
	        int j;
	        //On recupere la liste des projet de l'utilisateur
	    	try {
				ArrayList data = ProjectsParser.getInstance().getProjects(XAGDOP.getInstance().getUser().getLogin());
//				Pour tous les projets, on recupere les toUpdate et les toCreate de chaque fichier
				for(int i=0;i<data.size();i++)
				{
					DependenciesParser.getInstance().setFile((String)data.get(i));
					//Recuperation de la liste des updates
					//ArrayListdp.getToUpdate
					ArrayList toUpdate = DependenciesParser.getInstance().getAllToUpdate();
					for (j=0;j<toUpdate.size();j++)
					{
						oneProblem = new String[]{null,null,null,null};
							//Remplissage du tableau
						oneProblem[0] = "U";
						oneProblem[1] = Bundle.getText("iproblemlist.label.update");
						oneProblem[2] = (String)toUpdate.get(j);
						oneProblem[3] = (String)data.get(i);
						problems.add(oneProblem);
					}
					//Recuperation de la liste des Creates
					//ArrayListdp.getToUpdate
					ArrayList toCreate = DependenciesParser.getInstance().getAllToCreate();
					for (j=0;j<toCreate.size();j++)
					{
						oneProblem = new String[]{null,null,null,null};
						//Remplissage du tableau
						oneProblem[0] = "C";
						oneProblem[1] = Bundle.getText("iproblemlist.label.create");
						oneProblem[2] = (String)toCreate.get(j);
						oneProblem[3] = (String)data.get(i);
						problems.add(oneProblem);
					}
					
					
					
				}
				
	    	} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			
		}

		/**
		 * Retourne le nombre de lignes de la structure de donnees
		 */
		public int getRowCount() 
		{
			// TODO Auto-generated method stub
			return this.problems.size();
		}

		/**
		 * Retourne le nombre de colonnes de la structure de donnees
		 */
		public int getColumnCount() 
		{
			// TODO Auto-generated method stub
			return this.columnNames.length;
		}

		/**
		 * @param: row - ligne a laquelle on recupere la donnee
		 * @param: col - colonne a laquelle on recupere la donnee
		 * Retourne la valeur de la cellule dont les coordonnees sont
		 * passees en parametres.
		 */
		public Object getValueAt(int row, int col) 
		{
			// TODO Auto-generated method stub
			return ((String[])this.problems.get(row))[col];
		}
		
		/**
		 * @param: col - colonne a laquelle on recupere la donnee
		 * Retourne le nom de la colonne dont l'indice est passe 
		 * en parametre.
		 * Cette fonction est utilisee par le JScrollPane pour afficher
		 * titres de colonne
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		/*
	     * JTable uses this method to determine the default renderer/
	     * editor for each cell.  If we didn't implement this method,
	     * then columns which contain booleans would contain text 
	     * ("true"/"false"), rather than a check box and the last column
	     * would contain a string representation of the JButton defined.
	     */
	    public Class getColumnClass(int c) 
	    {
	        return this.getValueAt(0, c).getClass();
	    }

	    //
	    /**
		 * @param: row - ligne 
		 * @param: col - colonne
		 * Methode utilisee pour definir si une cellule de la JTable 
		 * dont les coordonnees sont passees en parametres est modifiable
		 * ou non
		 */
	    public boolean isCellEditable(int row, int col) 
	    {
	    	    return false;
	    }
	    
	    

	    
	}

    
    
    
    
}
