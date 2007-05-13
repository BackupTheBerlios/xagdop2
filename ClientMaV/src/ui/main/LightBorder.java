package ui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * A simple border with 2 lines.
 *
 * @author LAVIGNE Frederic
 * @version $Revision: 1.1 $
 */
public class LightBorder extends AbstractBorder {
    
    /**
     * The raised border
     */
    private static Border raisedBorder;

    /**
     * The lowered border
     */
    private static Border loweredBorder;

    /**
     * the high color of the border
     */
    protected Color highColor;

    /**
     * the low color of the border
     */
    protected Color lowColor;

    /**
     * Get the raised border
     *
     * @return the raised border
     */
    public static Border createRaisedBorder() {
	if (raisedBorder == null) {
	    raisedBorder = new LightBorder(Color.white, Color.lightGray.darker().darker());
	}
	return raisedBorder;
    }

    /**
     * Get the lowered border
     *
     * @return the lowered border
     */
    public static Border createLoweredBorder() {
	if (loweredBorder == null) {
	    loweredBorder = new LightBorder(Color.lightGray.darker().darker(), Color.white);
	}
	return loweredBorder;
    }

    /**
     * Constructs a new LightBorder
     *
     * @param color1 the high color
     * @param color2 the low color
     */
    public LightBorder(Color color1, Color color2) {
	highColor = color1;
	lowColor = color2;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	Color oldColor = g.getColor();
	boolean isReleased = true;

	if (c instanceof AbstractButton) {
	    AbstractButton b = (AbstractButton)c;
	    ButtonModel model = b.getModel();
	    isReleased = !((model.isPressed() && model.isArmed()) || (model.isSelected()));
	}

	g.setColor(isReleased?highColor:lowColor);
	g.drawLine(x, y, x + width - 1, y);
	g.drawLine(x, y, x, y + height - 1);
	
	g.setColor(isReleased?lowColor:highColor);
	g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
	g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
    
	g.setColor(oldColor);
    }

    public Insets getBorderInsets(Component c) {
	return new Insets(1, 1, 1, 1);
    }

    public Insets getBorderInsets(Component c, Insets insets) {
	insets.left = insets.top = insets.right = insets.bottom = 1;
	return insets;
    }

    public boolean isBorderOpaque() { return true; }
	
}
