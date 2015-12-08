package org.fmatjhotdraw.classdiagram.generators.figuregenerator;

import org.fmatjhotdraw.application.DrawApplication;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;
import org.fmatjhotdraw.classdiagram.modeller.figures.ClassFigure;
import org.fmatjhotdraw.framework.DrawingEditor;
import org.fmatjhotdraw.framework.DrawingView;
import org.fmatjhotdraw.framework.Figure;
import org.fmatjhotdraw.framework.FigureEnumeration;
import org.fmatjhotdraw.standard.AbstractCommand;
import org.fmatjhotdraw.standard.StandardDrawing;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by luisburgos on 8/12/15.
 */
public class FigureGeneratorCommand extends AbstractCommand {

    private DrawingEditor mEditor;

    public FigureGeneratorCommand(String newName, DrawingEditor newDrawingEditor) {
        super(newName, newDrawingEditor, true);
        mEditor = newDrawingEditor;
    }

    @Override
    public void execute() {
        super.execute();
        startClassDiagramGeneration();
    }

    private void startClassDiagramGeneration() {
        ClassFigureGenerator generator;
        generator = new ClassFigureGenerator
                .Builder()
                .build();

        final ArrayList<ClassFigure> figures = generator.generateClassFigures();
        addFiguresIntoDrawingView(figures);
    }

    private void addFiguresIntoDrawingView(final ArrayList<ClassFigure> figures) {
        for (ClassFigure figure : figures) {
            mEditor.view().drawing().add(figure);
        }
    }

}
