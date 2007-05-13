package ui.util.toolbar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;



public class RollabledButton extends Button {


    // Constants
    private static final int SIZE_X = 22;

    private static final int SIZE_Y = 22;

    private static final int MARGIN_X = 0;

    private static final int MARGIN_Y = 0;

    // Attributes
    private static final Color colorHighlight = new Color(200, 200, 200);

    /**
     * Constructor
     */
    public RollabledButton() {

        // Parent
        super();

        // set the dimension of the button
        this.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
        this.setMinimumSize(new Dimension(SIZE_X, SIZE_Y));
        this.setMaximumSize(new Dimension(SIZE_X, SIZE_Y));

        // set the margin of the button
        this.setMargin(new Insets(MARGIN_X, MARGIN_Y, MARGIN_X, MARGIN_Y));

        // Hide Focus Paint
        this.setFocusPainted(false);

        // Set Not Opaque
        this.setOpaque(false);

        // undo the selection of the button
        this.setSelected(false);

        // set the button transparent
        this.setContentAreaFilled(false);

        // unpaint the border
        this.setBorderPainted(false);

        // change the border
        this
                .setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, colorHighlight, null, Color.lightGray,
                        null));

        // add mouse listener
        this.addMouseListener(new MouseAdapter() {

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void mouseEntered(MouseEvent e) {

                // setBackground(Color.white);
                if (isEnabled()) {
                    setBorderPainted(true);
                }
            }

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void mouseExited(MouseEvent e) {

                // allow border painting
                setBorderPainted(false);
            }

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void mousePressed(MouseEvent e) {

                // change the border to lower when user clicked
                setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, colorHighlight, null, Color.lightGray,
                        null));
            }

            /**
             * descriptif de la m�thode (fonctionnalit� de la methode + mini algorithme si elle est compliqu�e)
             * 
             * @param e
             */
            public void mouseReleased(MouseEvent e) {

                // change the border to raised when user relaesed mouse's button
                setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, colorHighlight, null, Color.lightGray,
                        null));
            }

        });
    }

}
