
package ui.util.toolbar;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.SystemColor;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Astek Tool Bar
 * 
 * @see @Copyrigth Astek
 */
public class ToolBar extends JToolBar
{
    // Log

    // Constants
    private static final int MARGIN_X = 0;

    private static final int MARGIN_Y = 0;

    // Attributes
    private Color colorStart = SystemColor.controlLtHighlight;

    private Color colorEnd = SystemColor.controlShadow;

    /**
     * Constructor
     */
    public ToolBar()
    {

        // Parent
        super();

        // set the margin
        this.setMargin(new Insets(MARGIN_X, MARGIN_Y, MARGIN_X, MARGIN_Y));

        // disable floatable
        this.setFloatable(false);

        // don't paint the border line
        this.setBorderPainted(false);
    }

    /**
     * Constructor
     * 
     * @param colorStart_
     * @param colorEnd_
     */
    public ToolBar(Color colorStart_, Color colorEnd_)
    {

        // Call Other constructor
        this();

        // Set Attributes
        colorStart = colorStart_;
        colorEnd = colorEnd_;
    }

    /**
     * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme
     * si elle est compliqu�e)
     * 
     * @param g
     */
    public void paintComponent(Graphics g)
    {
        boolean bOpaque = isOpaque();
        
        if (bOpaque)
        {
            Graphics2D g2d = (Graphics2D)g.create();
            GradientPaint gp = null;

            if (getOrientation() == VERTICAL)
            {
                gp = new GradientPaint(0, 0, colorStart, getWidth(), 0, colorEnd,
                        false);
            }
            else
            {
                gp = new GradientPaint(0, 0, colorStart, 0, getHeight(), colorEnd,
                        false);
            }
            
            Paint oldPaint = g2d.getPaint();
            g2d.setPaint(gp);
            g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
            g2d.setPaint(oldPaint);
            
            g2d.dispose();
            
        }
        
        setOpaque(false);
        super.paintComponent(g);
        setOpaque(bOpaque);
        
    }

    /**
     * @see JToolBar#add(Action)
     */
    public JButton add(Action a)
    {

        JButton btn = new ToolBarButton();
        btn.setAction(a);
        
        add(btn);

        return btn;
    }
}

