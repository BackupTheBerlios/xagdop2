package controller;

import java.util.Comparator;

/**
 * Interface that define the method to implement in the comparator of object
  */
public interface MaVComparator extends Comparator
{

    /**
     * Set the attribute on which one the order must be applied
     */
    public void setAttribute(String strAttribute);

    /**
     * Set the order of the sort
     * true if is ascending sort, false otherwise
     */
    public void setOrder(boolean bAsc);
    
    /**
     * Method getOrder
     * return if order is descending or ascending
     * @return	true if ascending, not else
     */
	public boolean getOrder();

}
