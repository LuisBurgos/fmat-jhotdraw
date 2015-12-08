package org.fmatjhotdraw.classdiagram.generators.javagenerator;

import com.sun.codemodel.*;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;

import java.util.Iterator;

/**
 * Created by luisburgos on 7/12/15.
 */
public class JavaElementConverter {

    private JCodeModel model;
    private JDefinedClass definedClass;
    private JMethod methodHolder;
    private String fullyClassName;

    public JavaElementConverter() {
        this.model = new JCodeModel();
    }

    public void addClassName(Iterator classNameContainer) throws JClassAlreadyExistsException {
        while (classNameContainer.hasNext()) {
            fullyClassName = JavaClassesGenerator.packageName;
            fullyClassName += ((String)classNameContainer.next());
            logClasElement("className", fullyClassName);
            definedClass = model._class(fullyClassName);
        }
    }

    public void addAttributes(Iterator attributeIterator) {
        while (attributeIterator.hasNext()) {
            String currentAttr = ((String)attributeIterator.next());
            logClasElement("attr", currentAttr);
            addAttribute(currentAttr);
        }
    }

    private void addAttribute(String currentAttr) {
        definedClass.field(JMod.PUBLIC, int.class, currentAttr);
    }

    public void addMethods(Iterator methodsIterator) {
        while (methodsIterator.hasNext()) {
            String currentMethod = ((String)methodsIterator.next());
            logClasElement("method", currentMethod);
            addMethod(currentMethod);
        }
    }

    private void addMethod(String currentMethod) {
        methodHolder = definedClass.method(JMod.PUBLIC, void.class, currentMethod);
    }

    public void addDependencies(Iterator dependClasses) {
        while (dependClasses.hasNext()) {
            String currentDependencie = ((JModellerClass)dependClasses.next()).getName();
            logClasElement("dependency", currentDependencie);
            addDependencie(currentDependencie);
        }
    }

    private void addDependencie(String currentDependencie) {
        addAttribute(currentDependencie);
    }

    public void addSuperclass(Iterator superclasses) throws JClassAlreadyExistsException {
        while (superclasses.hasNext()) {
            String superclassName = ((JModellerClass)superclasses.next()).getName();
            logClasElement("super", superclassName);
            JDefinedClass definedSuperclass = model._class(
                    JavaClassesGenerator.packageName + superclassName,
                    ClassType.CLASS
            );
            definedClass._extends(definedSuperclass);
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
