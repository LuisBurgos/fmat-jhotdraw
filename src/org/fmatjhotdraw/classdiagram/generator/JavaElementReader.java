package org.fmatjhotdraw.classdiagram.generator;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.fmatjhotdraw.classdiagram.generator.JavaElement.CLASSNAME;

/**
 * Created by luisburgos on 7/12/15.
 */
public class JavaElementReader {

    private HashMap<JavaElement, Iterator> elements;
    private JavaElementConverter converter;

    public JavaElementReader() {
        this.elements = new HashMap<>();
        this.converter = new JavaElementConverter();
    }

    public void addElement(JavaElement key, String singleValue) {
        ArrayList list = new ArrayList();
        list.add(singleValue);
        elements.put(key , list.iterator());
    }

    public void addElement(JavaElement key, Iterator value) {
        elements.put(key, value);
    }

    public JCodeModel parse() throws JClassAlreadyExistsException {
        for(JavaElement element : elements.keySet()){
            Iterator iterator = elements.get(element);
            switch (element){
                case CLASSNAME:
                    converter.addClassName(iterator);
                    break;
                case ATTR:
                    converter.addAttributes(iterator);
                    break;
                case METHOD:
                    converter.addMethods(iterator);
                    break;
                case ASSOCIATION:
                    converter.addAssociations(iterator);
                    break;
                case DEPENDENCY:
                    converter.addDependencies(iterator);
                    break;
                case PARENT:
                    converter.addSuperclass(iterator);
                    break;
            }
        }
        return getGeneratedModel();
    }

    private JCodeModel getGeneratedModel() {
        return converter.getCodeModel();
    }

}
