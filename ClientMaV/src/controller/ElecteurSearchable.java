package controller;

import MaV.Electeur;




/**
 * @author rdupuis
 *
 */
public class ElecteurSearchable implements MaVSearchable
{

   
    public boolean startWith(Object value, String strValue, String strFieldName)
    {
        try
        {
            // Test if start with
            return ((Electeur) value).nom.toLowerCase().startsWith(strValue.toLowerCase());

        } catch (Throwable th)
        {
            th.printStackTrace();
            //ErrorDialogBox.show(th);
        }
        return false;
    }

}