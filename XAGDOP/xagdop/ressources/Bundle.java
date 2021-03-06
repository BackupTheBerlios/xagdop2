package xagdop.ressources;
/*
** Author	: Jeremy Tyriaux
*/


//Libraries
import java.util.*;



public class Bundle
{
 
  static public final String DATA_DIRECTORY = "xagdop/ressources/";
  static public final String DATA_FILE_EXTENSION = "properties";

  
  static private final String BASENAME = "xagdop/ressources/label";
  static private ResourceBundle bundle = ResourceBundle.getBundle(Bundle.BASENAME, Locale.ENGLISH);

  static public void setCurrentLocale(Locale locale)
  {
    if (locale != null)
    {
    	//System.out.println("Bundle.setCurentLocale( Lang: "+locale.getLanguage()+"   Pays: "+locale.getCountry()+ " )");
    	Locale.setDefault(locale);
    	Bundle.bundle = ResourceBundle.getBundle(Bundle.BASENAME, locale);
      }
  }

  static public String getText(String key)
  {
    try
    {
      return Bundle.bundle.getString(key);
    }
    catch (MissingResourceException e)
    {
      return key;
    }
  }

  static public char getChar(String key)
  {
    return Bundle.getText(key).charAt(0);
  }

  static public Integer getCode(String key)
  {
    return new Integer((int)Character.toUpperCase(Bundle.getChar(key)));
  }

}