package org.fmatjhotdraw.classdiagram.modeller;

import org.fmatjhotdraw.contrib.CustomSelectionTool;
import org.fmatjhotdraw.framework.DrawingEditor;
import org.fmatjhotdraw.framework.Figure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Created by luisburgos on 1/12/15.
 */
public class ClassSelectionTool extends CustomSelectionTool {

    private EntityInputForm inputForm;

    public ClassSelectionTool(DrawingEditor newDrawingEditor) {
        super(newDrawingEditor);
    }

    protected void handleMouseDoubleClick(MouseEvent e, int x, int y) {
        Figure figure = drawing().findFigure(e.getX(), e.getY());
        if ((figure != null) && (figure instanceof ClassFigure)) {
            showConfigMenu(figure);
        }

    }

    private void showConfigMenu(final Figure figure) {
        System.out.println(((ClassFigure) figure).getClassNameFigure().getText());
        inputForm = new EntityInputForm.Builder(ClassFigureHolder.class)
                .setAddAction(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String className = inputForm.getTextFildByName("className").getText();
                        System.out.println(className);
                        ((ClassFigure) figure).updateClassNameFigure(className);
                        ((ClassFigure) figure).update();
                        inputForm.dispose();
                    }
                })
                .setCancelAction(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        inputForm.dispose();
                    }
                })
                .build();
    }

}

