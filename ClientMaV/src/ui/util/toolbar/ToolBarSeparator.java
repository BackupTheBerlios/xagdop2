

package ui.util.toolbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JSeparator;
import javax.swing.UIManager;

/**
 * Astek separator for Astek Toolbar
 *
 * @see
 * @Copyrigth Astek
 */
public class ToolBarSeparator extends JSeparator {

	// Log

	// Attributes
	private static final Color colorShadow = UIManager.getColor("Separator.shadow");
	private static final Color colorHighlight = UIManager.getColor("Separator.highlight");

	/**
	 * Constructor
	 *
	 * @param nOrientation_
	 */
	public ToolBarSeparator(int nOrientation_) {
		super(nOrientation_);

		// correctif taille du s�parateur
		if (nOrientation_ == JSeparator.VERTICAL) {
			final int H = 22;
			final int W = 15;

			setMaximumSize(new Dimension(W, H));
			setMinimumSize(new Dimension(W, H));

			setPreferredSize(new Dimension(W, H));
		}
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param g
	 */
	public void paint(Graphics g) {
//		super.paint(g);
		update(g);
	}

	/**
	 * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
	 *
	 * @param g
	 */
	public void update(Graphics g) {
		Dimension d = getSize();

		if (getOrientation() == JSeparator.VERTICAL) {
			g.setColor(colorShadow);
			g.drawLine(4, 0, 4, d.height);
			g.setColor(colorHighlight);
			g.drawLine(5, 0, 5, d.height);
		} else {
			g.setColor(colorShadow);
			g.drawLine(0, 4, d.width, 4);
			g.setColor(colorHighlight);
			g.drawLine(0, 5, d.width, 5);
		}
	}

}

