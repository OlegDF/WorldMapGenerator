package com.worldmapgenerator.Model.DelaunayTriangulation;

import com.worldmapgenerator.Model.DiagramStructure.DiagramPoint;

import java.util.*;

public class DelaunayTriangulation {

    private final ArrayList<DiagramPoint> points;
    private final ArrayList<Triangle> triangles;

    private final double borderLeft, borderRight, borderBottom, borderTop;
    private final int numberOfPoints;

    private DiagramPoint supertrianglePoint1, supertrianglePoint2, supertrianglePoint3;

    private boolean checkingEnabled = false;

    private DelaunayTriangulation(int numberOfPoints, double borderLeft, double borderBottom, double borderRight,
                                  double borderTop, long seed) {
        this.numberOfPoints = numberOfPoints;
        if (borderRight < borderLeft) {
            double swap = borderRight;
            borderRight = borderLeft;
            borderLeft = swap;
        }
        if (borderTop < borderBottom) {
            double swap = borderTop;
            borderTop = borderBottom;
            borderBottom = swap;
        }
        this.borderLeft = borderLeft;
        this.borderBottom = borderBottom;
        this.borderRight = borderRight;
        this.borderTop = borderTop;
        points = generatePoints(seed);
        triangles = generateTriangles();
        constructDiagram();
        if (checkingEnabled) {
            check();
        }
    }

    /**
     * Создает диаграмму с произвольными крайними точками
     */
    public static DelaunayTriangulation randomTriangulation(int numberOfPoints, double borderLeft, double borderBottom,
                                                            double borderRight, double borderTop) {
        return new DelaunayTriangulation(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop,
                (long) (Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с произвольными крайними точками и заданным зерном (т. е. при одном и том же seed
     * генерируются одинаковые диаграммы)
     */
    public static DelaunayTriangulation seededTriangulation(int numberOfPoints, double borderLeft, double borderBottom,
                                                            double borderRight, double borderTop, long seed) {
        return new DelaunayTriangulation(numberOfPoints, borderLeft, borderBottom, borderRight, borderTop, seed);
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    public static DelaunayTriangulation randomTriangulationStartingAt00(int numberOfPoints, double borderRight, double borderTop) {
        return new DelaunayTriangulation(numberOfPoints, 0, 0, borderRight, borderTop,
                (long) (Math.random() * Integer.MAX_VALUE));
    }

    /**
     * Создает диаграмму с левым нижним углом в (0, 0)
     */
    public static DelaunayTriangulation seededTriangulationStartingAt00(int numberOfPoints, double borderRight, double borderTop, long seed) {
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
        for (int i = 0; i < numberOfPoints; i++) {
            double newX = borderLeft + random.nextDouble() * (borderRight - borderLeft);
            double newY = borderBottom + random.nextDouble() * (borderTop - borderBottom);
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
     * Создает триангуляцию з=созданного массива точек в виде массива треугольников
     * @return созданный массив
     */
    private ArrayList<Triangle> generateTriangles() {
        ArrayList<Triangle> triangles = new ArrayList<>();
        //первоначальный треугольник, включающий в себя все сгенерированные точки
        supertrianglePoint1 = new DiagramPoint(borderLeft - (borderRight - borderLeft),
                borderBottom - (borderTop - borderBottom));
        supertrianglePoint2 = new DiagramPoint(borderLeft + (borderRight - borderLeft) / 2,
                borderTop + (borderTop - borderBottom) * 2);
        supertrianglePoint3 = new DiagramPoint(borderLeft + (borderRight - borderLeft) * 2,
                borderBottom - (borderTop - borderBottom));
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

    /**
     * @return набор вершин, определяющих диаграмму
     */
    public ArrayList<DiagramPoint> getPoints() {
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

    public double getMapBorderLeft() {
        return borderLeft;
    }

    public double getMapBorderBottom() {
        return borderBottom;
    }

    public double getMapBorderRight() {
        return borderRight;
    }

    public double getMapBorderTop() {
        return borderTop;
    }

}
