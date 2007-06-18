package controller;

import MaV.Electeur;



/**
 * @author rdupuis
 *
 */
public class ElecteurComparator implements MaVComparator
{
    /**
     * Log
     */
    
    public final static String DEFAULT_ATTRIBUTE = "";
  
    public void setAttribute(String strAttribute)
    {
    }

   
    public void setOrder(boolean bAsc)
    {
    }

    /**
     * @see java.util.Comparator#compare(Object, Object)
     */
    public int compare(Object o1, Object o2)
    {
        return ((Electeur) o1).nom.compareToIgnoreCase(((Electeur) o2).nom);

    }
    
	public boolean getOrder()
	{
		return false;
	}

}
