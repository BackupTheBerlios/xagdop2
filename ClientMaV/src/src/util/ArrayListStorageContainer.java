package src.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import ui.util.DataListEvent;
import ui.util.DataListListener;
import controller.MaVComparator;


/**
 * Class that store objects. The object can be sorted. You can add listener to
 * the change of data in the model
 */
public class ArrayListStorageContainer

	extends ArrayList
	implements StorageContainer
{

	//	attribute that support listener list
	protected EventListenerList listenerManager = new EventListenerList();


	/**
	 * Descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 */
	public ArrayListStorageContainer()
	{
		super();
	}

	/**
	 * Descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param col
	 */
	public ArrayListStorageContainer(Collection col)
	{
		super();
		addAll(col);
	}

	/**
	 * Descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param vect_
	 */
	public ArrayListStorageContainer(ArrayList array_)
	{
		super();
		addAll(array_);
	}

	/**
	 * Constructor with array substract
	 *
	 * @param collFullList_, A full data array
	 * @param collToRemoveList_, a array containing data to remove from full data array
	 */
	public ArrayListStorageContainer(
			ArrayList arrayListFullList_,
			ArrayList arrayListToRemoveList_)
	{

		super();
		addAll(arrayListFullList_);

		// Remove ToRemoveList elements from FullList
		super.removeAll((Collection) arrayListToRemoveList_);
	}

	/**
	 * Add a listener to the model
	 */
	public void addDataListListener(DataListListener dll)
	{
		// register the listener to the manager
		listenerManager.add(DataListListener.class, dll);
	}

	/**
	 * Remove the listener to the model
	 */
	public void removeDataListListener(DataListListener dll)
	{
		// unregister hte listener
		listenerManager.remove(DataListListener.class, dll);
	}

	/**
	 * Method that dispatch the event
	 */
	public void fireDataListChanged(DataListEvent dle)
	{

		// get all the listener
		Object[] listeners =
			listenerManager.getListeners(DataListListener.class);

		// Process the listeners last to first, notifying them
		for (int i = 0; i < listeners.length; i++)
		{
			((DataListListener) listeners[i]).contentsChanged(dle);
		}
	}

	// super des methods qui modifie le array; et fireDataListChanged

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param index
	 * @param element
	 */
	public void add(int index, Object element)
	{
		// add data to the array
		super.add(index, element);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param element
	 *
	 * @return
	 */
	public boolean add(Object element)
	{
		boolean retour;

		// add data to the array
		retour = super.add(element);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
		return retour;
	}

	/**
		 * Method addWithContainsTest.
		 * @param elementToAdd
		 * @return boolean
		 */
	public boolean addWithContainsTest(Object elementToAdd)
	{
		boolean bReturn = false;

		if (null != elementToAdd)
		{
			// test if array not contains elementToAdd
			if (!contains(elementToAdd))
			{
				// add data to the array
				bReturn = super.add(elementToAdd);

				// notify all the listeners
				fireDataListChanged(new DataListEvent());
			}
		}
		return bReturn;

	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param c
	 *
	 * @return
	 */
	public boolean addAll(Collection c)
	{
		boolean retour;

		// add data to the array
		retour = super.addAll(c);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return retour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param index
	 * @param c
	 *
	 * @return
	 */
	public boolean addAll(int index, Collection c)
	{
		boolean retour;

		// add data to the array
		retour = super.addAll(c);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return retour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param element
	 */
	public void addElement(Object element)
	{

		// add data to the arrayList
		super.add(element);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param obj
	 * @param index
	 */
	public void insertElementAt(Object obj, int index)
	{
		// add data to the arrayList
		super.add(index, obj);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param index
	 *
	 * @return
	 */
	public Object remove(int index)
	{
		Object retour;

		// add data to the array
		retour = super.remove(index);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return retour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param o
	 *
	 * @return
	 */
	public boolean remove(Object o)
	{
		boolean retour;

		// add data to the array
		retour = super.remove(o);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return retour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param c
	 *
	 * @return
	 */
	public boolean removeAll(Collection c)
	{
		boolean retour;

		// add data to the array
		retour = super.removeAll(c);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return retour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 */
	public void removeAllElements()
	{
		// add data to the array
		super.clear();

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param obj
	 *
	 * @return
	 */
	public boolean removeElement(Object obj)
	{
		boolean bRetour;

		// add data to the array
		bRetour = super.remove(obj);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return bRetour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param index
	 */
	public void removeElementAt(int index)
	{

		// add data to the array
		super.remove(index);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param fromIndex
	 * @param toIndex
	 */
	protected void removeRange(int fromIndex, int toIndex)
	{
		// add data to the array
		super.removeRange(fromIndex, toIndex);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param c
	 *
	 * @return
	 */
	public boolean retainAll(Collection c)
	{
		boolean bRetour;

		// add data to the array
		bRetour = super.retainAll(c);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return bRetour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param index
	 * @param element
	 *
	 * @return
	 */
	public Object set(int index, Object element)
	{
		Object objRetour;

		// add data to the array
		objRetour = super.set(index, element);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());

		return objRetour;
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param obj
	 * @param index
	 */
	public void setElementAt(Object obj, int index)
	{
		// add data to the array
		super.set(index, obj);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 */
	public void clear()
	{
		// clear the data into the array
		super.clear();

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * Method that sort the model with the given comparator on the given attribute
	 * according to bAsc_(True if ascendant, false otherwise
	 */
	public void order(
		MaVComparator comparator,
		String strAttribute,
		boolean bAsc_)
	{
		// set the comparator
		comparator.setAttribute(strAttribute);
		comparator.setOrder(bAsc_);

		// sort hte array
		Collections.sort(this, comparator);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * array setter
	 *
	 * @param vect_
	 */
	public void set(ArrayList array_)
	{

		// set all the data of a array
		// without change the reference.
		super.clear();
		super.addAll((Collection) array_);

		// notify all the listeners
		fireDataListChanged(new DataListEvent());
	}

	/**
	 * Method addAll
	 * call super Method, but before, copy list of listeners
	 * @param arrayStorageContainer copy arrayStorageContainer
	 * @return
	 */
	public boolean addAll(ArrayListStorageContainer arrayStorageContainer)
	{
		int nCount = arrayStorageContainer.listenerManager.getListenerCount();
		Object[] listeners =
			arrayStorageContainer.listenerManager.getListenerList();
		for (int i = 0; i < nCount; i++)
		{
			try
			{
				if (null != listeners[i])
				{
					this.addDataListListener(
						(DataListListener) listeners[i]);
				}
			}
			catch (ClassCastException e)
			{
					System.out.println("ERROR adding DataListListener " + e.getMessage());
				
			}
		}

		return super.addAll(arrayStorageContainer);
	}

	

}