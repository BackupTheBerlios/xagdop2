package src.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Nephos
 *
 */
public class MessageDialogBox {

	// log4J Category

	public final static int YES_OPTION = JOptionPane.YES_OPTION;

	public final static int YES_ALL_OPTION = JOptionPane.NO_OPTION;

	public final static int NO_OPTION = JOptionPane.CANCEL_OPTION;

	/**
	 * Show A Message Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strMessage_,
	 *            the message to display
	 */
	public static void showMessageDialog(JFrame frame_, String strTitle_, String strMessage_) {

		// Display dialog box
		JOptionPane.showMessageDialog(frame_, strMessage_, strTitle_,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show A Message Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strMessage_,
	 *            the message to display
	 */
	public static void showMessageDialogWithoutRb(JFrame frame_, String strTitle_,
			String strMessage_) {

		// Display dialog box
		JOptionPane.showMessageDialog(frame_, strMessage_,
				strTitle_, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show A Error Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strError_,
	 *            the error message to display
	 */
	public static void showErrorDialog(JFrame frame_, String strTitle_, String strError_) {

		// Display dialog box
		JOptionPane.showMessageDialog(frame_, strError_, strTitle_, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show A Confirm Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strMessage_,
	 *            the message to display
	 */
	public static boolean showConfirmDialog(JFrame frame_, String strTitle_, String strMessage_) {

		// Options
		Object[] options = {
				"Yes",
				"No" };

		// Display dialog box
		if (0 == JOptionPane.showOptionDialog(frame_, strMessage_, strTitle_, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1])) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Show A Confirm Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strMessage_,
	 *            the message to display
	 */
	public static int showAdvancedConfirmDialog(JFrame frame_, String strTitle_,
			String strMessage_, String [] args) {

		// Options
		Object[] options = {
				"Oui",
				"Non",
				"Annuler", };

		// Display dialog box
		int nDialogReturn = JOptionPane
				.showOptionDialog(
						frame_, strMessage_, strTitle_,
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

		return nDialogReturn;
	}

	/**
	 * Show A Confirm Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strMessage_,
	 *            the message to display
	 */
	public static int showConfirmForAllDialog(JFrame frame_, String strTitle_,
			String strMessage_) {

		// Options
		Object[] options = {
				"Oui",
				"Oui pour tous",
				"Non", };


		// Display dialog box
		return JOptionPane.showOptionDialog(frame_, strMessage_, strTitle_,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[1]);

	}

	public static boolean showConfirmDialogWithoutRb(JFrame frame_, String strTitle_,
			String strMessage_) {

		// Options
		Object[] options = {
				"Oui",
				"Non",
				 };


		// Display dialog box
		if (0 == JOptionPane.showOptionDialog(frame_, strMessage_,
				strTitle_, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1])) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Show A Confirm Dialog Box
	 * 
	 * @param strTitle_,
	 *            the box title
	 * @param strMessage_,
	 *            the message to display
	 * 
	 * @return boolean, true = retry, false = cancel
	 */
	public static boolean showRetryOrCancelDialog(JFrame frame_, String strTitle_,
			String strMessage_) {

		// Options
				Object[] options = {
						"Réessayer",
						"Annuler", };

		// Display dialog box
		if (0 == JOptionPane.showOptionDialog(frame_, strMessage_, strTitle_,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				options, options[0])) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Show A Choice Dialog Box
	 * 
	 * @param strTitle_, the box title @param strMessage_, the message to
	 * display @param possibleValues_, an <I>Object[]</I> containing values;
	 * 
	 * @return User's input
	 */
	public static Object showChoiceDialog(JFrame frame_, String strTitle_, String strMessage_,
			Object[] possibleValues_) {

		// Display dialog box
		return JOptionPane.showInputDialog(frame_, strMessage_
				+ " : ", strTitle_,
				JOptionPane.PLAIN_MESSAGE, null, possibleValues_,
				possibleValues_[0]);
	}
}
