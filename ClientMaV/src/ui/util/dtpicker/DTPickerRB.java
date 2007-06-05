package ui.util.dtpicker;

import java.util.ListResourceBundle;


/**
 * Title:        Baghera Anomalies
 * Description:
 * Copyright:    Copyright (c)
 * Company:
 * @author
 * @version 1.0
 */

public class DTPickerRB extends ListResourceBundle
{
  public Object[][] getContents() {
      return contents;
  }
  static final Object[][] contents = {
      {"LUNDI", "L"},
      {"MARDI", "M"},
      {"MERCREDI", "M"},
      {"JEUDI", "J"},
      {"VENDREDI", "V"},
      {"SAMEDI", "S"},
      {"DIMANCHE", "D"}
  };
}