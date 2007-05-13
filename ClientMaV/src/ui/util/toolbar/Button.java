package ui.util.toolbar;

import javax.swing.Icon;
import javax.swing.JButton;



public class Button extends JButton {


    /**
     * Construct a new Button
     */
    public Button() {
        this(null, null);
    }

    /**
     * Construct a new Button
     * 
     * @param text
     *        the button label
     */
    public Button(String text) {
        this(text, null);
    }

    /**
     * Construct a new Button
     * 
     * @param icon
     *        the button icon
     */
    public Button(Icon icon) {
        this(null, icon);
    }

    /**
     * Construct a new Button
     * 
     * @param text
     *        the button text
     * @param icon
     *        the button icon
     */
    public Button(String text, Icon icon) {
        super(text, icon);
    }

}

