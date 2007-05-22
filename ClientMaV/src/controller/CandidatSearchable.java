package controller;

import MaV.Candidat;
import MaV._CandidatStub;


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
            return ((_CandidatStub) value).nom().toLowerCase().startsWith(strValue.toLowerCase());

        } catch (Throwable th)
        {
            th.printStackTrace();
            //ErrorDialogBox.show(th);
        }
        return false;
    }

}