package controller;



public interface MaVSearchable extends Searchable
{

	/**
	 * This method test if the value start with the string
	 *
	 * @param value : the object  to be tested
	 * @param strValue : the boject's starting values ?
	 * @param strFieldName : the name of the field that will be tested
	 *
	 * @return true, if the strFieldNAme values start with strValue_, false otherwise
	 */
	public boolean startWith(Object value, String strValue, String strFieldName);
}
