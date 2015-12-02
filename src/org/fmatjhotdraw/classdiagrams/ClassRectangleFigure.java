package org.fmatjhotdraw.classdiagrams;


import org.fmatjhotdraw.figures.AttributeFigure;
import org.fmatjhotdraw.framework.HandleEnumeration;
import org.fmatjhotdraw.util.StorableInput;
import org.fmatjhotdraw.util.StorableOutput;

import java.awt.*;
import java.io.IOException;

/**
 * Created by luisburgos on 25/11/15.
 */
public class ClassRectangleFigure extends AttributeFigure {

    private Rectangle fDisplayBox;
    private static final long serialVersionUID = 184722075881789163L;
    private int rectangleFigureSerializedDataVersion;

    public ClassRectangleFigure() {
        this(new Point(0, 0), new Point(0, 0));
    }

    public ClassRectangleFigure(Point var1, Point var2) {
        this.rectangleFigureSerializedDataVersion = 1;
        this.basicDisplayBox(var1, var2);
    }

    public void basicDisplayBox(Point var1, Point var2) {
        this.fDisplayBox = new Rectangle(var1);
        this.fDisplayBox.add(var2);
    }

    protected void basicMoveBy(int var1, int var2) {
        this.fDisplayBox.translate(var1, var2);
    }

    public Rectangle displayBox() {
        return new Rectangle(this.fDisplayBox.x, this.fDisplayBox.y, this.fDisplayBox.width, this.fDisplayBox.height);
    }

    @Override
    public HandleEnumeration handles() {
        return null;
    }

    public void drawBackground(Graphics var1) {
        Rectangle var2 = this.displayBox();
        var1.fillRect(var2.x, var2.y, var2.width, var2.height);
    }

    public void read(StorableInput var1) throws IOException {
        super.read(var1);
        this.fDisplayBox = new Rectangle(var1.readInt(), var1.readInt(), var1.readInt(), var1.readInt());
    }

    public void write(StorableOutput var1) {
        super.write(var1);
        var1.writeInt(this.fDisplayBox.x);
        var1.writeInt(this.fDisplayBox.y);
        var1.writeInt(this.fDisplayBox.width);
        var1.writeInt(this.fDisplayBox.height);
    }

    public void drawFrame(Graphics var1) {
        Rectangle r = this.displayBox();


        int desireHeight = r.height/3;

        var1.drawLine(
                r.x,
                r.y + desireHeight,
                r.x+r.width - 1,
                r.y + desireHeight
        );

        var1.drawLine(
                r.x,
                r.y + desireHeight * 2,
                r.x+r.width - 1,
                r.y + desireHeight * 2
        );

        var1.drawRect(r.x, r.y, r.width - 1, r.height - 1);
    }


}
