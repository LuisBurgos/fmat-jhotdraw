package org.fmatjhotdraw.classdiagram.samples;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import org.fmatjhotdraw.application.DrawApplication;
import org.fmatjhotdraw.classdiagram.generators.figuregenerator.FigureGeneratorCommand;
import org.fmatjhotdraw.classdiagram.generators.javagenerator.JavaGeneratorCommand;
import org.fmatjhotdraw.classdiagram.modeller.figures.ClassFigure;
import org.fmatjhotdraw.classdiagram.modeller.lines.*;
import org.fmatjhotdraw.classdiagram.modeller.util.ClassDiagramSelectionTool;
import org.fmatjhotdraw.figures.ConnectedTextTool;
import org.fmatjhotdraw.figures.TextFigure;
import org.fmatjhotdraw.framework.*;
import org.fmatjhotdraw.standard.ConnectionTool;
import org.fmatjhotdraw.standard.CreationTool;
import org.fmatjhotdraw.standard.ToggleGridCommand;
import org.fmatjhotdraw.util.CommandMenu;
import org.fmatjhotdraw.util.UndoableTool;

public class ClassDiagramModellerApplication extends DrawApplication {

    public ClassDiagramModellerApplication() {
        super("Class Diagram Editor");
    }

    protected void createTools(JToolBar palette) {
        super.createTools(palette);

        Tool tool = new UndoableTool(new ConnectedTextTool(this, new TextFigure()));
        palette.add(createToolButton(IMAGES+"ATEXT", "Label", tool));

        tool = new UndoableTool(new CreationTool(this, new ClassFigure()) {
             public void mouseDrag(MouseEvent e, int x, int y) {
                 // don't track drag events during creation: figure size is
                 //  calculated and independent of current size
             }
        });
        palette.add(createToolButton(IMAGES+"CLASS", "New Class", tool));

        tool = new UndoableTool(new ConnectionTool(this, new AggregationLineConnection()));
        palette.add(createToolButton(IMAGES+"AGGREGATION", "Aggregation Tool", tool));

        tool = new UndoableTool(new ConnectionTool(this, new CompositionLineConnection()));
        palette.add(createToolButton(IMAGES+"COMPOSITION", "Composition Tool", tool));

        tool = new UndoableTool(new ConnectionTool(this, new AssociationLineConnection()));
        palette.add(createToolButton(IMAGES+"ASSOCIATION", "Association Tool", tool));

        tool = new UndoableTool(new ConnectionTool(this, new DependencyLineConnection()));
        palette.add(createToolButton(IMAGES+"DEPENDENCY", "Dependency Tool", tool));

        tool = new UndoableTool(new ConnectionTool(this, new InheritanceLineConnection()));
        palette.add(createToolButton(IMAGES+"INHERITANCE", "Inheritance Tool", tool));
    }

    protected Tool createSelectionTool() {
        return new ClassDiagramSelectionTool(this);
    }

    protected void createMenus(JMenuBar mb) {
        mb.add(createFileMenu());
        mb.add(createEditMenu());
        //mb.add(createAlignmentMenu());
        //mb.add(createAttributesMenu());
        //mb.add(createLookAndFeelMenu());
        mb.add(createGeneratorsMenu());
    }

    private JMenu createGeneratorsMenu() {
        CommandMenu menu = new CommandMenu("Generators");
        menu.add(new JavaGeneratorCommand("Generate java", this));
        menu.add(new FigureGeneratorCommand("Generate class diagram", this));
        return menu;
    }

    protected JMenu createAttributesMenu() {
        JMenu menu = new JMenu("Attributes");
        menu.add(createColorMenu("Fill Color", FigureAttributeConstant.FILL_COLOR));
        menu.add(createColorMenu("Pen Color", FigureAttributeConstant.FRAME_COLOR));
        return menu;
    }

    protected JMenu createAlignmentMenu() {
        CommandMenu menu = new CommandMenu("Align");
        menu.add(new ToggleGridCommand("Toggle Snap to Grid", this, new Point(4,4)));
        return menu;
    }

    protected JMenu createFileMenu() {
        JMenu menu = super.createFileMenu();
        menu.insert(
            new AbstractAction("New Window") {
                public void actionPerformed(ActionEvent event) {
                    newWindow(createDrawing());
                }
            }, 5);

		menu.insertSeparator( 6 );

        return menu;
    }

    protected DrawingView createDrawingView() {
        DrawingView newView = super.createDrawingView();
        newView.setBackground(Color.white);
        return newView;
    }

    public static void main(String[] args) {
        ClassDiagramModellerApplication window = new ClassDiagramModellerApplication();
        window.open();
    }
}