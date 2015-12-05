package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.*;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;

import java.io.File;
import java.io.IOException;

/**
 * Created by luisburgos on 4/12/15.
 */
public class JavaClassGenerator {

    private static final String PACKAGE_NAME = "foo.";
    private static final String DESTINATION_FOLDER = "./target/classes";


    public static void generate(JModellerClass modellerBase) throws JClassAlreadyExistsException, IOException {

        JCodeModel classModel = new JCodeModel();
        JDefinedClass definedClass = classModel._class(
                PACKAGE_NAME + modellerBase.getName()
        );

        //JMethod methodHolder = definedClass.method(0, int.class, "foo");
        //methodHolder.body()._return(JExpr.lit(5));

        createFileIntoFolder(classModel);
    }

    private static void createFileIntoFolder(JCodeModel classModel) throws IOException {
        File file = new File(DESTINATION_FOLDER);
        file.mkdirs();
        classModel.build(file);
    }

}
