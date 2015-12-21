package org.fmatjhotdraw.classdiagram.modeller;

import java.util.*;
import java.io.Serializable;

/**
 * A JModellerClass represents a class in a class diagram. It is known by its name
 * and has attributes and methods and keeps track of its superclasses, associations
 * and dependencies.
 */
public class ClassDiagramModel implements Serializable {

    private static final String DEFAULT_CLASS_NAME = "Class";
    private String myName;
    private Vector myAttributes;
    private Vector myMethods;
    private Vector myAssociatedClasses;
    private Vector mySuperclasses;
    private Vector myDependClasses;

    static final long serialVersionUID = -3748332488864682801L;

    public ClassDiagramModel() {
        this(DEFAULT_CLASS_NAME);
    }

    public ClassDiagramModel(String newClassName) {
        setName(newClassName);
        myAttributes = new Vector();
        myMethods = new Vector();
        myAssociatedClasses = new Vector();
        mySuperclasses = new Vector();
        myDependClasses = new Vector();
    }

    public void setName(String newName) {
        myName = newName;
    }
    
    public String getName() {
        return myName;
    }
    
    public void addAttribute(String newAttribute) {
        myAttributes.addElement(newAttribute);
    }
    
    public void removeAttribute(String oldAttribute) {
        myAttributes.removeElement(oldAttribute);
    }

    public void renameAttribute(String oldAttribute, String newAttribute) {
        int attributeIndex = myAttributes.indexOf(oldAttribute);
        if (attributeIndex >= 0) {
            myAttributes.removeElementAt(attributeIndex);
            myAttributes.insertElementAt(newAttribute, attributeIndex);
        }
    }

    public Iterator getAttributes() {
        return myAttributes.iterator();
    }

    public Vector getVectorAttributes() {
        return myAttributes;
    }

    public Vector getVectorMethods() {
        return myMethods;
    }

    public int getNumberOfAttributes() {
        return myAttributes.size();
    }

    public boolean hasAttribute(String checkAttributeName) {
        return myAttributes.contains(checkAttributeName);
    }

    public void addMethod(String newMethod) {
        myMethods.addElement(newMethod);
    }

    public void removeMethod(String oldMethod) {
        myMethods.removeElement(oldMethod);
    }

    public void renameMethod(String oldMethod, String newMethod) {
        int methodIndex = myMethods.indexOf(oldMethod);
        if (methodIndex >= 0) {
            myMethods.removeElementAt(methodIndex);
            myMethods.insertElementAt(newMethod, methodIndex);
        }
    }

    public Iterator getMethods() {
        return myMethods.iterator();
    }

    public int getNumberOfMethods() {
        return myMethods.size();
    }

    public boolean hasMethod(String checkMethodName) {
        return myMethods.contains(checkMethodName);
    }

    public void addAssociation(ClassDiagramModel newAssociatedClass) {
        myAssociatedClasses.add(newAssociatedClass);
    }

    public void removeAssociation(ClassDiagramModel oldAssociatedClass) {
        myAssociatedClasses.remove(oldAssociatedClass);
    }

    public boolean hasAssociation(ClassDiagramModel checkAssociatedClass) {
        return myAssociatedClasses.contains(checkAssociatedClass);
    }

    public Iterator getAssociations() {
        return myAssociatedClasses.iterator();
    }

    public void addSuperclass(ClassDiagramModel newSuperclass) {
        mySuperclasses.add(newSuperclass);
    }
    
    public void removeSuperclass(ClassDiagramModel oldSuperclass) {
        mySuperclasses.remove(oldSuperclass);
    }

    public Iterator getSuperclasses() {
        return mySuperclasses.iterator();
    }   

    /**
     * Checks whether class has an inheritance cycle. A inheritance
     * cycle is encountered
     * - if the possible subclass it the same as the current class
     * - if the possible subrclass is already a superclass of the current class
     *
     * @param   possibleSuperclass  class to which should
     */
    public boolean hasInheritanceCycle(ClassDiagramModel possibleSubclass) {
        if (possibleSubclass == this) {
            return true;
        }

        return possibleSubclass.isSuperclass(this);

    }

    /**
     * Checks whether this class is the superclass of the class to test
     *
     * @param   possibleSubclass    class which should be subclass to this class or its superclasses
     */
    public boolean isSuperclass(ClassDiagramModel possibleSubclass) {
        if (possibleSubclass.mySuperclasses.contains(this)) {
            return true;
        }
        
        Iterator i = possibleSubclass.getSuperclasses();
        while (i.hasNext()) {
            Object currentObject = i.next();
            if (isSuperclass((ClassDiagramModel) currentObject)) {
                return true;
            }
        }

        return false;
    }

    public void addDependency(ClassDiagramModel newDependency) {
        myDependClasses.add(newDependency);
    }
    
    public void removeDependency(ClassDiagramModel oldDependency) {
        myDependClasses.remove(oldDependency);
    }
    
    public boolean hasDependency(ClassDiagramModel checkDependency) {
        return myDependClasses.contains(checkDependency);
    }
    
    public Iterator getDependencies() {
        return myDependClasses.iterator();
    }

    @Override
    public String toString() {
        String msg = "";
        msg += myName;
        msg += myAttributes.toString();
        msg += myAssociatedClasses.toString();
        msg += myMethods.toString();
        msg += myDependClasses.toString();
        msg += mySuperclasses.toString();
        return msg;

    }
}