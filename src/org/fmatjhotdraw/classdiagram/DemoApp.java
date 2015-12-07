package org.fmatjhotdraw.classdiagram;

import org.fmatjhotdraw.application.DrawApplication;
import org.fmatjhotdraw.classdiagram.legacy.BasicClassFigure;
import org.fmatjhotdraw.classdiagram.modeller.ClassSelectionTool;
import org.fmatjhotdraw.figures.*;
import org.fmatjhotdraw.framework.Tool;
import org.fmatjhotdraw.standard.CreationTool;

import javax.swing.JToolBar;

public class DemoApp extends DrawApplication {

    public DemoApp() {
        super("Class Diagram Modeller");
    }

    protected void createTools(JToolBar palette) {
        super.createTools(palette);

        Tool tool = new TextTool(this, new TextFigure());
        palette.add(createToolButton(IMAGES + "TEXT", "Text Tool", tool));
            
        tool = new CreationTool(this, new BasicClassFigure());
        palette.add(createToolButton(IMAGES + "CLASS", "Class tool", tool));
    }

    @Override
    protected Tool createSelectionTool() {
        return new ClassSelectionTool(this);
    }

    public static void main(String[] args) {
        DrawApplication window = new DemoApp();
        window.open();
    }
}
