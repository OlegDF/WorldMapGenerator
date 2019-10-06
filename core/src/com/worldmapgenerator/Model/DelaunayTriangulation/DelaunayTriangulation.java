package com.worldmapgenerator.Model.DelaunayTriangulation;

import com.worldmapgenerator.Model.DiagramStructure.Border;
import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;

public class DelaunayTriangulation {

    private final List<DiagramPoint> points;
    private final List<Triangle> triangles;

    private final Border border;
    private final int numberOfPoints;

    private DiagramPoint supertrianglePoint1, supertrianglePoint2, supertrianglePoint3;

    private boolean checkingEnabled = false;

    private DelaunayTriangulation(int numberOfPoints, Border border, long seed) {
        this.numberOfPoints = numberOfPoints;
        this.border = border;
        points = generatePoints(seed);
        triangles = generateTriangles();
        constructDiagram();
        if (checkingEnabled) {
            check();
        }
    }

    private DelaunayTriangulation(List<DiagramPoint> points, Border border) {
        this.numberOfPoints = points.size();
        this.border = border;
        this.points = points;
        triangles = generateTriangles();
        constructDiagram();
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     */
    public static DelaunayTriangulation randomTriangulation(int numberOfPoints, Border border) {
        return new DelaunayTriangulation(numberOfPoints, border, (long) (Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     */
    public static DelaunayTriangulation seededTriangulation(int numberOfPoints, Border border, long seed) {
        return new DelaunayTriangulation(numberOfPoints, border, seed);
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
        for (int i = 0; i < numberOfPoints; i++) {
            double newX = border.borderLeft + random.nextDouble() * (border.borderRight - border.borderLeft);
            double newY = border.borderBottom + random.nextDouble() * (border.borderTop - border.borderBottom);
            newPoints.add(new DiagramPoint(newX, newY));
        }
        Collections.sort(newPoints, new Comparator<DiagramPoint>() {
            @Override
            public int compare(DiagramPoint o1, DiagramPoint o2) {
                if (o1.getY() == o2.getY()) {
                    return Double.compare(o1.getX(), o2.getX());
                } else {
                    return Double.compare(o1.getY(), o2.getY());
                }
            }
        });
        return newPoints;
    }

    /**
     * Создает триангуляцию созданного массива точек в виде массива треугольников
     * @return созданный массив
     */
    private ArrayList<Triangle> generateTriangles() {
        ArrayList<Triangle> triangles = new ArrayList<>();
        //первоначальный треугольник, включающий в себя все сгенерированные точки
        supertrianglePoint1 = new DiagramPoint(border.borderLeft - (border.borderRight - border.borderLeft),
                border.borderBottom - (border.borderTop - border.borderBottom));
        supertrianglePoint2 = new DiagramPoint(border.borderLeft + (border.borderRight - border.borderLeft) / 2,
                border.borderTop + (border.borderTop - border.borderBottom) * 2);
        supertrianglePoint3 = new DiagramPoint(border.borderLeft + (border.borderRight - border.borderLeft) * 2,
                border.borderBottom - (border.borderTop - border.borderBottom));
        triangles.add(new Triangle(supertrianglePoint1, supertrianglePoint2, supertrianglePoint3));
        for (DiagramPoint point : points) {
            addPointToTriangulation(triangles, point);
        }
        for (Triangle triangle : triangles) {
            triangle.createCircumcenter(supertrianglePoint1, supertrianglePoint2, supertrianglePoint3);
        }
        cleanUpSupertriangle(triangles);
        return triangles;
    }

    /**
     * Добавляет очередную вершина к множеству учтенных в триангуляции, разделяя существующие треугольники
     * @param triangles - существующая триангуляция
     * @param point - новая вершина
     */
    private void addPointToTriangulation(ArrayList<Triangle> triangles, DiagramPoint point) {
        ArrayList<Triangle> trianglesToEliminate = new ArrayList<>();
        ArrayList<TriangleEdge> edgesToConnect = new ArrayList<>();
        for (Triangle triangle : triangles) {
            if (triangle.isPointInCircumcircle(point)) {
                trianglesToEliminate.add(triangle);
                addEdgeToArray(edgesToConnect, triangle.e12());
                addEdgeToArray(edgesToConnect, triangle.e23());
                addEdgeToArray(edgesToConnect, triangle.e31());
            }
        }
        for (TriangleEdge edge : edgesToConnect) {
            if (edge.numberOfAppearances == 1) {
                triangles.add(new Triangle(edge.p1, edge.p2, point));
            }
        }
        for (Triangle triangle : trianglesToEliminate) {
            triangles.remove(triangle);
        }
    }

    /**
     * Добавляет ребро в массив рёбер, которые могут стать основой для новых треугольников
     * @param edgesToConnect - существующие ребра с количеством раз, которые каждое ребро встречается
     * @param edgeToAdd - новое ребро
     */
    private void addEdgeToArray(ArrayList<TriangleEdge> edgesToConnect, TriangleEdge edgeToAdd) {
        for (TriangleEdge edge : edgesToConnect) {
            if (edge.equals(edgeToAdd)) {
                edge.addAppearance();
                return;
            }
        }
        edgesToConnect.add(edgeToAdd);
    }

    /**
     * Убирает из триангуляции все треугольники, включающие в себя вершины первого, наибольшего треугольника
     * @param triangles - массив треугольников
     */
    private void cleanUpSupertriangle(ArrayList<Triangle> triangles) {
        for (Iterator<Triangle> triangleIterator = triangles.iterator(); triangleIterator.hasNext(); ) {
            Triangle triangle = triangleIterator.next();
            if (triangle.isPointVertex(supertrianglePoint1) ||
                    triangle.isPointVertex(supertrianglePoint2) ||
                    triangle.isPointVertex(supertrianglePoint3)) {
                triangleIterator.remove();
            }
        }
    }

    /**
     * Соединяет вершины диаграммы ребрами на основе сгенерированных треугольников
     */
    private void constructDiagram() {
        for (Triangle triangle : triangles) {
            triangle.e12().connectPoints();
            triangle.e23().connectPoints();
            triangle.e31().connectPoints();
        }
    }

    public DelaunayTriangulation getLloydRelaxation() {
        ArrayList<DiagramPoint> newPoints = new ArrayList<>();
        for(DiagramPoint point: points) {
            newPoints.add(point.getPolygonCenter(border));
        }
        return new DelaunayTriangulation(newPoints, border);
    }

    /**
     * @return набор вершин, определяющих диаграмму
     */
    public List<DiagramPoint> getPoints() {
        return points;
    }

    /**
     * Проверяет правильность триангуляции Делоне по определению (никакой из ее треугольников не содержит никаких
     * вершин диаграммы, кроме 3 собственных)
     */
    private void check() {
        for (Triangle triangle : triangles) {
            for (DiagramPoint point : points) {
                if (!triangle.isPointVertex(point) &&
                        triangle.isPointInCircumcircle(point)) {
                    System.out.println("Point " + (int) (point.getX() * 1000) + ";" + (int) (point.getY() * 1000) + " is in the circumcircle of triangle " +
                            (int) (triangle.e12().p1.getX() * 1000) + ";" + (int) (triangle.e12().p1.getY() * 1000) + " " +
                            (int) (triangle.e12().p2.getX() * 1000) + ";" + (int) (triangle.e12().p2.getY() * 1000) + " " +
                            (int) (triangle.e23().p1.getX() * 1000) + ";" + (int) (triangle.e23().p1.getY() * 1000) + " " +
                            (int) (triangle.e23().p2.getX() * 1000) + ";" + (int) (triangle.e23().p2.getY() * 1000) + " " +
                            (int) (triangle.e31().p1.getX() * 1000) + ";" + (int) (triangle.e31().p1.getY() * 1000) + " " +
                            (int) (triangle.e31().p2.getX() * 1000) + ";" + (int) (triangle.e31().p2.getY() * 1000));
                }
            }
        }
    }

    public Border getMapBorder() {
        return border;
    }

}
