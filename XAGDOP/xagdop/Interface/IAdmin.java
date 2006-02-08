package xagdop.Interface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.xpath.XPathExpressionException;

import org.tmatesoft.svn.core.SVNException;

import xagdop.Controleur.CAdmin;
import xagdop.Model.User;
import xagdop.Parser.UsersParser;

public class IAdmin extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048783116748619019L;
	private static IAdmin IA = null;
	
	private JCheckBox AdminCheck = new JCheckBox();
    private JLabel AdminLabel = new JLabel();
    private JButton ButtonApply = new JButton();
    private JButton ButtonCancel = new JButton();
    private JButton ButtonOK = new JButton();
    private JButton ButtonCreateUser = new JButton();
    private JCheckBox PManagerCheck = new JCheckBox();
    private JLabel PManagerLabel = new JLabel();
    private JLabel UserLabel = new JLabel();
    private JComboBox UserListCombo = new JComboBox();
    private JPanel newPanel = new JPanel();
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private UsersParser users;
    private CAdmin cadmin;
    
	private IAdmin() {
		init();
	}
	
	
	private void init() {
        getContentPane().setLayout(new GridBagLayout());
        cadmin = new CAdmin();

       
        ArrayList list = null;
		try {
			 users = UsersParser.getInstance();
			list = users.getAllUsers();
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        newPanel.setLayout(new GridBagLayout());

        newPanel.setMinimumSize(new Dimension(296, 130));
        UserLabel.setText("Selectionner l'utilisateur");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        newPanel.add(UserLabel, gridBagConstraints);
        UserLabel.getAccessibleContext().setAccessibleName("Choisir l'utilisateur");


        

    	//Remplissage de la combobox avec les valeurs de la list
      	for(int i=0; i<list.size(); i++)
    	{
      		UserListCombo.addItem(((User)list.get(i)).getLogin());
		}   
      	
      	
        UserListCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	refresh();
            	
            	
            	
            	
            }
        });

       
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        newPanel.add(UserListCombo, gridBagConstraints);


        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        newPanel.add(AdminCheck, gridBagConstraints);

       
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        newPanel.add(PManagerCheck, gridBagConstraints);

        AdminLabel.setText("Administrateur");
        
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        newPanel.add(AdminLabel, gridBagConstraints);

        PManagerLabel.setText("Chef de Projet");
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        newPanel.add(PManagerLabel, gridBagConstraints);

        ButtonOK.setText("Ok");
        ButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               try {
				cadmin.Apply(users,(String)UserListCombo.getSelectedItem(),AdminCheck.isSelected(),PManagerCheck.isSelected());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        });

        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonOK, gridBagConstraints);

        ButtonCancel.setText("Annuler");
        ButtonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                IA.dispose();
            }
        });

        
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        newPanel.add(ButtonCancel, gridBagConstraints);

        ButtonApply.setText("Appliquer");
        
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        ButtonApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               try {
				cadmin.Apply(users,((String)UserListCombo.getSelectedItem()),AdminCheck.isSelected(),PManagerCheck.isSelected());
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        });
        newPanel.add(ButtonApply, gridBagConstraints);

        ButtonCreateUser.setText("Creer Nouvel User");
        ButtonCreateUser.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		IUserCreate IUC = IUserCreate.getIUC();
        		IUC.setVisible(true);
        	}
        });
        
        newPanel.add(ButtonCreateUser, new GridBagConstraints());

        getContentPane().add(newPanel, new GridBagConstraints());

        pack();
		setTitle("Administration");
		//setSize(300, 200);
		
	}
	
	public void refresh(){
		try {
			AdminCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isAdmin());
			PManagerCheck.setSelected(users.getUserByLogin((String)UserListCombo.getSelectedItem()).isPcreator());
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		}
	
	
	
	
	/**
	 * @return Returns the singleton.
	 */
	public static IAdmin getIA() {
		if (IA==null){
			IA = new IAdmin(); 
		}
		
		return IA;
	}
}