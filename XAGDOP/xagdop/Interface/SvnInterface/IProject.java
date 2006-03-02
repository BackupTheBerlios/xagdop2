package xagdop.Interface.SvnInterface;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.tmatesoft.svn.core.SVNException;
import xagdop.Controleur.CProject;
import xagdop.ressources.Bundle;


public class IProject extends JFrame{
	
	//private CProject cpro = new CProject();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1560367300092065010L;
	private static IProject IP = null;
	protected TextField TFNp; 
	protected TextArea TADesc;
	protected JLabel JlabelNameProject;
	protected JLabel JlabelDescProject;
	protected JPanel JPanelProject;
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	
	private IProject(){
		init();
	}
	
	
	private void init(){
		
		JPanelProject = new JPanel();
		JPanelProject.setLayout(new GridBagLayout());
		//JPanelProject.setMinimumSize(new Dimension(400,300));
		//Creation d un cadre dans la fenetre
		Border cadre = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder title = BorderFactory.createTitledBorder(cadre, Bundle.getText("iproject.cadre"));
		JPanelProject.setBorder(title);
		
		/* Nom du projet*/
		JlabelNameProject = new JLabel(Bundle.getText("iproject.label.projectname"));
		TFNp = new TextField(30);
		
		/*Description du projet*/
		JlabelDescProject = new JLabel(Bundle.getText("iproject.label.description"));
		TADesc=new TextArea(7,30);
		
		/*Creation boutons*/
		JButton ok = new JButton(Bundle.getText("iproject.button.ok"));
		JButton cancel = new JButton(Bundle.getText("button.cancel"));
		
		/*Affichage des champs*/
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 100;
        gridBagConstraints.weighty = 100;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10, 10, 0, 10);
        JPanelProject.add(JlabelNameProject, gridBagConstraints);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
       
        JPanelProject.add(TFNp, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10, 10, 0, 10);
        JPanelProject.add(JlabelDescProject, gridBagConstraints);
        
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
      
        
        JPanelProject.add(TADesc, gridBagConstraints);
		
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new Insets(20, 35, 10, 0);
        JPanelProject.add(ok, gridBagConstraints);

        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(20, 0, 10, 30);
        JPanelProject.add(cancel, gridBagConstraints);
        
        /*Action relative aux boutons*/		
        ok.addActionListener(new ActionListener()
				{
				    public void actionPerformed(ActionEvent e)
				    {
				    	CProject project= new CProject();			    	
				    	try{
				    		project.createProject(TFNp.getText(),TADesc.getText());
				    		JOptionPane.showMessageDialog(null ,"Le projet "+TFNp.getText()+" a bien ete cree ", "Validation" , 1) ;
				    		TADesc.setText("");
				    		TFNp.setText("");
				    	}catch(SVNException ex){
				    		JOptionPane.showMessageDialog(null ,"Impossible de se connecter au server subversion", "Validation" , 1) ;
				    	}
				    	catch(Exception ex){
				    		JOptionPane.showMessageDialog(null ,"Le projet "+TFNp.getText()+" n'a pu etre cree ", "Validation" , 1) ;
				    	}finally{
				    		IP.setVisible(false);
				    	}
				    }
				}) ;
        
        cancel.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		IP.dispose();
        	}
        });
        
        // Creation de la fenetre
		setTitle(Bundle.getText("iproject.title"));
		setLocation(300,200);
		setSize(300, 320);
		setResizable(false) ;
		getContentPane().add(JPanelProject,BorderLayout.CENTER);
		
		setVisible(true);
		pack();
		

		 
         
	}
	/**
	 * @return Returns the singleton.
	 */
	public static IProject getIP() {
		if (IP==null){
			IP = new IProject(); 
		}
		
		return IP;
	}
}