package org.fmatjhotdraw.classdiagram.generators.figuregenerator;

import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;

import java.util.ArrayList;

/**
 * Created by luisburgos on 8/12/15.
 */
public class ClassFigureGenerator {

    private static final String DEFAULT_SOURCES_FOLDER = "./target/sources";
    private JModellerClass modellerClass;
    public static String sourcesFolder;

    private ClassFigureGenerator(Builder builder) {
        this.sourcesFolder = builder.mDestinationFolder;
        this.modellerClass = builder.mModellerClass;
    }

    public static class Builder {
        private String mPackageName;
        private String mDestinationFolder;
        private JModellerClass mModellerClass;

        public Builder(JModellerClass modellerClass){
            if(modellerClass == null){
                throw new IllegalArgumentException("Classes can not be null");
            }
            mModellerClass = modellerClass;
        }

        public Builder setDestinationFolder(String destinationFolder){
            mDestinationFolder = destinationFolder;
            return this;
        }

        public ClassFigureGenerator build(){
            if(mDestinationFolder == null){
                mDestinationFolder = DEFAULT_SOURCES_FOLDER;
            }
            return new ClassFigureGenerator(this);
        }
    }


}
