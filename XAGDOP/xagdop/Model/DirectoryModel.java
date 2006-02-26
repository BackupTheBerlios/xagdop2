package xagdop.Model;


import java.io.File;
import java.util.Collection;

import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import xagdop.Controleur.CTreeNode;

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
	protected Collection infos;

    public DirectoryModel() {
        init();
    }

    public DirectoryModel( CTreeNode dir ) {
        init();
        file = (File)dir.getUserObject();
        directory = (File)dir.getUserObject();
        if(!directory.exists())
        	directory.mkdir();
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
		/*try {
			infos = SvnHistory.getInfo(dir);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        fireTableDataChanged();
    }

    public int getRowCount() {
    	
    	//if(infos != null) return infos.size(); else return 0;
        return children != null ? rowCount : 0;
    }

    public int getColumnCount() {
    	//return 3;
    	return children != null ? 3 : 0;
       
    }

    public Object getValueAt(int row, int column){
        if ( directory == null || children == null ) {
            return null;
        }

        File fileSysEntity = new File( directory, children[row] );

        switch ( column ) {
//        case 0:
//			return new Double(1.0+(infos.size()-row)/100);*/
//		case 0:
//			comment =  new ArrayList(infos);
//			return ((SVNLogEntry)comment.get(row)).getMessage();
//		case 1:
//			ArrayList author =  new ArrayList(infos);
//			return ((SVNLogEntry)author.get(row)).getAuthor();
//		default:
//			return "";
        case 0:
//        	return new Double(1.0+(infos.size()-row)/100);
        	return "";
        case 1:
//        	if(infos!=null){
//        		ArrayList comment =  new ArrayList(infos);
//        		System.out.println( ((SVNLogEntry)comment.get(row)).getMessage());
//        		return ((SVNLogEntry)comment.get(row)).getMessage();
//        	}
//        	return "";
            return fileSysEntity.getName();
        case 2:
//        	if(infos!=null){
//        		ArrayList author =  new ArrayList(infos);
//        		System.out.println( ((SVNLogEntry)author.get(row)).getAuthor());
//        		 return ((SVNLogEntry)author.get(row)).getAuthor();
//        	}
//        	return "";
            if ( fileSysEntity.isDirectory() ) {
                return "--";
            }
            else {
                return new Long( fileSysEntity.length() );
            }
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
//        if ( column == 0 ) {
//            return getValueAt( 0, column).getClass();
//        }
//        else {
            return super.getColumnClass( column );
        //}
    }
}                   



/*
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import javax.swing.table.AbstractTableModel;
//
//import org.tmatesoft.svn.core.SVNDirEntry;
//import org.tmatesoft.svn.core.SVNException;
//import org.tmatesoft.svn.core.SVNLogEntry;
//
//import xagdop.Controleur.CTreeNode;
//import xagdop.Svn.SvnHistory;
//
//public class DirectoryModel extends AbstractTableModel {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	protected File file;
//	protected Collection infos;
//	// protected int rowCount;
//	
//	
//	
//	public DirectoryModel( CTreeNode dir ) throws SVNException {
//		file = (File)dir.getUserObject();
//		if(file.exists()==false)
//			file.mkdir();
//		
//		File bla = new File(file.getAbsolutePath()+"/"+"claire");
//		CTreeNode tmp = new CTreeNode(bla,dir);
//		infos = SvnHistory.getInfo(tmp);
//		//System.out.println(infos.toString());
//		// rowCount = infos.size();
//		
//	}
//	
//	
//	public void setFile( CTreeNode dir ) throws SVNException {
//		
//		file = (File)dir.getUserObject();
//		infos = SvnHistory.getInfo(dir);
//		//rowCount = infos.size();
//		System.out.println("setFile"+infos.toString());
//		fireTableDataChanged();
//	}
//	
//	public int getRowCount() {
//		return infos != null ? infos.size() : 0;
//	}
//	
//	public int getColumnCount() {
//		return infos != null ? 2 :0;
//	}
//	
//	public Object getValueAt(int row, int column){
//		System.out.println("bla");
//		if ( file == null || infos == null ) {
//			return null;
//		}
//		ArrayList comment =  new ArrayList(infos);
//		System.out.println("getValuesAt row : "+row+" column : "+column);
//		System.out.println("value : "+(SVNLogEntry)comment.get(row));
//		
//		switch ( column ) {
//		/*case 0:
//			return new Double(1.0+(infos.size()-row)/100);*/
//		case 0:
//			comment =  new ArrayList(infos);
//			return ((SVNLogEntry)comment.get(row)).getMessage();
//		case 1:
//			ArrayList author =  new ArrayList(infos);
//			return ((SVNLogEntry)author.get(row)).getAuthor();
//		default:
//			return "";
//		}
//	}
//	
//	public String getColumnName( int column ) {
//		System.out.println("blabla");
//		switch ( column ) {
//		case 0:
//			return "Version";
//		case 1:
//			return "Commentaire";
//		case 2:
//			return "Auteur";
//		default:
//			return "unknown";
//		}
//	}
//	
//	public Class getColumnClass( int column ) {
//		System.out.println("blabalba");
//		return getValueAt( 0, column).getClass();
//		
//	}
//	
//	
//	
//}                   
//
