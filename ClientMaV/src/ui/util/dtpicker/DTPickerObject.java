package ui.util.dtpicker;



public class DTPickerObject
{

  private int nDay = 0;
  private boolean isCurrent = false;

  public DTPickerObject()
  {
  }

  public DTPickerObject(int nDay_, boolean isCurrent_)
  {
    nDay = nDay_;
    isCurrent = isCurrent_;
  }

  public String toString()
  {
    return Integer.toString( nDay);
  }

  public void setDay(int nDay_)
  {
    nDay = nDay_;
  }

  public void setCurrent(boolean bCurrent)
  {
    isCurrent = bCurrent;
  }

  public boolean isCurrent()
  {
    return isCurrent;
  }

  public int getDay()
  {
    return nDay;
  }
}