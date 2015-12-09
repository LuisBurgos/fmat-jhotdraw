package org.fmatjhotdraw.classdiagram.modeller.figures;

import java.awt.Graphics;

import org.fmatjhotdraw.figures.LineFigure;

/**
 * A SeparatorFigure is a similar to a LineFigure
 * but draws only a horizontal line and separates
 * from other figures.
 */
public class SeparatorFigure extends LineFigure {

    static final long serialVersionUID = 7110919055213080660L;

    /**
     * Create a new SeparatorFigure
     */
    public SeparatorFigure() {
        super();
    }

    /**
     * Draw the separation line and to hold some space free
     */
    public void draw(Graphics g) {
        g.setColor(getFrameColor());
        g.drawLine(startPoint().x, startPoint().y, endPoint().x - 1, startPoint().y);
    }
}