
package ui.util.toolbar;


public class ToolBarButton extends RollabledButton {


    // Attributes
    boolean bAllowTextOnButton = false;

    /**
     * Constructor
     * 
     * @param bAllowTextOnButton_,
     *        default FALSE
     */
    public ToolBarButton() {
    }

    /**
     * Constructor
     * 
     * @param bAllowTextOnButton_,
     *        default FALSE
     */
    public ToolBarButton(boolean bAllowTextOnButton_) {
        bAllowTextOnButton = bAllowTextOnButton_;
    }

    /**
     * Overload <i>setText() </i> method to consume it by default
     * 
     * @param strValue_
     */
    public void setText(String strValue_) {

        // Set only if allowed
        if (bAllowTextOnButton) {
            super.setText(strValue_);
        }

        // else Text consumed, due to 'Action' class calls, when Text is usefull
        // for Menu, it is not for ToolBar Buttons
    }

}
