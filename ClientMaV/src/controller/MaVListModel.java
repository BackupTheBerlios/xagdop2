
package controller;

// JDK
import javax.swing.AbstractListModel;

import src.util.ArrayListStorageContainer;
import src.util.StorageContainer;
import ui.util.DataListEvent;
import ui.util.DataListListener;



/**
 * List Model.
 *
 * <i>DataListListener</i> compatible.
 * Provide <i>sort()</i> method.

 */
public class MaVListModel
    extends AbstractListModel
    implements DataListListener
{


    // Attributes
    private StorageContainer myStorageContainer = null;

    /**
     * Constructor
     */
    public MaVListModel()
    {

        // Call Others constructor
        this(null);
    }

    /**
     * Constructor
     *
     * @param myStorageContainer_
     */
    public MaVListModel(StorageContainer myStorageContainer_)
    {

        // Parent
        super();

        // Set Data
        setData(myStorageContainer_);
    }

    /**
     * Get listener
     *
     * @return
     */
    public StorageContainer getData()
    {

        // Return it
        return myStorageContainer;
    }

    /**
     * Set Data
     *
     * @param myStorageContainer_
     */
    public void setData(StorageContainer myStorageContainer_)
    {

        // Set Data
        if (null != myStorageContainer_)
        {
            myStorageContainer = myStorageContainer_;
        }
        else
        {
            myStorageContainer = new ArrayListStorageContainer();
        }

        // Register to listener
        myStorageContainer.addDataListListener(this);

        // Notify update
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Get element at index
     *
     * @param nIndex_
     *
     * @return
     */
    public Object getElementAt(int nIndex_)
    {

        // Declare
        Object myObject = null;

        if (null != myStorageContainer)
        {
            myObject = myStorageContainer.get(nIndex_);
        }
        //System.out.println(myObject.getClass());
        // Return it
        return myObject;//((_CandidatStub)myObject)._toString();
    }

    /**
     * Get size of storage
     *
     * @return
     */
    public int getSize()
    {

        // Declare
        int nRetour = 0;

        if (null != myStorageContainer)
        {
            nRetour = myStorageContainer.size();
        }

        return nRetour;
    }

    /**
     * Content changed
     *
     * @param dle_
     */
    public void contentsChanged(DataListEvent dle_)
    {

        // Notify update
        fireContentsChanged(this, 0, getSize());
    }

    /**
     * Sort
     *
     * @param myComparator_
     * @param strAttribute_
     * @param bAsc_
     */
    public void sort(
        MaVComparator myComparator_,
        String strAttribute_,
        boolean bAsc_)
    {

        // Sort listener
        myStorageContainer.order(myComparator_, strAttribute_, bAsc_);
    }

}