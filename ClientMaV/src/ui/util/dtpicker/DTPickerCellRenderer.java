package ui.util.dtpicker;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;



public class DTPickerCellRenderer extends DefaultTableCellRenderer
{

  public DTPickerCellRenderer()
  {
    super();
  }

  public Component getTableCellRendererComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               boolean hasFocus,
                                               int row,
                                               int column)
  {
    if (((DTPickerObject)value).isCurrent() )
    {
      setForeground( Color.black);
    }
    else
    {
      setForeground( Color.lightGray);
    }
    return (super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column));
  }
}