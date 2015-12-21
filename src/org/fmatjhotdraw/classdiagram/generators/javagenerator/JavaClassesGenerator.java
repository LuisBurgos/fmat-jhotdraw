package org.fmatjhotdraw.classdiagram.generators.javagenerator;

import com.sun.codemodel.*;
import org.fmatjhotdraw.classdiagram.modeller.ClassDiagramModel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luisburgos on 4/12/15.
 */
public class JavaClassesGenerator {

    private static final String DEFAULT_PACKAGE_NAME = "foo.";
    private static final String DEFAULT_DESTINATION_FOLDER = "./target/classes";
    private ArrayList<ClassDiagramModel> modellerClasses;
    public static String packageName;
    public static String destinationFolder;

    private JavaClassesGenerator(Builder builder) {
        this.packageName = builder.mPackageName;
        this.destinationFolder = builder.mDestinationFolder;
        this.modellerClasses = builder.mModellerClasses;
    }

    public void generate() throws JClassAlreadyExistsException, IOException {
        for(ClassDiagramModel modellerBase : modellerClasses){
            generateJavaClass(modellerBase);
        }
    }

    private void generateJavaClass(ClassDiagramModel modellerBase) throws IOException, JClassAlreadyExistsException {
        JCodeModel classModel = parseClassModelFromModeller(modellerBase);
        JavaFilesGenerator.createFileIntoFolder(classModel, destinationFolder);
    }

    private JCodeModel parseClassModelFromModeller(ClassDiagramModel modellerBase) throws JClassAlreadyExistsException {
        JavaElementReader reader = getReaderFromModellerClass(modellerBase);
        JCodeModel classModel = reader.parse();
        return classModel;
    }

    private JavaElementReader getReaderFromModellerClass(ClassDiagramModel modellerBase) {
        JavaElementReader reader = new JavaElementReader();
        reader.addElement(JavaElement.CLASSNAME, modellerBase.getName());
        reader.addElement(JavaElement.ATTR, modellerBase.getAttributes());
        reader.addElement(JavaElement.METHOD, modellerBase.getMethods());
        reader.addElement(JavaElement.ASSOCIATION, modellerBase.getAssociations());
        reader.addElement(JavaElement.DEPENDENCY, modellerBase.getDependencies());
        reader.addElement(JavaElement.PARENT, modellerBase.getSuperclasses());
        return  reader;
    }

    public static class Builder {
        private String mPackageName;
        private String mDestinationFolder;
        private ArrayList<ClassDiagramModel> mModellerClasses;

        public Builder(ArrayList<ClassDiagramModel> modellerClasses){
            if(modellerClasses == null){
                throw new IllegalArgumentException("Classes can not be null");
            }
            mModellerClasses = modellerClasses;
        }

        public Builder setPackageName(String packageName){
            mPackageName = packageName;
            return this;
        }

        public Builder setDestinationFolder(String destinationFolder){
            mDestinationFolder = destinationFolder;
            return this;
        }

        public JavaClassesGenerator build(){
            if(mPackageName == null){
                mPackageName = DEFAULT_PACKAGE_NAME;
            }

            if(mDestinationFolder == null){
                mDestinationFolder = DEFAULT_DESTINATION_FOLDER;
            }
            return new JavaClassesGenerator(this);
        }
    }
}
