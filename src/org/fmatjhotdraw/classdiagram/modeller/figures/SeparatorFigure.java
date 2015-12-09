package org.fmatjhotdraw.classdiagram.modeller.figures;

import java.awt.Graphics;

import org.fmatjhotdraw.figures.LineFigure;

public class SeparatorFigure extends LineFigure {

    static final long serialVersionUID = 7110919055213080660L;

    public SeparatorFigure() {
        super();
    }

    public void draw(Graphics g) {
        g.setColor(getFrameColor());
        g.drawLine(startPoint().x, startPoint().y, endPoint().x - 1, startPoint().y);
    }
}