package controller;



/**
 * @author rdupuis
 *
 */
public class StringComparator implements MaVComparator
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
        return ((String) o1).compareToIgnoreCase((String) o2);

    }
    
	public boolean getOrder()
	{
		return false;
	}

}
