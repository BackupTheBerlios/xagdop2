package controller;


/**
 * @author rdupuis
 *
 */
public class StringSearchable implements MaVSearchable
{

   
    public boolean startWith(Object value, String strValue, String strFieldName)
    {
        try
        {
            // Test if start with
            return ((String) value).toLowerCase().startsWith(strValue.toLowerCase());

        } catch (Throwable th)
        {
            th.printStackTrace();
            //ErrorDialogBox.show(th);
        }
        return false;
    }

}