package ui.util.dtpicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.EventListenerList;


public class DTPicker extends JComponent implements DTPickerListener {

    /** Attribut catLog */

    //	 icon
    private static final ImageIcon ICON_DOWN_ARROW = new ImageIcon(DTPicker.class.getResource("/images/" + "fleche.gif"));

    private static final String STR_DATE_FORMAT = "dd/MM/yy";


    // Calendar
    private TimeZone _timeZone = TimeZone.getDefault();

    private GregorianCalendar _gregorianCalendar = new GregorianCalendar(_timeZone, Locale.getDefault());

    private Popup _popup = null;

    private JTextField jTextFieldDate = new JTextField();

    private JButton jButtonDown = new JButton();

    private DTPickerPanel calGUI = new DTPickerPanel();

    private static final PopupFactory factory = PopupFactory.getSharedInstance();

    /**
     * Descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     */
    public DTPicker() {

        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        calGUI.setDate(_gregorianCalendar);
        calGUI.addDateListener(this);

    }

    private Popup getPopup() {

        Point p = jTextFieldDate.getLocationOnScreen();
        Dimension inputSize = jTextFieldDate.getPreferredSize();
        _popup = factory.getPopup(jTextFieldDate, calGUI, p.x, p.y + inputSize.height);
        return _popup;
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @exception Exception
     */
    private void jbInit() throws Exception {

        Border border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(93,
                93, 93), new Color(134, 134, 134));

        jTextFieldDate.setBorder(null);
        jTextFieldDate.setPreferredSize(new Dimension(71, 21));
        jTextFieldDate.setHorizontalAlignment(SwingConstants.CENTER);

        jTextFieldDate.addKeyListener(new KeyAdapter() {
            
            boolean bInError = false;

            public void keyReleased(KeyEvent e) {
                
                if( !bInError){
                    jTextFieldDate.setForeground(Color.GRAY);
                }
                if ((int) e.getKeyChar() == KeyEvent.VK_ENTER) {
                    Date date = getDateFromText();
                    if (null != date) {
                    	Calendar cal = Calendar.getInstance();
                    	cal.setTime(date);
                        setDate(cal);
                        jTextFieldDate.setForeground(Color.BLACK);
                        bInError = false;
                    }
                    else {
                        if (!bInError) {
                            bInError = true;
                            jTextFieldDate.setForeground(Color.RED);
                            //MessageDialogBox.showErrorDialog(this,BagheraError.DATE_FORMAT_ERROR);
                        }
                    }
                    e.consume();
                }
                if ((int) e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                	Calendar cal = Calendar.getInstance();
                	cal.setTime(getDate());
                    setDate(cal);
                    jTextFieldDate.setForeground(Color.BLACK);
                }
                
            }
        });
        jTextFieldDate.addFocusListener(new FocusAdapter() {

            public void focusLost(FocusEvent e) {
                Date date = getDateFromText();
                if (null != date) {
                    //setDate(DateCalendarHelper.convertDate(DateCalendarHelper.convertDate(date)));
                    jTextFieldDate.setForeground(Color.BLACK);
                }
                else {
                    if (!jTextFieldDate.getForeground().equals(Color.RED)) {
                        jTextFieldDate.setForeground(Color.RED);
                        //ErrorDialogBox.show(BagheraError.DATE_FORMAT_ERROR);

                    }

                }

            }
        });
        this.setLayout(new GridBagLayout());
        jButtonDown.setMaximumSize(new Dimension(16, 18));
        jButtonDown.setMinimumSize(new Dimension(16, 18));
        jButtonDown.setPreferredSize(new Dimension(16, 18));
        jButtonDown.setIcon(ICON_DOWN_ARROW);
        jButtonDown.setMargin(new Insets(2, 2, 2, 2));
        jButtonDown.addActionListener(new java.awt.event.ActionListener() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {

                jButtonDown_actionPerformed(e);
            }

        });
        this.setBorder(border2);
        super.setPreferredSize(new Dimension(91, 23));
        this.setBackground(Color.lightGray);
        this.add(jTextFieldDate, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        this.add(jButtonDown, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // install ancestor listener to hide popup
        addAncestorListener(new AncestorListener() {

            public void ancestorAdded(AncestorEvent event) {

                // update the date and hide popup
                //dateSelected();
            }

            public void ancestorRemoved(AncestorEvent event) {

                // update the date and hide popup
                //dateSelected();
            }

            public void ancestorMoved(AncestorEvent event) {

                // update the date and hide popup
                //dateSelected();
            }
        });

    }

    private boolean bIsShowing = false;

    /**
     * method that is called when user push the down button. It shows the calendar when it is hiding and hides it when
     * it is visible.
     * 
     * @param ActionEvent :
     *        e, event that causes the action to be performed
     */
    private void jButtonDown_actionPerformed(ActionEvent e) {

        if (!bIsShowing) {
            getPopup();
            _popup.show();
        }
        else {
            _popup.hide();
        	 
        }
        bIsShowing = !bIsShowing;

    }

    /**
     * This method fix the minimum size of the component. It calls setPreferredSize see setPreferredSize that set the
     * minimum and maximim size when you use it
     * 
     * @param Dimension :
     *        dim, the width and height of the component
     */
    public void setSize(Dimension dim) {

        setPreferredSize(dim);
    }

    /**
     * This method fix the maximum size of the component. It is not implemented see setPreferredSize that set the
     * minimum and maximim size when you use it
     * 
     * @param Dimension :
     *        dim, the width and height of the component
     */
    public void setMaximumSize(Dimension dim) {

        /*
         * jButtonDown.setMaximumSize(new Dimension(16, 18)); super.setMaximumSize(new Dimension(91, 23));
         */
    }

    /**
     * This method fix the minimum size of the component. It is not implemented see setPreferredSize that set the
     * minimum and maximim size when you use it
     * 
     * @param Dimension :
     *        dim, the width and height of the component
     */
    public void setMinimumSize(Dimension dim) {

        /*
         * jButtonDown.setMinimumSize(new Dimension(16, 18)); super.setMinimumSize(new Dimension(91, 23));
         */
    }

    /**
     * This method fix the zize of the date picker. It fixes the minimum and maximum of the component.
     * 
     * @param Dimension :
     *        dim, the width and height of the component
     */
    public void setPreferredSize(Dimension dim) {

        super.setPreferredSize(dim);
        super.setMinimumSize(dim);
        super.setMaximumSize(dim);
        jTextFieldDate.setPreferredSize(new Dimension(((int) dim.getWidth()) - 20, ((int) dim.getHeight()) - 2));
        jTextFieldDate.setMinimumSize(new Dimension(((int) dim.getWidth()) - 20, ((int) dim.getHeight()) - 2));
        jTextFieldDate.setMaximumSize(new Dimension(((int) dim.getWidth()) - 20, ((int) dim.getHeight()) - 2));
    }

    /**
     * This method set the alignement of the textual component. By default the text is centered on the text component.
     * 
     * @param int :
     *        nAlignment, hte alignment of the text
     * @see javax.swing.SwingConstants
     */
    public void setHorizontalAlignment(int nAlignment) {

        jTextFieldDate.setHorizontalAlignment(nAlignment);
    }

    /**
     * Method that enabled the date picker. By default the date picker is enabled.
     * 
     * @param boolean :
     *        bActif, true if enable, false otherwise
     */
    public void setEnabled(boolean bActif) {
        // enabled the button
        jButtonDown.setEnabled(bActif);
        // enable the text field
        jTextFieldDate.setEditable(bActif);
        jTextFieldDate.setEnabled(bActif);
    }

    /**
     * Method setEditable.
     * 
     * @param bEditable_
     */
    public void setEditable(boolean bEditable_) {

//        jTextFieldDate.setEditable(bEditable_);
//        jTextFieldDate.setEnabled(bEditable_);
        jTextFieldDate.setDisabledTextColor(Color.black);

    }

    /**
     * Method that return the selected date
     * 
     * @return java.util.Date : the selected date or null
     */
    public Date getDate() {

        Date dateRetour = null;

        dateRetour = calGUI.getDate();

        return dateRetour;
    }

    /**
     * Method that return the selected date
     * 
     * @return java.util.Calendar : the selected date or null
     */
    public Calendar getCalendarDate() {

        Date dateRetour = null;
        Calendar calRetour = null;

        try {
            dateRetour = calGUI.getDate();
            calRetour = Calendar.getInstance();

            calRetour.setTime(dateRetour);
        }
        catch (Exception ex) {
            dateRetour = null;
        }

        return calRetour;
    }

    /**
     * Method that set the initiale date in the date picker
     * 
     * @param Calendar :
     *        calendarDate, the date to put in the dtpicker
     */
    public void setDate(Calendar calendarDate) {

        // create the date parser
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_DATE_FORMAT);

        // set the date into the text field
        jTextFieldDate.setText(simpleDateFormat.format(calendarDate.getTime()));
        fireActionPerformed(new ActionEvent(this, 0, "change_date"));
        calGUI.setDate(calendarDate);
    }

    /**
     * Method that set if a default value must appear in the date picker
     * 
     * @param boolean :
     *        hasDefault, true if the default date must appear, false otherwise
     */
    public void setDefault(boolean hasDefault) {

        if (!hasDefault) {
            jTextFieldDate.setText("");
        }
    }

    /**
     * @see DTPickerListener#dateSelected()
     */
    public void dateSelected() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        // set the pattern
        simpleDateFormat.applyPattern(STR_DATE_FORMAT);

        // show the date
        jTextFieldDate.setText(simpleDateFormat.format(calGUI.getDate()));
        fireActionPerformed(new ActionEvent(this, 0, "change_date"));

        if (null != _popup) {
            // hide the menu
            _popup.hide();
            bIsShowing = false;
        }
    }

    public String getValue() {

        return this.jTextFieldDate.getText();
    }

    public Date getDateFromText() {

        if (jTextFieldDate.getText().equals("")) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        // set the pattern
        simpleDateFormat.applyPattern("dd/MM/yy");

        // declare date to return
        Date resultDate = new Date();

        try {
            // get date
            resultDate = simpleDateFormat.parse(jTextFieldDate.getText());
            return resultDate;
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * Adds an <code>ActionListener</code> to the button.
     * 
     * @param l
     *        the <code>ActionListener</code> to be added
     */
    public void addActionListener(ActionListener l) {

        listenerList.add(ActionListener.class, l);
    }

    /**
     * Notifies all listeners that have registered interest for notification on this event type. The event instance is
     * lazily created using the parameters passed into the fire method.
     * 
     * @param e
     *        the <code>ActionEvent</code> object
     * @see EventListenerList
     */
    protected void fireActionPerformed(ActionEvent event) {

        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        ActionEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ActionListener.class) {
                // Lazily create the event:
                if (e == null) {
                    String actionCommand = event.getActionCommand();

                    e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand, event.getModifiers());
                }
                ((ActionListener) listeners[i + 1]).actionPerformed(e);
            }
        }
    }
}