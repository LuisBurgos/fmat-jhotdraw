package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.JClassAlreadyExistsException;
import org.fmatjhotdraw.classdiagram.modeller.figures.ClassFigure;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;
import org.fmatjhotdraw.framework.DrawingEditor;
import org.fmatjhotdraw.framework.Figure;
import org.fmatjhotdraw.framework.FigureEnumeration;
import org.fmatjhotdraw.standard.AbstractCommand;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luisburgos on 6/12/15.
 */
public class GeneratorCommand extends AbstractCommand {

    public GeneratorCommand(String newName, DrawingEditor newDrawingEditor) {
        super(newName, newDrawingEditor);
    }

    @Override
    public void execute() {
        super.execute();
        startClassGeneration();
    }

    private ArrayList<JModellerClass> getJModellersFromFigures(FigureEnumeration figures) {
        ArrayList<JModellerClass> modellerClasses = new ArrayList<>();
        while (figures.hasNextFigure()) {
            Figure f = figures.nextFigure();
            if(f instanceof ClassFigure){
                modellerClasses.add(((ClassFigure) f).getModellerClass());
            }
        }
        return modellerClasses;
    }

    private void startClassGeneration() {
        FigureEnumeration figures = getFigures();
        JavaClassGenerator generator = new JavaClassGenerator(
                null,
                null,
                getJModellersFromFigures(figures)
        );

        try {
            generator.generate();
        } catch (JClassAlreadyExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FigureEnumeration getFigures() {
        return view().drawing().figures();
    }
}
