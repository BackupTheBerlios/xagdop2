package xagdop.Model;


import java.io.File;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;

import xagdop.Controleur.CTreeNode;
import xagdop.Svn.SvnHistory;

public class DirectoryModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected File directory;
    protected String[] children;
    protected int rowCount;
    protected Object dirIcon;
    protected Object fileIcon;
    protected File file;
	protected ArrayList infos;

    public DirectoryModel() {
        init();
    }

    public DirectoryModel( CTreeNode dir ) {
        init();
        file = (File)dir.getUserObject();
        directory = (File)dir.getUserObject();
        if(!directory.exists())
        	directory.mkdir();
        
        try {
			infos = SvnHistory.getInfo(dir);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        children = directory.list();
        rowCount = children.length;
    }

    protected void init() {
        dirIcon = UIManager.get( "DirectoryPane.directoryIcon" );
        fileIcon = UIManager.get( "DirectoryPane.fileIcon" );
    }

    public void setDirectory( CTreeNode dir ) {
        if ( dir != null ) {
        	 directory = (File)dir.getUserObject();
             children = directory.list();
            rowCount = children.length;
        }
        else {
            directory = null;
            children = null;
            rowCount = 0;
        }
        file = (File)dir.getUserObject();
		try {
			infos = SvnHistory.getInfo(dir);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        fireTableDataChanged();
    }

    public int getRowCount() {
    	
    	return infos != null ? infos.size() :  0;

    }

    public int getColumnCount() {
    	return 3;
    }

    public Object getValueAt(int row, int column){
        switch ( column ) {

        case 0:
        	return new Double(1.0+(infos.size()-row)/100);

        case 1:
        	
        		ArrayList comment =  new ArrayList(infos);
        		return ((SVNLogEntry)comment.get(row)).getMessage();
        
        case 2:
        		ArrayList author =  new ArrayList(infos);
        		 return ((SVNLogEntry)author.get(row)).getAuthor();

        default:
            return "";
        }
    }

    public String getColumnName( int column ) {
        switch ( column ) {
        case 0:
            return "Version";
        case 1:
            return "Commentaires";
        case 2:
            return "Auteur";
        default:
            return "unknown";
        }
    }

    public Class getColumnClass( int column ) {
            return super.getColumnClass( column );

    }
}                   
