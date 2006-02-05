package xagdop.Util;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import xagdop.ressources.Bundle;

/**
 * The ErrorManager contains all the methods needed to log messages and errors
 *
 * @version $Revision: 1.1 $
 */
public class ErrorManager {

	private static ErrorManager msErrorManager ;
	
	private Component owner = null;
	private JTextArea errorArea = null;
	private String errLog;
	private String errTitle;

	private ErrorManager() { }

	public static ErrorManager getInstance()
	{
		if(msErrorManager==null)
			msErrorManager = new ErrorManager();
		return msErrorManager;
	}

	public void setOwner(Component _owner)
	{
		owner = _owner;
	}

	public void setErrorArea(JTextArea area)
	{
		errorArea = area;
	}

	public void setErrMsg(String errMsg) {
		this.errLog = errMsg;
	}

	public void setErrTitle(String errTitle) {
		this.errTitle = errTitle;
	}

	/**
	 * Display a given a Throwable object.
	 *
	 * @param t Throwable
	 */
	public void display(Throwable t)
	{
		t.printStackTrace();
		display(t.getClass().getName(), t.getMessage());
	}

	/**
	 * Display a message
	 *
	 * @param title the message's title
	 * @param msg the message itself
	 */
	public void display(String title, String msg)
	{
		JOptionPane.showMessageDialog(owner,
		                              msg,
		                              title,
		                              JOptionPane.ERROR_MESSAGE);
	}
	public void display(){
		JOptionPane.showMessageDialog(owner,
                errLog,
                errTitle,
                JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Add a log message to the JTextArea corresponding
	 * to the given message key
	 *
	 * @param messageKey
	 */
	public void printKey(String messageKey)
	{
		println(Bundle.getText(messageKey));
	}


	/**
	 * Add a log message to the JTextArea
	 *
	 * @param message
	 */
	public synchronized void println(String message)
	{
		Date current_date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String result = "";
		
		if(message!=null && !message.equals(""))
		{
			result = "[" + formatter.format(current_date) + "] " + message;
		}
		
		if(errorArea == null)
		{
			System.err.println(result);
		}
		else
		{
			errorArea.append(result+"\n");
		}
	}
}
