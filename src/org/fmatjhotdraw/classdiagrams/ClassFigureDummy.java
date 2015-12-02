package org.fmatjhotdraw.classdiagrams;

import org.fmatjhotdraw.figures.AttributeFigure;
import org.fmatjhotdraw.framework.HandleEnumeration;
import org.fmatjhotdraw.standard.BoxHandleKit;
import org.fmatjhotdraw.standard.HandleEnumerator;
import org.fmatjhotdraw.util.CollectionsFactory;
import org.fmatjhotdraw.util.StorableInput;
import org.fmatjhotdraw.util.StorableOutput;

import java.awt.*;
import java.io.IOException;

/**
 * Created by luisburgos on 24/11/15.
 */
public class ClassFigureDummy extends AttributeFigure {

    private Rectangle fDisplayBox;

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = 184722075881789163L;
    private int rectangleFigureSerializedDataVersion = 1;

    public ClassFigureDummy() {
        this(new Point(0,0), new Point(0,0));
    }

    public ClassFigureDummy(Point origin, Point corner) {
        basicDisplayBox(origin,corner);
    }

    public void basicDisplayBox(Point origin, Point corner) {
        fDisplayBox = new Rectangle(origin);
        fDisplayBox.add(corner);


    }

    public HandleEnumeration handles() {
        java.util.List handles = CollectionsFactory.current().createList();
        BoxHandleKit.addHandles(this, handles);
        return new HandleEnumerator(handles);
    }

    public Rectangle displayBox() {
        return new Rectangle(
                fDisplayBox.x,
                fDisplayBox.y,
                fDisplayBox.width,
                fDisplayBox.height);
    }

    protected void basicMoveBy(int x, int y) {
        fDisplayBox.translate(x,y);
    }

    public void drawBackground(Graphics g) {
        Rectangle r = displayBox();
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
    }

    public void read(StorableInput dr) throws IOException {
        super.read(dr);
        fDisplayBox = new Rectangle(
                dr.readInt(),
                dr.readInt(),
                dr.readInt(),
                dr.readInt());
    }


    public void drawFrame(Graphics g) {
        Rectangle r = this.displayBox();


        int desireHeight = r.height/3;

        g.drawLine(
                r.x,
                r.y + desireHeight,
                r.x+r.width - 1,
                r.y + desireHeight
        );

        g.drawLine(
                r.x,
                r.y + desireHeight * 2,
                r.x+r.width - 1,
                r.y + desireHeight * 2
        );

        g.drawRect(r.x, r.y, r.width - 1, r.height - 1);
    }

}
