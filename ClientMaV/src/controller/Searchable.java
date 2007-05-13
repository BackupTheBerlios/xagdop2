/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package controller;

/**
 * This interface describes a class that will be indicates if
 * his representative view start with a string
 * @author AUBERT Marc-Jean,STOLF Yannick
 */
public interface Searchable {

    /**
     * This method test if the value start with the string
     *
     * @param value_, the object  to be tested
     * @param strValue_, the boject's starting values ?
     * @param strFieldName_, the name of the field that will be tested
     *
     * @return true, if the strFieldNAme values start with strValue_, false otherwise
     */
    public boolean startWith(Object value_, String strValue_,
			     String strFieldName_);
}


