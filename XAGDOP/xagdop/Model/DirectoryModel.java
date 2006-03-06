package xagdop.Model;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import xagdop.Controleur.CTreeNode;
import xagdop.Interface.XAGDOP;
import xagdop.Svn.SvnHistory;

public class DirectoryModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    protected File file;
	protected ArrayList infos;

   
    public DirectoryModel( CTreeNode dir ) {
        
        file = (File)dir.getUserObject();
        if(!file.exists())
        	file.mkdir();
  
    }

    
    public void setDirectory( CTreeNode dir ) {
       
        file = (File)dir.getUserObject();
		try {
			infos = SvnHistory.getInfo(dir);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
        fireTableDataChanged();
    }

    public int getRowCount() {
    	
    	return infos != null ? infos.size() :  0;

    }

    public int getColumnCount() {
    	return 5;
    }

    public Object getValueAt(int row, int column){
        switch ( column ) {

        case 0:
        	return new Double(1.0+(infos.size()-row)/10.0);
        	
        case 1:
        	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
        	return formatter.format(((SVNLogEntry)infos.get(infos.size()-row-1)).getDate());
        	
        case 2:
        	return ((SVNLogEntry)infos.get(infos.size()-row-1)).getMessage();
        	
        case 3:
        	return ((SVNLogEntry)infos.get(infos.size()-row-1)).getAuthor();
        	
        case 4:
        	return new JButton(new ImageIcon(XAGDOP.class.getResource("/xagdop/ressources/Icon/delete.jpg")));

        default:
            return "";
        }
    }

    public String getColumnName( int column ) {
        switch ( column ) {
        case 0:
            return "Version";
        case 1:
            return "Date";
        case 2:
            return "Commentaires";
        case 3:
            return "Auteur";
        case 4:
        	return "Récuperer";
        default:
            return "unknown";
        }
    }

    public Class getColumnClass( int column ) {
    	System.out.println(getValueAt(0, column).getClass());
    	return getValueAt(0, column).getClass();

    }
  
}   

