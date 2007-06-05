/*
 * Created on 1 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ui.util.dtpicker;


/**
 * @author basset
 *
 * Non Blocking dialog extends JDialog
 * 
 * Surcharge la JDialog classique pour permettre � deux frame 
 * d'avoir chacune leur Modal Dialog sans bloquer l'autre frame
 *  
 */

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class ParentModalDialog extends JDialog {
	protected boolean _modal = false;

	public ParentModalDialog(JFrame parent, String title, boolean modal) {
		super(parent, title, false);
		this._modal = modal;
	}

	public ParentModalDialog(JFrame parent, String title) {
		super(parent, title, false);
	}

	/**
	 * @param parent
	 */
	public ParentModalDialog(JFrame parent) {
		super(parent);
	}

	/**
	 * @param parent
	 */
	public ParentModalDialog(Frame parent) {
		super(parent);
	}

	/**
	 * @param parent
	 */
	public ParentModalDialog(Dialog parent) {
		super(parent);
	}

	/**
	 * @param parent
	 * @param modal
	 */
	public ParentModalDialog(JFrame parent, boolean modal) {
		super(parent, false);
		this._modal = modal;
	}
	
	/**
	 * @param parent
	 * @param modal
	 */
	public ParentModalDialog(Frame parent, boolean modal) {
		super(parent, false);
		this._modal = modal;
	}
	
	/**
	 * 
	 */
	public ParentModalDialog() {
		super();
	}

	public void dispose() {
		setVisible(false);
		super.dispose();
	}


	public void setVisible(boolean visible) {
		getParent().setEnabled(!(visible && _modal));
		super.setVisible(visible);
		if (_modal )
			try {
				if (SwingUtilities.isEventDispatchThread()) {
					EventQueue theQueue = getToolkit().getSystemEventQueue();
					while (isVisible()) {
						AWTEvent event = theQueue.getNextEvent();

						Object src = event.getSource();
						if (event instanceof ActiveEvent) {
							((ActiveEvent) event).dispatch();
						} else if (src instanceof Component) {
							((Component) src).dispatchEvent(event);
						}
					}
				} else
					synchronized (getTreeLock()) {
						while (isVisible() )
							try {
								getTreeLock().wait();
							} catch (InterruptedException e) {
								break;
							}
					}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	public void setModal(boolean modal) {
		this._modal = modal;
	}

	/*
	public static void main(String[] args) {
		JFrame f = new JFrame("Fenetre 1");
		JButton button = new JButton("Test");
		final ParentModalDialog dialog2 = new ParentModalDialog(f,
				"Test-Dialog", true);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dialog2.setVisible(true);

			}

		});
		dialog2.getContentPane().add(new JLabel("TEST"));
		dialog2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog2.setSize(300, 300);


		f.getContentPane().add(button);
		f.setSize(300, 300);
		f.setVisible(true);

		f = new JFrame("Fenetre Principale");
		final ParentModalDialog dialog = new ParentModalDialog(f,
				"Test-Dialog", true);
		JButton button2 = new JButton("click me");
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}

		});
		dialog.getContentPane().add(button2);
		dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		dialog.setSize(300, 300);

		f.getContentPane().add(
				new JButton(new AbstractAction("Dialog ") {
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(true);
						System.out
								.println("Sollte erst weitergehen, wenn Dialog geschlossen wurde.");
					}
				}));
		f.setSize(300, 300);
		f.setVisible(true);
	}
	*/
}