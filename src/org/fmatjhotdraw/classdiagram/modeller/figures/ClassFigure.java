package org.fmatjhotdraw.classdiagram.modeller.figures;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import org.fmatjhotdraw.classdiagram.modeller.ClassDiagramModel;
import org.fmatjhotdraw.contrib.GraphicalCompositeFigure;
import org.fmatjhotdraw.figures.RectangleFigure;
import org.fmatjhotdraw.figures.TextFigure;
import org.fmatjhotdraw.framework.Figure;
import org.fmatjhotdraw.framework.FigureAttributeConstant;
import org.fmatjhotdraw.framework.FigureChangeEvent;
import org.fmatjhotdraw.framework.HandleEnumeration;
import org.fmatjhotdraw.standard.BoxHandleKit;
import org.fmatjhotdraw.standard.HandleEnumerator;
import org.fmatjhotdraw.util.CollectionsFactory;
import org.fmatjhotdraw.util.StorableInput;
import org.fmatjhotdraw.util.StorableOutput;

public class ClassFigure extends GraphicalCompositeFigure {

    private final String DEFAULT_METHOD_NAME = "method";
    private final String DEFAULT_ATTR_NAME = "attribute";
    private ClassDiagramModel myClass;
    private Font attributeFont;
    private Font methodFont;
    private GraphicalCompositeFigure myAttributesFigure;
    private GraphicalCompositeFigure myMethodsFigure;
    private TextFigure myClassNameFigure;
    private final static Font CLASSNAME_FONT = new Font("Helvetica", Font.BOLD, 12);
    private final static Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, 12);

    private final static Insets INSETS0400 = new Insets(0, 4, 0, 0);
    private final static Insets INSETS4440 = new Insets(4, 4, 4, 0);
    private final static Insets INSETS4444 = new Insets(4, 4, 4, 4);

    static final long serialVersionUID = 6098176631854387694L;

    public ClassFigure() {
        this(new RectangleFigure());
    }

    public ClassFigure(Figure newPresentationFigure) {
        super(newPresentationFigure);
    }

    /**
     * Hook method called to initialize a ClassFigure.
     * It is called from the constructors and the clone() method.
     */
    protected void initialize() {
        // start with an empty Composite
        removeAll();

        // set the fonts used to print attributes and methods
        attributeFont = DEFAULT_FONT;
        methodFont = DEFAULT_FONT;


        if (getModellerClass() == null) {
            setModellerClass(new ClassDiagramModel());

        }

        // create a TextFigure responsible for the class name
        setClassNameFigure(new TextFigure() {
            public void setText(String newText) {
                super.setText(newText);
                getModellerClass().setName(newText);
                update();
            }
        });

        getClassNameFigure().setFont(CLASSNAME_FONT);
        getClassNameFigure().setText(getModellerClass().getName());

        // add the TextFigure to the Composite
        GraphicalCompositeFigure nameFigure = new GraphicalCompositeFigure(new SeparatorFigure());
        nameFigure.add(getClassNameFigure());
        nameFigure.getLayouter().setInsets(INSETS4444);
        add(nameFigure);

        // create a figure responsible for maintaining attributes
        setAttributesFigure(new GraphicalCompositeFigure(new SeparatorFigure()));
        getAttributesFigure().getLayouter().setInsets(INSETS4444);
        // add the figure to the Composite
        add(getAttributesFigure());

        // create a figure responsible for maintaining methods
        setMethodsFigure(new GraphicalCompositeFigure(new SeparatorFigure()));
        getMethodsFigure().getLayouter().setInsets(INSETS4444);
        // add the figure to the Composite
        add(getMethodsFigure());

        setAttribute(FigureAttributeConstant.POPUP_MENU, createPopupMenu());

        super.initialize();
    }

    protected JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("add attribute") {
            public void actionPerformed(ActionEvent event) {
                addAttribute(DEFAULT_ATTR_NAME);
            }
        });
        popupMenu.add(new AbstractAction("add method") {
            public void actionPerformed(ActionEvent event) {
                addMethod(DEFAULT_METHOD_NAME);
            }
        });

        popupMenu.setLightWeightPopupEnabled(true);
        return popupMenu;
    }

    protected void setAttributesFigure(GraphicalCompositeFigure newAttributesFigure) {
        myAttributesFigure = newAttributesFigure;
    }

    public GraphicalCompositeFigure getAttributesFigure() {
        return myAttributesFigure;
    }

    protected void setMethodsFigure(GraphicalCompositeFigure newMethodsFigure) {
        myMethodsFigure = newMethodsFigure;
    }

    public GraphicalCompositeFigure getMethodsFigure() {
        return myMethodsFigure;
    }

    protected void setClassNameFigure(TextFigure newClassNameFigure) {
        myClassNameFigure = newClassNameFigure;
    }

    public TextFigure getClassNameFigure() {
        return myClassNameFigure;
    }

    //TODO: Needs to be refactor
    public void addAttribute(String newAttribute) {
        TextFigure classFigureAttribute;
        if(!getModellerClass().hasAttribute(newAttribute)
                && (Objects.equals(newAttribute, DEFAULT_ATTR_NAME))){
            getModellerClass().addAttribute(newAttribute);
            classFigureAttribute = new TextFigure() {
                public void setText(String newString) {
                    if (!getText().equals(newString)) {
                        getModellerClass().renameAttribute(getText(), newString);
                    }
                    super.setText(newString);
                    updateAttributeFigure();
                }
            };

        }else {
            classFigureAttribute = new TextFigure();
        }
        classFigureAttribute.setText(newAttribute);
        classFigureAttribute.setFont(attributeFont);
        getAttributesFigure().add(classFigureAttribute);
        updateAttributeFigure();
        return;
    }

    protected void removeAttribute(Figure oldAttribute) {
        getModellerClass().removeAttribute(((TextFigure)oldAttribute).getText());
        getAttributesFigure().remove(oldAttribute);
        updateAttributeFigure();
    }

    protected void updateAttributeFigure() {
        getAttributesFigure().update();
        update();
    }

    public void addMethod(String newMethod) {
        TextFigure classFigureMethod;
        if(!getModellerClass().hasMethod(newMethod)
                && (Objects.equals(newMethod, DEFAULT_METHOD_NAME))){
            getModellerClass().addMethod(newMethod);
            classFigureMethod = new TextFigure() {
                public void setText(String newString) {
                    if (!getText().equals(newString)) {
                        getModellerClass().renameMethod(getText(), newString);
                    }
                    super.setText(newString);
                    updateMethodFigure();
                }
            };
        }else {
            classFigureMethod = new TextFigure();
        }
        classFigureMethod.setText(newMethod);
        classFigureMethod.setFont(methodFont);
        getMethodsFigure().add(classFigureMethod);
        updateMethodFigure();
    }

    protected void removeMethod(Figure oldMethod) {
        getModellerClass().removeMethod(((TextFigure)oldMethod).getText());
        getMethodsFigure().remove(oldMethod);
        updateMethodFigure();
    }

    protected void updateMethodFigure() {
        getMethodsFigure().update();
        update();
    }

    protected void setModellerClass(ClassDiagramModel newClass) {
        myClass = newClass;
    }

    public ClassDiagramModel getModellerClass() {
        return myClass;
    }

    public void updateModellerClass(ClassDiagramModel newModeller){
        setModellerClass(newModeller);
        //initialize();
        updateComponents();
        update();
    }

    private void updateComponents() {
        updateClassNameFigure(getModellerClass().getName());
    }

    public void figureRequestRemove(FigureChangeEvent e) {
        Figure removeFigure = e.getFigure();
        if (getAttributesFigure().includes(removeFigure)) {
            removeAttribute(removeFigure);
        }
        else if (getMethodsFigure().includes(removeFigure)) {
            removeMethod(removeFigure);
        }
        else {
            // remove itself
            listener().figureRequestRemove(new FigureChangeEvent(this, displayBox()));
        }
    }

    public HandleEnumeration handles() {
        /*List handles = CollectionsFactory.current().createList(4);

        handles.add(new NullHandle(getPresentationFigure(), RelativeLocator.northWest()));
        handles.add(new NullHandle(getPresentationFigure(), RelativeLocator.northEast()));
        handles.add(new NullHandle(getPresentationFigure(), RelativeLocator.southWest()));
        handles.add(new NullHandle(getPresentationFigure(), RelativeLocator.southEast()));

        return new HandleEnumerator(handles);*/
        List handles = CollectionsFactory.current().createList();
        BoxHandleKit.addHandles(this, handles);
        return new HandleEnumerator(handles);
    }

    public boolean isEmpty() {
        return figureCount() == 0;
    }

    public void read(StorableInput dr) throws IOException {
        getClassNameFigure().setText(dr.readString());

        int attributesCount = dr.readInt();
        for (int attributeIndex = 0; attributeIndex < attributesCount; attributeIndex++) {
            addAttribute(dr.readString());
        }

        int methodsCount = dr.readInt();
        for (int methodIndex = 0; methodIndex < methodsCount; methodIndex++) {
            addMethod(dr.readString());
        }
        setPresentationFigure((Figure)dr.readStorable());
        setAttribute(FigureAttributeConstant.POPUP_MENU, createPopupMenu());
        update();
    }

    /**
     * Write the figure and its contained elements to the StorableOutput.
     */
    public void write(StorableOutput dw) {
        dw.writeString(getModellerClass().getName());
        dw.writeInt(getModellerClass().getNumberOfAttributes());

        Iterator attributeIterator = getModellerClass().getAttributes();
        while (attributeIterator.hasNext()) {
            dw.writeString((String)attributeIterator.next());
        }
        dw.writeInt(getModellerClass().getNumberOfMethods());

        Iterator methodIterator = getModellerClass().getMethods();
        while (methodIterator.hasNext()) {
            dw.writeString((String)methodIterator.next());
        }
        dw.writeStorable(getPresentationFigure());
    }

    /**
     * Read the serialized figure and its contained elements from the input stream and
     * creates the popup menu
     */
    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
        // call superclass' private readObject() indirectly
        s.defaultReadObject();

        setAttribute(FigureAttributeConstant.POPUP_MENU, createPopupMenu());
    }

    public void updateClassNameFigure(String newName) {
        getClassNameFigure().setText(newName);
    }
}
