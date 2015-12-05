package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by luisburgos on 4/12/15.
 */
public class CodeModelSample {

    public static void main(String[] args) throws IOException, JClassAlreadyExistsException {
        JCodeModel cm = new JCodeModel();
        JDefinedClass dc = cm._class("foo.Bar");
        JMethod m = dc.method(0, int.class, "foo");
        m.body()._return(JExpr.lit(5));

        File file = new File("./target/classes");
        file.mkdirs();
        cm.build(file);
    }
}
