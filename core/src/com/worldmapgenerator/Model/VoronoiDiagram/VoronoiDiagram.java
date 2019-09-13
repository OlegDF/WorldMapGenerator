package com.worldmapgenerator.Model.VoronoiDiagram;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;
import com.worldmapgenerator.Model.QuadraticEquation;

import java.awt.*;
import java.util.*;

public class VoronoiDiagram {

    private final ArrayList<DiagramPoint> points;

    private final double borderLeft, borderRight, borderBottom, borderTop;
    private final int numberOfPoints;

    private VoronoiDiagram(int numberOfPoints, double borderLeft, double borderBottom, double borderRight,
                           double borderTop, long seed) {
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
    static VoronoiDiagram randomDiagram(int numberOfPoints, double borderLeft, double borderBottom,
                                        double borderRight, double borderTop) {
        return new VoronoiDiagram(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop,
                (long)(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     */
    static VoronoiDiagram seededDiagram(int numberOfPoints, double borderLeft, double borderBottom,
                                        double borderRight, double borderTop, long seed) {
        return new VoronoiDiagram(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop, seed);
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    static VoronoiDiagram randomDiagramStartingAt00(int numberOfPoints, double borderRight, double borderTop) {
        return new VoronoiDiagram(numberOfPoints, 0, 0, borderRight, borderTop,
                (long)(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    static VoronoiDiagram seededDiagramStartingAt00(int numberOfPoints, double borderRight, double borderTop, long seed) {
        return new VoronoiDiagram(numberOfPoints, 0, 0, borderRight, borderTop, seed);
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
            double newX = borderLeft + random.nextDouble() * (borderRight - borderLeft);
            double newY = borderBottom + random.nextDouble() * (borderTop - borderBottom);
            newPoints.add(new DiagramPoint(newX, newY));
        }
        Collections.sort(newPoints, new Comparator<DiagramPoint>() {
            @Override
            public int compare(DiagramPoint o1, DiagramPoint o2) {
                if(o1.getY() == o2.getY()) {
                    return Double.compare(o1.getX(), o2.getX());
                } else {
                    return Double.compare(o1.getY(), o2.getY());
                }
            }
        });
        return newPoints;
    }

    private void generatePolygons() {
        double sweepLine = 0;
        TreeSet<Arc> beachLine = new TreeSet<>();
        PriorityQueue<Event> eventsQueue = new PriorityQueue<>();
        for(DiagramPoint point: points) {
            eventsQueue.add(new EventNewPoint(point));
        }
        while(!eventsQueue.isEmpty()) {
            Event currentEvent = eventsQueue.poll();
            sweepLine = currentEvent.newSweepLine;
            switch (currentEvent.type) {
                case NEW_POINT:
                    EventNewPoint currentNewPointEvent = (EventNewPoint)currentEvent;
                    Arc newArc = new Arc(currentNewPointEvent.causingPoint, currentNewPointEvent.causingPoint.getX(), currentNewPointEvent.causingPoint.getX());
                    Arc interruptedArc = beachLine.lower(newArc);
                    beachLine.remove(interruptedArc);
                    break;
            }
        }
        Polygon poly = new Polygon();
    }

    /**
     * Дуга - составляющая границы существующей части диаграммы.
     * Имеет форму параболы с фокусом в одной из точек
     */
    private class Arc implements Comparable<Arc> {

        private final DiagramPoint focus;
        private final double leftBorder;
        private final double rightBorder;

        private Arc(DiagramPoint focus, double leftBorder, double rightBorder) {
            this.focus = focus;
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        private QuadraticEquation getIntersectionEquation(Arc secondArc, double sweepline) {
            double x1 = focus.getX();
            double y1 = focus.getY();
            double x2 = secondArc.focus.getX();
            double y2 = secondArc.focus.getY();
            return new QuadraticEquation(
                    y2 - y1,
                    -2 * (x1 * (y2 - sweepline) - x2 * (y1 - sweepline)),
                    x1 * x1 * (y2 - sweepline) - x2 * x2 * (y1 - sweepline) +
                            (y1 - sweepline) * (y2 - sweepline) * (y1 - y2)
            );
        }

        private Double intersectLeft(Arc secondArc, double sweepline) {
            return getIntersectionEquation(secondArc, sweepline).getLesserRoot();
        }

        private Double intersectRight(Arc secondArc, double sweepline) {
            return getIntersectionEquation(secondArc, sweepline).getLesserRoot();
        }

        @Override
        public int compareTo(Arc o) {
            return Double.compare(this.leftBorder, o.leftBorder);
        }
    }

    /**
     * NEW_POINT = очередная точка переходит за направляющую
     * NEW_CORNER = дуга схлопывается в 0 и ее соседние дуги встречаются, образуя новый угол в диаграмме
     */
    private enum EventType {
        NEW_POINT, NEW_CORNER
    }

    private abstract class Event implements Comparable<Event> {

        EventType type;
        double newSweepLine;

        Event() {
            this.type = EventType.NEW_POINT;
            this.newSweepLine = Double.MAX_VALUE;
        }

        abstract void resolveEvent();

        @Override
        public int compareTo(Event o) {
            return Double.compare(newSweepLine, o.newSweepLine);
        }

    }

    private class EventNewPoint extends Event {

        private final DiagramPoint causingPoint;

        EventNewPoint(DiagramPoint causingPoint) {
            super();
            this.causingPoint = causingPoint;
            this.type = EventType.NEW_POINT;
            this.newSweepLine = causingPoint.getY();
        }

        @Override
        void resolveEvent() {
        }

    }

    private class EventNewCorner extends Event {

        private final Arc leftArc, midArc, rightArc;

        EventNewCorner(Arc leftArc, Arc midArc, Arc rightArc, double newSweepLine) {
            super();
            this.type = EventType.NEW_CORNER;
            this.leftArc = leftArc;
            this.midArc = midArc;
            this.rightArc = rightArc;
            this.newSweepLine = newSweepLine;
        }

        @Override
        void resolveEvent() {
        }

    }

}
