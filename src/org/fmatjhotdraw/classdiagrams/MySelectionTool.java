package org.fmatjhotdraw.classdiagrams;

import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import org.fmatjhotdraw.framework.*;
import org.fmatjhotdraw.standard.*;


public class MySelectionTool extends SelectionTool {

    public MySelectionTool(DrawingEditor newDrawingEditor) {
        super(newDrawingEditor);
    }

    /**
     * Handles mouse down events and starts the corresponding tracker.
     * @param e
     * @param x
     * @param y
     */
    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        setView((DrawingView) e.getSource());
        if (e.getClickCount() == 2) {
            Figure figure = drawing().findFigure(e.getX(), e.getY());

            if (figure != null) {
                inspectFigure(figure);
                return;
            }
        }
        super.mouseDown(e, x, y);
    }

    protected void inspectFigure(Figure figurePressed) {
        if (figurePressed instanceof BasicClassFigure) {
            setNameToClass((BasicClassFigure)figurePressed);
        }
    }

    private void setNameToClass(BasicClassFigure classFigure) {
            String className = JOptionPane.showInputDialog(null, "Ingresa el nombre de la clase");
            if (className != null) {
                classFigure.getTitleSection().setText(className);
            }
    }
}
