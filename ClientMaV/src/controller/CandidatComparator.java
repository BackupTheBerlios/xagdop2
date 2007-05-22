package controller;

import MaV.Candidat;
import MaV._CandidatStub;



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
        return ((_CandidatStub) o1).nom().compareToIgnoreCase(((_CandidatStub) o2).nom());

    }
    
	public boolean getOrder()
	{
		return false;
	}

}
