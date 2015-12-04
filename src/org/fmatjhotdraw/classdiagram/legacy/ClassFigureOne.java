package org.fmatjhotdraw.classdiagram.legacy;


import org.fmatjhotdraw.contrib.GraphicalCompositeFigure;
import org.fmatjhotdraw.figures.RectangleFigure;
import org.fmatjhotdraw.figures.TextFigure;
import org.fmatjhotdraw.framework.Figure;

public class ClassFigureOne extends GraphicalCompositeFigure {

    private JModellerClass           myClass;
    private GraphicalCompositeFigure myAttributesFigure;
    private GraphicalCompositeFigure myMethodsFigure;
    private TextFigure classNameFigure;

    public ClassFigureOne() {
        this(new ClassRectangleFigure());
    }

    public ClassFigureOne(Figure newPresentationFigure) {
        super(newPresentationFigure);
    }

    /**
     * Hook method called to initialize a ClassFigureOne.
     * It is called from the superclass' constructor and the clone() method.
     */
    protected void initialize() {
        // start with an empty Composite
        removeAll();
        // create a new Model object associated with this View figure
        setModellerClass(new JModellerClass());
        // create a TextFigure responsible for the class name
        setClassNameFigure(new TextFigure() {
            public void setText(String newText) {
                super.setText(newText);
                getModellerClass().setName(newText);
                update();
            }
        });
        // add the TextFigure to a Composite
        GraphicalCompositeFigure nameFigure = new GraphicalCompositeFigure(new RectangleFigure());
        nameFigure.add(getClassNameFigure());

        add(nameFigure);
        // create a figure responsible for maintaining attributes
        setAttributesFigure(new GraphicalCompositeFigure(new RectangleFigure()));

        add(getAttributesFigure());
        // create a figure responsible for maintaining methods
        setMethodsFigure(new GraphicalCompositeFigure(new RectangleFigure()));

        add(getMethodsFigure());
        setAttribute(Figure.POPUP_MENU, createPopupMenu());
        super.initialize();
    }

    private Object createPopupMenu() {
        return null;
    }

    public JModellerClass getModellerClass() {
        return myClass;
    }

    public void setModellerClass(JModellerClass myClass) {
        this.myClass = myClass;
    }

    public GraphicalCompositeFigure getAttributesFigure() {
        return myAttributesFigure;
    }

    public void setAttributesFigure(GraphicalCompositeFigure myAttributesFigure) {
        this.myAttributesFigure = myAttributesFigure;
    }

    public GraphicalCompositeFigure getMethodsFigure() {
        return myMethodsFigure;
    }

    public void setMethodsFigure(GraphicalCompositeFigure myMethodsFigure) {
        this.myMethodsFigure = myMethodsFigure;
    }

    public TextFigure getClassNameFigure() {
        return classNameFigure;
    }

    public void setClassNameFigure(TextFigure classNameFigure) {
        this.classNameFigure = classNameFigure;
    }
}