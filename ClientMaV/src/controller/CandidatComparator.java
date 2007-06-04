package controller;

import src.util.CandidatClient;



/**
 * @author rdupuis
 *
 */
public class CandidatComparator implements MaVComparator
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
        return ((CandidatClient) o1).getCand().nom().compareToIgnoreCase(((CandidatClient) o2).getCand().nom());

    }
    
	public boolean getOrder()
	{
		return false;
	}

}
