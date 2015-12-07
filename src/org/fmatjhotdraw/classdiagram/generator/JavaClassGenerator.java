package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.*;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by luisburgos on 4/12/15.
 */
public class JavaClassGenerator {

    private static final String DEFAULT_PACKAGE_NAME = "foo.";
    private static final String DEFAULT_DESTINATION_FOLDER = "./target/classes";

    private ArrayList<JModellerClass> modellerClasses;
    private String packageName;
    private String destinationFolder;

    public JavaClassGenerator(
            String packageName,
            String destinationFolder,
            ArrayList<JModellerClass> modellerClasses
    ) {
        this.packageName = packageName;
        this.destinationFolder = destinationFolder;
        this.modellerClasses = modellerClasses;
    }

    public void generate() throws JClassAlreadyExistsException, IOException {
        for(JModellerClass modellerBase : modellerClasses){
            generateJavaClass(modellerBase);
        }
    }

    private void generateJavaClass(JModellerClass modellerBase) throws IOException, JClassAlreadyExistsException {

        if(packageName == null){
            packageName = DEFAULT_PACKAGE_NAME;
        }

        if(destinationFolder == null){
            destinationFolder = DEFAULT_DESTINATION_FOLDER;
        }

        JCodeModel classModel = getClassModelFromModeller(modellerBase);
        //createFileIntoFolder(classModel);
    }

    private JCodeModel getClassModelFromModeller(JModellerClass modellerBase) throws JClassAlreadyExistsException {
        JCodeModel classModel = new JCodeModel();

        /*JDefinedClass definedClass = classModel._class(
                packageName + modellerBase.getName()
        );*/

        String className = modellerBase.getName();
        System.out.println("Class Name: " + className);

        Iterator attributeIterator = modellerBase.getAttributes();
        while (attributeIterator.hasNext()) {
            logClasElement(className, "attr", ((String)attributeIterator.next()));
        }

        Iterator methodsIterator = modellerBase.getMethods();
        while (methodsIterator.hasNext()) {
            logClasElement(className, "method", ((String)methodsIterator.next()));
        }

        Iterator associatedClasses = modellerBase.getAssociations();
        while (associatedClasses.hasNext()) {
            logClasElement(className, "association", ((JModellerClass)associatedClasses.next()).getName());
        }
        Iterator superclasses = modellerBase.getSuperclasses();
        while (superclasses.hasNext()) {
            logClasElement(className, "super", ((JModellerClass)superclasses.next()).getName());
        }
        Iterator dependClasses = modellerBase.getDependencies();
        while (dependClasses.hasNext()) {
            logClasElement(className, "dependency", ((JModellerClass)dependClasses.next()).getName());
        }

        //JMethod methodHolder = definedClass.method(0, int.class, "methodName");
        //methodHolder.body()._return(JExpr.lit(5));
        return classModel;
    }

    private void logClasElement(String className, String elementType, String element) {
        System.out.println("In " + className + ": " + elementType + " - " + element);
    }

    private void createFileIntoFolder(JCodeModel classModel) throws IOException {
        File file = new File(destinationFolder);
        file.mkdirs();
        classModel.build(file);
    }



}
