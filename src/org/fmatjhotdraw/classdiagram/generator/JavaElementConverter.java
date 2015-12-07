package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;

import java.util.Iterator;

/**
 * Created by luisburgos on 7/12/15.
 */
public class JavaElementConverter {

    private JCodeModel model;

    public JavaElementConverter() {
        this.model = new JCodeModel();
    }

    public void addClassName(Iterator classNameContainer) throws JClassAlreadyExistsException {

        while (classNameContainer.hasNext()) {
            String fullyclassName = ((String)classNameContainer.next());
            logClasElement("className", fullyclassName);
            JDefinedClass definedClass = model._class(
                fullyclassName
            );
        }
    }

    public void addAttributes(Iterator attributeIterator) {
        while (attributeIterator.hasNext()) {
            logClasElement("attr", ((String)attributeIterator.next()));
        }
    }

    public void addMethods(Iterator methodsIterator) {
        while (methodsIterator.hasNext()) {
            logClasElement("method", ((String)methodsIterator.next()));
            //JMethod methodHolder = definedClass.method(0, int.class, "methodName");
            //methodHolder.body()._return(JExpr.lit(5));
        }
    }

    public void addDependencies(Iterator dependClasses) {
        while (dependClasses.hasNext()) {
            logClasElement("dependency", ((JModellerClass)dependClasses.next()).getName());
        }
    }

    public void addSuperclass(Iterator superclasses) {
        while (superclasses.hasNext()) {
            logClasElement("super", ((JModellerClass)superclasses.next()).getName());
        }
    }

    public void addAssociations(Iterator associatedClasses) {
        while (associatedClasses.hasNext()) {
            logClasElement("association", ((JModellerClass)associatedClasses.next()).getName());
        }
    }


    public JCodeModel getCodeModel(){
        return model;
    }

    private void logClasElement(String elementType, String element) {
        System.out.println(elementType + " - " + element);
    }

}
