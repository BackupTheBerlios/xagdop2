package controller;

import src.util.CandidatClient;




/**
 * @author rdupuis
 *
 */
public class CandidatSearchable implements MaVSearchable
{

   
    public boolean startWith(Object value, String strValue, String strFieldName)
    {
        try
        {
            // Test if start with
            return ((CandidatClient) value).getCand().nom().toLowerCase().startsWith(strValue.toLowerCase());

        } catch (Throwable th)
        {
            th.printStackTrace();
            //ErrorDialogBox.show(th);
        }
        return false;
    }

}