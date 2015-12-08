package org.fmatjhotdraw.classdiagram.generators.figuregenerator;

import org.fmatjhotdraw.framework.DrawingEditor;
import org.fmatjhotdraw.standard.AbstractCommand;

/**
 * Created by luisburgos on 8/12/15.
 */
public class FigureGeneratorCommand extends AbstractCommand {

    public FigureGeneratorCommand(String newName, DrawingEditor newDrawingEditor) {
        super(newName, newDrawingEditor);
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("Generating class diagram..");
    }

}
