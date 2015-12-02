package org.fmatjhotdraw.classdiagrams.legacy;

import org.fmatjhotdraw.figures.RectangleFigure;
import org.fmatjhotdraw.figures.TextFigure;
import org.fmatjhotdraw.framework.Figure;
import org.fmatjhotdraw.framework.FigureEnumeration;
import org.fmatjhotdraw.framework.HandleEnumeration;
import org.fmatjhotdraw.standard.BoxHandleKit;
import org.fmatjhotdraw.standard.CompositeFigure;
import org.fmatjhotdraw.standard.HandleEnumerator;
import org.fmatjhotdraw.util.CollectionsFactory;
import org.fmatjhotdraw.util.Storable;
import org.fmatjhotdraw.util.StorableInput;
import org.fmatjhotdraw.util.StorableOutput;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by luisburgos on 26/11/15.
 */
public class ClassHeaderFigure extends CompositeFigure {

    private Rectangle fDisplayBox;

    private RectangleFigure className;
    private RectangleFigure classVars;
    private RectangleFigure classMethods;

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -7877776240236946511L;
    private int pertFigureSerializedDataVersion = 1;
    private TextFigure classNameText;

    public ClassHeaderFigure() {
        initialize();
    }

    @Override
    protected void basicMoveBy(int x, int y) {
        fDisplayBox.translate(x, y);
        super.basicMoveBy(x, y);
    }

    @Override
    public Rectangle displayBox() {
        return new Rectangle(
                fDisplayBox.x,
                fDisplayBox.y,
                fDisplayBox.width,
                fDisplayBox.height);
    }

    @Override
    public void basicDisplayBox(Point origin, Point corner) {
        fDisplayBox = new Rectangle(origin);
        fDisplayBox.add(corner);
        layout();
    }

    private void drawBorder(Graphics g) {
        super.draw(g);

        Rectangle r = displayBox();

        Figure f = figureAt(0);
        Rectangle rf = f.displayBox();
        g.setColor(Color.gray);
        g.drawLine(r.x, r.y+rf.height+2, r.x+r.width, r.y + rf.height+2);
        g.setColor(Color.white);
        g.drawLine(r.x, r.y+rf.height+3, r.x+r.width, r.y + rf.height+3);

        g.setColor(Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x , r.y + r.height, r.x + r.width, r.y + r.height);
    }

    @Override
    public void draw(Graphics g) {
        drawBorder(g);
        super.draw(g);
    }

    @Override
    public HandleEnumeration handles() {
        java.util.List handles = CollectionsFactory.current().createList();
        BoxHandleKit.addHandles(this, handles);
        return new HandleEnumerator(handles);
    }

    private void initialize() {
        fDisplayBox = new Rectangle(0, 0, 0, 0);

        //className = new RectangleFigure();

        //add(className);
    }

    private void layout() {
        Point partOrigin = new Point(fDisplayBox.x, fDisplayBox.y);
        Dimension extent = new Dimension(0, 0);
        Dimension partExtent = new Dimension(fDisplayBox.width, floorDiv(fDisplayBox.height,3));

        FigureEnumeration fe = figures();
        while (fe.hasNextFigure()) {
            Figure f = fe.nextFigure();

            Point corner = new Point(partOrigin.x+partExtent.width,partOrigin.y+partExtent.height);
            TextFigure x = new TextFigure();
            x.setText("Class Name");
            f.addDependendFigure(x);
            f.basicDisplayBox(partOrigin, corner);

            extent.width = Math.max(extent.width, partExtent.width);
            extent.height += partExtent.height;
            partOrigin.y += partExtent.height;
        }
        fDisplayBox.width = extent.width;
        fDisplayBox.height = extent.height;
    }

    private boolean needsLayout() {
        Dimension extent = new Dimension(0, 0);

        FigureEnumeration fe = figures();
        while (fe.hasNextFigure()) {
            Figure f = fe.nextFigure();
            extent.width = Math.max(extent.width, f.size().width);
        }
        int newExtent = extent.width;
        return newExtent != fDisplayBox.width;
    }

    //-- store / load ----------------------------------------------

    @Override
    public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
    }

    public void writeTasks(StorableOutput dw, java.util.List l) {
        dw.writeInt(l.size());
        Iterator iter = l.iterator();
        while (iter.hasNext()) {
            dw.writeStorable((Storable)iter.next());
        }
    }

    @Override
    public void read(StorableInput dr) throws IOException {
        super.read(dr);
        fDisplayBox = new Rectangle(
                dr.readInt(),
                dr.readInt(),
                dr.readInt(),
                dr.readInt());
        layout();
    }

    @Override
    public Insets connectionInsets() {
        Rectangle r = fDisplayBox;
        int cx = r.width/2;
        int cy = r.height/2;
        return new Insets(cy, cx, cy, cx);
    }

    public java.util.List readTasks(StorableInput dr) throws IOException {
        int size = dr.readInt();
        java.util.List l = CollectionsFactory.current().createList(size);
        for (int i=0; i<size; i++) {
            l.add(dr.readStorable());
        }
        return l;
    }


    public int floorDiv(long x, long y) {
        long r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return (int) r;
    }

    public RectangleFigure getClassName() {
        return className;
    }

    public void setClassName(RectangleFigure className) {
        this.className = className;
    }

    public RectangleFigure getClassVars() {
        return classVars;
    }

    public void setClassVars(RectangleFigure classVars) {
        this.classVars = classVars;
    }

    public RectangleFigure getClassMethods() {
        return classMethods;
    }

    public void setClassMethods(RectangleFigure classMethods) {
        this.classMethods = classMethods;
    }

}
