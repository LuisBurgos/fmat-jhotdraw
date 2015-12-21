package org.fmatjhotdraw.classdiagram.generators.figuregenerator;

import org.fmatjhotdraw.classdiagram.modeller.ClassDiagramModel;
import org.fmatjhotdraw.classdiagram.modeller.figures.ClassFigure;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by luisburgos on 8/12/15.
 */
public class ClassFigureGenerator {

    private static final String DEFAULT_SOURCES_FOLDER = "./target/sources";
    public static String sourcesFolder;
    private String classFilesGenerated = sourcesFolder + "/class";

    private ClassFigureGenerator(Builder builder) {
        this.sourcesFolder = builder.mSourcesFolder;
    }

    public ArrayList<ClassFigure> generateClassFigures(){

        final File sourcesDirectoryFolder = new File(sourcesFolder);
        final ArrayList<File> files = new ArrayList();

        listFilesForFolder(sourcesDirectoryFolder, files);

        final ArrayList<String> fileNames = new ArrayList();
        generateCompiledClassFromFiles(files, fileNames);

        final ArrayList<ClassDiagramModel> modellers = generateModellers();

        final ArrayList<ClassFigure> generatedFigures = new ArrayList<>();
        generateFiguresFromModellers(modellers, generatedFigures);

        return generatedFigures;
    }

    private void generateFiguresFromModellers(ArrayList<ClassDiagramModel> modellers,
                                              ArrayList<ClassFigure> generatedFigures) {
        ClassFigure figure;
        for(int i = 0; i < modellers.size(); i++){
            ClassDiagramModel modellerClass = modellers.get(i);
            figure = new ClassFigure();
            figure.updateModellerClass(modellerClass);

            final Vector v = modellerClass.getVectorAttributes();
            Enumeration vEnum = v.elements();
            while(vEnum.hasMoreElements()) {
                figure.addAttribute((String) vEnum.nextElement());
            }


            final Vector v2 = modellerClass.getVectorMethods();
            Enumeration v2Enum = v2.elements();
            while(v2Enum.hasMoreElements()) {
                figure.addMethod((String) v2Enum.nextElement());
            }

            figure.update();
            generatedFigures.add(figure);
        }
    }

    private void generateCompiledClassFromFiles(final ArrayList<File> files, ArrayList<String> fileNames) {

    }

    public ArrayList<ClassDiagramModel> generateModellers(){
        ArrayList<ClassDiagramModel> ms = new ArrayList<>();
        ClassDiagramModel modeller = new ClassDiagramModel();
        modeller.setName("NewClass");
        modeller.addAttribute("attribute1");
        modeller.addMethod("method");

        ClassDiagramModel modeller1 = new ClassDiagramModel();
        modeller1.setName("NewClass2");
        modeller1.addAttribute("attribute2");
        modeller1.addAttribute("attribute3");
        modeller1.addMethod("method2");
        //modeller1.addSuperclass(modeller);
        ms.add(modeller1);
        ClassDiagramModel modeller2 = new ClassDiagramModel();
        modeller2.setName("NewClass3");
        modeller2.addAttribute("attribute3");
        modeller2.addMethod("method3");
        //modeller2.addSuperclass(modeller1);
        ms.add(modeller2);
        //modeller.addSuperclass(modeller2);
        ms.add(modeller);

        return ms;
    }

    private void listFilesForFolder(final File folder, final ArrayList<File> files) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, files);
            } else {
                //JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                //int code = compiler.run(null, null, null, fileEntry.getPath());
                //System.out.println(code);
                if(fileEntry.getName().contains(".java")){
                    files.add(fileEntry);
                }
            }
        }
    }

    public static class Builder {
        private String mSourcesFolder;

        public Builder(){}

        public Builder setSourcesFolder(String sourcesFolder){
            mSourcesFolder = sourcesFolder;
            return this;
        }

        public ClassFigureGenerator build(){
            if(mSourcesFolder == null){
                mSourcesFolder = DEFAULT_SOURCES_FOLDER;
            }
            return new ClassFigureGenerator(this);
        }
    }


}
