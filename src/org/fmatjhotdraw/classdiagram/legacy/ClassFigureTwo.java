package org.fmatjhotdraw.classdiagram.legacy;

import org.fmatjhotdraw.framework.*;
import org.fmatjhotdraw.standard.*;
import org.fmatjhotdraw.figures.*;
import org.fmatjhotdraw.util.*;

import java.awt.*;
import java.io.*;
import java.util.List;
public class ClassFigureTwo extends CompositeFigure {
    private Rectangle fDisplayBox;

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -7877776240236946511L;
    private int pertFigureSerializedDataVersion = 1;

    public ClassFigureTwo() {
        fDisplayBox = new Rectangle(0, 0, 0, 0);
        RectangleFigure className = new RectangleFigure();
        add(className);
        add(new LineFigure());
        add(new LineFigure());
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

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    @Override
    public HandleEnumeration handles() {
        List handles = CollectionsFactory.current().createList();
        BoxHandleKit.addHandles(this, handles);
        return new HandleEnumerator(handles);
    }

    private void layout() {
        Point origin = new Point(fDisplayBox.x, fDisplayBox.y);

        FigureEnumeration fe = figures();
        int sumHeight = floorDiv(fDisplayBox.height,3);
        int counter = 0;
        while (fe.hasNextFigure()) {
            Figure f = fe.nextFigure();
            
            if(f instanceof RectangleFigure){
                Point corner = new Point(fDisplayBox.x+fDisplayBox.width,fDisplayBox.y+fDisplayBox.height);
                f.basicDisplayBox(origin, corner);
            }
            else{
                counter++;
                Point partOrigin = new Point(fDisplayBox.x,fDisplayBox.y+(sumHeight*counter));
                Point partEnd = new Point(fDisplayBox.x+fDisplayBox.width,fDisplayBox.y+(sumHeight*counter));
                f.basicDisplayBox(partOrigin,partEnd);
            }
        }
    }

    @Override
    public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
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

    public int floorDiv(long x, long y) {
        long r = x / y;
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return (int) r;
    }
}