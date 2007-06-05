package ui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import ui.util.toolbar.ToolBar;

public class MainFrame extends JFrame implements
		MouseListener
		{

	/************************************* BORDER **************************/
	/***********************************************************************/
	/**image**/
	public static final ImageIcon MAV_BORDER =
        new ImageIcon(
            MainFrame.class.getResource(
                "/images/" + "mav.gif"));
	
	ImageBorder imgborder = new ImageBorder(MAV_BORDER.getImage(),
			ImageBorder.WEST,
			ImageBorder.BOTTOM);	
	Border _border = new CompoundBorder(LightBorder.createRaisedBorder(),
    new CompoundBorder(imgborder, LightBorder.createLoweredBorder()));
	
	/** *********************************** MENU BAR ***********************/
	/***********************************************************************/
	private JMenuBar _jMenuBar = new JMenuBar();
	
	/** ************************** ------> Menu FICHIER ******************** */
	private JMenu _mnuFichier = new JMenu();
	
	/** ************************** ------> Menu Edition ******************** */
	private JMenu _mnuEdit = new JMenu();
	
	/** ************************** ------> Menu Datapackage ******************** */
	private JMenu _mnuDatapackage = new JMenu();
	
	/** ************************** ------> Menu Tools ******************** */
	private JMenu _mnuTools = new JMenu();
	
	/** ************************** ------> Menu Help ******************** */
	private JMenu _mnuHelp = new JMenu();
	
	/** *********************************** TOOLBAR  ************************/
	/************************************************************************/
	private ToolBar _jToolBar = new ToolBar();
	
	/** ********** New datapackage ******/
//	private CreateDatapackage _newData = new CreateDatapackage();
	/** ********** Open datapackage ******/
	//private OpenDatapackage _openData = new OpenDatapackage();
	/** ********** Save datapackage ******/
	//private SaveDatapackage _saveData = new SaveDatapackage();
	/** ********** cut datapackage ******/
	//private CutDatapackage _cutData = new CutDatapackage();
	/** ********** copy datapackage ******/
	//private CopyDatapackage _copyData = new CopyDatapackage();
	/** ********** paste datapackage ******/
	//private PasteDatapackage _pasteData = new PasteDatapackage();
	/** ********** delete datapackage ******/
	//private DeleteDatapackage _deleteData = new DeleteDatapackage();
	/** ********** Show properties datapackage ******/
	//private ShowPropDatapackage _showPropData = new ShowPropDatapackage();
	/** ********** Edit properties datapackage ******/
	//private EditPropDatapackage _editPropData = new EditPropDatapackage();
	
	
	/** Action Fenetre "� propos" (Manu aide) */
	/*private AboutAbstractAction _myActionAbout = new AboutAbstractAction() {
		public void actionPerformed(ActionEvent e) {
			new AstekAboutDialogBox(); 
		}
	};
	
	/**
     * Icone de Smart
     */
    public static final ImageIcon SMART_ICON =
        new ImageIcon(
            MainFrame.class.getResource(
                "/images/" + "smart.gif"));
	
	/** Log */
	


	/**
	 * @see java.lang.Object#Object() CONSTRUCTEUR
	 */
	public MainFrame() throws Exception {
		super();
		this.setIconImage(SMART_ICON.getImage());
		this.setTitle("MaV");

		// Component Construction
		try {
			init();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		//this.addChildUIComponent(_myActionAbout);
		this.refreshLabels();

	}

	/**
	 * Method jbInit.
	 *
	 * @throws Exception :
	 */
	protected void init() throws Exception {
		// FENETRE PRINCIPALE
		// Set Size not necessary, let the toolkit manage
		//        this.setSize(new Dimension(800, 600));

		////////// ADDD MENU BAR ////////////////
		this.setJMenuBar(_jMenuBar);
		//add Fichier menu
		_mnuFichier.setText("Fichier");
		_mnuFichier.setMnemonic('f');
		_jMenuBar.add(_mnuFichier);
		//add Edit menu
		_mnuEdit.setText("Edit");
		_mnuEdit.setMnemonic('e');
		_jMenuBar.add(_mnuEdit);
		//add Datapackage
		_mnuDatapackage.setText("Datapackage");
		_mnuDatapackage.setMnemonic('d');
		_jMenuBar.add(_mnuDatapackage);
		//add Tools
		_mnuTools.setText("Tools");
		_mnuTools.setMnemonic('T');
		_jMenuBar.add(_mnuTools);
		//add Help
		_mnuHelp.setText("?");
		_mnuHelp.setMnemonic('?');
		//_mnuHelp.add(_myActionAbout);
		_jMenuBar.add(_mnuHelp);
		
		/////////TOOL BAR //////////////////////
	/*	_jToolBar.add(_newData);
		_jToolBar.add(_openData);
		_jToolBar.add(_saveData);
		_jToolBar.add(new AstekToolBarSeparator(JSeparator.VERTICAL));
		_jToolBar.add(_cutData);
		_jToolBar.add(_copyData);
		_jToolBar.add(_pasteData);
		_jToolBar.add(_deleteData);
		_jToolBar.add(new AstekToolBarSeparator(JSeparator.VERTICAL));
		_jToolBar.add(_showPropData);
		_jToolBar.add(_editPropData);			*/
		
		
		//add tool bar
		this.getContentPane().add(_jToolBar, BorderLayout.NORTH);	
		//add border 
		JComponent c = (JComponent)getContentPane();
		c.setBorder(_border);
		this.setSize(new Dimension(800,600));
	}

	
	public void refreshLabels() {
		this.setTitle("MaV");		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}