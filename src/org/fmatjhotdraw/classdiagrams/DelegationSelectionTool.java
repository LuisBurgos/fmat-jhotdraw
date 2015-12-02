package org.fmatjhotdraw.classdiagrams;


import org.fmatjhotdraw.contrib.CustomSelectionTool;
import org.fmatjhotdraw.figures.TextFigure;
import org.fmatjhotdraw.figures.TextTool;
import org.fmatjhotdraw.framework.DrawingEditor;
import org.fmatjhotdraw.framework.Figure;

import java.awt.event.MouseEvent;

public class DelegationSelectionTool extends CustomSelectionTool {

    private TextTool myTextTool;

    public DelegationSelectionTool(DrawingEditor view) {
        super(view);
        setTextTool(new TextTool(view, new TextFigure()));
    }

    protected void handleMouseDoubleClick(MouseEvent e, int x, int y) {
        Figure figure = drawing().findFigureInside(e.getX(), e.getY());
        if ((figure != null) && (figure instanceof TextFigure)) {
            getTextTool().activate();
            getTextTool().mouseDown(e, x, y);
        }
        System.out.println("DOBLE CLIKC");
    }

    protected void handleMouseClick(MouseEvent e, int x, int y) {
        deactivate();
    }

    public void deactivate() {
        super.deactivate();
        if (getTextTool().isActive()) {
            getTextTool().deactivate();
        }
    }

    public TextTool getTextTool() {
        return myTextTool;
    }

    public void setTextTool(TextTool myTextTool) {
        this.myTextTool = myTextTool;
    }
}