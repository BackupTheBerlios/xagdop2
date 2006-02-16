package xagdop.Interface.Preferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import xagdop.ressources.Bundle;


public class IPreferencesFile extends JFrame {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3923804882288240982L;
	private static IPreferencesFile IPF = null ;
	

    private IPreferencesFile() {
        initComponents();
    }
    
    
    private void initComponents() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

       JPanel Panel = new JPanel();
       final JTextField preferencePath = new JTextField();
       JButton BrowseButton = new JButton();
       JButton ValidateButton = new JButton();
       JButton cancelButton = new JButton();
        getContentPane().setLayout(new GridBagLayout());

       
        Panel.setLayout(new GridBagLayout());

        preferencePath.setText("");
        preferencePath.setColumns(15);
        
        



        gridBagConstraints.insets = new Insets(0, 0, 0, 19);
        Panel.add(preferencePath, gridBagConstraints);

        BrowseButton.setText(Bundle.getText("ipreferencefile.button.browse"));
        BrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	JFileChooser openPreferences = new JFileChooser(".") ;
            	FiltreSimple erf = new FiltreSimple("Fichiers xml (*.xml)",".xml");
     		   openPreferences.addChoosableFileFilter(erf);
     		  openPreferences.setCurrentDirectory(new File(".")) ;
  		    openPreferences.setDialogTitle(Bundle.getText("ipreferencefile.filechooser.title")) ;
  		  int resultVal = openPreferences.showOpenDialog(IPreferencesFile.getInstance()) ; // get the user's choice
		    if (resultVal == JFileChooser.APPROVE_OPTION) // user opened a file
			{
			    String inputFile  = openPreferences.getSelectedFile().getAbsolutePath() ; // getting file name etc...
			    preferencePath.setText(inputFile);
			}

            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            }
        });

        Panel.add(BrowseButton, gridBagConstraints);
        ValidateButton.setText("Valider");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        Panel.add(ValidateButton, gridBagConstraints);
        ValidateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	/*
            	 * Traitement a faire pour commiter la liste des fichiers pr�f�rences
            	 */	
            	
            	dispose();
            }
        });

        
        cancelButton.setText("Annuler");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        Panel.add(cancelButton, gridBagConstraints);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            		dispose();
            }
        });

        
        
        
        getContentPane().add(Panel, gridBagConstraints);

        pack();
    }
  



    
    public static IPreferencesFile getInstance(){
    	if ( IPF == null )
    		IPF = new IPreferencesFile();
    	
		return IPF;
    	
    }

    public class FiltreSimple extends FileFilter{
        //Description et extension accept???e par le filtre
        private String description;
        private String extension;
        //Constructeur ??? partir de la description et de l'extension accept???e
        public FiltreSimple(String description, String extension){
           if(description == null || extension ==null){
              throw new NullPointerException("La description (ou extension) ne peut ???tre null.");
           }
           this.description = description;
           this.extension = extension;
        }
        //Impl???mentation de FileFilter
        public boolean accept(File file){
           if(file.isDirectory()) { 
              return true; 
           } 
           String nomFichier = file.getName().toLowerCase(); 

           return nomFichier.endsWith(extension);
        }
           public String getDescription(){
           return description;
        }
     }    
}

