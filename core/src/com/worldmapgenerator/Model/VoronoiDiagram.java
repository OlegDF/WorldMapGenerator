package com.worldmapgenerator.Model;

import java.awt.*;
import java.util.*;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class VoronoiDiagram {

    private final ArrayList<DiagramPoint> points;

    private final float borderLeft, borderRight, borderBottom, borderTop;
    private final int numberOfPoints;

    private VoronoiDiagram(int numberOfPoints, float borderLeft, float borderBottom, float borderRight,
                           float borderTop, long seed) {
        this.numberOfPoints = numberOfPoints;
        this.borderLeft = borderLeft;
        this.borderBottom = borderBottom;
        this.borderRight = borderRight;
        this.borderTop = borderTop;
        points = generatePoints(seed);
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     */
    static VoronoiDiagram randomDiagram(int numberOfPoints, float borderLeft, float borderBottom,
                                               float borderRight, float borderTop) {
        return new VoronoiDiagram(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop,
                (long)(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     */
    static VoronoiDiagram seededDiagram(int numberOfPoints, float borderLeft, float borderBottom,
                                               float borderRight, float borderTop, long seed) {
        return new VoronoiDiagram(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop, seed);
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    static VoronoiDiagram newDiagramStartingAt00(int numberOfPoints, float borderRight, float borderTop) {
        return new VoronoiDiagram(numberOfPoints, 0, 0, borderRight, borderTop,
                (long)(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает массив точек и заполняет их точками со случайными координатами в пределах границы поля;
     * также сортирует их по возрастанию кординаты y
     *
     * @return созданный массив
     */
    private ArrayList<DiagramPoint> generatePoints(long seed) {
        ArrayList<DiagramPoint> newPoints = new ArrayList<>();
        Random random = new Random(seed);
        for(int i = 0; i < numberOfPoints; i++) {
            float newX = borderLeft + random.nextFloat() * (borderRight - borderLeft);
            float newY = borderBottom + random.nextFloat() * (borderTop - borderBottom);
            newPoints.add(new DiagramPoint(newX, newY));
        }
        Collections.sort(newPoints, new Comparator<DiagramPoint>() {
            @Override
            public int compare(DiagramPoint o1, DiagramPoint o2) {
                if(o1.getY() == o2.getY()) {
                    return Float.compare(o1.getX(), o2.getX());
                } else {
                    return Float.compare(o1.getY(), o2.getY());
                }
            }
        });
        return newPoints;
    }

    private void generatePolygons() {
        float sweepLine = 0;
        TreeSet<Arc> beachLine = new TreeSet<>();

    }

    private class Arc implements Comparable<Arc> {

        private final DiagramPoint focus;
        private final float leftBorder;
        private final float rightBorder;

        private Arc(DiagramPoint focus, float leftBorder, float rightBorder) {
            this.focus = focus;
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        private QuadraticEquation getIntersectionEquation(Arc secondArc, float sweepline) {
            float x1 = focus.getX();
            float y1 = focus.getY();
            float x2 = secondArc.focus.getX();
            float y2 = secondArc.focus.getY();
            return new QuadraticEquation(
                    y2 - y1,
                    -2 * (x1 * (y2 - sweepline) - x2 * (y1 - sweepline)),
                    x1 * x1 * (y2 - sweepline) - x2 * x2 * (y1 - sweepline) +
                            (y1 - sweepline) * (y2 - sweepline) * (y1 - y2)
            );
        }

        private Float intersectLeft(Arc secondArc, float sweepline) {
            return getIntersectionEquation(secondArc, sweepline).getLesserRoot();
        }

        private Float intersectRight(Arc secondArc, float sweepline) {
            return getIntersectionEquation(secondArc, sweepline).getLesserRoot();
        }

        @Override
        public int compareTo(Arc o) {
            return this.focus.compareTo(o.focus);
        }
    }

    /**
     * NEW_POINT = очередная точка переходит в
     */
    private enum eventType{
        NEW_POINT, NEW_CORNER
    }

    private class Event {
    }

}
