package org.fmatjhotdraw.classdiagram.modeller.lines;

import org.fmatjhotdraw.classdiagram.modeller.figures.ClassFigure;
import org.fmatjhotdraw.classdiagram.modeller.JModellerClass;
import org.fmatjhotdraw.figures.ArrowTip;
import org.fmatjhotdraw.figures.LineConnection;
import org.fmatjhotdraw.framework.Figure;

import java.awt.*;

/**
 * Created by luisburgos on 3/12/15.
 */
public class AggregationLineConnection extends LineConnection {

    static final long serialVersionUID = 3140686678671889499L;

    /**
     * Create a new instance with a predefined arrow
     */
    public AggregationLineConnection() {
        setStartDecoration(null);
        ArrowTip arrowEnd = new ArrowTip(0.4, 12.0, 0.0);
        arrowEnd.setFillColor(Color.white);
        arrowEnd.setBorderColor(Color.black);
        setEndDecoration(arrowEnd);

        AggregationDecoration arrowStart = new AggregationDecoration();
        setStartDecoration(arrowStart);
    }

    /**
     * Hook method to plug in application behaviour into
     * a template method. This method is called when a
     * connection between two objects has been established.
     */
    protected void handleConnect(Figure start, Figure end) {
        super.handleConnect(start, end);

        JModellerClass startClass = ((ClassFigure)start).getModellerClass();
        JModellerClass endClass = ((ClassFigure)end).getModellerClass();

        startClass.addAssociation(endClass);
    }

    /**
     * Hook method to plug in application behaviour into
     * a template method. This method is called when a
     * connection between two objects has been cancelled.
     */
    protected void handleDisconnect(Figure start, Figure end) {
        super.handleDisconnect(start, end);
        if ((start != null) && (end!= null)) {
            JModellerClass startClass = ((ClassFigure)start).getModellerClass();
            JModellerClass endClass = ((ClassFigure)end).getModellerClass();
            startClass.removeSuperclass(endClass);
        }
    }

    /**
     * Test whether an inheritance relationship between two ClassFigures can
     * be established. An inheritance relationship can be established if
     * there is no cyclic inheritance graph. This method is called before
     * the two classes are connected in the diagram.
     *
     * @param   start   subclass
     * @param   end     superclass
     * @return  true, if an inheritance relationship can be established, false otherwise
     */
    public boolean canConnect(Figure start, Figure end) {
        JModellerClass startClass = ((ClassFigure)start).getModellerClass();
        JModellerClass endClass = ((ClassFigure)end).getModellerClass();

        return !endClass.hasInheritanceCycle(startClass);
    }
}