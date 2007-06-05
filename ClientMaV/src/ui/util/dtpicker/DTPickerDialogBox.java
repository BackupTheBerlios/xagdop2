package ui.util.dtpicker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class DTPickerDialogBox extends ParentModalDialog 
{
	private DTPickerPanel _dtPickerPanel = null;
	private boolean _bIsValidate = false;
	
	public DTPickerDialogBox(JFrame owner)
	{
		super(owner, "Choix d'une date");
		this.setModal(true);
		this.init();
		this.pack();
		//UITools.centerOnMainFrame(this);
	}
	
	private void init()
	{
		//create layout
		this.getContentPane().setLayout(new BorderLayout());
		//create calendar panel
		_dtPickerPanel = new DTPickerPanel();
		//add the calendar panel
		this.getContentPane().add(_dtPickerPanel, BorderLayout.CENTER);
		//create control panel
		JPanel panelControl = new JPanel();
		//create valid button
		JButton btnOk = new JButton("Ok");
		//add button to control panel
		panelControl.add(btnOk);
		//add action on button
		btnOk.addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(ActionEvent)
             */
            public void actionPerformed(ActionEvent arg0) 
            {
            	valid();
            }
        });
		//create cancel button
		JButton btnCancel = new JButton("Cancel");
		//add button to control panel
		panelControl.add(btnCancel);
		//add action on button
		btnCancel.addActionListener(new ActionListener() {
            /**
             * @see ActionListener#actionPerformed(ActionEvent)
             */
            public void actionPerformed(ActionEvent arg0) 
            {
            	cancel();
            }
        });
        //add panel to the dialog box
        this.getContentPane().add(panelControl, BorderLayout.SOUTH);
	}
	
	private void cancel()
	{
		_bIsValidate = false;
		destroy();	
	}
	
	private void destroy()
	{
		//hide the dialog box
		this.setVisible(false);
		//destroy graphical component
		this.dispose();
	}
	
	private void valid()
	{
System.out.println("VALID");
		_bIsValidate = true;
		destroy();
	}
	
	public boolean isUserValidate()
	{
		return _bIsValidate;
	}
	
	public Calendar getSelectedDate()
	{
		return _dtPickerPanel.getCalendarDate();
	}
}
