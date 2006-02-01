package xagdop.Interface;

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
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;


public class IPreferencesFile extends JFrame {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = -3923804882288240982L;
	private static IPreferencesFile IPF = null ;
	
    /** Creates new form IPro */
    private IPreferencesFile() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

       JPanel Panel = new JPanel();
       final JTextField preferencePath = new JTextField();
       JButton BrowseButton = new JButton();
       JButton ValidateButton = new JButton();
       
        getContentPane().setLayout(new GridBagLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel.setLayout(new GridBagLayout());

        preferencePath.setText("textextex");
        
        
        



        gridBagConstraints.insets = new Insets(0, 0, 0, 19);
        Panel.add(preferencePath, gridBagConstraints);

        BrowseButton.setText("Browse !!");
        BrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	JFileChooser openPreferences = new JFileChooser(".") ;
            	FiltreSimple erf = new FiltreSimple("Fichiers zip (*.zip)",".zip");
     		   openPreferences.addChoosableFileFilter(erf);
     		  openPreferences.setCurrentDirectory(new File(".")) ;
  		    openPreferences.setDialogTitle("ouvrir un fichier :p") ;
  		  int resultVal = openPreferences.showOpenDialog(IPreferencesFile.getInstance()) ; // get the user's choice
		    if (resultVal == JFileChooser.APPROVE_OPTION) // user opened a file
			{
			    String inputFile  = openPreferences.getSelectedFile().getAbsolutePath() ; // getting file name etc...
			    preferencePath.setText(inputFile);
			}

            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            }
        });

        Panel.add(BrowseButton, gridBagConstraints);
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        Panel.add(ValidateButton, gridBagConstraints);
        
        getContentPane().add(Panel, gridBagConstraints);

        pack();
    }
  



    
    public static IPreferencesFile getInstance(){
    	if ( IPF == null )
    		IPF = new IPreferencesFile();
    	
		return IPF;
    	
    }

    public class FiltreSimple extends FileFilter{
        //Description et extension accept�e par le filtre
        private String description;
        private String extension;
        //Constructeur � partir de la description et de l'extension accept�e
        public FiltreSimple(String description, String extension){
           if(description == null || extension ==null){
              throw new NullPointerException("La description (ou extension) ne peut �tre null.");
           }
           this.description = description;
           this.extension = extension;
        }
        //Impl�mentation de FileFilter
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
