/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package ui.util.dtpicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;

import ui.main.MainFrame;


public class DTPickerPanel extends JPanel {

    private static final String strDateFormat = "MMM yyyy";

    // Listener
    EventListenerList _someListener = new EventListenerList();

    // Resource bundle
    DTPickerRB rb = new DTPickerRB();

    // icon
    private static ImageIcon iconDownArrow = new ImageIcon(MainFrame.class.getResource(
            "/images/" + "fleche.gif"));

    private static ImageIcon iconRightArrow = new ImageIcon(MainFrame.class.getResource(
            "/images/" +  "rightFleche.gif"));

    private static ImageIcon iconLeftArrow = new ImageIcon(MainFrame.class.getResource(
            "/images/" +  "leftFleche.gif"));

    private static ImageIcon iconDoubleRightArrow = new ImageIcon(MainFrame.class.getResource(
            "/images/" +  "rightDoubleFleche.gif"));

    private static ImageIcon iconDoubleLeftArrow = new ImageIcon(MainFrame.class.getResource(
            "/images/" + "leftDoubleFleche.gif"));


    private Calendar gregorianCalendar = new GregorianCalendar();

    int nCurrentSelectedMonth = gregorianCalendar.get(Calendar.MONTH);

    DTPickerCellRenderer cellCalendarRenderer = null;

    // JTextField jTextFieldDate = new JTextField();
    BorderLayout borderLayout1 = new BorderLayout();

    JPanel jPanel1 = new JPanel();

    JButton jButtonPlus = new JButton();

    JButton jButtonPlusYears = new JButton();

    JLabel jLabelDate = new JLabel();

    JButton jButtonMoins = new JButton();

    JButton jButtonMoinsYears = new JButton();

    FlowLayout flowLayout2 = new FlowLayout();

    DefaultTableModel model = new DefaultTableModel();

    JPanel jPanel2 = new JPanel();

    JTable jTableCalendrier = new JTable(model);

    BorderLayout borderLayout2 = new BorderLayout();

    JPanel jPanel3 = new JPanel();

    FlowLayout flowLayout3 = new FlowLayout();

    JLabel jLabel1 = new JLabel();

    JLabel jLabel2 = new JLabel();

    JLabel jLabel3 = new JLabel();

    JLabel jLabel4 = new JLabel();

    JLabel jLabel5 = new JLabel();

    JLabel jLabel6 = new JLabel();

    JLabel jLabel7 = new JLabel();

    Border border1;

    Border border2;

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    /**
     * Descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     */
    public DTPickerPanel() {
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // init calendar
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        // add colum to the table
        model.addColumn(rb.getString("LUNDI"));
        model.addColumn(rb.getString("MARDI"));
        model.addColumn(rb.getString("MERCREDI"));
        model.addColumn(rb.getString("JEUDI"));
        model.addColumn(rb.getString("VENDREDI"));
        model.addColumn(rb.getString("SAMEDI"));
        model.addColumn(rb.getString("DIMANCHE"));

        // set cell selectionnable
        jTableCalendrier.setCellSelectionEnabled(true);

        // lock inout data in cells
        for (int i = 0; i < 7; i++) {
            ((JTextField) ((DefaultCellEditor) jTableCalendrier.getDefaultEditor(model.getColumnClass(i)))
                    .getComponent()).setEditable(false);
        }

        // set single mode selection
        jTableCalendrier.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // create cell renderer
        cellCalendarRenderer = new DTPickerCellRenderer();

        // customize the table with good column width and cell renderer
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(0).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(0).setMinWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(1).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(1).setMinWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(2).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(2).setMinWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(3).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(3).setMinWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(4).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(4).setMinWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(5).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(5).setMinWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(6).setCellRenderer(cellCalendarRenderer);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(6).setPreferredWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(22);
        jTableCalendrier.getTableHeader().getColumnModel().getColumn(6).setMinWidth(22);

        // fill the calendar table
        fillCalendarTable(nCurrentSelectedMonth);

    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @exception Exception
     */
    private void jbInit() throws Exception {
        border1 = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black, 1), BorderFactory
                .createEmptyBorder(0, 0, 0, -1));
        border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(93, 93, 93),
                new Color(134, 134, 134));

        // jTextFieldDate.setBorder(null);
        // jTextFieldDate.setPreferredSize(new Dimension(71, 21));

        /*
         * jTextFieldDate.setMaximumSize(new Dimension(100, 21)); jTextFieldDate.setMinimumSize(new Dimension(10, 21));
         * jTextFieldDate.setPreferredSize(new Dimension(70, 21));
         */

        // jTextFieldDate.setEditable(false);
        // jTextFieldDate.setHorizontalAlignment(SwingConstants.CENTER);
        // jTextFieldDate.setText("31/12/2001");
        this.setLayout(gridBagLayout1);

        // this.setPreferredSize(new Dimension(121, 25));
        this.setBorder(border2);

        // super.setMinimumSize(new Dimension(91, 23));
        // super.setMaximumSize(new Dimension(91, 23));
        super.setPreferredSize(new Dimension(91, 23));

        // this.setMinimumSize(new Dimension(121, 25));
        this.setBackground(Color.lightGray);

        // this.setMaximumSize(new Dimension(121, 25));
        this.setLayout(borderLayout1);
        jLabelDate.setMaximumSize(new Dimension(70, 189));
        jLabelDate.setMinimumSize(new Dimension(70, 18));
        jLabelDate.setPreferredSize(new Dimension(70, 18));
        jLabelDate.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelDate.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanel1.setLayout(flowLayout2);
        flowLayout2.setHgap(2);
        flowLayout2.setVgap(2);
        jButtonMoins.setBorder(null);
        jButtonMoins.setMaximumSize(new Dimension(16, 16));
        jButtonMoins.setMinimumSize(new Dimension(16, 16));
        jButtonMoins.setPreferredSize(new Dimension(16, 16));
        jButtonMoins.setIcon(iconLeftArrow);
        jButtonMoins.setMargin(new Insets(1, 1, 1, 1));
        jButtonMoins.setDefaultCapable(false);
        jButtonMoins.addActionListener(new java.awt.event.ActionListener() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                jButtonMoins_actionPerformed(e);
            }

        });
        jButtonMoinsYears.setBorder(null);
        jButtonMoinsYears.setMaximumSize(new Dimension(16, 16));
        jButtonMoinsYears.setMinimumSize(new Dimension(16, 16));
        jButtonMoinsYears.setPreferredSize(new Dimension(16, 16));
        jButtonMoinsYears.setIcon(iconDoubleLeftArrow);
        jButtonMoinsYears.setMargin(new Insets(1, 1, 1, 1));
        jButtonMoinsYears.setDefaultCapable(false);
        jButtonMoinsYears.addActionListener(new java.awt.event.ActionListener() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                jButtonMoinsYears_actionPerformed(e);
            }

        });
        jButtonPlus.setBorder(null);
        jButtonPlus.setMaximumSize(new Dimension(16, 16));
        jButtonPlus.setMinimumSize(new Dimension(16, 16));
        jButtonPlus.setPreferredSize(new Dimension(16, 16));
        jButtonPlus.setIcon(iconRightArrow);
        jButtonPlus.setMargin(new Insets(1, 1, 1, 1));
        jButtonPlus.setDefaultCapable(false);
        jButtonPlus.addActionListener(new java.awt.event.ActionListener() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                jButtonPlus_actionPerformed(e);
            }

        });
        jButtonPlusYears.setBorder(null);
        jButtonPlusYears.setMaximumSize(new Dimension(16, 16));
        jButtonPlusYears.setMinimumSize(new Dimension(16, 16));
        jButtonPlusYears.setPreferredSize(new Dimension(16, 16));
        jButtonPlusYears.setIcon(iconDoubleRightArrow);
        jButtonPlusYears.setMargin(new Insets(1, 1, 1, 1));
        jButtonPlusYears.setDefaultCapable(false);
        jButtonPlusYears.addActionListener(new java.awt.event.ActionListener() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                jButtonPlusYears_actionPerformed(e);
            }

        });
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        jTableCalendrier.addMouseListener(new java.awt.event.MouseAdapter() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void mouseReleased(MouseEvent e) {
                jTableCalendrier_mouseReleased(e);
            }

        });
        jTableCalendrier.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jPanel2.setLayout(borderLayout2);
        jPanel3.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setHgap(0);
        flowLayout3.setVgap(0);
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel1.setMaximumSize(new Dimension(22, 17));
        jLabel1.setMinimumSize(new Dimension(22, 17));
        jLabel1.setPreferredSize(new Dimension(22, 17));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText(rb.getString("DIMANCHE"));
        jLabel2.setText(rb.getString("SAMEDI"));
        jLabel2.setPreferredSize(new Dimension(22, 17));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setMinimumSize(new Dimension(22, 17));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel2.setMaximumSize(new Dimension(22, 17));
        jLabel3.setText(rb.getString("VENDREDI"));
        jLabel3.setPreferredSize(new Dimension(22, 17));
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setMinimumSize(new Dimension(22, 17));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel3.setMaximumSize(new Dimension(22, 17));
        jLabel4.setText(rb.getString("JEUDI"));
        jLabel4.setPreferredSize(new Dimension(22, 17));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setMinimumSize(new Dimension(22, 17));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel4.setMaximumSize(new Dimension(22, 17));
        jLabel5.setText(rb.getString("MERCREDI"));
        jLabel5.setPreferredSize(new Dimension(22, 17));
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setMinimumSize(new Dimension(22, 17));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel5.setMaximumSize(new Dimension(22, 17));
        jLabel6.setText(rb.getString("MARDI"));
        jLabel6.setPreferredSize(new Dimension(22, 17));
        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel6.setMinimumSize(new Dimension(22, 17));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel6.setMaximumSize(new Dimension(22, 17));
        jLabel7.setText(rb.getString("LUNDI"));
        jLabel7.setPreferredSize(new Dimension(22, 17));
        jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel7.setMinimumSize(new Dimension(22, 17));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel7.setMaximumSize(new Dimension(22, 17));

        /*
         * this.add(jTextFieldDate, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0 ,GridBagConstraints.WEST,
         * GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); this.add(jButtonDown, new
         * GridBagConstraints(1, 0, 1, 1, 0.0, 0.0 ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0,
         * 0, 0), 0, 0));
         */
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jButtonMoins, null);
        jPanel1.add(jButtonMoinsYears, null);
        jPanel1.add(jLabelDate, null);
        jPanel1.add(jButtonPlusYears, null);
        jPanel1.add(jButtonPlus, null);
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jTableCalendrier, BorderLayout.CENTER);
        jPanel2.add(jPanel3, BorderLayout.NORTH);
        jPanel3.add(jLabel7, null);
        jPanel3.add(jLabel6, null);
        jPanel3.add(jLabel5, null);
        jPanel3.add(jLabel4, null);
        jPanel3.add(jLabel3, null);
        jPanel3.add(jLabel2, null);
        jPanel3.add(jLabel1, null);
        this.setPreferredSize(new Dimension(155, 137));
        
    }

    /**
     * private method that is called to fill the graphic component with a new month
     * 
     * @param ActionEvent :
     *        e, event that causes the action to be performed
     */
    protected void fillCalendarTable(int nMonth) {
        int nMaxDay = 1;
        int nMaxDayPrevious = 1;
        int nMinDay = 1;
        int nCol = 0;
        int nRow = 1;
        int nDay = 0;
        Object[] txtDay = null;

        
        Calendar calendar = (Calendar)gregorianCalendar.clone();
        
        // create the Date parser
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        // update calendar
        // calendar.set(Calendar.MONTH,nMonth);
        // set the gregorian calendar
        simpleDateFormat.setCalendar(calendar);

        // set the pattern
        simpleDateFormat.applyPattern(strDateFormat);

        // set the current day
        jLabelDate.setText(simpleDateFormat.format(calendar.getTime()));

        // remove all the olds values
        while (0 != model.getRowCount()) {
            model.removeRow(0);
        }

        nMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // get the fisrt day of the month
        nCol = calendar.get(Calendar.DATE);

        calendar.set(Calendar.DATE, 1);

        nDay = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.set(Calendar.DATE, nCol);

        // get the number of day for last month
        nCol = calendar.get(Calendar.MONTH);

        calendar.add(Calendar.MONTH, -1);

        nMaxDayPrevious = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);

        // create the first row
        txtDay = new Object[7];

        if (1 < nDay) {
            nDay = nDay - 2;
        }
        else {
            nDay = nDay + 5;
        }

        // create an empty row
        for (int i = 0; i < nDay; i++) {
            txtDay[i] = new DTPickerObject(nMaxDay - nDay + i + 1, false);
        }

        // set the column
        nCol = nDay;

        for (int i = 0; i < nMaxDay; i++) {
            if (7 == nCol) {

                // add a row each 7 days
                nCol = 0;

                // add the row
                model.addRow(txtDay);

                nRow = nRow + 1;

                // create new line
                txtDay = new Object[7];
            }

            // set the day
            txtDay[nCol] = new DTPickerObject(i + 1, true);
            nCol = nCol + 1;
        }

        // continue the row with the next month
        int i = 0;

        for (i = nCol; i < 7; i++) {
            txtDay[i] = new DTPickerObject(i - nCol + 1, false);
        }

        // add the last row
        nRow = nRow + 1;

        model.addRow(txtDay);

        // test if the Maximum row is reached
        if (nRow < 7) {
            txtDay = new Object[7];

            for (int j = 0; j < 7; j++) {
                txtDay[j] = new DTPickerObject(j + i + 1, false);
            }

            model.addRow(txtDay);
        }
    }

    /**
     * private method that is called when user increase the month.
     * 
     * @param ActionEvent :
     *        e, event that causes the action to be performed
     */
    void jButtonPlus_actionPerformed(ActionEvent e) {

        // Move to next month
        gregorianCalendar.add(Calendar.MONTH, 1);

        nCurrentSelectedMonth = gregorianCalendar.get(Calendar.MONTH);

        // repaint the table
        fillCalendarTable(nCurrentSelectedMonth);
    }

    /**
     * private method that is called when user increase the year.
     * 
     * @param ActionEvent :
     *        e, event that causes the action to be performed
     */
    void jButtonPlusYears_actionPerformed(ActionEvent e) {

        // Move to next month
        gregorianCalendar.add(Calendar.YEAR, 1);

        nCurrentSelectedMonth = gregorianCalendar.get(Calendar.YEAR);

        // repaint the table
        fillCalendarTable(nCurrentSelectedMonth);
    }

    /**
     * private method that is called when user decrease the month.
     * 
     * @param ActionEvent :
     *        e, event that causes the action to be performed
     */
    void jButtonMoins_actionPerformed(ActionEvent e) {

        // Move to previous month
        gregorianCalendar.add(Calendar.MONTH, -1);

        nCurrentSelectedMonth = gregorianCalendar.get(Calendar.MONTH);

        // repaint the table
        fillCalendarTable(nCurrentSelectedMonth);
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @param e
     */
    void jButtonMoinsYears_actionPerformed(ActionEvent e) {

        // Move to previous month
        gregorianCalendar.add(Calendar.YEAR, -1);

        nCurrentSelectedMonth = gregorianCalendar.get(Calendar.YEAR);

        // repaint the table
        fillCalendarTable(nCurrentSelectedMonth);
    }

    /**
     * private method that is called when user click on a cell. It select the date and filill the text area.
     * 
     * @param MouseEvent :
     *        e, event that causes the action to be performed
     */
    void jTableCalendrier_mouseReleased(MouseEvent e) {
        int nRow = jTableCalendrier.getSelectedRow();
        int nColumn = jTableCalendrier.getSelectedColumn();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        // change date only if user click on current month
        if (((DTPickerObject) model.getValueAt(nRow, nColumn)).isCurrent()) {
            gregorianCalendar.set(Calendar.DAY_OF_MONTH, ((DTPickerObject) model.getValueAt(nRow, nColumn)).getDay());

            // set the pattern
            simpleDateFormat.applyPattern(strDateFormat);

            // show the date
            // jTextFieldDate.setText(simpleDateFormat.format(gregorianCalendar.getTime()));
        }

        fireDateChange();
    }

    /**
     * Method that return the selected date
     * 
     * @return java.util.Calendar : the selected date or null
     */
    public Calendar getCalendarDate() {
        return gregorianCalendar;
    }

    /**
     * Method that set the initiale date in the date picker
     * 
     * @param Calendar :
     *        calendarDate, the date to put in the dtpicker
     */
    public void setDate(Calendar calendarDate) {

        // create the date parser
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);

        // set the date in the calendar
//        gregorianCalendar.set(Calendar.DATE, calendarDate.get(Calendar.DATE));
//        gregorianCalendar.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH));
//        gregorianCalendar.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR));
        gregorianCalendar.setTime(calendarDate.getTime());
        // get the new current month
        nCurrentSelectedMonth = gregorianCalendar.get(Calendar.MONTH);

        // set the current day
        jLabelDate.setText(simpleDateFormat.format(gregorianCalendar.getTime()));

        // repaint the table
        fillCalendarTable(nCurrentSelectedMonth);
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @param listener
     */
    public void addDateListener(DTPickerListener listener) {
        _someListener.add(DTPickerListener.class, listener);
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     * 
     * @param listener
     */
    public void removeDateListener(DTPickerListener listener) {
        _someListener.remove(DTPickerListener.class, listener);
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
     */
    protected void fireDateChange() {

        // Guaranteed to return a non-null array
        Object[] listeners = _someListener.getListenerList();

        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == DTPickerListener.class) {
                ((DTPickerListener) listeners[i + 1]).dateSelected();
            }
        }
    }

    /**
     * Method that return the selected date
     * 
     * @return java.util.Date : the selected date or null
     */
    public Date getDate() {
        return gregorianCalendar.getTime();
    }

}


