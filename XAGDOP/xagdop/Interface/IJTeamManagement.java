package xagdop.Interface;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import xagdop.Controleur.CTeamManagement;
import xagdop.Model.Project;
import xagdop.Parser.ProjectsParser;
import xagdop.ressources.Bundle;


import xagdop.Interface.IAffect; 


public class IJTeamManagement extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1080162447493236178L;
	private static IJTeamManagement IJTM= null;
	
	
	private JButton ButtonCancel = new JButton();
    private JButton ButtonOK = new JButton();
    private JButton ButtonAffecter = new JButton();
    private JPanel newPanel = new JPanel();
    private JTable JT;
    private JScrollPane JSP;
	private GridBagConstraints gbc = new GridBagConstraints();
	private ProjectsParser projects;
	private Project projet;
	private CTeamManagement CTeamM; 
	
	private String nomProjet;
	public static String nP;
	
	private IJTeamManagement(String ProjectName){
		nP = ProjectName;
		this.nomProjet = ProjectName ;
		projects = ProjectsParser.getInstance();
		init();
	}
	
	
	private void init(){
		getContentPane().setLayout(new GridBagLayout());
		nP = this.getNomProjet();
		this.projet = this.getProjectParser().buildProject(this.nomProjet);
		System.out.println(nomProjet);
		CTeamM = new CTeamManagement(nP);		
				
		newPanel.setLayout(new GridBagLayout());
		
		newPanel.setMinimumSize(new Dimension(296, 130));
		
		ButtonOK.setText(Bundle.getText("ijteam.jtable.ok"));
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	
            	int j =JT.getRowCount();            	
        		int i=0;
        		while(i<j)
        		{
        			CTeamM.Apply(projects,(String)JT.getValueAt(i,0),((Boolean)JT.getValueAt(i,1)).booleanValue(),((Boolean)JT.getValueAt(i,2)).booleanValue(),((Boolean)JT.getValueAt(i,3)).booleanValue(),((Boolean)JT.getValueAt(i,4)).booleanValue());
        			i++;  
        		}        	
            	
            	(IJAdmin.getIJA()).dispose();
             
                  
                //fermer la fenetre
            	dispose();
            }
        });	// Fin bouton OK
        
        ButtonAffecter.setText(Bundle.getText("ijteam.jtable.affecter"));
        ButtonAffecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	
            	IAffect IA = IAffect.getIA();
        		IA.setLocation(300,300);
           	    IA.setProjectName(nP);
           	    IA.setVisible(true);
           	    IJTM.refreshUsers();             
           	    getContentPane().validate();	    
            }
        }); // Fin bouton Affecter
        
 
        ButtonCancel.setText(Bundle.getText("button.cancel"));
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                 //Fermer la fenetre
            	dispose();
            }
        }); // Fin bouton Cancel
        
        
        JT = new JTable(new IJTeamManagementTableModel(projet.getUsersLogin(),nP));
        JT.setDefaultRenderer(JButton.class, new IJTeamManagementTableCellrenderer());
        JT.setDefaultEditor(JButton.class, new IJTeamManagementTableCellEditor(this,this.nomProjet));
        JT.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JT.setSize(new Dimension(650,200));
        JT.setPreferredScrollableViewportSize(JT.getSize());
        JT.getColumnModel().getColumn(0).setResizable(false);
        JT.getColumnModel().getColumn(1).setResizable(false);
        JT.getColumnModel().getColumn(2).setResizable(false);
        JT.getColumnModel().getColumn(3).setResizable(false);
        JT.getColumnModel().getColumn(4).setResizable(false);
        JT.getColumnModel().getColumn(5).setResizable(false);
        
        JT.getColumnModel().getColumn(0).setMinWidth(200);
        JT.getColumnModel().getColumn(1).setMinWidth(105);
        JT.getColumnModel().getColumn(2).setMinWidth(105);
        JT.getColumnModel().getColumn(3).setMaxWidth(105);
        JT.getColumnModel().getColumn(4).setMaxWidth(105);        
        JT.getColumnModel().getColumn(5).setMaxWidth(100);	// colonne desaffecter
        
        this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
        JSP = new JScrollPane(JT);
        newPanel.add(JSP,gbc);
        this.donnerContrainte(gbc,0,1,1,1,100,100,GridBagConstraints.NONE);
        newPanel.add(ButtonOK, gbc);
        this.donnerContrainte(gbc,1,1,1,1,100,100,GridBagConstraints.NONE);
        newPanel.add(ButtonAffecter,gbc);
        this.donnerContrainte(gbc,2,1,1,1,100,100,GridBagConstraints.NONE);
        newPanel.add(ButtonCancel,gbc);
        
        
        getContentPane().add(newPanel, new GridBagConstraints());
        pack();
        this.getContentPane().validate();
        IJTM=this;
        super.setTitle(Bundle.getText("main.button.team"));
        super.setVisible(true);
	}// Fin Init
	
	
	public void refreshUsers()
	{
		GridBagConstraints gbc = new GridBagConstraints();
        this.donnerContrainte(gbc,0,0,3,1,100,100,GridBagConstraints.BOTH);
        
		this.projects = ProjectsParser.getInstance();
		this.projet = this.projects.buildProject(this.nomProjet);
		((IJTeamManagementTableModel)this.JT.getModel()).init(this.projet);
		JT.setDefaultEditor(JButton.class, new IJTeamManagementTableCellEditor(this,this.nomProjet));
		this.remove(newPanel);
		this.newPanel.remove(JSP);
		
		JSP = new JScrollPane(JT);
		this.newPanel.add(JSP,gbc);
		this.getContentPane().add(newPanel, new GridBagConstraints());
		this.pack();
		this.getContentPane().validate();
	}
	
	
	/** Panel de message de fin de transaction
     * @param gbc <CODE>GridBagConstraints</CODE> represente la contrainte qui va prendre les
     * valeurs specifiees
     * @param gx <CODE>int</CODE> represente la colonne dans laquelle l'?l?ment va etre place
     * @param gy <CODE>int</CODE> represente la ligne dans laquelle l'?l?ment va etre place
     * @param gw <CODE>int</CODE> represente le nombre de colonnes sur lesquelles l'?l?ment va etre place
     * @param gh <CODE>int</CODE> represente le nombre de lignes sur lesquelles l'?l?ment va etre place
     * @param wx <CODE>int</CODE> poucentage de place utilise dans sa colonne
     * @param wy <CODE>int</CODE> poucentage de place utilise dans sa ligne
     */
	public void donnerContrainte(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy)
    {
		gbc.gridx=gx;
		gbc.gridy=gy;
		gbc.gridwidth=gw;
		gbc.gridheight=gh;
		gbc.weightx=wx;
		gbc.weighty=wy;
		gbc.fill=GridBagConstraints.NONE;
    }
    
    /** Panel de message de fin de transaction
     * @param gbc <CODE>GridBagConstraints</CODE> represente la contrainte qui va prendre les
     * valeurs specifiees
     * @param gx <CODE>int</CODE> represente la colonne dans laquelle l'?l?ment va etre place
     * @param gy <CODE>int</CODE> represente la ligne dans laquelle l'?l?ment va etre place
     * @param gw <CODE>int</CODE> represente le nombre de colonnes sur lesquelles l'?l?ment va etre place
     * @param gh <CODE>int</CODE> represente le nombre de lignes sur lesquelles l'?l?ment va etre place
     * @param wx <CODE>int</CODE> poucentage de place utilise dans sa colonne
     * @param wy <CODE>int</CODE> poucentage de place utilise dans sa ligne
     * @param constraint <CODE>int</CODE> contrainte de redimensionnement
     */    
    public void donnerContrainte(GridBagConstraints gbc, int gx, int gy, int gw, int gh, int wx, int wy, int constraint)
    {
    	gbc.gridx=gx;
    	gbc.gridy=gy;
    	gbc.gridwidth=gw;
    	gbc.gridheight=gh;
    	gbc.weightx=wx;
    	gbc.weighty=wy;
    	gbc.fill=constraint;
    }
	
	
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}


	public String getNomProjet() {
		return nomProjet;
	}

	public static IJTeamManagement getIJTM(String nP) {
		
		if (IJTM==null){
			IJTM = new IJTeamManagement(nP); 
		}
		IJTM.setNomProjet(nP);
		
		return IJTM;
	}
	
	public JTable getTable() {
		return this.JT;
	}
	
	/*public ArrayList getUsers(int g)
	{
		ArrayList usersProject = new ArrayList();
		ArrayList usersProjetId = this.projet.getUsersLogin();
		Iterator i = this.users.getAllUsers().iterator();
		Iterator j = usersProjetId.iterator();
		Integer usersProjet;
		Users usersGlobal;
		while(j.hasNext())
		{
			usersProjet = ((Integer)j.next());
			
			while(i.hasNext())
			{
				usersGlobal = ((Users)i.next());
				if(usersGlobal.getId()==usersProjet.intValue())
				{
					usersProject.add(usersGlobal);
				}
			}
			
			i = this.users.getAllUsers().iterator();
			
			
		}
		return usersProject;
	}*/
	
	public ProjectsParser getProjectParser()
	{
		return this.projects;
	}
	
}