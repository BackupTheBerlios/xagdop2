package xagdop.Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import xagdop.Model.User;

public class IJTeamManagementTableCellEditor extends AbstractCellEditor 
										implements TableCellEditor, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6769375527688617834L;
	protected static final String EDIT = "edit";
	
    private JButton JB;
    private IJTeamManagement IJTM;
    private String nomProjet;
    
    
    public IJTeamManagementTableCellEditor(IJTeamManagement IJTM, String projet) 
    {
    	this.nomProjet = projet;
    	JB = new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/supprimer.gif")));
    	JB.setActionCommand(EDIT);
    	JB.addActionListener(this);
    	JB.setBorderPainted(false);
        this.IJTM = IJTM;
    }

	
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (EDIT.equals(e.getActionCommand())) {
			this.desaffecterUser();			
		}
		
	}

	 public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) 
	    {
	        return JB;
	    }

	public void desaffecterUser() {
		
		int rowToDelete;
        
    	int out = JOptionPane.showOptionDialog(this.IJTM.getTable(),new String("Etes-vous sur de vouloir desaffecter cet utilisateur ?"),new String("Suppression d'un utilisateur"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
		if(out == JOptionPane.YES_OPTION) 
		{
			rowToDelete =this.IJTM.getTable().getSelectedRow();
		//	System.out.println("user : "+((String)this.IJTM.getTable().getModel().getValueAt(rowToDelete,0)));
		//	Iterator i = this.IJTM.getUsers().iterator();
			
		//	while(i.hasNext())
		//	{
		//		Users o = ((Users)i.next());
		//		if(o.getLogin() == ((String)this.IJTM.getTable().getModel().getValueAt(rowToDelete,0)))
		//		{
					this.IJTM.getProjectParser().removeUser(this.nomProjet,((String)this.IJTM.getTable().getModel().getValueAt(rowToDelete,0)));
					IJTeamManagement.getIJTM(this.nomProjet).refreshUsers();
					
		//		}
				
			}
		}
	
	
}
