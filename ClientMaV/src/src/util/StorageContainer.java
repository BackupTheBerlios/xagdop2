package src.util;

import java.util.List;

import ui.util.DataListEvent;
import ui.util.DataListListener;
import controller.MaVComparator;


/**
 * Cette interface decrit les methodes minimales que
 * doivent comporter les classes de stockages que l'on utilise
 * dans les objets locaux.
 *
 * @author AUBERT Marc-Jean,STOLF Yannick
 * @see VectorListener,HashListener
 */
public interface StorageContainer extends List{

    /**
     * Retourne l'objet situee a l'index nIndex
     *
     * @param nIndex_
     *
     * @return, the value at the specified index
     */
    public Object get(int nIndex_);

    /**
     * Computes the size of the stored data
     *
     * @return, the number of elements stored in this class
     */
    public int size();

    /**
     * Add an obvject in this class
     *
     * @param value_, the object to be added
     *
     * @return true, if successful, false otherwise
     */
    public boolean add(Object value_);

    /**
     * Remove an element inthis storage
     *
     * @param value_, the element to be removed
     *
     * @return, true if removing was successful, false otherwise
     */
    public boolean removeElement(Object value_);

    /**
     * Indicates the data inside thjis class has changed
     *
     * @param dle_, the event who declenching the notification
     */
    public void fireDataListChanged(DataListEvent dle_);

    /**
     * Add a listener who want knows when the data of this storage class
     * has changed
     *
     * @param dll, a datatlistlitener object
     */
    public void addDataListListener(DataListListener dll);

    /**
     * Remove a listener , that wouldn't be prevent that a data changed inside this class
     *
     * @param dll, a datatlistlitener object
     */
    public void removeDataListListener(DataListListener dll);

    /**
     * Method that sort the data with the given comparator on the given attribute
     * according to bAsc_(True if ascendant, false otherwise
     *
     * @param comparator, the JComparator
     * @param strAttribute, the attribute use to compare
     * @param bAsc_n the sens of the sort
     */
    public void order(MaVComparator comparator, String strAttribute,
		      boolean bAsc_);
}