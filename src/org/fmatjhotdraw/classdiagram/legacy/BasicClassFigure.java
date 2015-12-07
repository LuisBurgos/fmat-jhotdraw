package org.fmatjhotdraw.classdiagram.legacy;

import org.fmatjhotdraw.contrib.GraphicalCompositeFigure;
import org.fmatjhotdraw.contrib.TextAreaFigure;
import org.fmatjhotdraw.figures.RectangleFigure;

import java.awt.Point;
import java.awt.Rectangle;

public class BasicClassFigure extends GraphicalCompositeFigure {

    private Rectangle classFigureArea;
    private TextAreaFigure titleSection;
    private RectangleFigure attributesSection;
    private RectangleFigure methodsSection;

    public BasicClassFigure() {
        initializeClassFigure();
    }

    @Override
    protected void change() {
        super.change(); 
    }
    
    
    
    @Override
    protected void basicMoveBy(int x, int y) {
        classFigureArea.translate(x, y);
        super.basicMoveBy(x, y);
    }

    @Override
    public void basicDisplayBox(Point origin, Point destiny) {
        classFigureArea = new Rectangle(origin);
        classFigureArea.add(destiny);
        updateFiguresDimension();
    }

    @Override
    public Rectangle displayBox() {
        return new Rectangle(
                classFigureArea.x,
                classFigureArea.y,
                classFigureArea.width,
                classFigureArea.height);
    }

    private void updateFiguresDimension() {

        double titleHeightProportion = 0.15;
        double attributeHeightProportion = 0.35;
        double methodsHeightProportion = 0.50;

        int titleSectionHeight = (int) (classFigureArea.height * titleHeightProportion);
        int attributeSectionHeight = (int) (classFigureArea.height * attributeHeightProportion);
        int methodsSectionHeight = (int) (classFigureArea.height * methodsHeightProportion);

        Point titleSectionStartPoint = new Point(
                classFigureArea.x, 
                classFigureArea.y);
        Point titleSectionEndPoint = new Point(
                titleSectionStartPoint.x + classFigureArea.width,
                titleSectionStartPoint.y + titleSectionHeight
        );

        titleSection.basicDisplayBox(titleSectionStartPoint, titleSectionEndPoint);

        Point attributeSectionStartPoint = new Point(
                titleSectionStartPoint.x,
                titleSectionEndPoint.y
        );
        Point attrbuteSectionEndPoint = new Point(
                attributeSectionStartPoint.x + classFigureArea.width,
                attributeSectionStartPoint.y + attributeSectionHeight
        );

        attributesSection.basicDisplayBox(attributeSectionStartPoint, attrbuteSectionEndPoint);

        Point methodsSectionStartPoint = new Point(
                attributeSectionStartPoint.x,
                attrbuteSectionEndPoint.y
        );
        Point methodsSectionEndPoint = new Point(
                methodsSectionStartPoint.x + classFigureArea.width,
                methodsSectionStartPoint.y + methodsSectionHeight
        );

        methodsSection.basicDisplayBox(methodsSectionStartPoint, methodsSectionEndPoint);
        

    }

    private void initializeClassFigure() {

        titleSection = new TextAreaFigure();
        attributesSection = new RectangleFigure();
        methodsSection = new RectangleFigure();

        add(titleSection);
        add(attributesSection);
        add(methodsSection);
        
        classFigureArea = new Rectangle();
        
        titleSection.addFigureChangeListener(this);
    }

    public Rectangle getClassFigureArea() {
        return classFigureArea;
    }

    public TextAreaFigure getTitleSection() {
        return titleSection;
    }

    public RectangleFigure getAttributesSection() {
        return attributesSection;
    }

    public RectangleFigure getMethodsSection() {
        return methodsSection;
    }


}
