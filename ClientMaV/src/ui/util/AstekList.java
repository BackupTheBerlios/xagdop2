package ui.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.MaVComparator;
import controller.MaVListModel;
import controller.MaVSearchable;

/**
 * JList with method to select an object
 * 
 * @see
 * @Copyrigth Astek
 */
public class AstekList extends JList implements ListDataListener, KeyListener, ListSelectionListener {


    // Attribute
    protected MaVComparator myComparator = null;

    protected MaVSearchable mySearchDelegationObject = null;

    protected String strDefaultSortAttribute = "";

    protected String strSearchAttribute = "";

    protected boolean bDefaultAscendentOrder = true;

    
    public AstekList(){
    	super();
    }
    
    /**
     * Constructor
     */
    public AstekList(MaVListModel mySortableListModel_) {

        // Call Parent
        super(mySortableListModel_);

        mySortableListModel_.addListDataListener(this);
        //KeyboardBlockerEventQueue.getInstance().setBlockingEnabled(false);
        
        addListSelectionListener( this );

        //		addKeyListener(this);
    }

    public void keyReleased(KeyEvent event) {
    }

    public void keyPressed(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
        selectWithKeyChar(event.getKeyChar());
    }

    public boolean selectWithKeyChar(char keyChar) {
        int index = -1;

        index = selectionForKey(keyChar, getModel());

        if (-1 != index) {
            setSelectedIndex(index);
            ensureIndexIsVisible(index);

            return true;
        }
        else {
            return false;
        }

    }

    public int selectionForKey(char key, ListModel listModel) {
        int i = 0;
        int size = 0;
        int current_selection = -1;

        // if no element is selected
        if (getSelectedIndex() == -1) {
            return -1;
        }
        Object selected_item = listModel.getElementAt(getSelectedIndex());
        String value = null;
        String pattern = null;

        size = listModel.getSize();

        if (null != selected_item) {
            selected_item = selected_item.toString();

            for (i = 0; i < size; i++) {
                if (selected_item.equals(listModel.getElementAt(i).toString())) {
                    current_selection = i;
                    break;
                }
            }
        }

        pattern = ("" + key).toLowerCase();
        key = pattern.charAt(0);

        for (i = ++current_selection; i < size; i++) {

            if (getCellRenderer() != null && getCellRenderer() instanceof JLabel) {
                JLabel label = (JLabel) getCellRenderer().getListCellRendererComponent(this, listModel.getElementAt(i),
                        i, true, true);
                value = label.getText().toLowerCase();
            }
            else {
                value = listModel.getElementAt(i).toString().toLowerCase();
            }

            if (value.length() > 0 && key == value.charAt(0)) {
                return i;
            }
        }

        for (i = 0; i < current_selection; i++) {
            if (getCellRenderer() != null && getCellRenderer() instanceof JLabel) {
                JLabel label = (JLabel) getCellRenderer().getListCellRendererComponent(this, listModel.getElementAt(i),
                        i, true, true);
                value = label.getText().toLowerCase();
            }
            else {
                value = listModel.getElementAt(i).toString().toLowerCase();
            }
            if (value.length() > 0 && key == value.charAt(0)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @param myComparator_
     * @param strDefaultSortAttribute_
     * @param bDefaultAscendentOrder_
     */
    public void setComparator(MaVComparator myComparator_, String strDefaultSortAttribute_,
            boolean bDefaultAscendentOrder_) {

        // Set Attribute
        myComparator = myComparator_;
        strDefaultSortAttribute = strDefaultSortAttribute_;
        bDefaultAscendentOrder = bDefaultAscendentOrder_;
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @param mySearchDelegationObject_
     */
    public void setSearchDelegationObject(MaVSearchable mySearchDelegationObject_, String strSearchAttribute_) {

        // Set Attribute
        mySearchDelegationObject = mySearchDelegationObject_;
        strSearchAttribute = strSearchAttribute_;
    }

    /**
     * Sort list with embedded defaults
     */
    public void sortListWithDefaults() {

        // Sort Model
        ((MaVListModel) this.getModel()).sort(myComparator, this.strDefaultSortAttribute, this.bDefaultAscendentOrder);
    }

    /**
     * Sort list with given parameters
     * 
     * @param strSortAttribute_
     * @param bAscendentOrder_
     */
    public void sortListWithParameters(String strSortAttribute_, boolean bAscendentOrder_) {

        // Sort Model
        ((MaVListModel) this.getModel()).sort(myComparator, strSortAttribute_, bAscendentOrder_);
    }

    /**
     * Selected search text
     * 
     * @param strValue_
     * @param strAttributeName_
     */
    public void searchText(String strValue_) {

        // Check if have delegation objec
        if (mySearchDelegationObject != null) {

            // Check if value need a selection
            if (strValue_.equals("")) {

                // Clear all selection
                this.clearSelection();
            }
            else {

                // Declare found boolean
                boolean bFound = false;

                // Browse list
                for (int i = 0; i < this.getModel().getSize(); i++) {

                    // Test if current object's given attribute start with given
                    // value
                    if (mySearchDelegationObject.startWith(this.getModel().getElementAt(i), strValue_,
                            strSearchAttribute)) {

                        // Select it
                        this.setSelectedIndex(i);

                        // Ensure that selection is visible and on top
                        if (i + this.getVisibleRowCount() > this.getModel().getSize()) {
                            this.ensureIndexIsVisible(i);
                        }
                        else if (i < this.getVisibleRowCount()) {
                            this.ensureIndexIsVisible(i);
                        }
                        else {
                            this.ensureIndexIsVisible(i + this.getVisibleRowCount() - 1);
                        }

                        // Set found
                        bFound = true;

                        // End browse
                        break;
                    }
                }

                // Clear selection if not found
                if (!bFound) {
                    this.clearSelection();
                }
            }
        }
    }

    public void intervalAdded(ListDataEvent e) {
        clearSelection();
    }

    public void intervalRemoved(ListDataEvent e) {
        clearSelection();
    }

    public void contentsChanged(ListDataEvent e) {
        clearSelection();
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;

        JList list = (JList) e.getSource();
        list.ensureIndexIsVisible(list.getSelectedIndex());
    }
    
    public List getList(){
    	List myAllList = new ArrayList();
    	for(int i = 0 ; i < getModel().getSize();i++){
    		myAllList.add(getModel().getElementAt(i));
    	}
    	return myAllList;
    }

}