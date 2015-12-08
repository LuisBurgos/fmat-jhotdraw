package org.fmatjhotdraw.classdiagram.generators.figuregenerator;

import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;
import org.fmatjhotdraw.classdiagram.modeller.figures.ClassFigure;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
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

        final ArrayList<JModellerClass> modellers = generateModellers();

        final ArrayList<ClassFigure> generatedFigures = new ArrayList<>();
        generateFiguresFromModellers(modellers, generatedFigures);

        return generatedFigures;
    }

    private void generateFiguresFromModellers(ArrayList<JModellerClass> modellers,
                                              ArrayList<ClassFigure> generatedFigures) {
        ClassFigure figure;
        for(int i = 0; i < modellers.size(); i++){
            JModellerClass modellerClass = modellers.get(i);
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

    public ArrayList<JModellerClass> generateModellers(){
        ArrayList<JModellerClass> ms = new ArrayList<>();
        JModellerClass modeller = new JModellerClass();
        modeller.setName("New Class");
        modeller.addAttribute("attribute 1");
        modeller.addMethod("method()");

        JModellerClass modeller1 = new JModellerClass();
        modeller1.setName("New Class 2");
        modeller1.addAttribute("attribute 2");
        modeller1.addAttribute("attribute 2.2");
        modeller1.addMethod("method2()");
        //modeller1.addSuperclass(modeller);
        ms.add(modeller1);
        JModellerClass modeller2 = new JModellerClass();
        modeller2.setName("New Class 3");
        modeller2.addAttribute("attribute 3");
        modeller2.addMethod("method3()");
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
