
package controller;


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


