package com.worldmapgenerator.Model.DelaunayTriangulation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;

public class DelaunayTriangulation {

    private final ArrayList<DiagramPoint> points;
    private final ArrayList<Triangle> triangles;

    private final float borderLeft, borderRight, borderBottom, borderTop;
    private final int numberOfPoints;

    private DelaunayTriangulation(int numberOfPoints, float borderLeft, float borderBottom, float borderRight,
                           float borderTop, long seed) {
        this.numberOfPoints = numberOfPoints;
        this.borderLeft = borderLeft;
        this.borderBottom = borderBottom;
        this.borderRight = borderRight;
        this.borderTop = borderTop;
        points = generatePoints(seed);
        triangles = generateTriangles();
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     */
    public static DelaunayTriangulation randomTriangulation(int numberOfPoints, float borderLeft, float borderBottom,
                                        float borderRight, float borderTop) {
        return new DelaunayTriangulation(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop,
                (long)(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     */
    public static DelaunayTriangulation seededTriangulation(int numberOfPoints, float borderLeft, float borderBottom,
                                        float borderRight, float borderTop, long seed) {
        return new DelaunayTriangulation(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop, seed);
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    public static DelaunayTriangulation randomTriangulationStartingAt00(int numberOfPoints, float borderRight, float borderTop) {
        return new DelaunayTriangulation(numberOfPoints, 0, 0, borderRight, borderTop,
                (long)(Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    public static DelaunayTriangulation seededTriangulationStartingAt00(int numberOfPoints, float borderRight, float borderTop, long seed) {
        return new DelaunayTriangulation(numberOfPoints, 0, 0, borderRight, borderTop, seed);
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

    /**
     * Создает триангуляцию з=созданного массива точек в виде массива треугольников
     *
     * @return созданный массив
     */
    private ArrayList<Triangle> generateTriangles() {
        ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(
                new DiagramPoint(borderLeft - (borderRight - borderLeft), borderBottom - (borderTop - borderBottom)),
                new DiagramPoint(borderLeft + (borderRight - borderLeft) / 2 , borderBottom - (borderTop - borderBottom) * 3),
                new DiagramPoint(borderLeft - (borderRight - borderLeft), borderBottom + (borderTop - borderBottom) * 2)
        ));
        for(DiagramPoint point: points) {
            addPointToTriangulation(triangles, point);
        }
        return triangles;
    }

    private void addPointToTriangulation(ArrayList<Triangle> triangles, DiagramPoint point) {
        ArrayList<Triangle> trianglesToEliminate = new ArrayList<>();
        ArrayList<TriangleEdge> edgesToConnect = new ArrayList<>();
        for(Triangle triangle: triangles) {
            if(triangle.isPointInCircumcircle(point)) {
                trianglesToEliminate.add(triangle);
                addEdgeToArray(edgesToConnect, triangle.e12());
                addEdgeToArray(edgesToConnect, triangle.e23());
                addEdgeToArray(edgesToConnect, triangle.e31());
            }
        }
        for(TriangleEdge edge: edgesToConnect) {
            if(edge.numberOfAppearances == 1) {
                triangles.add(new Triangle(edge.p1, edge.p2, point));
            }
        }
        for(Triangle triangle: trianglesToEliminate) {
            triangles.remove(triangle);
        }
    }

    private void addEdgeToArray(ArrayList<TriangleEdge> edgesToConnect, TriangleEdge edgeToAdd) {
        for(TriangleEdge edge: edgesToConnect) {
            if(edge.equals(edgeToAdd)) {
                edge.addAppearance();
                return;
            }
        }
        edgesToConnect.add(edgeToAdd);
    }

}
