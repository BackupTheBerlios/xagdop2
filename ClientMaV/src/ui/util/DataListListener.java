package ui.util;

import java.util.EventListener;

/**
 * Interface that define an object which is a listener of the baghera data model
 */
public  interface DataListListener extends EventListener {

  /**
   * Method call when model or data have changed
   */
  public void contentsChanged(DataListEvent myDataListEvent_);

}