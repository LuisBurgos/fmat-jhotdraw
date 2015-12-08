package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.JCodeModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by luisburgos on 7/12/15.
 */
public class JavaFilesGenerator {

    public static void createFileIntoFolder(
            JCodeModel classModel,
            String destinationFolder
    ) throws IOException {
        File file = new File(destinationFolder);
        file.mkdirs();
        classModel.build(file);
    }

}
