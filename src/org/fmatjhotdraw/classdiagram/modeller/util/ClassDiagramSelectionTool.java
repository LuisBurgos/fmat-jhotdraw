package org.fmatjhotdraw.classdiagram.modeller.util;

import java.awt.event.MouseEvent;

import org.fmatjhotdraw.contrib.CustomSelectionTool;
import org.fmatjhotdraw.figures.TextFigure;
import org.fmatjhotdraw.figures.TextTool;
import org.fmatjhotdraw.framework.DrawingEditor;
import org.fmatjhotdraw.framework.Figure;

public class ClassDiagramSelectionTool extends CustomSelectionTool {

    private TextTool mTextTool;
    
    public ClassDiagramSelectionTool(DrawingEditor newEditor) {
        super(newEditor);
        setTextTool(new TextTool(newEditor, new TextFigure()));
    }

    /**
     * Hook method which can be overriden by subclasses to provide
     * specialised behaviour in the event of a mouse double click.
     * @param e
     * @param x
     * @param y
     */
    protected void handleMouseDoubleClick(MouseEvent e, int x, int y) {
        Figure figure = drawing().findFigureInside(e.getX(), e.getY());
        if ((figure != null) && (figure instanceof TextFigure)) {
            getTextTool().activate();
            getTextTool().mouseDown(e, x, y);

        }
    }

    /**
     * Hook method which can be overriden by subclasses to provide
     * specialised behaviour in the event of a mouse down.
     */
    protected void handleMouseClick(MouseEvent e, int x, int y) {
        deactivate();
    }

    /**
     * Terminates the editing of a text figure.
     */
    public void deactivate() {
        super.deactivate();
        if (getTextTool().isActive()) {
    	    getTextTool().deactivate();
        }
    }

    /**
     * Set the text tool to which double clicks should be delegated. The text tool is shared by
     * all figures upon which this selection tool operates.
     *
     * @param newTextTool delegate text tool
     */
    protected void setTextTool(TextTool newTextTool) {
        mTextTool = newTextTool;
    }

    /**
     * Return the text tool to which double clicks are delegated. The text tool is shared by
     * all figures upon which this selection tool operates.
     *
     * @return delegate text tool
     */
    protected TextTool getTextTool() {
       return mTextTool;
    }
}