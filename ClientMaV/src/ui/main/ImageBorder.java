package ui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * A border with an image.
 *
 * @version $Revision: 1.1 $
 * @author Frederic LAVIGNE
 */
public class ImageBorder extends AbstractBorder implements javax.swing.SwingConstants {

    /**
     * The image alignment (default is BOTTOM)
     */
    protected int align = BOTTOM;

    /**
     * The border position (default is WEST)
     */ 
    protected int position = WEST;

    /**
     * The image to draw
     */
    protected Image image;

    /**
     * Number of steps in The gradient
     */
    protected int gradientCount = 30;

    /**
     * Start Color for the gradient
     */
    protected Color start = Color.blue;

    /**
     * End Color for the gradient
     */
    protected Color end = Color.black;

    /**
     * Construct a new ImageBorder
     *
     * @param image the image to draw
     */
    public ImageBorder(Image image) {
	if (image == null)
	    throw new IllegalArgumentException("Image cannot be null");
	//image = ImageUtils.rotateImage(anImage);
	this.image = image;
    }
    
    /**
     * Construct a new ImageBorder
     *
     * @param image the image to draw
     * @param position the position of the image (NORTH, SOUTH, EAST, WEST)
     * @param align the alignment of the image (LEFT, RIGHT, TOP, BOTTOM, CENTER)
     */
    public ImageBorder(Image image, int position, int align) {
	this(image);
	setPosition(position);
	setImageAlignment(align);
    }

    /**
     * Set the colors used for the gradient
     *
     * @param start the start color
     * @param end the end color
     */
    public void setColors(Color start, Color end) {
	this.start = start;
	this.end = end;
    }

    /**
     * Set the number of steps in the gradient
     *
     * @param c a value of type 'int'
     */
    public void setGradientCount(int c) {
	gradientCount = c;
    }

    /**
     * TOP, CENTER, BOTTOM
     */
    public void setImageAlignment(int align) {
	this.align = align;
    }

    /**
     * LEFT (WEST), RIGHT (EAST), TOP(NORTH) ,BOTTOM (SOUTH),
     */
    public void setPosition(int position) {
	this.position = position;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	Color oldColor = g.getColor();

	int ix = 0;
	int iy = 0;
	int iwidth = 0;
	int iheight = 0;

	int imgX = 0, imgY = 0;

	int gradient = HORIZONTAL;

	switch (position) {
	case NORTH:
	    ix = x;
	    iy = y;
	    iwidth = width;
	    iheight = image.getHeight(c);
	    gradient = VERTICAL;
	    break;
	case SOUTH:
	    ix = x;
	    iy = y + height - image.getHeight(c);
	    iwidth = width;
	    iheight = image.getHeight(c);
	    gradient = VERTICAL;
	    break;
	case WEST:
	    ix = x;
	    iy = y;
	    iwidth = image.getWidth(c);
	    iheight = height;
	    gradient = HORIZONTAL;
	    break;
	case EAST:
	    ix = x + width - image.getWidth(c);
	    iy = y;
	    iwidth = image.getWidth(c);
	    iheight = height;
	    gradient = HORIZONTAL;
	}

	switch (align) {
	case TOP:
	    imgX = x;
	    imgY = y;
	    break;
	case BOTTOM:
	    imgX = x;
	    imgY = y + height - image.getHeight(c);
	    break;
	case LEFT:
	    imgX = x;
	    imgY = y;
	    break;
	case RIGHT:
	    imgX = x + width - image.getWidth(c);
	    imgY = 0;
	    break;
	case CENTER:
	    if ((position == NORTH) || (position == SOUTH)) {
		imgX = x + (width - image.getWidth(c)) / 2;
		imgY = 0;
	    } else {
		imgX = 0;
		imgY = y + (height - image.getHeight(c)) / 2;
	    }
	}

	fillGradient(g, start, end, ix, iy, iwidth, iheight, gradientCount, gradient);

	g.setColor(oldColor);

	g.drawImage(image, imgX, imgY, c);
    }
    
    public Insets getBorderInsets(Component c) {
	if (position == NORTH) {
	    return new Insets(image.getHeight(c), 0, 0, 0);
	} else if (position == SOUTH) {
	    return new Insets(0, 0, image.getHeight(c), 0);
	} else if (position == WEST) {
	    return new Insets(0, image.getWidth(c), 0, 0);
	} else if (position == EAST) {
	    return new Insets(0, 0, 0, image.getWidth(c));
	} else {
	    return null;
	}
    }
    
    public Insets getBorderInsets(Component c, Insets insets) {
	Insets myInsets = getBorderInsets(c);
	insets.top = myInsets.top;
	insets.right = myInsets.right;
	insets.bottom = myInsets.bottom;
	insets.left = myInsets.left;
	return insets;
    }

    public boolean isBorderOpaque() {
	return true;
    }
    /**
     * Fill a rectangle with a gradient of colors
     *
     * @param gr the graphics to fill
     * @param start the start color
     * @param end the end color
     * @param x the rectangle x coordinate
     * @param y the rectangle y coordinate
     * @param w the rectangle w coordinate
     * @param h the rectangle h coordinate
     * @param n the number of steps in the gradient
     * @param direction VERTICAL or HORIZONTAL
     */
    public static void fillGradient(Graphics gr, Color start, Color end, int x, int y, int w, int h, int n, int direction) {

	int space = 0;
	if (direction == VERTICAL) {
	    space = (int)(w/n);
	} else {
	    space = (int)(h/n);
	}
	int r = start.getRed();
	int g = start.getGreen();
	int b = start.getBlue();
	int r2 = end.getRed();
	int g2 = end.getGreen();
	int b2 = end.getBlue();
    
	int rp = (int)((r2 - r)/n);
	int gp = (int)((g2 - g)/n);
	int bp = (int)((b2 - b)/n);
    
	for (int i = 0; i < n; i++) {
	    gr.setColor(new Color(r,g,b));
	    r = r + rp;
	    g = g + gp;
	    b = b + bp;
	    if (direction == VERTICAL) {
		gr.fillRect(x+space*i,y,space,h);
	    } else {
		gr.fillRect(x,y+space*i,w,space);
	    }
	}
	if (direction == VERTICAL) {
	    gr.fillRect(x+space*n,y,w - x+space*n,h);
	} else {
	    gr.fillRect(x,y+space*n,w,h-y+space*n);
	}
    }
    
}

