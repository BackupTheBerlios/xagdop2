package xagdop.Interface;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

public class ICheckOut extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel JLBTitle;
	private JTable JTProjets;
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public ICheckOut()
	{
		this.init();
	}
	
	public void init()
	{
		this.setLayout(gbl);
		this.JLBTitle = new JLabel("salut");
		this.donnerContrainte(gbc,0,0,1,1,100,100);
		this.getContentPane().add(this.JLBTitle,gbc);
		
		//this.setSize();
		String[] str = {"moi","toi","lui","elle","nous","vous","ils"};
		this.JTProjets = new JTable(new ProjectTableModel(str));
		//this.JLProjets.setCellRenderer(new ListProjectCellRenderer());
		//this.JLProjets.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//this.JLProjets.add(new JCheckBox("cucu",true));
		this.donnerContrainte(gbc,0,1,1,1,100,100);
		//this.getContentPane().add(this.JLProjets,gbc);
		this.pack();
		this.setVisible(true);
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
	 
    class ProjectTableModel extends AbstractTableModel
    {

		private static final long serialVersionUID = 1L;
		private Object[] rowData;
		private String[] columns;

		public ProjectTableModel(String[] data)
		{
			this.rowData = data;
		}
		
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    
}
